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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class APIController {

    private final static String template = "Hello, %s";
    private final static Helper.Properties props = Helper.loadProperties();

    @GetMapping("/")
    public apiResponse apiCall(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new apiResponse(String.format(template, name));
    }

    @GetMapping("/rz_call")
    public apiResponse rzCall() throws IOException {
        //prediction api takes POST requests
        //testing calling the routezero api from our api
        if(Helper.getApiKey().contains("Error")){
            return new apiResponse(Helper.getApiKey());
        }

        String jsonString = "{\"apiKey\":\""+Helper.getApiKey()+"\",\"id\":\"id\",\"journeys\":[{\"transport\":{\"type\":\"flight\"},\"distanceKm\":480,\"travellers\":2},{\"transport\":{\"type\":\"electricScooter\"},\"distanceKm\":2.1,\"travellers\":1}]}";
        String responseString = Helper.postJsonAsString(props.getEmissionsEndpoint(), jsonString);

        return new apiResponse(responseString);
    }

    @GetMapping("/api/get_predictions") //this is where the frontend will contact the server, we should require the frontend provide an API key to access this API
    public apiResponse getPredictions() {
        return new apiResponse("predictions");
    }

    @GetMapping("/properties")
    public Helper.Properties props(){
        return props;
    }
}
