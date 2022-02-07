package com.example.workshopdemo.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.workshopdemo.security.ApplicationUserRole.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/secure/admin").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.GET, "/secure").hasAnyRole(ADMIN.name(), USER.name())
//                .antMatchers(HttpMethod.POST, "/secure").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers(HttpMethod.GET, "/unsecure").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name()) // ADMIN -> ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authorities(USER.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(
                admin
                ,user1
        );
    }
}
