package com.lambdaschool.zoos.controller;

import com.lambdaschool.zoos.model.User;
import com.lambdaschool.zoos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/data")
public class UserController
{

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admin/animals/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllAnimals()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ZOODATA')")
    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllZoos()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ANIMALDATA')")
    @GetMapping(value = "/animals", produces = {"application/json"})
    public ResponseEntity<?> listAllAnimalData()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_MGR')")
    @GetMapping(value = "/actuator/animals/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllMgrZoos()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody User newuser) throws URISyntaxException
    {
        newuser =  userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User updateUser, @PathVariable long id)
    {
        userService.update(updateUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
