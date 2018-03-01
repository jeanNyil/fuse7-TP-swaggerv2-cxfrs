package org.jeannyil.fuse.cxfrs.ipservice.processor;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.jeannyil.fuse.cxfrs.ipservice.constants.GeoLocationParametersEnum;

public class FreeGeoIpRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.setPattern(ExchangePattern.InOut);
        Message inMessage = exchange.getIn();
        
        //creating the request
        String ip = inMessage.getBody(String.class);
        String type = inMessage.getHeader(GeoLocationParametersEnum.TYPE.toString(),String.class);

        MessageContentsList req = new MessageContentsList();
        req.add(type);
        req.add(ip);
        
        // set the operation name
        inMessage.setHeader(CxfConstants.OPERATION_NAME, "getGeoIp");
        // using the proxy client API
        inMessage.setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.FALSE);
        // Put the request in the message body
        inMessage.setBody(req);
	}	
}
