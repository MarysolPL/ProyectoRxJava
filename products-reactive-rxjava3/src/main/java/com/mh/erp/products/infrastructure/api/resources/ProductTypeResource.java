package com.mh.erp.products.infrastructure.api.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mh.erp.products.domain.model.ProductType;
import com.mh.erp.products.domain.services.ProductTypeService;
import com.mh.erp.products.infrastructure.api.dtos.ProductIdAndNameDto;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

@RestController
@RequestMapping(ProductTypeResource.PRODUCT_TYPES)
public class ProductTypeResource {
	public static final String PRODUCT_TYPES = "/producttype";
	public static final String PRODUCT_TYPE_ID = "/{productTypeId}";
	public static final String SEARCH = "/search";
	public static final String CODE = "/code";
    private ProductTypeService productTypeService;
    
	public ProductTypeResource(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	@RequestMapping(SEARCH)
    public Observable<ProductType> findAll(@RequestParam int limit) {
		Observable<ProductType> products = 
    			productTypeService.findAll(limit);
    	return products;
    } 
    
    @PostMapping
	public Maybe<ProductType> create(@Valid @RequestBody ProductType product) {
        product.doDefault();
        return this.productTypeService.create(product);
    }
    
    @PutMapping(PRODUCT_TYPE_ID)
   	public Maybe<ProductType> update(@PathVariable Long productTypeId, @Valid @RequestBody ProductType product) {
       return this.productTypeService.update(productTypeId, product);
    }
    
    @GetMapping(PRODUCT_TYPE_ID)
    public Maybe<ProductType> read(@PathVariable Long productTypeId) {
        return this.productTypeService.read(productTypeId);
    }
    
    @DeleteMapping(PRODUCT_TYPE_ID)
    public Maybe<Integer> delete(@PathVariable Long productTypeId) {
        return this.productTypeService.delete(productTypeId);
    }
    
    @GetMapping(CODE)
    public Maybe<ProductType> findByEanOrCode(@RequestParam(required = false) String ean) {
        return this.productTypeService.findByCode(ean);
    }
}
