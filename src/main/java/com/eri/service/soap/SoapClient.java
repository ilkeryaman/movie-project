package com.eri.service.soap;


import com.eri.generated.movieapi.stub.ListMoviesResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;

public class SoapClient extends WebServiceGatewaySupport {

    public ListMoviesResponse listMovies(String url, Object request){
        return (ListMoviesResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);
    }

    public void addMovie(String url, Object request) {
        JAXBElement res = (JAXBElement) getWebServiceTemplate().marshalSendAndReceive(url, request);
    }

    public void deleteMovie(String url, Object request) {
        JAXBElement res = (JAXBElement) getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}