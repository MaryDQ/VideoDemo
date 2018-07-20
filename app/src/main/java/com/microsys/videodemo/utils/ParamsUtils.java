package com.microsys.videodemo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2018/1/2.
 * 描    述：参数帮助类
 * 修改历史：
 * ===========================
 */

public class ParamsUtils {





    /**
     * 拼接常用参数
     *
     * @param businessId
     * @param params
     * @return
     */
    public static Map getCommonBusinessParams(String businessId, String... params) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        Map<String, Object> mapParams = new HashMap<String, Object>(16);
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("参数错误");
        }
        for (int i = 0; i < params.length; i += 2) {
            mapParams.put(params[i], params[i + 1]);
        }
        map.put("businessId", businessId);
        map.put("businessParams", mapParams);
        return map;
    }



    public static Map getCommonBusinessParams(String businessId, Map params) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("businessId", businessId);
        map.put("businessParams", params);
        return map;
    }


}
