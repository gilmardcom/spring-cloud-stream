package com.gilmard.api.datatype;

public enum QuestionType {

    TIME, DATE, UNKNOWN;

    public static QuestionType from(String in) {
        try {
            return QuestionType.valueOf(in.toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
