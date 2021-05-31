package com.genehelix.controllers.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    private RestTemplate mRestTemplate;

    @GetMapping("/countries")
    public List<Object> getCountries(){

        String URL= "https://restcountries.eu/rest/v2/all";

        Object[] objects= mRestTemplate.getForObject(URL, Object[].class);

        return Arrays.asList(objects);
    }


}
