package lu.cnfpcfullstackdev.tfl_api.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateListingRequestDTO {
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Max(value = 1000, message = "Quantity cannot exceed 1000")
    private Integer quantity;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDateTime expiryDate;

    @NotBlank(message = "Pickup time is required")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Pickup time must be in format HH:MM-HH:MM (e.g., 17:00-18:00)")
    @NotNull(message = "Expiry date is required")
    private String pickupTime;

    // Empty constructor
    public CreateListingRequestDTO() {
    }

    public CreateListingRequestDTO(String title, String description, Integer quantity, LocalDateTime expiryDate, String pickupTime) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.pickupTime = pickupTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPickupTime() {
        return this.pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}