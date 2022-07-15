package com.betacampers.desafio_quality.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomError> propertyWithoutRoomHandler(CustomException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getError());
    }

    // Trata as exceções lançadas quando ocorre erro na validção dos atributos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        // Organiza todos os erros recebidos em uma lista.
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        // Instancia um CustomError, recebendo o simpleName da exception no primeiro parâmetro
        // e transformando as mensagens de cada FieldError em uma String no segundo parâmetro
        CustomError error = new CustomError(exception.getClass().getSimpleName(), errors.stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(" | ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Trata as exceções lançadas quando ocorre erro na transformação do JSON recebido em Objeto Java
    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<CustomError> invalidFormatHandler(MismatchedInputException exception) {
        // Através do caminho do erro (path), pega o campo (getFieldName) onde o erro ocorreu. Quando o erro ocorrer
        // dentro de uma lista, o replace substituirá o valor "null" por ":" para melhorar a visualização.
        String field = exception.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining())
                .replace("null", ":");

        // Concatena uma mensagem contendo o nome do campo e o tipo esperado no campo.
        String message = field + ": esperado " + exception.getTargetType().getSimpleName() + ".";

        // Instancia um CustomError, recebendo o simpleName da exception no primeiro parâmetro
        // e a mensagem no segundo parâmetro.
        CustomError error = new CustomError(exception.getClass().getSimpleName(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
