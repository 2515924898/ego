package com.ego.item.service;

import com.ego.common.pojo.PageResult;
import com.ego.item.mapper.BrandMapper;
import com.ego.item.mapper.CategoryBrandMapper;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.CategoryBrand;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(Integer pageNo, Integer rows, String sortBy, Boolean desc, String key) {
        //开始分页
        PageHelper.startPage(pageNo,rows);
        //过滤
        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().andLike("name","%"+key+"%").orEqualTo("letter", key);
        }
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + " "+(desc?"DESC":"ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        Page<Brand> page = (Page<Brand>)brandMapper.selectByExample(example);
        //返回结果
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        //1.保存品牌信息
        brandMapper.insertSelective(brand);
        //2.保存品牌-类别信息
        if(cids != null){
            cids.forEach(cid->{
                CategoryBrand categoryBrand = new CategoryBrand();
                categoryBrand.setCategoryId(cid);
                categoryBrand.setBrandId(brand.getId());
                categoryBrandMapper.insertSelective(categoryBrand);
            });
        }
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //1.更新brand表
        brandMapper.updateByPrimaryKey(brand);
        //2.删除旧的brand和category的关系
        Example example = new Example(CategoryBrand.class);
        example.createCriteria().andEqualTo("brandId",brand.getId());
        categoryBrandMapper.deleteByExample(example);
        //3.新增brand和category关系
        if(cids != null){
            cids.forEach(cid->{
                CategoryBrand categoryBrand = new CategoryBrand();
                categoryBrand.setCategoryId(cid);
                categoryBrand.setBrandId(brand.getId());
                categoryBrandMapper.insertSelective(categoryBrand);
            });
        }
    }

    public void deleteBrandById(Long bid) {
        //1.删除category_brand关系
        Example example = new Example(CategoryBrand.class);
        example.createCriteria().andEqualTo("brandId",bid);
        categoryBrandMapper.deleteByExample(example);
        //2.删除brand信息
        brandMapper.deleteByPrimaryKey(bid);
    }
}
