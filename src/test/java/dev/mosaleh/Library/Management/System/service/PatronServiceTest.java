package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.model.Patron;
import dev.mosaleh.Library.Management.System.repository.PatronRepository;
import dev.mosaleh.Library.Management.System.dtos.PatronRequest;
import dev.mosaleh.Library.Management.System.dtos.PatronResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    private Patron patron;
    private PatronRequest patronRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john.doe@example.com");
        patron.setPhoneNumber("1234567890");
        patron.setAddress("123 Main St");

        patronRequest = new PatronRequest();
        patronRequest.setName("John Doe");
        patronRequest.setEmail("john.doe@example.com");
        patronRequest.setPhoneNumber("1234567890");
        patronRequest.setAddress("123 Main St");
    }

    @Test
    public void testGetAllPatrons() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Patron> patronPage = new PageImpl<>(List.of(patron));

        when(patronRepository.findAll(pageable)).thenReturn(patronPage);

        Page<PatronResponse> result = patronService.getAllPatrons(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
        verify(patronRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetPatronById() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        PatronResponse result = patronService.getPatronById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPatronById_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> patronService.getPatronById(1L));

        assertEquals("Patron not found", thrown.getMessage());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddPatron() {
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        PatronResponse result = patronService.addPatron(patronRequest);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("1234567890", result.getPhoneNumber());
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    public void testUpdatePatron() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        PatronResponse result = patronService.updatePatron(1L, patronRequest);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    public void testUpdatePatron_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> patronService.updatePatron(1L, patronRequest));

        assertEquals("Patron not found", thrown.getMessage());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeletePatron() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deletePatron(1L);

        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).delete(any(Patron.class));
    }

    @Test
    public void testDeletePatron_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> patronService.deletePatron(1L));

        assertEquals("Patron not found", thrown.getMessage());
        verify(patronRepository, times(1)).findById(1L);
    }
}
