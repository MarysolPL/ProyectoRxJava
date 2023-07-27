package com.mh.erp.products.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.domain.persistence.ProductPersistence;
import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Service
public class ProductService {
	private final ProductPersistence productPersistence;

	public ProductService(ProductPersistence productPersistence) {
		this.productPersistence = productPersistence;
	}

	public Observable<Product> findAll(int limit) {
		return this.productPersistence.findAll(limit);
	}

	@Transactional
	public Maybe<Product> create(Product product) {
		product.setCreated(LocalDateTime.now());
		return this.productPersistence.create(product);
	}

	public Maybe<Product> read(Long productId) {
		return this.productPersistence.read(productId);
	}

	@Transactional
	public Maybe<Integer> delete(Long productId) {
		return this.productPersistence.delete(productId);
	}

	@Transactional
	public Maybe<Product> update(Long productId, Product product) {
		product.setProductId(productId);
		return this.productPersistence.update(product);
	}

	@Transactional
	public Maybe<Product> updateEan(Long productId, String ean) {
		return this.productPersistence.updateEan(productId, ean);
	}

	public Maybe<Product> findByEanOrCode(String ean) {
		return this.productPersistence.findByEanOrCode(ean);
	}

}
