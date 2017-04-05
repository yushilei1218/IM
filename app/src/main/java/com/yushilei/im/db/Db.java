package com.yushilei.im.db;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * @auther by yushilei.
 * @time 2017/4/5-15:04
 * @desc
 */

public class Db {
    private static DbManager db;

    public static DbManager getDb() {
        synchronized (Db.class) {
            if (db == null) {
                DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                        .setDbName("friend.db")
                        .setDbVersion(1)
                        .setDbOpenListener(new DbManager.DbOpenListener() {
                            @Override
                            public void onDbOpened(DbManager db) {
                                // 开启WAL, 对写入加速提升巨大
                                db.getDatabase().enableWriteAheadLogging();
                            }
                        });

                db = x.getDb(daoConfig);
            }
        }
        return db;
    }
}
