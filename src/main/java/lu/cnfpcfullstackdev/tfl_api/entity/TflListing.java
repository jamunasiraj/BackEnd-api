package lu.cnfpcfullstackdev.tfl_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TflListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer quantity;
    private LocalDateTime expiryDate;
    private String pickupTime;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    //Many-to-One relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")  // FK column in food_listing table
    private User business;

    public TflListing() {
    }

    public TflListing(Long id, String title, String description, Integer quantity, LocalDateTime expiryDate, String pickupTime, ListingStatus status, User business) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.pickupTime = pickupTime;
        this.status = status;
        this.business = business;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ListingStatus getStatus() {
        return this.status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    public User getBusiness() {
        return this.business;
    }

    public void setBusiness(User business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", pickupTime='" + getPickupTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", business='" + getBusiness() + "'" +
            "}";
    }   
}
