package com.api.utils;

import com.api.model.PatientDetails;
import com.api.model.PublicServiceDetails;
import com.api.model.User;
import com.api.model.VictimDetails;
import com.api.repository.PatientDetailsRepository;
import com.api.repository.PublicServiceDetailsRepository;
import com.api.repository.UserRepository;
import com.api.repository.VictimDetailsRepository;
import com.api.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailScheduler {

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PublicServiceDetailsRepository publicServiceDetailsRepository;

    @Autowired
    private VictimDetailsRepository victimDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

//    @Scheduled(cron = "0 0 12 * * ?")
    @Scheduled(fixedRate = 10000)
    public void sendMail() {
        User pmoPtnt = userRepository.findUserByCategory("PMO_PTNT");
        User pmoPusr = userRepository.findUserByCategory("PMO_PUSR");
        User pmoVctm = userRepository.findUserByCategory("PMO_VCTM");
        List<User> userPatients = userRepository.findByCategory("Patient");
        List<User> userPublicServices = userRepository.findByCategory("Public Services");
        List<User> userVictims = userRepository.findByCategory("Affected Victims");
        for(User user : userPatients) {
            if(user.getUApproveStatus().equals("0")) {
                PatientDetails patient = patientDetailsRepository.findPatientByApprovedStatus("0");
                mailService.sendMail("Fund Request Verification", null, patient.getPCaseType(), pmoPtnt.getUEmail());
            }
        }
        for(User user : userPublicServices) {
            if(user.getUApproveStatus().equals("0")) {
                PublicServiceDetails publicService = publicServiceDetailsRepository.findPublicServiceByApprovedStatus("0");
                mailService.sendMail("Fund Request Verification", null, publicService.getPUServiceType(), pmoPusr.getUEmail());
            }
        }
        for(User user : userVictims) {
            if(user.getUApproveStatus().equals("0")) {
                VictimDetails victim = victimDetailsRepository.findVictimByApprovedStatus("0");
                mailService.sendMail("Fund Request Verification", null, victim.getVCaseType(), pmoVctm.getUEmail());
            }
        }
    }
}
