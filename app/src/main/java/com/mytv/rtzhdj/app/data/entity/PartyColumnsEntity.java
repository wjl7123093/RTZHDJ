package com.mytv.rtzhdj.app.data.entity;

/**
 * PartyColumnsEntity   党建新闻栏目实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-5 更新了 NodeId 字段
 */
public class PartyColumnsEntity {

    private int NodeID;
    private String Title;   // 栏目标题

    public int getNodeId() {
        return NodeID;
    }

    public void setNodeId(int nodeId) {
        NodeID = nodeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
