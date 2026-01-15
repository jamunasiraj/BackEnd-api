package lu.cnfpcfullstackdev.tfl_api.service;

import lu.cnfpcfullstackdev.tfl_api.dto.request.CreateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.request.UpdateListingRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.ListingResponseDTO;
import lu.cnfpcfullstackdev.tfl_api.mapper.ListingMapper;
import lu.cnfpcfullstackdev.tfl_api.entity.ListingStatus;
import lu.cnfpcfullstackdev.tfl_api.entity.TflListing;
import lu.cnfpcfullstackdev.tfl_api.entity.User;
import lu.cnfpcfullstackdev.tfl_api.entity.UserRole;
import lu.cnfpcfullstackdev.tfl_api.exception.ForbiddenException;
import lu.cnfpcfullstackdev.tfl_api.exception.ResourceNotFoundException;
import lu.cnfpcfullstackdev.tfl_api.exception.UnauthorizedException;
import lu.cnfpcfullstackdev.tfl_api.repository.TflListingRepository;
import lu.cnfpcfullstackdev.tfl_api.repository.UserRepository;
import lu.cnfpcfullstackdev.tfl_api.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TflListingService {
    @Autowired
    private TflListingRepository repository;

    @Autowired
    private UserRepository userRepository; 

    // GET all - returns DTOs
    public List<ListingResponseDTO> getAllListings() {
        return repository.findAll()
                .stream()
                .map(ListingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // GET by ID - returns DTO
    public ListingResponseDTO getListingById(Long id) {
        TflListing listing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing", id));
        return ListingMapper.toResponseDTO(listing);
    }

    public List<ListingResponseDTO> getListingsByBusiness(Long businessId) {
        return repository.findByBusinessId(businessId)
                .stream()
                .map(ListingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ListingResponseDTO> getExpiringSoon(int hours) {
        LocalDateTime cutoff = LocalDateTime.now().plusHours(hours);
        return repository.findByExpiryDateBefore(cutoff)
                .stream()
                .map(ListingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

   
   // SEARCH - returns DTOs
    public List<ListingResponseDTO> searchByStatus(String status) {
        if (status != null) {
            ListingStatus listingStatus = ListingStatus.valueOf(status.toUpperCase());
            return repository.findByStatus(listingStatus)
                    .stream()
                    .map(ListingMapper::toResponseDTO)
                    .collect(Collectors.toList());
        }
        return getAllListings();
    }

    public ListingResponseDTO createListing(CreateListingRequestDTO dto) {
        Long currentUserId = UserPrincipal.getCurrentUserId();
        if (currentUserId == null) {
            throw new UnauthorizedException("User not authenticated");
        }
        
        User business = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId));
        
        if (business.getRole() != UserRole.BUSINESS) {
            throw new ForbiddenException("Only businesses can create listings");
        }
        
        // Convert DTO to entity
        TflListing listing = ListingMapper.toEntity(dto);
        listing.setBusiness(business);
        listing.setStatus(ListingStatus.AVAILABLE);
        
        // Save
        TflListing saved = repository.save(listing);
        
        return ListingMapper.toResponseDTO(saved);
    }
    
    public ListingResponseDTO updateListing(Long id, UpdateListingRequestDTO dto) {
        Long currentUserId = UserPrincipal.getCurrentUserId();
    
        TflListing listing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing", id));
        
        if (!listing.getBusiness().getId().equals(currentUserId)) {
            throw new ForbiddenException("You can only update your own listings");
        }
        
        // Update
        ListingMapper.updateEntity(listing, dto);
        TflListing updated = repository.save(listing);
        
        return ListingMapper.toResponseDTO(updated);
    }
    
    public void deleteListing(Long id) {
        Long currentUserId = UserPrincipal.getCurrentUserId();
    
        TflListing listing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing", id));
        
        if (!listing.getBusiness().getId().equals(currentUserId)) {
            throw new ForbiddenException("You can only delete your own listings");
        }
        
        repository.deleteById(id);
    }

    public void adminDeleteListing(Long id) {
        // Admin can delete any listing without ownership check
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Listing", id);
        }
        repository.deleteById(id);
    }
}
