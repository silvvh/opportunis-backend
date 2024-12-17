package com.bcc.projeto.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CPFAlreadyInUseException.class)
    public ResponseEntity<String> handleCPFAlreadyInUse(CPFAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CNPJAlreadyInUseException.class)
    public ResponseEntity<String> handleCnpjException(CNPJAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TelephoneAlreadyInUseException.class)
    public ResponseEntity<String> handleTelephoneAlreadyInUse(TelephoneAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        if (rootMessage.contains("email")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já está em uso.");
        } else if (rootMessage.contains("cnpj")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ já está em uso.");
        } else if (rootMessage.contains("telephone")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Telefone já está em uso.");
        } else if (rootMessage.contains("cpf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já está em uso.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro de integridade: dados duplicados.");
    }
}
