package com.example.assetservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assetservice.entities.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	Optional<Asset> findByAssetTag(String assetTag);
	Optional<Asset> findBySerialNumber(String serialNumber);
	List<Asset> findByCreatedBy(String createdBy);
}
