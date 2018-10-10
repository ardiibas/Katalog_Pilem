package com.gamatechno.app.katalogpilem.utils;

import com.gamatechno.app.katalogpilem.network.MovieClient;
import com.gamatechno.app.katalogpilem.network.ServiceGenerator;

public class ApiUtils {

    public static final String BASE_URL_API = "https://api.themoviedb.org/";

    public static MovieClient getAPIService(){
        return ServiceGenerator.getClient(BASE_URL_API).create(MovieClient.class);
    }
}
