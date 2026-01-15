package lu.cnfpcfullstackdev.tfl_api.controller;

import lu.cnfpcfullstackdev.tfl_api.entity.User;
import lu.cnfpcfullstackdev.tfl_api.repository.UserRepository;
import lu.cnfpcfullstackdev.tfl_api.service.TflListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // All methods require ADMIN role
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TflListingService listingService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Delete any listing (moderation)
    @DeleteMapping("/listings/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.adminDeleteListing(id); // New service method
        return ResponseEntity.noContent().build();
    }

    // Ban user
    @PostMapping("/users/{id}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long id) {
        // Implementation would add "banned" field to User entity
        return ResponseEntity.noContent().build();
    }
}