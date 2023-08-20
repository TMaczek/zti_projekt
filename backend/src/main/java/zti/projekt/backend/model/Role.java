package zti.projekt.backend.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * Klasa reprezentujaca role jakie moga miec uzytkownicy
 * (przy obecnej wersji aplikacji nie są one wykorzystywane poza zaspokojeniem potrzeb Spring Security)
 */
@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="role_id")
    private Long roleId;

    private String authority;

    public Role(){
        super();
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Long roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
