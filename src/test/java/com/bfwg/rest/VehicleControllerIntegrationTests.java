package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.Location;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.security.auth.IAuthenticationFacade;
import com.bfwg.service.GeocodingService;
import com.bfwg.service.VehicleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private GeocodingService geoService;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    ObjectMapper mapper = new ObjectMapper();

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
    public void FindById_ReturnsVehicleWithCorrectId_WhenVehicleExists() throws Exception {

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

    @Test
    public void FindById_ReturnsNotFound_WhenVehicleWithIdDoesNotExist() throws Exception {

        //Arrange
        Vehicle vehicleToReturn = new Vehicle();
        vehicleToReturn.setId((long)1);

        given(vehicleService.findById(vehicleToReturn.getId())).willReturn(vehicleToReturn);

        //Act Arrange
        mvc.perform(get("/api/vehicles/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByOwner_ReturnsVehicle_WhenOwnerHasVehicle() throws Exception {

        // Arrange
        User owner = new User();
        owner.setId((long)1);
        owner.setFirstname("Jack");
        owner.setLastname("Smith");

        Vehicle ownedVehicle = new Vehicle();
        ownedVehicle.setId((long)1);
        ownedVehicle.setMake("Tesla");

        Set<Vehicle> ownedVehicles = new HashSet<>();
        ownedVehicles.add(ownedVehicle);
        owner.setVehicles(ownedVehicles);

        List<Vehicle> listToReturn = new ArrayList<>();
        listToReturn.add(ownedVehicle);

        given(vehicleService.findByUser(Matchers.any(User.class))).willReturn(listToReturn);

        // Act, Assert
        mvc.perform(get("/api/vehicles/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findByOwner_ReturnsEmptyResult_WhenOwnerDoesNotHaveAVehicle()
            throws Exception {

        // Arrange
        User owner = new User();
        owner.setId((long)1);
        owner.setFirstname("Jack");
        owner.setLastname("Smith");

        given(vehicleService.findByUser(Matchers.any(User.class))).willReturn(null);

        // Act, Assert
        String responseContent = mvc.perform(get("/api/vehicles/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(owner)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.isTrue(responseContent.isEmpty());
    }

    @Test
    public void findByType_ReturnsVehiclesOfSpecificType() throws Exception {

        // Arrange
        Vehicle firstVehicle = new Vehicle();
        Vehicle secondVehicle = new Vehicle();

        firstVehicle.setId((long)1);
        firstVehicle.setMake("Jeep");
        firstVehicle.setModel("Fast");
        firstVehicle.setType("SUV");

        secondVehicle.setId((long)2);
        secondVehicle.setMake("Nissan");
        secondVehicle.setModel("SuperFast");
        secondVehicle.setType("SUV");

        List<Vehicle> SuvVehicles = new ArrayList<>();

        SuvVehicles.add(firstVehicle);
        SuvVehicles.add(secondVehicle);

        given(vehicleService.findByType("SUV")).willReturn(SuvVehicles);

        // Act, Assert
        mvc.perform(get("/api/vehicles/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content("SUV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findByType_ReturnsEmptyResponse_WhenNoVehiclesOfTypeExist() throws Exception {

        // Arrange
        given(vehicleService.findByType("SUV")).willReturn(null);

        // Act, Assert
        String responseContent = mvc.perform(get("/api/vehicles/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content("SUV"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.isTrue(responseContent.isEmpty());
    }

    @Test
    public void findByCity_ReturnsVehiclesThatAreInSpecifiedLocation() throws Exception {

        // Arrange
        Vehicle firstVehicle = new Vehicle();
        Vehicle secondVehicle = new Vehicle();
        Location cityCoordinates = new Location();

        cityCoordinates.setLatitude(10.0);
        cityCoordinates.setLongitude(20.0);

        firstVehicle.setId((long)1);
        firstVehicle.setMake("Jeep");
        firstVehicle.setModel("Fast");

        secondVehicle.setId((long)2);
        secondVehicle.setMake("Nissan");
        secondVehicle.setModel("SuperFast");

        List<Vehicle> SuvVehicles = new ArrayList<>();

        SuvVehicles.add(firstVehicle);
        SuvVehicles.add(secondVehicle);

        given(geoService.findPointByCity("NY")).willReturn(cityCoordinates);
        given(vehicleService.findByLocation(cityCoordinates.getLongitude(), cityCoordinates.getLatitude(), 15.0))
                .willReturn(SuvVehicles);

        //Act Assert
        mvc.perform(get("/api/vehicles/city/NY")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findByCity_ReturnsEmptyResponseWhenThereIsNoVehiclesInCity()
            throws Exception {

        // Arrange
        Location cityCoordinates = new Location();
        cityCoordinates.setLatitude(10.0);
        cityCoordinates.setLongitude(20.0);

        given(geoService.findPointByCity("NY")).willReturn(cityCoordinates);
        given(vehicleService.findByLocation(cityCoordinates.getLongitude(), cityCoordinates.getLatitude(), 15.0))
                .willReturn(null);

        //Act Assert
        String responseContent = mvc.perform(get("/api/vehicles/city/NY")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.isTrue(responseContent.isEmpty());
    }

    @Test
    public void deleteVehicle_ReturnsNoContent_WhenVehicleIsDeleted() throws Exception {

        // Arrange
        User applicationUser = mock(User.class);
        applicationUser.setId((long)2);
        Vehicle vehicleToDelete = new Vehicle();
        vehicleToDelete.setId((long)1);
        vehicleToDelete.setMake("Tesla");
        vehicleToDelete.setUser(applicationUser);

        //Mock security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .willReturn(applicationUser);

        given(vehicleService.findById(vehicleToDelete.getId())).willReturn(vehicleToDelete);

        // Act, Assert
        mvc.perform(delete("/api/vehicles/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("2"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteVehicle_ReturnsNotFound_WhenVehicleWithIdDoesNotExist() throws Exception {

        // Arrange
        User goodUser = new User();
        goodUser.setId((long)2);
        Vehicle vehicleToDelete = new Vehicle();
        vehicleToDelete.setId((long)1);
        vehicleToDelete.setMake("Tesla");
        vehicleToDelete.setUser(goodUser);

        given(vehicleService.findById(vehicleToDelete.getId())).willReturn(null);

        // Act, Assert
        mvc.perform(delete("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteVehicle_ReturnsUnauthorized_WhenUserIsNotAuthorized()
            throws Exception {

        // Arrange
        User applicationUser = mock(User.class);
        applicationUser.setId((long)2);
        Vehicle vehicleToDelete = new Vehicle();
        vehicleToDelete.setId((long)1);
        vehicleToDelete.setMake("Tesla");
        vehicleToDelete.setUser(applicationUser);

        //Mock security Context
        User badUser = new User();
        badUser.setId((long)1);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .willReturn(badUser);

        given(vehicleService.findById(vehicleToDelete.getId())).willReturn(vehicleToDelete);

        // Act, Assert
        mvc.perform(delete("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("2"))
                .andExpect(status().isUnauthorized());
    }
}
