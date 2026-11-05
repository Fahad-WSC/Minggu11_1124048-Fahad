package model;

import java.util.List;

public class DebitCard implements PaymentStrategy {
    private String cardNumber;
    private double balance;
    private String pin;

    public DebitCard(String cardNumber, double balance, String pin) {
        this.cardNumber = cardNumber;
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
        return List.of(AuthorizationMethod.PIN);
    }

    @Override
    public boolean canPay(double amount) {
        return amount <= 10000000 && this.balance >= amount;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}