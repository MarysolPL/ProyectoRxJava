package com.mh.erp.products.infrastructure.api.dtos;

public class ProductTypeIdAndNameDto {
	private Long productTypeId;
	private String code;
	private String name;

	public Long getProductId() {
		return productTypeId;
	}

	public void setProductId(Long productTypeId) {
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
}
