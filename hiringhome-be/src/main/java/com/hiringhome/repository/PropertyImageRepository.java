package com.hiringhome.repository;

import com.hiringhome.entity.Property;
import com.hiringhome.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    @Modifying
    @Transactional
    void deleteByProperty(Property property);
}
