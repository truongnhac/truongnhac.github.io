package vnpay.entities.response;


import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private String cif;
    private String hoTen;
    private String cmnd;
    private String gioiTinh;
    private String ngaySinh;
    private String userName;
    private String maGoiDichVu;
    private String goiDichVu;
    private String tgtt;
    private List<Account> account;

}
