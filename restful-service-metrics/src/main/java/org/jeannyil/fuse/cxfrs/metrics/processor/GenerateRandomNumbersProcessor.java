package org.jeannyil.fuse.cxfrs.metrics.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.jeannyil.fuse.cxfrs.metrics.constants.GenerateNumbersParametersEnum;
import org.jeannyil.fuse.cxfrs.metrics.exceptions.InputParameterValidationException;
import org.jeannyil.fuse.cxfrs.metrics.model.RandomlyGeneratedNumber;
import org.jeannyil.fuse.cxfrs.metrics.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This camel processor generates random numbers according to the request count and range parameters
 */
public class GenerateRandomNumbersProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message inMessage = exchange.getIn();

		Integer requestCount = inMessage.getHeader(GenerateNumbersParametersEnum.COUNT.toString(),Integer.class);
		
		// The 'count' query parameter is mandatory and must be greater than 0
		if (requestCount == null || requestCount.intValue() <= 0)
			throw new InputParameterValidationException("The 'count' query parameter is mandatory and must be > 0!");

		// Set the count as an exchange property for later metrics usage
		exchange.setProperty(GenerateNumbersParametersEnum.COUNT.toString(), requestCount);

        Integer requestRange = inMessage.getHeader(GenerateNumbersParametersEnum.RANGE.toString(),Integer.class);

        // The 'range' query parameter is mandatory and must be greater than 0
        if (requestRange == null || requestRange.intValue() <= 0)
            throw new InputParameterValidationException("The 'range' query parameter is mandatory and must be > 0!");

		// Generate random numbers and prepare the response POJO
		Response rep = new Response();
		rep.setCount(requestCount.intValue());
		rep.setRange(requestRange.intValue());
		List<RandomlyGeneratedNumber> randomlyRandomlyGeneratedNumbers = new ArrayList<RandomlyGeneratedNumber>();
		for (int i = 0; i < requestCount.intValue(); i++) {
			RandomlyGeneratedNumber randomNumberObject = new RandomlyGeneratedNumber();
			int number = new Random().nextInt(requestRange.intValue()) + 1;
			randomNumberObject.setNumber(number);
			if ((number % 2) == 0) {
				randomNumberObject.setEven(true);
			} else {
                randomNumberObject.setEven(false);
			}
			randomlyRandomlyGeneratedNumbers.add(randomNumberObject);
		}
		rep.setRandomlyGeneratedNumbers(randomlyRandomlyGeneratedNumbers);
		inMessage.setBody(rep);
	}

}
