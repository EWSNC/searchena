package com.example.searchena.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LRUMap;

@Slf4j
public class IdempotentUtils {
    private static LRUMap reqCache = new LRUMap<>(100);

    public static boolean judge(String filePath, Object lockClass) {
        synchronized (lockClass) {
            if (reqCache.containsKey(filePath)) {
                log.info("正在执行:{}", filePath);
                return false;
            }
            reqCache.put(filePath, 1);
            return true;
        }
    }

    public static void pop(String filePath, Object lockClass) {
        synchronized (lockClass) {
            log.info("remove:{}", filePath);
            reqCache.remove(filePath);
        }
    }
}
