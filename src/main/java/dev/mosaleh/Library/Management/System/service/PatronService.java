package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.model.Patron;
import dev.mosaleh.Library.Management.System.repository.PatronRepository;
import dev.mosaleh.Library.Management.System.dtos.PatronRequest;
import dev.mosaleh.Library.Management.System.dtos.PatronResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public Page<PatronResponse> getAllPatrons(Pageable pageable) {
        return patronRepository.findAll(pageable).map(this::convertToResponse);
    }

    public PatronResponse getPatronById(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new RuntimeException("Patron not found"));
        return convertToResponse(patron);
    }

    @Transactional
    public PatronResponse addPatron(PatronRequest patronRequest) {
        Patron patron = new Patron();
        patron.setName(patronRequest.getName());
        patron.setEmail(patronRequest.getEmail());
        patron.setPhoneNumber(patronRequest.getPhoneNumber());
        patron.setAddress(patronRequest.getAddress());
        Patron savedPatron = patronRepository.save(patron);
        return convertToResponse(savedPatron);
    }

    @Transactional
    public PatronResponse updatePatron(Long id, PatronRequest patronRequest) {
        Patron existingPatron = patronRepository.findById(id).orElseThrow(() -> new RuntimeException("Patron not found"));
        existingPatron.setName(patronRequest.getName());
        existingPatron.setEmail(patronRequest.getEmail());
        existingPatron.setPhoneNumber(patronRequest.getPhoneNumber());
        existingPatron.setAddress(patronRequest.getAddress());
        Patron updatedPatron = patronRepository.save(existingPatron);
        return convertToResponse(updatedPatron);
    }

    @Transactional
    public void deletePatron(Long id) {
        Patron existingPatron = patronRepository.findById(id).orElseThrow(() -> new RuntimeException("Patron not found"));
        patronRepository.delete(existingPatron);
    }

    private PatronResponse convertToResponse(Patron patron) {
        PatronResponse response = new PatronResponse();
        response.setId(patron.getId());
        response.setName(patron.getName());
        response.setEmail(patron.getEmail());
        response.setPhoneNumber(patron.getPhoneNumber());
        response.setAddress(patron.getAddress());
        return response;
    }
}
