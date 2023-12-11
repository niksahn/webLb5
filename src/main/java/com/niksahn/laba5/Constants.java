package com.niksahn.laba5;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    /**
     * Время жизни сессии сейчас - 1 минута
     */
    public static final Long sessionLife = 100*60L;

    public Constants() {
    }

    static {
        String filename = "./images/";
        Path pathToFile = Paths.get(filename);
        image_path = pathToFile.toAbsolutePath().toString();
        avatar_path = "/avatar/";
        course_path = "/course/";
        news_path = "/news/";

    }

    public static String image_path;
    public static String avatar_path;
    public static String course_path;
    public static String news_path;

    public static String defaultImageType = ".png";
}