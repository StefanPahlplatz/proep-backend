package com.bfwg.rest;

import com.bfwg.Application;
import com.bfwg.model.Available;
import com.bfwg.model.Vehicle;
import com.bfwg.service.AvailableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import ognl.ObjectMethodAccessor;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Context;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailableControllerIntegrationTests {

    @MockBean
    private AvailableService availableService;

    @Autowired
    private MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void findByVehicle_ReturnsAvailableJson()
        throws Exception{

        // Arrange
        Available available = new Available();
        Vehicle vehicle = new Vehicle();
        Date startDate = new Date(2018, 12, 12);
        Date endDate = new Date(2018, 12, 15);

        vehicle.setId((long)5);
        vehicle.setMake("Tesla");

        available.setStartdate(startDate);
        available.setEnddate(endDate);
        available.setVehicle(vehicle);

        List<Available> availableList = new ArrayList();
        availableList.add(available);

        given(availableService.findByVehicle(any(Vehicle.class))).willReturn(availableList);

        // Assert
        mvc.perform(get("/api/available/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(vehicle))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicle.id",
                        is((vehicle.getId().intValue()))));
    }

    @Test
    public void findByVehicle_ReturnsNotFound_WhenVehicleIsNotAvailable()
            throws Exception{

        // Arrange
        Vehicle vehicle = new Vehicle();
        Date startDate = new Date(2018, 12, 12);
        Date endDate = new Date(2018, 12, 15);

        vehicle.setId((long)5);
        vehicle.setMake("Tesla");

        given(availableService.findByVehicle(any(Vehicle.class))).willReturn(null);

        // Assert
        mvc.perform(get("/api/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByStartdateBeforeAndEnddateAfter_ReturnsAvailableJson() throws Exception {

        //Arrange
        Available available = new Available();
        Vehicle vehicle = new Vehicle();
        Date startDateToLookUp = new Date(2018,12,01);
        Date endDateToLookUp = new Date(2019,01,15);

        Date startDate = new Date(2019,01,01);
        Date endDate = new Date(2019,01,14);

        vehicle.setId((long)1);
        vehicle.setMake("Tesla");

        available.setStartdate(startDate);
        available.setEnddate(endDate);
        available.setVehicle(vehicle);

        List<Available> availables = new ArrayList<>();
        availables.add(available);

        given(availableService.findByStartdateBeforeAndEnddateAfter(any(Date.class), any(Date.class)))
                .willReturn(availables);

        // Assert
        mvc.perform(get("/api/available/date")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(startDateToLookUp) +
                        mapper.writeValueAsString(endDateToLookUp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicle.id",
                        is(available.getVehicle().getId().intValue())));
    }

    @Test
    public void findByStartdateBeforeAndEnddateAfter_ReturnsNotFound_WhenVehicleIsNotFound()
            throws Exception {

        //Arrange
        given(availableService.findByStartdateBeforeAndEnddateAfter(
                any(Date.class), any(Date.class)))
                .willReturn(new ArrayList<>());

        // Assert
        String content = mvc.perform(get("/api/available/date")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("[]" ,content);
    }

    @Test
    public void findByStartdateBeforeAndEnddateAfterAndVehicle_ReturnsAvailable()
            throws Exception {

        // Arrange
        Available available = new Available();
        Vehicle vehicle = new Vehicle();
        Date startDate = new Date(2018, 12, 12);
        Date endDate = new Date(2018, 12, 15);

        vehicle.setId((long)5);
        vehicle.setMake("Tesla");

        available.setStartdate(startDate);
        available.setEnddate(endDate);
        available.setVehicle(vehicle);

        given(availableService.findByStartdateBeforeAndEnddateAfterAndVehicle(
                any(Date.class), any(Date.class), any(Vehicle.class)))
                .willReturn(available);

        // Assert
        mvc.perform(get("/api/available/dateandvehicle")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vehicle.id",
                        is((vehicle.getId().intValue()))));
    }
}
