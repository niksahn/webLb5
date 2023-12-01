package com.niksahn.laba5;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    /**
     * Время жизни сессии сейчас - 1 минута
     */
    public static final Long sessionLife = 100L * 60;

    public Constants() {}

    static {
        String filename = "./images/";
        Path pathToFile = Paths.get(filename);
        image_path = pathToFile.toAbsolutePath().toString();
    }

    public static String image_path;
}