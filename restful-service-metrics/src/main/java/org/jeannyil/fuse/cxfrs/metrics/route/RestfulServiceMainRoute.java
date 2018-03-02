package org.jeannyil.fuse.cxfrs.metrics.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jeannyil.fuse.cxfrs.metrics.constants.ErrorTypesEnum;
import org.jeannyil.fuse.cxfrs.metrics.constants.UtilHeadersEnum;

public class RestfulServiceMainRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// Error handling
		errorHandler(defaultErrorHandler().maximumRedeliveries(0));
		onException(Exception.class)
				.handled(true) // Suppressing exception rethrow to the caller
				.logStackTrace(true)
				.logExhausted(true)
				.logHandled(true)
				.setProperty(UtilHeadersEnum.ERRORTYPE.toString(), simple(ErrorTypesEnum.ALLOTHER_ERROR.toString()))
				// Set the exception message and build the ErrorBean
				.transform().simple("${exception.message}")
				.process("buildErrorBeanProcessor")
				// Transform the ErrorBean message to JSON format
				.marshal().json(JsonLibrary.Jackson, true)
				// Prepare an exception RESTful response to caller
				.process("prepareRestResponseProcessor")
                // Collect counter metrics on requests that failed for all other reasons
                .toD("metrics:counter:${routeId}.other-ko");

		/**
		 *  Route that consumes the RESTful service requests and routes them to the appropriate
		 *  operation for processing.
		 */
		from("cxfrs:bean:restfulService?bindingStyle=SimpleConsumer")
				.routeId("{{camel.name.route}}-main")
				.streamCaching() // Enable stream-caching
				.log(LoggingLevel.INFO, "Received RESTful request - Headers: ${headers} \n body: ${body}")
				// Call operation dynamically
				.toD("direct:${header.operationName}")
				.log(LoggingLevel.INFO, "The exchange: ${exchange}")
				// Prepare successful RESTful response
				.process("prepareRestResponseProcessor")
                // Collect counter metrics on successfully processed exchanges
                .toD("metrics:counter:${routeId}.ok");
	}

}
