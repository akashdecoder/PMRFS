package com.api.controller;

import com.api.Web3jClient;
import com.api.model.*;
import com.api.repository.*;

import com.api.security.UserDetail;
import com.api.services.MailService;
import com.api.services.OtpService;
import com.api.services.SMSService;
import com.api.services.ValidationService;
import com.api.utils.AddressPrivateKeyMap;
import com.api.utils.Addresses;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class PostResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersFundsRepository usersFundsRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PublicServiceDetailsRepository publicServiceDetailsRepository;

    @Autowired
    private VictimDetailsRepository victimDetailsRepository;

    @Autowired
    private AadhaarDetailsRepository aadhaarDetailsRepository;

    @Autowired
    private OrganizationDetailsRepository organizationDetailsRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private ContributorDetailsRepository contributorDetailsRepository;

    @Autowired
    private AddressLogRepository addressLogRepository;

    @Autowired
    private FundingDetailsRepository fundingDetailsRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private Web3jClient web3jClient;

    private User tempUser;

    private static User user;

    @PostMapping("/registered")
    public String createUser(@Valid User user, RedirectAttributes redirectAttributes, Model model, HttpSession session) throws ExecutionException, InterruptedException {
        List<User> users = userRepository.findAll();
        User u = userRepository.findByAadhaar(user.getUAadhaar());
        Pair<String, String> addressKeyPair = AddressPrivateKeyMap.getNewKeyPair(users);
        if(u == null) {
            Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
            if(!validationService.isValid(Long.toString(user.getUAadhaar()))) {
                redirectAttributes.addFlashAttribute("warning", "Invalid Aadhaar");
                return "redirect:/register";
            }
            if(!validationService.isValid(user.getUEmail())) {
                redirectAttributes.addFlashAttribute("warning", "Invalid Email");
                return "redirect:/register";
            }
            if(!validationService.isValid(user.getUPassword())) {
                redirectAttributes.addFlashAttribute("warning", "Invalid Password");
                return "redirect:/register";
            }

            if(user.getUCategory().equals("PMO_PTNT")) {
                while(true) {
                    addressKeyPair = AddressPrivateKeyMap.getNewKeyPair(users);
                    if (Long.parseLong(web3jClient.getBalance(web3j, addressKeyPair.getFirst())) > 100) {
                        break;
                    }
                }
                user.setUAddress(addressKeyPair.getFirst());
                user.setUPrivateKey(addressKeyPair.getSecond());
                user.setUCurrentOutstandingAmount(web3jClient.getBalance(web3j, user.getUAddress()));
                Roles roles = rolesRepository.findByRName("PMO_PTNT");
                user.addRole(roles);
            } else if(user.getUCategory().equals("PMO_PUSR")) {
                while(true) {
                    addressKeyPair = AddressPrivateKeyMap.getNewKeyPair(users);
                    if (Long.parseLong(web3jClient.getBalance(web3j, addressKeyPair.getFirst())) > 100) {
                        break;
                    }
                }
                user.setUAddress(addressKeyPair.getFirst());
                user.setUPrivateKey(addressKeyPair.getSecond());
                user.setUCurrentOutstandingAmount(web3jClient.getBalance(web3j, user.getUAddress()));
                Roles roles = rolesRepository.findByRName("PMO_PUSR");
                user.addRole(roles);
            } else if (user.getUCategory().equals("PMO_VCTM")) {
                while(true) {
                    addressKeyPair = AddressPrivateKeyMap.getNewKeyPair(users);
                    if (Long.parseLong(web3jClient.getBalance(web3j, addressKeyPair.getFirst())) > 100) {
                        break;
                    }
                }
                user.setUAddress(addressKeyPair.getFirst());
                user.setUPrivateKey(addressKeyPair.getSecond());
                user.setUCurrentOutstandingAmount(web3jClient.getBalance(web3j, user.getUAddress()));
                Roles roles = rolesRepository.findByRName("PMO_VCTM");
                user.addRole(roles);
            } else if(user.getUCategory().equals("CONTRIBUTOR")) {
                while(true) {
                    addressKeyPair = AddressPrivateKeyMap.getNewKeyPair(users);
                    if (Long.parseLong(web3jClient.getBalance(web3j, addressKeyPair.getFirst())) > 100) {
                        break;
                    }
                }
                List<User> contributors = userRepository.findByCategory(user.getUCategory());
                user.setUAddress(addressKeyPair.getFirst());
                user.setUPrivateKey(addressKeyPair.getSecond());
                user.setUCurrentOutstandingAmount(web3jClient.getBalance(web3j, user.getUAddress()));
                Roles roles = rolesRepository.findByRName("CONTRIBUTOR");
                user.addRole(roles);
            }
            else {
                if(aadhaarDetailsRepository.getAddressByAadhaar(Long.toString(user.getUAadhaar())) != null) {
                    if(user.getUCategory().equals("Patient")) {
                        Roles roles = rolesRepository.findByRName("PTNT");
                        user.setUDisableVerification("0");
                        user.addRole(roles);
                    } else if(user.getUCategory().equals("Public Services")) {
                        Roles roles = rolesRepository.findByRName("PUSR");
                        user.setUDisableVerification("0");
                        user.addRole(roles);
                    } else if(user.getUCategory().equals("Affected Victims")) {
                        Roles roles = rolesRepository.findByRName("VCTM");
                        user.setUDisableVerification("0");
                        user.addRole(roles);
                    }
                }
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getUPassword());
            user.setUPassword(encodedPassword);
            if(mailService.sendMail("Registration message", user.getUFirstName(), "", user.getUEmail())) {
                userRepository.save(user);
                redirectAttributes.addFlashAttribute("message", "Registered Successfully");
                return "redirect:/register";
            } else {
                redirectAttributes.addFlashAttribute("warning", "Invalid Email Id.");
            }
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("warning", "User Already Exists");
        return "redirect:/register";
    }

    @PostMapping("/requested")
    public String requestFund(@Valid UsersFunds usersFunds, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) {
        String email = loggedUser.getUsername();
        User user = userRepository.findByEmail(email);
        if(user.getUCurrentRequestedAmount() != null) {
            redirectAttributes.addFlashAttribute("warning", "Please wait till it get approved for applying a new request.");
            return "redirect:/requestFunds";
        }
        usersFunds.setUFirstName(user.getUFirstName());
        user.setUCurrentRequestedAmount(usersFunds.getUFundsHistory());
        usersFunds.setUId(user.getUId());
        usersFundsRepository.save(usersFunds);
        userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), usersFunds.getUFundsHistory(), usersFunds.getUReason(), user.getUId());
        redirectAttributes.addFlashAttribute("message", user.getUFirstName() + " Fund Raised");
        return "redirect:/requestFunds";
    }

//    @PostMapping("/verified_ptnt")
//    public String verifyPatientDetails(@Valid PatientDetails patientDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
//        String pmEmail = "";
//        String email = loggedUser.getUsername();
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        User user = userRepository.findByEmail(email);
//        List<User> users = userRepository.findAll();
//        UsersFunds usersFunds = new UsersFunds();
//        patientDetails.setUId(user.getUId());
//        for(User u : users) {
//            if(u.hasRole("PMO_PTNT")) {
//                pmEmail = u.getUEmail();
//            } else if(u.hasRole("PMO_VCTM")) {
//                pmEmail = u.getUEmail();
//            } else if(u.hasRole("PMO_PUSR")) {
//                pmEmail = u.getUEmail();
//            }
//        }
//        if(web3jClient.isValidated(patientDetails, null, null, true)) {
//            userRepository.updateUserApprovedStatus("0", email);
//            patientDetails.setUApproveStatus("0");
//            patientDetailsRepository.save(patientDetails);
//            usersFunds.setUFirstName(user.getUFirstName());
//            user.setUCurrentRequestedAmount(usersFunds.getUFundsHistory());
//            usersFunds.setUId(user.getUId());
//            usersFunds.setUFundsHistory(patientDetails.getPFundNeed());
//            usersFunds.setUReason(patientDetails.getPCaseType());
//            usersFunds.setURequestTimestamp(timestamp);
//            usersFunds.setUIsApproved(0);
//            usersFundsRepository.save(usersFunds);
//            userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), patientDetails.getPFundNeed(), patientDetails.getPCaseType(), patientDetails.getUId());
//            userRepository.updateUserDisableVerification("1", user.getUId());
//            redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
//            mailService.sendMail("Fund Request Verification", null, patientDetails.getPCaseType(), pmEmail);
//            return "redirect:/verification";
//        }
//        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
//        return "redirect:/verification";
//    }

    @PostMapping("/verified_ptnt")
    public String verifyPatientDetailsResource(@Valid PatientDetails patientDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
        String pmEmail = "";
        String email = loggedUser.getUsername();
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = userRepository.findByEmail(email);
        List<User> users = userRepository.findAll();

        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();

        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(patientDetails.getPHospitalFundRequestId()));

        Pair<String, String> addressKeyPair = AddressPrivateKeyMap.getNewKeyPairForRequester(fundingDetailsList);
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));

        patientDetails.setUId(user.getUId());
        for(User u : users) {
            if(u.hasRole("PMO_PTNT")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_VCTM")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_PUSR")) {
                pmEmail = u.getUEmail();
            }
        }
        if(organizationDetails.getOIsApproved().equals("0")) {
            if(web3jClient.isValidated(patientDetails, null, null, true)) {
                patientDetails.setUApproveStatus("0");
                patientDetailsRepository.save(patientDetails);
                FundingDetails fundingDetails = new FundingDetails();
                fundingDetails.setUId(user.getUId());
                fundingDetails.setFRequestedAmount(Long.parseLong(patientDetails.getPFundNeed()));
                fundingDetails.setFRequestedTimestamp(timestamp);
                fundingDetails.setFRequestReason(patientDetails.getPCaseType());
                fundingDetails.setUBankAccountNumber(user.getUBankAccountNumber());
                fundingDetails.setFAccountAddress(addressKeyPair.getFirst());
                fundingDetailsRepository.save(fundingDetails);
                userRepository.updateUserDisableVerification("1", user.getUId());
                redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
                mailService.sendMail("Fund Request Verification", null, patientDetails.getPCaseType(), pmEmail);
                return "redirect:/verification";
            }
        }
        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
        return "redirect:/verification";
    }

//    @PostMapping("/verified_vctm")
//    public String verifyVictimDetails(@Valid VictimDetails victimDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
//        String pmEmail = "";
//        String email = loggedUser.getUsername();
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        User user = userRepository.findByEmail(email);
//        List<User> users = userRepository.findAll();
//        UsersFunds usersFunds = new UsersFunds();
//        victimDetails.setUId(user.getUId());
//        for(User u : users) {
//            if(u.hasRole("PMO")) {
//                pmEmail = u.getUEmail();
//            }
//        }
//        if(web3jClient.isValidated(null, null, victimDetails, true)) {
//            userRepository.updateUserApprovedStatus("0", email);
//            victimDetails.setUApproveStatus("0");
//            victimDetailsRepository.save(victimDetails);
//            usersFunds.setUFirstName(user.getUFirstName());
//            user.setUCurrentRequestedAmount(usersFunds.getUFundsHistory());
//            usersFunds.setUId(user.getUId());
//            usersFunds.setUFundsHistory(victimDetails.getVFundNeed());
//            usersFunds.setUReason(victimDetails.getVCaseType());
//            usersFunds.setURequestTimestamp(timestamp);
//            usersFunds.setUIsApproved(0);
//            usersFundsRepository.save(usersFunds);
//            userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), victimDetails.getVFundNeed(), victimDetails.getVCaseType(), victimDetails.getUId());
//            userRepository.updateUserDisableVerification("1", user.getUId());
//            redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
//            mailService.sendMail("Fund Request Verification", null, victimDetails.getVCaseType(), pmEmail);
//            return "redirect:/verification";
//        }
//        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
//        return "redirect:/verification";
//    }

    @PostMapping("/verified_vctm")
    public String verifyVictimDetails(@Valid VictimDetails victimDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
        String pmEmail = "";
        String email = loggedUser.getUsername();
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = userRepository.findByEmail(email);
        List<User> users = userRepository.findAll();

        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();

        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(victimDetails.getVOrganizationId()));

        Pair<String, String> addressKeyPair = AddressPrivateKeyMap.getNewKeyPairForRequester(fundingDetailsList);
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));

        victimDetails.setUId(user.getUId());
        for(User u : users) {
            if(u.hasRole("PMO_PTNT")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_VCTM")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_PUSR")) {
                pmEmail = u.getUEmail();
            }
        }
        if(organizationDetails.getOIsApproved().equals("0")) {
            if(web3jClient.isValidated(null, null, victimDetails, true)) {
                victimDetails.setUApproveStatus("0");
                victimDetailsRepository.save(victimDetails);
                FundingDetails fundingDetails = new FundingDetails();
                fundingDetails.setUId(user.getUId());
                fundingDetails.setFRequestedAmount(Long.parseLong(victimDetails.getVFundNeed()));
                fundingDetails.setFRequestedTimestamp(timestamp);
                fundingDetails.setFRequestReason(victimDetails.getVCaseType());
                fundingDetails.setUBankAccountNumber(user.getUBankAccountNumber());
                fundingDetails.setFAccountAddress(addressKeyPair.getFirst());
                fundingDetailsRepository.save(fundingDetails);
                userRepository.updateUserDisableVerification("1", user.getUId());
                redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
                mailService.sendMail("Fund Request Verification", null, victimDetails.getVCaseType(), pmEmail);
                return "redirect:/verification";
            }
        }
        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
        return "redirect:/verification";
    }

//    @PostMapping("/verified_pusr")
//    public String verifyPublicServiceDetails(@Valid PublicServiceDetails publicServiceDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
//        String pmEmail = "";
//        String email = loggedUser.getUsername();
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        User user = userRepository.findByEmail(email);
//        List<User> users = userRepository.findAll();
//        UsersFunds usersFunds = new UsersFunds();
//        publicServiceDetails.setUId(user.getUId());
//        for(User u : users) {
//            if(u.hasRole("PMO")) {
//                pmEmail = u.getUEmail();
//            }
//        }
//        if(web3jClient.isValidated(null, publicServiceDetails, null, true)) {
//            userRepository.updateUserApprovedStatus("0", email);
//            publicServiceDetails.setUApproveStatus("0");
//            publicServiceDetailsRepository.save(publicServiceDetails);
//            usersFunds.setUFirstName(user.getUFirstName());
//            user.setUCurrentRequestedAmount(usersFunds.getUFundsHistory());
//            usersFunds.setUId(user.getUId());
//            usersFunds.setUFundsHistory(publicServiceDetails.getPUFundNeed());
//            usersFunds.setUReason(publicServiceDetails.getPUServiceType());
//            usersFunds.setURequestTimestamp(timestamp);
//            usersFunds.setUIsApproved(0);
//            usersFundsRepository.save(usersFunds);
//            userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), publicServiceDetails.getPUFundNeed(), publicServiceDetails.getPUServiceType(), publicServiceDetails.getUId());
//            userRepository.updateUserDisableVerification("1", user.getUId());
//            redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
//            mailService.sendMail("Fund Request Verification", null, publicServiceDetails.getPUServiceType(), pmEmail);
//            return "redirect:/verification";
//        }
//        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
//        return "redirect:/verification";
//    }

    @PostMapping("/verified_pusr")
    public String verifyPublicServiceDetails(@Valid PublicServiceDetails publicServiceDetails, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) throws Exception {
        String pmEmail = "";
        String email = loggedUser.getUsername();
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = userRepository.findByEmail(email);
        List<User> users = userRepository.findAll();

        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();
        OrganizationDetails organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(publicServiceDetails.getPUOfficialConsentId()));

        Pair<String, String> addressKeyPair = AddressPrivateKeyMap.getNewKeyPairForRequester(fundingDetailsList);
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));

        publicServiceDetails.setUId(user.getUId());
        for(User u : users) {
            if(u.hasRole("PMO_PTNT")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_VCTM")) {
                pmEmail = u.getUEmail();
            } else if(u.hasRole("PMO_PUSR")) {
                pmEmail = u.getUEmail();
            }
        }
        if(organizationDetails.getOIsApproved().equals("0")) {
            if(web3jClient.isValidated(null, publicServiceDetails, null, true)) {
                publicServiceDetails.setUApproveStatus("0");
                publicServiceDetailsRepository.save(publicServiceDetails);
                FundingDetails fundingDetails = new FundingDetails();
                fundingDetails.setUId(user.getUId());
                fundingDetails.setFRequestedAmount(Long.parseLong(publicServiceDetails.getPUFundNeed()));
                fundingDetails.setFRequestedTimestamp(timestamp);
                fundingDetails.setFRequestReason(publicServiceDetails.getPUServiceType());
                fundingDetails.setUBankAccountNumber(user.getUBankAccountNumber());
                fundingDetails.setFAccountAddress(addressKeyPair.getFirst());
                fundingDetailsRepository.save(fundingDetails);
                userRepository.updateUserDisableVerification("1", user.getUId());
                redirectAttributes.addFlashAttribute("message", "Request is Verified. Check Account frequently within 3 days.");
                mailService.sendMail("Fund Request Verification", null, publicServiceDetails.getPUServiceType(), pmEmail);
                return "redirect:/verification";
            }
        }
        redirectAttributes.addFlashAttribute("warning", "Invalid Request. Fill in correct details");
        return "redirect:/verification";
    }



    @PostMapping("/updated/{uId}")
    public String updateProfile(User user, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) {
        if(!validationService.isValid(Long.toString(user.getUAadhaar()))) {
            redirectAttributes.addFlashAttribute("warning", "Invalid Aadhaar");
            return "redirect:/account/"+user.getUId();
        }
        if(!validationService.isValid(user.getUEmail())) {
            redirectAttributes.addFlashAttribute("warning", "Invalid Email");
            return "redirect:/account/"+user.getUId();
        }
        userRepository.updateUser(user.getUFirstName(), user.getULastName(), user.getUEmail(), user.getUPhone(), user.getUAadhaar(), user.getUPan(), user.getUDob(), user.getUBankAccountNumber(), user.getUIFSCCode(), user.getUId());
        redirectAttributes.addFlashAttribute("message", "Profile Updated");
        return "redirect:/myProfile";
    }


    @PostMapping("/contributed")
    public String contributeToPMFund(@Valid ContributorDetails contributorDetails, @AuthenticationPrincipal UserDetails loggedUser, RedirectAttributes redirectAttributes) {
        String email = loggedUser.getUsername();
        User user = userRepository.findByEmail(email);
        User pmo = new User();

        Timestamp timestamp = Timestamp.from(Instant.now());
        if(Integer.parseInt(contributorDetails.getCAmount()) <= Integer.parseInt(user.getUCurrentOutstandingAmount())) {
            Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
            Credentials credentials = web3jClient.getCredentialsFromPrivateKey(user.getUPrivateKey());
            contributorDetails.setCName(user.getUFirstName() + " " + user.getULastName());
            contributorDetails.setCAddress(user.getUAddress());
            contributorDetails.setCContributionTimestamp(timestamp);
            try {
                if(contributorDetails.getCContributionFor().equals("PMO_PTNT")) {
                    pmo = userRepository.findUserByCategory("PMO_PTNT");
                    web3jClient.transferEthereum(web3j, credentials, pmo.getUAddress(), Long.parseLong(contributorDetails.getCAmount()));
                    userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), null, null, user.getUId());
                    contributorDetailsRepository.save(contributorDetails);
                    mailService.sendMail("Contribution", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor(), user.getUEmail());
                    mailService.sendMail("Contribution PMO", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor() + "with amount: " + contributorDetails.getCAmount(), pmo.getUEmail());
                    redirectAttributes.addFlashAttribute("message", contributorDetails.getCAmount() + " is being contributed to PM Relief Fund for " + contributorDetails.getCContributionFor() + ".");
                    return "redirect:/contribute";

                } else if(contributorDetails.getCContributionFor().equals("PMO_PUSR")) {
                    pmo = userRepository.findUserByCategory("PMO_PUSR");
                    web3jClient.transferEthereum(web3j, credentials, pmo.getUAddress(), Long.parseLong(contributorDetails.getCAmount()));
                    userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), null, null, user.getUId());
                    contributorDetailsRepository.save(contributorDetails);
                    mailService.sendMail("Contribution", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor(), user.getUEmail());
                    mailService.sendMail("Contribution PMO", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor() + "with amount: " + contributorDetails.getCAmount(), pmo.getUEmail());
                    redirectAttributes.addFlashAttribute("message", contributorDetails.getCAmount() + " is being contributed to PM Relief Fund for " + contributorDetails.getCContributionFor() + ".");
                    return "redirect:/contribute";
                } else if(contributorDetails.getCContributionFor().equals("PMO_VCTM")) {
                    pmo = userRepository.findUserByCategory("PMO_VCTM");
                    web3jClient.transferEthereum(web3j, credentials, pmo.getUAddress(), Long.parseLong(contributorDetails.getCAmount()));
                    userRepository.updateUserFunds(user.getUCurrentOutstandingAmount(), null, null, user.getUId());
                    contributorDetailsRepository.save(contributorDetails);
                    mailService.sendMail("Contribution", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor(), user.getUEmail());
                    mailService.sendMail("Contribution PMO", user.getUFirstName() + " " + user.getULastName(), contributorDetails.getCContributionFor() + "with amount: " + contributorDetails.getCAmount(), pmo.getUEmail());
                    redirectAttributes.addFlashAttribute("message", contributorDetails.getCAmount() + " is being contributed to PM Relief Fund for " + contributorDetails.getCContributionFor() + ".");
                    return "redirect:/contribute";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        redirectAttributes.addFlashAttribute("warning", "Invalid Details. Please check and fill the correct details");
        return "redirect:/contribute";
    }
}