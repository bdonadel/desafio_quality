package com.betacampers.desafio_quality.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handle(Exception ignored) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomError("Erro interno", "Ocorreu um erro interno no servidor."));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomError> handle(CustomException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getError());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> handle(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getCause();

        if (cause instanceof MismatchedInputException) {
            return handle((MismatchedInputException) cause);
        }

        return ResponseEntity
                .badRequest()
                .body(new CustomError("Requisição inválida", "A requisição está malformada."));
    }

    // Trata as exceções lançadas quando ocorre erro na validção dos atributos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handle(MethodArgumentNotValidException exception) {
        // Organiza todos os erros recebidos em uma lista.
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        // Instancia um CustomError, recebendo o simpleName da exception no primeiro parâmetro
        // e transformando as mensagens de cada FieldError em uma String no segundo parâmetro
        CustomError error = new CustomError("Campo(s) inválido(s)", errors.stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(" | ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Trata as exceções lançadas quando ocorre erro na transformação do JSON recebido em Objeto Java
    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<CustomError> handle(MismatchedInputException exception) {
        // Através do caminho do erro (path), pega o campo (getFieldName) onde o erro ocorreu. Quando o erro ocorrer
        // dentro de uma lista, o replace substituirá o valor "null" por ":" para melhorar a visualização.
        String field = exception.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining())
                .replace("null", "");

        // Concatena uma mensagem contendo o nome do campo e o tipo esperado no campo.
        String message = field + ": esperado " + getJsonTypeName(exception.getTargetType()) + ".";

        // Instancia um CustomError, recebendo o simpleName da exception no primeiro parâmetro
        // e a mensagem no segundo parâmetro.
        CustomError error = new CustomError("Campo(s) inválido(s)", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private String getJsonTypeName(Class<?> type) {
        if (Number.class.isAssignableFrom(type)) {
            return "number";
        }
        if (CharSequence.class.isAssignableFrom(type)) {
            return "string";
        }
//        if (Boolean.class.isAssignableFrom(type)) {
//            return "boolean";
//        }
        if (Collection.class.isAssignableFrom(type)) {
            return "array";
        }
        return "object";
    }
}
