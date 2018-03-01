package org.jeannyil.fuse.cxfrs.metrics.constants;

public enum GenerateNumbersParametersEnum {
	COUNT("count"),
	RANGE("range");

	private String inputParameter;

	/**
	 * Constructor
	 * @param inputParameter
	 */
	private GenerateNumbersParametersEnum(String inputParameter) {
		this.setInputParameter(inputParameter);
	}

	/**
	 * @return the inputParameter
	 */
	public String getInputParameter() {
		return inputParameter;
	}

	/**
	 * @param inputParameter the inputParameter to set
	 */
	public void setInputParameter(String inputParameter) {
		this.inputParameter = inputParameter;
	}
	
	/**
	 * Override the toString() method
	 */
	@Override
	public String toString() {
		return getInputParameter();
	}

}
