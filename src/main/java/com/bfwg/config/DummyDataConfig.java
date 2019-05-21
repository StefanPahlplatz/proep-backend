package com.bfwg.config;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;
import com.bfwg.repository.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataConfig {

    @Bean
    public CommandLineRunner demoData(AuthorityRepository authorityRepository){

        return args -> {
            authorityRepository.save(new Authority(1L, UserRoleName.ROLE_USER));
            authorityRepository.save(new Authority(2L, UserRoleName.ROLE_ADMIN));
        };
    }

}
