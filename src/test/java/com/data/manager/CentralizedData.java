package com.data.manager;

import com.data.loader.AccessDataLoader;
import com.util.StringManager;

public class CentralizedData {
    public static boolean isCentralizedData(String data) {
        return StringManager.getListMatcherByRegex(data, "^\\$(\\(.*\\))?\\{.+}$|^v[A-Z].*$").size() > 0;
    }

    public static String getCentralizedArgumentValue(String centralizedArgument) {
        if (StringManager.getListMatcherByRegex(centralizedArgument, "^\\$\\{.+\\}$").size() > 0) {
            return centralizedArgument.replaceFirst("^\\$\\{", "").replaceAll("(\\})(?!.*\\1)", "");
        } else if (StringManager.getListMatcherByRegex(centralizedArgument, "^\\$(\\(.*\\))\\{.+\\}$").size() > 0) {
            String objectType = StringManager.substringByRegex(centralizedArgument, "^\\$\\(.*\\)")
                    .replaceAll("^\\$?\\(|\\)$", "").toLowerCase();
            String dataValuePath = centralizedArgument.replaceFirst("^\\$?\\(.*\\)", "").replaceAll("^\\$?\\{|\\}$",
                    "");
            return objectType + ">>" + dataValuePath;
        } else if (StringManager.getListMatcherByRegex(centralizedArgument, "^v[A-Z].*$").size() > 0) {
            return centralizedArgument.replaceFirst("^v(?=[A-Z])", "");
        } else {
            return centralizedArgument;
        }
    }

    public static String getCentralizedAccessData(String centralizedArgument) {
        String[] filterValues = new String[0];
        if (isCentralizedData(centralizedArgument)) {
            filterValues = getCentralizedArgumentValue(centralizedArgument).split("::");
        } else {
            new Exception("invalid argument '" + centralizedArgument + "'!").printStackTrace();
        }
        String userProfile = new String();
        try {
            userProfile = AccessDataLoader.getData(filterValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProfile;
    }
}
