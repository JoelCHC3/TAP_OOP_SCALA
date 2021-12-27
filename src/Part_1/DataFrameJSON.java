package Part_1;


import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;


public class DataFrameJSON extends AbstractDataFrame {

    /**
     * Constructor of the DataFrame. It creates the lists for the data to be stored in and saves the path.
     * @param path Path of the file.
     */
    public DataFrameJSON(String path) {
        data = new ArrayList<>();
        this.path = path;
    }

    /**
     * Reads the file and stores the data.
     * @throws Exception The exception thrown.
     */
    public void openDataFrame() throws Exception {
            Gson gson = new Gson();
            Path lele = Paths.get(path);
            Reader reader = Files.newBufferedReader(lele);

            Map<?, ?>[] map = gson.fromJson(reader, Map[].class);

            ArrayList<String> aux;
            aux = new ArrayList<>();
            data.add(0,aux);
            data.get(0).add("");
            int i = 1;
            for (Map.Entry<?, ?> entry : map[0].entrySet()) {
                aux = new ArrayList<>();
                aux.add(entry.getKey().toString());
                data.add(i, aux);
                i++;
            }
            columns = (i-1);
            i = 1;
            int id = 0;
            for (Map<?, ?> value : map) {
                for (Map.Entry<?, ?> entry : value.entrySet()) {
                    String x = entry.getValue().toString();
                    if (x.endsWith(".0")) {          //Fixing the problem with gson always reading numbers as Double
                        x = x.substring(0, x.length() - 2);    //Basically the ".0" is deleted, so it can be treated as int
                    }
                    data.get(i).add(x);
                    i++;
                }
                data.get(0).add(String.valueOf(id));
                id++;
                i = 1;
                rows++;
            }
            reader.close();
    }
}
