package com.gilmard.microservices.date;

import com.gilmard.api.datatype.Answer;
import com.gilmard.api.datatype.QuestionType;
import com.gilmard.util.http.ServiceAddressFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.function.Function;

@SpringBootApplication
@ComponentScan("com.gilmard")
public class DateServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DateServiceApplication.class);

    private final ServiceAddressFinder serviceAddressFinder;

    public DateServiceApplication(ServiceAddressFinder serviceAddressFinder) {
        this.serviceAddressFinder = serviceAddressFinder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DateServiceApplication.class, args);
    }

    @Bean
    public Function<QuestionType, Answer> dateProcessor() {
        return questionType -> {
            Answer answer = new Answer(QuestionType.DATE);
            answer.setData(LocalDate.now().toString());
            answer.assignTimestampAnswered();
            answer.setServiceAddress(serviceAddressFinder.getServiceAddress());
            return answer;
        };
    }
}
