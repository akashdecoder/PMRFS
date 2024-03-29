package com.api;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Web3jClientTest {

    @Autowired
    private static Web3jClient web3jClient;

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    //sender or genesis block
    public static String ADDRESS_PMO = "0x301B71e1dbcD0a3b7C706Cb4cd5DA819bEc295Ad";


    //recipients
    public static List<String> keys = Arrays.asList("f91dfddf21e1a59087666e994dff7fd8191ad500439b4031698f3e7907d5394b", "eb80cc7c99775d7ef1ed83dd031b85daed76724a1607a0dbd2a7f3fd2e47ee2d", "49f68a9f63800983ce88948961941848da034e6c1e7efd095d514637fe77c576", "eb5e692d6e20668619ff4e74eb397da15c1e8035a030730a51648345f64df8fc");


    public static void printWeb3Version(Web3j web3j) {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String web3ClientVersionString = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Web3 client version: " + web3ClientVersionString);
    }

    public static Credentials getCredentialsFromWallet() throws IOException, CipherException {
        return WalletUtils.loadCredentials(
                "passphrase",
                "wallet/path"
        );
    }

    public static Credentials getCredentialsFromPrivateKey(String privateKey) {
        return Credentials.create(privateKey);
    }

    public static void transferEthereum(Web3j web3j, Credentials credentials, String recipient, long value) throws Exception {
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

    public static String getBalance(Web3j web3j, String address) throws ExecutionException, InterruptedException {
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger accountBalance = balance.getBalance();
        BigDecimal value = Convert.fromWei(String.valueOf(accountBalance), Convert.Unit.ETHER);
        return value.toBigInteger().toString();
    }

    public static void main(String[] args) throws Exception {

//        Scanner in = new Scanner(System.in);
//        String senderAddress, receiverAddress, privateKey;
//        int amount;
//        System.out.print("Enter Sender Address: ");
//        senderAddress = in.next();
//        System.out.print("Enter Private Key: ");
//        privateKey = in.next();
//        System.out.print("EnterReceiver Address: ");
//        receiverAddress = in.next();
//        System.out.print("Enter Amount: ");
//        amount = in.nextInt();
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:8545"));

//        Credentials credentials = getCredentialsFromPrivateKey("ff676d1ccd6c56a34b7bc08801d1c1cd9c11a861d0c5b55618b55100a0c1bf5b");
//        transferEthereum(web3j, credentials, receiverAddress, amount);

//        List<String> keys = AddressPrivateKeyMap.convertKeysToList();
//        for(String key : keys) {
//            if(Integer.parseInt(getBalance(web3j, key)) < 3) {
//                for(String sender : keys) {
//                    if(Integer.parseInt(getBalance(web3j, sender)) > 10000000) {
//                        Credentials senderCredential = getCredentialsFromPrivateKey(AddressPrivateKeyMap.addressKeyPair.get(sender));
//                        transferEthereum(web3j, senderCredential, key, 9999999);
//                    }
//                }
//            }
//        }

        Credentials credentials = getCredentialsFromPrivateKey("ff676d1ccd6c56a34b7bc08801d1c1cd9c11a861d0c5b55618b55100a0c1bf5b");
        transferEthereum(web3j, credentials, "0x2325b13775484156f66989D0aCB18d61D7ceb5Dc", 1);

    }
}

