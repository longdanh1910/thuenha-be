//package com.hiringhome.repository;
//
//import com.hiringhome.entity.Property;
//import com.hiringhome.entity.PropertyStatus;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface PropertyRepository extends JpaRepository<Property, Long> {
//    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
//
//    Page<Property> findByOwnerId(Long ownerId, Pageable pageable);
//
//    @Query("SELECT p FROM Property p WHERE p.status = 'AVAILABLE' AND " +
//           "LOWER(p.address) LIKE LOWER(CONCAT('%', :location, '%'))")
//    Page<Property> searchByLocation(String location, Pageable pageable);
//
//    @Query("SELECT p FROM Property p ORDER BY p.totalBookings DESC")
//    List<Property> findTopBookedProperties(Pageable pageable);
//
//    @Query("SELECT p FROM Property p ORDER BY p.averageRating DESC")
//    List<Property> findTopRatedProperties(Pageable pageable);
//}
