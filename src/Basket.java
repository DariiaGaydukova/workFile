import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.*;

public class Basket {
    private String[] products;
    private int[] prices;
    private long[] productBasket;


    public Basket(String[] products, int[] prices) {
        this.prices = prices;
        this.products = products;
        productBasket = new long[products.length];

    }

    public void addToCart(int productNum, int amount) {
        productBasket[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваши покупки завершены. Ваша корзина:\n");

        long sum = 0;


        for (int j = 0; j < productBasket.length; j++) {

            System.out.println(products[j] + " на сумму:  " + (productBasket[j] * prices[j]));
            sum += productBasket[j] * prices[j];
        }
        System.out.println("Общая сумма покупок " + sum);
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (String a : getProducts()) {
                out.print(a + " ");
            }
            for (int b : getPrices()) {
                out.print(b + " ");
            }
            for (long c : getProductBasket()) {
                out.print(c + " ");
            }
        }
    }


    public void saveJson(File jsonFile) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try (FileWriter file = new FileWriter(String.valueOf(jsonFile))) {
            file.write(gson.toJson(this));
        }
    }

    public static Basket loadJson(File jsonFile) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(jsonFile));
        return gson.fromJson(reader, Basket.class);
    }

    static Basket loadFromTxtFile(File textFile) throws Exception {
        if (textFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile));) {

            String[] products = reader.readLine().strip().split(" ");
            String[] pricesStr = reader.readLine().strip().split(" ");
            int[] prices = new int[pricesStr.length];

            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(pricesStr[i]);
            }

            Basket basket = new Basket(products, prices);

            String[] amountsStr = reader.readLine().strip().split(" ");

            for (int i = 0; i < amountsStr.length; i++) {
                basket.productBasket[i] = Integer.parseInt(amountsStr[i]);
            }
            return basket;
        }
        } else {
            String[] products = {"apple", "milk", "rice"};
            int[] prices = {70, 100, 120};
            Basket basket = new Basket(products, prices);
            return basket;
        }

    }



    public int[] getPrices() {
        return prices;
    }

    public long[] getProductBasket() {
        return productBasket;
    }


    public String[] getProducts() {
        return products;
    }
}
