package com.khagani.to_do_app.controller;
import com.khagani.to_do_app.dto.UserRequest;
import com.khagani.to_do_app.dto.UserResponse;
import com.khagani.to_do_app.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void addUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }
    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public List<UserResponse> getAllUsers(){
      return   userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public void updateUserById(@PathVariable Long id,@RequestBody UserRequest userRequest){
        userService.updateUserById(id,userRequest);
    }
    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
    }





}
