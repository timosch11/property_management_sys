package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.LeaseContract;
import de.hs_esslingen.property_management_system.usecases.LeaseContractUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lease-contracts")
public class LeaseContractRestController {
    
    @Autowired
    LeaseContractUseCase leaseContractUseCase;
    
    @GetMapping
    public Map<String, List<LeaseContract>> findAllLeaseContracts() {
        List<LeaseContract> leaseContractList = leaseContractUseCase.findAllLeaseContracts();
        return addKeyLeaseContractList(leaseContractList);
    }

    @GetMapping("/{id}")
    public Map<String, List<LeaseContract>> findLeaseContractsById(@PathVariable Integer id) {
        List<LeaseContract> leaseContractList = leaseContractUseCase.findLeaseContractsById(id);
        return addKeyLeaseContractList(leaseContractList);
    }

    @PostMapping
    public Map<String, String> postLeaseContract(@RequestBody LeaseContract leaseContract) {
        String message = leaseContractUseCase.createLeaseContract(leaseContract);
        return addKeyForResponseMassage(message);
    }

    @PutMapping
    public Map<String, String> putLeaseContract(@RequestBody LeaseContract leaseContract) {
        String message = leaseContractUseCase.updateLeaseContract(leaseContract);
        return addKeyForResponseMassage(message);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteLeaseContract(@PathVariable Integer id) {
        String message = leaseContractUseCase.deleteLeaseContract(id);
        return addKeyForResponseMassage(message);
    }

    private Map<String, List<LeaseContract>> addKeyLeaseContractList(List<LeaseContract> leaseContractList) {
        Map<String, List<LeaseContract>> leaseContractMap = new HashMap<>();
        leaseContractMap.put("leaseContracts", leaseContractList);
        return leaseContractMap;
    }

    private Map<String, String> addKeyForResponseMassage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
