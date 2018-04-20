package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartySubNewsEntity   党建新闻二级列表实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class PartySubNewsEntity {

    private List<PartyNewsEntity> channelNewsBlock;    // 二级新闻列表

    public List<PartyNewsEntity> getChannelNewsBlock() {
        return channelNewsBlock;
    }

    public void setChannelNewsBlock(List<PartyNewsEntity> channelNewsBlock) {
        this.channelNewsBlock = channelNewsBlock;
    }

}
