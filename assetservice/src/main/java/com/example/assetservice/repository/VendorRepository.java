package com.example.assetservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assetservice.entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	 Optional<Vendor> findByName(String name);
	    Optional<Vendor> findByGstNumber(String gstNumber);
}
