package com.zsd.android.dblib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zsd.android.dblib.annotation.DbField;
import com.zsd.android.dblib.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase sqLiteDatabase;
    private Class<T> entityClass;
    private String tableName;
    private boolean isInit;
    private HashMap<String, Field> cacheMap;

    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        if (!isInit) {
            DbTable dbTable = entityClass.getAnnotation(DbTable.class);
            if (dbTable != null && !"".equals(dbTable.value())) {
                tableName = dbTable.value();
            } else {
                tableName = entityClass.getName();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            String createTabSql = getCreateTabSql();
            sqLiteDatabase.execSQL(createTabSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    private String getCreateTabSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ");
        sb.append(tableName + "(");
        Field[] fields = entityClass.getDeclaredFields();
        int primaryKeyCount = 0;
        for (Field field : fields) {
            Class<?> type = field.getType();
            DbField dbField = field.getAnnotation(DbField.class);
            if (dbField != null && !"".equals(dbField.value())) {
                boolean primaryKey = dbField.primaryKey();
                if (primaryKey && !type.isAssignableFrom(int.class)) {
                    throw new RuntimeException("主键必须是int类型");
                }
                if (primaryKey && primaryKeyCount > 1) {
                    throw new RuntimeException("主键最多只能有一个");
                }
                if (type.isAssignableFrom(String.class)) {
                    sb.append(dbField.value() + " TEXT,");
                } else if (type.isAssignableFrom(int.class)) {
                    if (primaryKey) {
                        primaryKeyCount++;
                        sb.append(dbField.value() + " INTEGER primary key autoincrement,");
                    } else {
                        sb.append(dbField.value() + " INTEGER,");
                    }
                } else if (type.isAssignableFrom(long.class)) {
                    sb.append(dbField.value() + " BIGINT,");
                } else if (type.isAssignableFrom(double.class)) {
                    sb.append(dbField.value() + " DOUBLE,");
                } else if (type.isAssignableFrom(byte[].class)) {
                    sb.append(dbField.value() + " BLOG,");
                }
            } else {
                if (type.isAssignableFrom(String.class)) {
                    sb.append(field.getName() + " TEXT,");
                } else if (type.isAssignableFrom(int.class)) {
                    sb.append(field.getName() + " INTEGER,");
                } else if (type.isAssignableFrom(long.class)) {
                    sb.append(field.getName() + " BIGINT,");
                } else if (type.isAssignableFrom(double.class)) {
                    sb.append(field.getName() + " DOUBLE,");
                } else if (type.isAssignableFrom(byte[].class)) {
                    sb.append(field.getName() + " BLOG,");
                }
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
    }

    private void initCacheMap() {
        String sql = "select * from " + tableName + " limit 1, 0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        Field[] columnFields = entityClass.getDeclaredFields();
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : columnFields) {
                field.setAccessible(true);
                String fieldName = null;
                DbField dbField = field.getAnnotation(DbField.class);
                if (dbField != null && !"".equals(dbField.value())) {
                    fieldName = dbField.value();
                } else {
                    fieldName = field.getName();
                }
                if (columnName.equals(fieldName)) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                cacheMap.put(columnName, columnField);
            }
        }
    }

    @Override
    public long insert(T bean) {
        Map<String, String> map = getValues(bean);
        ContentValues values = getContentValues(map);
        return sqLiteDatabase.insert(tableName, null, values);
    }

    @Override
    public long update(T bean, T where) {
        Map<String, String> map = getValues(bean);
        ContentValues contentValues = getContentValues(map);

        Map<String, String> whereMap = getValues(where);
        Condition condition = new Condition(whereMap);
        return sqLiteDatabase.update(tableName, contentValues, condition.whereCause, condition.whereArgs);
    }

    @Override
    public long delete(T where) {
        Map<String, String> whereMap = getValues(where);
        Condition condition = new Condition(whereMap);
        return sqLiteDatabase.delete(tableName, condition.whereCause, condition.whereArgs);
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> whereMap = getValues(where);
        String limtString = null;
        if (startIndex != null && limit != null) {
            limtString = startIndex + "," + limit;
        }
        Condition condition = new Condition(whereMap);
        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereCause, condition.whereArgs, null, null, orderBy, limtString);
        return getResult(cursor, where);
    }

    private List<T> getResult(Cursor cursor, T obj) {
        ArrayList list = new ArrayList<>();
        //就是 User user = null;
        Object item = null;
        while (cursor.moveToNext()) {
            try {
                //user = new User();
                item = obj.getClass().newInstance();
                //user.setId(cursor.getId());
                Iterator<Map.Entry<String, Field>> iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Field> entry = iterator.next();
                    //获取列名
                    String columnName = entry.getKey();
                    //列名拿到列名在游标中的位置
                    Integer columnIndex = cursor.getColumnIndex(columnName);
                    //获取成员变量的类型
                    Field field = entry.getValue();
                    Class<?> type = field.getType();
                    //cursor.getString(columnIndex)
                    if (columnIndex != -1) {
                        if (type.isAssignableFrom(String.class)) {
                            //User user = new User();
                            //user.setId(1);
                            //上下两者相同
                            //id.set(user, 1);
                            field.set(item, cursor.getString(columnIndex));
                        } else if (type.isAssignableFrom(Double.class)) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type.isAssignableFrom(Integer.class)) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type.isAssignableFrom(Long.class)) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type.isAssignableFrom(byte[].class)) {
                            field.set(item, cursor.getBlob(columnIndex));
                        } else {
                            continue;
                        }
                    }
                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    private Map<String, String> getValues(T bean) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
            String key = entry.getKey();
            Field field = entry.getValue();
            field.setAccessible(true);
            try {
                Object object = field.get(bean);
                if (object == null) {
                    continue;
                }
                String value = object.toString();
                map.put(key, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues values = new ContentValues();
        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                values.put(key, value);
            }
        }
        return values;
    }

    public class Condition {
        private String whereCause;
        private String[] whereArgs;

        public Condition(Map<String, String> whereMap) {
            ArrayList<String> list = new ArrayList();
            StringBuilder sb = new StringBuilder();
            sb.append("1=1");
            //获取所有的字段名
            Set<String> keys = whereMap.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = whereMap.get(key);
                if (value != null) {
                    sb.append(" and " + key + " = ?");
                    list.add(value);
                }
            }
            this.whereCause = sb.toString();
            this.whereArgs = list.toArray(new String[list.size()]);
        }
    }
}
