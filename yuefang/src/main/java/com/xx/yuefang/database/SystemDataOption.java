package com.xx.yuefang.database;

import com.xx.yuefang.bean.SystemSetup;
import com.xx.yuefang.database.entity.SystemData;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.util.List;

public class SystemDataOption {

    private SystemDataOption() {
    }

    private static SystemDataOption defaultInstance;

    public static SystemDataOption getInstance() {

        SystemDataOption userOption = defaultInstance;
        if (defaultInstance == null) {
            synchronized (SystemDataOption.class) {
                if (defaultInstance == null) {
                    userOption = new SystemDataOption();
                    defaultInstance = userOption;
                }
            }
        }
        return userOption;
    }

    public void insertSystemData(SystemData systemData) {
        SystemDataDao systemDataDao = YueFangApplication.getApplication().getDaoSession().getSystemDataDao();
        if (!isSystemDataExit()) {
            systemDataDao.insert(systemData);
        } else {
            systemDataDao.update(systemData);
        }
    }

    public boolean isSystemDataExit() {
        SystemDataDao systemDataDao = YueFangApplication.getApplication().getDaoSession().getSystemDataDao();
        List<SystemData> list = systemDataDao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public SystemData querrySystemData() {
        SystemDataDao systemDataDao = YueFangApplication.getApplication().getDaoSession().getSystemDataDao();
        SystemData systemData = systemDataDao.queryBuilder().unique();
        return systemData;
    }


}
