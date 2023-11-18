package com.niksahn.laba5.manager;

import com.niksahn.laba5.Constants;
import com.niksahn.laba5.model.SessionDto;
import com.niksahn.laba5.model.UserDto;
import com.niksahn.laba5.repository.SessionRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;


public class SessionManager {
    private final SessionRepository sessionRepository;

    @TransactionScoped
    public Boolean sessionIsValid(Long session_id, Long user_id) {
        deleteOutdates(user_id);
        var sessionOpt = sessionRepository.findById(session_id);
        if (sessionOpt.isPresent()) {
            var session = sessionOpt.get();
            if (LocalDateTime.now().getLong(ChronoField.MINUTE_OF_DAY) - session.time < Constants.sessionLife) {
                updateSession(session_id);
                return true;
            }
        }
        return false;
    }

    // @Autowired
    public SessionManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Long getNewSession(Long user_id) {
        var session = sessionRepository.save(new SessionDto(LocalDateTime.now().getLong(ChronoField.MINUTE_OF_DAY), user_id));
        return session.id;
    }

    @TransactionScoped
    private void deleteOutdates(Long user) {
        sessionRepository.deleteOutdates(user, LocalDateTime.now().getLong(ChronoField.MINUTE_OF_DAY), Constants.sessionLife);
    }

    @TransactionScoped
    private void updateSession(Long session_id) {
        var sessionOpt = sessionRepository.findById(session_id);
        if (sessionOpt.isPresent()) {
            var session = sessionOpt.get();
            session.time = LocalDateTime.now().getLong(ChronoField.MINUTE_OF_DAY);
            sessionRepository.save(session);
        }
    }
}
