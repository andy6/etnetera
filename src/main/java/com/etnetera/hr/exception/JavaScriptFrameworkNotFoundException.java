package com.etnetera.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Simple exception throws if JavaScript Framework was not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JavaScriptFrameworkNotFoundException extends RuntimeException {

    public JavaScriptFrameworkNotFoundException() {
        super();
    }

    public JavaScriptFrameworkNotFoundException(final String message) {
        super(message);
    }
}
