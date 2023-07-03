package com.gilmard.microservices.ask.me;

import com.gilmard.api.AskMe;
import com.gilmard.api.datatype.Answer;
import com.gilmard.api.datatype.QuestionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@RestController
public class AskMeServiceImpl implements AskMe {

    private static final Logger LOG = LoggerFactory.getLogger(AskMeServiceImpl.class);

    private final StreamBridge streamBridge;
    private final Scheduler scheduler;

    private CompletableFuture<Answer> waitForAnswer;

    public AskMeServiceImpl(StreamBridge streamBridge,
                            @Qualifier("getAskMeScheduler") Scheduler scheduler) {
        this.streamBridge = streamBridge;
        this.scheduler = scheduler;
    }

    @Override
    public Mono<Answer> question(String type) {

        QuestionType questionType = QuestionType.from(type);
        waitForAnswer = new CompletableFuture<>();
        streamBridge.send("questionSupplier-out-0", questionType);

        return Mono.fromCallable(() -> {
            try {
                final Answer answer = waitForAnswer.get(); // BAD practice, just for simple demo
                // answer.assignTimestampAnswered(); // comment out to check the pure messaging time
                LOG.info("Time elapsed to get answer: {} microsecond",
                        calcTimeDifference(answer.getTimestampAsked(),answer.getTimestampAnswered(), ChronoUnit.MICROS));
                return answer;
            } catch (Exception e) {
                return new Answer(QuestionType.UNKNOWN);
            }
        }).subscribeOn(scheduler);
    }

    @Bean
    public Consumer<Answer> answerConsumer() {
        return answer -> waitForAnswer.complete(answer);
    }

    private long calcTimeDifference(ZonedDateTime z1, ZonedDateTime z2, ChronoUnit unit) {
        return unit.between(z1, z2);
    }
}