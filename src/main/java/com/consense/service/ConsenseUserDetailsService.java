package com.consense.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ConsenseUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//
//		User user = userRepository.findUserByName(name);
//		if (user == null) {
//			throw new UsernameNotFoundException("Username " + name + " not found");
//		}
//		
//		return new User(name, "password", getGrantedAuthorities(name));
//	}
//	
//	private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
//		Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		if (username.equals("John")) {
////			authorities = asList(() -> "ROLE_ADMIN", () -> "ROLE_BASIC");
//			authorities.add("ROLE_ADMIN");
//		} else {
////			authorities = asList(() -> "ROLE_BASIC");
//		}
//		return authorities;
//		}
//	}

}
