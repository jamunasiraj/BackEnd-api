package lu.cnfpcfullstackdev.tfl_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lu.cnfpcfullstackdev.tfl_api.entity.ListingStatus;
import lu.cnfpcfullstackdev.tfl_api.entity.TflListing;

@Repository
public interface TflListingRepository extends JpaRepository<TflListing, Long> {
    // Spring Data JPA provides basic CRUD methods automatically

    List<TflListing> findByStatus(ListingStatus status);

    // Find by business
    List<TflListing> findByBusinessId(Long businessId);
    
    // Find available listings by business
    List<TflListing> findByBusinessIdAndStatus(Long businessId, ListingStatus status);
    
    // Find listings expiring soon
    List<TflListing> findByExpiryDateBefore(LocalDateTime dateTime);
    
    // Find by status and expiring after certain date
    List<TflListing> findByStatusAndExpiryDateAfter(ListingStatus status, LocalDateTime dateTime);
}
