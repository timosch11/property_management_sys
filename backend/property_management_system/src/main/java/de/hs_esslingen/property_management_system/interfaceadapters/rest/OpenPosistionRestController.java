package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.OpenPosition;
import de.hs_esslingen.property_management_system.usecases.OpenPositionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/open-positions")
public class OpenPosistionRestController {

    @Autowired
    OpenPositionUseCase openPositionUseCase;

    @GetMapping
    public Map<String, List<OpenPosition>> getAllOpenPositions() {
        List<OpenPosition> openPositionList = openPositionUseCase.getAllOpenPositions();
        Map<String, List<OpenPosition>> openPositionMap = new HashMap<>();
        openPositionMap.put("openPositions", openPositionList);
        return openPositionMap;
    }
}
