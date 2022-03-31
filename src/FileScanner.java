import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileScanner {

    private String filePathBasic = "dataForGenerating/";
    private String fileName;
    private List<String> loadedValues;

    public FileScanner(String fileName) throws Exception {
        this.fileName = fileName;
        readFromFile();

    }

    private Exception readFromFile() throws Exception {
        loadedValues = new LinkedList<>();
        FileReader fileReader = new FileReader(filePathBasic + fileName);
        Scanner scanLines = new Scanner(fileReader);

        while(scanLines.hasNext()) {
            loadedValues.add(scanLines.nextLine());
        }

        return null;
    }

    List<String> getLoadedValues() {
        return loadedValues;
    }
}
