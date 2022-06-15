package com.api.controller.restcontrollers;

import com.api.model.IncomeDetails;
import com.api.model.PatientDetails;
import com.api.model.PublicServiceDetails;
import com.api.model.VictimDetails;
import com.api.repository.IncomeDetailsRepository;
import com.api.repository.PatientDetailsRepository;
import com.api.repository.PublicServiceDetailsRepository;
import com.api.repository.VictimDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetController {

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PublicServiceDetailsRepository publicServiceDetailsRepository;

    @Autowired
    private VictimDetailsRepository victimDetailsRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @GetMapping("/allPatientDetails")
    public List<PatientDetails> getPatientDetails() {
        return patientDetailsRepository.findAll();
    }

    @GetMapping("/allVictimDetails")
    public List<VictimDetails> getVictimDetails() {
        return victimDetailsRepository.findAll();
    }

    @GetMapping("/allPublicServiceDetails")
    public List<PublicServiceDetails> getPublicServiceDetails() {
        return publicServiceDetailsRepository.findAll();
    }

    @GetMapping("/allIncomeDetails")
    public List<IncomeDetails> getIncomeDetails() {
        return incomeDetailsRepository.findAll();
    }
}
