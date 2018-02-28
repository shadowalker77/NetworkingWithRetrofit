package com.alirezabdn.networking;

/**
 * Created by Administrator on 2/28/2018.
 */

public class Core {
    private static String BASE_URL = "";

    public static void initialize(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
