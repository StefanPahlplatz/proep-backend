package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.security.auth.IAuthenticationFacade;
import com.bfwg.service.VehicleService;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebAppConfiguration
public class VehicleControllerIntegrationTests extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Test
    public void FindAll_ReturnsAllVehicles()
            throws Exception{

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setId((long)1);
        vehicle.setMake("Opel");
        vehicle.setModel("FastCar");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId((long)2);
        vehicle2.setMake("Tesla");
        vehicle2.setModel("ElectricCar");

        List<Vehicle> vehiclesToReturn = new ArrayList<>();
        vehiclesToReturn.add(vehicle);
        vehiclesToReturn.add(vehicle2);
        given(vehicleService.findAll()).willReturn(vehiclesToReturn);

        // Act Assert
        mvc.perform(get("/api/vehicles/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void FindAll_ReturnsNull_WhenNoVehiclesAreFound()
            throws Exception{

        // Arrange
        given(vehicleService.findAll()).willReturn(null);

        // Act Assert
       String response = mvc.perform(get("/api/vehicles/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.isTrue(response.isEmpty());
    }

    @Test
    public void FindById_ReturnsVehicleWhithCorrectId_WhenVehicleExists() throws Exception {

        //Arrange

        Vehicle vehicleToReturn = new Vehicle();
        vehicleToReturn.setId((long)1);

        given(vehicleService.findById(vehicleToReturn.getId())).willReturn(vehicleToReturn);

        //Act Arrange
        mvc.perform(get("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id",
                        is(vehicleToReturn.getId().intValue())));
    }
}
