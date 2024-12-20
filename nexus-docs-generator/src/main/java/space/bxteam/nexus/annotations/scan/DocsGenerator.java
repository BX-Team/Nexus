package space.bxteam.nexus.annotations.scan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import space.bxteam.nexus.annotations.scan.command.CommandResult;
import space.bxteam.nexus.annotations.scan.command.CommandScanResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class DocsGenerator {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("space.bxteam.nexus.core.Nexus");
        NexusScanner scanner = new NexusScanner(aClass.getClassLoader(), "space.bxteam.nexus.core");

        List<CommandResult> results = scanner.scan(new CommandScanResolver())
                .stream()
                .sorted(Comparator.comparing(CommandResult::name))
                .distinct()
                .toList();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter("assets" + File.separator + "commands.json")) {
            gson.toJson(results, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
