package org.example.camel.servlet.proxy.client;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foresters.mq.model.CRMResponse;
import com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken;
import com.microsoft.schemas.crm._2007.webservices.CrmServiceSoap;

public class FbaCrmClient {
	
	private static Logger logger = LoggerFactory.getLogger(FbaCrmClient.class);

	public static void main(String[] args) throws JAXBException {
		
		File fXmlFile = new File("src/main/resources/CRMFBA_Request.xml");
		String input = FileUtils.getStringFromFile(fXmlFile);;
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(CrmServiceSoap.class);
		//factory.setAddress("http://fhfituatcrm01.forestersdev.local:5555/MSCrmServices/2007/CrmService.asmx");
		factory.setAddress("http://fhvmwcrms2.forestersdev.local:5555/MSCrmServices/2007/CrmService.asmx");
		CrmServiceSoap soapClient = (CrmServiceSoap) factory.create();
		
		CrmAuthenticationToken authenticationToken = new CrmAuthenticationToken();
		authenticationToken.setAuthenticationType(0);
		authenticationToken.setCrmTicket("");
		authenticationToken.setOrganizationName("FITQ9CRM");
		authenticationToken.setCallerId("00000000-0000-0000-0000-000000000000");
		
		Client client = ClientProxy.getClient(soapClient);
		// Create a list for holding all SOAP headers
		List<Header> headersList = new ArrayList<Header>();
		
		//JAXBContext jaxbContext = JAXBContext.newInstance("com.microsoft.schemas.crm._2007.coretypes"); 
		Header testSoapHeader1 = new Header(new QName("http://schemas.microsoft.com/crm/2007/WebServices", "CrmAuthenticationToken"), authenticationToken, new JAXBDataBinding(CrmAuthenticationToken.class));
		headersList.add(testSoapHeader1);
		
		client.getRequestContext().put(Header.HEADER_LIST, headersList);
		
		HTTPConduit http = (HTTPConduit) client.getConduit();
		AuthorizationPolicy policy = http.getAuthorization();
		policy.setUserName("FORESTERSDEV\\crmadmin");
		policy.setPassword("Pwd2011");
		//policy.setAuthorizationType("NTLM");
		
		//Turn off chunking so that NTLM can occur
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(36000);
		httpClientPolicy.setAllowChunking(false);
		http.setClient(httpClientPolicy);
		
		client.getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
		client.getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
		
		String response = null;
		response = soapClient.fetch(input);
		logger.debug("response from soap call " + response);
		
		JAXBContext jaxbContext2 = JAXBContext.newInstance(CRMResponse.class);
		Unmarshaller unmarshaller = jaxbContext2.createUnmarshaller();
		CRMResponse crmResponse = (CRMResponse) unmarshaller.unmarshal(new StringReader(response));
		
		logger.debug("Result size: " + crmResponse.getResults().size());
				
	}
}
