package zti.projekt.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zti.projekt.backend.repository.UserRepository;

/**
 * Serwis opowiedzialny za uzytkownikow rankingu
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * Pobieranie uzytkownika po jego nazwie
     * Metoda musi byc zaimplementowana przy implementacji UserDetailsService
     * @param username nazwa uzytkownika
     * @return Obiekt uzytkownika o tejj nazwie
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

    }
}
