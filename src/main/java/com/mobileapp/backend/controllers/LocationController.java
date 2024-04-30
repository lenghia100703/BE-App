package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.location.AddLocationDto;
import com.mobileapp.backend.dtos.location.EditLocationDto;
import com.mobileapp.backend.dtos.location.LocationDto;
import com.mobileapp.backend.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping("")
    public PaginatedDataDto<LocationDto> getAllLocations(@RequestParam("page") int page) {
        return locationService.getAllLocations(page);
    }

    @GetMapping("/{id}")
    public CommonResponseDto<LocationDto> getLocationById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new LocationDto(locationService.getLocationById(id)));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteLocation(@PathVariable Long id) {
        return new CommonResponseDto<>(locationService.deleteLocation(id));
    }

    @PostMapping(value = "")
    public CommonResponseDto<LocationDto> createLocation(@RequestBody AddLocationDto addLocationDto) {
        return new CommonResponseDto<>(locationService.createLocation(addLocationDto));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editLocation(@PathVariable("id") Long id, @RequestBody EditLocationDto editLocationDto) {
        return new CommonResponseDto<>(locationService.editLocation(id, editLocationDto));
    }
}
