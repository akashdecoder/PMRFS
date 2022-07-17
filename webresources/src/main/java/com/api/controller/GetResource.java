package com.api.controller;


import com.api.Web3jClient;
import com.api.model.*;
import com.api.repository.*;

import com.api.security.UserDetail;
import com.api.services.MailService;
import com.api.utils.AddressPrivateKeyMap;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import java.util.*;
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
    public String showMemberDashboard(@AuthenticationPrincipal UserDetail loggedMember, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        User user = userRepository.findByEmail(loggedMember.getUsername());
        if(user.getUBankAccountNumber() == null) {
            if(user.hasRole("PTNT") || user.hasRole("VCTM") || user.hasRole("PUSR")) {
                model.addAttribute("warning", "Please update your bank account number and IFSC code");
            }
        }
        List<FundingDetails> fundingDetailsList = fundingDetailsRepository.findAll();
        List<FundingDetails> approvedFunds = new ArrayList<>();
        List<FundingDetails> usersFunds = new ArrayList<>();
        for(FundingDetails details : fundingDetailsList) {
            if(details.getFApprovedAmount() != null) {
                approvedFunds.add(details);
            }
        }

        for(FundingDetails userFundDetails : fundingDetailsList) {
            if(Objects.equals(userFundDetails.getUId(), user.getUId())) {
                usersFunds.add(userFundDetails);
            }
        }

        List<ContributorDetails> contributorDetailsList = contributorDetailsRepository.findAllByName(user.getUFirstName()+" "+user.getULastName());

        attributes.put("fundDetails", approvedFunds); // For PMO
        attributes.put("users", usersFunds); // For other roles
        attributes.put("fund", userRepository.findByEmail(loggedMember.getUsername()).getUCurrentOutstandingAmount()); // For other roles
        attributes.put("contributions", contributorDetailsList);
        attributes.put("contributions_lists", contributorDetailsRepository.findAll());
        attributes.put("uId", user.getUId());
        model.addAllAttributes(attributes);
        return "dashboard";
    }

    @GetMapping("/approveFunds")
    public String showRequestedFunds(@AuthenticationPrincipal UserDetail loggedUser, Model model, RedirectAttributes redirectAttributes) {
        if(loggedUser.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to conitue");
            return "redirect:/login";
        }
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

    @GetMapping("/request/{fId}")
    public String approveRequestAmount(@PathVariable("fId") Long fId, @AuthenticationPrincipal UserDetail loggedMember) throws Exception {

        FundingDetails requestedFundDetails = fundingDetailsRepository.getById(fId);
        long requestedAmount = requestedFundDetails.getFRequestedAmount() / 86508; // INR to ETH
        long approvedAmount = 0;
        String transactionHash = "";

        User requestedUser = userRepository.getById(requestedFundDetails.getUId());
        User pmoUser = userRepository.findByEmail(loggedMember.getUsername());

        OrganizationDetails organizationDetails = new OrganizationDetails();

        userRepository.updateApprovedUserFunds("0", requestedUser.getUId());

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

        // Send transaction from the approver to the requester
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
        Credentials credentials = web3jClient.getCredentialsFromPrivateKey(pmoUser.getUPrivateKey());
        Pair<Long, String> transactionDetail = web3jClient.transferEthereum(web3j, pmoUser.getUAddress(), credentials, requestedFundDetails.getFAccountAddress(), requestedAmount);
        approvedAmount = transactionDetail.getFirst();
        transactionHash = transactionDetail.getSecond();

        // Updating fund details and user details
        Timestamp timestamp = Timestamp.from(Instant.now());
        fundingDetailsRepository.updateFundingDetails((approvedAmount * 86508), timestamp, transactionHash, requestedFundDetails.getFAccountAddress(), fId);
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
        boolean flag = false;
        String buttonText = "";
        String email = loggedUser.getUsername();

        Map<String, Object> attributes = new HashMap<>();

        User user = userRepository.findByEmail(email);

        if(user.getUBankAccountNumber() == null) {
            flag = true;
            buttonText = "Profile not updated";
        } else if(user.getUDisableVerification().compareTo("0") == 0) {
            buttonText = "Submit for verification";

        } else if(user.getUDisableVerification().compareTo("1") == 0){
            flag = true;
            buttonText = "Current Fund Request Pending";
        }

        attributes.put("patient", new PatientDetails());
        attributes.put("pusr", new PublicServiceDetails());
        attributes.put("vctm", new VictimDetails());
        attributes.put("disable", flag);
        attributes.put("buttonText", buttonText);
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
    public String getUserProfile(@AuthenticationPrincipal UserDetail loggedMember, Model model, RedirectAttributes redirectAttributes) {
        if(loggedMember.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to conitue");
            return "redirect:/login";
        }
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
    public String getRequestList(Model model, @AuthenticationPrincipal UserDetail loggedUser, RedirectAttributes redirectAttributes) {
        if(loggedUser.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to continue");
            return "redirect:/login";
        }
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
    public String getContributePage(Model model, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) {
        if(loggedMember.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to conitue");
            return "redirect:/login";
        }
        model.addAttribute("contributor", new ContributorDetails());
        return "contribute.html";
    }

    @GetMapping("/allContributions")
    public String getAllContributions(Model model, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) {
        if(loggedMember.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to conitue");
            return "redirect:/login";
        }
        model.addAttribute("contributions_lists", contributorDetailsRepository.findAll() );
//        model.addAttribute("contributions_lists_ptnt", contributorDetailsRepository.findAllByContributionFor("PMO_PTNT"));
//        model.addAttribute("contributions_lists_pusr", contributorDetailsRepository.findAllByContributionFor("PMO_PUSR"));
//        model.addAttribute("contributions_lists_vctm", contributorDetailsRepository.findAllByContributionFor("PMO_VCTM"));
        return "all_contributions.html";
    }

    @GetMapping("/allApprovedFunds")
    public String getAllApprovedFunds(Model model, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) {
        if(loggedMember.getUsername() == null) {
            redirectAttributes.addFlashAttribute("warning", "Please login to conitue");
            return "redirect:/login";
        }
        model.addAttribute("fundDetails", fundingDetailsRepository.findAll());
        return "all_approved_funds.html";
    }

    @GetMapping("/forgotPassword")
    public String showForgotPassword() {
        return "forgot_password";
    }

    @GetMapping("/resetPassword")
    public String showResetPassword(@Param(value = "token") String token, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findUserByResetPasswordToken(token);
        model.addAttribute("token", token);
        if(user == null) {
            redirectAttributes.addFlashAttribute("warning", "Invalid token");
            return "redirect:/resetPassword";
        }
        redirectAttributes.addFlashAttribute("message", "Password is reset, please login to continue.");
        return "redirect:/login";
    }
}

