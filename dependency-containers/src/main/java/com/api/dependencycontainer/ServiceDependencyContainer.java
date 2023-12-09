package com.api.dependencycontainer;

import com.api.Web3jClient;
import com.api.services.MailService;
import com.api.services.RandomNumberGeneratorService;
import com.api.services.ResetPasswordService;
import com.api.services.ValidationService;

public class ServiceDependencyContainer {
    private final Web3jClient web3jClient;
    private final MailService mailService;
    private  final ValidationService validationService;
    private final ResetPasswordService resetPasswordService;
    private RandomNumberGeneratorService randomNumberGeneratorService;

    public ServiceDependencyContainer(Web3jClient web3jClient, MailService mailService, ValidationService validationService, ResetPasswordService resetPasswordService, RandomNumberGeneratorService randomNumberGeneratorService) {
        this.web3jClient = web3jClient;
        this.mailService = mailService;
        this.validationService = validationService;
        this.resetPasswordService = resetPasswordService;
        this.randomNumberGeneratorService = randomNumberGeneratorService;
    }

    public Web3jClient getWeb3jClient() {
        return web3jClient;
    }

    public MailService getMailService() {
        return mailService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public ResetPasswordService getResetPasswordService() {
        return resetPasswordService;
    }

    public RandomNumberGeneratorService getRandomNumberGeneratorService() {
        return randomNumberGeneratorService;
    }
}
