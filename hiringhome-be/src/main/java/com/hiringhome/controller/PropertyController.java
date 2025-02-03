//package com.hiringhome.controller;
//
//import com.hiringhome.dto.property.PropertyRequest;
//import com.hiringhome.dto.property.PropertyResponse;
//import com.hiringhome.service.PropertyService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/properties")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
//public class PropertyController {
//    private final PropertyService propertyService;
//
//    @PostMapping
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<PropertyResponse> createProperty(@Valid @RequestBody PropertyRequest request) {
//        return ResponseEntity.ok(propertyService.createProperty(request));
//    }
//
//    @PostMapping("/{id}/images")
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<Void> uploadImages(@PathVariable Long id, @RequestParam("images") List<MultipartFile> images) {
//        propertyService.uploadImages(id, images);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<PropertyResponse>> getAllProperties(
//            @RequestParam(required = false) String location,
//            Pageable pageable) {
//        return ResponseEntity.ok(propertyService.searchProperties(location, pageable));
//    }
//
//    @GetMapping("/top-rated")
//    public ResponseEntity<List<PropertyResponse>> getTopRatedProperties() {
//        return ResponseEntity.ok(propertyService.getTopRatedProperties());
//    }
//
//    @GetMapping("/most-booked")
//    public ResponseEntity<List<PropertyResponse>> getMostBookedProperties() {
//        return ResponseEntity.ok(propertyService.getMostBookedProperties());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
//        return ResponseEntity.ok(propertyService.getPropertyById(id));
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<PropertyResponse> updateProperty(
//            @PathVariable Long id,
//            @Valid @RequestBody PropertyRequest request) {
//        return ResponseEntity.ok(propertyService.updateProperty(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
//        propertyService.deleteProperty(id);
//        return ResponseEntity.ok().build();
//    }
//}
