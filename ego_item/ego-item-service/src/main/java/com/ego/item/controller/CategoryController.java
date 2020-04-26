package com.ego.item.controller;

import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据parentId查询类目
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid){
        try{
            if(pid == null || pid.longValue()<0){
                return ResponseEntity.badRequest().build();
            }

            List<Category> categoryList = this.categoryService.queryCategoryListByParentId(pid);
            if(CollectionUtils.isEmpty(categoryList)){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categoryList);
        }catch (Exception e){
            e.printStackTrace();
        }

        //响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Category category){
        categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam(value = "id",required = true) Long id,@RequestParam(value = "name",required = true) String name){
        categoryService.updateNameById(id,name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据bid查询categories
     */
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid")Long bid){
        List<Category> category = categoryService.queryCategoryByBid(bid);
        return ResponseEntity.ok(category);
    }
}
