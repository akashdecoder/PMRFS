package com.api.controller;

import com.api.MailService;
import com.api.Web3jClient;
import com.api.model.*;
import com.api.UserDetail;
import com.api.repository.*;

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
        User admin = userRepository.findByEmail(loggedMember.getUsername());
        User u = userRepository.findByEmail(loggedMember.getUsername());
        List<UsersFunds> usersFunds = usersFundsRepository.findAllByUId(u.getUId());
        List<PMOFundHistory> pmFunds = pmoFundHistoryRepository.findAllApprovedStatus("1");
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
        admin.setUCurrentOutstandingAmount(web3jClient.getBalance(web3j, admin.getUAddress()));
        List<ContributorDetails> contributorDetailsList = contributorDetailsRepository.findAllByName(u.getUFirstName()+" "+u.getULastName());
        attributes.put("pmFunds", pmFunds); // For PMO
        attributes.put("users", usersFunds); // For other roles
        attributes.put("fund", userRepository.findByEmail(loggedMember.getUsername()).getUCurrentOutstandingAmount()); // For other roles
        attributes.put("contributions", contributorDetailsList);
        attributes.put("contributions_lists", contributorDetailsRepository.findAll());
        model.addAllAttributes(attributes);
        return "dashboard";
    }

    @GetMapping("/approveFunds")
    public String showRequestedFunds(@AuthenticationPrincipal UserDetail loggedUser, Model model) {
        User admin = userRepository.findByEmail(loggedUser.getUsername());
        List<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<User>();
        for(User user : users) {
            if(user.hasRole("PMO_PTNT")) {
                continue;
            } else if(user.hasRole("PMO_PUSR")) {
                continue;
            } else if(user.hasRole("PMO_VCTM")) {
                continue;
            } else if(user.hasRole("CONTRIBUTOR")) {
                continue;
            }
            System.out.println(user.getUEmail());
            userList.add(user);
        }
        model.addAttribute("admin", userList);
        return "pmo_approve";
    }

    @GetMapping("/request/{uId}")
    public String approveRequest(@PathVariable("uId") Long uId, @AuthenticationPrincipal UserDetail loggedMember, RedirectAttributes redirectAttributes) throws Exception {
        User u = userRepository.getById(uId);
        User admin = userRepository.findByEmail(loggedMember.getUsername());
        PMOFundHistory pmoFundHistory = new PMOFundHistory();
        if(u.getUCurrentOutstandingAmount() == null) {
            u.setUCurrentOutstandingAmount("0");
        }
        if (admin.getUCurrentOutstandingAmount() == null) {
            admin.setUCurrentOutstandingAmount("0");
        }
        if(u.getUCurrentRequestedAmount() == null) {
            redirectAttributes.addFlashAttribute("message", u.getUFirstName() + " not raised fund request");
            return "redirect:/approveFunds";
        }
        long amountRequested = Long.parseLong(u.getUCurrentRequestedAmount());
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
        Credentials credentials = web3jClient.getCredentialsFromPrivateKey(admin.getUPrivateKey());
        web3jClient.transferEthereum(web3j, credentials, u.getUAddress(), amountRequested);
        userRepository.updateUserApprovedStatus("1", u.getUEmail());
        u.setUCurrentOutstandingAmount(Long.toString(Long.parseLong(web3jClient.getBalance(web3j, u.getUAddress()))));
        userRepository.updateUserFunds(u.getUCurrentOutstandingAmount(), null, null, u.getUId());
        usersFundsRepository.updateUserFunds(1, u.getUId());
        admin.setUCurrentOutstandingAmount(Long.toString(Long.parseLong(web3jClient.getBalance(web3j, admin.getUAddress()))));
        userRepository.updateUserFunds(admin.getUCurrentOutstandingAmount(), null, null, admin.getUId());
        userRepository.updateUserDisableVerification("0", uId);
        pmoFundHistory.setUId(u.getUId());
        pmoFundHistory.setPFApprovedAmount(Long.toString(amountRequested));
        pmoFundHistory.setPFApprovedFundReason(u.getURequestReason());
        pmoFundHistory.setPFApprovedFundStatus("1");
        mailService.sendMail("Fund Approved", u.getURequestReason(), Long.toString(amountRequested), u.getUEmail());
        pmoFundHistoryRepository.save(pmoFundHistory);
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
            model.addAttribute("requests", requestList);
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
        model.addAttribute("contribution_lists", contributorDetailsRepository);
//        model.addAttribute("contributions_lists_ptnt", contributorDetailsRepository.findAllByContributionFor("PMO_PTNT"));
//        model.addAttribute("contributions_lists_pusr", contributorDetailsRepository.findAllByContributionFor("PMO_PUSR"));
//        model.addAttribute("contributions_lists_vctm", contributorDetailsRepository.findAllByContributionFor("PMO_VCTM"));
        return "all_contributions.html";
    }

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
}
