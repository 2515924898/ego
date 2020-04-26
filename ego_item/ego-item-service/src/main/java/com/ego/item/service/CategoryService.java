package com.ego.item.service;

import com.ego.item.mapper.CategoryMapper;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<Category> queryCategoryListByParentId(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    public void save(Category category) {
        categoryMapper.insert(category);
    }

    public void updateNameById(Long id, String name) {
        categoryMapper.updateNameById(id,name);
    }

    public void deleteById(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据bid查询对应的categories
     * @param bid
     * @return
     */
    public List<Category> queryCategoryByBid(Long bid) {
        List<Category> categoryList = categoryMapper.findListByBid(bid);
        return categoryList;
    }
}
