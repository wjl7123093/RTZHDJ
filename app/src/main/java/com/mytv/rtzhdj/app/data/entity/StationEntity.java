package com.mytv.rtzhdj.app.data.entity;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * StationEntity   站点实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-25
 * @update
 */
public class StationEntity implements IPickerViewData {

    private int PublishmentSystemId;
    private String PublishmentSystemName;

    public int getPublishmentSystemId() {
        return PublishmentSystemId;
    }

    public void setPublishmentSystemId(int publishmentSystemId) {
        PublishmentSystemId = publishmentSystemId;
    }

    public String getPublishmentSystemName() {
        return PublishmentSystemName;
    }

    public void setPublishmentSystemName(String publishmentSystemName) {
        PublishmentSystemName = publishmentSystemName;
    }

    @Override
    public String getPickerViewText() {
        return this.getPublishmentSystemName();
    }
}
