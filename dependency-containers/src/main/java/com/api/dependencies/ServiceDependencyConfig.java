package com.api.dependencies;

import com.api.Web3jClient;
import com.api.dependencycontainer.ServiceDependencyContainer;
import com.api.services.MailService;
import com.api.services.RandomNumberGeneratorService;
import com.api.services.ResetPasswordService;
import com.api.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceDependencyConfig {
    @Autowired
    private Web3jClient web3jClient;
    @Autowired
    private MailService mailService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ResetPasswordService resetPasswordService;
    @Autowired
    private RandomNumberGeneratorService randomNumberGeneratorService;

    @Bean
    public ServiceDependencyContainer serviceDependencyContainer() {
        return new ServiceDependencyContainer(web3jClient, mailService, validationService, resetPasswordService, randomNumberGeneratorService);
    }
}
