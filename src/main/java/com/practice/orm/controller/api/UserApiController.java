package com.practice.orm.controller.api;

import com.practice.orm.dto.user.CreateUserDTO;
import com.practice.orm.dto.user.UpdateUserDTO;
import com.practice.orm.model.User;
import com.practice.orm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Optional<User>> findUserById(@RequestParam(name = "id") Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping]
    public ResponseEntity<Optional<User>> findByUsername(@RequestParam(name = "username") String username){
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO userDTO){
        return new ResponseEntity<>("Save user " +
                userService.create(userDTO).getUsername() ,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateUserById(
            @RequestParam Long id,
            @RequestBody UpdateUserDTO userDTO){
        boolean isCreated = userService.update(id, userDTO);

        HttpStatus status;
        if (isCreated)
            status = HttpStatus.CREATED;
        else
            status = HttpStatus.OK;

        return new ResponseEntity<>("Update User " + userDTO.getUsername(), status);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestParam Long id){
        userService.deleteById(id);
        return new ResponseEntity<>("Delete user " + id, HttpStatus.OK);
    }
}
