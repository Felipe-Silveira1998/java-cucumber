package com.core;

import com.models.CucumberScenario;
import com.pages.BasePage;
import com.constants.TestRunnerConstants;
import com.util.GenerateEvidences;
import com.util.SpreadsheetManager;
import com.util.StringManager;
import com.util.FilesManager;
import cucumber.api.Result;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.constants.TestRunnerConstants.CHROME_PROFILE_DIR;

public class Hooks extends BasePage {
    public static String evidencesPath = null;
    public static String timeStampStart = null;
    private long initStopwatch, elapsedTime;
    private static SpreadsheetManager executionSheetManager;
    private static int executionRowStart = 1;
    private static String excutionResultTime;
    private static String executionSheetFileName;
    public static WebDriver webDriver;

    @After
    public void fechar(Scenario scenario) throws Exception {
        embedScreenshot(scenario);
        DriverFactory.killDriver();
        setElapsedTime(System.currentTimeMillis() - initStopwatch);
        setScenarioFinalResult(scenario);
        setScenarioExecutionResult(scenario);
        GenerateEvidences.saveEvidences(CucumberScenario.getScenarioName(), CucumberScenario.getFinalResult(), "Done",
                getElapsedTime());
    }

    public void embedScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void before(Scenario scenario) throws Exception {
        if (executionRowStart <= 1)
            executionSheetManager = new SpreadsheetManager(TestRunnerConstants.EXECUTION_SHEET_PATH);
        else
            executionSheetManager = new SpreadsheetManager(
                    TestRunnerConstants.EXECUTION_RESULT_DIRECTORY + File.separator + executionSheetFileName);
        webDriver = DriverFactory.getDriver();
        initStopwatch = System.currentTimeMillis();
        String dir = System.getProperty("user.dir");
        timeStampStart = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());
        setExcutionResultTime(timeStampStart);
        setFeatureFileNameFromScenarioId(scenario);
        setScenarioName(scenario);
        logScenarioInformations();
        evidencesPath = dir + File.separator + "output" + File.separator + timeStampStart + File.separator
                + CucumberScenario.getFeatureName() + File.separator + CucumberScenario.getScenarioName();
        GenerateEvidences.evidence_count = 0;

        executionSheetFileName = "ExecutionSheet " + getExcutionResultTime() + ".xlsx";
    }

    private static String getScenarioFinalResult(Scenario scenario) {
        Field field = FieldUtils.getField(((Scenario) scenario).getClass(), "stepResults", true);
        field.setAccessible(true);
        String finalResult = null;
        try {
            @SuppressWarnings("unchecked")
            ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
            for (Result result : results) {
                if (result.getError() != null) {
                    String[] resultErrorMessage = result.getError().getMessage().split("Build info:");
                    finalResult = resultErrorMessage[0].replaceFirst("([A-Z,a-z,0-9]+\\.?)+\\:\\s", "");
                    break;
                } else {
                    finalResult = "Done";
                }
            }
        } catch (Exception e) {
            System.out.printf("\nError while logging final result: ", e);
        }
        return finalResult;
    }

    public static void setScenarioExecutionResult(Scenario scenario) throws FileNotFoundException, IOException {
        String sheetName = "ExecutionResult";
        String finalResult;
        executionSheetManager.write(sheetName, executionRowStart,
                executionSheetManager.getColumnIndex("Scenario", sheetName), scenario.getName(), false);
        if (!CucumberScenario.getFinalResult().equals("Done")) {
            finalResult = "Failed";
        } else {
            finalResult = "Passed";
        }
        executionSheetManager.write(sheetName, executionRowStart,
                executionSheetManager.getColumnIndex("Final Result", sheetName), finalResult, false);
        executionSheetManager.write(sheetName, executionRowStart,
                executionSheetManager.getColumnIndex("Result", sheetName), CucumberScenario.getFinalResult(), false);
        executionSheetManager.saveToNewSpreadsheet(new File(TestRunnerConstants.EXECUTION_SHEET_PATH).getParent()
                + File.separator + executionSheetFileName);
        executionRowStart++;
    }

    private void setFeatureFileNameFromScenarioId(Scenario scenario) throws Exception {
        String rawFeatureName = scenario.getId().split(";")[0].replaceAll("\\-+", " ");
        List<String> featurePathSplitedList = Arrays.asList(rawFeatureName.split("(\\/|\\\\)"));
        int lastIndex = featurePathSplitedList.size() - 1;
        String featureName = StringManager.substringByRegex(featurePathSplitedList.get(lastIndex), "(.*)(?=\\.feature)")
                .replaceAll("\\s+$", "");
        if (featureName == null || featureName == "") {
            throw new Exception("The name of feature is not filled");
        }
        CucumberScenario.setFeatureName(featureName);
    }

    private void setScenarioName(Scenario scenario) {
        CucumberScenario.setScenarioName(scenario.getName().replaceAll("\"", "").replaceAll("\\.", ""));
    }

    private static void setScenarioFinalResult(Scenario scenario) {
        String finalResult = null;
        try {
            Class<?> clasz = ClassUtils.getClass("cucumber.runtime.java.JavaHookDefinition$ScenarioAdaptor");
            Field field = FieldUtils.getField(clasz, "scenario", true);
            field.setAccessible(true);
            Object objectScenario = field.get(scenario);
            Field fieldStepResults = objectScenario.getClass().getDeclaredField("stepResults");
            fieldStepResults.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<Result> results = (ArrayList<Result>) fieldStepResults.get(objectScenario);
            for (Result result : results) {
                if (result.getError() != null) {
                    String[] resultErrorMessage = result.getErrorMessage().split("Build info:");
                    finalResult = resultErrorMessage[0].contains("NullPointerException") ? "NullPointerException"
                            : resultErrorMessage[0].replaceFirst("([A-Z,a-z,0-9]+\\.?)+\\:\\s", "");
                    break;
                } else {
                    finalResult = "Done";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CucumberScenario.setFinalResult(finalResult);
    }

    /**
     * Delete temporary chrome profile files
     *
     * @throws Exception
     */
    public static void deleteTemporaryChromeProfileFiles() throws Exception {
        File[] files = FilesManager.getFileList(CHROME_PROFILE_DIR);
        File[] filesDefaultProfile = FilesManager.getFileList(CHROME_PROFILE_DIR + File.separator + "Default");
        ArrayList<String> fileListNotDelete = new ArrayList<String>(
                Arrays.asList("Default", "Local State", "Cookies", "Extensions"));
        Arrays.asList(files).stream().filter(file -> !fileListNotDelete.contains(file.getName()))
                .forEach(file -> FilesManager.deleteFile(file));
        Arrays.asList(filesDefaultProfile).stream().filter(file -> !fileListNotDelete.contains(file.getName()))
                .forEach(file -> FilesManager.deleteFile(file));
    }

    public void logScenarioInformations() {
        System.out.println("Feature: " + CucumberScenario.getFeatureName());
        System.out.println("Scenario: " + CucumberScenario.getScenarioName() + "\n");
    }

    public static String getExcutionResultTime() {
        return excutionResultTime;
    }

    public static void setExcutionResultTime(String excutionResultTime) {
        Hooks.excutionResultTime = excutionResultTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

}