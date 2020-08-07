package com.lambdaschool.webcountries.controllers;


import com.lambdaschool.webcountries.models.Country;
import com.lambdaschool.webcountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountries(List<Country> countryList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();

        for(Country c : countryList){
            if(tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }

//    private List<Country find>

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> countriesByFirstLetter(@PathVariable char letter){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        List<Country> rtnList = findCountries(countryList, c -> c.getName().toLowerCase().charAt(0) == letter);
        rtnList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

//        System.out.println(rtnList);
//        System.out.println("Name starting with " + letter );
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

}
