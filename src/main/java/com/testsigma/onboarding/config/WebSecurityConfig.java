package com.testsigma.onboarding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity @Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","USER")
                    .antMatchers(HttpMethod.GET,"/room/reservation/v1/all","/room/reservation/v1").permitAll()
                    .antMatchers(HttpMethod.GET,"/room/reservation/v1/basicauth").hasAnyRole("ADMIN","USER")
                    .antMatchers(HttpMethod.GET,"/room/reservation/v1/{\\d+}").hasAnyRole("ADMIN","USER")
                    .antMatchers(HttpMethod.OPTIONS,"/**").fullyAuthenticated()
                    .anyRequest().fullyAuthenticated()
                .and()
                    .httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("renju").password("renju").roles("USER");
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
