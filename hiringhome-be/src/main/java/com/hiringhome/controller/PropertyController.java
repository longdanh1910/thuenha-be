package com.hiringhome.controller;

import com.hiringhome.dto.property.CreatePropertyRequest;
import com.hiringhome.dto.property.PropertyResponse;
import com.hiringhome.dto.property.UpdatePropertyRequest;
import com.hiringhome.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreatePropertyRequest request) {
        return ResponseEntity.ok(propertyService.createProperty(userDetails.getUsername(), request));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<List<String>> uploadImages(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(propertyService.uploadImages(userDetails.getUsername(), id, files));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponse> updateProperty(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody UpdatePropertyRequest request) {
        return ResponseEntity.ok(propertyService.updateProperty(userDetails.getUsername(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        propertyService.deleteProperty(userDetails.getUsername(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyResponse>> searchProperties(
            @RequestParam(required = false) String location,
            Pageable pageable) {
        return ResponseEntity.ok(propertyService.searchProperties(location, pageable));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<PropertyResponse>> getTopRatedProperties(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getTopRatedProperties(pageable));
    }

    @GetMapping("/top-booked")
    public ResponseEntity<List<PropertyResponse>> getTopBookedProperties(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getTopBookedProperties(pageable));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<PropertyResponse>> getAvailableProperties(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAvailableProperties(pageable));
    }
}
