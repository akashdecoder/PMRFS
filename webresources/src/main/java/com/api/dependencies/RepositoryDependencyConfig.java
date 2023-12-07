package com.api.dependencies;

import com.api.dependencycontainer.RepositoryDependencyContainer;
import com.api.repository.*;
import com.api.services.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryDependencyConfig {
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
    private ResetPasswordService resetPasswordService;

    @Bean
    public RepositoryDependencyContainer dependencyContainer() {
        return new RepositoryDependencyContainer(
                userRepository,
                usersFundsRepository,
                rolesRepository,
                patientDetailsRepository,
                publicServiceDetailsRepository,
                victimDetailsRepository,
                pmoFundHistoryRepository,
                aadhaarDetailsRepository,
                organizationDetailsRepository,
                incomeDetailsRepository,
                contributorDetailsRepository,
                fundingDetailsRepository
        );
    }
}
