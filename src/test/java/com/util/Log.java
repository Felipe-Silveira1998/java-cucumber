package com.util;

import com.constants.BasePageConstants;
import com.enums.LogType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.function.Predicate;

public class Log {
    private static final Logger logger = Logger.getLogger("");

    /**
     * Receive and show all execution logs
     *
     * @param message Log message
     * @param type    Log type
     */
    public static void sendLog(String message, LogType type) {
        if (System.getProperty("saveConsoleAsTxt") != null
                && System.getProperty("saveConsoleAsTxt").equalsIgnoreCase("true")) {
//  		System.out.println(new Timestamp(System.currentTimeMillis()) + " - " + message);
        }
        switch (type) {
            case ERROR:
                logger.error(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case FATAL:
                logger.fatal(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case TRACE:
                logger.trace(message);
                break;
        }
    }

    /**
     * Get the second method that is being executed in runtime and return its name
     */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Replace the ^ and & to Empty String for creating the log message
     *
     * @param log The log message
     */
    private static String replaceStartEndRegex(String log) {
        return log.replace(BasePageConstants.CIRCUNFLEJO, StringUtils.EMPTY).replace(BasePageConstants.DOLAR, StringUtils.EMPTY);
    }

    /**
     * Create the log message based on cucumber annotation value, matching the
     * current method that is being executed with the size of the object[] passed as
     * parameters
     *
     * @param className  The class name
     * @param methodName The method name
     * @param objects    Array of objects containning the values to create the log
     */
    public static String createLog(Class<?> className, String methodName, Object[] objects) throws Exception {
        Predicate<Method> isGetNameEqualsMethodName = e -> e.getName().equals(methodName);
        Predicate<Method> isParameterCountEqualsObjectsLength = e -> e.getParameterCount() == objects.length;
        Method method = Arrays.stream(className.getMethods())
                .filter(isGetNameEqualsMethodName.and(isParameterCountEqualsObjectsLength)).findFirst()
                .orElseThrow(Exception::new);
        String logValue = getAnnotationValue(method);
        String annotation = StringManager.substringByRegex(
                method.getAnnotations()[0].annotationType().toString().replaceAll(".class", ""), "[^.]*$");
        logValue = replaceStartEndRegex(logValue);
        String timestamp = new Timestamp(System.currentTimeMillis()) + " - ";
        String whitespaces = StringUtils.repeat(" ", (timestamp.length() + 4));
        for (int i = 0; i < method.getParameterCount(); i++) {
            if (objects[i].toString().contains("\n")) {
                String[] parameterRows = objects[i].toString().split("\\r?\\n");
                objects[i] = "";
                for (int c = 0; c < parameterRows.length; c++) {
                    objects[i] += c < (parameterRows.length - 1) ? whitespaces + parameterRows[c] + "\n"
                            : whitespaces + parameterRows[c];
                }

                logValue += "\n" + whitespaces + "\"\"\"\n" + objects[i] + "\n" + whitespaces + "\"\"\"\n";
            } else {
                logValue = logValue.replaceFirst(BasePageConstants.REGEX, "[" + objects[i] + "]");
            }
        }
        logValue = annotation + " " + logValue;
        return logValue;
    }

    /**
     * Create the log message based on cucumber annotation value, matching the
     * current method that is being executed
     *
     * @param className  The class name
     * @param methodName The method name
     */
    String createLog(Class<?> className, String methodName) throws Exception {
        Predicate<Method> isGetNameEqualsMethodName = e -> e.getName().equals(methodName);
        Method method = Arrays.stream(className.getMethods()).filter(isGetNameEqualsMethodName).findFirst()
                .orElseThrow(Exception::new);
        String logValue = getAnnotationValue(method);
        logValue = replaceStartEndRegex(logValue);
        return logValue;
    }

    /**
     * Get the annotation value of a method
     *
     * @param method The object of the method
     * @throws Exception
     */
    private static String getAnnotationValue(Method method) throws Exception {
        if (method.isAnnotationPresent(Given.class)) {
            return method.getAnnotation(Given.class).value();
        } else if (method.isAnnotationPresent(When.class)) {
            return method.getAnnotation(When.class).value();
        } else if (method.isAnnotationPresent(And.class)) {
            return method.getAnnotation(And.class).value();
        } else if (method.isAnnotationPresent(Then.class)) {
            return method.getAnnotation(Then.class).value();
        } else if (method.isAnnotationPresent(Dado.class)) {
            return method.getAnnotation(Dado.class).value();
        } else if (method.isAnnotationPresent(Quando.class)) {
            return method.getAnnotation(Quando.class).value();
        } else if (method.isAnnotationPresent(E.class)) {
            return method.getAnnotation(E.class).value();
        } else if (method.isAnnotationPresent(Então.class)) {
            return method.getAnnotation(Então.class).value();
        } else if (method.isAnnotationPresent(Entao.class)) {
            return method.getAnnotation(Entao.class).value();
        } else
            throw new Exception("Anotação do cucumber inesperada");
    }

}