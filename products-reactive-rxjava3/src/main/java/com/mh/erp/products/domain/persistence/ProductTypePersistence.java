package com.mh.erp.products.domain.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.domain.model.ProductType;
//import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

@Repository
public interface ProductTypePersistence {
	Observable<ProductType> findAll(int limit);

	Maybe<ProductType> create(ProductType productType);
	
	Maybe<ProductType> read(Long productTypeId);

	Maybe<ProductType> update(ProductType productType);

	Maybe<Integer> delete(Long productTypeId);
	
	Maybe<ProductType> findByCode(String code);
}
