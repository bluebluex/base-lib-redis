package tomax.loo.lesson.redis.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-05-31 20:58
 **/
public class DebtTest {

    public static final String pubkey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANvXnfilQUiWpjaq+v4/j6eSNeN9ip9DeHX/HoUQqDdgkwBFS7zVp/JSbIxhOq1bb2SKx7zY2jyl1AJS78Ml/D0CAwEAAQ==";
    public static final String pubkey1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLoEAfb0rStZth4Kgk47n6OsC27L97Uv1nqD0hdvYHe/ee3lUg95PCq2JBX+hEuJWlaVNmyKpVlOVhxi5xZ/fyY+0azbNRQW+OiA/JGVRLb7j05UBx+BVOWIvxfMzoiIiOsd2FeJjG12nzUKDxH1ReW+B18SlVOR8lb9Q6T8+suwIDAQAB";
    public static final String pubkey2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmEqCUfZ0mdZkAdDyFOicV0Bb3\n";
    public static final String pubkey4 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIugQB9vStK1m2HgqCTjufo6wLbsv3tS/WeoPSF29gd7957eVSD3k8KrYkFf6ES4laVpU2bIqlWU5WHGLnFn9/Jj7RrNs1FBb46ID8kZVEtvuPTlQHH4FU5Yi/F8zOiIiI6x3YV4mMbXafNQoPEfVF5b4HXxKVU5HyVv1DpPz6y7AgMBAAECgYA8UYZiDhos3Pe7WOpKxXZae3c1jyFJjZibZzrBcjrnEM7weuwbydiziIZx55TX20YKp224QYgUYLaXfp4hr+CU2JrOYM6fTXGa/PkGEFxTe11z5pZ6+jQV4SsAOSzGYihb6IR3jGX4ARGSjQKRUlpDOmGPYs2e3fBhd6y/4NsHAQJBAN+1/cCjJGA7tQnEddwPaLr9I/WhvxaF8lzfI/SXshF9/OVN2U6YIvr0TQ/dWMD5lPBEatVhMLUR0++yWDtDB4sCQQCfx1y/QYIOQpo2I14mURNkNt4jGinedT62JsAKJuTg9CmgSaZeip5x0iKvZKHM2lam9GxKLUEhB5vvQgE07hWRAkBb9bfFxEpjtKeKyFOajksC8qQhiMfAPneObbueA+2S+zNGH2ZAxJE8j8hNyTN+wBnVWoqABF+9TwLL+YgDdF5ZAkAeOKtGUbGz2M0qaE4qmTM9xVMG+K/qr+qIsZOpV+n60rP86XFaeIW3qbN90V75TGMPeTPyY9Tp1nKcMrPfuxjBAkEAoDXBU7TVnBSPmw6D+mJNTgviPEgOn0THvTnSSVoMAUi2J65my5Q6HQSsdDYrUxGyIRmt0QC69GPBJGowD6vFVA==";
    public static final String pubkey3 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLoEAfb0rStZth4Kgk47n6OsC27L97Uv1nqD0hdvYHe/ee3lUg95PCq2JBX+hEuJWlaVNmyKpVlOVhxi5xZ/fyY+0azbNRQW+OiA/JGVRLb7j05UBx+BVOWIvxfMzoiIiOsd2FeJjG12nzUKDxH1ReW+B18SlVOR8lb9Q6T8+suwIDAQAB";
    public static final String pubkey6 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLoEAfb0rStZth4Kgk47n6OsC27L97Uv1nqD0hdvYHe/ee3lUg95PCq2JBX+hEuJWlaVNmyKpVlOVhxi5xZ/fyY+0azbNRQW+OiA/JGVRLb7j05UBx+BVOWIvxfMzoiIiOsd2FeJjG12nzUKDxH1ReW+B18SlVOR8lb9Q6T8+suwIDAQAB";
    public static final String pubkey5 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXvzHOo4XzwdhJ1zzzZZGacjCVv5JsqH+hkPTLAoXW+j0pDQJFY1jPj6ok+U6O2mg+LNEHalct/njLe22w1uGHcEbkyWapBa1W/6Z6AwqUzCWdQzFRG4ILJ1a/fTvvx/sJq+wwHWvd66fBiY5bl9lJ1/cc0KFShdUMV6J7k3yX3wIDAQAB";
    public static final String priKey = "7895367891234567";
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        WsReqInfo wsReqInfo = new WsReqInfo();

        String encryptKey = RSAUtils.encrypt(priKey, pubkey3);

        wsReqInfo.setKey(encryptKey);
        String content1 = "{\n" +
                "\"idNo\": \"898898989898989\",\n" +
                "\"contractNo\": \" \"\n" +
                "}";
        String content2 = "{\n" +
                "\"idNo\": \"898898989898989\",\n" +
                "\"mobile\": \"1888888888\",\n" +
                "\"name\": \"Tomcat898898\"\n" +
                "}";
        String content = "{\"allExtendData\":\"{}\",\"annualRate\":\"0.11\",\"assetType\":\"1\",\"borrowerContractAmount\":\"300000\",\"borrowerName\":\"柏先雨\",\"borrowerType\":\"1\",\"contractNo\":\"12367897894654\",\"depositAmount\":\"300000\",\"idno\":\"43112119840420171X\",\"idnoType\":\"1\",\"loanPurpose\":\"17\",\"loanTimeCount\":\"90\",\"loanTimeType\":\"1\",\"loanType\":\"1\",\"phone\":\"13267061107\",\"productType\":\"7\",\"repaymentMode\":\"1\"}";

        String contentAes = AESUtils.encrypt(content, priKey);
        String sign = RSAUtils.sign(content1, pubkey4);
        System.out.println(contentAes);
        wsReqInfo.setContent(contentAes);
        //wsReqInfo.setSign_string(sign);
        String jsonStr = mapper.writeValueAsString(wsReqInfo);
        System.out.println(jsonStr);

        boolean b = RSAUtils.checkSign(content1, sign, pubkey3);


        String str = "12-0.1,6-0.2";
        String[] split = str.split(",");
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        for (String s : split) {
            String[] ss = s.split("-");
            System.out.println(ss[0] + " " + ss[1]);
            map.put(ss[0], ss[1]);
        }
        str = map.get("3");
        System.out.println(map.get("3") + "======" + map1.get("6") + "====" + str);
        String s = "C0027,C0028";
        boolean c0028 = s.contains("C0028");

        /*User user = new User();
        user.setLst(null);
        System.out.println(c0028);
        System.out.println(user.getLst().get(0));*/


    }
}
