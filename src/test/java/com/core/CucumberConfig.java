package com.core;

import custom.cucumber.junit.CucumberOptions;
import custom.cucumber.junit.CustomCucumber;
import org.junit.runner.RunWith;


@RunWith(CustomCucumber.class)
@CucumberOptions(glue = {}, monochrome = true, strict = true, plugin = {
        "html:target/cucumber/cucumber", "json:target/cucumber/cucumber.json" })
public class CucumberConfig {
}
