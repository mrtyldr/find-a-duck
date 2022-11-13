package com.kodizim.kodforum.api;


import com.kodizim.kodforum.api.model.ErrorResponse;
import com.kodizim.kodforum.error.AlreadyExistsException;
import com.kodizim.kodforum.error.ApiException;
import com.kodizim.kodforum.error.ErrorCode;
import com.kodizim.kodforum.error.InProgressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestErrorHandler {



    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ErrorResponse handleException(Throwable e) {
        log.error(ErrorCode.UNKNOWN.name() + " error occurred! Message: {} ", e.getMessage());

        var message = "An unknown error occurred!";
        return ErrorResponse.of(ErrorCode.UNKNOWN.name(), message);
    }
    @ExceptionHandler()
    @ResponseStatus(FORBIDDEN)
    void handle(AccessDeniedException e) {

    }


    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(ApiException e) {
        var status = switch (e.getCode()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case UNKNOWN -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.of(e.getCode().name(), e.getMessage(), e.getDetail()));
    }



    @ExceptionHandler
    @ResponseStatus(ACCEPTED)
    ErrorResponse handle(InProgressException e) {
        return ErrorResponse.of(ErrorCode.IN_PROGRESS.name(), e.getMessage());
    }



    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handle(MaxUploadSizeExceededException e) {
        return ErrorResponse.of(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.name(), e.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handle(AlreadyExistsException e) {

        return ErrorResponse.of(ErrorCode.ALREADY_EXISTS.name(),e.getMessage());
    }





    @ExceptionHandler()
    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    void handle(HttpMediaTypeNotSupportedException e) {

    }

    @ExceptionHandler()
    @ResponseStatus(NOT_ACCEPTABLE)
    void handle(HttpMediaTypeNotAcceptableException e) {

    }
}
