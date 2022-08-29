package com.copin.asset.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.copin.asset.mapper.CategoryMapper;
import com.copin.asset.mapper.ProductMapper;

@Service
public class CategoryService {

	
	CategoryMapper cmapper;
	ProductMapper pmapper;
	FileUploadService fService;
	@Autowired
	public CategoryService(CategoryMapper cmapper, ProductMapper pMapper, FileUploadService fService) {
		this.cmapper = cmapper;
		this.pmapper = pMapper;
		this.fService = fService;
	}
	
	public List<String> getCategoryNameDepth0(){
		return cmapper.getCategoryNameDepth0();
	}
	public List<Map<String, Object>> getCategoryNameDepth1(String name){
		return cmapper.getCategoryNameDepth1(name);
	}
	public List<String> getCategoryNameDepth1Rest(String name){
		return cmapper.getCategoryNameDepth1Rest(name);
	}
	
	public List<String> getDescendantCategoryName(String name){
		return cmapper.getDescendantCategoryName(name);
	}
	
	public List<String> getCategoryNameDelete(){
		return cmapper.getCategoryNameDelete();
	}
	
	public String getCategoryNameCheck(String name) {
		return cmapper.getCategoryNameCheck(name);
	}
	
	public String getAncestorName(String name) {
		return cmapper.getAncestorName(name);
	}
	
	
	public Map<String, Object> imgUpload(List<MultipartFile> files, String categoryName){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(MultipartFile file : files) {
			
			try {
				
				String fileOriginName =  file.getOriginalFilename().trim();
				
				if( pmapper.getProductNameCheck(fileOriginName) == null) {
					
					
					fService.upload(file);
					pmapper.insertProduct(fileOriginName, categoryName);
					map.put("result", 1);
					
				}else {
					
					map.put("result", 3);
					
				}
			}catch (Exception e) {
				
				
				e.printStackTrace();
				map.put("result", 2);
				
			}
			
		}
		
		map.put("imgList", pmapper.getProductNameCreateDate(categoryName));
		
		return map;
	}
	
	
	public void setCategoryNameUpdate(String name, String newname) {
		cmapper.setCategoryNameUpdate(name, newname);
	}
	
	public void setCategoryStatusDelete(String name) {
		cmapper.setCategoryStatusDelete(name);
	}
	
	public void insertCategory(String name) {
		cmapper.insertCategory(name);;
	}
	
	public void insertCategoryRelationDepth0(String name) {
		cmapper.insertCategoryRelationDepth0(name);
	}
	
	public void insertCategoryRelationDepth1(String ancestor, String descendant) {
		cmapper.insertCategoryRelationDepth1(ancestor, descendant);
	}
	
	
	public void setAncestorUpdate(String newAncestor, String ancestor, String descendant) {
		cmapper.setAncestorUpdate(newAncestor, ancestor, descendant);
	}
	
	public void setCategoryStatusRestore(String categoryName) {
		cmapper.setCategoryStatusRestore(categoryName);
	}
	
	
	
	
	
	
	public void archiveDelete(String deleteName) {
		
		try {
			if(cmapper.getCategoryNameCheck(deleteName) == null) {
				fService.delete(deleteName);
				pmapper.productDelete(deleteName);
			}else {
				
				List<Integer> descendanList = cmapper.getDeleteDescendantCheck(deleteName);
				
				if( descendanList.isEmpty()) {
					
					List<String> proName = pmapper.getCaegoryDepth2ProductDeleteName(deleteName);
					
					if(!proName.isEmpty()) {
						for(int i = 0; i < proName.size(); i++) {
							fService.delete(proName.get(i));
							pmapper.productDelete(proName.get(i));
						}
					}
					
					cmapper.CategoryRelationDepth2Delete2(deleteName);
					cmapper.CategoryRelationDepth1Delete(deleteName);
					cmapper.categoryDeleteName(deleteName);
					
				}else {
					
					List<String> proName = pmapper.getCaegoryDepth1ProductDeleteName(deleteName);
					
					if(proName.isEmpty()) {
						
						cmapper.CategoryRelationDepth2Delete1(deleteName);
						cmapper.CategoryRelationDepth1Delete(deleteName);
						for(int i = 0; i < descendanList.size(); i++) {
							cmapper.categoryDeletePkey(descendanList.get(i));
						}
						cmapper.categoryDeleteName(deleteName);
						
					}else {
					
						for(int i = 0; i < proName.size(); i++) {
							fService.delete(proName.get(i));
							pmapper.productDelete(proName.get(i));
						}
						
						cmapper.CategoryRelationDepth2Delete1(deleteName);
						cmapper.CategoryRelationDepth1Delete(deleteName);
						
						for(int i = 0; i < descendanList.size(); i++) {
							cmapper.categoryDeletePkey(descendanList.get(i));
						}
						
						cmapper.categoryDeleteName(deleteName);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
