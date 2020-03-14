package com.xx.yuefang.strategy;

import com.xx.yuefang.database.entity.UserBean;

public interface InitUser {

    void initUserData(UserBean userBean);
    void initUserView(UserBean userBean);
}
