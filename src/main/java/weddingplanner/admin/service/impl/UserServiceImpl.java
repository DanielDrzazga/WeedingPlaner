package weddingplanner.admin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weddingplanner.admin.dto.request.UserRequestDTO;
import weddingplanner.admin.repository.*;
import weddingplanner.admin.service.UserService;
import weddingplanner.application.models.*;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Long saveUser(UserRequestDTO userRequestDTO) throws Exception {

        User user = User.builder()
                //TODO password encryption
                .password(userRequestDTO.getNewPassword())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .active(true)
                .build();

        Role role = roleRepository.findByRoleName(RoleEnum.ROLE_USER.getValue());
        user.getRoles().add(role);

        userRepository.save(user);

        return user.getId();
    }


}