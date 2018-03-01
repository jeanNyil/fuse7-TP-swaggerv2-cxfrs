package org.jeannyil.fuse.cxfrs.metrics.constants;

public enum UtilHeadersEnum {
	ERRORTYPE("utilHeaders");

	private String utilHeaders;

	private UtilHeadersEnum(String utilHeaders) {
		this.setUtilHeaders(utilHeaders);
	}

	/**
	 * @return the utilHeaders
	 */
	public String getUtilHeaders() {
		return utilHeaders;
	}

	/**
	 * @param utilHeaders the utilHeaders to set
	 */
	public void setUtilHeaders(String utilHeaders) {
		this.utilHeaders = utilHeaders;
	}
	
	/**
	 * Override the toString() method
	 */
	@Override
	public String toString() {
		return getUtilHeaders();
	}

}
