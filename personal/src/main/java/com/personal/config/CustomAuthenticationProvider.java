package com.personal.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mysql.cj.exceptions.PasswordExpiredException;
import com.personal.domain.Authority;
import com.personal.domain.CustomUserDetails;
import com.personal.domain.User;
import com.personal.mapper.AuthorityMapper;
import com.personal.mapper.UserMapper;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	AuthorityMapper authorityMapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        
        User user = userMapper.selectUserByUserName(username);
        
        if (user == null) { throw new UsernameNotFoundException(username + "is not found."); }
        
        if(!encoder.matches(password, user.getPassword())) {
        	System.out.println("패스워드 틀렸다.");
        	throw new PasswordExpiredException();
        }
        
        CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setUsername(user.getUserName());
		customUserDetails.setPassword(user.getPassword());
		customUserDetails.setAuthorities(getAuthorities(user.getUserId()));
		customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);
        
        return new UsernamePasswordAuthenticationToken(username, password, customUserDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	
	public Collection<GrantedAuthority> getAuthorities(int userId) {
        List<Authority> authList = authorityMapper.selectAuthority(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }

}
