package view;

import model.PaymentStrategy;
import java.util.List;
import java.util.Scanner;

public class PaymentView {
    private Scanner scanner = new Scanner(System.in);

    public double inputAmount() {
        System.out.print("Masukkan jumlah yang dibayar: ");
        return scanner.nextDouble();
    }

    public int displayPaymentMethods(List<PaymentStrategy> methods) {
        System.out.println("Pilih metode pembayaran yang tersedia:");
        for (int i = 0; i < methods.size(); i++) {
            System.out.println((i + 1) + ". " + methods.get(i).getClass().getSimpleName());
        }
        System.out.print("Pilihan Anda: ");
        return scanner.nextInt();
    }

    public String inputAuthorization(String authName) {
        System.out.print("Masukkan " + authName + ": ");
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}