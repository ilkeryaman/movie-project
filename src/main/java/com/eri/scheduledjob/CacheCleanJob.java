package com.eri.scheduledjob;

import com.eri.constant.enums.CacheKey;
import com.eri.service.cache.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CacheCleanJob {
    private final static Logger logger = LoggerFactory.getLogger(CacheCleanJob.class);

    @Resource
    ICacheService cacheService;

    @Scheduled(fixedDelayString = "${cache.clean.intervalInMilliseconds}", initialDelayString = "${cache.clean.intervalInMilliseconds}")
    public void cleanCache(){
        String key = CacheKey.MOVIES.getName();
        if(cacheService.findListFromCacheWithKey(key) != null){
            cacheService.evictCache(key);
            logger.info("Cache is cleaned.");
        }
    }
}
