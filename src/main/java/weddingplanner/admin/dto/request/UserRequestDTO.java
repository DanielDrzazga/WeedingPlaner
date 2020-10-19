package weddingplanner.admin.dto.request;

import lombok.*;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@Getter
@Setter
@Builder
public class UserRequestDTO {

    private String email;

    private String firstName;

    private String lastName;

    private String oldPassword;

    private String newPassword;

    private String newRepeatedPassword;

    private String roleName;


}
