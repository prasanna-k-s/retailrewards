package com.task.retail.rewards.services;

import com.task.retail.rewards.model.Customer;
import com.task.retail.rewards.repository.CustomerRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String saveCustomerInformation(MultipartFile customerData) throws Exception {
        List<Customer> newcustomers = new ArrayList<Customer>();
        XSSFWorkbook workbook = new XSSFWorkbook(customerData.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            if (Objects.nonNull(worksheet.getRow(i)) && Objects.nonNull(worksheet.getRow(i).getCell(0)) && Objects.nonNull(worksheet.getRow(i).getCell(1))) {
                Customer customer = new Customer();
                customer.setId(new BigDecimal(String.valueOf(worksheet.getRow(i).getCell(0))).longValue());
                customer.setName(String.valueOf(worksheet.getRow(i).getCell(1)));
                newcustomers.add(customer);
            }
        }
        customerRepository.saveAll(newcustomers);
        return "Successfully saved Customers";
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void saveCustomers(List<Customer> customers) {
        customerRepository.saveAll(customers);
    }
}
