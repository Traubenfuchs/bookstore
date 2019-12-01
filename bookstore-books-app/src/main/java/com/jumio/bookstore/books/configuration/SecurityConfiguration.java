package com.jumio.bookstore.books.configuration;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;

/**
 * Configures which routes require which authorities and enables oauth2 via JWT.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .csrf().disable()
      .authorizeRequests(authorizeRequests ->
        authorizeRequests
          .antMatchers(HttpMethod.GET, "/api/**")
          .hasAnyAuthority("SCOPE_read:books")
      )
      .oauth2ResourceServer().jwt();
  }
}
