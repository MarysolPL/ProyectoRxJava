package com.mh.erp.products.infrastructure.postgredb.daos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.RxJava3SortingRepository;

import com.mh.erp.products.domain.model.Product;
import com.mh.erp.products.infrastructure.postgredb.entities.ProductEntity;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ProductRepository extends RxJava3SortingRepository<ProductEntity, Long>{
	
	@Query(value = "SELECT * FROM tb_product WHERE is_deleted = 0 ORDER BY RANDOM() LIMIT :limit ")
	Observable<ProductEntity> findAll(@Param("limit") int limit);
	
	@Modifying
	@Query(value = "UPDATE tb_product set is_deleted = 1 WHERE tb_product_id = :productId ")
	Maybe<Integer> logicallyDelete(@Param("productId") Long productId);


	boolean existsByCodeIgnoreCaseAndIsDeletedAndProductIdNot(String code, Integer isDeleted, Long productId);
	
	boolean existsByEanIgnoreCaseAndIsDeletedAndProductIdNot(String code, Integer isDeleted, Long productId);
	
	boolean existsByProductIdAndIsDeleted(Long productId, Integer isDeleted);

	boolean existsByCodeIgnoreCaseAndIsDeleted(String code, Integer isDeleted);
	
	boolean existsByEanIgnoreCaseAndIsDeleted(String code, Integer isDeleted);

	//@Query("ProductEntity.findByEanOrCode")
	@Query(value = "SELECT * FROM tb_product WHERE is_deleted = 0 and (ean = :ean or code = :code) limit 1")
	Maybe<ProductEntity> findByEanOrCode(@Param("code") String code, @Param("ean") String ean);
	
	@Query(value = "SELECT * FROM tb_product WHERE is_deleted = 0 and ean = :ean limit 1")
	Maybe<ProductEntity> findByEan(@Param("ean") String ean);
	
	@Query(value = "SELECT * FROM tb_product WHERE is_deleted = 0 and code = :code and tb_product_id <> :productId")
	Maybe<ProductEntity> findByCodeAndProductIdNot(String code, Long productId);
	
	@Query(value = "SELECT * FROM tb_product WHERE tb_product_id = :product_id ")
	Maybe<ProductEntity> getReferenceById(@Param("product_id") Long productId);
} 
