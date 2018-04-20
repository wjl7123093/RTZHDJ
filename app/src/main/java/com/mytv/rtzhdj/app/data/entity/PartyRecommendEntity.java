package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartyRecommendEntity   党建新闻推荐实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-9 更新类名
 */
public class PartyRecommendEntity {

    private List<PartyNewsEntity> SpecialBlock;        // 专题
    private List<PartyNewsEntity> ImportandBlock;    // 要闻

    public List<PartyNewsEntity> getSpecialBlock() {
        return SpecialBlock;
    }

    public void setSpecialBlock(List<PartyNewsEntity> specialBlock) {
        this.SpecialBlock = specialBlock;
    }

    public List<PartyNewsEntity> getImportandBlock() {
        return ImportandBlock;
    }

    public void setImportandBlock(List<PartyNewsEntity> importandBlock) {
        this.ImportandBlock = importandBlock;
    }

}
