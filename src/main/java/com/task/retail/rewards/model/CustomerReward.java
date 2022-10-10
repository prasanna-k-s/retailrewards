package com.task.retail.rewards.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="customer_reward")
public class CustomerReward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_reward_id", nullable = false)
    private Long customer_reward_id;
    private Double rewardValue;

    private String rewardMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public CustomerReward() {
    }

    public CustomerReward(Double rewardValue, String rewardMonth) {
        this.rewardValue = rewardValue;
        this.rewardMonth = rewardMonth;
    }

    public Long getCustomer_reward_id() {
        return customer_reward_id;
    }

    public void setCustomer_reward_id(Long customer_reward_id) {
        this.customer_reward_id = customer_reward_id;
    }

    public Double getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(Double rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getRewardMonth() {
        return rewardMonth;
    }

    public void setRewardMonth(String rewardMonth) {
        this.rewardMonth = rewardMonth;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
