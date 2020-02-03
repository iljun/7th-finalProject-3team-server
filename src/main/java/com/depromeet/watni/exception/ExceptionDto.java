package com.depromeet.watni.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExceptionDto {
    private HttpStatus error;
    private String error_description;

    public ExceptionDto(HttpStatus error, String error_description){
        this.error = error;
        this.error_description = error_description;
    }
}
