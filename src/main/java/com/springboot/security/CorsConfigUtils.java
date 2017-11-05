package com.springboot.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsConfigUtils {

    private static String METHODS = "POST, GET, PUT, OPTIONS, DELETE, PATCH";
    private static String ORIGIN = "http://127.0.0.1:4200";
    private static String HEADERS = "Authorization, authorization, " +
            "Origin, X-Requested-With, Content-Type, Accept, X-XSRF-TOKEN, credential";

    public static void config(HttpServletResponse response, HttpServletRequest request){

        response.setHeader("Access-Control-Allow-Origin", ORIGIN);
        response.setHeader("Access-Control-Allow-Methods", METHODS);
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", HEADERS);
        response.setHeader("Access-Control-Expose-Headers", HEADERS);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
    }
}
