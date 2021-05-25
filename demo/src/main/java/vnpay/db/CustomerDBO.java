package vnpay.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vnpay.entities.request.CustomerRequest;
import vnpay.entities.response.Account;
import vnpay.entities.response.CustomerResponse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDBO {
    private static final Logger log = LogManager.getLogger(CustomerDBO.class);
    public static CustomerResponse getCustomerInfor(CustomerRequest request) throws  Exception{
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        CustomerResponse cus= new CustomerResponse();
        try{
            connection=DBPoolConnection.getConnection();
            statement=connection.prepareCall("{call PKG_MB.PROC_CREATE_TRANS_WAITCF(?,?,?,?,?,?)}");
            statement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            statement.setString(2,request.getVnpType());
            statement.setString(3,request.getVnpValue());
            statement.setString(4,request.getChannelID());
            statement.setString(5,request.getTransactionDateTime());
            statement.executeQuery();
            resultSet= (ResultSet)  statement.getObject(1);
            while (resultSet.next()){
                cus.setCif(resultSet.getString("CIF"));
                cus.setHoTen(resultSet.getString("HOTEN"));
                cus.setCmnd(resultSet.getString("CMND"));
                cus.setGioiTinh(resultSet.getString("GIOITINH"));
                cus.setNgaySinh(resultSet.getString("NGAYSINH"));
                cus.setUserName(resultSet.getString("USERNAME"));
                cus.setMaGoiDichVu(resultSet.getString("MAGOIDICHVU"));
                cus.setGoiDichVu(resultSet.getString("GOIDICHVU"));
                cus.setTgtt(resultSet.getString("TGTT"));
                return  cus;
            }
            log.info("not found customer by userName:" + request.getVnpValue());

        }catch (Exception e){
            log.info("get customer info ex: " + e.toString(), e);
        }
        return cus;
    }
    public static List<Account> getAccountInfor(String cif) throws  Exception{
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection=DBPoolConnection.getConnection();
            if(connection != null) {
                statement = connection.prepareCall("{call PKG_MB.PROC_CREATE_TRANS_WAITCF(?,?,?,?,?,?)}");
                statement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                statement.setString(2, cif);

                statement.executeQuery();
                resultSet = (ResultSet) statement.getObject(1);
                List<Account> result = new ArrayList<>();
                while (resultSet.next()) {
                    Account a= new Account();
                    a.setAccountNo(resultSet.getString("ACCOUNTNO"));
                    a.setCif(resultSet.getString("CIF"));
                    a.setProductcode(resultSet.getString("PRODUCTCODE"));
                    a.setCcy(resultSet.getString("CCY"));
                    a.setBranchAcc(resultSet.getString("BRANCHACC"));
                    a.setAccStatus(resultSet.getString("ACCSTATUS"));
                    a.setAccountType(resultSet.getString("ACCOUNTTYPE"));
                    a.setBranchName(resultSet.getString("BRANCHNAME"));
                    result.add(a);
                }
                return result;
            }
            log.info("Connection is null: " + connection);

        }catch (SQLException e) {
            log.info("getMid ex: " + e.toString(), e);
        }
        return null;
    }

}
