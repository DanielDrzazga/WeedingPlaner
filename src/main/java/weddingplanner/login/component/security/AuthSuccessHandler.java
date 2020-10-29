package weddingplanner.login.component.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import weddingplanner.admin.repository.UserRepository;
import weddingplanner.application.component.CurrentUser;
import weddingplanner.application.models.User;
import weddingplanner.login.component.tool.AuthoritiesConverter;
import weddingplanner.login.dto.response.SecurityResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * Create by Daniel Drzazga on 29.10.2020
 **/

@Component
@AllArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private AuthoritiesConverter authoritiesConverter;
    private ObjectMapper objectMapper;

    private UserRepository userRepository;
    private CurrentUser currentUser;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        String responseBody = createResponse(authentication);

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        PrintWriter responseWriter = httpServletResponse.getWriter();
        responseWriter.println(responseBody);
        responseWriter.flush();
    }

    private String createResponse(Authentication authentication) throws JsonProcessingException {

        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            String userName = ((UserDetails) authentication.getPrincipal()).getUsername();

            User user = userRepository.findByEmail(userName);

            currentUser.setId(user.getId());

            UserDetails userDetails = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roles = authoritiesConverter.convert(authorities);

            SecurityResponseDTO securityResponseDTO = new SecurityResponseDTO();
            securityResponseDTO.setRoles(roles);
            securityResponseDTO.setEmail(userDetails.getUsername());

            return objectMapper.writeValueAsString(securityResponseDTO);
        }

        throw new IllegalArgumentException("Principal invalid");
    }
}
