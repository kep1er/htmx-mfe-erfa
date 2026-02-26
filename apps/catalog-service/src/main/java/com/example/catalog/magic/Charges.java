package com.example.catalog.magic;

import jakarta.persistence.Embeddable;

@Embeddable
public class Charges {

    private Integer max;
    private String recharge;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getRecharge() {
        return recharge;
    }

    public void setRecharge(String recharge) {
        this.recharge = recharge;
    }
}
