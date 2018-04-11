package com.mytv.rtzhdj.app.data.api.cache;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 我要投票数据 Cache
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface VoteDetailCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<VoteDetailEntity>> getMyVoteDetail(Observable<VoteDetailEntity> data, EvictProvider evictProvider);

}
