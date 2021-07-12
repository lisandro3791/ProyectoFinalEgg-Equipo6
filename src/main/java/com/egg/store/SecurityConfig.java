/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Mecha
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure( HttpSecurity http) throws Exception {
        //revisar si el camino de los templates es  " /images/* "
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/images/**").permitAll()
                    .antMatchers("/**").permitAll() //.authenticated()
                //4631
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .and()
               .logout().permitAll()
               .and()
               .exceptionHandling().accessDeniedPage("/error-403");

    }
    
}
