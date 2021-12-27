package Part_2;

public class ColumnException extends Exception{
    /**
     * The number of columns does not match the composite number.
     */
    public ColumnException(String path) {
        super(path);
    }
}
