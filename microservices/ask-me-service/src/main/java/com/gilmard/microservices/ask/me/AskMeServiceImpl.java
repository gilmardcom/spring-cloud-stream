package com.gilmard.microservices.ask.me;

import com.gilmard.api.AskMe;
import com.gilmard.api.datatype.Answer;
import com.gilmard.api.datatype.QuestionType;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

@RestController
public class AskMeServiceImpl implements AskMe {

    private final StreamBridge streamBridge;

    private CompletableFuture<Answer> waitForAnswer;


    public AskMeServiceImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public Mono<Answer> question(String type) {

        QuestionType questionType = QuestionType.from(type);
        waitForAnswer = new CompletableFuture<>();
        System.out.println(">>> Question asked");
        streamBridge.send("questionProcessor-in-0", questionType);

        return Mono.fromCallable(() -> {
            try {
                return waitForAnswer.get(); // BAD practice, just for simple demo
            } catch (Exception e) {
                return new Answer(QuestionType.UNKNOWN);
            }
        });
    }

    @Bean
    public Function<QuestionType, Answer> questionProcessor() {
        return questionType -> {
            System.out.println("~~~ Question processed");
            return new Answer(questionType);
        };
    }

    @Bean
    public Consumer<Answer> answerConsumer() {
        return answer -> {
            System.out.println("<<< Question answered");
            waitForAnswer.complete(answer);
        };
    }
}