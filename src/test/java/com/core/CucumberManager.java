package com.core;

import io.cucumber.core.cli.Main;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CucumberManager {
    private static Class<?> cucumberRunnerClass;
    private static String featureStr = "";

    public static void setFeatureToRunner(String feature) {
        featureStr = feature == null ? "" : feature;
    }

    public static void runCucumberCLI() {
        String feature = featureStr.equals("") ? featureStr : "/" + featureStr + ".feature";

        List<String> cucumberTagsList = new ArrayList<String>();
        String cucumberTags = System.getProperty("cucumberTags") == null ? "" : System.getProperty("cucumberTags");
        cucumberTagsList = Arrays.asList(cucumberTags.split("\\s*\\,\\s*"));
        List<String> cucumberTagsArgsList = new ArrayList<String>();
        if (cucumberTagsList.size() > 0 && !cucumberTagsList.get(0).equals("")) {
            for (String cucumberTag : cucumberTagsList) {
                cucumberTagsArgsList.add("-t");
                cucumberTagsArgsList.add(cucumberTag);
            }
        }

        String[] cucumberArgs = { "-g" , "src/test/resources/features" + feature, "--plugin", "pretty",
                "--plugin", "json:target/cucumber/cucumber.json", "--plugin", "html:target/cucumber/cucumber", "-m" };

        cucumberArgs = ArrayUtils.addAll(cucumberArgs, cucumberTagsArgsList.stream().toArray(String[]::new));
        Main.run(cucumberArgs, ClassLoader.getSystemClassLoader());
    }

    public static void setUpCucumberOptionsConfig() throws IOException {
        String featureFileName = featureStr.equals("") ? "" : featureStr.replaceAll("\\.feature", "") + ".feature";

        featureFileName = featureFileName.equals("") ? "" : File.separator + featureFileName;

        System.setProperty("cucumber.options", "src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "features" + featureFileName);
    }
}