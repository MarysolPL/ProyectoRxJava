package com.mh.erp.products.infrastructure.postgredb.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.mh.erp.products.domain.exceptions.ConflictException;
import com.mh.erp.products.domain.exceptions.NotFoundException;
import com.mh.erp.products.domain.model.ProductType;
import com.mh.erp.products.domain.persistence.ProductTypePersistence;
import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;
import com.mh.erp.products.infrastructure.postgredb.daos.ProductTypeRepository;
import com.mh.erp.products.infrastructure.postgredb.entities.ProductTypeEntity;
import com.mh.erp.products.infrastructure.postgredb.entities.metadata.IsDeleted;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@Repository
public class ProductTypePersistencePostgredb implements ProductTypePersistence {
	
	private ProductTypeRepository productTypeRepository;
	
    public ProductTypePersistencePostgredb(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}
    
    @Override
    public Observable<ProductType> findAll(int limit) {
    	Observable<ProductType> products = this.productTypeRepository.findAll(limit).map(ProductTypeEntity::toProductType);
    	return products;
    }

	@Override
	public Maybe<ProductType> create(ProductType productType) {
		return this.productTypeRepository.findByCode(productType.getCode())
				.flatMap(
					productEntity -> Maybe.error(
							new ConflictException("El código del producto ya existe: " + productType.getCode())
					),
					(error) -> Maybe.error(error),
					() -> Maybe.just(new ProductTypeEntity(productType))
				)
				.flatMapSingle(productTypeRepository::save)
				.map(ProductTypeEntity::toProductType);
	}

	@Override
	public Maybe<ProductType> read(Long productTypeIdx) { 
		Maybe<ProductType> product = this.productTypeRepository.findById(productTypeIdx)
				.switchIfEmpty(Maybe.error(() -> new NotFoundException("No existe productTypeIdx: " + productTypeIdx)))
				.map(ProductTypeEntity::toProductType);
		
    	return product;
	}

	@Override
	public Maybe<ProductType> update(ProductType productType) {
		return this.productTypeRepository.findByCodeIdNot(productType.getCode(), productType.getProductTypeId())
				.flatMap(
					emptyEntity -> Maybe.error(
							new ConflictException("El código de producto ya existe en otro producto: " + productType.getCode())
					),
					(error) -> Maybe.error(error),
					() -> Maybe.just(new ProductTypeEntity(productType))
				)
				.flatMapSingle(productTypeRepository::save)
				.map(ProductTypeEntity::toProductType);
	}
	
	
	@Override
	public Maybe<Integer> delete(Long productTypeId) {
    	return this.productTypeRepository.findById(productTypeId)
    			.switchIfEmpty(Maybe.error(() -> new NotFoundException("No existe productTypeId: " + productTypeId)))
				.flatMap(
					productEntity -> this.productTypeRepository.logicallyDelete(productTypeId)
				);
	}
	
	@Override
	public Maybe<ProductType> findByCode(String code) {
		Maybe<ProductType> product = productTypeRepository.findByCode(code)
				.map(ProductTypeEntity::toProductType)
				.onErrorResumeNext(error -> Maybe.error(new ConflictException("El codigo del Producto no existe: " + code)));
    	return product;
	}
}
