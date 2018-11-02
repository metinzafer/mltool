package com.gnsmind.springBoot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/member/register").permitAll()
            .antMatchers("/member/confirm").permitAll()
        	.antMatchers("/member/getMemberIP").permitAll()
        	.antMatchers("/member/member_search").permitAll()
        	.antMatchers("/member/member_delete").permitAll()
        	.antMatchers("/member/member_update").permitAll();
    }
}
