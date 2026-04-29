package hexlet.code;

import hexlet.code.diff.Differ;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class App implements Callable<Integer> {

    @Parameters(paramLabel = "filepath1",
            index = "0",
            description = "path to first file")
    private Path filepath1;

    @Parameters(paramLabel = "filepath2",
            index = "1",
            description = "path to second file")
    private Path filepath2;

    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            paramLabel = "format", arity = "1")
    private String format = "stylish";


    @Override
    public Integer call() {
        try {
            var data1 = Parser.getData(filepath1);
            var data2 = Parser.getData(filepath2);

            String diff = Differ.generate(data1, data2, format);
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
