package org.jeannyil.fuse.cxfrs.metrics.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jeannyil.fuse.cxfrs.metrics.model.ErrorBean;


/**
 * This camel processor expects an error message as in the exchange body
 * As a result, the ErrorBean is populated in the exchange body
 */
public class BuildErrorBeanProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		// Build the ErrorBean
		ErrorBean errorBean = new ErrorBean();
		errorBean.setErrorMessage(exchange.getIn().getBody(String.class));
		
		// Set the exchange body with the new errorBean
		exchange.getIn().setBody(errorBean);
	}

}
