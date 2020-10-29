package weddingplanner.login.component.tool;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Create by Daniel Drzazga on 29.10.2020
 **/

@Component
public class AuthoritiesConverter {

    public List<String> convert(Collection<? extends GrantedAuthority> authorities) {

        return authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
