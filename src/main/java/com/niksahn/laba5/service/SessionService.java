package com.niksahn.laba5.service;

import com.niksahn.laba5.Constants;
import com.niksahn.laba5.model.dto.SessionDto;
import com.niksahn.laba5.repository.SessionRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.temporal.ChronoField;


@Service
@EnableScheduling
public class SessionService {
    private final SessionRepository sessionRepository;

    @Transactional
    public Boolean sessionIsValid(Long session_id) {
        var sessionOpt = sessionRepository.findById(session_id);
        if (sessionOpt.isPresent()) {
            updateSession(session_id);
            return true;
        }
        return false;
    }

    public Long getUserIdFromSession(Long session_id) {
        if (sessionIsValid(session_id)) {
            var sessionOpt = sessionRepository.findById(session_id);
            if (sessionOpt.isPresent()) {
                return sessionOpt.get().getUserId();
            }
        }
        return null;
    }

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Long getSession(Long user_id) {
        var session = sessionRepository.findByUserId(user_id);
        if (session == null) session = sessionRepository.save(new SessionDto(getTime(), user_id));
        return session.getId();
    }

    @Scheduled(fixedRate = 2000)
    @Transactional
    public void deleteOutdates() {
        sessionRepository.deleteOutdates(getTime(), Constants.sessionLife);
    }

    public void deleteSession(Long session_id) {
        sessionRepository.deleteById(session_id);
    }

    @Transactional
    private void updateSession(Long session_id) {
        var sessionOpt = sessionRepository.findById(session_id);
        if (sessionOpt.isPresent()) {
            var session = sessionOpt.get();
            session.setTime(getTime());
            sessionRepository.save(session);
        }
    }


    private Long getTime() {
        return Clock.systemUTC().instant().getLong(ChronoField.INSTANT_SECONDS);
    }
}
