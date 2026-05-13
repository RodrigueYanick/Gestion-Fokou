package fokou.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fokou.entity.Utilisateur;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    @Getter
    private Long id;
    private String login;
    @Getter
    private String email;
    private String motDePasse;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(Utilisateur utilisateur) {
        String roleName = utilisateur.getRole().getNomRole(); // ex: ROLE_ADMIN
        return new UserPrincipal(
                utilisateur.getId(),
                utilisateur.getLogin(),
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                Collections.singletonList(new SimpleGrantedAuthority(roleName))
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}