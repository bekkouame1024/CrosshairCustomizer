package com.bekkouame1024.mod.crosshaircustomizer.utils;

import java.util.regex.Pattern;

public class FileNameValidator {
    private static final Pattern VALID_FILENAME_PATTERN = Pattern.compile("^[a-z0-9_]+\\.([pP][nN][gG])$");

    public static boolean isValidFileName(String fileName) {
        return VALID_FILENAME_PATTERN.matcher(fileName).matches();
        
    }
}
