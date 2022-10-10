package com.task.retail.rewards.api;

import com.task.retail.rewards.model.Customer;
import com.task.retail.rewards.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerAPIController {

    @Autowired
    public CustomerService customerService;

    @PostMapping("/import-customers")
    public String saveCustomerInfo(@RequestParam("file") MultipartFile customerData) throws Exception {
        return customerService.saveCustomerInformation(customerData);
    }

    @GetMapping("/get-customer-info")
    public List<Customer> getCustomer(){
        return customerService.getCustomers();
    }

}
