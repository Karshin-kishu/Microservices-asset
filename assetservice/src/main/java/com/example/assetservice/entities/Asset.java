package com.example.assetservice.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assets")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Asset {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Unique tag (e.g., "ASSET-0001")
	    @Column(nullable = false, unique = true)
	    private String assetTag;

	    @Column(nullable = false)
	    private String name;        // "Dell Laptop"

	    @Column(nullable = false)
	    private String type;        // "Laptop", "Router" etc.

	    @Column(nullable = false, unique = true)
	    private String serialNumber;

	    private LocalDate purchaseDate;
	    private LocalDate warrantyEndDate;

	    @Column(nullable = false)
	    private String status;      // ACTIVE / IN_REPAIR / RETIRED

	    private String location;

	    // comes from JWT introspection: authentication.getName()
	    @Column(nullable = false, updatable = false)
	    private String createdBy;

	    @Column(nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    // Relationships
	    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id", nullable = false)
	    private Category category;

	    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	    @JoinColumn(name = "vendor_id", nullable = false)
	    private Vendor vendor;

	    @PreUpdate
	    public void onUpdate() {
	        this.updatedAt = LocalDateTime.now();
	    }
	
}
