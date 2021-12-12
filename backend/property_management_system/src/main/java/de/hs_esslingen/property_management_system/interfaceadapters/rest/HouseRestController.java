package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.House;
import de.hs_esslingen.property_management_system.usecases.HouseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/houses")
public class HouseRestController {

    @Autowired
    HouseUseCase houseUseCase;

    @GetMapping
    public Map<String, List<House>> getAllHouses() {
        List<House> houseList = houseUseCase.findAllHouses();
        return addKeyHouseList(houseList);
    }

    @GetMapping("/{id}")
    public Map<String, List<House>> getHouseByID (@PathVariable Integer id) {
        List<House> houseList = houseUseCase.findHouseById(id);
        return addKeyHouseList(houseList);
    }

    @PostMapping
    public Map<String, String> postHouse (@RequestBody House house) {
        String message = houseUseCase.createHouse(house);
        return addKeyForResponseMassage(message);
    }

    @PutMapping
    public Map<String, String> putHouse(@RequestBody House house) {
        String message = houseUseCase.updateHouse(house);
        return addKeyForResponseMassage(message);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteHouse(@PathVariable Integer id) {
        String message = houseUseCase.deleteHouseById(id);
        return addKeyForResponseMassage(message);
    }

    private Map<String, List<House>> addKeyHouseList(List<House> houseList) {
        Map<String, List<House>> houseMap = new HashMap<>();
        houseMap.put("houses", houseList);
        return houseMap;
    }

    private Map<String, String> addKeyForResponseMassage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
