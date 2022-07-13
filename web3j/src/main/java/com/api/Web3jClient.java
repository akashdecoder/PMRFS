package com.api;

import com.api.model.PatientDetails;
import com.api.model.PublicServiceDetails;
import com.api.model.VictimDetails;
import com.api.validationcontractmodel.PmCare;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Service
public class Web3jClient {

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    public Credentials getCredentialsFromPrivateKey(String privateKey) {
        return Credentials.create(privateKey);
    }

    public Long transferEthereum(Web3j web3j, String sender, Credentials credentials, String recipient, long value) throws Exception {

        if(value > Long.parseLong(getBalance(web3j, sender))) {
            value = Long.parseLong(getBalance(web3j,sender)) - 1;
        }

        TransactionManager transactionManager = new RawTransactionManager(
                web3j,
                credentials
        );
        Transfer transfer = new Transfer(web3j, transactionManager);
        TransactionReceipt transactionReceipt = transfer.sendFunds(
                recipient,
                BigDecimal.valueOf(value),
                Convert.Unit.ETHER,
                GAS_PRICE,
                GAS_LIMIT
        ).send();
        System.out.print("Transaction = " + transactionReceipt.getTransactionHash());
        return value;
    }

    public String getBalance(Web3j web3j, String address) throws ExecutionException, InterruptedException {
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger accountBalance = balance.getBalance();
        BigDecimal value = Convert.fromWei(String.valueOf(accountBalance), Convert.Unit.ETHER);
        return value.toBigInteger().toString();
    }

    public String deployContract(Web3j web3j, Credentials credentials) throws Exception {
        return PmCare.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT)
                .send()
                .getContractAddress();
    }

    public PmCare loadContract(String contractAddress, Web3j web3j, Credentials credentials) {
        return PmCare.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
    }

    public Boolean isValidated(PatientDetails patientDetails, PublicServiceDetails publicServiceDetails, VictimDetails victimDetails, boolean isValidRequest) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));
        Credentials credentials = getCredentialsFromPrivateKey("ffcb1bfdb084fa4ee971b6dee58f82957ec6045c43b068f2606111f2638ef716");
        String contractAddress = deployContract(web3j, credentials);
        PmCare pmCare = loadContract(contractAddress, web3j, credentials);
        if(patientDetails != null) {
            return pmCare.Patient_Fund_Validation(BigInteger.valueOf(Long.parseLong(patientDetails.getPFundNeed())), patientDetails.getPCaseType(), isValidRequest).send();
        } else if(publicServiceDetails != null) {
            return pmCare.Public_Service_Fund_Validation(BigInteger.valueOf(Long.parseLong(publicServiceDetails.getPUFundNeed())), publicServiceDetails.getPUServiceType(), isValidRequest).send();
        } else if(victimDetails != null) {
            return pmCare.Victim_Fund_Validation(BigInteger.valueOf(Long.parseLong(victimDetails.getVFundNeed())), victimDetails.getVCaseType(), isValidRequest).send();
        }
        return false;
    }


}

