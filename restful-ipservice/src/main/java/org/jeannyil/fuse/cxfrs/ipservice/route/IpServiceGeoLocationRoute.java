package org.jeannyil.fuse.cxfrs.ipservice.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jeannyil.fuse.cxfrs.ipservice.constants.ErrorTypesEnum;
import org.jeannyil.fuse.cxfrs.ipservice.constants.GeoLocationParametersEnum;
import org.jeannyil.fuse.cxfrs.ipservice.exception.TypeValidationException;

public class IpServiceGeoLocationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Error handling
        errorHandler(defaultErrorHandler().maximumRedeliveries(0));
        onException(TypeValidationException.class)
                .handled(true) // Flag exception as handled
                .logStackTrace(true)
                .logExhausted(true)
                .logHandled(true)
                .doTry()
                .setProperty("errorType", constant(ErrorTypesEnum.VALIDATION_ERROR.toString()))
                // Set the exception message and build the ErrorBean
                .transform().simple("${exception.message}")
                .process("buildErrorBeanProcessor")
                // Transform the ErrorBean message to JSON format
                .marshal().json(JsonLibrary.Jackson, true);
        onException(Exception.class)
                .handled(true) // Suppressing exception rethrow to the caller
                .logStackTrace(true)
                .logExhausted(true)
                .logHandled(true)
                .setProperty("errorType", constant(ErrorTypesEnum.ALLOTHER_ERROR.toString()))
                // Set the exception message and build the ErrorBean
                .transform().simple("${exception.message}")
                .process("buildErrorBeanProcessor")
                // Transform the ErrorBean message to JSON format
                .marshal().json(JsonLibrary.Jackson, true);

        /**
         *  Route that implements the getGeoLocation REST service operation.
         *  This route calls the remote FreeGeoIp RESTful service and returns the raw response
         */
        from("direct:getGeoLocation")
                .routeId("{{camel.name.route}}-getGeoLocation")
                .log(LoggingLevel.INFO, "Starting the getGeoLocation RESTful service operation...")
                // Validate input request
                .process("validateRequestProcessor")
                .setProperty(GeoLocationParametersEnum.TYPE.toString(),
                        header(GeoLocationParametersEnum.TYPE.toString()))
                .setProperty(GeoLocationParametersEnum.IP.toString(),
                        header(GeoLocationParametersEnum.IP.toString()))
                .removeHeaders("*") // Reset all the exchange message headers before proceeding to CXFRS client component
                .setHeader(GeoLocationParametersEnum.TYPE.toString(),
                        exchangeProperty(GeoLocationParametersEnum.TYPE.toString()))
                .setBody(exchangeProperty(GeoLocationParametersEnum.IP.toString()))
                .process("freeGeoIpRequestProcessor")
                .to("cxfrs:bean:freeGeoIpRsClient")
                .log(LoggingLevel.INFO, "FreeGeoIp Service Response:\n ${body}")
                .log(LoggingLevel.INFO, "The getGeoLocation RESTful service operation is DONE");
    }
}
