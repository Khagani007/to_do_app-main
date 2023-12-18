package com.khagani.to_do_app.service.impl;


import com.khagani.to_do_app.dto.UserRequest;
import com.khagani.to_do_app.dto.UserResponse;
import com.khagani.to_do_app.entity.User;
import com.khagani.to_do_app.exceptions.UserNotFoundException;
import com.khagani.to_do_app.mapper.UserMapper;
import com.khagani.to_do_app.repository.UserRepository;
import com.khagani.to_do_app.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;



    @Override
    public void createUser(UserRequest userRequest) {
        log.info("createUser().start");
        User user = userMapper.mapToUserEntity(userRequest);

        userRepository.save(user);

        log.info("createdToDo().end");
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("getAllUsers().start");
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = userMapper.mapToUserResponseList(users);
        log.info("getAllUsers().end");
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("getUserById().start id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not founded by id " + id));
        log.info("getUserById().end id {} ", id);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public void updateUserById(Long id, UserRequest userRequest) {
        log.info("updateUserById().start id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not founded by id " + id));
        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
            userRepository.save(user);
        }

        log.info("updateUserById().end id {}", id);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleteUserById().start id {}", id);
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not founded by id " + id));
        userRepository.deleteById(id);
        log.info("deleteUserById().end id {}", id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void increaseAttemptCount(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    int attemptAccount = user.getAttemptCount();
                    if (attemptAccount > 2) {
                        user.setAccountNonLocked(false);

                    }
                    user.setAttemptCount(user.getAttemptCount() + 1);
                    userRepository.save(user);
                });
    }

    public void resetAttempts(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.setAttemptCount(0);
                    userRepository.save(user);
                });
    }
}


















