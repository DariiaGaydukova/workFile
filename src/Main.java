import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.txt");
        Basket basket = null;

        try {
            basket = Basket.loadFromTxtFile(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < basket.getProducts().length; i++) {
            System.out.println(basket.getProducts()[i] + " price: " + basket.getPrices()[i]);
        }

        System.out.println("Выберите товар и количество или введите `end`");
        while (true) {

            String input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                break;

            } else {
                String[] count = input.split(" ");

                int productNumber = Integer.parseInt(count[0]) - 1;
                int productCount = Integer.parseInt(count[1]);
                basket.addToCart(productNumber, productCount);

                try {
                    basket.saveTxt(file);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }
}