package org.jeannyil.fuse.cxfrs.metrics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "count",
        "range",
        "randomlyGeneratedNumbers"
})
public class Response {
	@JsonProperty("count")
	int count;
    @JsonProperty("range")
    int range;
	@JsonProperty("randomlyGeneratedNumbers")
	List<RandomlyGeneratedNumber> randomlyGeneratedNumbers = new ArrayList<RandomlyGeneratedNumber>();

    @JsonProperty("count")
    public int getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(int count) {
        this.count = count;
    }

    @JsonProperty("range")
    public int getRange() {
        return range;
    }

    @JsonProperty("range")
    public void setRange(int range) {
        this.range = range;
    }

    @JsonProperty("randomlyGeneratedNumbers")
    public List<RandomlyGeneratedNumber> getRandomlyGeneratedNumbers() {
        return randomlyGeneratedNumbers;
    }

    @JsonProperty("randomlyGeneratedNumbers")
    public void setRandomlyGeneratedNumbers(List<RandomlyGeneratedNumber> randomlyGeneratedNumbers) {
        this.randomlyGeneratedNumbers = randomlyGeneratedNumbers;
    }
}
