package com.mh.erp.products.infrastructure.postgredb.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import org.springframework.beans.BeanUtils;
import com.mh.erp.products.domain.model.ProductType;
import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;
import com.mh.erp.products.infrastructure.api.dtos.ProductSummaryDto;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "tb_product_type")
public class ProductTypeEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROD_SEQ")
	@SequenceGenerator(name="PROD_SEQ", sequenceName="seq_tb_product", allocationSize=1)
	@Column("tb_product_type_id")
    private Long productTypeId;
    private String code;
    private String name;
    
    @Column("is_active")
    private Integer isActive = 1;
    
    @Column("is_deleted")
    private Integer isDeleted = 0;
    
    @Column("created")
    private LocalDateTime created;
    
    @Column("updated")
    private LocalDateTime updated;
    
    public ProductTypeEntity() {
    	
	}
    
    public ProductTypeEntity(Long productTypeId) {
    	this.productTypeId = productTypeId;
	}
    
    public ProductTypeEntity(ProductType product) {
     	BeanUtils.copyProperties(product, this);
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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}


	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
	
	public ProductType toProductType() {
    	 ProductType product = new ProductType();
         BeanUtils.copyProperties(this, product);
         return product;
	}
	 
	public ProductSummaryDto toProductSummayDto() {
		ProductSummaryDto productSummaryDto = new ProductSummaryDto();
        BeanUtils.copyProperties(this, productSummaryDto);
        return productSummaryDto;
	}
	
	public ProductIdAndNameDto toProductIdAndNameDto() {
		ProductIdAndNameDto ProductIdAndNameDto = new ProductIdAndNameDto();
        BeanUtils.copyProperties(this, ProductIdAndNameDto);
        return ProductIdAndNameDto;
	}
	
}
