package com.xx.yuefang.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xx.yuefang.database.entity.City;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "T_City".
*/
public class CityDao extends AbstractDao<City, Long> {

    public static final String TABLENAME = "T_City";

    /**
     * Properties of entity City.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CitySort = new Property(0, long.class, "citySort", true, "CitySort");
        public final static Property ProId = new Property(1, int.class, "proId", false, "ProID");
        public final static Property CityName = new Property(2, String.class, "cityName", false, "CityName");
    }


    public CityDao(DaoConfig config) {
        super(config);
    }
    
    public CityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, City entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCitySort());
        stmt.bindLong(2, entity.getProId());
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(3, cityName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, City entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCitySort());
        stmt.bindLong(2, entity.getProId());
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(3, cityName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public City readEntity(Cursor cursor, int offset) {
        City entity = new City( //
            cursor.getLong(offset + 0), // citySort
            cursor.getInt(offset + 1), // proId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // cityName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, City entity, int offset) {
        entity.setCitySort(cursor.getLong(offset + 0));
        entity.setProId(cursor.getInt(offset + 1));
        entity.setCityName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(City entity, long rowId) {
        entity.setCitySort(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(City entity) {
        if(entity != null) {
            return entity.getCitySort();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(City entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
