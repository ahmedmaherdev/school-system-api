package com.ahmedmaher.schoolsystem.advice;

import com.ahmedmaher.schoolsystem.dto.CustomErrorResDTO;
import com.ahmedmaher.schoolsystem.exception.BadRequestException;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResDTO> handleValidationException(MethodArgumentNotValidException ex) {
        CustomErrorResDTO errorDTO = new CustomErrorResDTO(ex.getBindingResult().getFieldError().getDefaultMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResDTO> handleValidationException(DuplicatedException ex) {
        CustomErrorResDTO errorDTO = new CustomErrorResDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResDTO> handleBadCredentialsException(BadCredentialsException ex) {
        CustomErrorResDTO customErrorResDTO = new CustomErrorResDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResDTO> handleBadCredentialsException(BadRequestException ex) {
        CustomErrorResDTO customErrorResDTO = new CustomErrorResDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResDTO);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResDTO> handleMaxUploadFileException(MaxUploadSizeExceededException ex) {
        CustomErrorResDTO customErrorResDTO = new CustomErrorResDTO(ex.getMessage() , "fail" ,System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorResDTO> handleNotFoundException(NotFoundException ex){
        CustomErrorResDTO errorDTO = new CustomErrorResDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<CustomErrorResDTO> handleUnauthorizedException(UnauthorizedException ex) {
        CustomErrorResDTO errorDTO = new CustomErrorResDTO(ex.getMessage(), "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<CustomErrorResDTO> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorResDTO customErrorResDTO = new CustomErrorResDTO("you do not have permission to make this action.", "fail" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customErrorResDTO);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorResDTO> unhandleException(RuntimeException ex) {
        CustomErrorResDTO customErrorResDTO = new CustomErrorResDTO(ex.getMessage(), "error" , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorResDTO);
    }
}
