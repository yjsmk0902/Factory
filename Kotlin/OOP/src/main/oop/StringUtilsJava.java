public abstract class StringUtilsJava {
    private StringUtilsJava() {}

    public boolean isDirectoryPath(String path) {
        return path.endsWith("/");
    }
}
