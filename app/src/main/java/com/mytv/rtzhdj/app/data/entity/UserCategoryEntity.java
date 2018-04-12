package com.mytv.rtzhdj.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * UserCategoryEntity   用户类型实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-12
 */
public class UserCategoryEntity implements Serializable {

    private List<UserCategory> Item;

    public List<UserCategory> getItem() {
        return Item;
    }

    public void setItem(List<UserCategory> item) {
        this.Item = item;
    }

    class UserCategory implements Serializable {

        private int CategoryId;         // 用户类型id
        private String ClassCode;       // 用户类型代码
        private String CategoryName;    // 用户类型名称（党员/群众）

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int categoryId) {
            this.CategoryId = categoryId;
        }

        public String getClassCode() {
            return ClassCode;
        }

        public void setClassCode(String classCode) {
            ClassCode = classCode;
        }

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String categoryName) {
            this.CategoryName = categoryName;
        }
    }
}
