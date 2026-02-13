package com.example.assetservice.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private String name;
    private String gstNumber;
    private String contactEmail;
    private String phone;
    private String address;
    private Boolean active;
}
