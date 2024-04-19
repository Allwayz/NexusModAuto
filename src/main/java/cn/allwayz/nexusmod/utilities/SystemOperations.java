package cn.allwayz.nexusmod.utilities;

import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author allwayz
 * @create 2024-04-19 19:32
 */
@Component
public class SystemOperations {
    @Resource
    ProcessBuilder processBuilder;

    public void wget(String... command) {
        String[] commands = new String[command.length + 1];
        commands[0] = "wget";
        for (int i = 1; i < commands.length; i++) {
            commands[i] = command[i - 1];
        }
        process(processBuilder.command(commands));
    }

    public void unzip(String... fileName) {
        String[] commands = {"tar", "-xf", "./mod/*.zip"};
        process(processBuilder.command(commands));
    }

    private void process(ProcessBuilder processBuilder) {
        try {
            System.out.println("正在执行： " + processBuilder.command());
            processBuilder.directory(new File("/Users/allwayz/Documents/git/NexusModAuto"));
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void script(String... script) {
        for (String s : script) {

            process(processBuilder.command(s));
        }
    }

    public void cd(String path) {
        process(processBuilder.command("cd", path));
    }

    public void ls(String path) {
        process(processBuilder.command("ls", path));
    }

    public void sh(String shell) {
        process(processBuilder.command("sh", shell));
    }
}
