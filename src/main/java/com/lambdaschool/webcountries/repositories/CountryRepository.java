package com.lambdaschool.webcountries.repositories;

import com.lambdaschool.webcountries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
