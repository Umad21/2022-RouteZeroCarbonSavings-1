package com.routezeroenterprise.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class testAPIController {

    private final static String template = "Hello, %s";
    private final static Helper.Properties props = Helper.loadProperties();

    @GetMapping("/")
    public testAPI apiCall(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new testAPI(String.format(template, name));
    }

    @GetMapping("/rz_call")
    public testAPI rzCall() throws IOException {
        //prediction api takes POST requests
        //testing calling the routezero api from our api
        if(Helper.getApiKey().contains("Error")){
            return new testAPI(Helper.getApiKey());
        }

        String jsonString = "{\"apiKey\":\""+Helper.getApiKey()+"\",\"id\":\"id\",\"journeys\":[{\"transport\":{\"type\":\"flight\"},\"distanceKm\":480,\"travellers\":2},{\"transport\":{\"type\":\"electricScooter\"},\"distanceKm\":2.1,\"travellers\":1}]}";
        String responseString = Helper.postJsonAsString(props.getEmissionsEndpoint(), jsonString);

        return new testAPI(responseString);
    }

    @GetMapping("/properties")
    public Helper.Properties props(){
        return props;
    }
}