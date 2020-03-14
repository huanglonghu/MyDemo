package com.xx.yuefang.handler;

import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.strategy.ClickSureListener;
import java.util.HashMap;

public class UpdateAccountMsg {
    private UserBean userBean;
    private Builder builder;

    private UpdateAccountMsg(UserBean userBean, Builder builder) {
        this.userBean = userBean;
        this.builder = builder;
    }


    public void update() {
        switch (userBean.getUserType()) {
            case 2:
                HttpUtil.getInstance().updateDeveloper(builder.map, builder.key).subscribe(
                        str -> {
                            refreshData(builder.key, builder.value);
                            builder.completeStrategy.click(builder.value);
                        }
                );
                break;
            case 3:
                HttpUtil.getInstance().updateSalePerson(builder.map, builder.key).subscribe(
                        str -> {
                            refreshData(builder.key, builder.value);
                            builder.completeStrategy.click(builder.value);
                        }
                );
                break;
            case 4:
                HttpUtil.getInstance().updateUser(builder.map, builder.key).subscribe(
                        str -> {
                            refreshData(builder.key, builder.value);
                            builder.completeStrategy.click(builder.value);
                        }
                );
                break;
            case 5:
                HttpUtil.getInstance().updateCompany(builder.map, builder.key).subscribe(
                        str -> {
                            refreshData(builder.key, builder.value);
                            builder.completeStrategy.click(builder.value);
                        }
                );
                break;
            case 6:
                HttpUtil.getInstance().updateParter(builder.map, builder.key).subscribe(
                        str -> {
                            refreshData(builder.key, builder.value);
                            builder.completeStrategy.click(builder.value);
                        }
                );
                break;
        }
    }


    private void refreshData(String key, String value) {
        switch (key) {
            case "Name":
                if (userBean.getUserType() == 2 || userBean.getUserType() == 5) {
                    userBean.setCommanyName(value);
                } else if (userBean.getUserType() == 4) {
                    userBean.setNickName(value);
                }else {
                    userBean.setBusinessCardName(value);
                }
                break;
            case "Sex":
                userBean.setSex(value);
                break;
            case "PhoneNumber":
                userBean.setPhoneNumber(value);
                break;
            case "Email":
                userBean.setEmail(value);
                break;
            case "Address":
                userBean.setAddress(value);
                break;
            case "Profile":
                userBean.setProfile(value);
                break;
            case "Introduce":
                userBean.setIntroduce(value);
                break;
            case "ReservationDays":
                int i = Integer.parseInt(value);
                userBean.setReservationDays(i);
                break;
            case "Avatar":
                userBean.setAvatar(value);
                break;
            case "Telephone":
                userBean.setTelephone(value);
                break;
        }
        UserOption.getInstance().updateUser(userBean);
        if (key.equals("Name") || key.equals("PhoneNumber")) {
            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_ACCOUNTMSG_CHANGE));
        }
    }


    public static class Builder {
        private UserBean userBean;
        private ClickSureListener completeStrategy;
        private String key;
        private String value;
        private HashMap<String, Object> map;

        public Builder() {
            userBean = UserOption.getInstance().querryUser();
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder map(HashMap<String, Object> map) {
            this.map = map;
            return this;
        }


        public Builder completeStrategy(ClickSureListener completeStrategy) {
            this.completeStrategy = completeStrategy;
            return this;
        }

        public HashMap<String, Object> getMap() {
            return map;
        }

        public void setMap(HashMap<String, Object> map) {
            this.map = map;
        }

        public UpdateAccountMsg build() {
            return new UpdateAccountMsg(userBean, this);
        }
    }


}
