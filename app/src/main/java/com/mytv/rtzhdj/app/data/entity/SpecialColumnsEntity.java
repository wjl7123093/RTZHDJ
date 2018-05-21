package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * SpecialColumnsEntity   专题二级栏目实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class SpecialColumnsEntity {

    private String ImageUrl;                // 专题背景图地址
    private String Content;                 // 内容
    private List<PartyColumnsEntity> SubObjs;    // 专题二级栏目列表
    private String AllImgUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public List<PartyColumnsEntity> getSubObjs() {
        return SubObjs;
    }

    public void setSubObjs(List<PartyColumnsEntity> subObjs) {
        this.SubObjs = subObjs;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
