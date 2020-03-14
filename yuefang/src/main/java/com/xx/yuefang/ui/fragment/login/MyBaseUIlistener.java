package com.xx.yuefang.ui.fragment.login;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xx.yuefang.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MyBaseUIlistener implements IUiListener {
    private Tencent mTencent;
    private Context context;
    private UserInfo mUserInfo;
    private  String str;
    public MyBaseUIlistener(Context context) {
        this.context = context;
    }
    @Override
    public void onComplete(Object response) {
        mTencent = Tencent.createInstance("101700584",context);
        Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
        LogUtil.log("response:" + response);
        JSONObject obj = (JSONObject) response;
        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken,expires);
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(context,qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    LogUtil.log("登录成功"+response.toString());
                    if(response ==null){
                        return;
                    }
                    try {
                        JSONObject jo = (JSONObject) response;
                        String img = jo.getString("figureurl_qq_1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    LogUtil.log("登录失败"+uiError.toString());
                }

                @Override
                public void onCancel() {


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();

    }
}
