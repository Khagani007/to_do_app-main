package com.khagani.to_do_app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.khagani.to_do_app.dto.UserRequest;
import com.khagani.to_do_app.dto.UserResponse;
import com.khagani.to_do_app.entity.User;
import com.khagani.to_do_app.exceptions.UserNotFoundException;
import com.khagani.to_do_app.mapper.UserMapper;
import com.khagani.to_do_app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles({"test","test1"})
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    void createUser() {

        UserRequest request = new UserRequest();
        request.setName("u1");
        request.setPassword("n1");
        request.setEmail("aa@bb.cc");

        User user = User.builder()
                .name("u1")
                .password("n1")
                .email("aa@bb.cc")
                .build();

        when(userMapper.mapToUserEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(any(User.class));

        service.createUser(request);

        verify(userMapper).mapToUserEntity(request);
        verify(userRepository).save(user);
    }

    @Test
    void getUserByIdThrowException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));

        verify(userRepository).findById(id);
        verifyNoInteractions(userMapper);
    }


    @Test
    void getUserByIdSuccess() {
        Long id = 1L;

        User user = User.builder()
                .id(1L)
                .name("name")
                .password("password")
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("name")
                .password("password")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserResponse(user)).thenReturn(userResponse);

        UserResponse actual = service.getUserById(id);

        assertEquals(1L, actual.getId());
        assertEquals("name", actual.getName());
        assertEquals("password", actual.getPassword());

        verify(userRepository).findById(id);
        verify(userMapper).mapToUserResponse(user);
    }

    @Test
    void getAllUsers() {
        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(User.builder()
                .name("a1")
                .password("p1")
                .build());
        mockUserList.add(User.builder()
                .name("a2")
                .password("p2")
                .build());

        List<UserResponse> mockUserResponseList = new ArrayList<>();
        mockUserResponseList.add(UserResponse.builder()
                .name("a1")
                .password("p1")
                .build());
        mockUserResponseList.add(UserResponse.builder()
                .name("a2")
                .password("p2")
                .build());


        when(userRepository.findAll()).thenReturn(mockUserList);
        when(userMapper.mapToUserResponseList(mockUserList)).thenReturn(mockUserResponseList);

        List<UserResponse> actual = service.getAllUsers();

        assertEquals(mockUserResponseList, actual);
        assertEquals(2, actual.size());

        verify(userRepository).findAll();
        verify(userMapper).mapToUserResponseList(mockUserList);

    }

    @Test
    void updateUserByIdSuccess() {

        Long id = 1L;
        UserRequest userRequest = UserRequest.builder()
                .name("tst")
                .password("password")
                .build();

        User user = User.builder()
                .id(1L)
                .name("name")
                .password("password")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        service.updateUserById(id, userRequest);

        assertEquals("tst", user.getName());

        verify(userRepository).save(user);
    }

    @Test
    void updateUserByIdSuccessIf() {
        Long id = 1L;
        UserRequest userRequest = UserRequest.builder().build();

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        service.updateUserById(id, userRequest);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUserByIdThrowException() {
        Long id = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setName("New Name");

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.updateUserById(id, userRequest));

        verify(userRepository).findById(id);
    }

    @Test
    void deleteUserByIdSuccess() {

        Long id = 1L;

        User user = User.builder()
                .id(1L)
                .name("name")
                .password("password")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        service.deleteUser(id);


        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUserByIdThrowException() {

        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.deleteUser(id));

        verify(userRepository).findById(id);
    }

    @Test
    void loadUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername(username);

        assertEquals(user, userDetails);
    }

//    @Test
//    void loadUserByUsernameWhenUserNotFoundException() {
//        String username = "testuser";
//
//        when(userRepository.findByUsername(username)).thenReturn(null);
//
//        UserDetails userDetails = service.loadUserByUsername(username);
//
//        assertNull(userDetails);
//
//    }


}