import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = (Document) builder.parse(new File("shop.xml"));

        NodeList loadList = doc.getElementsByTagName("load");
        NodeList saveList = doc.getElementsByTagName("save");
        NodeList logList = doc.getElementsByTagName("log");

        Node load = loadList.item(0);
        Node save = saveList.item(0);
        Node log = logList.item(0);

        Element loadElement = (Element) load;
        Element saveElement = (Element) save;
        Element logElement = (Element) log;


        Scanner scanner = new Scanner(System.in);
        Basket basket = null;


        if (loadElement.getElementsByTagName("enabled").item(0).getTextContent().equals("true")) {
            if (loadElement.getElementsByTagName("format").item(0).getTextContent().equals("text")) {
                try {
                    File textFile = new File(loadElement.getElementsByTagName("fileName").item(0).getTextContent());
                    basket = Basket.loadFromTxtFile(textFile);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (loadElement.getElementsByTagName("format").item(0).getTextContent().equals("json")) {
                try {
                    File textFile = new File((loadElement.getElementsByTagName("fileName").item(0).getTextContent()));
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
                if (logElement.getElementsByTagName("enabled").item(0).getTextContent().equals("true")) {
                    try {
                        clientLog.exportAsCSV(new File("client.csv"));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                basket.printCart();
                break;
            } else {
                String[] count = input.split(" ");

                int productNumber = Integer.parseInt(count[0]) - 1;
                int productCount = Integer.parseInt(count[1]);

                basket.addToCart(productNumber, productCount);
                clientLog.log(productNumber, productCount);


                if ((saveElement.getElementsByTagName("format").item(0).getTextContent().equals("json"))) {
                    try {
                        basket.saveJson(new File(saveElement.getElementsByTagName("fileName").item(0).getTextContent()));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    try {

                        basket.saveTxt(new File(saveElement.getElementsByTagName("fileName").item(0).getTextContent()));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

            }
        }
    }

}
