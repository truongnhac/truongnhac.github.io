package vnpay.Bussines;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import vnpay.db.CustomerDBO;
import vnpay.entities.request.CustomerRequest;
import vnpay.entities.response.Account;
import vnpay.entities.response.CustomerResponse;
import vnpay.sercurity.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class GetCustomerBusiness {
    public CustomerResponse getCustomer(CustomerRequest request) throws Exception{
        String secretKey="h0NguyenTruongNh@c";
        String sign= request.getVnpType()+request.getVnpValue()+request.getChannelID()+request.getTransactionDateTime()+secretKey;
        String signature= signaTure(secretKey,sign);
        if(!signature.equals(request.getSignature())){
        log.info("Signature Invalid");
        return null;
        }
        CustomerResponse  customerInfor= CustomerDBO.getCustomerInfor(request);
        List<Account> a=CustomerDBO.getAccountInfor(customerInfor.getCif());
        customerInfor.setAccount(a);
        return customerInfor;
    }
    public String signaTure(String key,String signature) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            byte[] hashbyte = sha256_HMAC.doFinal(signature.getBytes());
            String has= new Base64().encode(hashbyte);
            return has;
        }catch (Exception e){
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
    }
}
