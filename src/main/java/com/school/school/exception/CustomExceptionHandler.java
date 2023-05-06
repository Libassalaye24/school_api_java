package com.school.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Gestion d'une exception spécifique
    @ResponseBody
    @ExceptionHandler(InscriptionException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorDTO handleYourException(InscriptionException ex, WebRequest request) {
        // Créez un objet ErrorResponse pour encapsuler les détails de l'erreur
        ErrorDTO errorResponse = new ErrorDTO(HttpStatus.OK.getReasonPhrase(), ex.getMessage());

        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
        return errorResponse;
    }

    // Gestion d'autres exceptions...

    // Gestion des exceptions par défaut
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleDefaultException(Exception ex, WebRequest request) {
        // Créez un objet ErrorResponse pour encapsuler les détails de l'erreur
        ErrorDTO errorResponse = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());

        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
        return errorResponse;
    }
}

