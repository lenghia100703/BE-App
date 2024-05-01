package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.location.AddLocationDto;
import com.mobileapp.backend.dtos.location.EditLocationDto;
import com.mobileapp.backend.dtos.location.LocationDto;
import com.mobileapp.backend.entities.LocationEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserService userService;

    public PaginatedDataDto<LocationDto> getAllLocations(int page) {
        List<LocationEntity> allLocations = locationRepository.findAll();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<LocationEntity> locationPage = locationRepository.findAll(pageable);

            List<LocationEntity> location = locationPage.getContent();

            return new PaginatedDataDto<>(location.stream().map(LocationDto::new).toList(), page, allLocations.toArray().length);
        } else {
            return new PaginatedDataDto<>(allLocations.stream().map(LocationDto::new).toList(), 1, allLocations.toArray().length);
        }
    }

    public LocationEntity getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "News not found!"));
    }

    public LocationDto createLocation(AddLocationDto addLocationDto) {
        LocationEntity location = new LocationEntity();
        location.setName(addLocationDto.getName());
        location.setDescription(addLocationDto.getDescription());
        location.setLatitude(addLocationDto.getLatitude());
        location.setLongitude(addLocationDto.getLongitude());
        location.setCreatedAt(new Date(System.currentTimeMillis()));
        location.setCreatedBy(userService.getCurrentUser().getEmail());

        return new LocationDto(locationRepository.save(location));
    }

    public String deleteLocation(Long id) {
        LocationEntity location = locationRepository.getById(id);
        if (location == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        locationRepository.delete(location);

        return "Deleted successfully";
    }

    public String editLocation(Long id, EditLocationDto editLocationDto) {
        LocationEntity location = locationRepository.getById(id);
        if (location == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        location.setName(editLocationDto.getName());
        location.setDescription(editLocationDto.getDescription());
        location.setLatitude(editLocationDto.getLatitude());
        location.setLongitude(editLocationDto.getLongitude());
        location.setUpdatedAt(new Date(System.currentTimeMillis()));
        location.setUpdatedBy(userService.getCurrentUser().getEmail());

        locationRepository.save(location);

        return "Edited successfully";
    }
}
