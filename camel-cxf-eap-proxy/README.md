Camel CXF Proxy on EAP Example
=============

This is a sample project to create a CXF proxy using Camel on EAP 

Prerequisites
=============

* Minimum of Java 1.8
* Maven 3.2 or greater
* Fuse 7.1 running on JBoss EAP 7.1


Getting started at the Command Line
------------------------------------

Start the application server from the command line

For Linux:

    $JBOSS_HOME/bin/standalone.sh -c standalone-full.xml

For Windows:

    %JBOSS_HOME%\bin\standalone.bat -c standalone-full.xml


Building the application
------------------------

To build the application do:

    mvn clean install


Deploying the application
-------------------------

To deploy the application to a running application server do:

    mvn clean package wildfly:deploy


Access the application
----------------------

The proxied webservice is located at

	http://localhost:8080/camel-example-cxf-proxy/webservices/incident

<http://localhost:8080/camel-example-cxf-proxy/webservices/incident>

The real webservice is located at

	http://localhost:8080/real-web-service
	
<http://localhost:8080/real-web-service>

To make a SOAP call open soapUI or another SOAP query tool and create a new
project w/WSDL of <http://localhost:8080/camel-example-cxf-proxy/webservices/incident?wsdl>.
Then make SOAP requests of this format:

	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
	                  xmlns:rep="http://reportincident.example.camel.apache.org">
	   <soapenv:Header/>
	   <soapenv:Body>
	      <rep:inputReportIncident>
	         <incidentId></incidentId>
	         <incidentDate>2011-11-18</incidentDate>
	         <givenName>Bob</givenName>
	         <familyName>Smith</familyName>
	         <summary>Bla bla</summary>
	         <details>More bla</details>
	         <email>your@email.org</email>
	         <phone>12345678</phone>
	      </rep:inputReportIncident>
	   </soapenv:Body>
	</soapenv:Envelope>


Undeploying the application
---------------------------

    mvn wildfly:undeploy


Further reading
---------------

Apache Camel documentation

http://camel.apache.org/
