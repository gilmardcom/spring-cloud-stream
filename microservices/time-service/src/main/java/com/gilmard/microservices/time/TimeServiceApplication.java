package com.gilmard.microservices.time;

import com.gilmard.api.datatype.Answer;
import com.gilmard.api.datatype.QuestionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalTime;
import java.util.function.Function;

@SpringBootApplication
@ComponentScan("com.gilmard")
public class TimeServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(TimeServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TimeServiceApplication.class, args);
    }

    @Bean
    public Function<QuestionType, Answer> timeProcessor() {
        return questionType -> {
            Answer answer = new Answer(QuestionType.TIME);
            answer.setData(LocalTime.now().toString());
            answer.assignTimestampAnswered();
            return answer;
        };
    }
}
