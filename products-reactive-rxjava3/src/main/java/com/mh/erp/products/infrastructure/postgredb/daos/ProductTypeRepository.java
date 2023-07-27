package com.mh.erp.products.infrastructure.postgredb.daos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.RxJava3SortingRepository;

import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.infrastructure.postgredb.entities.ProductEntity;
import com.mh.erp.products.infrastructure.postgredb.entities.ProductTypeEntity;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ProductTypeRepository extends RxJava3SortingRepository<ProductTypeEntity, Long>{
	
	@Query(value = "SELECT * FROM tb_product_type WHERE is_deleted = 0 ORDER BY RANDOM() LIMIT :limit ")
	Observable<ProductTypeEntity> findAll(@Param("limit") int limit);
	
	@Modifying
	@Query(value = "UPDATE tb_product_type set is_deleted = 1 WHERE tb_product_type_id = :productTypeId ")
	Maybe<Integer> logicallyDelete(@Param("productTypeId") Long productTypeId);

	//@Query("ProductEntity.findByEanOrCode")
	@Query(value = "SELECT * FROM tb_product_type WHERE is_deleted = 0 and code = :code limit 1")
	Maybe<ProductTypeEntity> findByCode(@Param("code") String code);
	
	@Query(value = "SELECT * FROM tb_product_type WHERE is_deleted = 0 and code = :code and tb_product_type_id <> :productTypeId")
	Maybe<ProductTypeEntity> findByCodeIdNot(String code, Long productTypeId);
	
	@Query(value = "SELECT * FROM tb_product_type WHERE tb_product_type_id = :productTypeId ")
	Maybe<ProductTypeEntity> getReferenceById(@Param("product_type_id") Long productTypeId);
} 
