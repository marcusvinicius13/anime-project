package br.com.academy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The user`s name, cannot be empty")
    private String name;

    private String username;
    private String password;
    private String authorities; // Ex: ROLE_ADMIN, ROLE_USER

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * Este método diz se a conta do usuário está ou não Expirada.
     * Esse dado pode vir do banco de dados, mas não iremos buscar do banco.
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * Este método diz se a conta do usuário está ou não Bloqueada.
     * Esse dado pode vir do banco de dados, mas não iremos buscar do banco.
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *
     * Este método diz se a credencial do usuário está ou não Expirada.
     * Esse dado pode vir do banco de dados, mas não iremos buscar do banco.
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * Este método diz se o usuário está ou não habilitado.
     * Esse dado pode vir do banco de dados, mas não iremos buscar do banco.
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
