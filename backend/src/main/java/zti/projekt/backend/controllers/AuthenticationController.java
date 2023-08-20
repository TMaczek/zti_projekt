package zti.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zti.projekt.backend.dtos.LoginResponseDTO;
import zti.projekt.backend.dtos.RegistrationDTO;
import zti.projekt.backend.model.ApplicationUser;
import zti.projekt.backend.services.AuthenticationService;

/**
 * Kontroler żądań dot. autentykacji
 */
@RestController
@RequestMapping("/auth") 
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Metoda odpowiedzialna za api rejestracji uzytkownika.
     * @param body Cialo zadania zawierajace username oraz password
     * @return Obiekt ApplicationUser zarejestrowanego uzytkownika.
     */
    @CrossOrigin("**")
    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    /**
     * Metoda odpowiedzialna za api logowania uzytkownika.
     * @param body Cialo zadania zawierajace username oraz password.
     * @return Obiekt zawierajacy dane zalogowania.
     */
    @CrossOrigin("**")
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

}
