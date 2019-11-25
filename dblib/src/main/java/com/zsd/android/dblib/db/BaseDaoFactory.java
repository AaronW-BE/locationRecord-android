package com.zsd.android.dblib.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseDaoFactory {

    private final String SQLITE_DB_PATH = "data/data/com.robotsme.app.location/location.db";
    private static volatile BaseDaoFactory instance;
    private SQLiteDatabase sqLiteDatabase;
    private String sqLitePath;
    protected Map<String, BaseDao> map;

    protected BaseDaoFactory() {
        sqLitePath = SQLITE_DB_PATH;
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqLitePath, null);
        map = Collections.synchronizedMap(new HashMap<String, BaseDao>());
    }

    public static BaseDaoFactory getInstance() {
        BaseDaoFactory factory = instance;
        if (factory == null) {
            synchronized (BaseDaoFactory.class) {
                if (factory == null) {
                    factory = new BaseDaoFactory();
                    instance = factory;
                }
            }
        }
        return factory;
    }

    public <T> BaseDao<T> getDao(Class<T> entityClass) {
        BaseDao<T> baseDao = null;
        baseDao = map.get(entityClass.getSimpleName());
        if (baseDao != null) {
            return baseDao;
        }
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            map.put(entityClass.getSimpleName(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
