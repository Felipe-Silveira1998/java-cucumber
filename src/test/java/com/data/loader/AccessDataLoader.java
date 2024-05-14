package com.data.loader;

import com.constants.TestRunnerConstants;
import com.util.PropertiesManager;
import com.util.YamlManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AccessDataLoader {
	private static Map<?, ?> environmentDataMap;

	public static String getData(String... filterValues) throws FileNotFoundException, IOException {
		PropertiesManager enviromentProp = new PropertiesManager(
				TestRunnerConstants.ACCESS_PROPERTIES_PATH + File.separator + "access_envs.properties");
		loadData(enviromentProp.getProperties().getProperty("test_execution_environment"));
		return searchData(filterValues);

	}

	private static String searchData(String... filterValues) {
		List<String> filterList = Arrays.asList(filterValues);
		String userProfile = new String();
		for (String filter : filterList) {
			if (filterList.lastIndexOf(filter) == (filterList.size() - 1)) {
				userProfile = environmentDataMap.get(filter).toString();
			} else {
				environmentDataMap = Map.class.cast(environmentDataMap.get(filter));
			}
		}
		return userProfile;
	}

	private static void loadData(String environment) throws FileNotFoundException {
		List<Object> yamlContent = YamlManager
				.readYaml(TestRunnerConstants.ACCESS_PROPERTIES_PATH + File.separator + "access_data.yaml");
		environmentDataMap = Map.class.cast(Map.class.cast(yamlContent.get(0)).get(environment));
	}
}
