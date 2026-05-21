package com.sn.socialnetworkapp.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiResponseDto<T> {
    T data;
    boolean success;
    List<Error> errors;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Error {
        String message;
        int code;
    }

    public static <T> ApiResponseDto<T> success(T data) {
        return ApiResponseDto
                .<T>builder()
                .success(true)
                .data(data)
                .errors(Collections.emptyList())
                .build();
    }

    public static <T> ApiResponseDto<T> failure(String message) {
        return failure(0, message);
    }

    public static <T> ApiResponseDto<T> failure(int code, String message) {
        List<Error> errors = Collections.singletonList(Error.builder()
                .code(code)
                .message(message)
                .build());

        return ApiResponseDto
                .<T>builder()
                .success(false)
                .errors(errors)
                .build();
    }
}
