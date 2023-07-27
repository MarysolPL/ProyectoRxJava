package com.mh.erp.products.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductType {
    private Long productTypeId;
    private String code;
    @NotBlank
    private String name;
    private String encriptedCode;
    private String ean;
    private Integer isActive;
    //@PositiveBigDecimal
    private BigDecimal salesPrice;
    //@PositiveBigDecimal
    private BigDecimal salesPriceWithTax;
    private Integer canBeSold = 1;
    private Integer isDeleted = 0;
    private String productTypeLocation; 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;
    
    public ProductType() {
	}
    
    public ProductType(Long productTypeId) {
    	this.productTypeId = productTypeId;
	}
    
	public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEncriptedCode() {
		return encriptedCode;
	}
	public void setEncriptedCode(String encriptedCode) {
		this.encriptedCode = encriptedCode;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public BigDecimal getSalesPriceWithTax() {
		return salesPriceWithTax;
	}
	public void setSalesPriceWithTax(BigDecimal salesPriceWithTax) {
		this.salesPriceWithTax = salesPriceWithTax;
	}
	public Integer getCanBeSold() {
		return canBeSold;
	}
	public void setCanBeSold(Integer canBeSold) {
		this.canBeSold = canBeSold;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getProductTypeLocation() {
		return productTypeLocation;
	}
	public void setProductTypeLocation(String productTypeLocation) {
		this.productTypeLocation = productTypeLocation;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(((ProductType)obj).getProductTypeId().equals(this.getProductTypeId())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.productTypeId.intValue();
	}
	
	public void doDefault() {
        if (Objects.isNull(code)) {
            this.code = UUID.randomUUID().toString();
        }
    }
}
