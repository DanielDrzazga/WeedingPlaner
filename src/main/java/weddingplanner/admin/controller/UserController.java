package weddingplanner.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;
import weddingplanner.admin.dto.request.UserRequestDTO;
import weddingplanner.admin.service.UserService;

import javax.validation.Valid;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<Void> createNewUser (@Valid @RequestBody UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponentsBuilder) throws Exception {

        Long userId = userService.saveUser(userRequestDTO);

        UriComponents uriComponents = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserRequestDTO> getCurrentUser(){

        UserRequestDTO userRequestDTO = userService.getCurrentUser();
        return new ResponseEntity<>(userRequestDTO,HttpStatus.OK);
    }



}
