package com.copin.asset.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copin.asset.mapper.ProductMapper;

@Service
public class ProductService {

	ProductMapper pmapper;
	
	@Autowired
	public ProductService(ProductMapper pmapper) {
		this.pmapper = pmapper;
	}
	
	
	public List<Map<String, Object>> getProduct(String categoryName){
		return pmapper.getProduct(categoryName);
	}
	
	public List<Map<String, Object>> getProductDateLatest(String categoryName){
		return pmapper.getProductDateLatest(categoryName);
	}
	
	
	public List<String> getCategoryNameDelete(){
		return pmapper.getProductNameDelete();
	}
	public String getProductNameCheck(String productName) {
		return pmapper.getProductNameCheck(productName);
	}
	
	
	public void setProductCategorypkeyUpdate(String categoryName, String productName) {
		pmapper.setProductCategorypkeyUpdate(categoryName, productName);
	}
	
	public void setProductNameUpdate(String newProductName, String productName) {
		pmapper.setProductNameUpdate(newProductName, productName);
	}
	
	public void setProductStatusDelete(String productName) {
		pmapper.setProductStatusDelete(productName);
	}
	
	public void setProductStatusRestore(String productName) {
		pmapper.setProductStatusRestore(productName);
	}
}
