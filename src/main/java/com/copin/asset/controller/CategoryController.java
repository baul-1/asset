package com.copin.asset.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.copin.asset.service.AccountService;
import com.copin.asset.service.CategoryService;
import com.copin.asset.service.FileUploadService;
import com.copin.asset.service.ProductService;

@Controller
public class CategoryController {

	
	AccountService aService;
	CategoryService cService;
	ProductService pService;
	FileUploadService fService;
	@Autowired
	public CategoryController(FileUploadService fService, AccountService aService, CategoryService cService, ProductService pService) {
		this.fService = fService;
		this.cService = cService;
		this.aService = aService;
		this.pService = pService;
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
	
	@RequestMapping(value = "mapstamper", method = RequestMethod.GET)
	@CrossOrigin("*")
	public String mapstamper() {
		return "mapstamper/main";
	}
	
	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request) {
		
		if(accuntCheck(request) == true) {
			return "redirect:map";
		}
		return "login/loginpage";
	}
	

	
	@RequestMapping(value = "signout", method = RequestMethod.GET)
	public String signout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "map", method = RequestMethod.GET)
	public String fIndex(HttpServletRequest request,  Model model ) {
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}
		
		model.addAttribute("catname", cService.getCategoryNameDepth0());
		
		return "ui/findex";
	}
	
	@RequestMapping(value = "fcatmodify", method=RequestMethod.GET)
	public String fcatmodify(HttpServletRequest request, @RequestParam Map<String, String> map) {
		
		String newname = paramCheck(map, "newname");
		String catname = paramCheck(map, "catname");
	
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(newname) || "".equals(catname) ) {
			return "redirect:map";
		}else {
			if(cService.getCategoryNameCheck(newname) == null) {
				cService.setCategoryNameUpdate(catname, newname);
			}
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "fcatdelete", method=RequestMethod.GET)
	public String fcatdelete( HttpServletRequest request, @RequestParam Map<String,String> map) {
		
		String cat = paramCheck(map, "cat");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if("".equals(cat)) {
			return "redirect:map";
		}else {
			cService.setCategoryStatusDelete(cat);
		}
		
		return "redirect:map";
	}
	
	@RequestMapping(value = "fcatinsert")
	public String fcatinsert(HttpServletRequest request, @RequestParam Map<String,String> map) {
		
		String insertname = paramCheck(map, "insertname");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(insertname) ) {
			return "redirect:map";
		}else {
			try {
				
				if(cService.getCategoryNameCheck(insertname) == null) {
					cService.insertCategory(insertname);
					cService.insertCategoryRelationDepth0(insertname);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				return "redirect:map";
			}
		}
		return"redirect:map";
	}
	

	@RequestMapping(value = "fsub", method = RequestMethod.GET)
	public String fsub( @RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		String cat = paramCheck(map, "cat");

		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if("".equals(cat) ) {
			return "redirect:map";
		}else {
			model.addAttribute("catname", cat);
			model.addAttribute("catnamelist", cService.getCategoryNameDepth0());
			model.addAttribute("subname", cService.getCategoryNameDepth1(cat));
		}
		return "ui/fsub";
	}
	
	@RequestMapping(value = "fsubinsert", method = RequestMethod.GET)
	public String fsubinsert(HttpServletRequest request, @RequestParam Map<String,String>map,  RedirectAttributes rttr){
		
		String insertcatname = paramCheck(map, "insertcatname");
		String insertsubname = paramCheck(map, "insertsubname");
	
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(insertcatname) || "".equals(insertsubname)) {
			return "redirect:map";
		}else {
			try {
				if(cService.getCategoryNameCheck(insertsubname) == null) {
					cService.insertCategory(insertsubname);
					cService.insertCategoryRelationDepth1(insertcatname, insertsubname);
				}
			}catch (Exception e) {
				e.printStackTrace();
				return "redirect:map";
			}
		}
		rttr.addAttribute("cat", insertcatname);
		
		return "redirect:fsub";
	}
	
	@RequestMapping(value = "fsubmodify", method=RequestMethod.GET)
	public String fsubmodify(HttpServletRequest request, @RequestParam Map<String, String> map, RedirectAttributes rttr) {

			
		String newname = paramCheck(map, "newname");
		String subname = paramCheck(map, "subname");

		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(newname) || "".equals(subname) ) {
			return "redirect:map";
		}else {
	
			if(cService.getCategoryNameCheck(newname) == null) {
				cService.setCategoryNameUpdate(subname, newname);
				rttr.addAttribute("cat", cService.getAncestorName(newname));
			}else {
				rttr.addAttribute("cat", cService.getAncestorName(subname));
			}
		}
			
		return "redirect:fsub";
	}
	
	@RequestMapping(value = "fsubmove", method = RequestMethod.GET)
	public String fsubmove(HttpServletRequest request, @RequestParam Map<String,String> map, RedirectAttributes rttr) {
		
		String catname = paramCheck(map, "catname");
		String subname = paramCheck(map, "subname");
		String mcatname = paramCheck(map, "mcatname");
		
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(catname) || "".equals(subname) || "".equals(mcatname) ) {
			return "redirect:map";
		}else {
			cService.setAncestorUpdate(mcatname, catname, subname);
		}
		
		rttr.addAttribute("cat",catname);
		
		return "redirect:fsub";
	}
	

	
	@RequestMapping(value = "fsubdelete", method=RequestMethod.GET)
	public String fsubdelete(HttpServletRequest request, @RequestParam Map<String,String> map, RedirectAttributes rttr){
		
		String sub = paramCheck(map, "sub");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if("".equals(sub)) {
			return "redirect:map";
		}else {
			cService.setCategoryStatusDelete(sub);
		}
		rttr.addAttribute("cat", cService.getAncestorName(sub));
		
		return "redirect:fsub";
	}
	
	
	
	@RequestMapping(value = "fimg", method = RequestMethod.GET)
	public String fimg( @RequestParam Map<String,String>map, HttpServletRequest request, Model model) {
		
		String subname = paramCheck(map, "sub");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(subname) ) {
			return "redirect:map";
		}else {
			
			String catname = cService.getAncestorName(subname);
			
			if(catname == null || "".equals(catname)) {
				return "redirect:map";
			}else {
				model.addAttribute("catname", catname);
				model.addAttribute("subname", subname);
				model.addAttribute("imgname", pService.getProduct(subname));
				model.addAttribute("catnamelist", cService.getCategoryNameDepth0());
			}
		}
		
		return "ui/fimg";
	}
	

	
	@RequestMapping(value = "fimgmove", method = RequestMethod.GET)
	public String fImgMove(@RequestParam Map<String, String> map, HttpServletRequest request, RedirectAttributes rttr) {
		
		
		String subname = paramCheck(map, "rsubname");
		String imgname = paramCheck(map, "imgname");
		String msubname = paramCheck(map, "subname");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(subname) || "".equals(imgname) || "".equals(msubname) ) {
			return "redirect:map";
		}else {
			pService.setProductCategorypkeyUpdate(msubname, imgname);
			rttr.addAttribute("sub", subname);
		}
		
		return "redirect:fimg";
	}
	
	@RequestMapping(value = "fimgselectmove", method = RequestMethod.GET)
	public String fImgSelectMove(@RequestParam Map<String, String> map, HttpServletRequest request, RedirectAttributes rttr) {
		
		String [] imgList = request.getParameterValues("checkbox");
		
		String subname = paramCheck(map, "rsubname");
		String msubname = paramCheck(map, "subname");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if(  imgList == null || imgList.length < 1 || "".equals(subname) || "".equals(msubname) ) {
			return "redirect:map";
		}else {
			for (int i = 0; i < imgList.length; i++) {
				pService.setProductCategorypkeyUpdate(msubname, imgList[i].trim());
			}
			rttr.addAttribute("sub", subname);
		}
		return "redirect:fimg";
	}
		

	@RequestMapping(value = "fimgmodify")
	public String fimgmodify(HttpServletRequest request, @RequestParam Map<String,String> map, RedirectAttributes rttr) {
		
		
		String imgname = paramCheck(map, "imgname");
		String newname = paramCheck(map, "newname");
		String subname = paramCheck(map, "subname");
		
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(imgname) || "".equals(newname) || "".equals(subname) ) {
			return "redirect:map";
		}else {
			try {
				if(pService.getProductNameCheck(newname) == null) {
					if(fService.modify(imgname, newname)) {
						pService.setProductNameUpdate(newname, imgname);
					};
				}
			}catch (Exception e) {
				e.printStackTrace();
				rttr.addAttribute("sub", imgname);
				return"redirect:fimg";
			}
		}
		
		rttr.addAttribute("sub", subname);
		
		return "redirect:fimg";    
	}
	
	
	@RequestMapping(value = "fimgdelete", method=RequestMethod.GET)
	public String fimgdelete(HttpServletRequest request, @RequestParam Map<String,String> map, RedirectAttributes rttr) {
		
		String imgname = paramCheck(map, "img");
		String subname = paramCheck(map, "sub");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(imgname) || "".equals(subname) ) {
			return "redirect:map";
		}else {
			pService.setProductStatusDelete(imgname);
		}
		
		rttr.addAttribute("sub", subname);
		
		return "redirect:fimg"; 
	}
	
	@RequestMapping(value = "fimgselectdelete", method=RequestMethod.GET)
	public String fimgSelectDelete( HttpServletRequest request, RedirectAttributes rttr) {
		
		String [] imgList = request.getParameterValues("checkbox");
		String subname = request.getParameter("rsubname");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if ( imgList == null || subname == null || "".equals(subname.trim()) || imgList.length < 1) {
			return "redirect:map";
		}else {
			for (int i = 0; i < imgList.length; i++) {
				pService.setProductStatusDelete(imgList[i].trim());
			}
		}
		
		rttr.addAttribute("sub", subname.trim());
		
		return "redirect:fimg"; 
	}
	

	@RequestMapping(value ="farchive", method = RequestMethod.GET)
	public String farchive( HttpServletRequest request, Model model) {
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}
		model.addAttribute("catname", cService.getCategoryNameDelete());
		model.addAttribute("imgname", pService.getCategoryNameDelete());
		
		return "ui/farchive";
	}
	
	
	@RequestMapping(value = "farchivedelete", method = RequestMethod.GET)
	public String fArchiveDelete(HttpServletRequest request, @RequestParam Map<String, String> map) {
		
		String delete = paramCheck(map, "delete");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if("".equals(delete)) {
			return "redirect:farchive";
		}else {
			cService.archiveDelete(delete);
		}
		
		
		return"redirect:farchive";
	}
	
	@RequestMapping(value = "farchiveselectdelete", method = RequestMethod.GET)
	public String fArchiveSelectDelete(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		
		String[] deleteList = request.getParameterValues("checkbox");
 		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( deleteList == null || deleteList.length < 1 ) {
			return "redirect:farchive";
		}else {
			
			for(int i =0; i < deleteList.length; i++) {
				cService.archiveDelete(deleteList[i]);
			}
		}
		
		return "redirect:farchive";
	}
	
	
	@RequestMapping(value = "farchiverestore", method = RequestMethod.GET)
	public String fArchiveRestore(HttpServletRequest request, @RequestParam Map<String, String> map) {
		
		
		String restore = paramCheck(map, "restore");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if( "".equals(restore)  ) {
			return "redirect:farchive";
		}else {
			cService.setCategoryStatusRestore(restore);
			pService.setProductStatusRestore(restore);
		}
		
		return"redirect:farchive";
	}
	
	@RequestMapping(value = "farchiverestoreselect", method = RequestMethod.GET)
	public String fArchiveRestoreSelect(HttpServletRequest request, @RequestParam Map<String, String> map) {
		
		String[] restoreList = request.getParameterValues("checkbox");
		
		if(accuntCheck(request) == false) {
			return "redirect:/";
		}else if(restoreList == null || restoreList.length < 1  ) {
			return "redirect:farchive";
		}else {
			
			for(int i =0; i < restoreList.length; i++) {
				cService.setCategoryStatusRestore(restoreList[i]);
				pService.setProductStatusRestore(restoreList[i]);
			}
			
		}
		
		return"redirect:farchive";
	}
	
}
