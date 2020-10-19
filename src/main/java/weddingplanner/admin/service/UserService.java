package weddingplanner.admin.service;

import weddingplanner.admin.dto.request.UserRequestDTO;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/


public interface UserService {
    Long saveUser(UserRequestDTO userRequestDTO) throws Exception;

    UserRequestDTO getCurrentUser() throws Exception;

    void updateUser(UserRequestDTO userRequestDTO) throws Exception;
}
