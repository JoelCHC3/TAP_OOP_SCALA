package Part_1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFrameTXT extends AbstractDataFrame {

    /**
     * Constructor of the DataFrame. It creates the lists for the data to be stored in and saves the path.
     * @param path Path of the file.
     */
    public DataFrameTXT(String path) {
        data = new ArrayList<>();
        this.path = path;
    }

    /**
     * Reads the file and stores the data. File's first line must only contain the delimiter between sharps, so for a
     * comma to be the delimiter the first line should be: #,#.
     * @throws Exception The exception thrown.
     */
    public void openDataFrame() throws Exception {

        Scanner sc = new Scanner(new File(path));
        int i;
        sc.useDelimiter("\n");
        String line = sc.next();
        String[] token = line.split("#");   //This token is the same as the delimiter used in the source file
        String delimiter = token[1];

        String[] labels;
        line = sc.next();
        line = line.replaceAll(delimiter+"\\s+", delimiter);
        line = line.replaceAll("\"", "");
        line = line.replaceAll("([\\r\\n])", "");
        labels = line.split(delimiter);
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
                line = line.replaceAll(delimiter+"\\s+", delimiter);
                line = line.replaceAll("\"", "");
                line = line.trim();
                line = line.replaceAll("([\\r\\n])", "");
                labels = line.split(delimiter);
                for (i = 1; i <= labels.length; i++) {
                    data.get(i).add(labels[i-1]);
                }
            }
            data.get(0).add(String.valueOf(id));
            id++;
            rows++;
        }
    }
}
