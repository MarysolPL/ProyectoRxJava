package com.mh.erp.products.infrastructure.postgredb.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.mh.erp.products.domain.exceptions.ConflictException;
import com.mh.erp.products.domain.exceptions.NotFoundException;
import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.domain.persistence.ProductPersistence;
import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;
import com.mh.erp.products.infrastructure.postgredb.daos.ProductRepository;
import com.mh.erp.products.infrastructure.postgredb.entities.ProductEntity;
import com.mh.erp.products.infrastructure.postgredb.entities.metadata.IsDeleted;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@Repository
public class ProductPersistencePostgredb implements ProductPersistence {

	private ProductRepository productRepository;

	public ProductPersistencePostgredb(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Observable<Product> findAll(int limit) {
		Observable<Product> products = this.productRepository.findAll(limit).map(ProductEntity::toProduct);
		return products;
	}

	@Override
	public Maybe<Product> create(Product product) {
		return this.productRepository.findByEanOrCode(product.getCode(), product.getEan()).flatMap(
				productEntity -> Maybe.error(new ConflictException(
						"El código o EAN del producto ya existe: " + product.getCode() + ", " + product.getEan())),
				(error) -> Maybe.error(error), () -> Maybe.just(new ProductEntity(product)))
				.flatMapSingle(productRepository::save).map(ProductEntity::toProduct);
	}

	@Override
	public Maybe<Product> read(Long productId) {
		Maybe<Product> product = this.productRepository.findById(productId)
				.switchIfEmpty(Maybe.error(() -> new NotFoundException("No existe productId: " + productId)))
				.map(ProductEntity::toProduct);

		return product;
	}

	@Override
	public Maybe<Product> update(Product product) {
		return this.productRepository.findByCodeAndProductIdNot(product.getCode(), product.getProductId())
				.flatMap(
						emptyEntity -> Maybe.error(new ConflictException(
								"El código de producto ya existe en otro producto: " + product.getCode())),
						(error) -> Maybe.error(error), () -> Maybe.just(new ProductEntity(product)))
				.flatMapSingle(productRepository::save).map(ProductEntity::toProduct);
	}

	@Override
	public Maybe<Integer> delete(Long productId) {
		return this.productRepository.findById(productId)
				.switchIfEmpty(Maybe.error(() -> new NotFoundException("No existe productId: " + productId)))
				.flatMap(productEntity -> this.productRepository.logicallyDelete(productId));
	}

	@Override
	public Maybe<Product> updateEan(Long productId, String ean) {
		return this.productRepository.findByEan(ean)
				.flatMap(
					emptyEntity -> Maybe.error(
							new ConflictException("El código de producto ya existe en otro producto: " + ean)
					),
					(error) -> Maybe.error(error),
					() -> this.productRepository.findById(productId).setEan(ean)
				)
				.flatMapSingle(productRepository::save)
				.map(ProductEntity::toProduct);
	}
	

	@Override
	public Maybe<Product> findByEanOrCode(String ean) {
		Maybe<Product> product = productRepository.findByEan(ean).map(ProductEntity::toProduct).onErrorResumeNext(
				error -> Maybe.error(new ConflictException("El EAN del Producto no existe: " + ean)));
		return product;
	}

	private void assertCodeNotExist(String code) {
		boolean existCode = productRepository.existsByCodeIgnoreCaseAndIsDeleted(code, IsDeleted.NO);
		if (existCode == true) {
			throw new ConflictException("El Codigo del Producto ya existe : " + code);
		}
	}

	private void assertEanNotExist(String ean) {
		if (ean == null) {
			return;
		}
		boolean exist = productRepository.existsByEanIgnoreCaseAndIsDeleted(ean, IsDeleted.NO);
		if (exist == true) {
			throw new ConflictException("El EAN del Producto ya existe : " + ean);
		}
	}

	private void assertCodeNotExistInOthersProducts(String code, Long productId) {
		boolean exist = productRepository.existsByCodeIgnoreCaseAndIsDeletedAndProductIdNot(code, IsDeleted.NO,
				productId);
		if (exist == true) {
			throw new ConflictException("El Codigo ya existe en otros producto: " + code);
		}
	}

	private void assertEanNotExistInOthersProducts(String code, Long productId) {
		boolean exist = productRepository.existsByEanIgnoreCaseAndIsDeletedAndProductIdNot(code, IsDeleted.NO,
				productId);
		if (exist == true) {
			throw new ConflictException("El Ean ya existe en otros productos: " + code);
		}
	}

	private void assertIdExist(Long productId) {
		boolean exist = productRepository.existsByProductIdAndIsDeleted(productId, IsDeleted.NO);
		if (exist == false) {
			throw new NotFoundException("No existe productId: " + productId);
		}
	}
}
