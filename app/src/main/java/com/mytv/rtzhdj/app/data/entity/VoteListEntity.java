package com.mytv.rtzhdj.app.data.entity;

/**
 * VoteListEntity   投票列表实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-25    更新新版Entity
 */
public class VoteListEntity {

    private int Id;
    private String Title;           // 标题
    private String AddDate;         // 起始时间
    private String EndDate;         // 结束时间

    private int NodeId;             // 节点Id
    private int State;              // 状态（1 进行中，2 已结束）
    private boolean IsTop;          // 是否置顶
    private String Content;         // 内容

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        this.State = state;
    }

    public boolean isTop() {
        return IsTop;
    }

    public void setTop(boolean top) {
        IsTop = top;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}
