package com.hiringhome.repository;

import com.hiringhome.entity.Booking;
import com.hiringhome.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByBooking(Booking booking);
    
    Page<Review> findByBooking_Property_IdOrderByCreatedAtDesc(Long propertyId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.booking.property.id = :propertyId")
    Double calculateAverageRatingByPropertyId(@Param("propertyId") Long propertyId);

    @Modifying
    @Query("UPDATE Property p SET p.averageRating = :rating WHERE p.id = :propertyId")
    void updatePropertyRating(@Param("propertyId") Long propertyId, @Param("rating") Double rating);
}
