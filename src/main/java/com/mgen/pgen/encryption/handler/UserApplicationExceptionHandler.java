package com.mgen.pgen.encryption.handler;

import com.mgen.pgen.encryption.data.dto.ErrorDTO;
import com.mgen.pgen.encryption.data.dto.UserApplicationResponse;
import com.mgen.pgen.encryption.exception.UserGlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserApplicationResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        UserApplicationResponse userApplicationResponse = new UserApplicationResponse();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ErrorDTO errorDTO = new ErrorDTO(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            errorDTOList.add(errorDTO);
        });
        userApplicationResponse.setErrors(errorDTOList);
        return userApplicationResponse;
    }


    @ExceptionHandler(UserGlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserApplicationResponse<?> handleGlobalException(UserGlobalException ex) {
        UserApplicationResponse userApplicationResponse = new UserApplicationResponse();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        errorDTOList.add(errorDTO);
        userApplicationResponse.setErrors(errorDTOList);
        return userApplicationResponse;
    }
}
