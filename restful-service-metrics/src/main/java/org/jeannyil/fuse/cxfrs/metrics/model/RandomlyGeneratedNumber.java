package org.jeannyil.fuse.cxfrs.metrics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "number",
    "isEven"
})
public class RandomlyGeneratedNumber {
    @JsonProperty("number")
    int number;

    @JsonProperty("isEven")
    Boolean isEven;

    @JsonProperty("number")
    public int getNumber() {
        return number;
    }

    @JsonProperty("isEven")
    public void setNumber(int number) {
        this.number = number;
    }

    @JsonProperty("isEven")
    public Boolean getEven() {
        return isEven;
    }

    @JsonProperty("isEven")
    public void setEven(Boolean even) {
        isEven = even;
    }
}
