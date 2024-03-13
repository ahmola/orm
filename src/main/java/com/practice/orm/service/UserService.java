package com.practice.orm.service;

import com.practice.orm.dto.user.CreateUserDTO;
import com.practice.orm.dto.user.UpdateUserDTO;
import com.practice.orm.exception.UserNotFoundException;
import com.practice.orm.model.User;
import com.practice.orm.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User create(CreateUserDTO userDTO) {
        return userRepository.save(new User(userDTO));
    }


    public boolean update(Long id, UpdateUserDTO userDTO) {
        if(!userRepository.existsById(id)){
            User user = User.builder()
                    .username(userDTO.getUsername())
                    .email(userDTO.getEmail())
                    .build();
            userRepository.save(user);
            return true;
        }

        User user = userRepository.findById(id).get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        for (Long friend_id : userDTO.getFriends_id()) {
            User friend = userRepository.findById(friend_id).get();
            if(user.getFriends().stream()
                    .noneMatch(f -> f.getId() == friend.getId())){
                user.getFriends().add(friend);
            }
        }
        userRepository.save(user);

        return false;
    }

    public void deleteById(Long id) {
        if(!userRepository.existsById(id))
            throw new UserNotFoundException(id.toString());
        userRepository.deleteById(id);
    }
}
