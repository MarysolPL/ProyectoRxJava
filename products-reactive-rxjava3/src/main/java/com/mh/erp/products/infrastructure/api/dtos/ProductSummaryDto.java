package com.mh.erp.products.infrastructure.api.dtos;

import java.math.BigDecimal;

public class ProductSummaryDto {
    private Long productId;
    private String code;
    private String name;
    private BigDecimal salesPrice;
    private BigDecimal salesPriceWithTax;
    //private Long productTypeId;
	 
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	/*public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}*/
	
}
