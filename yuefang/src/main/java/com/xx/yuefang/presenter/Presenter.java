package com.xx.yuefang.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AgentBean;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.ui.fragment.house.HouseArFragment;
import com.xx.yuefang.ui.fragment.house.SelectSaleManFragment;
import com.xx.yuefang.ui.fragment.login.LoginFragment;
import com.xx.yuefang.ui.fragment.main.SplashFragment;
import com.xx.yuefang.ui.fragment.me.Agent;
import com.xx.yuefang.ui.fragment.me.MyAppoint;
import com.xx.yuefang.ui.fragment.me.MyFans;
import com.xx.yuefang.ui.fragment.me.Review;
import com.xx.yuefang.ui.fragment.me.Setting;
import com.xx.yuefang.ui.fragment.me.developer.HouseResouces;
import com.xx.yuefang.ui.fragment.me.MyComment;
import com.xx.yuefang.ui.fragment.me.employee.MyEmployee;
import com.xx.yuefang.ui.fragment.me.developer.SellRecord;
import com.xx.yuefang.ui.fragment.me.member.MyCollection;
import com.xx.yuefang.ui.fragment.me.member.MyFootPrint;
import com.xx.yuefang.ui.widget.OneKeyYueFangDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Presenter {

    private FragmentManager fm;
    private Context context;

    private Presenter() {
        fragmentMap = new HashMap<>();
    }

    private static Presenter defaultInstance;

    public static Presenter getInstance() {
        Presenter presenter = defaultInstance;
        if (defaultInstance == null) {
            synchronized (Presenter.class) {
                if (defaultInstance == null) {
                    presenter = new Presenter();
                    defaultInstance = presenter;
                }
            }
        }
        return presenter;
    }

    public void step2fragment(BaseFragment fragment, String stack) {
        fm.beginTransaction().replace(R.id.mainActivity_fragmentContainer, fragment).addToBackStack(stack).commit();
    }

    public void step2fragment(BaseFragment fragment) {
        fm.beginTransaction().replace(R.id.mainActivity_fragmentContainer, fragment).commit();
    }


    public void step2Fragment(String name) {
        fm.beginTransaction().replace(R.id.mainActivity_fragmentContainer, getFragment(name)).addToBackStack(name).commit();
    }

    private HashMap<String, BaseFragment> fragmentMap;

    public BaseFragment getFragment(String name) {
        if ("my_house_resource".equals(name)) {
            return new HouseResouces();
        } else if ("wdlp".equals(name)) {
            return new HouseResouces();
        }
        if (fragmentMap.get(name) == null) {
            BaseFragment fragment = null;
            switch (name) {
                case "splash":
                    fragment = new SplashFragment();
                    break;
                case "Main":
                    fragment = new MainFragment();
                    break;
                case "my_house_resource":
                    fragment = new HouseResouces();
                    break;
                case "myfootprint":
                    fragment = new MyFootPrint();
                    break;
                case "my_comment":
                    fragment = new MyComment();
                    break;
                case "wdsc":
                    fragment = new MyCollection();
                    break;
                case "myappoint":
                    fragment = new MyAppoint();
                    break;
                case "sell_record":
                    fragment = new SellRecord();
                    break;
                case "my_employee":
                    fragment = new MyEmployee();
                    break;
                case "setting":
                    fragment = new Setting();
                    break;
                case "login":
                    fragment = new LoginFragment();
                    break;
                case "ar":
                    fragment = new HouseArFragment();
                    break;
                case "myfans":
                    fragment = new MyFans();
                    break;
                case "myagent":
                    fragment = new Agent();
                    break;
                case "lpyy":
                    fragment = new MyAppoint();
                    break;
                case "wdlp":
                    fragment = new HouseResouces();
                    break;
                case "review":
                    fragment = new Review();
                    break;
            }
            if (name.equals("review") || name.equals("myappoint") || name.equals("lpyy")) {
                return fragment;
            } else {
                fragmentMap.put(name, fragment);
            }
            return fragment;
        } else {
            return fragmentMap.get(name);
        }
    }

    public void back() {
        fm.popBackStack();
    }

    private void init(Builder builder) {
        this.fm = builder.fm;
        this.context = builder.context;
    }

    public void oneKeyYueFang(PremisesDetail.DataBean dataBean) {
        if(dataBean!=null){
            HttpUtil.getInstance().isReservation(dataBean.getId()).subscribe(
                    str -> {//判断是否已经预约 //判断是否有经纪人
                        OneKeyYueFangDialog oneKeyYueFangDialog = new OneKeyYueFangDialog((AppCompatActivity) context);
                        oneKeyYueFangDialog.show(dataBean);
                    }
            );
        }
    }

    public void selectSalePerson(PremisesDetail.DataBean dataBean) {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean == null) {
            Presenter.getInstance().step2Fragment("login");
        } else {
            HttpUtil.getInstance().getAgent(dataBean.getId()).subscribe(
                    str -> {
                        JSONObject jo = new JSONObject(str);
                        int errcode = jo.getInt("Errcode");
                        if (errcode == 0) {
                            AgentBean agentBean = GsonUtil.fromJson(str, AgentBean.class);
                            AgentBean.DataBean data = agentBean.getData();
                            Chat chat = new Chat();
                            Bundle bundle = new Bundle();
                            bundle.putString("converationId", data.getUserType() + "__" + data.getId());
                            bundle.putString("converstaionName", data.getBusinessCardName());
                            bundle.putInt("premieseId", dataBean.getId());
                            chat.setArguments(bundle);
                            Presenter.getInstance().step2fragment(chat, "chat");
                        } else if (errcode == 5008) {
                            SelectSaleManFragment selectSaleManFragment = new SelectSaleManFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("premisesId", dataBean.getId());
                            bundle.putInt("type", 2);
                            selectSaleManFragment.setArguments(bundle);
                            Presenter.getInstance().step2fragment(selectSaleManFragment, "selectSaleMan");
                        }
                    }
            );
        }
    }

    public void initCreditLayout(LinearLayout linearLayout, int credit) {
        int a = 0;
        int level = 0;
        if (credit >= 131) {
            int count = credit - 131;
            a = 3;
            if (credit > 310) {
                level = 5;
            } else {
                level = count / 40 + 1;
            }
        } else if (credit >= 31 && credit <= 130) {
            int count = credit - 31;
            level = count / 20 + 1;
            a = 2;
        } else if (credit >= 1 && credit <= 30) {
            int count = credit - 1;
            level = count / 10 + 1;
            a = 1;
        }

        for (int i = 0; i < level; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.layout_credit, linearLayout, false);
            switch (a) {
                case 1:
                    imageView.setBackgroundResource(R.drawable.heart);
                    break;
                case 2:
                    imageView.setBackgroundResource(R.drawable.badge);
                    break;
                case 3:
                    imageView.setBackgroundResource(R.drawable.crown);
                    break;
            }
            linearLayout.addView(imageView);
        }

    }

    public static class Builder {
        private FragmentManager fm;
        private Context context;

        public Builder fragmentManager(FragmentManager fm) {
            this.fm = fm;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Presenter build() {
            getInstance().init(this);
            return defaultInstance;
        }
    }

    public boolean hasNotchScreen(Activity activity) {
        if (getInt("ro.miui.notch", activity) == 1 || hasNotchAtHuawei(activity) || hasNotchAtOPPO(activity)
                || hasNotchAtVivo(activity) || isAndroidP(activity) != null) {
            return true;
        }

        return false;
    }

    public int getInt(String key, Activity activity) {
        int result = 0;
        if (isXiaomi()) {
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = new String(key);
                params[1] = new Integer(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Android P 刘海屏判断
     *
     * @param activity
     * @return
     */
    public DisplayCutout isAndroidP(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && android.os.Build.VERSION.SDK_INT >= 28) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null)
                return windowInsets.getDisplayCutout();
        }
        return null;
    }

    /**
     * 华为刘海屏判断
     *
     * @return
     */
    public boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            LogUtil.log("hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            LogUtil.log("hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            LogUtil.log("hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }


    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    public static final int VIVO_FILLET = 0x00000008;//是否有圆角

    public boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            LogUtil.log("hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            LogUtil.log("hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            LogUtil.log("hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    public boolean hasNotchAtOPPO(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    // 是否是小米手机
    public boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }
}
