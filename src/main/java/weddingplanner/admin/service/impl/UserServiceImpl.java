package weddingplanner.admin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weddingplanner.admin.dto.request.UserRequestDTO;
import weddingplanner.admin.repository.*;
import weddingplanner.admin.service.UserService;
import weddingplanner.application.component.*;
import weddingplanner.application.exception.ProcessException;
import weddingplanner.application.models.*;

import java.util.function.Supplier;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MD5Encoder md5Encoder;

    private CurrentUser currentUser;

    @Override
    public Long saveUser(UserRequestDTO userRequestDTO) throws Exception {

        User user = User.builder()
                .password(md5Encoder.getMD5Hash(userRequestDTO.getNewPassword()))
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

    @Override
    public UserRequestDTO getCurrentUser() throws Exception {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(userNotFound());

        return UserRequestDTO.builder()
                .newPassword(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void updateUser(UserRequestDTO userRequestDTO) throws Exception {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(userNotFound());

        if (userRequestDTO.isPasswordChangeDetected()) {
            validatePasswordEquality(userRequestDTO, user);
            user.setPassword(md5Encoder.getMD5Hash(userRequestDTO.getNewPassword()));
        }

        user.setEmail(userRequestDTO.getEmail());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());

        userRepository.save(user);
    }

    private void validatePasswordEquality(UserRequestDTO userRequestDTO, User user) throws Exception {
        if (!user.getPassword().equals(md5Encoder.getMD5Hash(userRequestDTO.getOldPassword()))) {
            throw new ProcessException("Old password doesn't match");
        }
    }

    private Supplier<? extends RuntimeException> userNotFound() {
        return () -> new RuntimeException("User not found");
    }

}