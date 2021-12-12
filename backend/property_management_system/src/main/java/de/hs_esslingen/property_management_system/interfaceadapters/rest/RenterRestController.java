package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.Renter;
import de.hs_esslingen.property_management_system.usecases.RenterUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/renters")
public class RenterRestController {

    @Autowired
    private RenterUseCase renterUseCase;

    @GetMapping
    public Map<String, List<Renter>> getAllRenters () {
        List<Renter> renterList = renterUseCase.findAllRenters();
        return addKeyForRenterList(renterList);
    }

    @GetMapping("/{id}")
    public Map<String, List<Renter>> getRenterById (@PathVariable Integer id) {
        List<Renter> renterList = renterUseCase.findRenterById(id);
        return addKeyForRenterList(renterList);
    }

    @PostMapping
    public Map<String, String> postRenter(@RequestBody Renter renter) {
        String message = renterUseCase.createRenter(renter);
        return addKeyForResponseMassage(message);
    }

    @PutMapping
    public Map<String, String> putRenter(@RequestBody Renter renter) {
        String message = renterUseCase.updateRenter(renter);
        return addKeyForResponseMassage(message);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteRenter(@PathVariable Integer id) {
        String message = renterUseCase.deleteRenter(id);
        return addKeyForResponseMassage(message);
    }

    private Map<String, List<Renter>> addKeyForRenterList (List<Renter> renterList) {
        Map<String, List<Renter>> renterMap = new HashMap<>();
        renterMap.put("renters", renterList);
        return renterMap;
    }

    private Map<String, String> addKeyForResponseMassage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
