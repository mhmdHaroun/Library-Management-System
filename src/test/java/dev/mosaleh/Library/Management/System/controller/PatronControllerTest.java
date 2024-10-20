package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.dtos.PatronRequest;
import dev.mosaleh.Library.Management.System.dtos.PatronResponse;
import dev.mosaleh.Library.Management.System.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private MockMvc mockMvc;

    private PatronRequest patronRequest;
    private PatronResponse patronResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();

        patronRequest = new PatronRequest();
        patronRequest.setName("John Doe");
        patronRequest.setEmail("john.doe@example.com");
        patronRequest.setPhoneNumber("1234567890");
        patronRequest.setAddress("1234 Elm St");

        patronResponse = new PatronResponse();
        patronResponse.setId(1L);
        patronResponse.setName("John Doe");
        patronResponse.setEmail("john.doe@example.com");
        patronResponse.setPhoneNumber("1234567890");
        patronResponse.setAddress("1234 Elm St");
    }

    @Test
    public void testGetPatronById() throws Exception {
        when(patronService.getPatronById(1L)).thenReturn(patronResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testAddPatron() throws Exception {
        when(patronService.addPatron(any(PatronRequest.class))).thenReturn(patronResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"email\":\"john.doe@example.com\", \"phoneNumber\":\"1234567890\", \"address\":\"1234 Elm St\"}")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        when(patronService.updatePatron(1L, patronRequest)).thenReturn(patronResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"email\":\"john.doe@example.com\", \"phoneNumber\":\"1234567890\", \"address\":\"1234 Elm St\"}")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }
}
