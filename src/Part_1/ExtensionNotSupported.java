package Part_1;

public class ExtensionNotSupported extends Exception{
    /**
     * The extension of the file is not supported.
     */
    public ExtensionNotSupported(String path) {
        super(path);
    }
}
