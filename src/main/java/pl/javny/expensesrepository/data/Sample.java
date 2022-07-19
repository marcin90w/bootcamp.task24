package pl.javny.expensesrepository.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sample {

    private final String fileName = "resources/createexampledata.txt";

    public String readFile() {
        try (
                var fileReader = new FileReader(fileName);
                var reader = new BufferedReader(fileReader);
        ) {
            String fullText = "";
            String text = null;
            while ((text = reader.readLine()) != null) {
                fullText += text + "\n";
            }
            return fullText;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    return "";
    }
}
