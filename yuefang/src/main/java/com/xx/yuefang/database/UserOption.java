package com.xx.yuefang.database;

import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.util.List;

public class UserOption {

    private UserOption() {
    }

    private static UserOption defaultInstance;

    public static UserOption getInstance() {

        UserOption userOption = defaultInstance;
        if (defaultInstance == null) {
            synchronized (UserOption.class) {
                if (defaultInstance == null) {
                    userOption = new UserOption();
                    defaultInstance = userOption;
                }
            }
        }
        return userOption;
    }

    public void addUser(UserBean userBean) {
        UserBeanDao userBeanDao = YueFangApplication.getApplication().getDaoSession().getUserBeanDao();
        if (querryUser() == null) {
            userBeanDao.insert(userBean);
        }else {
            userBeanDao.update(userBean);
        }
    }

    public UserBean querryUser() {
        UserBeanDao userBeanDao = YueFangApplication.getApplication().getDaoSession().getUserBeanDao();
        List<UserBean> list = userBeanDao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void updateUser(UserBean userBean) {
        UserBeanDao userBeanDao = YueFangApplication.getApplication().getDaoSession().getUserBeanDao();
        userBeanDao.update(userBean);
    }


    public void delteUser() {
        UserBeanDao userBeanDao = YueFangApplication.getApplication().getDaoSession().getUserBeanDao();
        userBeanDao.deleteAll();
    }


}
