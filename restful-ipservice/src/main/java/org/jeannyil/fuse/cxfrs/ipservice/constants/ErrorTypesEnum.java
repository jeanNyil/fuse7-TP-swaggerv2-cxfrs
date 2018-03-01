package org.jeannyil.fuse.cxfrs.ipservice.constants;

public enum ErrorTypesEnum {
	VALIDATION_ERROR("validation-error"),
	ALLOTHER_ERROR("all-other-errors");
	
	private String errorType;
	
	private ErrorTypesEnum(String errorType) {
		this.setErrorType(errorType);
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * Override the toString() method
	 */
	@Override
	public String toString() {
		return getErrorType();
	}

}
