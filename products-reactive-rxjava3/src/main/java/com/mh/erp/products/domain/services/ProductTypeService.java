package com.mh.erp.products.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.domain.model.ProductType;
import com.mh.erp.products.domain.persistence.ProductTypePersistence;
import com.mh.erp.products.infrastructure.api.dtos.ProductTypeIdAndNameDto;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Service
public class ProductTypeService {
	private final ProductTypePersistence productTypePersistence;

	public ProductTypeService(ProductTypePersistence productTypePersistence) {
		this.productTypePersistence = productTypePersistence;
	}

	public Observable<ProductType> findAll(int limit) {
		return this.productTypePersistence.findAll(limit);
	}

	@Transactional
	public Maybe<ProductType> create(ProductType productType) {
		productType.setCreated(LocalDateTime.now());
		return this.productTypePersistence.create(productType);
	}

	public Maybe<ProductType> read(Long productTypeId) {
		return this.productTypePersistence.read(productTypeId);
	}

	@Transactional
	public Maybe<Integer> delete(Long productTypeId) {
		return this.productTypePersistence.delete(productTypeId);
	}

	@Transactional
	public Maybe<ProductType> update(Long productTypeId, ProductType product) {
		product.setProductTypeId(productTypeId);
		return this.productTypePersistence.update(product);
	}
	
	public Maybe<ProductType> findByCode(String code) {
		return this.productTypePersistence.findByCode(code);
	}

}
