package com.gilmard.microservices.distributor;

import com.gilmard.api.datatype.Answer;
import com.gilmard.api.datatype.QuestionType;
import com.gilmard.util.http.ServiceAddressFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.function.Consumer;

@SpringBootApplication
@ComponentScan("com.gilmard")
public class DistributorServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DistributorServiceApplication.class);

    private final StreamBridge streamBridge;

    private final ServiceAddressFinder serviceAddressFinder;

    public DistributorServiceApplication(StreamBridge streamBridge, ServiceAddressFinder serviceAddressFinder) {
        this.streamBridge = streamBridge;
        this.serviceAddressFinder = serviceAddressFinder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DistributorServiceApplication.class, args);
    }

    @Bean
    public Consumer<QuestionType> questionDistributor() {
        return questionType -> {
            switch (questionType) {
                case TIME:
                    streamBridge.send("timeQuestion-out-0", questionType);
                    break;
                case DATE:
                    streamBridge.send("dateQuestion-out-0", questionType);
                    break;
                case UNKNOWN:
                default:
                    final Answer answer = new Answer(QuestionType.UNKNOWN);
                    answer.assignTimestampAnswered();
                    answer.setServiceAddress(serviceAddressFinder.getServiceAddress());
                    streamBridge.send("unknownAnswer-out-0", answer);
                    break;
            }
        };
    }
}
