package com.hiringhome.service;

import com.hiringhome.dto.property.CreatePropertyRequest;
import com.hiringhome.dto.property.PropertyResponse;
import com.hiringhome.dto.property.UpdatePropertyRequest;
import com.hiringhome.entity.Property;
import com.hiringhome.entity.PropertyImage;
import com.hiringhome.entity.User;
import com.hiringhome.entity.enums.PropertyStatus;
import com.hiringhome.repository.PropertyRepository;
import com.hiringhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "uploads/properties/";

    public PropertyResponse createProperty(String username, CreatePropertyRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = new Property();
        property.setOwner(user);
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setAddress(request.getAddress());
        property.setPrice(request.getPrice());
        property.setArea(request.getArea());
        property.setNumberOfRooms(request.getNumberOfRooms());
        property.setPropertyType(request.getPropertyType());
        property.setAmenities(request.getAmenities());
        property.setStatus(PropertyStatus.AVAILABLE);

        return toPropertyResponse(propertyRepository.save(property));
    }

    public PropertyResponse updateProperty(String username, Long propertyId, UpdatePropertyRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own properties");
        }

        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setAddress(request.getAddress());
        property.setPrice(request.getPrice());
        property.setArea(request.getArea());
        property.setNumberOfRooms(request.getNumberOfRooms());
        property.setPropertyType(request.getPropertyType());
        property.setAmenities(request.getAmenities());
        property.setStatus(request.getStatus());

        return toPropertyResponse(propertyRepository.save(property));
    }

    public List<String> uploadImages(String username, Long propertyId, List<MultipartFile> files) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You can only upload images to your own properties");
        }

        List<String> imageUrls = new ArrayList<>();
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);

                String imageUrl = "/api/uploads/properties/" + fileName;
                PropertyImage propertyImage = new PropertyImage();
                propertyImage.setProperty(property);
                propertyImage.setImageUrl(imageUrl);
                property.getImages().add(propertyImage);

                imageUrls.add(imageUrl);
            }

            propertyRepository.save(property);
            return imageUrls;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }
    }

    public void deleteProperty(String username, Long propertyId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own properties");
        }

        propertyRepository.delete(property);
    }

    public Page<PropertyResponse> searchProperties(String location, Pageable pageable) {
        if (location != null && !location.isEmpty()) {
            return propertyRepository.findByAddressContainingIgnoreCase(location, pageable)
                    .map(this::toPropertyResponse);
        }
        return propertyRepository.findAll(pageable)
                .map(this::toPropertyResponse);
    }

    public List<PropertyResponse> getTopRatedProperties(Pageable pageable) {
        return propertyRepository.findByOrderByAverageRatingDesc(pageable)
                .stream()
                .map(this::toPropertyResponse)
                .toList();
    }

    public List<PropertyResponse> getTopBookedProperties(Pageable pageable) {
        return propertyRepository.findByOrderByTotalReviewsDesc(pageable)
                .stream()
                .map(this::toPropertyResponse)
                .toList();
    }

    public Page<PropertyResponse> getAvailableProperties(Pageable pageable) {
        return propertyRepository.findByStatus(PropertyStatus.AVAILABLE, pageable)
                .map(this::toPropertyResponse);
    }

    private PropertyResponse toPropertyResponse(Property property) {
        return PropertyResponse.builder()
                .id(property.getId())
                .title(property.getTitle())
                .description(property.getDescription())
                .address(property.getAddress())
                .price(property.getPrice())
                .area(property.getArea())
                .numberOfRooms(property.getNumberOfRooms())
                .propertyType(property.getPropertyType())
                .amenities(property.getAmenities())
                .status(property.getStatus())
                .averageRating(property.getAverageRating())
                .totalReviews(property.getTotalReviews())
                .images(property.getImages().stream().map(PropertyImage::getImageUrl).toList())
                .ownerName(property.getOwner().getFullName())
                .ownerAvatar(property.getOwner().getAvatarUrl())
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }
}
