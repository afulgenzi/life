package com.fulg.life.webmvc.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.Role;
import com.fulg.life.service.RoleService;
import com.fulg.life.service.UserService;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private static final String ANONYMOUS_USER = "anonymous";
	private static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		LOG.info("CustomUserDetailsService.loadUserByUsername({})", login);
		if (!ANONYMOUS_USER.equals(login)) {
			com.fulg.life.model.entities.User domainUser = userService.getUser(login);

			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			return new User(domainUser.getUsername(), domainUser.getPassword(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked,
					getGrantedAuthorities(roleService.getUserRoles(domainUser)));
		} else {
			throw new UsernameNotFoundException("You cannot login as " + ANONYMOUS_USER + " user.");
		}
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole().toUpperCase()));
		}
		return authorities;
	}

}