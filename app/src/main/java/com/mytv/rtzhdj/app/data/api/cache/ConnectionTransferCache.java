package com.mytv.rtzhdj.app.data.api.cache;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 组织关系转接 Cache
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-12
 * @update
 */
public interface ConnectionTransferCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BaseJson>> getOrganizationalChange(Observable<BaseJson> data, EvictProvider evictProvider);

}
