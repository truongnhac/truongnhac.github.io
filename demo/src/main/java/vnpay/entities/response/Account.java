package vnpay.entities.response;

import lombok.Data;

@Data
public class Account {
    private String accountNo;
    private String cif;
    private String productcode;
    private String ccy;
    private String branchAcc;
    private String accStatus;
    private String accountType;
    private String branchName;
}
