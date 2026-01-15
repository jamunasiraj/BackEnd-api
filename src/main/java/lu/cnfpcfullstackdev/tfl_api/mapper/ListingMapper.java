package lu.cnfpcfullstackdev.tfl_api.mapper;

import lu.cnfpcfullstackdev.tfl_api.dto.request.CreateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.request.UpdateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.ListingResponseDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.BusinessSummaryDTO;
import lu.cnfpcfullstackdev.tfl_api.entity.TflListing;

public class ListingMapper {
    
    // Convert Entity → Response DTO
    public static ListingResponseDTO toResponseDTO(TflListing listing) {
        if (listing == null) {
            return null;
        }
        
        ListingResponseDTO dto = new ListingResponseDTO();
        dto.setId(listing.getId());
        dto.setTitle(listing.getTitle());
        dto.setDescription(listing.getDescription());
        dto.setQuantity(listing.getQuantity());
        dto.setExpiryDate(listing.getExpiryDate());
        dto.setPickupTime(listing.getPickupTime());
        dto.setStatus(listing.getStatus());
        
        // Map business information
        if (listing.getBusiness() != null) {
            BusinessSummaryDTO businessDTO = 
                new BusinessSummaryDTO(
                    listing.getBusiness().getId(),
                    listing.getBusiness().getBusinessName(),
                    listing.getBusiness().getBusinessAddress()
                );
            dto.setBusiness(businessDTO);
        }
        
        return dto;
    }
    
    // Convert CreateRequestDTO → Entity
    public static TflListing toEntity(CreateListingRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TflListing listing = new TflListing();
        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setQuantity(dto.getQuantity());
        listing.setExpiryDate(dto.getExpiryDate());
        listing.setPickupTime(dto.getPickupTime());
        
        return listing;
    }
    
    // Update existing entity from UpdateRequestDTO
    public static void updateEntity(TflListing listing, UpdateListingRequestDTO dto) {
        if (listing == null || dto == null) {
            return;
        }
        
        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setQuantity(dto.getQuantity());
        listing.setExpiryDate(dto.getExpiryDate());
        listing.setPickupTime(dto.getPickupTime());
    }
}