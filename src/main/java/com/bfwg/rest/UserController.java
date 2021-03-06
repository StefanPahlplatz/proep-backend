package com.bfwg.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bfwg.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.User;
import com.bfwg.model.UserRequest;
import com.bfwg.service.UserService;

import javax.mail.MessagingException;

/**
 * Created by fan.jin on 2016-10-15.
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private EmailService emailService;


  @RequestMapping(method = GET, value = "/user/{userId}")
  public ResponseEntity<User> loadById(@PathVariable Long userId) {

    User foundUser = this.userService.findById(userId);
    if (foundUser == null){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    else{
      return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
  }

  @RequestMapping(method = GET, value = "/user/all")
  public List<User> loadAll() {
    return this.userService.findAll();
  }

  @RequestMapping(method = GET, value = "/user/reset-credentials")
  public ResponseEntity<Map> resetCredentials() {
    this.userService.resetCredentials();
    Map<String, String> result = new HashMap<>();
    result.put("result", "success");
    return ResponseEntity.accepted().body(result);
  }

  @RequestMapping(method =POST , value = "/signup")
  public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest,
      UriComponentsBuilder ucBuilder) {

    User existUser = this.userService.findByUsername(userRequest.getUsername());
    if (existUser != null) {
      throw new ResourceConflictException(userRequest.getId(), "Username already exists");
    }
    User user = this.userService.save(userRequest);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
    if(user.getEmail()!= null){
      try {
        this.emailService.completeRegistration(user);
      }
      catch (MessagingException e) {
        e.printStackTrace();
      }
    }
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @RequestMapping(method = PUT, value = "/user/{userId}")
  public ResponseEntity<?> updateUser(@PathVariable Long userId,@RequestBody User userRequest){

    User user = this.userService.findById(userRequest.getId());

    if(user == null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if(userId != userRequest.getId()){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    this.userService.save(userRequest);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /*
   * We are not using userService.findByUsername here(we could), so it is good that we are making
   * sure that the user has role "ROLE_USER" to access this endpoint.
   */
  @RequestMapping("/whoami")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User user() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
