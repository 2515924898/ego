package com.ego.item.mapper;

import com.ego.item.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {

    @Update("update tb_category set name = #{name} where id = #{id}")
    void updateNameById(@Param("id") Long id, @Param("name") String name);

    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> findListByBid(@Param("bid") Long bid);
}
