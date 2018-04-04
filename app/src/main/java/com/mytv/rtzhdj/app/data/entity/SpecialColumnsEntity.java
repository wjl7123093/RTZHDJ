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

    private String imageUrl;
    private String content;
    private List<SpecialColumn> subObjs;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SpecialColumn> getSubObjs() {
        return subObjs;
    }

    public void setSubObjs(List<SpecialColumn> subObjs) {
        this.subObjs = subObjs;
    }

    class SpecialColumn {
        private int nodeId;
        private String title;

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
