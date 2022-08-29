package com.copin.asset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper {
	
	@Select("select c.name from category c join categoryrelation cr on c.categorypkey = cr.ancestor where cr.depth = 0 and c.status = 0")
	List<String> getCategoryNameDepth0();
	
	@Select("select c2.name as categoryname, p.name as productname from category c1 left outer join categoryrelation cr on c1.categorypkey = cr.ancestor and cr.depth = 1  left outer join category c2 on  c2.categorypkey = cr.descendant left outer join product p on c2.categorypkey = p.categorypkey and p.status = 0 where cr.ancestor = (select categorypkey from category where name = #{categoryName}) and c2.status = 0 group by c2.name")
	List<Map<String, Object>> getCategoryNameDepth1(String categoryName);
	
	@Select("select c2.name as categoryname from category c1 left outer join categoryrelation cr on c1.categorypkey = cr.ancestor and cr.depth = 1  left outer join category c2 on  c2.categorypkey = cr.descendant left outer join product p on c2.categorypkey = p.categorypkey and p.status = 0 where cr.ancestor = (select categorypkey from category where name = #{categoryName}) and c2.status = 0 group by c2.name")
	List<String> getCategoryNameDepth1Rest(String categoryName);
	
	@Select("select category.name from category join categoryrelation on category.categorypkey = categoryrelation.descendant where categoryrelation.ancestor = (select categorypkey from category where name = #{categoryName}) and categoryrelation.depth = 1;")
	List<String> getDescendantCategoryName(String categoryName);
	
	@Select("select name from category where status = 1")
	List<String> getCategoryNameDelete();
	
	@Select("select name from category where name = #{categoryName}")
	String getCategoryNameCheck(String categoryName);
	
	@Update("update category set name = #{param2} where name = #{param1}")
	void setCategoryNameUpdate(String categoryName, String newCategoryName);
	
	@Update("update category set status = 1 where name = #{categoryName}")
	void setCategoryStatusDelete(String categoryName);
	
	@Update("update category set status = 0 where name = #{categoryName}")
	void setCategoryStatusRestore(String categoryName);
	
	@Insert("insert into category (categorypkey, name, status) values (null, #{categoryName}, 0)")
	void insertCategory(String categoryName);
	
	
	@Insert("insert into categoryrelation (ancestor, descendant, depth) select categorypkey, categorypkey, 0 from category where name = #{categoryName}")
	void insertCategoryRelationDepth0(String categoryName);
	
	@Insert("insert into categoryrelation (ancestor, descendant, depth) select c1.categorypkey, c2.categorypkey, 1 from category c1 join category c2 on c1.name = #{param1} and  c2.name =#{param2}")
	void insertCategoryRelationDepth1(String ancestor, String descendant);
	
	
	
	@Select("select c2.name from category  c1 join categoryrelation cr on c1.categorypkey = cr.descendant join category c2 on c2.categorypkey = cr.ancestor where c1.name = #{categoryName}")
	String getAncestorName(String categoryName);
	
	
	@Update("update categoryrelation set ancestor = (select categorypkey from category where name = #{param1}) where ancestor = (select categorypkey from category where name = #{param2}) and descendant  = (select categorypkey from category where name = #{param3})")
	void setAncestorUpdate(String newAncestor, String ancestor, String descendant);
	
	
	
	
	
	
	
	@Select("select c.categorypkey from category  c join categoryrelation cr on c.categorypkey = cr.descendant where cr.ancestor = (select categorypkey from category where name = #{categoryName}) and cr.ancestor != cr.descendant")
	List<Integer> getDeleteDescendantCheck(String categoryName);
	
	@Delete("delete from categoryrelation where ancestor = (select ancestor from (select ancestor from category join categoryrelation on category.categorypkey = categoryrelation.ancestor where name = #{categoryName} group by name ) as sub) and  ancestor = descendant")
	void CategoryRelationDepth1Delete(String categoryName);
	
	@Delete("delete from categoryrelation where descendant in (select categorypkey from (select c.categorypkey from category  c join categoryrelation cr on c.categorypkey = cr.descendant where cr.ancestor = (select categorypkey from category where name = #{categoryName}) and cr.ancestor != cr.descendant) as sub)")
	void CategoryRelationDepth2Delete1(String categoryName);	
	
	@Delete("delete from categoryrelation where descendant in (select categorypkey from (select c.categorypkey from category  c join categoryrelation cr on c.categorypkey = cr.descendant where cr.descendant = (select categorypkey from category where name = #{categoryName})and cr.ancestor != cr.descendant) as sub)")
	void CategoryRelationDepth2Delete2(String categoryName);
	
	@Delete("delete from category where name = #{categoryName}")
	void categoryDeleteName(String categoryName);
	
	@Delete("delete from category where categorypkey = #{categoryPkey}")
	void categoryDeletePkey(int categoryPkey);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
