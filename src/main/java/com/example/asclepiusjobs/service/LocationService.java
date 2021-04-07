package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.LocationDto;
import com.example.asclepiusjobs.model.Location;
import com.example.asclepiusjobs.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationDto convertLocationEntityToDto(Location locationEntity){
        LocationDto locationDto=new LocationDto();
        locationDto.setCountry(locationEntity.getCountry());
        locationDto.setCity(locationEntity.getCity());
        locationDto.setPostalCode(locationEntity.getPostalCode());
        locationDto.setStreet(locationEntity.getStreet());
        locationDto.setHouse(locationEntity.getHouse());
        locationDto.setApartment(locationEntity.getApartment());
        return locationDto;
    }

    public Location createLocationFromLocationDto(LocationDto dtoLocation){
        Location location= new Location();
        location.setCountry(dtoLocation.getCountry());
        location.setCity(dtoLocation.getCity());
        location.setStreet(dtoLocation.getStreet());
        location.setHouse(dtoLocation.getHouse());
        location.setApartment(dtoLocation.getApartment());
        location.setPostalCode(dtoLocation.getPostalCode());
        return locationRepository.save(location);
    }

    public Location saveOrUpdate(Location location){
        return locationRepository.save(location);
    }
}
