package com.yushilei.im.util;

import java.util.Collection;
import java.util.Set;

/**
 * @auther by yushilei.
 * @time 2017/4/5-14:49
 * @desc
 */

public class CollectionUtil {
    public static boolean isEmpty(Collection list) {
        return list == null || list.size() == 0;
    }
}
