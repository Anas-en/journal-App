package com.anas.journalApp.api.weather;

import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class WeatherResponse {
    private Current current;

    @Data
    public class Current {

        private Integer temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private Integer feelslike;

        @JsonProperty("is_day")
        private String isDay;
    }

}
