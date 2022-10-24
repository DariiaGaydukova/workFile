import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReaderShop {

    String enabled;
    String fileName;
    String format;


    public ReaderShop(Node config) {

        NodeList nodeList = config.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == currentNode.getNodeType()) {
                if (currentNode.getNodeName().equals("enabled")) {
                    enabled = config.getTextContent();
                }
                if (currentNode.getNodeName().equals("fileName")) {
                    fileName = config.getTextContent();
                }

                if (currentNode.getNodeName().equals("format")) {
                    format = currentNode.getTextContent();
                }


            }
        }}
}
