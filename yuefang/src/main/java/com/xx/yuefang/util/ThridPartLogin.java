package com.xx.yuefang.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.RegisterBody;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.activity.MainActivity;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.fragment.login.LoginFragment;
import com.xx.yuefang.ui.fragment.login.PerfectInformation;
import com.xx.yuefang.ui.fragment.login.RegisterFragment;
import org.json.JSONObject;
import java.util.Arrays;

public class ThridPartLogin {

    private CallbackManager callbackManager;
    private BaseFragment fragment;
    private BaseUiListener loginListener;
    private ClickSureListener clickSureListener;
    private Tencent tencent;
    private ShareDialog shareDialog;

    private ThridPartLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //获取登录成功之后的用户详细信息
                        if (object != null) {
                            LogUtil.log("========facebook信息===========" + object.toString());
                            String id = object.optString("id");   //比如:1565455221565
                            String name = object.optString("name");  //比如：Zhang San
                            String gender = object.optString("gender");  //性别：比如 male （男）  female （女）
                            JSONObject object_pic = object.optJSONObject("picture");
                            JSONObject object_data = object_pic.optJSONObject("data");
                            String photo = object_data.optString("url");
                            if (clickSureListener != null) {
                                clickSureListener.openIdCallBack(3, id);
                            }
                            if (fragment != null && fragment instanceof LoginFragment) {
                                requestThirdLogin(3, id, name, gender, photo);
                            }
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,gender,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(fragment.getContext(), "facebook登录取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(fragment.getContext(), "facebook登录失败", Toast.LENGTH_SHORT).show();
            }
        });
        tencent = Tencent.createInstance("101700584", YueFangApplication.getApplication());
        loginListener = new BaseUiListener(tencent);
    }

    private static ThridPartLogin defaultInstance;

    public static ThridPartLogin getInstance() {
        ThridPartLogin thridPartLogin = defaultInstance;
        if (defaultInstance == null) {
            synchronized (ThridPartLogin.class) {
                if (defaultInstance == null) {
                    thridPartLogin = new ThridPartLogin();
                    defaultInstance = thridPartLogin;
                }
            }
        }
        return thridPartLogin;
    }

    public void initParam(BaseFragment fragment, ClickSureListener clickSureListener) {
        this.fragment = fragment;
        this.clickSureListener = clickSureListener;
    }

    public ClickSureListener getClickSureListener() {
        return clickSureListener;
    }

    public void setClickSureListener(ClickSureListener clickSureListener) {
        this.clickSureListener = clickSureListener;
    }

    private MainActivity activity;

    public void initActivity(MainActivity activity) {
        this.activity = activity;
        shareDialog = new ShareDialog(activity);
        shareDialog.registerCallback(callbackManager, shareCallback);
    }


    public void qqLogin() {
        tencent.login(activity, "get_simple_userinfo", loginListener);
    }

    public void wxdl(String state) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, "wx4685b2feaa7ac93a", true);
        boolean a = msgApi.registerApp("wx4685b2feaa7ac93a");
        SendAuth.Req req = new SendAuth.Req();
        //授权域 获取用户个人信息则填写snsapi_userinfo
        req.scope = "snsapi_userinfo";
        //用于保持请求和回调的状态 可以任意填写
        req.state = state;

        msgApi.sendReq(req);
    }


    public void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("user_friends", "public_profile"));
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        } else if (requestCode == 64206 || requestCode == 64207) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void qqShare(Bundle bundle) {
        String webUrl = bundle.getString("webUrl");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        //如果没有位图，可以传null，会显示默认的图片
        String picture = bundle.getString("picture");
        int scene = bundle.getInt("scene");
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);//描述
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, webUrl);//连接
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "约房");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, picture);
        if (scene == 2) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        tencent.shareToQQ(activity, params, loginListener);
    }

    public void wxShare(Context context, Bundle bundle) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, "wx4685b2feaa7ac93a", true);
        msgApi.registerApp("wx4685b2feaa7ac93a");
        // 检查手机或者模拟器是否安装了微信
        if (!msgApi.isWXAppInstalled()) {
            Toast.makeText(activity, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        String webUrl = bundle.getString("webUrl");
        webpageObject.webpageUrl = webUrl;
        String title = bundle.getString("title");
        msg.title = title;
        String description = bundle.getString("description");
        msg.description = description;
        //如果没有位图，可以传null，会显示默认的图片
        String picture = bundle.getString("picture");
        int type = bundle.getInt("type");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        bitmap = createBitmapThumbnail(bitmap);
        msg.setThumbImage(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage";
        req.message = msg;
        int scene = bundle.getInt("scene");
        req.scene = scene;
        // 向微信发送请求
        msgApi.sendReq(req);
    }

    public Bitmap createBitmapThumbnail(Bitmap bitMap) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 99;
        int newHeight = 99;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
        return newBitMap;
    }

    public void facebookShare(Bundle bundle) {
        String webUrl = bundle.getString("webUrl");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        //如果没有位图，可以传null，会显示默认的图片
        String picture = bundle.getString("picture");
        String url = ImagUtil.handleUrl(picture);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(webUrl))
                .setContentTitle(title)
                .setContentDescription(description)
                .setImageUrl(Uri.parse(url))
                .build();
        shareDialog.show(linkContent);
    }

    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException error) {
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            LogUtil.log("==========facebook分享成功=========");
        }
    };


    private class BaseUiListener implements IUiListener {
        private Tencent tencent;

        public BaseUiListener(Tencent tencent) {
            this.tencent = tencent;
        }

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(fragment.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(fragment.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            try {
                String accessToken = values.getString("access_token");
                String expires = values.getString("expires_in");
                String openID = values.getString("openid");
                if (clickSureListener != null) {
                    clickSureListener.openIdCallBack(2, openID);
                }
                if (fragment instanceof LoginFragment) {
                    tencent.setAccessToken(accessToken, expires);
                    tencent.setOpenId(openID);
                    UserInfo userInfo = new UserInfo(fragment.getContext(), tencent.getQQToken());
                    userInfo.getUserInfo(userInfoListener);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }

    }


    IUiListener userInfoListener = new IUiListener() {

        @Override
        public void onError(UiError arg0) {
        }

        @Override
        public void onComplete(Object arg0) {
            LogUtil.log("==========userInfo=========" + arg0.toString());
            if (arg0 == null) {
                return;
            }
            try {
                JSONObject jo = (JSONObject) arg0;
                String nickName = jo.getString("nickname");
                String gender = jo.getString("gender");
                String img = jo.getString("figureurl");
                requestThirdLogin(2, tencent.getOpenId(), nickName, gender, img);
            } catch (Exception e) {
            }
        }

        @Override
        public void onCancel() {
        }
    };

    public void requestThirdLogin(int loginType, String openId, String nickName, String gender, String img) {
        HttpUtil.getInstance().thridLogin(loginType, openId).subscribe(
                str -> {
                    if (str.contains("1009")) {
//                        RegisterFragment registerFragment = new RegisterFragment();
//                        RegisterBody registerBody = new RegisterBody();
//                        registerBody.setAvatar(img);
//                        registerBody.setLoginType(loginType);
//                        registerBody.setNickName(nickName);
//                        registerBody.setOpenId(openId);
//                        registerBody.setSex(gender.equals("男") ? false : true);
//                        registerFragment.setRegisterBody(registerBody);
//                        Presenter.getInstance().step2fragment(registerFragment, "register");

                        PerfectInformation perfectInformation = new PerfectInformation();
                        RegisterBody registerBody = new RegisterBody();
                        registerBody.setAvatar(img);
                        registerBody.setLoginType(loginType);
                        registerBody.setNickName(nickName);
                        registerBody.setOpenId(openId);
                        registerBody.setSex(gender.equals("男") ? false : true);
                        perfectInformation.setRegisterBody(registerBody);
                        Presenter.getInstance().step2fragment(perfectInformation, "perfectInformation");
                    } else {
                        Toast.makeText(fragment.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        com.xx.yuefang.bean.LoginResult loginResult = GsonUtil.fromJson(str, com.xx.yuefang.bean.LoginResult.class);
                        com.xx.yuefang.bean.LoginResult.DataBean data = loginResult.getData();
                        if (data != null) {
                            UserOption.getInstance().delteUser();
                            UserBean userBean = new UserBean();
                            userBean.setToken(data.getToken());
                            userBean.setUserType(data.getUserType());
                            userBean.setNickName(data.getNickName());
                            userBean.setId((long) data.getId());
                            UserOption.getInstance().addUser(userBean);
                            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGIN_SUCCESS));
                            Presenter.getInstance().back();
                        }
                    }
                }
        );
    }


}
