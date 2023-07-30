package ru.practice.server.model.wagon;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Wagon {
    @JsonProperty("wagonType")
    public abstract String getWagonType();
}
