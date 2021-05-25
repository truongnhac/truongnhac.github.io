package vnpay.entities.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String vnpType;
    private String vnpValue;
    private String channelID;
    private String transactionDateTime;
    private String signature;
}
