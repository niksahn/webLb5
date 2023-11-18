package com.niksahn.laba5.di;

import com.niksahn.laba5.manager.SessionManager;
import com.niksahn.laba5.repository.SessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Di {
    @Bean
    public SessionManager sessionManager(SessionRepository sessionRepository) {
        return new SessionManager(sessionRepository);
    }
}
