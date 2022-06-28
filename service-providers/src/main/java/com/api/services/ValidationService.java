package com.api.services;

import com.api.model.OrganizationDetails;
import com.api.model.PatientDetails;
import com.api.model.PublicServiceDetails;
import com.api.model.VictimDetails;
import com.api.repository.AadhaarDetailsRepository;
import com.api.repository.IncomeDetailsRepository;
import com.api.repository.OrganizationDetailsRepository;
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

    public boolean isValidPatientRequest(PatientDetails patientDetails) {
        boolean flag = false;
        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(patientDetails.getPHospitalFundRequestId()));
        if((aadhaarDetailsRepository.getAddressByAadhaar(patientDetails.getPAadhaar()) != null) && (incomeDetailsRepository.getByIncomeId(Long.parseLong(patientDetails.getPIncomeId())) != null) && (organizationDetailsRepository.getByRequestId(Long.parseLong(patientDetails.getPHospitalFundRequestId())) != null) && (UniqueIdentifiers.fundCategoryMap.containsKey(patientDetails.getPCaseType())) && ((Integer.parseInt(patientDetails.getPFundNeed())) <= (Integer.parseInt(organizationDetails.getOAmount())))) {
            flag = true;
        }
        return flag;
    }

    public boolean isValidPUSRRequest(PublicServiceDetails publicServiceDetails) {
        boolean flag = false;
        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(publicServiceDetails.getPUOfficialConsentId()));
        if((aadhaarDetailsRepository.getAddressByAadhaar(publicServiceDetails.getPUAadhaar()) != null) && (organizationDetailsRepository.getByRequestId(Long.parseLong(publicServiceDetails.getPUOfficialConsentId())) != null) && ((Integer.parseInt(publicServiceDetails.getPUFundNeed())) <= (Integer.parseInt(organizationDetails.getOAmount())))) {
            flag = true;
        }
        return flag;
    }

    public boolean isValidVictimRequest(VictimDetails victimDetails) {
        boolean flag = false;
        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(victimDetails.getVOrganizationId()));
        if((aadhaarDetailsRepository.getAddressByAadhaar(victimDetails.getVAadhaar()) != null) && (organizationDetailsRepository.getByRequestId(Long.parseLong(victimDetails.getVOrganizationId())) != null) && ((Integer.parseInt(victimDetails.getVFundNeed())) <= (Integer.parseInt(organizationDetails.getOAmount())))) {
            flag = true;
        }
        return flag;
    }
}
