package com.zerutis.codingchallenge.exception;

import java.time.LocalDateTime;

public record ApiException(
        String message,
        LocalDateTime dateTime) {
}
