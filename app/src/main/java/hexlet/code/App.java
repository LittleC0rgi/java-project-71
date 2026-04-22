package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true, version = "1.0")
public class App implements Callable<Integer> {

    @Parameters(paramLabel = "filepath1", index = "0", description = "path to first file")
    private Path filepath1;

    @Parameters(paramLabel = "filepath2", index = "1", description = "path to second file")
    private Path filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]",
            paramLabel = "format", arity = "1")
    private String format = "stylish";

    public static Map<String, Object> getData(Path path) throws Exception {
        String content = readFile(path);
        return parse(content);
    }

    public static Map<String, Object> parse(String content) throws Exception {
        return Map.of();
    }

    public static String readFile(Path path) throws Exception {
        return Files.readString(path);
    }

    @Override
    public Integer call() {
        try {
            var data1 = getData(filepath1);
            var data2 = getData(filepath2);

            System.out.println(data1);
            System.out.println(data2);

            return 0;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
