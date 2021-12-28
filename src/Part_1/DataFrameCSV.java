package Part_1;
import java.io.File;
import java.util.*;

public class DataFrameCSV extends AbstractDataFrame {

    /**
     * Constructor of the DataFrame. It creates the lists for the data to be stored in and saves the path.
     * @param path Path of the file.
     */
    public DataFrameCSV(String path) {
        data = new ArrayList<>();
        this.path = path;
    }

    /**
     * Reads the file and stores the data.
     * @throws Exception The exception thrown.
     */
    public void openDataFrame() throws Exception {
        Scanner sc = new Scanner(new File(path));
        int i;
        sc.useDelimiter("\n");
        String line = sc.next();

        String[] labels;
        line = line.replaceAll(",\\s+", ",");
        line = line.replaceAll("\"", "");
        line = line.trim();
        line = line.replaceAll("([\\r\\n])", "");
        labels = line.split(",");
        columns = labels.length;
        rows = 0;

        ArrayList<String> aux;
        aux = new ArrayList<>();
        data.add(0,aux);
        data.get(0).add("");
        for(i=1; i<=labels.length; i++) {
            aux = new ArrayList<>();
            aux.add(labels[i-1]);
            data.add(i, aux);
        }
        int id = 0;
        while(sc.hasNext()) {
            if(((line = sc.next()) != null) && (line.length() > 0)) {
                line = line.replaceAll(",\\s+", ",");
                line = line.replaceAll("\"", "");
                line = line.trim();
                line = line.replaceAll("([\\r\\n])", "");
                labels = line.split(",");
                for (i = 1; i <= labels.length; i++) {
                    data.get(i).add(labels[i-1]);
                }
                data.get(0).add(String.valueOf(id));
                id++;
                rows++;
            }
        }
    }
}
