package model;

import java.util.List;

public class Ewallet implements PaymentStrategy {
    private String phoneNumber;
    private double balance;
    private String pin;

    public Ewallet(String phoneNumber, double balance, String pin) {
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.pin = pin;
    }

    @Override
    public boolean validate(double amount) {
        return this.balance >= amount;
    }

    @Override
    public void pay(double amount) {
        this.balance -= amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.PIN, AuthorizationMethod.OTP);
    }

    @Override
    public boolean canPay(double amount) {
        return amount > 0 && this.balance >= amount;
    }

    public String getPin() { return pin; }
    public double getBalance() { return balance; }
}