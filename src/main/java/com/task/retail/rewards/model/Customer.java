package com.task.retail.rewards.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer_reward_id", cascade = CascadeType.MERGE)
    List<CustomerReward> rewards = new ArrayList<CustomerReward>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomerReward> getRewards() {
        return rewards;
    }

    public void setRewards(List<CustomerReward> rewards) {
        this.rewards = rewards;
    }
}
