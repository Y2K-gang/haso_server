package org.example.haso.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)

public record BaseResponse<T> (
        int status,
        String message,
        T data
){}

