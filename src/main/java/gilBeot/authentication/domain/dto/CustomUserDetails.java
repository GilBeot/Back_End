package gilBeot.authentication.domain.dto;

import gilBeot.authentication.domain.MemberDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails { //DTO

    private final MemberDomain memberDomain;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //ROLE 값 반환

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDomain.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return memberDomain.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDomain.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
