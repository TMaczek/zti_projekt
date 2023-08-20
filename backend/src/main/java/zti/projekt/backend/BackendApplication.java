package zti.projekt.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import zti.projekt.backend.model.ApplicationUser;
import zti.projekt.backend.model.Role;
import zti.projekt.backend.repository.RoleRepository;
import zti.projekt.backend.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * CommandLineRunner uzywany po to, by sie upenic że zawsze w bazie jest przynajmniej admin
	 * (bardzo przydatny przy pisaniu i testach, gdy baza jest resetowana)
	 * @param roleRepository repozytorium ról
	 * @param userRepository repozytorium uzytkowników
	 * @param passwordEncoder endkoder hasla
	 * @return
	 */
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent())  return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();

			roles.add(adminRole);
			ApplicationUser admin = new ApplicationUser(1L, "admin", passwordEncoder.encode("password"), roles);

			userRepository.save(admin);
		};
	}

	/**
	 * Bean odpowiedzialny za przepusczanie cors'ów
	 * @return
	 */

	@Bean
	public FilterRegistrationBean simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// Port frontendu
		config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
