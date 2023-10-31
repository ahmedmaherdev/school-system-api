package com.ahmedmaher.schoolsystem.advice;


import com.ahmedmaher.schoolsystem.dto.CustomErrorDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<CustomErrorDTO> unhandleException(RuntimeException ex) {
//        CustomErrorDTO customErrorDTO = new CustomErrorDTO("Something, went wrong. please, try again later." , "error" , System.currentTimeMillis());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorDTO);
//    }
}
