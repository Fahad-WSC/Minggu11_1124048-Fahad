package model;

import java.util.Collections;
import java.util.List;

public class Wallet implements PaymentStrategy {
    private String name;
    private double cashValue;

    public Wallet(String name, double cashValue) {
        this.name = name;
        this.cashValue = cashValue;
    }

    @Override
    public boolean validate(double amount) {
        return this.cashValue >= amount;
    }

    @Override
    public void pay(double amount) {
        this.cashValue -= amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return Collections.emptyList();
    }

    @Override
    public boolean canPay(double amount) {
        return amount >= 100 && amount <= 1000000 && this.cashValue >= amount;
    }

    public double getCashValue() {
        return cashValue;
    }
}