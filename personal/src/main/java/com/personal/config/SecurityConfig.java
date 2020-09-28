package com.personal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.personal.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomUserDetailService customUserDetailService;
	
	@Override 
	public void configure(WebSecurity web) throws Exception { 
		web.ignoring() 
		.antMatchers("/resources/**") 
		.antMatchers("/css/**") 
		.antMatchers("/vendor/**") 
		.antMatchers("/js/**") 
		.antMatchers("/favicon*/**") 
		.antMatchers("/img/**");
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .authorizeRequests()
	        .antMatchers("/", "/index").permitAll()
	        .antMatchers("/admin").hasRole("ADMIN")
	        .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
        .and()
        	.logout()
        	.logoutUrl("/logout");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        //auth.userDetailsService(customUserDetailService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
