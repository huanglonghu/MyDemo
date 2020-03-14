package com.xx.yuefang.observable;

import java.util.ArrayList;
import java.util.List;

public class UserObservable<T> {
    public static final int TYPE_NICKNAME = 0;
    public static final int TYPE_SEX = 1;
    public static final int TYPE_PHONENUMBER = 2;
    public static final int TYPE_PHOTO = 3;
    public static final int TYPE_LOGINOUT = 4;
    public static final int TYPE_BUSINESSCARDNAME = 5;
    public static final int TYPE_EMAIL = 6;
    public static final int TYPE_ADRESS = 7;
    public static final int TYPE_COMPANYNAME = 8;
    public static final int TYPE_RESERVAIONDATS = 9;
    public static final int TYPE_INTRODUCE = 10;
    public static final int TYPE_REFRESH_APPOINT = 11;
    public static final int TYPE_SYSTEM_NEWS = 12;
    public static final int TYPE_CHAT_NEWS = 13;
    public static final int TYPE_LOGIN_SUCCESS = 14;
    public static final int TYPE_HOUSEDETAIL_TOGGLE = 15;
    public static final int TYPE_DELETE_AGENT = 16;
    public static final int TYPE_HOUSETYPEDETAL_BACK = 17;
    public static final int TYPE_SELECT_LOCATION = 18;
    public static final int TYPE_LOCATION_SEARCH = 19;
    public static final int TYPE_KEYBOARD_OUT = 20;
    public static final int TYPE_KEYBOARD_BACK = 21;
    public static final int TYPE_Bind_AGENT = 22;
    public static final int TYPE_SELECT_PHOTO_CHAT = 23;
    public static final int TYPE_USER_COMMENT_SUCCESS = 24;
    public static final int TYPE_SELECT_PHOTO_zm = 25;
    public static final int TYPE_SELECT_PHOTO_fm = 26;
    public static final int TYPE_SELECT_PHOTO_License = 27;
    public static final int TYPE_TELE_PHONE = 28;
    public static final int TYPE_PROFILE = 29;
    public static final int TYPE_ACCOUNTMSG_CHANGE = 30;

    private UserObservable() {
    }

    private static UserObservable defaultInstance;

    public static UserObservable getInstance() {
        UserObservable userObservable = defaultInstance;
        if (defaultInstance == null) {
            synchronized (UserObservable.class) {
                if (defaultInstance == null) {
                    userObservable = new UserObservable();
                    defaultInstance = userObservable;
                }
            }
        }
        return userObservable;
    }

    List<UserObserver<T>> mObservers = new ArrayList<UserObserver<T>>();

    public void register(UserObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mObservers.contains(observer))
                mObservers.add(observer);
        }
    }

    public synchronized void unregister(UserObserver observer) {
        mObservers.remove(observer);
    }

    public void notifyObservers(T data) {
        for (int i = 0; i < mObservers.size(); i++) {
            UserObserver<T> observer = mObservers.get(i);
            observer.onUpdate(this, data);
        }
    }
}
