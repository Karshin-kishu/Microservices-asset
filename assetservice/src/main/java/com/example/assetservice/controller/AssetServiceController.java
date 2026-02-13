package com.example.assetservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assetservice.dto.AssetRequestDTO;
import com.example.assetservice.dto.AssetResponseDTO;
import com.example.assetservice.service.AssetService;

@RestController
@RequestMapping("/assetservice")
public class AssetServiceController {
	
	@Autowired
	private AssetService assetService;
	
	
		@GetMapping("/assets")
		public List<?> myAssets(Authentication authentication) {
			String email = authentication.getName(); // <-- email from introspection
			// use email in service queries (createdBy, ownership etc.)
			return null;
		}
	
	 	@PostMapping("/create")
	    public ResponseEntity<AssetResponseDTO> create(@RequestBody AssetRequestDTO dto,
	                                                  Authentication authentication) {
	        return ResponseEntity.ok(assetService.create(dto, authentication));
	    }

	    @GetMapping
	    public ResponseEntity<List<AssetResponseDTO>> listMine(Authentication authentication) {
	        return ResponseEntity.ok(assetService.listMine(authentication));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<AssetResponseDTO> getById(@PathVariable Long id,
	                                                    Authentication authentication) {
	        return ResponseEntity.ok(assetService.getById(id, authentication));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<AssetResponseDTO> update(@PathVariable Long id,
	                                                   @RequestBody AssetRequestDTO dto,
	                                                   Authentication authentication) {
	        return ResponseEntity.ok(assetService.update(id, dto, authentication));
	    }

	    @PatchMapping("/{id}/status")
	    public ResponseEntity<AssetResponseDTO> patchStatus(@PathVariable Long id,
	                                                        @RequestBody Map<String, String> body,
	                                                        Authentication authentication) {
	        String status = body.get("status");
	        return ResponseEntity.ok(assetService.patchStatus(id, status, authentication));
	    }
}
