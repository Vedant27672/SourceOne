package com.SourceOne.controller;

import com.SourceOne.models.User;
import com.SourceOne.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ---------------- CREATE ---------------- */

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, @RequestParam(name = "createdByUsername", required = false) String createdByUsername) {
        User createdBy = null;
        if (createdByUsername != null) {
            createdBy = userService.findByUsername(createdByUsername);
        }
        User createdUser = userService.create(user, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /* ---------------- READ ---------------- */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam(name = "updatedByUsername", required = false) String updatedByUsername) {
        User updatedBy = null;
        if (updatedByUsername != null) {
            updatedBy = userService.findByUsername(updatedByUsername);
        }
        User updatedUser = userService.update(user, updatedBy);
        return ResponseEntity.ok(updatedUser);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable("username") String username) {
        User deletedUser = userService.deleteAndReturn(username);
        return ResponseEntity.ok(deletedUser);
    }

}
