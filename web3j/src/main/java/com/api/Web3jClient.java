package com.api;

import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Service
public class Web3jClient {

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    public Web3j connectToEthereumNetwork(String url) throws IOException {
        if(url != null) {
            Web3j web3j = Web3j.build(new HttpService(url));
            if(web3j.netVersion().send().getNetVersion() != null) {
                return web3j;
            }
        }
        return null;
    }

    public void printWeb3Version(Web3j web3j) {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String web3ClientVersionString = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Web3 client version: " + web3ClientVersionString);
    }

    public Credentials getCredentialsFromWallet() throws IOException, CipherException {
        return WalletUtils.loadCredentials(
                "passphrase",
                "wallet/path"
        );
    }

    public Credentials getCredentialsFromPrivateKey(String privateKey) {
        return Credentials.create(privateKey);
    }

    public void transferEthereum(Web3j web3j, Credentials credentials, String recipient, long value) throws Exception {
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
    }

    public String getBalance(Web3j web3j, String address) throws ExecutionException, InterruptedException {
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger accountBalance = balance.getBalance();
        BigDecimal value = Convert.fromWei(String.valueOf(accountBalance), Convert.Unit.ETHER);
        return value.toBigInteger().toString();
    }

}

