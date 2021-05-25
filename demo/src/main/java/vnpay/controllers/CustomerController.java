package vnpay.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnpay.Bussines.GetCustomerBusiness;
import vnpay.entities.request.CustomerRequest;
import vnpay.entities.response.CustomerResponse;

import javax.xml.bind.ValidationException;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class CustomerController {
    @Autowired
    GetCustomerBusiness customerBusiness;
    @RequestMapping(value= "/searchCustomer")
    public ResponseEntity<CustomerResponse> getCustomer(@RequestBody CustomerRequest request) throws Exception{
        if(request.getVnpType().equals("1")){
            log.info("Cif request: " + request.getVnpValue());
        }else if(request.getVnpType().equals("2")){
            log.info("cmnd request: " + request.getVnpValue());
        }else if(request.getVnpType().equals("3")){
            log.info("UserName request: " + request.getVnpValue());
        }else if(request.getVnpType().equals("3")){
            log.info("Name request: " + request.getVnpValue());
        }
        try{
            return ResponseEntity.ok(customerBusiness.getCustomer(request));
        }catch (Exception e) {
            throw new ValidationException("không thể lấy thông tin Customer");

        }

    }
}
