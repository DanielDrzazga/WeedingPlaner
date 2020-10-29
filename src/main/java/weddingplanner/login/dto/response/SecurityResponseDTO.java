package weddingplanner.login.dto.response;

import lombok.*;

import java.util.List;

/**
 * Create by Daniel Drzazga on 29.10.2020
 **/

@Getter
@Setter
@NoArgsConstructor
public class SecurityResponseDTO {

    private String email;
    private List<String> roles;
}
