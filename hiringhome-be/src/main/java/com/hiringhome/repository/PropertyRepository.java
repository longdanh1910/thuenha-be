package com.hiringhome.repository;

import com.hiringhome.entity.Property;
import com.hiringhome.entity.enums.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findByAddressContainingIgnoreCase(String location, Pageable pageable);
    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
    Page<Property> findByOrderByAverageRatingDesc(Pageable pageable);
    Page<Property> findByOrderByTotalReviewsDesc(Pageable pageable);
}
