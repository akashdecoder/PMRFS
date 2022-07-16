package com.api.controller;


import com.api.Web3jClient;
import com.api.model.*;
import com.api.repository.*;

import com.api.security.UserDetail;
import com.api.services.MailService;
import com.api.utils.AddressPrivateKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class GetResource {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UsersFundsRepository usersFundsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PublicServiceDetailsRepository publicServiceDetailsRepository;

    @Autowired
    private VictimDetailsRepository victimDetailsRepository;

    @Autowired
    private PMOFundHistoryRepository pmoFundHistoryRepository;

    @Autowired
    private AadhaarDetailsRepository aadhaarDetailsRepository;

    @Autowired
    private OrganizationDetailsRepository organizationDetailsRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private ContributorDetailsRepository contributorDetailsRepository;

    @Autowired
    private FundingDetailsRepository fundingDetailsRepository;

    @Autowired
    private AddressLogRepository addressLogRepository;

    @Autowired
    private Web3jClient web3jClient;

    @Autowired
    private MailService mailService;

    @GetMapping("/register")
    public String viewForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "cant exit";
    }

    @GetMapping("/logged_out")
    public String loggedOut() {
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String showMemberDashboard(@AuthenticationPrincipal UserDetail loggedMember, Model model) throws ExecutionException, InterruptedException {
        Map<String, Object> attributes = new HashMap<String, Object>();
        User u = userRepository.findByEmail(loggedMember.getUsername());
//        List<UsersFunds> usersFunds = usersFundsRepository.findAllByUId(u.getUId());
        List<PMOFundHistory> pmFunds = pmoFundHistoryRepository.findAllApprovedStatus("1");
        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();
        List<FundingDetails> approvedFunds = new ArrayList<>();
        List<FundingDetails> usersFunds = new ArrayList<>();
        for(FundingDetails details : fundingDetailsList) {
            if(details.getFApprovedAmount() != null) {
                approvedFunds.add(details);
            }
        }

        for(FundingDetails userFundDetails : approvedFunds) {
            if(userFundDetails.getUId() == u.getUId()) {
                usersFunds.add(userFundDetails);
            }
        }

        List<ContributorDetails> contributorDetailsList = contributorDetailsRepository.findAllByName(u.getUFirstName()+" "+u.getULastName());
        attributes.put("fundDetails", approvedFunds); // For PMO
        attributes.put("users", usersFunds); // For other roles
        attributes.put("fund", userRepository.findByEmail(loggedMember.getUsername()).getUCurrentOutstandingAmount()); // For other roles
        attributes.put("contributions", contributorDetailsList);
        attributes.put("contributions_lists", contributorDetailsRepository.findAll());
        model.addAllAttributes(attributes);
        return "dashboard";
    }

//    @GetMapping("/approveFunds")
//    public String showRequestedFunds(@AuthenticationPrincipal UserDetail loggedUser, Model model) {
//        User admin = userRepository.findByEmail(loggedUser.getUsername());
//        List<User> users = userRepository.findAll();
//        List<User> userList = new ArrayList<User>();
//        for(User user : users) {
//            if(user.hasRole("PMO_PTNT")) {
//                continue;
//            } else if(user.hasRole("PMO_PUSR")) {
//                continue;
//            } else if(user.hasRole("PMO_VCTM")) {
//                continue;
//            } else if(user.hasRole("CONTRIBUTOR")) {
//                continue;
//            }
//            System.out.println(user.getUEmail());
//            userList.add(user);
//        }
//        model.addAttribute("admin", userList);
//        return "pmo_approve";
//    }

    @GetMapping("/approveFunds")
    public String showRequestedFunds(@AuthenticationPrincipal UserDetail loggedUser, Model model) {
        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();
        List<FundingDetails> requestedFundsPtnt = new ArrayList<>();
        List<FundingDetails> requestedFundsPusr = new ArrayList<>();
        List<FundingDetails> requestedFundsVctm = new ArrayList<>();
        for(FundingDetails details : fundingDetailsList) {
            if(userRepository.getById(details.getUId()).hasRole("PTNT")) {
                if(details.getFApprovedAmount() == null) {
                    requestedFundsPtnt.add(details);
                }
            } else if(userRepository.getById(details.getUId()).hasRole("PUSR")) {
                if(details.getFApprovedAmount() == null) {
                    requestedFundsPusr.add(details);
                }
            } else if(userRepository.getById(details.getUId()).hasRole("VCTM")) {
                if(details.getFApprovedAmount() == null) {
                    requestedFundsVctm.add(details);
                }
            }
        }
        model.addAttribute("fundDetailsPtnt", requestedFundsPtnt);
        model.addAttribute("fundDetailsPusr", requestedFundsPusr);
        model.addAttribute("fundDetailsVctm", requestedFundsVctm);
        return "pmo_approve";
    }

//    @GetMapping("/request/{uId}")
//    public String approveRequest(@PathVariable("uId") Long uId, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) throws Exception {
//        User u = userRepository.getById(uId);
//        User admin = userRepository.findByEmail(loggedMember.getUsername());
//        UsersFunds uf = usersFundsRepository.findByApprovedStatusAndUid(0, uId);
//        PMOFundHistory pmoFundHistory = new PMOFundHistory();
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        if(u.getUCurrentOutstandingAmount() == null) {
//            u.setUCurrentOutstandingAmount("0");
//        }
//        if (admin.getUCurrentOutstandingAmount() == null) {
//            admin.setUCurrentOutstandingAmount("0");
//        }
//        if(u.getUCurrentRequestedAmount() == null) {
//            redirectAttributes.addFlashAttribute("message", u.getUFirstName() + " not raised fund request");
//            return "redirect:/approveFunds";
//        }
//        long amountRequested = Long.parseLong(u.getUCurrentRequestedAmount());
//        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
//        Credentials credentials = web3jClient.getCredentialsFromPrivateKey(admin.getUPrivateKey());
//        web3jClient.transferEthereum(web3j, credentials, u.getUAddress(), amountRequested);
//        if(u.getUCategory().equals("Patient")) {
//            PatientDetails patient = patientDetailsRepository.findPatientDetailsByUIdAndApproveStatus(u.getUId(), "0");
//            patientDetailsRepository.updatePatientDetails("1", patient.getPId());
//        } else if(u.getUCategory().equals("Public Services")) {
//            PublicServiceDetails publicService = publicServiceDetailsRepository.findPublicServiceDetailsByUIdAndApproveStatus(u.getUId(), "0");
//            publicServiceDetailsRepository.updatePublicServiceDetails("1", publicService.getPUId());
//        } else if(u.getUCategory().equals("Affected Victims")) {
//            VictimDetails victim = victimDetailsRepository.findVictimDetailsByUIdAndApproveStatus(u.getUId(), "0");
//            victimDetailsRepository.updateVictimDetails("1", victim.getVId());
//        }
//        userRepository.updateUserApprovedStatus("1", u.getUEmail());
//        u.setUCurrentOutstandingAmount(Long.toString(Long.parseLong(web3jClient.getBalance(web3j, u.getUAddress()))));
//        userRepository.updateUserFunds(u.getUCurrentOutstandingAmount(), null, null, u.getUId());
//        usersFundsRepository.updateUserFunds(1, timestamp, u.getUId(), 0);
//        admin.setUCurrentOutstandingAmount(Long.toString(Long.parseLong(web3jClient.getBalance(web3j, admin.getUAddress()))));
////        userRepository.updateUserFunds(admin.getUCurrentOutstandingAmount(), null, null, admin.getUId());
//        userRepository.updateUserDisableVerification("0", uId);
//        pmoFundHistory.setUId(u.getUId());
//        pmoFundHistory.setPFApprovedAmount(Long.toString(amountRequested));
//        pmoFundHistory.setPFApprovedFundReason(u.getURequestReason());
//        pmoFundHistory.setPFApprovedFundStatus("1");
//        pmoFundHistory.setURequestTimestamp(uf.getURequestTimestamp());
//        pmoFundHistory.setUApprovedTimestamp(timestamp);
//        mailService.sendMail("Fund Approved", u.getURequestReason(), Long.toString(amountRequested), u.getUEmail());
//        pmoFundHistoryRepository.save(pmoFundHistory);
//        return "redirect:/dashboard";
//    }

    @GetMapping("/request/{fId}")
    public String approveRequestAmount(@PathVariable("fId") Long fId, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) throws Exception {

        FundingDetails requestedFundDetails = fundingDetailsRepository.getById(fId);
        long requestedAmount = requestedFundDetails.getFRequestedAmount() / 86508; // INR to ETH

        User requestedUser = userRepository.getById(requestedFundDetails.getUId());
        User pmoUser = userRepository.findByEmail(loggedMember.getUsername());

        userRepository.updateApprovedUserFunds("0", requestedUser.getUId());

        OrganizationDetails organizationDetails = new OrganizationDetails();

        if(requestedUser.getUCategory().equals("Patient")) {
            PatientDetails patient = patientDetailsRepository.findPatientDetailsByUIdAndApproveStatus(requestedUser.getUId(), "0");
            patientDetailsRepository.updatePatientDetails("1", patient.getPId());
            organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(patient.getPHospitalFundRequestId()));
        } else if(requestedUser.getUCategory().equals("Public Services")) {
            PublicServiceDetails publicService = publicServiceDetailsRepository.findPublicServiceDetailsByUIdAndApproveStatus(requestedUser.getUId(), "0");
            publicServiceDetailsRepository.updatePublicServiceDetails("1", publicService.getPUId());
            organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(publicService.getPUOfficialConsentId()));
        } else if(requestedUser.getUCategory().equals("Affected Victims")) {
            VictimDetails victim = victimDetailsRepository.findVictimDetailsByUIdAndApproveStatus(requestedUser.getUId(), "0");
            victimDetailsRepository.updateVictimDetails("1", victim.getVId());
            organizationDetails = organizationDetailsRepository.getByRequestId(Long.parseLong(victim.getVOrganizationId()));
        }

        String senderAddress = userRepository.findByEmail(loggedMember.getUsername()).getUAddress();

        // Send transaction from the approver to the requester
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
        Credentials credentials = web3jClient.getCredentialsFromPrivateKey(pmoUser.getUPrivateKey());
        Long approvedAmount = web3jClient.transferEthereum(web3j, pmoUser.getUAddress(), credentials, requestedFundDetails.getFAccountAddress(), requestedAmount);

        // Updating fund details and user details
        Timestamp timestamp = Timestamp.from(Instant.now());
        fundingDetailsRepository.updateFundingDetails((approvedAmount * 86508), timestamp, fId);
        System.out.println(Long.toString(Long.parseLong(requestedUser.getUCurrentOutstandingAmount()) + (approvedAmount * 86508)));
        userRepository.updateApprovedUserFunds(Long.toString(Long.parseLong(requestedUser.getUCurrentOutstandingAmount()) + (approvedAmount * 86508)), requestedUser.getUId());

        // Sends the approved amount to a dummy account
        List<String> addresses = AddressPrivateKeyMap.convertKeysToList();
        Credentials dummyCredentials = web3jClient.getCredentialsFromPrivateKey(AddressPrivateKeyMap.addressKeyPair.get(requestedFundDetails.getFAccountAddress()));
        web3jClient.transferEthereum(web3j, requestedFundDetails.getFAccountAddress(), dummyCredentials, AddressPrivateKeyMap.DUMMY_ACCOUNT_ADDRESS,  approvedAmount);

        // Remove access to the account from the user
        fundingDetailsRepository.revokeAccountAddress("000", fId);
        userRepository.updateUserDisableVerification("0", requestedUser.getUId());

        // Send a confirmation mail to the requester
        mailService.sendMail("Fund Approved", requestedFundDetails.getFRequestReason(), Long.toString(requestedAmount * 86508), requestedUser.getUEmail());

        // Save to PMO fund history
        PMOFundHistory pmoFundHistory = new PMOFundHistory();
        pmoFundHistory.setUId(requestedUser.getUId());
        pmoFundHistory.setPFApprovedAmount(Long.toString(requestedAmount));
        pmoFundHistory.setPFApprovedFundReason(requestedFundDetails.getFRequestReason());
        pmoFundHistory.setPFApprovedFundStatus("1");
        pmoFundHistory.setURequestTimestamp(requestedFundDetails.getFRequestedTimestamp());
        pmoFundHistory.setUApprovedTimestamp(timestamp);
        pmoFundHistoryRepository.save(pmoFundHistory);

        userRepository.updateUserFunds(Long.toString(Long.parseLong(web3jClient.getBalance(web3j, pmoUser.getUAddress()))), null, null, pmoUser.getUId());
        organizationDetailsRepository.updateIsApprovedOrganizationId("1", organizationDetails.getOId());

        return "redirect:/dashboard";
    }

    @GetMapping("/verification")
    public String getVerificationPage(Model model, @AuthenticationPrincipal UserDetail loggedUser) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        String email = loggedUser.getUsername();
        User user = userRepository.findByEmail(email);
        attributes.put("patient", new PatientDetails());
        attributes.put("pusr", new PublicServiceDetails());
        attributes.put("vctm", new VictimDetails());
        if(user.getUDisableVerification().compareTo("0") == 0) {
            attributes.put("disable", false);
            attributes.put("buttonText", "Submit for verification");
        } else {
            attributes.put("disable", true);
            attributes.put("buttonText", "Submit for verification");
        }
        model.addAllAttributes(attributes);
        return "verification.html";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users.html";
    }

    @GetMapping("/myProfile")
    public String getUserProfile(@AuthenticationPrincipal UserDetail loggedMember, Model model) {
        User user = userRepository.findByEmail(loggedMember.getUsername());
        model.addAttribute("user", user);
        return "userprofile.html";
    }

    @GetMapping("/account/{uId}")
    public String getUpdateProfile(@AuthenticationPrincipal UserDetail loggedUser, Model model) {
        User user = userRepository.findByEmail(loggedUser.getUsername());
        model.addAttribute("user", user);
        return "update_profile.html";
    }

    @GetMapping("/organizationRequests")
    public String getRequestList(Model model, @AuthenticationPrincipal UserDetail loggedUser) {
        User user = userRepository.findByEmail(loggedUser.getUsername());
        if(user.hasRole("PMO_PTNT")) {
            List<OrganizationDetails> requestList = organizationDetailsRepository.findAll();
            model.addAttribute("requests", requestList);
        } else {
            List<OrganizationDetails> requestList = organizationDetailsRepository.getAllByAadhaar(Long.toString(user.getUAadhaar()));
            List<OrganizationDetails> currentRequests = new ArrayList<>();
            System.out.println(requestList.get(0).toString());
            for(OrganizationDetails organizationDetails : requestList) {
                if(organizationDetails.getOIsApproved().equals("0")) {
                    currentRequests.add(organizationDetails);
                }
            }
            model.addAttribute("requests", currentRequests);
        }
        return "organization_request_list.html";
    }

    @GetMapping("/aadhaarDetails")
    public String getAllAadhaarDetails(Model model) {
        List<AadhaarDetails> aadhaarDetailsList = aadhaarDetailsRepository.findAll();
        model.addAttribute("users", aadhaarDetailsList);
        return "aadhaar_list.html";
    }

    @GetMapping("/contribute")
    public String getContributePage(Model model) {
        model.addAttribute("contributor", new ContributorDetails());
        return "contribute.html";
    }

    @GetMapping("/allContributions")
    public String getAllContributions(Model model) {
        model.addAttribute("contributions_lists", contributorDetailsRepository.findAll() );
//        model.addAttribute("contributions_lists_ptnt", contributorDetailsRepository.findAllByContributionFor("PMO_PTNT"));
//        model.addAttribute("contributions_lists_pusr", contributorDetailsRepository.findAllByContributionFor("PMO_PUSR"));
//        model.addAttribute("contributions_lists_vctm", contributorDetailsRepository.findAllByContributionFor("PMO_VCTM"));
        return "all_contributions.html";
    }

    @GetMapping("/allApprovedFunds")
    public String getAllApprovedFunds(Model model) {
        model.addAttribute("users_funds_lists", usersFundsRepository.findAllByApprovedStatus(1));
        return "all_approved_funds.html";
    }
}

