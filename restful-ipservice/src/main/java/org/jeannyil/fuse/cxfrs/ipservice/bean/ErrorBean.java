package org.jeannyil.fuse.cxfrs.ipservice.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class ErrorBean {
	@JsonProperty
	private Map<String, String> inputParameters;
	@JsonProperty
	private String errorMessage;
	
	/**
	 * Constructor
	 */
	public ErrorBean() {}

	/**
	 * @return the inputParameters
	 */
	@JsonAnyGetter
	public Map<String, String> getInputParameters() {
		return inputParameters;
	}

	/**
	 * Set the inputParameters
	 * @param inputParameters
	 */
	public void setInputParameters(Map<String, String> inputParameters) {
		this.inputParameters = inputParameters;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Set the errorMessage
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
