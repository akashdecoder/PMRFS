package com.api.dependencycontainer;

import com.api.repository.*;

public class RepositoryDependencyContainer {
    
    private final UserRepository userRepository;
    private final UsersFundsRepository usersFundsRepository;
    private RolesRepository rolesRepository;
    private final PatientDetailsRepository patientDetailsRepository;
    private final PublicServiceDetailsRepository publicServiceDetailsRepository;
    private final VictimDetailsRepository victimDetailsRepository;
    private final PMOFundHistoryRepository pmoFundHistoryRepository;
    private final AadhaarDetailsRepository aadhaarDetailsRepository;
    private final OrganizationDetailsRepository organizationDetailsRepository;
    private final IncomeDetailsRepository incomeDetailsRepository;
    private final ContributorDetailsRepository contributorDetailsRepository;
    private final FundingDetailsRepository fundingDetailsRepository;

    public RepositoryDependencyContainer(UserRepository userRepository, UsersFundsRepository usersFundsRepository, RolesRepository rolesRepository, PatientDetailsRepository patientDetailsRepository, PublicServiceDetailsRepository publicServiceDetailsRepository, VictimDetailsRepository victimDetailsRepository, PMOFundHistoryRepository pmoFundHistoryRepository, AadhaarDetailsRepository aadhaarDetailsRepository, OrganizationDetailsRepository organizationDetailsRepository, IncomeDetailsRepository incomeDetailsRepository, ContributorDetailsRepository contributorDetailsRepository, FundingDetailsRepository fundingDetailsRepository) {
        this.userRepository = userRepository;
        this.usersFundsRepository = usersFundsRepository;
        this.rolesRepository = rolesRepository;
        this.patientDetailsRepository = patientDetailsRepository;
        this.publicServiceDetailsRepository = publicServiceDetailsRepository;
        this.victimDetailsRepository = victimDetailsRepository;
        this.pmoFundHistoryRepository = pmoFundHistoryRepository;
        this.aadhaarDetailsRepository = aadhaarDetailsRepository;
        this.organizationDetailsRepository = organizationDetailsRepository;
        this.incomeDetailsRepository = incomeDetailsRepository;
        this.contributorDetailsRepository = contributorDetailsRepository;
        this.fundingDetailsRepository = fundingDetailsRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UsersFundsRepository getUsersFundsRepository() {
        return usersFundsRepository;
    }

    public RolesRepository getRolesRepository() {
        return rolesRepository;
    }

    public PatientDetailsRepository getPatientDetailsRepository() {
        return patientDetailsRepository;
    }

    public PublicServiceDetailsRepository getPublicServiceDetailsRepository() {
        return publicServiceDetailsRepository;
    }

    public VictimDetailsRepository getVictimDetailsRepository() {
        return victimDetailsRepository;
    }

    public PMOFundHistoryRepository getPmoFundHistoryRepository() {
        return pmoFundHistoryRepository;
    }

    public AadhaarDetailsRepository getAadhaarDetailsRepository() {
        return aadhaarDetailsRepository;
    }

    public OrganizationDetailsRepository getOrganizationDetailsRepository() {
        return organizationDetailsRepository;
    }

    public IncomeDetailsRepository getIncomeDetailsRepository() {
        return incomeDetailsRepository;
    }

    public ContributorDetailsRepository getContributorDetailsRepository() {
        return contributorDetailsRepository;
    }

    public FundingDetailsRepository getFundingDetailsRepository() {
        return fundingDetailsRepository;
    }
}
