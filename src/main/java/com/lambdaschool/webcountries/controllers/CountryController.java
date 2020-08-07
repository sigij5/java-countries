package com.lambdaschool.webcountries.controllers;


import com.lambdaschool.webcountries.models.Country;
import com.lambdaschool.webcountries.repositories.CountryRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation(){
        List<Country> countryList = new ArrayList<>();
        long totalPop = 0;
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        for(Country c : countryList){
            totalPop = c.getPopulation() + totalPop;
        }
        System.out.println("Total Population is " + totalPop);
        return new ResponseEntity<>("Status OK", HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minPopulation(){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maxPopulation(){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c2.getPopulation(), c1.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> medianPopulation(){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));
        int totalCountries = countryList.size();
        int medianCountry = (1 + totalCountries) / 2;
        return new ResponseEntity<>(countryList.get(medianCountry - 1), HttpStatus.OK);
    }

}
