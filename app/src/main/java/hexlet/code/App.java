package hexlet.code;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public static String buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
        var body = Stream.concat(data1.keySet().stream(), data2.keySet().stream()) //
                .distinct() //
                .sorted() //
                .flatMap(key -> buildLines(key, data1, data2).stream())
                .collect(Collectors.joining("\n"));

        return "{\n" + body + "\n}";
    }

    private static List<String> buildLines(String key, Map<String, Object> d1,
            Map<String, Object> d2) {
        boolean in1 = d1.containsKey(key);
        boolean in2 = d2.containsKey(key);

        if (in1 && !in2) {
            return List.of("  - " + key + ": " + d1.get(key));
        }
        if (!in1 && in2) {
            return List.of("  + " + key + ": " + d2.get(key));
        }

        Object v1 = d1.get(key);
        Object v2 = d2.get(key);

        if (Objects.equals(v1, v2)) {
            return List.of("    " + key + ": " + v1);
        }

        return List.of("  - " + key + ": " + v1, "  + " + key + ": " + v2);
    }

    @Override
    public Integer call() {
        try {
            var data1 = Parser.getData(filepath1);
            var data2 = Parser.getData(filepath2);

            String diff = buildDiff(data1, data2);
            System.out.println(diff);

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
