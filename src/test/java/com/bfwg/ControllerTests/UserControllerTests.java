package com.bfwg.ControllerTests;


import com.bfwg.model.User;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.deser.*;
import com.bfwg.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests {

    @Autowired
    private UserController userController;

    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext mockApplicationContext;

    protected void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(mockApplicationContext).build();
    }

    @Autowired
    private TestEntityManager testEntityManager;

    // to be finished
}
