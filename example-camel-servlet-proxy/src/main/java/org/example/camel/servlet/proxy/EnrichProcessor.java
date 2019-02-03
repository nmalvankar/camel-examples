package org.example.camel.servlet.proxy;

import java.util.Map;
import java.util.TreeMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnrichProcessor implements Processor {

	private Logger logger = LoggerFactory.getLogger(EnrichProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		Map<String, String> headers = new TreeMap<>();
		
		// Add these headers to the exchange for the service call
		for (Map.Entry<String, String> entry : headers.entrySet())
			exchange.getIn().setHeader(entry.getKey(), entry.getValue());
	}

}