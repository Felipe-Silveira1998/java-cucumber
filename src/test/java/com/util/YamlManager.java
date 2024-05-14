package com.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class YamlManager {
    public static List<Object> readYaml(String yamlPath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File(yamlPath));
        LinkedHashMap<String, Object> yamlMap = yaml.load(inputStream);
        List<Object> listMap = new ArrayList<Object>();
        for (String key : yamlMap.keySet()) {
            listMap.add(yamlMap.get(key));
        }
        return listMap;
    }
}
