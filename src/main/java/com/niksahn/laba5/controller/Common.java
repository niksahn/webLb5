package com.niksahn.laba5.controller;

import com.niksahn.laba5.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class Common {
    public static ResponseEntity<String> checkAuth(Long session_id, SessionService sessionService) {
        if (!sessionService.sessionIsValid(session_id)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        return null;
    }

    public static ResponseEntity<String> validate(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder result = new StringBuilder();
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError i : errors) {
                String fieldErrors = ((FieldError) i).getField();
                result.append(fieldErrors).append(" - ").append(i.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return null;
    }
}
