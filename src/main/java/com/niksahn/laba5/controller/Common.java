package com.niksahn.laba5.controller;

import com.niksahn.laba5.manager.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class Common {
    public static ResponseEntity<String> checkAuth(Long session_id, SessionService sessionService) {
        if (!sessionService.sessionIsValid(session_id)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        return null;
    }
}
