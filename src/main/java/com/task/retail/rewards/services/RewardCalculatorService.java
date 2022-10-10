package com.task.retail.rewards.services;

import com.task.retail.rewards.model.Customer;
import com.task.retail.rewards.model.CustomerReward;
import com.task.retail.rewards.model.Transaction;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RewardCalculatorService {

    @Autowired
    private CustomerService customerService;


    public void processTransactions(List<Transaction> transactions) {
        processTransactions(transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId)));
    }

    public void processTransactions(Map<Long, List<Transaction>> transactions) {
        List<Customer> customers = customerService.getCustomers();
        transactions.forEach((customerId, transactionForIndvCustomer) -> {
            //If given transaction contains a Valid customer Id, We will process it otherwise discard it.
            customers.stream().filter( customer -> customer.getId().equals(customerId) ).forEach( validCustomer -> {
                Map<String, List<Transaction>> monthlyTransactions = transactionForIndvCustomer.stream().collect(
                        Collectors.groupingBy(transaction -> String.valueOf(transaction.getTransactionDate().getMonth())));
                for (Map.Entry<String, List<Transaction>> monthlyTrans : monthlyTransactions.entrySet()) {
                    Double monthlyPurchaseVal = monthlyTrans.getValue().stream().mapToDouble(Transaction::getAmount).sum();
                    if (monthlyPurchaseVal > 0) {
                        Double rewardValue = validCustomer.getRewards().stream().filter( reward -> reward.getRewardMonth().equals(monthlyTrans.getKey()))
                                .mapToDouble(CustomerReward::getRewardValue).sum();
                        if (monthlyPurchaseVal > 100) {
                            rewardValue = (monthlyPurchaseVal - 100) * 2 + 50;
                        } else if(monthlyPurchaseVal > 50) {
                            rewardValue = monthlyPurchaseVal - 50;
                        }
                        CustomerReward customerReward = new CustomerReward();
                        customerReward.setRewardValue(rewardValue);
                        customerReward.setRewardMonth(monthlyTrans.getKey());
                        customerReward.setCustomer(validCustomer);
                        validCustomer.getRewards().add(customerReward);
                    }
                }
            });
        });
        customerService.saveCustomers(customers);
    }

    public void processTransactions(MultipartFile transactionData) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(transactionData.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            if (Objects.nonNull(worksheet.getRow(i)) && Objects.nonNull(worksheet.getRow(i).getCell(0))
                    && Objects.nonNull(worksheet.getRow(i).getCell(1))) {
                Transaction transaction = new Transaction();
                transaction.setCustomerId(new BigDecimal(String.valueOf(worksheet.getRow(i).getCell(0))).longValue());
                transaction.setTransactionDate(LocalDate.parse(String.valueOf(worksheet.getRow(i).getCell(1)), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                transaction.setAmount(Double.parseDouble(String.valueOf(worksheet.getRow(i).getCell(2))));
                transactions.add(transaction);
            }
        }
        processTransactions(transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId)));
    }
}
