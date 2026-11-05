import controller.PaymentController;
import view.PaymentView;

public class Main {
    public static void main(String[] args) {
        PaymentView view = new PaymentView();
        PaymentController controller = new PaymentController(view);
        
        controller.processTransaction();
    }
}