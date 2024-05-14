package com.runners;

import com.core.DriverFactory;
import com.core.Hooks;
import io.cucumber.core.cli.Main;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RunnerCucumberSuiteCLI {
    public static String featuresPath;

    @Test
    public void run() throws Exception {
        //System.setProperty("disableExecutionResult", "true");
        String driverPath = System.getProperty("user.dir") + File.separator + "driver" + File.separator
                + DriverFactory.driverName;
        Process p = Runtime.getRuntime().exec("chmod +x " + driverPath);
        p.waitFor();
        Hooks.setExcutionResultTime(new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date()));
        String cucumberTags = System.getProperty("cucumberTags") != null ? System.getProperty("cucumberTags") : "";
        List<String> cucumberTagsList = new ArrayList<String>();
        cucumberTagsList = Arrays.asList(cucumberTags.split("\\s\\,"));
        List<String> cucumberTagsArgsList = new ArrayList<String>();
        if (cucumberTagsList.size() > 0 && !cucumberTagsList.get(0).equals("")) {
            for (String cucumberTag : cucumberTagsList) {
                cucumberTagsArgsList.add("-t");
                cucumberTagsArgsList.add(cucumberTag);
            }
        }

        String[] cucumberArgs = {"-g", "com.core", "-g", "com.pages",
                "-g", "com.steps",
                "src/test/resources/features", "--plugin", "pretty", "--plugin", "json:target/cucumber/cucumber.json",
                "--plugin", "html:target/cucumber/cucumber", "-m"};
        cucumberArgs = ArrayUtils.addAll(cucumberArgs, cucumberTagsArgsList.stream().toArray(String[]::new));
        Main.run(cucumberArgs, ClassLoader.getSystemClassLoader());
    }
}