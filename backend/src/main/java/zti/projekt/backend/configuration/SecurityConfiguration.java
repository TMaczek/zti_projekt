package zti.projekt.backend.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import zti.projekt.backend.utils.RSAKeyProperties;

/**
 * Klasa konfiguracyjna zawierająca metody dot. bezpieczeństwa
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Klucze RSA - prywatny, publiczny
     */
    private final RSAKeyProperties keys;

    public SecurityConfiguration(RSAKeyProperties keys) {
        this.keys = keys;
    }

    /**
     * Metoda odpowiedzialna za enkoder hasla w projekcie
     * @return Enkoder hasla uzywany przy rejestracji i logowaniu
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Menadzer odpowiedzialny za autentykacje
     * @param detailsService instancja serwisu odpowiedzialnego za zarzadzanie uzytkownikami aplikacji
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    /**
     * Bean odpowiedzialy za filtrowanie i przepuszczanie żądań http
     * @param http obiekt na ktorym konfigurujemy przepuszczanie/blokowanie żądań http
     * @return zbudowany obiekt SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * Metoda odpowiedzialna za dekoder tokenu JWT
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    /**
     * Metoda odpowiedzialna za enkoder tokenu JWT
     * @return JwtEncoder
     */
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);

    }



}
