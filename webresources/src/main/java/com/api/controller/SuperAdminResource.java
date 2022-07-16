package com.api.controller;

import com.api.model.AadhaarDetails;
import com.api.model.IncomeDetails;
import com.api.model.OrganizationDetails;
import com.api.repository.AadhaarDetailsRepository;
import com.api.repository.IncomeDetailsRepository;
import com.api.repository.OrganizationDetailsRepository;
import com.api.services.RandomNumberGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SuperAdminResource {

    @Autowired
    private AadhaarDetailsRepository aadhaarDetailsRepository;

    @Autowired
    private OrganizationDetailsRepository organizationDetailsRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private RandomNumberGeneratorService randomNumberGeneratorService;

    @GetMapping("/addAadhaarDetails")
    public String showAadhaarPage(Model model) {
        model.addAttribute("aadhaar", new AadhaarDetails());
        return "addAadhaarDetails.html";
    }

    @PostMapping("/addedAadhaarDetails")
    public String addAadhaarDetails(@Valid AadhaarDetails aadhaarDetails, RedirectAttributes redirectAttributes) {
        AadhaarDetails aadhaarDetail = aadhaarDetailsRepository.findByAadhaar(aadhaarDetails.getUAadhaar());
        if(aadhaarDetail == null) {
            aadhaarDetailsRepository.save(aadhaarDetails);
            redirectAttributes.addFlashAttribute("message", "Added Details Successfully");
            return "redirect:/addAadhaarDetails";
        }
        redirectAttributes.addFlashAttribute("warning", "Aadhaar already exists.");
        return "redirect:/addAadhaarDetails";
    }

    @GetMapping("/addIncomeDetails")
    public String showIncomePage(Model model) {
        model.addAttribute("income", new IncomeDetails());
        return "add_income_details.html";
    }

    @PostMapping("/addedIncomeDetails")
    public String addIncomeDetails(@Valid IncomeDetails incomeDetails, RedirectAttributes redirectAttributes) {
        AadhaarDetails aadhaarDetail = aadhaarDetailsRepository.findByAadhaar(incomeDetails.getUAadhaar());
        if(aadhaarDetail != null) {
            incomeDetails.setIIncomeId(Long.parseLong(randomNumberGeneratorService.generateRandomId()));
            incomeDetailsRepository.save(incomeDetails);
            redirectAttributes.addFlashAttribute("message", "Added Details Successfully");
            return "redirect:/addIncomeDetails";
        }
        redirectAttributes.addFlashAttribute("warning", "Aadhaar is not valid");
        return "redirect:/addIncomeDetails";
    }

    @GetMapping("/addRequestFundDetails")
    public String showRequestFundPage(Model model) {
        model.addAttribute("request", new OrganizationDetails());
        return "organizarion_request_fund.html";
    }

    @PostMapping("/addedRequestFundDetails")
    public String addRequestFundDetails(@Valid OrganizationDetails organizationDetails, RedirectAttributes redirectAttributes) {
        AadhaarDetails aadhaarDetail = aadhaarDetailsRepository.findByAadhaar(organizationDetails.getUAadhaar());
        if(aadhaarDetail != null) {
            organizationDetails.setORequestId(Long.parseLong(randomNumberGeneratorService.generateRandomId()));
            organizationDetails.setOIsApproved("0");
            organizationDetailsRepository.save(organizationDetails);
            redirectAttributes.addFlashAttribute("message", "Added Details Successfully");
            return "redirect:/addRequestFundDetails";
        }
        redirectAttributes.addFlashAttribute("warning", "Aadhaar is not valid");
        return "redirect:/addRequestFundDetails";
    }
}
