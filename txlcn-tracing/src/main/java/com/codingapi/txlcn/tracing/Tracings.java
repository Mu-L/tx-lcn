package com.codingapi.txlcn.tracing;

import com.codingapi.txlcn.common.util.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Description: DTX Tracing 工具
 * Date: 19-2-11 下午1:02
 *
 * @author ujued
 */
@Slf4j
public class Tracings {

    /**
     * 私有构造器
     */
    private Tracings() {
    }

    /**
     * 传输Tracing信息
     *
     * @param tracingSetter Tracing信息设置器
     */
    public static void transmit(TracingSetter tracingSetter) {
        if (TracingContext.tracing().hasGroup()) {
            log.debug("tracing transmit group:{}", TracingContext.tracing().groupId());
            tracingSetter.set(TracingConstants.HEADER_KEY_GROUP_ID, TracingContext.tracing().groupId());
            tracingSetter.set(TracingConstants.HEADER_KEY_APP_MAP, TracingContext.tracing().appMapBase64String());
        }
    }

    /**
     * 获取传输的Tracing信息
     *
     * @param tracingGetter Tracing信息获取器
     */
    public static void apply(TracingGetter tracingGetter) {
        String groupId = Optional.ofNullable(tracingGetter.get(TracingConstants.HEADER_KEY_GROUP_ID)).orElse("");
        String appList = Optional.ofNullable(tracingGetter.get(TracingConstants.HEADER_KEY_APP_MAP)).orElse("");
        log.debug("tracing apply group:{}, app map:{}", groupId, appList);
        TracingContext.init(Maps.newHashMap(TracingConstants.GROUP_ID, groupId, TracingConstants.APP_MAP, appList));
    }

    /**
     * Tracing信息设置器
     */
    public interface TracingSetter {
        /**
         * 设置tracing属性
         *
         * @param key   key
         * @param value value
         */
        void set(String key, String value);
    }

    /**
     * Tracing信息获取器
     */
    public interface TracingGetter {
        /**
         * 获取tracing属性
         *
         * @param key key
         * @return tracing value
         */
        String get(String key);
    }
}