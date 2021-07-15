package br.com.demo.ia.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.demo.ia.repository.UserRepository;

@Service
class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(
			final UserRepository userRepository
	) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User [ %s ] not found.", username)));
	}

	

}
