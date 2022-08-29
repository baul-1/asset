package com.copin.asset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ProductMapper {
	
	@Select("select * from product where categorypkey = (select categorypkey from category where name = #{categoryName}) and status = 0 order by name")
	List<Map<String, Object>> getProduct(String categoryName);
	
	@Select("select * from product where categorypkey = (select categorypkey from category where name = #{categoryName}) and status = 0 order by createdate desc")
	List<Map<String,Object>> getProductDateLatest(String categoryName);
	
	@Select("select * from product where categorypkey = (select categorypkey from category where name = #{categoryName}) and status = 0")
	List<Map<String, Object>> getProductVO(String categoryName);
	
	@Select("select name from product where categorypkey = (select categorypkey from category where name = #{categoryName}) and status = 0")
	List<String> getProductName(String categoryName);
	
	@Select("select name, createdate from product where categorypkey = (select categorypkey from category where name = #{categoryName}) and status = 0")
	List<Map<Object, Object>> getProductNameCreateDate(String categoryName);
	
	
	@Select("select name from product where name =#{productName}")
	String getProductNameCheck(String productName);
	
	@Update("update product set categorypkey = (select categorypkey from category where name = #{param1}) where name = #{param2} ")
	void setProductCategorypkeyUpdate(String categoryName, String productName);
	
	@Update("update product set status = 1 where name = #{productName}")
	void setProductStatusDelete(String productName);
	
	@Update("update product set status = 0 where name = #{productName}")
	void setProductStatusRestore(String productName);
	
	@Update("update product set name = #{param1} where name = #{param2}")
	void setProductNameUpdate(String newProductName, String productName);
	
	@Insert("insert into product (productpkey, name, status, createdate, categorypkey) select null, #{param1}, 0, now(), categorypkey from category where name = #{param2}")
	void insertProduct(String productName, String categoryName);
	
	@Select("select name from product where status = 1")
	List<String> getProductNameDelete();
	
	@Delete("delete from product where name = #{productName}")
	void productDelete(String productName);
	
	@Select("select name from product where categorypkey in (select c.categorypkey from category  c join categoryrelation cr on c.categorypkey = cr.descendant where cr.ancestor = (select categorypkey from category where name = #{categoryName}) and cr.ancestor != cr.descendant)")
	List<String> getCaegoryDepth1ProductDeleteName(String categoryName);
	
	@Select("select name from product where categorypkey in (select c.categorypkey from category  c join categoryrelation cr on c.categorypkey = cr.descendant where cr.descendant = (select categorypkey from category where name = #{categoryName}) and cr.ancestor != cr.descendant)")
	List<String> getCaegoryDepth2ProductDeleteName(String categoryName);

	
	

}
