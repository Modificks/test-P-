package com.PUMB.test.exceptionHandler;

import com.PUMB.test.exceptionHandler.exception.FileParsingException;
import com.PUMB.test.exceptionHandler.exception.MissingFileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseError handleWrongFileTypeException(UnsupportedMediaTypeStatusException e) {

        return new ResponseError(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMissingFileException(MissingFileException e) {
        return new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileParsingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleFileParsingException(FileParsingException e) {
        return new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}