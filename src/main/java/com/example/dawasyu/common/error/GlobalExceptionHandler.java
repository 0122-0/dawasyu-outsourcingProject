package com.example.dawasyu.common.error;

import java.util.ArrayList;
import java.util.List;

import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice // 글로벌 예외 컨트롤러
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        log.error("CustomException: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    // valid 예외 처리
    // 컨트롤러에서 개별적으로 처리하지 않은 경우 여기서 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());
        List<ErrorResponse.FieldError> fieldErrors = processFieldErrors(e.getBindingResult());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Repository(JPA) 계층 예외 처리 - EntityNotFoundException 처리
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("EntityNotFoundException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Repository(JPA) 계층 예외 처리 - DataAccessException 처리
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        log.error("DataAccessException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, "데이터베이스 접근 중 오류 발생");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 지원하지 않는 HTTP 메소드 호출 시 발생하는 예외 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 요청 파라미터 타입 불일치 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 필수 요청 파라미터 누락 예외 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // JSON 파싱 오류 등의 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, "요청 본문을 파싱할 수 없습니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Bean Validation 관련 예외 처리
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("BindException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, "형식이 올바르지 않습니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 그 외 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage<Void>> handleServerError(Exception ex) {
        ResponseMessage<Void> response = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // BindingResult 에서 발생한 필드 에러 목록을 ErrorResponse.FieldError 목록으로 반환
    private List<ErrorResponse.FieldError> processFieldErrors(BindingResult bindingResult) {
        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ErrorResponse.FieldError error = ErrorResponse.FieldError.of(
                    fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage());
            fieldErrors.add(error);
        }

        return fieldErrors;
    }
}