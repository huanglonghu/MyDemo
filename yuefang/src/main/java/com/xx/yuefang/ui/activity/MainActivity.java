package com.xx.yuefang.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.tencent.connect.common.Constants;
import com.xx.yuefang.R;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.handler.ActivityResultHandler;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.ui.fragment.house.Ar;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.ui.fragment.house.HotSearch;
import com.xx.yuefang.ui.fragment.house.HouseArFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.fragment.house.houseDetail.AskEveryoneList;
import com.xx.yuefang.ui.fragment.login.LoginFragment;
import com.xx.yuefang.ui.fragment.news.NewsDeatil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.SoftHideKeyBoardUtil;
import com.xx.yuefang.util.ThridPartLogin;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        setContentView(R.layout.activity_main);
        SoftHideKeyBoardUtil.assistActivity(this);
        fm = getSupportFragmentManager();
        new Presenter.Builder().context(this).fragmentManager(fm).build();
        fm.addOnBackStackChangedListener(getListener());
        if (YueFangApplication.getApplication().getCount() == 0) {
            Presenter.getInstance().step2Fragment("splash");
        } else {
            Presenter.getInstance().step2Fragment("Main");
        }
        HttpUtil.getInstance().init(this);
        ThridPartLogin.getInstance().initActivity(this);
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(R.id.mainActivity_fragmentContainer);
                    if (currFrag instanceof MainFragment) {
                        MainFragment mainFragment = (MainFragment) currFrag;
                        int position = mainFragment.getPosition();
                        UserBean userBean = UserOption.getInstance().querryUser();
                        if (userBean != null) {
                            if (position == 3) {
                                UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_REFRESH_APPOINT));
                            } else if (position == 2) {
                                UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_SYSTEM_NEWS));
                            }
                        }
                        currFrag.onResume();
                    }
                }
            }
        };
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtil.log(requestCode + "==============result============" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == 64206 || requestCode == 64207 || requestCode == Constants.REQUEST_QQ_SHARE) {
            ThridPartLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 1001) {
            //刷新客服聊天
            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_CHAT_NEWS));
        } else if (requestCode == 9527) {

        } else {
            ActivityResultHandler.getInstance().handler(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.mainActivity_fragmentContainer);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment instanceof MainFragment) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            } else if (fragment instanceof HouseArFragment || fragment instanceof LoginFragment || fragment instanceof Ar
                    || fragment instanceof Chat || fragment instanceof HouseDetailContainer || fragment instanceof HotSearch) {
                fragment.onKeyDown();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //  super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleScheme(intent);
    }

    public void handleScheme(Intent intent) {
        String action = intent.getAction();
        if ("push".equals(action)) {
            if (message != null) {
                toChat(message);
            }
        } else if ("android.intent.action.VIEW".equals(action)) {
            Uri appLinkData = intent.getData();
            if (appLinkData != null) {
                String type = appLinkData.getQueryParameter("type");
                String id = appLinkData.getQueryParameter("id");
                int i = Integer.parseInt(id);
                if ("1".equals(type)) {
                    toHouseDetail(i);
                }else if("2".equals(type)){
                    toNewsDetail(i);
                }
            }
        }
    }

    private Message message;

    public void onEvent(NotificationClickEvent event) {
        message = event.getMessage();
        //判断当前是否在聊天界面
        if (!isForegroundRunning()) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.setAction("push");
            startActivity(mainIntent);
        } else {
            toChat(message);
        }
    }


    private void toHouseDetail(int id) {
        HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putBoolean("isFirst", true);
        bundle.putBoolean("initData", true);
        houseDetailFragment.setArguments(bundle);
        Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
    }

    private void toNewsDetail(int id){
        NewsDeatil newsDeatil = new NewsDeatil();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        newsDeatil.setArguments(bundle);
        Presenter.getInstance().step2fragment(newsDeatil, "newsDetail");
    }

    private void toChat(Message message) {
        BaseFragment currFrag = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.mainActivity_fragmentContainer);
        UserInfo fromUser = message.getFromUser();
        String nickname = fromUser.getNickname();
        String userName = fromUser.getUserName();
        Bundle bundle = new Bundle();
        bundle.putString("converstaionName", nickname);
        bundle.putString("converationId", userName);
        if (currFrag instanceof Chat) {
            currFrag.setArguments(bundle);
            currFrag.initData();
        } else {
            Chat chat = new Chat();
            chat.setArguments(bundle);
            Presenter.getInstance().step2fragment(chat, "chat");
        }
    }

    public boolean isForegroundRunning() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list != null) {
            for (ActivityManager.RunningAppProcessInfo info : list) {
                if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && getApplication().getPackageName().equals(info.processName)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域

                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}