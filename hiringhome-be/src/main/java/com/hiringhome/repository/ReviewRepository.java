//package com.hiringhome.repository;
//
//import com.hiringhome.entity.Review;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ReviewRepository extends JpaRepository<Review, Long> {
//    Page<Review> findByPropertyId(Long propertyId, Pageable pageable);
//
//    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.property.id = :propertyId")
//    Double calculateAverageRating(Long propertyId);
//
//    boolean existsByPropertyIdAndUserId(Long propertyId, Long userId);
//}
