import java.io.File;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {
    List<String> productList = new ArrayList<>();


    public void log(int productNum, int amount) {
        productList.add(Integer.parseInt("productNum"), "amount");
        productList.add(productNum, Integer.toString(amount));

    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile))) {
            csvWriter.writeAll((List<String[]>) productList);
        }
    }

}
