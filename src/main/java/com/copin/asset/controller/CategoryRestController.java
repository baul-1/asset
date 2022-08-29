package com.copin.asset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.copin.asset.service.AccountService;
import com.copin.asset.service.CategoryService;
import com.copin.asset.service.FileUploadService;
import com.copin.asset.service.ProductService;

@RestController
public class CategoryRestController {

	AccountService aService;
	CategoryService cService;
	ProductService pService;
	FileUploadService fuService;
	
	@Autowired
	public CategoryRestController(AccountService aService, CategoryService cService, ProductService pService,  FileUploadService fuService) {
		this.aService = aService;
		this.cService = cService;
		this.pService = pService;
		this.fuService = fuService;
	}
	
	public boolean accuntCheck(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("account") == null) {
			return false;
		}
		return true;
	}
	

	public String paramCheck (Map<String, String> map, String key)  {
		String value = map.get(key);
		if( value == null ) {
			return "";
		}else if ( "".equals(key.trim()) ){
			return "";
		}else {
			return value.trim();
		}
	}
	
	@RequestMapping("/asset")
	public Map<String, Object> assetAPI(){
		Map<String, Object> mapObj = new HashMap<String, Object>();
		
		List<String> depth0 = cService.getCategoryNameDepth0();
		for (String string : depth0) {
			List<String> depth1 = cService.getCategoryNameDepth1Rest(string);
			Map<String, Object> tt = new HashMap<String, Object>();
			for (String string2 : depth1) {
				tt.put(string2, pService.getProduct(string2));
			}
			mapObj.put(string, tt);
		}
		
		return mapObj;
	}
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public Map<String, Object> loginf(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		String id = paramCheck(map, "id");
		String pw = paramCheck(map, "pw");
		
		HttpSession session = request.getSession();
		
		if( "".equals(id) || "".equals(pw) ) {
			mapObj.put("result", 0);
		}else {
			int result = aService.accountLogin(id, pw);
			
			if(result == 3) {
				session.setAttribute("account", aService.getAccount(id));
			}
			
			mapObj.put("result", result);
			
		}
		
		return mapObj;
	}
	
	@RequestMapping(value = "fimgarray", method = RequestMethod.GET)
	public Map<String, Object> fimgArray(@RequestParam Map<String, String> map, HttpServletRequest request ){
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
		
		
		String type = paramCheck(map, "type");
		String subname = paramCheck(map, "subname");
		
		if(accuntCheck(request)==false) {
			mapObj.put("result", 0);
		}else if( "".equals(type) || "".equals(subname) ) {
			mapObj.put("result", 1);
		}else if("productname".equals(type)) {
			mapObj.put("result", 2);
			mapObj.put("imgList", pService.getProduct(subname));
		}else if("latest".equals(type)) {
			mapObj.put("result", 3);
			mapObj.put("imgList", pService.getProductDateLatest(subname));
		}
		
		return mapObj;
	}
	
	
	@RequestMapping(value =  "fimgSubSelect", method =  RequestMethod.GET)
	public Map<String, Object> fimgSubSelect(HttpServletRequest request, @RequestParam Map<String, String> map, HttpServletResponse response) {
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
		
		String cat = paramCheck(map, "cat");
		
		if(accuntCheck(request) == false) {
			mapObj.put("result", 0);
		}else if( "".equals(cat) ) {
			mapObj.put("result", 0);
		}else {
			mapObj.put("result", 1);
			mapObj.put("sub", cService.getDescendantCategoryName(cat));
		}
	
		return mapObj;
		
	}
	
	
	@RequestMapping(value ="imgupload", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Map<String, Object> imguploadtest( HttpServletRequest request, @RequestParam Map<String, String> map, @RequestParam("fileList") List<MultipartFile> files){
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
			
		String subname = paramCheck(map, "subname");
		
		if(accuntCheck(request) == false) {
			mapObj.put("result", 0);
		}else if(files == null || "".equals(subname)) {
			mapObj.put("result", 0);
		}else {
			mapObj = cService.imgUpload(files, subname);
		}
		
		return mapObj;
	}
	
	@RequestMapping(value = "catnamecheck", method = RequestMethod.POST)
	public Map<String, Object> catNameCheck(@RequestParam Map<String, String> map, HttpServletResponse response) {
		
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
		
		String catname = paramCheck(map, "catname");
		
		
		String result =  cService.getCategoryNameCheck(catname);
		
		if(result == null) {
			mapObj.put("result", 0);
		}else {
			mapObj.put("result", 1);
		}
		
		return mapObj;
		
	}
	
	
	@RequestMapping(value = "imgnamecheck", method = RequestMethod.POST)
	public Map<String, Object> subNameCheck(@RequestParam Map<String, String> map, HttpServletResponse response) {
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
		
		String imgname = paramCheck(map, "imgname");
		
		
		String result = pService.getProductNameCheck(imgname);
		
		if(result == null) {
			mapObj.put("result", 0);
		}else {
			mapObj.put("result", 1);
		}
		
		return mapObj;
	}
	
	
}













