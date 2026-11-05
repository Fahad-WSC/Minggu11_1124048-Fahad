package model;

import java.util.List;

public class CreditCard implements PaymentStrategy {
    private String cardNumber;
    private double limit;
    private double currentDebt;
    private String ccv;
    private String pin;

    public CreditCard(String cardNumber, double limit, String ccv, String pin) {
        this.cardNumber = cardNumber;
        this.limit = limit;
        this.currentDebt = 0.0;
        this.ccv = ccv;
        this.pin = pin;
    }

    @Override
    public boolean validate(double amount) {
        return (this.currentDebt + amount) <= this.limit;
    }

    @Override
    public void pay(double amount) {
        this.currentDebt += amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.CCV, AuthorizationMethod.PIN);
    }

    @Override
    public boolean canPay(double amount) {
        return amount >= 100000 && amount <= 100000000 && (this.currentDebt + amount) <= this.limit;
    }

    public String getCcv() { return ccv; }
    public String getPin() { return pin; }
    public double getCurrentDebt() { return currentDebt; }
}