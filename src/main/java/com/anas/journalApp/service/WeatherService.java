package com.anas.journalApp.service;

import com.anas.journalApp.api.weather.WeatherResponse;
import com.anas.journalApp.cache.AppCache;
import com.anas.journalApp.constants.Placeholders;
import com.anas.journalApp.enums.AppConfigKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey ;



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    private static final String  API = "http://api.weatherstack.com/current?access_key=<APIKEY>&query=<CITY>";


    public WeatherResponse getWeather(String city){

        String finalAPI = appCache.APP_CACHE.get(AppConfigKey.WEATHER_API_BASE_URL.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY, apiKey);

        //repsonseEntity - > anyHeader or ALl ,

        //de-serialization the process of Converting JSON response into Java Objects  Serialiazation - > POJO to JSON


//        Another Implementation
//        public WeatherResponse getWeather(String city) {
//            String url = "https://api.example.com/weather?city={city}";
//
//            return restTemplate.getForObject(
//                    url,
//                    WeatherResponse.class,
//                    city
//            );

        WeatherResponse weatherResponse = redisService.get("Weather_of_"+city ,WeatherResponse.class);
        if(weatherResponse !=null){
            return weatherResponse;
        }else{
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body =  response.getBody();
            if(body != null){
                redisService.set("Weather_of_"+city,body , 300l);  //-1 -> for infinite time
            }
            return body;
        }

    }


    //example of POST call
    public void postWeather(){


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","value");

        String requestBody = "{\n" +
                "    \"username\":\"Anas\",\n" +
                "    \"password\":\"anas\"\n" +
                "}";

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
//        restTemplate.exchange(finalAPI,HttpMethod.POST,httpEntity,WeatherResponse.class );

        User user = (User) User.builder().username("Anas").password("anas").build();
        HttpEntity<User> httpEntity1 = new HttpEntity<>(user,httpHeaders);
    }

}
