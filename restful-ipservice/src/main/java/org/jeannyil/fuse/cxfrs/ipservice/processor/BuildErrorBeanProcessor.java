package org.jeannyil.fuse.cxfrs.ipservice.processor;

import java.util.Map;
import java.util.TreeMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jeannyil.fuse.cxfrs.ipservice.bean.ErrorBean;
import org.jeannyil.fuse.cxfrs.ipservice.constants.GeoLocationParametersEnum;


/**
 * This camel processor expects:
 * - Input parameters in the exchange headers
 * - Error message as the exchange message
 * As a result, the ErrorBean is populated in the exchange body
 */
public class BuildErrorBeanProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		// Input parameters map
		Map<String,String> inputParameters = new TreeMap<String,String>();
		inputParameters.put(GeoLocationParametersEnum.TYPE.toString(),
							exchange.getProperty(GeoLocationParametersEnum.TYPE.toString(), String.class));
		inputParameters.put(GeoLocationParametersEnum.IP.toString(),
							exchange.getProperty(GeoLocationParametersEnum.IP.toString(), String.class));
		
		// Build the ErrorBean
		ErrorBean errorBean = new ErrorBean();
		errorBean.setErrorMessage(exchange.getIn().getBody(String.class));
		errorBean.setInputParameters(inputParameters);
		
		// Set the exchange body with the new errorBean
		exchange.getIn().setBody(errorBean);
	}

}
