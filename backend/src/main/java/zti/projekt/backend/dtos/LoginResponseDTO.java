package zti.projekt.backend.dtos;

import zti.projekt.backend.model.ApplicationUser;

/**
 * DTO odpowiedzi na zadanie logowania - zwraca username zalogowanego uzytkownika oraz wygenerowany token JWT
 */
public class LoginResponseDTO {
    private ApplicationUser user;

    private String jwt;

    public LoginResponseDTO() {
        super();
    }

    public LoginResponseDTO(ApplicationUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
