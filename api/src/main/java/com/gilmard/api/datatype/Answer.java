package com.gilmard.api.datatype;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

public class Answer {
    private final QuestionType type;
    private final ZonedDateTime timestampAsked;
    private String data;
    private ZonedDateTime timestampAnswered;
    private String serviceAddress;

    public Answer() {
        this.type = null;
        this.timestampAsked = null;
        this.data = null;
        this.timestampAnswered = null;
    }

    public Answer(QuestionType type) {
        this.type = type;
        this.timestampAsked = ZonedDateTime.now();
    }

    public QuestionType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    public ZonedDateTime getTimestampAnswered() {
        return timestampAnswered;
    }

    public void assignTimestampAnswered() {
        this.timestampAnswered = ZonedDateTime.now();
    }

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    public ZonedDateTime getTimestampAsked() {
        return timestampAsked;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
