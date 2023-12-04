package com.ahmedmaher.schoolsystem.advice;

import com.ahmedmaher.schoolsystem.dto.CustomErrorResponseDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorResponseDTO> handleNotFoundException(NotFoundException ex){
        CustomErrorResponseDTO errorDTO = new CustomErrorResponseDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        CustomErrorResponseDTO errorDTO = new CustomErrorResponseDTO(ex.getBindingResult().getFieldError().getDefaultMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponseDTO> handleValidationException(DuplicatedException ex) {
        CustomErrorResponseDTO errorDTO = new CustomErrorResponseDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<CustomErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex) {
        CustomErrorResponseDTO errorDTO = new CustomErrorResponseDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        CustomErrorResponseDTO customErrorResponseDTO = new CustomErrorResponseDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponseDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<CustomErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorResponseDTO customErrorResponseDTO = new CustomErrorResponseDTO("you do not have permission to make this action.", "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customErrorResponseDTO);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorResponseDTO> unhandleException(RuntimeException ex) {
        CustomErrorResponseDTO customErrorResponseDTO = new CustomErrorResponseDTO(ex.getMessage(), "error" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorResponseDTO);
    }
}
