package Part_4;

import Part_2.DataFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class DFStruct {
    private final List<DataFrame> dataFrames;

    /**
     * Initializes the list of DataFrame.
     */
    public DFStruct() {dataFrames = new ArrayList<>();}

    /**
     * Adds a DataFrame to the list.
     * @param d The DataFrame to be added.
     */
    public void addDataFrame(DataFrame d) {dataFrames.add(d);}

    /**
     * Implements the MapReduce logic.
     * @param map The map function to be applied.
     * @param reduce The reduce logic to be applied.
     * @param <T> The generic type.
     * @return The result of the MapReduce.
     */
    public <T> T MapReduce(Function<DataFrame,T> map, BinaryOperator<T> reduce){
        Stream<DataFrame> dfStream = dataFrames.parallelStream();
        return dfStream.map(map).reduce(reduce).get();
    }
}
