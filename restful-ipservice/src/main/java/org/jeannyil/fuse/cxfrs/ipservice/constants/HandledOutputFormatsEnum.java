package org.jeannyil.fuse.cxfrs.ipservice.constants;

public enum HandledOutputFormatsEnum {
	
	XML("xml"),
	JSON("json");
	
	private String handledType;
	
	/**
	 * Constructor
	 * @param handledType
	 */
	private HandledOutputFormatsEnum(String handledType) {
		this.setHandledType(handledType);
	}
	
	/**
	 * @return all possible values
	 *
	public String getAllValues() {
		StringBuffer sb = new StringBuffer();
		for (HandledOutputFormatsEnum format : HandledOutputFormatsEnum.values())
			sb.append(" - " + format.getHandledType());
		return sb.toString();
	} /
	
	/**
	 * @return the handledType
	 */
	public String getHandledType() {
		return handledType;
	}

	/**
	 * @param handledType the handledType to set
	 */
	public void setHandledType(String handledType) {
		this.handledType = handledType;
	}
	
	/**
	 * Override the toString() method
	 */
	@Override
	public String toString() {
		return getHandledType();
	}

}
