import java.io.File;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
  private List<String[]> productList = new ArrayList<>();

    public void log(int productNum, int amount) {
        productList.add(new String[] {"productNum", "amount"});
        productList.add(new String[]{Integer.toString(productNum), Integer.toString(amount)});

    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile))) {
            csvWriter.writeAll(productList);
        }
    }

}
