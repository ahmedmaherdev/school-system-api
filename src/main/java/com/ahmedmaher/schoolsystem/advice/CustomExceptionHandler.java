package com.ahmedmaher.schoolsystem.advice;


import com.ahmedmaher.schoolsystem.dto.CustomErrorDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorDTO> handleNotFoundException(NotFoundException ex){
        CustomErrorDTO errorDTO = new CustomErrorDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorDTO> handleValidationException(MethodArgumentNotValidException ex) {
        CustomErrorDTO errorDTO = new CustomErrorDTO(ex.getBindingResult().getFieldError().getDefaultMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorDTO> handleValidationException(DuplicatedException ex) {
        CustomErrorDTO errorDTO = new CustomErrorDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<CustomErrorDTO> handleUnauthorizedException(UnauthorizedException ex) {
        CustomErrorDTO errorDTO = new CustomErrorDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorDTO> handleBadCredentialsException(BadCredentialsException ex) {
        CustomErrorDTO customErrorDTO = new CustomErrorDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<CustomErrorDTO> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorDTO customErrorDTO = new CustomErrorDTO("you dn not have permission to make this action.", "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customErrorDTO);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorDTO> unhandleException(RuntimeException ex) {
        CustomErrorDTO customErrorDTO = new CustomErrorDTO(ex.getMessage(), "error" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorDTO);
    }
}
