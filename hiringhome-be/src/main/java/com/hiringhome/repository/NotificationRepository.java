//package com.hiringhome.repository;
//
//import com.hiringhome.entity.Notification;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
//
//    long countByUserIdAndIsReadFalse(Long userId);
//
//    void deleteByUserIdAndCreatedAtBefore(Long userId, LocalDateTime date);
//}
