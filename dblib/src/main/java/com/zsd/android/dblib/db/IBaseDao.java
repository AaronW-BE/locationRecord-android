package com.zsd.android.dblib.db;

import java.util.List;

public interface IBaseDao<T> {

    long insert(T bean);

    long update(T bean, T where);

    long delete(T where);

    List<T> query(T where);

    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);
}
