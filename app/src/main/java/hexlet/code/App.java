package hexlet.code;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true, version = "1.0")
public class App implements Callable<Integer> {

    @Override
    public Integer call() {
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
