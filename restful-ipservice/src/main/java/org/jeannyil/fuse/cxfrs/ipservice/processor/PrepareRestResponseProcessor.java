package org.jeannyil.fuse.cxfrs.ipservice.processor;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.jeannyil.fuse.cxfrs.ipservice.constants.ErrorTypesEnum;
import org.jeannyil.fuse.cxfrs.ipservice.constants.GeoLocationParametersEnum;
import org.jeannyil.fuse.cxfrs.ipservice.constants.UtilHeadersEnum;

public class PrepareRestResponseProcessor  implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
        Response response = convertToJaxRs(message,
                exchange.getProperty(GeoLocationParametersEnum.TYPE.toString(), String.class),
                exchange.getProperty(UtilHeadersEnum.ERRORTYPE.toString(), String.class));
        exchange.getIn().setBody(response);
	}

	/**
	 * Set the RESTful response body and the HTTP Content-Type header according the response type
	 * @param message
	 * @param responseMediaType
	 * @return
	 */
	private Response convertToJaxRs(Message message, String responseMediaType, String errorType) {
        
		String mediaType = responseMediaType.contains("json") ? 
				MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
		
		// In case of validation error - return BAD_REQUEST HTTP Code
        if (errorType != null && errorType.equalsIgnoreCase(ErrorTypesEnum.VALIDATION_ERROR.toString()))
        		return Response.status(Status.BAD_REQUEST).entity(message.getBody()).build();
        
        // In case of all other exceptions type - return INTERNAL_SERVER_ERROR HTTP Code
        if (errorType != null && errorType.equalsIgnoreCase(ErrorTypesEnum.ALLOTHER_ERROR.toString())) 
        		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message.getBody()).build();
        
        // For all other cases - return 200 HTTP code
        return  Response.ok(message.getBody(), mediaType).build();        	
    }
	
}
