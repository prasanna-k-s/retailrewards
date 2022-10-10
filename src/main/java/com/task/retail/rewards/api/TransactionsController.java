package com.task.retail.rewards.api;

import com.task.retail.rewards.model.Transaction;
import com.task.retail.rewards.services.RewardCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/process")
public class TransactionsController {

    @Autowired
    public RewardCalculatorService rewardCalculatorService;

    @PostMapping("/transaction")
    public void getCustomerTransactions(@RequestBody List<Transaction> transactions) throws Exception{
        rewardCalculatorService.processTransactions(transactions);
    }

    @PostMapping("/file")
    public void transactionUpload(@RequestParam("file") MultipartFile transactionData) throws Exception{
        rewardCalculatorService.processTransactions(transactionData);
    }

}
