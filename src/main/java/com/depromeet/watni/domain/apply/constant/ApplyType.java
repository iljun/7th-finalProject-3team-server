package com.depromeet.watni.domain.apply.constant;

public enum  ApplyType {

    CODE(Values.CODE);

    private String value;

    ApplyType(String value) {
        if (!this.name().equals(value)) {
            throw new IllegalArgumentException("Incorrect applyType value");
        }
    }

    public static class Values {
        public static final String CODE = "CODE";
    }
}
