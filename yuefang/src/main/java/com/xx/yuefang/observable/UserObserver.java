package com.xx.yuefang.observable;

public interface UserObserver<T> {

    void onUpdate(UserObservable<T> observable, T data);
}
