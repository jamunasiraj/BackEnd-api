package lu.cnfpcfullstackdev.tfl_api.dto.response;

public class BusinessSummaryDTO {
    private Long id;
    private String businessName;
    private String businessAddress;
    
    public BusinessSummaryDTO() {}
    
    public BusinessSummaryDTO(Long id, String businessName, String businessAddress) {
        this.id = id;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return this.businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
}