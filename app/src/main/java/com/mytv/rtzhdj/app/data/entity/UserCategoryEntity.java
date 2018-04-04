package com.mytv.rtzhdj.app.data.entity;

/**
 * UserCategoryEntity   用户类型实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class UserCategoryEntity {

    private int categoryId;         // 用户类型id
    private String categoryName;    // 用户类型名称（党员/群众）

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
