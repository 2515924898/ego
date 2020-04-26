package com.ego.item.pojo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "tb_category_brand")
@Data
public class CategoryBrand {
    private Long categoryId;
    private Long brandId;
}
