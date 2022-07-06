package com.api.services;

import com.api.model.OrganizationDetails;
import com.api.model.PatientDetails;
import com.api.model.PublicServiceDetails;
import com.api.model.VictimDetails;
import com.api.repository.AadhaarDetailsRepository;
import com.api.repository.IncomeDetailsRepository;
import com.api.repository.OrganizationDetailsRepository;
import com.api.utils.UniqueIdentifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private AadhaarDetailsRepository aadhaarDetailsRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private OrganizationDetailsRepository organizationDetailsRepository;

    public boolean isValid(String data) {
        String aadhaarRegex = "^[0-9]{4}[0-9]{4}[0-9]{4}$";
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(data.matches(aadhaarRegex)) {
            return true;
        }
        if(data.matches(passwordRegex)) {
            return true;
        }
        if(data.matches(emailRegex)) {
            return true;
        }
        return false;
    }
}
