package com.mytv.rtzhdj.app.data.api.cache;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 志愿服务详情 数据 Cache
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface VolunteerServiceDetailCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BaseJson<VolunteerDetailEntity>>> getVolunteerServiceDetail(Observable<BaseJson<VolunteerDetailEntity>> data, EvictProvider evictProvider);

}
