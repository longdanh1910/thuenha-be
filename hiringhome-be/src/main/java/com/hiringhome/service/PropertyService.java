//package com.hiringhome.service;
//
//import com.hiringhome.dto.property.PropertyRequest;
//import com.hiringhome.dto.property.PropertyResponse;
//import com.hiringhome.entity.Property;
//import com.hiringhome.entity.PropertyImage;
//import com.hiringhome.entity.User;
//import com.hiringhome.repository.PropertyRepository;
//import com.hiringhome.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PropertyService {
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final String UPLOAD_DIR = "uploads/properties/";
//
//    public PropertyResponse createProperty(PropertyRequest request) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User owner = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Property property = new Property();
//        property.setOwner(owner);
//        property.setTitle(request.getTitle());
//        property.setDescription(request.getDescription());
//        property.setAddress(request.getAddress());
//        property.setPricePerNight(request.getPricePerNight());
//        property.setMaxGuests(request.getMaxGuests());
//        property.setBedrooms(request.getBedrooms());
//        property.setBathrooms(request.getBathrooms());
//        property.setLatitude(request.getLatitude());
//        property.setLongitude(request.getLongitude());
//        property.setStatus(PropertyStatus.AVAILABLE);
//
//        return mapToResponse(propertyRepository.save(property));
//    }
//
//    public void uploadImages(Long propertyId, List<MultipartFile> images) {
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//
//        images.forEach(image -> {
//            try {
//                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
//                Path uploadPath = Paths.get(UPLOAD_DIR);
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//                Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
//
//                PropertyImage propertyImage = new PropertyImage();
//                propertyImage.setProperty(property);
//                propertyImage.setImageUrl(UPLOAD_DIR + fileName);
//                property.getImages().add(propertyImage);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload image", e);
//            }
//        });
//
//        propertyRepository.save(property);
//    }
//
//    public Page<PropertyResponse> searchProperties(String location, Pageable pageable) {
//        Page<Property> properties = location != null && !location.isEmpty()
//                ? propertyRepository.searchByLocation(location, pageable)
//                : propertyRepository.findByStatus(PropertyStatus.AVAILABLE, pageable);
//
//        return properties.map(this::mapToResponse);
//    }
//
//    public PropertyResponse getProperty(Long id) {
//        Property property = propertyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//        return mapToResponse(property);
//    }
//
//    public List<PropertyResponse> getTopRatedProperties() {
//        return propertyRepository.findTopRatedProperties(Pageable.ofSize(5))
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    public List<PropertyResponse> getMostBookedProperties() {
//        return propertyRepository.findTopBookedProperties(Pageable.ofSize(5))
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    public PropertyResponse updatePropertyStatus(Long id, PropertyStatus status) {
//        Property property = propertyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//        property.setStatus(status);
//        return mapToResponse(propertyRepository.save(property));
//    }
//
//    private PropertyResponse mapToResponse(Property property) {
//        PropertyResponse response = new PropertyResponse();
//        response.setId(property.getId());
//        response.setTitle(property.getTitle());
//        response.setDescription(property.getDescription());
//        response.setAddress(property.getAddress());
//        response.setPricePerNight(property.getPricePerNight());
//        response.setStatus(property.getStatus());
//        response.setMaxGuests(property.getMaxGuests());
//        response.setBedrooms(property.getBedrooms());
//        response.setBathrooms(property.getBathrooms());
//        response.setLatitude(property.getLatitude());
//        response.setLongitude(property.getLongitude());
//        response.setAverageRating(property.getAverageRating());
//        response.setTotalBookings(property.getTotalBookings());
//        response.setOwnerName(property.getOwner().getUsername());
//        response.setOwnerId(property.getOwner().getId());
//        response.setImageUrls(property.getImages().stream()
//                .map(PropertyImage::getImageUrl)
//                .collect(Collectors.toList()));
//        return response;
//    }
//}
