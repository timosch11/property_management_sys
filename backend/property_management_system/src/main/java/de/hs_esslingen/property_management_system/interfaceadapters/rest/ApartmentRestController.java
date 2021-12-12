package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.Apartment;
import de.hs_esslingen.property_management_system.usecases.ApartmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apartments")
public class ApartmentRestController {

    @Autowired
    private ApartmentUseCase apartmentUseCase;

    @GetMapping
    public Map<String, List<Apartment>> getAllApartments() {
        List<Apartment> apartmentList = apartmentUseCase.findAllApartments();
        return addKeyApartmentList(apartmentList);
    }

    @GetMapping("/{id}")
    public Map<String, List<Apartment>> getApartmentById(@PathVariable Integer id) {
        List<Apartment> apartmentList = apartmentUseCase.findApartmentById(id);
        return addKeyApartmentList(apartmentList);
    }

    @PostMapping
    public Map<String, String> postApartment (@RequestBody Apartment apartment) {
        String message = apartmentUseCase.createApartment(apartment);
        return addKeyForResponseMassage(message);
    }

    @PutMapping
    public Map<String, String> putApartment(@RequestBody Apartment apartment) {
        String message = apartmentUseCase.updateApartment(apartment);
        return addKeyForResponseMassage(message);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteApartment(@PathVariable Integer id) {
        String message = apartmentUseCase.deleteApartment(id);
        return addKeyForResponseMassage(message);
    }

    private Map<String, List<Apartment>> addKeyApartmentList(List<Apartment> apartmentList) {
        Map<String, List<Apartment>> apartmentMap = new HashMap<>();
        apartmentMap.put("apartments", apartmentList);
        return apartmentMap;
    }

    private Map<String, String> addKeyForResponseMassage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
