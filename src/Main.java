import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = (Document) builder.parse(new File("shop.xml"));

        Node config = doc.getDocumentElement();

        ReaderShop load = new ReaderShop(doc.getElementsByTagName("load").item(0));
        ReaderShop save = new ReaderShop(doc.getElementsByTagName("save").item(0));
        ReaderShop log = new ReaderShop(doc.getElementsByTagName("log").item(0));


        Scanner scanner = new Scanner(System.in);
        Basket basket = null;


        if (load.equals("true")) {
            if (load.format.equals("txt")) {
                try {
                    File textFile = new File("basket.txt");
                    basket = Basket.loadFromTxtFile(textFile);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (load.format.equals("json")) {
                try {
                    File textFile = new File("basket.json");
                    basket = Basket.loadJson(textFile);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (basket == null) {
            String[] products = {"apple", "milk", "rice"};
            int[] prices = {70, 100, 120};
            basket = new Basket(products, prices);
        }

        ClientLog clientLog = new ClientLog();


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
                clientLog.log(productNumber, productCount);


                if (save.format.equals("json")) {
                    try {
                        basket.saveJson(new File("basket.json"));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    try {

                        basket.saveTxt(new File("basket.txt"));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

            }
        }
        if (log.enabled.equals("true")) {
            try {
                clientLog.exportAsCSV(new File("client.csv"));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
