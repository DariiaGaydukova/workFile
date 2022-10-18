import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.swing.text.Document;
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

        Node config = doc.getDocumentElement();

        ReaderShop load = new ReaderShop(((org.w3c.dom.Document) doc).getElementsByTagName("load").item(0));
        ReaderShop save = new ReaderShop(doc.getElementsByTagName("save").item(0));





        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.json");
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