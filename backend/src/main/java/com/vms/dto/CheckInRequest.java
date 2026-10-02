package com.vms.dto;

/**
 * Request body shape sent by the frontend on check-in:
 * { visitorId, purpose, location, temperature }
 * This avoids requiring the frontend to send a full nested Visitor/EntryLog object.
 */
public class CheckInRequest {
    private Long visitorId;
    private String purpose;
    private String location;
    private Double temperature;

    public Long getVisitorId() { return visitorId; }
    public void setVisitorId(Long visitorId) { this.visitorId = visitorId; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
}
