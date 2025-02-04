package com.hiringhome.repository;

import com.hiringhome.entity.Booking;
import com.hiringhome.entity.Property;
import com.hiringhome.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByTenantOrderByCreatedAtDesc(User tenant, Pageable pageable);
    
    Page<Booking> findByPropertyOrderByCreatedAtDesc(Property property, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
           "WHERE b.property.id = :propertyId " +
           "AND b.status != 'CANCELLED' " +
           "AND ((b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn))")
    boolean existsOverlappingBooking(
            @Param("propertyId") Long propertyId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);
}
