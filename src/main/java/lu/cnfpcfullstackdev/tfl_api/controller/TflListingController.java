package lu.cnfpcfullstackdev.tfl_api.controller;

import lu.cnfpcfullstackdev.tfl_api.dto.request.CreateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.request.UpdateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.ListingResponseDTO;
import lu.cnfpcfullstackdev.tfl_api.service.TflListingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class TflListingController {
    @Autowired
    private TflListingService service;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ListingResponseDTO>> getAllListings() {
        return ResponseEntity.ok(service.getAllListings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ListingResponseDTO> getListingById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getListingById(id));
    }

    @Operation(
        summary = "Create new food listing",
        description = "Creates a new food listing. Only authenticated BUSINESS users can create listings.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<ListingResponseDTO> createListing(@Valid @RequestBody CreateListingRequestDTO dto) {  
        ListingResponseDTO created = service.createListing(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ListingResponseDTO>> searchByStatus(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(service.searchByStatus(status));
    }

    @GetMapping("/business/{businessId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ListingResponseDTO>> getListingsByBusiness(
            @PathVariable Long businessId) {
        return ResponseEntity.ok(service.getListingsByBusiness(businessId));
    }

    @GetMapping("/expiring-soon")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ListingResponseDTO>> getExpiringSoon(
            @RequestParam(defaultValue = "24") int hours) {
        return ResponseEntity.ok(service.getExpiringSoon(hours));
    }

    @Operation(
        summary = "Update food listing",
        description = "Updates an existing food listing. Only the business that owns the listing can update it.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<ListingResponseDTO> updateListing(
            @PathVariable Long id,
            @Valid @RequestBody UpdateListingRequestDTO dto) {
        return ResponseEntity.ok(service.updateListing(id, dto));
    }

    @Operation(
        summary = "Delete food listing",
        description = "Deletes a food listing. Only the business that owns the listing can delete it.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        service.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
