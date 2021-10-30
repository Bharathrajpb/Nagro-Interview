package com.c4l.accountstatement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class AccountStatementConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN="ADMIN";
	private static final String USER="USER";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles(USER)
                .and()
                .withUser("admin").password("{noop}admin").roles(USER,ADMIN);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http         
           .authorizeRequests()
            .antMatchers("/resources/**", "/webjars/**","/assets/**").permitAll()
               .antMatchers("/").permitAll()
               .antMatchers(HttpMethod.GET, "/statement/statement-report/**").hasRole(USER)
               .antMatchers(HttpMethod.GET, "/statement/statement-report-amountrange/**").hasRole(ADMIN)
               .antMatchers("/get-statement").hasRole(USER)
               .antMatchers("/get-statement-daterange").hasRole(ADMIN)
               .antMatchers("/get-statement-amountrange").hasRole(ADMIN)
               .anyRequest().authenticated()
               .and()
           .formLogin()
               .loginPage("/login")
               .defaultSuccessUrl("/dashboard")
               .failureUrl("/login?error")
               .permitAll()
               .and()
               .logout()
                   .invalidateHttpSession(true)
                   .clearAuthentication(true)
                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                   .logoutSuccessUrl("/login?logout")
                       .permitAll()
                   .and()
    	.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        	.and()
    	.sessionManagement().maximumSessions(1)
    	.and()
    	.invalidSessionUrl("/?sessionexpired=true");
    }

}