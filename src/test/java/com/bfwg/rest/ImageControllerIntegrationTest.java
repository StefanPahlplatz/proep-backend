package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.Image;
import com.bfwg.model.Review;
import com.bfwg.model.Vehicle;
import com.bfwg.service.ImageService;
import com.bfwg.service.ReservationService;
import com.bfwg.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@WebAppConfiguration
public class ImageControllerIntegrationTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void shouldSaveUploadedFileWithCorrectName() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);

        Image image1 = new Image();
        image1.setVehicle(vehicle);
        image1.setId(2L);

        given(vehicleService.findById(vehicle.getId())).willReturn(vehicle);

        when(imageService.save(any(Image.class))).thenReturn(image1);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        this.mvc.perform(fileUpload("/api/images/"+vehicle.getId()).file(multipartFile))
                .andExpect(status().isCreated());

        assertThat(image1.getPath()).contains("image2");
    }

}
