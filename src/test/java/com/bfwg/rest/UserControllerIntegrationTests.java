package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.User;
import com.bfwg.model.UserRequest;
import com.bfwg.security.auth.IAuthenticationFacade;
import com.bfwg.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebAppConfiguration
public class UserControllerIntegrationTests extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Test
    public void FindAll_ReturnsAllUsers()
    throws Exception{

        // Arrange
        User firstUser = new User();
        firstUser.setFirstname("John");
        firstUser.setLastname("Doe");
        firstUser.setId((long)123);
        firstUser.setEmail("user@email.com");

        User secondUser = new User();
        secondUser.setFirstname("Sam");
        secondUser.setLastname("Smith");
        firstUser.setId((long)123);
        secondUser.setEmail("samSmith@gmail.com");

        List<User> allUsers = new ArrayList<>();

        allUsers.add(firstUser);
        allUsers.add(secondUser);

        given(userService.findAll()).willReturn(allUsers);

        // Assert
        mvc.perform(get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findAll_ReturnsNull_WhenNoUsersExist() throws Exception {

        //Arrange
        given(userService.findAll()).willReturn(null);

        mvc.perform(get("api/user/all")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ReturnsFoundUser()
        throws Exception{

        // Arrange
        User foundUser = new User();
        foundUser.setFirstname("John");
        foundUser.setLastname("Doe");
        foundUser.setId((long)123);
        foundUser.setEmail("user@email.com");

        given(userService.findById(foundUser.getId())).willReturn(foundUser);

        // Assert
        mvc.perform(get("/api/user/" + foundUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(foundUser.getId().intValue())));
    }

    @Test
    public void findById_ReturnsNotFound_WhenUserWithIdDoesNotExist()
        throws Exception{

        // Arrange
        User user = new User();
        user.setId((long)1);

        given(userService.findById(any(Long.class))).willReturn(null);

        // Assert
        mvc.perform(get("/api/user/"+user.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveUser_ReturnsJson()
        throws Exception{

        //Arrange
        User user = new User();
        UserRequest request = new UserRequest();
        ObjectMapper mapper = new ObjectMapper();

        user.setId(new Long(1));
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("John");
        user.setLastname("Doe");

        request.setFirstname(user.getFirstname());
        request.setLastname(user.getLastname());
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());

        given(userService.findByUsername(user.getUsername())).willReturn(null);
        given(userService.save(any(UserRequest.class))).willReturn(user);

        // Assert
         mvc.perform(post("/api/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void SaveUser_ReturnsError_WhenUserAlreadyExists() throws Exception {

        //Arrange
        User user = new User();
        UserRequest request = new UserRequest();
        ObjectMapper mapper = new ObjectMapper();

        user.setId(new Long(1));
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("John");
        user.setLastname("Doe");

        request.setFirstname(user.getFirstname());
        request.setLastname(user.getLastname());
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());

        given(userService.findByUsername(user.getUsername())).willReturn(user);

        // Assert
        mvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isConflict());
    }
}