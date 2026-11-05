package controller;

import model.*;
import view.PaymentView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentController {
    private List<PaymentStrategy> paymentMethods;
    private PaymentView view;

    public PaymentController(PaymentView view) {
        this.view = view;
        this.paymentMethods = new ArrayList<>();
        
        // Inisialisasi data awal sesuai modul
        paymentMethods.add(new Wallet("Main Wallet", 10000.0));
        paymentMethods.add(new DebitCard("1234-5678-9012", 100000.0, "1234"));
        paymentMethods.add(new CreditCard("9876-5432-1098", 5000000.0, "123", "4321"));
        paymentMethods.add(new EWallet("08123456789", 500000.0, "1111"));
    }

    public void processTransaction() {
        double amount = view.inputAmount();

        // Filter metode pembayaran menggunakan canPay(amount)
        List<PaymentStrategy> availableMethods = paymentMethods.stream()
                .filter(method -> method.canPay(amount))
                .collect(Collectors.toList());

        if (availableMethods.isEmpty()) {
            view.showMessage("Tidak ada metode pembayaran yang memenuhi syarat untuk nominal tersebut.");
            return;
        }

        int choice = view.displayPaymentMethods(availableMethods);
        if (choice < 1 || choice > availableMethods.size()) {
            view.showMessage("Pilihan tidak valid.");
            return;
        }

        PaymentStrategy selectedStrategy = availableMethods.get(choice - 1);

        try {
            if (!selectedStrategy.validate(amount)) {
                throw new IllegalArgumentException("Validasi gagal: Saldo atau limit tidak mencukupi.");
            }

            List<AuthorizationMethod> auths = selectedStrategy.getRequiredAuthorizations();
            for (AuthorizationMethod auth : auths) {
                String input = view.inputAuthorization(auth.name());

                if (auth == AuthorizationMethod.PIN) {
                    String correctPin = getPinFromStrategy(selectedStrategy);
                    if (!input.equals(correctPin)) {
                        throw new SecurityException("PIN Salah!");
                    }
                } else if (auth == AuthorizationMethod.CCV) {
                    String correctCcv = ((CreditCard) selectedStrategy).getCcv();
                    if (!input.equals(correctCcv)) {
                        throw new SecurityException("CCV Salah!");
                    }
                } else if (auth == AuthorizationMethod.OTP) {
                    if (!input.equals("123456")) {
                        view.showMessage("(Simulasi: Masukkan 123456 untuk OTP)");
                        throw new SecurityException("OTP Salah!");
                    }
                }
            }

            selectedStrategy.pay(amount);
            view.showMessage("Pembayaran berhasil diproses!");

            displayRemainingBalance(selectedStrategy);

        } catch (Exception e) {
            view.showMessage("Error: " + e.getMessage());
        }
    }

    private String getPinFromStrategy(PaymentStrategy strategy) {
        if (strategy instanceof DebitCard) return ((DebitCard) strategy).getPin();
        if (strategy instanceof CreditCard) return ((CreditCard) strategy).getPin();
        if (strategy instanceof EWallet) return ((EWallet) strategy).getPin();
        return "";
    }

    private void displayRemainingBalance(PaymentStrategy strategy) {
        if (strategy instanceof Wallet) {
            view.showMessage("Sisa wallet: " + ((Wallet) strategy).getCashValue());
        } else if (strategy instanceof DebitCard) {
            view.showMessage("Sisa saldo debit: " + ((DebitCard) strategy).getBalance());
        } else if (strategy instanceof CreditCard) {
            view.showMessage("Total hutang credit card: " + ((CreditCard) strategy).getCurrentDebt());
        } else if (strategy instanceof EWallet) {
            view.showMessage("Sisa saldo e-wallet: " + ((EWallet) strategy).getBalance());
        }
    }
}