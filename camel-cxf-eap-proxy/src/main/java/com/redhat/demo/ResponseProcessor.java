/*
 * #%L
 * Wildfly Camel :: Example :: Camel CXF JAX-WS
 * %%
 * Copyright (C) 2013 - 2016 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.redhat.demo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.example.reportincident.OutputReportIncident;

public class ResponseProcessor implements Processor {
    /**
     * Simple processor to return a response.
     *
     * 
     */
    @Override
    public void process(Exchange exchange) throws Exception {
    	OutputReportIncident out = new OutputReportIncident();
        out.setCode("OK;");
        
        exchange.getOut().setBody(out);
    }
}
