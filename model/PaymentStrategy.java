package model;

import java.util.List;

public interface PaymentStrategy {
    boolean validate(double amount);
    void pay(double amount);
    List<AuthorizationMethod> getRequiredAuthorizations();
    boolean canPay(double amount);
}