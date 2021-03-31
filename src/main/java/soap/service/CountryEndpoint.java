package soap.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import soap_service.GetCountryRequest;
import soap_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	
	private static final String NAMESPACE_URI = "http://soap-service";

	private CountryRepository countryRepository;

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {

		
		System.out.println("********** CLIENT_HOST/PORT "+this.httpRequest.getRemoteHost()+"/"+this.httpRequest.getRemotePort()+" **********");
		Enumeration<String> requestHeaderNames = httpRequest.getHeaderNames();
		
		System.out.println("========== HEADER CXF CLIENT START ==========");
		if (requestHeaderNames != null) {
			while (requestHeaderNames.hasMoreElements()) {
				String headerKey = requestHeaderNames.nextElement();
				System.out.println(headerKey+ ":" + httpRequest.getHeader(headerKey));
			}
		}
		System.out.println("========== HEADER CXF CLIENT END ==========");

		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}
}