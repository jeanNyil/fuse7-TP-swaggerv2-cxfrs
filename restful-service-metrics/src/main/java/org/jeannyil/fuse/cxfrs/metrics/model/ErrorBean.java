package org.jeannyil.fuse.cxfrs.metrics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorBean {
	@JsonProperty("errorMessage")
	private String errorMessage;
	
	/**
	 * Constructor
	 */
	public ErrorBean() {}

	/**
	 * @return the erroMessage
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
