//package com.hiringhome.repository;
//
//import com.hiringhome.entity.Booking;
//import com.hiringhome.entity.BookingStatus;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface BookingRepository extends JpaRepository<Booking, Long> {
//    Page<Property> findByTenantId(Long tenantId, Pageable pageable);
//
//    Page<Property> findByPropertyOwnerId(Long ownerId, Pageable pageable);
//
//    List<Booking> findByPropertyIdAndStatus(Long propertyId, BookingStatus status);
//
//    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId " +
//           "AND ((b.checkInDate BETWEEN :startDate AND :endDate) " +
//           "OR (b.checkOutDate BETWEEN :startDate AND :endDate))")
//    List<Booking> findOverlappingBookings(Long propertyId, LocalDateTime startDate, LocalDateTime endDate);
//
//    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.property.owner.id = :ownerId " +
//           "AND b.status = 'COMPLETED' AND YEAR(b.checkOutDate) = :year AND MONTH(b.checkOutDate) = :month")
//    Double calculateMonthlyIncome(Long ownerId, int year, int month);
//}
