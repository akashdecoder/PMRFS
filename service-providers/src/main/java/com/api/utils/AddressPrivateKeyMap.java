package com.api.utils;

import com.api.model.FundingDetails;
import com.api.model.User;
import kotlin.Pair;

import java.util.*;

public class AddressPrivateKeyMap {

    public static Map<String, String> addressKeyPair = new HashMap<String, String>(){{
        put("0x58dfA52f628718f271833a661867109632Fcb68E", "a5b198838ab09ca28a02582c66d250d9f4930aa204a702a89c333889431b3a04");
        put("0xcBfF30484f75eE81F067fC8e263e26f810358c75", "2a55a82acf61edf23ba10caa9a20538c9cfd49cb32fdf3ab44a19fff6b4e5947");
        put("0x1C3d4712c7fD24B6a4B6EE4d7602B285F5D04E20", "3126ae7d962f4f68d935284a0f64bedb33984507e7bbc0fa8f9343faef5b62e2");
        put("0xCb9D009C988d75832d2C300d9de83789d5c9Ff2c", "fd55cab4a23b5466996b50bfa6f7dccb433ce53ce745874623fab39e0b4cc028");
        put("0x8E7B0C7cAA9ACc776d5e9F5e186e87CAD6467757", "0baada89ce70b8b0434ce636bf3a3cdccf6b7a72ff9e1c48a91c229645f4cf7e");
        put("0x764B4F51D00ca58AbF8F3a1775af94Ea52942985", "f412f0fb0057f134037ba0bc3597c106cae5a634015380fe39febad4d89c184f");
        put("0x8FAe3371a6157Df67a6BE76DAd0f795F033C51a4", "93f31d0d81eb7a12755133a6f277b7afa950adf695ab812b595198615817a4cb");
        put("0xDAEa16cAf3D68812e05DE9c394832780276A32a9", "58704663678540e6acdaa1c8a52628abdce4def223bd29a9a3583410d1365614");
        put("0x8453E38822DD2C444f36563521aE6d016Ff7EF89", "04566c3c9eeaf7993e2e36e8704776a7c3263546c1771e4dfbd6c2ded476de5e");
        put("0x9088C0b574F56D1787E3E1696B8f05888F794f5E", "9e95f7ddddbf1ed094224bd17f2b85e12b8ab19b6c748d1b33cb95739a82f9ba");
        put("0xF9A0376f0ab87CCf102c3111F55A4ABFD0e0c84C", "e6143213914a83f04546f11db99f6dace74b1a668199354d27f40dc791ddd8a9");
        put("0x396A83934a4800Ce3b05334450E5120b748DB13a", "3ae3938cd7ba96176125303fd9942778c57bdd9fe5af8b94f98ce5a2e5f36ce3");
        put("0x5C1efd91b0AE3103C6db594437794F03DC6d4f95", "ab9f42445ca7bd3085b5148dfa17a71d0b14faf6065a682f710150e07f1cb76a");
        put("0xd01D858D71760816c903446616bD70477aA3C327", "e8a5b636ceff0af1f0fcc7dca8afa7642d16b4d0e8251c91980547cd9ece76f4");
        put("0xAaD1Ab6421cBcc06014f4D1A7b94b78Fba6013Bc", "af0de77de804aa45735bf63bb59c57687978ba9260a53963a336d596d8584c25");
        put("0x668Dd03ce1ED9b05ef3E390175f0446B25277e8E", "f7944a7c9e145ba75dd8b3bf9b157de18c03c7149cfd33da6cb2f8e649916705");
        put("0x439d877c6119B06aaD590737b782593bbF80E623", "7b10758d69724ac77973b0e8a3078607a7327e9de86696b22015e033a7139cdf");
        put("0x5d28734064629D20012e642209b6daD87Ee99fe4", "bb3b83cf12a4560e2d2b7dd83902bd0b151a250677a6fc6c19b35f5b1f8adf62");
        put("0x62d1d3C5FC0FB1a7f1b63768A5e0677858CC9F1E", "63e1abcb506ef4e7627774ade41cd9f8ba4c58c1fb1164c07375b28ee377ba6a");
        put("0x6930a429731038F3047DE0FB490A26Ab1C1b27cB", "e364d24f3ca2abfc5be19a7f69f37bfbef15a4dc88aa6c8c71e4f92200d97d7d");
        put("0xB35Caf151E5d0B6129846a50d13586FDB964754e", "c69d21e8a57d6d07fe387b19ebabecfdb82a9136553b20b82bffb3c3cb30776c");
        put("0x78963bC7B127598DcD1055c0c63dc24EFBbC1C65", "9632c2b0c74323e9c22d25cae75710cb85fdd23d37946c18a427e7ef85769473");
        put("0x21EA56B221F9200E0f0991D967a8427ee72586BD", "6ddf6d48073b58d3d616507f1446b7a821f4fdf342d691ee5467bee397f54206");
        put("0xA32Dfd8cbAf963c2B0fDf77e78d283EfeB1004A6", "fbb568289abeff57ed662cf2dc67a2176f37c131b543c5099268852ff5475309");
        put("0x6027579F854011706C865049059E02C879233cE6", "c7ec9ccf48c1884d001cdf6fb2cc0be54d6208b3a42f6179e820c2ab7f11c849");
        put("0x915a0dFe1F1eEB7edC63F53b1790e66Ce6BBc5a3", "27fa76e8f7fa079a33a0a87d421dea017b744206494f9ff54410e29569bee255");
        put("0x61eDe7774E40d8732e43193145C010196986833D", "814afdf35e7126c504aacf558282136821aa92322fa3158eeebc1acb83eac46a");
        put("0xD3bB649Fc6A4b70A01501aaaB1b51093E99aC50F", "7b3389aa96846f595cc404339605393ea53c702eaf0b20afcb695906ee3b4ced\n");
        put("0x47f84Da924F6a9fa0e3961a82363Ef312678CaeA", "b6a2230ffca91813b1d9b0caefa8d2c33d686e417f26c0e711ca4862aec6fe8c");
        put("0xd1Ad18EfE725b8ce88FE343450968F1Fa1B9a29D", "e7b6a030f3bb0ce15fb98f416d9dc932ad24a0a31aa3fb5611eb0ff5f9c74105");
        put("0x7c3FF02020a144e4415046aA75C3506D0EBFDb87", "c661b60de2cbc400cb0ecb89a3b8a02eded438bd95277c90ac569868a7caa20c");
        put("0xCd876B2bE3cD50c2A341630b0a33B9Ea7f283375", "0d267a71e87eb3624ff6bf804e8ab74f4e571c93b977534bef9b7c631a0c8050");
        put("0xA5B06775307d141b7f20cc48e2d28aAb20b095Eb", "0f11fcf6291678b983534ddaf08d0df5c506e7e378ca7639c0e23708e5821199");
        put("0xE8Ae0F667deA94d593e1618C1D7B72bC02b2aD94", "a814513ffaab44d34070ec09be4b87eae1dc892bd3113f4abe5501198c7b74b1");
        put("0xf234BF8Fc0c456d426EF3Cb7c307eF83252CbC4b", "780a6ead4b527f5aabd42e32c882a2313298f2ecf5d577f9c200de79b8890491");
        put("0x8D480169Cbde25349aeB973482E149eF63D47BB2", "12685ec90bae295f036e468a17a14ba3d17265e81aa4c767ae17b7f1c634315e");
        put("0xE8342fa7735d0F37Ea2d335f01e0A238AA88858F", "6c099c7d9d8f27e53c7346f9d6c5bf2d57ce8ebaabbfce6c0c902fb091a43e8d");
        put("0xC8e3Bc0A5c2e25aFa45668Ee275Da0Eb51B1aDb1", "fde8e90774c1a3d27f05fb46ff2a5df0f84662ff07d621913e3ab658808023e0");
        put("0xE67981de9Cf4904c900eB893d82470C69018ba3A", "a275b6ccd26f00c5f5ea3f9e2d4f6b659acee48f366744b68751282ce7f86fdc");
        put("0xf9f9525e50Ff077Cb2E97f7513803B06F43eD371", "692c787d0dbbfd1b939c2e990a4f1b5d7ae500bda2971c10dab6e7854c55a537");
        put("0x91827a878a44024e0d280FB93c9505BA367DC758", "20a82ac4cbbaf144dca14bd76b36809764fa7f6d2088426c9a941339d9a7cae3");
        put("0x76946c4B7069123Ab155164E755535b9FB6952BD", "34b0e6d19026ff14ecc738bdb41abcb722e4fccec4b35caa9fe3acc4f2ce2f5f");
        put("0x48f3081fa710316681437338D36b5e504e22Bf44", "54d27c64e2a36c5ebc8fd9ba7e5ac1bb9ccb69f4933600a2e8fe279acd243921");
        put("0x9C7E9634082F85E92d4A3FD9917a07Ef99769E8D", "07c2aecdfe2f2d6cd0db8775fde4f6446cb902b9afbd4054b48ad4e6c523af31");
        put("0x914686a4161F0e1e463a3459759547A9A9875908", "e86c750b15ff86106b97d7f26707d54a0b57987bd8ad5591ac64b4073e81580c");
        put("0xcFde2D6B475FaB6EBd963802396Cdf0B895aC956", "03bd364fc7fa456463b01385f6a758a126deab54a747287624c1c07b8b939421");
        put("0x21C1C5488c74793110CC8c841Cb739ca2c657E84", "7e8a1bb4c4a41bb700cb9e369962cda919724f858e52d2eaba43ee3c45b2936c");
        put("0x54800A35D1D4241593A23735cc15ef5D5B4dc9Be", "237e4b77b1950ae3830b8ff9e827d5c0bc51818a9233d59b77de2f7465f1ed6a");
        put("0x6fD279227f92b3491e6ec62a3D18F71573C47fd6", "5c41e45b6986188edde6c612f926d4a7d9ed06c65d147c124c91164665f8aba2");
        put("0x748f31A5810E1E041C3b815168D376EfA094B271", "0914c31f3c19b64f840c82176c4117a8e8814d8badfdcb13ce5a7fee407096ee");
        put("0x6Be512D401219B3EB50628A39ba94ba2ff34b04d", "4d562a4549c8f9ddb02ab78abf1887fd273472c2269cb46ffbfadfc6fd94dab5");
        put("0x7A25199107CB71Ed737182d1Aa0036eD23A21A10", "85ed81bb84d3d98b12ad06e8d773b2ff67210c140ad6da7280006f9a038b1049");
        put("0x2c0e0aAC775474F4f1a131AC116471Fc75aF3f40", "898b1124d0a8815919c2260ea9e4545ccc906b8bbd7c3bfebab47b2336b4cc28");
        put("0xDe88c672960D6C03779B731A4ff7B4Af0DC20F88", "1ad318a3367574af95ca5016df0266f4bd685331d43a32bfba51c3ba5ce1a9b7");
        put("0x142c1d1725C56BC03c5D9Ac2b70aBB550d3286b3", "9adddb055dac96c407df778f34e8459226adf1b1aecb993b63f007bd056f01ae");
        put("0xfd6F49faa4fBC9DcF1a6E0E948721F9AB9BB080D", "78b0d12397c47006e8a2ed851de342d47911af616eff54eeb75f38a36c0c7982");
        put("0x8aB2c3D5EF419A87d3940DAE5fC438B0b8EC08e3", "9910de26ea684a1d1dd5781a180ef156696947ba1787192e64b4bbd650ae3936");
        put("0xB79677eFBcB3540A9B125457A793dA6B050dF9ac", "ed8e568c3d656e62726736a0b470c729045a34019612befb12b7732c35d1bd1d");
        put("0x25A4150CA01B397c635b82b5b1D190b0A1bDA6D2", "d11bdaa1251af376c131c91e724754e4457c8cf41a63d4e017745fc1c39d47cf");
        put("0xd7Cc417d29Edea5167A9bB8C7943b2B78aaD7cae", "2427dc6e53a8a56587a9b9e5d6b92cd95ad2ffec267a5488f986d70a22b5f794");
        put("0x39474AC733C8dcAB91a5dC26536EEB1fcD0d1a14", "e8ac80a90a4380cbac6bb427e97999e1627550fb1c85c55b26029f42a544802e");
        put("0x13E850cdEd9d35c06A538dEF563b19C0039f0455", "d53f37d43e4860a9700c2af934bd56e96518a76233a2f60802f3de643cd9f3cf");
        put("0x46b1Db63a6D0a3814101a444747EC5939EF4205A", "d16d6999bc3b0b930c9e4970d717d79669943a5a98c796aa44ab3f147b08602e");
        put("0x0B6c20AAC70FBDCf197335a68BF98ac33830B74f", "b8af23ad16b7f739cdd179a1d1407ebfc7c0c0235797448cece109226757f48e");
        put("0x9757E8176448E1Fe951214FB82c05A02dd6a1f7c", "f40ac1db9fc6d8071a2dcf71e68593a96e2041b3e3779a6dd90e8e69d6952777");
        put("0x723988B5BA67B5Cd582f76D55b2A771034eE819B", "77e6c6b02288f2c985e906c7ebd029f461d1f6bfc78d1220b112e4de1a483714");
        put("0x8caDa2c4886480580eB4736cDf9D525914a87762", "87350b20b255febd0c4942ac43838fe285d26ef6a200b9581bc66c541f191dad");
        put("0x04c98728CEdB82F723FA511C72B451ad5ae5A2Dd", "8e04ac437ae9bbeaca6075b9eb30a7654acceddcc68eb705ed695e9be432c88f");
        put("0x4B26E145C5330d0EEA6BC328eb773a42Cbe1E8AB", "3c4df84e22cd197449d1b18caf620d4a873ff584efdd86619f4f05087810e90c");
        put("0x76F7acE5168b329f234Bbf905d5439F5633e4Ed5", "d886a6a8b5a265ddf2c78c78a88835bf435b4f4f2a86886939b3d4014a61d732");
        put("0x4311FAA5B6674075E660621850c773df8645ea5D", "d47b361c510d69210b2ac95d76f4aecf36656035866fc5279e2325a148f155f2");
        put("0x3d5209738dF452fF0bc6d5E625A65F893f10F2d9", "063e18f095c20ac93d82ad3ce7cd94ed27b0bb850579c984ff6f960007a282a4");
        put("0x5e49E9fBF4D5652a1482AC3Ae93dfD33b36dA4f9", "7ded14a4c9fbf536f5fcf85611fe9adbb1babaea85d0c981992b417c0e895bf6");
        put("0xEb072D8F1A6AA81EfE69aEa50bea24C9c8b8c120", "dc420c9001dbf8a947ab1c06c4c93d73b7ded2c50466e0facb9914829e47be21");
        put("0x29EB5f3334626B57Ac91998a34FC424Ad7D0B0fD", "c322ed4daaa832e2a219a236c34e4f2da72758c5c41fa7096658729246a16a06");
        put("0x5517D89B902b20d67178532DB0c01Cdbf94fAe72", "1976ab1571ee3ddfdff3b858c7ebe9e130d2948a513175dd905d4f9f73810b3a");
        put("0xED5535003AC48fCa1eec80aE47D4E7071c465DEB", "4a72ad662ed14c0a04179f9277ad9eeff5bdc3ca999a9963235d4b3538fbb5ef");
        put("0x2325b13775484156f66989D0aCB18d61D7ceb5Dc", "308e716e057eb91e9abb3c80539b5a5308bdd8229a01e199c6b9234eca33f069");
        put("0x66CFefE041FE7a84e8DacB073b5E46eCE3e062Ee", "51a93bd04f271320bcb8b1f056519684ff438e18570bee3fe4c9a4eb10cbeabb");
        put("0x7691a673b1d50FC1996e821C116D60593F0B41Fe", "3b5f67e4caf9a04a5579ce7986b10a62d8839b0b67ba2b8524191a1021b67f7c");
        put("0x53E34c01b126056Cc936dc1E959ed757855e8E22", "a8a0c65c0ca17dc79ddf525ba2b03266150ea5c66bc44ef2f6c968d5b909a363");
        put("0x755f26eBa32FD6D10E923022fB9bE0C5Ff1c9592", "047d8645bfb6bcb55b0e7a966a0bbdf505321b4c0032ba8b2b7612882531c294");
        put("0x18Da0ED9312D860Af006449841B45e33156b28f5", "ff676d1ccd6c56a34b7bc08801d1c1cd9c11a861d0c5b55618b55100a0c1bf5b");
        put("0x45fB3AbC3FE45c08e7470cDd3aAD180ab0B2EaE8", "9bb2fae8d29c3483e597e91770b94a56f3dc3e39077e97d24c8e36fbee680209");
        put("0x5f1EFca0D3119A795d8b285846aA8f77F90b1822", "b97ef373b85883becc0766e7ddacadb5b7718489a4edf126ba3e204662aecc03");
        put("0xc295da7e8cC0037a3c182865c128AbA4223a68e1", "26e89f4967646638ade0259444b7065da585bc31de56146cfdd67f0aec9070d2");
        put("0xB1a84C60a686ac9A4E8662f6AB926BAAA93692E4", "bf09e5d1ec19c30fdf994540549ec9cf841f9ea004e2f061d8989e17cb7a3696");
        put("0x76AA4181bF5DDE35c0Fb07A6a86184aa96B777b7", "411c5c2761ff9d1c729fc8b7c9fa83923515af4875f96a0f60147fad0de39cc6");
        put("0x5f34cA94D76988e8f30752355dA96A3a6e04cC40", "0e9b2722168eada2c4f500189b2aff7d1c6b129c8a5021d426563a74af70aada");
        put("0xc809ccD68c537387b225CcFd9282b28526073070", "b27d0e832f2d8e8695082ac8720303bf4899f496011e60c45b7cb958185396e1");
        put("0xac626C6ce9a06804E52bc501ba74E79F87203C17", "88f86e0fa5a4ed10d3c5105ded0978850ff2326b7b586d885f5707e94e959bc0");
        put("0x44F594eaEd042916892565E221c005E86EE007d0", "2d09f0d4ffc596b336ca9c1970d4f3cd3dfdceac0f2b109e9d01fdbfb9a09c3d");
        put("0x2a11a186C7B4255fa0F45Dc20894a6dA4AA9472b", "f381ee9b069d9136d57b926ec6302c040228114bc34f791420a13eb63ed66473");
        put("0x2fcd762275e46744d1C8F98D808AB9d305324C10", "38138783d2039cf36a6ce4c854e10d9b60f936a25c7de07a31651c5ace8a081b");
        put("0x4B36F06867D2b3F05a11c6763d00370eF02332Df", "ce3f3f74eeca63c20da677adccc2cd150da8674ac542886506fb503463023173");
        put("0x0B549Fbd1fB9c6bC3a9b6BD85A17Ef3f570643e8", "fe9f79ec9578dbbf0fb831bd434f1cc133acf35e5095a08e98d1e8682a344f7e");
        put("0x58531321d358E8aDAcd807e7bff914B913A35423", "e7f31e8421cbd66313dcefc2a66c9121e0ec4c0f9801c2da83d457d367e420cc");
        put("0x56b90F29687417Dc6C929EA28913b9BE39b66BFa", "8a21e5401cf71de400f52b2b2b42303e6e8cb660aa1003790b61b8b90f67e4f4");
//        put("0x1cCf59AeA69C5cfE3CD0c06E16bCC6F25DA01eB1", "4a64b42a88aed7bc607b37131cae24d0957c89f30e4c19ca980e8b1ab302a93e");
//        put("0x85A83555072bF2A6265d364Cb9aF71f335C916D5", "ffcb1bfdb084fa4ee971b6dee58f82957ec6045c43b068f2606111f2638ef716");
    }};

    public static String DUMMY_ACCOUNT_ADDRESS = "0x1cCf59AeA69C5cfE3CD0c06E16bCC6F25DA01eB1";

    public static List<String> convertKeysToList() {
        List<String> keyList = new ArrayList<>();
        for(String key : addressKeyPair.keySet()) {
            keyList.add(key);
        }
        return keyList;
    }

    public static String generateFreshKey(int low, int high, List<String> keys) {
        Random random = new Random();
        int randomIndex = random.nextInt(high - low) + low;
        return keys.get(randomIndex);
    }

    public static Pair<String, String> getNewKeyPair(List<User> users) {
        Pair<String, String> newPair;
        List<String> keyList = convertKeysToList();
        String freshKey = generateFreshKey(0, 97, keyList);
        for(User user : users) {
            if(user.getUAddress().equals(freshKey)) {
                return getNewKeyPair(users);
            }
        }
        newPair = new Pair<>(freshKey, addressKeyPair.get(freshKey));
        return newPair;
    }

    public static Pair<String, String> getNewKeyPairForRequester(List<FundingDetails> fundingDetailsList) {
        Pair<String, String> newPair;
        List<String> keyList = convertKeysToList();
        String freshKey = generateFreshKey(0, 97, keyList);
        for(FundingDetails fundingDetails : fundingDetailsList) {
            if(fundingDetails.getFAccountAddress().equals(freshKey)) {
                return getNewKeyPairForRequester(fundingDetailsList);
            }
        }
        newPair = new Pair<>(freshKey, addressKeyPair.get(freshKey));
        return newPair;
    }
}
