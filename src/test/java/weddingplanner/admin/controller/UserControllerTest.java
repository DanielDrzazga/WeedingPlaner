package weddingplanner.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import weddingplanner.admin.dto.request.UserRequestDTO;
import weddingplanner.admin.service.UserService;
import weddingplanner.application.models.RoleEnum;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @Captor
    private ArgumentCaptor<UserRequestDTO> argumentCaptor;

    @Test
    public void postingNewUserShouldCreateANewUserInTheDatabase() throws Exception {

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .newPassword("password1")
                .firstName("Jan")
                .lastName("Kowalski")
                .email("JanKowalski@polska.pl")
                .roleName(RoleEnum.ROLE_USER.getValue())
                .build();
        Long userId = 1L;

        when(userService.saveUser(argumentCaptor.capture())).thenReturn(userId);

        this.mockMvc
                .perform(post("/admin/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/users/" + userId));

        assertThat(argumentCaptor.getValue().getEmail(), is("JanKowalski@polska.pl"));
        assertThat(argumentCaptor.getValue().getFirstName(), is("Jan"));
        assertThat(argumentCaptor.getValue().getLastName(), is("Kowalski"));
        assertThat(argumentCaptor.getValue().getNewPassword(), is("password1"));
        assertThat(argumentCaptor.getValue().getRoleName(), is("USER"));

    }

    @Test
    public void getCurrentUserShouldReturnCurrentUser() throws Exception {

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .newPassword("password1")
                .firstName("Jan")
                .lastName("Kowalski")
                .email("JanKowalski@polska.pl")
                .roleName(RoleEnum.ROLE_USER.getValue())
                .build();

        when(userService.getCurrentUser()).thenReturn(userRequestDTO);

        this.mockMvc
                .perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("firstName", is("Jan")))
                .andExpect(jsonPath("lastName", is("Kowalski")))
                .andExpect(jsonPath("email", is("JanKowalski@polska.pl")));
    }

    @Test
    public void updateCurrentUserShouldUpdateUser() throws Exception {

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .newPassword("password1")
                .firstName("Jan")
                .lastName("Kowalski")
                .email("JanKowalski@polska.pl")
                .roleName(RoleEnum.ROLE_USER.getValue())
                .build();

        doNothing().when(userService).updateUser(userRequestDTO);

        this.mockMvc
                .perform(put("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk());

    }

}