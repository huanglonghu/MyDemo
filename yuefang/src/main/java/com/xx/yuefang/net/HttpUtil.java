package com.xx.yuefang.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.xx.yuefang.bean.AddPareterBody;
import com.xx.yuefang.bean.AddSalerBody;
import com.xx.yuefang.bean.Appoint;
import com.xx.yuefang.bean.AppointSalepersonBean;
import com.xx.yuefang.bean.BackPsw;
import com.xx.yuefang.bean.ChangePsw;
import com.xx.yuefang.bean.CompanyRegisterBody;
import com.xx.yuefang.bean.EnterFor;
import com.xx.yuefang.bean.ErrorBody;
import com.xx.yuefang.bean.GetAllApoint;
import com.xx.yuefang.bean.GetHouseResourcesList;
import com.xx.yuefang.bean.GetNewsList;
import com.xx.yuefang.bean.GetPremisesList;
import com.xx.yuefang.bean.GetReservationBody;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.bean.LoginBody;
import com.xx.yuefang.bean.ParterRegisterBody;
import com.xx.yuefang.bean.QuerryReservationBody;
import com.xx.yuefang.bean.QuerryReviewBody;
import com.xx.yuefang.bean.RegisterBody;
import com.xx.yuefang.bean.UpdateCompany;
import com.xx.yuefang.bean.UpdateCompanyBody;
import com.xx.yuefang.bean.UpdateDeveloper;
import com.xx.yuefang.bean.UpdateDeveloperBody;
import com.xx.yuefang.bean.UpdateParter;
import com.xx.yuefang.bean.UpdateParterBody;
import com.xx.yuefang.bean.UpdateReviewBody;
import com.xx.yuefang.bean.UpdateSalepersonBody;
import com.xx.yuefang.bean.UpdateSaler;
import com.xx.yuefang.bean.UpdateUser;
import com.xx.yuefang.bean.UpdateUserBody;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.widget.NetLoading;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Query;

public class HttpUtil {
    private HttpInterface httpInterface;
    private HttpLoggingInterceptor loggingInterceptor;
    private Retrofit retrofit;
    private OkHttpClient client;
    private NetLoading netLoading;

    private HttpUtil() {
        //打印retrofit日志
        loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                LogUtil.log(message);
            }
        });

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl oldHttpUrl = request.url();
                Request.Builder builder = request.newBuilder();
                String path = oldHttpUrl.encodedPath();
                if (path.contains("/sns/oauth2/access_token") || path.contains("/sns/userinfo")) {
                    String oldUrl = oldHttpUrl.toString();
                    String newUrl = "https://api.weixin.qq.com" + oldUrl.substring(HttpParam.baseUrl.length(), oldUrl.length());
                    Request newRequest = request
                            .newBuilder()
                            .url(newUrl)
                            .build();
                    return chain.proceed(newRequest);
                } else {
                    if (!path.contains("RegisterUser") && !path.contains("Login")) {
                        UserBean userBean = UserOption.getInstance().querryUser();
                        if (userBean != null) {
                            String token = userBean.getToken();
                            if (!TextUtils.isEmpty(token)) {
                                request = builder.addHeader("auth", token).build();
                                LogUtil.log("==============token============" + token);
                            }
                        }
                    }

                    okhttp3.Response response = chain.proceed(request);
                    if (response.code() == 401) {
                        UserBean userBean = UserOption.getInstance().querryUser();
                        if (userBean != null) {
                            UserOption.getInstance().delteUser();
                        }
                        UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGINOUT));
                        Presenter.getInstance().step2Fragment("login");
                    }
                    return response;
                }
            }
        };
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder().client(client).baseUrl(HttpParam.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        httpInterface = retrofit.create(HttpInterface.class);
    }

    private static HttpUtil defauleInstance;

    public static HttpUtil getInstance() {
        HttpUtil httpUtil = defauleInstance;
        if (defauleInstance == null) {
            synchronized (HttpUtil.class) {
                httpUtil = new HttpUtil();
                defauleInstance = httpUtil;
            }
        }
        return httpUtil;
    }

    private Context context;

    public void init(Context context) {
        this.context = context;
    }


    public Observable<String> wxdl(String code) {
        String APP_ID = "wx4685b2feaa7ac93a";
        String APP_Secret = "2f2325e8efc2fa72a156a86049831a74";
        Call<ResponseBody> call = httpInterface.wxdl(APP_ID, APP_Secret, code, "authorization_code");
        return enqueueCall2(call);
    }

    public Observable<String> getwxUserMsg(String accessTken, String openId) {
        Call<ResponseBody> call = httpInterface.getwxUserMsg(accessTken, openId);
        return enqueueCall2(call);
    }

    public Observable<String> isConcernWxgzh() {
        Call<ResponseBody> call = httpInterface.isConcernWxgzh();
        return enqueueCall(call);
    }

    public Observable<String> isPwdExit() {
        Call<ResponseBody> call = httpInterface.isPwdExit();
        return enqueueCall(call);
    }

    public Observable<String> querryMyAgent(int page) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Page", page);
        map.put("Limit", 20);
        Call<ResponseBody> call = httpInterface.querryMyAgent(map);
        return enqueueCall(call);
    }


    public Observable<String> querryMyFans(int page) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Page", page);
        map.put("Limit", 20);
        Call<ResponseBody> call = httpInterface.querryMyFans(map);
        return enqueueCall(call);
    }


    public Observable<String> getFansDetail(int id) {
        Call<ResponseBody> call = httpInterface.getFansDetail(id);
        return enqueueCall(call);
    }


    public Observable<String> register(RegisterBody registerBody) {
        Call<ResponseBody> call = httpInterface.register(registerBody);
        return enqueueCall(call);
    }

    public Observable<String> login(LoginBody loginBody) {
        Call<ResponseBody> call = httpInterface.login(loginBody);
        return enqueueCall(call);
    }

    public Observable<String> getPremisesList(GetPremisesList getPremisesList) {
        Call<ResponseBody> call = httpInterface.getPremisesList(getPremisesList);
        return enqueueCall(call);
    }

    public Observable<String> getPremisesDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getPremisesDetailById(id);
        return enqueueCall(call);
    }

    public Observable<String> getMoreHouseMessageById(int id) {
        Call<ResponseBody> call = httpInterface.getMoreHouseMessageById(id);
        return enqueueCall(call);
    }

    public Observable<String> getPremisesById(int id) {
        Call<ResponseBody> call = httpInterface.getPremisesById(id);
        return enqueueCall(call);
    }

    public Observable<String> rise(int id) {
        Call<ResponseBody> call = httpInterface.rise(id);
        return enqueueCall(call);
    }

    public Observable<String> getUserMsg(long id) {
        Call<ResponseBody> call = httpInterface.getUserMsg(id);
        return enqueueCall(call);
    }

    public Observable<String> appoint(Appoint appoint) {
        Call<ResponseBody> call = httpInterface.appoint(appoint);
        return enqueueCall(call);
    }

    public Observable<String> getSalepresonListById(int id) {
        Call<ResponseBody> call = httpInterface.getSalepresonListById(id);
        return enqueueCall(call);
    }

    public Observable<String> getPickUpType() {
        Call<ResponseBody> call = httpInterface.getPickUpType();
        return enqueueCall(call);
    }


    public Observable<String> getYzm(String mobile, int type) {//类型（1用户注册验证码2登录确认验证码3修改密码验证码）
        HashMap<String, Object> map = new HashMap<>();
        map.put("PhoneNumber", mobile);
        map.put("Type", type);
        String s = "PhoneNumber=" + mobile + "&Type=" + type + "&key=" + "45d56d70086043538e9c17b73051dbc7";
        String md5 = getMD5(s);
        map.put("Sign", md5);
        Call<ResponseBody> call = httpInterface.getYzm(map);
        return enqueueCall(call);
    }

    public String getMD5(String str) {
        byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder md5 = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            if ((b & 0xFF) < 0x10)
                md5.append("0");
            md5.append(Integer.toHexString(b & 0xFF));
        }
        return md5.toString();
    }


    public Observable<String> getAppointDay(int id) {
        Call<ResponseBody> call = httpInterface.getAppointDay(id);
        return enqueueCall(call);
    }

    public Observable<String> getFirstYuyue() {
        Call<ResponseBody> call = httpInterface.getFirstYuyue();
        return enqueueCall(call);
    }

    public Observable<String> getCollectionList() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Page", 1);
        map.put("Limit", 200);
        Call<ResponseBody> call = httpInterface.getCollectionList(map);
        return enqueueCall(call);
    }

    public Observable<String> getAllApoint(GetAllApoint getAllApoint) {
        Call<ResponseBody> call = httpInterface.getAllApoint(getAllApoint);
        return enqueueCall(call);
    }


    public Observable<String> getDeveloperMsgById(long id) {
        Call<ResponseBody> call = httpInterface.getDeveloperMsgById(id);
        return enqueueCall(call);
    }

    public Observable<String> getSellerMsgById(long id) {
        Call<ResponseBody> call = httpInterface.getSellerMsgById(id);
        return enqueueCall(call);
    }

    public Observable<String> getReservationType() {
        Call<ResponseBody> call = httpInterface.getReservationType();
        return enqueueCall(call);
    }

    public Observable<String> appointOption2(int id, int state, String remark) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("State", state);
        map.put("Id", id);
        map.put("Remarks", remark);
        Call<ResponseBody> call = httpInterface.appointOption(map);
        return enqueueCall(call);
    }

    public Observable<String> appointOption(int id, int state) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("State", state);
        map.put("Id", id);
        Call<ResponseBody> call = httpInterface.appointOption(map);
        return enqueueCall(call);
    }


    public Observable<String> getHouseResourceListBySeller(GetHouseResourcesList getHouseResourcesList) {
        Call<ResponseBody> call = httpInterface.getHouseResourceListBySeller(getHouseResourcesList);
        return enqueueCall(call);
    }

    public Observable<String> getHouseResourceListByDeveloper(GetHouseResourcesList getHouseResourcesList) {
        Call<ResponseBody> call = httpInterface.getHouseResourceListByDeveloper(getHouseResourcesList);
        return enqueueCall(call);
    }


    public Observable<String> houseResourceOption(ArrayList<Integer> ids) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", ids);
        Call<ResponseBody> call = httpInterface.houseResourceOption(map);
        return enqueueCall(call);
    }

    public Observable<String> queryCommentList() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Page", 1);
        map.put("Limit", 100);
        Call<ResponseBody> call = httpInterface.queryCommentList(map);
        return enqueueCall(call);
    }

    public Observable<String> getCommentDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getCommentDetailById(id);
        return enqueueCall(call);
    }

    public Observable<String> sellerComment(Integer id, float score, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesReservationId", id);
        map.put("Score", score);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.sellerComment(map);
        return enqueueCall(call);
    }

    public Observable<String> userComment(Integer id, float score, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesReservationId", id);
        map.put("Score", score);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.userComment(map);
        return enqueueCall(call);
    }

    public Observable<String> getSellerList(GetSellerList getSellerList) {
        Call<ResponseBody> call = httpInterface.getSellerList(getSellerList);
        return enqueueCall(call);
    }

    public Observable<String> getParterList(GetSellerList getSellerList) {
        Call<ResponseBody> call = httpInterface.getParterList(getSellerList);
        return enqueueCall(call);
    }

    public Observable<String> deleteSaleperson(int id) {
        Call<ResponseBody> call = httpInterface.deleteSaleperson(id);
        return enqueueCall(call);
    }

    public Observable<String> deleteSalepersons(ArrayList<Integer> ids) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", ids);
        Call<ResponseBody> call = httpInterface.deleteSalePersons(map);
        return enqueueCall(call);
    }

    public Observable<String> deleteParter(int id) {
        Call<ResponseBody> call = httpInterface.deleteParter(id);
        return enqueueCall(call);
    }

    public Observable<String> deleteParters(ArrayList<Integer> ids) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", ids);
        Call<ResponseBody> call = httpInterface.deleteParters(map);
        return enqueueCall(call);
    }


    public Observable<String> addSaler(AddSalerBody addSalerBody) {
        Call<ResponseBody> call = httpInterface.addSaler(addSalerBody);
        return enqueueCall(call);
    }


    public Observable<String> deleteAppoint(ArrayList<Integer> list) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", list);
        Call<ResponseBody> call = httpInterface.deleteAppoint(map);
        return enqueueCall(call);
    }

    public Observable<String> getReservationDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getReservationDetailById(id);
        return enqueueCall(call);
    }

    public Observable<String> userCollection(int id) {
        Call<ResponseBody> call = httpInterface.userCollection(id);
        return enqueueCall(call);
    }

    public Observable<String> getFootprint() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Page", 1);
        map.put("Limit", 200);
        Call<ResponseBody> call = httpInterface.getFootprint(map);
        return enqueueCall(call);
    }


    public Observable<String> isReservation(int id) {
        Call<ResponseBody> call = httpInterface.isReservation(id);
        return enqueueCall(call);
    }

    public Observable<String> updateUser(UpdateUser updateUser) {
        Call<ResponseBody> call = httpInterface.updateUser(updateUser);
        return enqueueCall(call);
    }

    public Observable<String> getAdList() {
        Call<ResponseBody> call = httpInterface.getAdList();
        return enqueueCall(call);
    }

    public Observable<String> getNewsList(GetNewsList getNewsList) {
        Call<ResponseBody> call = httpInterface.getNewsList(getNewsList);
        return enqueueCall(call);
    }


    public Observable<String> getNewsType() {
        Call<ResponseBody> call = httpInterface.getNewsType();
        return enqueueCall(call);
    }


    public Observable<String> getNewsDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getNewsDetailById(id);
        return enqueueCall(call);
    }

    public Observable<String> getDynamicDetail(int id) {
        Call<ResponseBody> call = httpInterface.getDynamicDetail(id);
        return enqueueCall(call);
    }


    public Observable<String> enterfor(EnterFor enterFor) {
        Call<ResponseBody> call = httpInterface.enterfor(enterFor);
        return enqueueCall(call);
    }


    public Observable<String> deleteBrowseRecords(ArrayList<Integer> ids) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", ids);
        Call<ResponseBody> call = httpInterface.deleteBrowseRecords(map);
        return enqueueCall(call);
    }

    public Observable<String> deleteCollectionRecords(ArrayList<Integer> ids) {
        HashMap<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ids", ids);
        Call<ResponseBody> call = httpInterface.deleteCollectionRecords(map);
        return enqueueCall(call);
    }

    public Observable<String> commentOn(int premisesBaseId, float score, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("Score", score);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.commentOn(map);
        return enqueueCall(call);
    }


    public Observable<String> ask(int premisesBaseId, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.ask(map);
        return enqueueCall(call);
    }


    public Observable<String> replay(int id, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ParentId", id);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.replay(map);
        return enqueueCall(call);
    }

    public Observable<String> getQaList(Integer premisesBaseId, Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("ParentId", id);
        map.put("Limit", 100);
        map.put("Page", 1);
        Call<ResponseBody> call = httpInterface.getQaList(map);
        return enqueueCall(call);
    }

    public Observable<String> getSearchDatas() {
        Call<ResponseBody> call = httpInterface.getSearchDatas();
        return enqueueCall(call);
    }

    public Observable<String> upload(MultipartBody.Part parts, String url) {
        Call<ResponseBody> call = httpInterface.uploadPicture(url, parts);
        return enqueueCall(call);
    }

    public Observable<String> exit() {
        Call<ResponseBody> call = httpInterface.exit();
        return enqueueCall(call);
    }


    public Observable<String> updateDeveloper(UpdateDeveloper updateDeveloper) {
        Call<ResponseBody> call = httpInterface.updateDeveloper(updateDeveloper);
        return enqueueCall(call);
    }

    public Observable<String> updateSaler(UpdateSaler updateSaler) {
        Call<ResponseBody> call = httpInterface.updateSaler(updateSaler);
        return enqueueCall(call);
    }

    public Observable<String> getSystemSetup() {
        Call<ResponseBody> call = httpInterface.getSystemSetup();
        return enqueueCall(call);
    }

    public Observable<String> getAllSalePerson(int developerId, int premisesBaseId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("DeveloperId", developerId);
        Call<ResponseBody> call = httpInterface.getAllSalePerson(map);
        return enqueueCall(call);
    }

    public Observable<String> appointSalePerson(AppointSalepersonBean appointSalepersonBean) {
        Call<ResponseBody> call = httpInterface.appointSalePerson(appointSalepersonBean);
        return enqueueCall(call);
    }

    public Observable<String> backPsw(BackPsw backPsw) {
        Call<ResponseBody> call = httpInterface.backPsw(backPsw);
        return enqueueCall(call);
    }

    public Observable<String> getSystemNews() {
        Call<ResponseBody> call = httpInterface.getSystemNews();
        return enqueueCall(call);
    }

    public Observable<String> querryCommentById(int id) {
        Call<ResponseBody> call = httpInterface.qyerryCommentByid(id);
        return enqueueCall(call);
    }

    public Observable<String> querryWdById(int id) {
        Call<ResponseBody> call = httpInterface.querryWdById(id);
        return enqueueCall(call);
    }


    public Observable<String> replayComment(int parentId, String content) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("ParentId", parentId);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.replyNewsComment(map);
        return enqueueCall(call);
    }


    public Observable<String> riseNewsComment(int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Id", id);
        Call<ResponseBody> call = httpInterface.riseNewsComment(map);
        return enqueueCall(call);
    }


    public Observable<String> getAllPremisesId() {
        Call<ResponseBody> call = httpInterface.getAllPremisesId();
        return enqueueCall(call);
    }

    public Observable<String> getPremisesNum(String name) {
        Call<ResponseBody> call = httpInterface.getPremisesNum(name);
        return enqueueCall(call);
    }


    public Observable<String> getPremisesCommentByReplyId(int id) {
        Call<ResponseBody> call = httpInterface.getPremisesCommentByReplyId(id);
        return enqueueCall(call);
    }

    public Observable<String> getReservationNum(GetReservationBody getReservationBody) {
        Call<ResponseBody> call = httpInterface.getReservationNum(getReservationBody);
        return enqueueCall(call);
    }


    public Observable<String> getNewsInfoCommentByReplyId(int id) {
        Call<ResponseBody> call = httpInterface.getNewsInfoCommentByReplyId(id);
        return enqueueCall(call);
    }


    public Observable<String> getPremiseMsgByDdeveloperId() {
        Call<ResponseBody> call = httpInterface.getPremiseMsgByDdeveloperId();
        return enqueueCall(call);
    }

    public Observable<String> registerPartner(ParterRegisterBody registerBody) {
        Call<ResponseBody> call = httpInterface.registerPartner(registerBody);
        return enqueueCall(call);
    }

    public Observable<String> registerCompany(CompanyRegisterBody companyRegisterBody) {
        Call<ResponseBody> call = httpInterface.registerCompany(companyRegisterBody);
        return enqueueCall(call);
    }

    public Observable<String> querryReservation(QuerryReservationBody body) {
        Call<ResponseBody> call = httpInterface.querryReservation(body);
        return enqueueCall(call);
    }

    public Observable<String> getReviewNum(int[] applyType) {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("ApplyType", applyType);
        Call<ResponseBody> call = httpInterface.getReviewNum(map);
        return enqueueCall(call);
    }

    public Observable<String> updateRevieweState(UpdateReviewBody body) {
        Call<ResponseBody> call = httpInterface.updateRevieweState(body);
        return enqueueCall(call);
    }

    public Observable<String> getReviewDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getReviewDetailById(id);
        return enqueueCall(call);
    }

    public Observable<String> querryReviews(QuerryReviewBody body) {
        Call<ResponseBody> call = httpInterface.querryReviews(body);
        return enqueueCall(call);
    }

    public Observable<String> getAgentByPremisesId(@Query("id") int id) {
        Call<ResponseBody> call = httpInterface.getAgentByPremisesId(id);
        return enqueueCall(call);
    }

    public Observable<String> bindCompany(int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Id", id);
        Call<ResponseBody> call = httpInterface.bindCompany(map);
        return enqueueCall(call);
    }

    public Observable<String> unBindCompany(int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Id", id);
        Call<ResponseBody> call = httpInterface.unBindCompany(map);
        return enqueueCall(call);
    }

    public Observable<String> isBindCompany() {
        Call<ResponseBody> call = httpInterface.isBindCompany();
        return enqueueCall(call);
    }

    public Observable<String> updateCompany(UpdateCompany updateCompany) {
        Call<ResponseBody> call = httpInterface.updateCompany(updateCompany);
        return enqueueCall(call);
    }

    public Observable<String> updateParter(UpdateParter updateParter) {
        Call<ResponseBody> call = httpInterface.updateParter(updateParter);
        return enqueueCall(call);
    }

    public Observable<String> addParter(AddPareterBody addPareterBody) {
        Call<ResponseBody> call = httpInterface.addParter(addPareterBody);
        return enqueueCall(call);
    }

    public Observable<String> updateParter(HashMap<String, Object> body, String key) {
        Call<ResponseBody> call = null;
        switch (key) {
            case "Name":
                call = httpInterface.updateParterName(body);
                break;
            case "Sex":
                call = httpInterface.updateParterSex(body);
                break;
            case "PhoneNumber":
                call = httpInterface.updateParterPhone(body);
                break;
            case "Email":
                call = httpInterface.updateParterWx(body);
                break;
            case "Address":
                call = httpInterface.updateParterAddress(body);
                break;
            case "Profile":
                call = httpInterface.updateParterProfile(body);
                break;
            case "Avatar":
                call = httpInterface.updateParterAvater(body);
                break;
            case "Telephone":
                call = httpInterface.updateParterTel(body);
                break;
        }

        return enqueueCall(call);
    }

    public Observable<String> updateSalePerson(HashMap<String, Object> body, String key) {
        Call<ResponseBody> call = null;
        switch (key) {
            case "Name":
                call = httpInterface.updateSalePersonName(body);
                break;
            case "Sex":
                call = httpInterface.updateSalePersonSex(body);
                break;
            case "PhoneNumber":
                call = httpInterface.updateSalePersonPhone(body);
                break;
            case "Email":
                call = httpInterface.updateSalePersonWx(body);
                break;
            case "Address":
                call = httpInterface.updateSalePersonAddress(body);
                break;
            case "Profile":
                call = httpInterface.updateSalePersonProfile(body);
                break;
            case "Avatar":
                call = httpInterface.updateSalePersonAvater(body);
                break;
            case "Telephone":
                call = httpInterface.updateSalePersonTel(body);
                break;

        }

        return enqueueCall(call);

    }

    public Observable<String> updateUser(HashMap<String, Object> map, String key) {
        Call<ResponseBody> call = null;
        switch (key) {
            case "Name":
                call = httpInterface.updateUserName(map);
                break;
            case "Sex":
                call = httpInterface.updateUserSex(map);
                break;
            case "PhoneNumber":
                call = httpInterface.updateUserPhone(map);
                break;
            case "Avatar":
                call = httpInterface.updateUserAvater(map);
                break;
        }
        return enqueueCall(call);

    }

    public Observable<String> updateCompany(HashMap<String, Object> map, String key) {
        Call<ResponseBody> call = null;
        switch (key) {
            case "Name":
                call = httpInterface.updateCompanyName(map);
                break;
            case "Introduce":
                call = httpInterface.updateCompanyIntroduce(map);
                break;
            case "PhoneNumber":
                call = httpInterface.updateCompanyPhone(map);
                break;
            case "Avatar":
                call = httpInterface.updateCompanyAvatar(map);
                break;
        }
        return enqueueCall(call);
    }

    public Observable<String> updateDeveloper(HashMap<String, Object> map, String key) {

        Call<ResponseBody> call = null;
        switch (key) {
            case "Name":
                call = httpInterface.updateDeveloperName(map);
                break;
            case "Introduce":
                call = httpInterface.updateDeveloperIntroduce(map);
                break;
            case "PhoneNumber":
                call = httpInterface.updateDeveloperPhone(map);
                break;
            case "Avatar":
                call = httpInterface.updateDeveloperAvatar(map);
                break;
            case "ReservationDays":
                call = httpInterface.updateDeveloperReservationDays(map);
                break;
        }
        return enqueueCall(call);

    }


    public Observable<String> getParterById() {
        Call<ResponseBody> call = httpInterface.getParterById();
        return enqueueCall(call);
    }

    public Observable<String> getCompanyById() {
        Call<ResponseBody> call = httpInterface.getCompanyById();
        return enqueueCall(call);
    }

    public Observable<String> getCommentList(int premisesBaseId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("Limit", 100);
        map.put("Page", 1);
        map.put("TimeOrHeat", true);
        Call<ResponseBody> call = httpInterface.getCommentList(map);
        return enqueueCall(call);
    }

    public Observable<String> isReadSystemNews() {
        Call<ResponseBody> call = httpInterface.isReadSystemNews();
        return enqueueCall(call);
    }

    public Observable<String> commentRise(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RiseOrFall", true);
        map.put("Id", id);
        Call<ResponseBody> call = httpInterface.commentRise(map);
        return enqueueCall(call);
    }

    public Observable<String> changePsw(ChangePsw changePsw, int userType) {
        Call<ResponseBody> call = null;
        switch (userType) {
            case 2:
                call = httpInterface.changeDeveloperPsw(changePsw);
                break;
            case 3:
                call = httpInterface.changeSalepersonPsw(changePsw);
                break;
            case 4:
                call = httpInterface.changeUserPsw(changePsw);
                break;
            case 5:
                call = httpInterface.changeCompanyPsw(changePsw);
                break;
            case 6:
                call = httpInterface.changeParterPsw(changePsw);
                break;
        }
        return enqueueCall(call);
    }


    public Observable<String> thridLogin(int type, String openId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("LoginType", type);
        map.put("OpenId", openId);
        Call<ResponseBody> call = httpInterface.thridLogin(map);
        return enqueueCall(call);
    }


    public Observable<String> thridRegister(RegisterBody thridRegister) {
        Call<ResponseBody> call = httpInterface.thridRegister(thridRegister);
        return enqueueCall(call);
    }

    public Observable<String> querryPremisesList(int premisesBaseId, int page) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("PremisesBaseId", premisesBaseId);
        map.put("Page", page);
        map.put("Limit", 10);
        Call<ResponseBody> call = httpInterface.querryPremisesList(map);
        return enqueueCall(call);
    }

    public Observable<String> checkVersion() {
        Call<ResponseBody> call = httpInterface.checkVersion();
        return enqueueCall3(call);
    }

    public Observable<String> bindThird(int loginType, String opendId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("LoginType", loginType);
        map.put("OpenId", opendId);
        Call<ResponseBody> call = httpInterface.bindThird(map);
        return enqueueCall(call);
    }

    public Observable<String> unBindThird(int loginType) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("LoginType", loginType);
        Call<ResponseBody> call = httpInterface.unBindThird(map);
        return enqueueCall(call);
    }

    public Observable<String> getTotalById(int id) {
        Call<ResponseBody> call = httpInterface.getTotalById(id);
        return enqueueCall(call);
    }

    public Observable<String> getPremisesDetailLeftOrRight(boolean isLeft, int id) {
        Call<ResponseBody> call = httpInterface.getPremisesDetailLeftOrRight(isLeft, id);
        return enqueueCall(call);
    }

    public Observable<String> deleteAgent(int id, String reason) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Id", id);
        map.put("ApplyReason", reason);
        Call<ResponseBody> call = httpInterface.deleteAgent(map);
        return enqueueCall(call);
    }

    public Observable<String> deleteAgentofPart(int userId, int userType, String reseon) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", userId);
        map.put("UserType", userType);
        map.put("ApplyReason", reseon);
        Call<ResponseBody> call = httpInterface.deleteAgentofPart(map);
        return enqueueCall(call);
    }

    public Observable<String> getAgentMsg(int userId, int userType) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("UserId", userId);
        map.put("UserType", userType);
        Call<ResponseBody> call = httpInterface.getAgentMsg(map);
        return enqueueCall(call);
    }

    public Observable<String> addAgent(int userType, int PremisesBaseId, int SalespersonId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("UserType", userType);
        map.put("PremisesBaseId", PremisesBaseId);
        map.put("SalespersonId", SalespersonId);
        Call<ResponseBody> call = httpInterface.addAgent(map);
        return enqueueCall(call);
    }

    public Observable<String> getPosterDetail(int id) {
        Call<ResponseBody> call = httpInterface.getPosterDetail(id);
        return enqueueCall(call);
    }

    public Observable<String> getPremisesMsg(int id) {
        Call<ResponseBody> call = httpInterface.getPremisesMsg(id);
        return enqueueCall(call);
    }

    public Observable<String> writeLog(String content) {
        Call<ResponseBody> staticImg = httpInterface.writeLog(content);
        return enqueueCall(staticImg);
    }

    public Observable<String> getAgent(int id) {
        Call<ResponseBody> agent = httpInterface.getAgent(id);
        return enqueueCall(agent);
    }

    public Observable<String> getNewsCommentList(int newsId, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("NewsInfoId", newsId);
        map.put("Page", page);
        map.put("Limit", 20);
        map.put("TimeOrHeat", true);
        Call<ResponseBody> call = httpInterface.getNewsCommentList(map);
        return enqueueCall(call);
    }

    public Observable<String> getNewsCommentDetailById(int id) {
        Call<ResponseBody> call = httpInterface.getNewsCommentDetailById(id);
        return enqueueCall(call);
    }


    public Observable<String> addNewsComment(int newsInfoId, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("NewsInfoId", newsInfoId);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.addNewsComment(map);
        return enqueueCall(call);
    }


    public Observable<String> replyPremisesComment(int id, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ParentId", id);
        map.put("Content", content);
        Call<ResponseBody> call = httpInterface.replayComment(map);
        return enqueueCall(call);
    }


    HashMap<Call<ResponseBody>, NetLoading> map = new HashMap<>();

    @NonNull
    private Observable<String> enqueueCall(final Call<ResponseBody> call) {
        netLoading = new NetLoading(context);
        netLoading.show();
        map.put(call, netLoading);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                a(observableEmitter, call);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }


    @NonNull
    private Observable<String> enqueueCall2(final Call<ResponseBody> call) {
        netLoading = new NetLoading(context);
        netLoading.show();
        map.put(call, netLoading);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                b(observableEmitter, call);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }


    @NonNull
    private Observable<String> enqueueCall3(final Call<ResponseBody> call) {
        netLoading = new NetLoading(context);
        netLoading.show();
        map.put(call, netLoading);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                c(observableEmitter, call);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private void a(final ObservableEmitter<String> observableEmitter, final Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
                //  {"Errcode":1002,"Message":"用户名或密码错误！","Data":null}
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        if (body.contains("\"Errcode\":0")) {
                            observableEmitter.onNext(body);
                        } else {
                            try {
                                JSONObject jb = new JSONObject(body);
                                String message = jb.getString("Message");
                                int errcode = jb.getInt("Errcode");
                                //5008 楼盘未绑定经纪人 1009 尚未注册
                                if (errcode == 1009 || errcode == 5008) {
                                    observableEmitter.onNext(body);
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        String body = response.errorBody().string();

                        /**
                         * Errcode : 400
                         * Message : 参数错误
                         * Data : ["字段 Password 必须是一个字符串，其最小长度为 6，最大长度为 15。"]
                         */
                        ErrorBody errorBody = GsonUtil.fromJson(body, ErrorBody.class);
                        List<String> data = errorBody.getData();
                        if (data != null && data.size() > 0) {
                            Toast.makeText(context, data.get(0), Toast.LENGTH_SHORT).show();
                        } else {
                            if (!"已拒绝为此请求授权。".equals(errorBody.getMessage())) {
                                Toast.makeText(context, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(context, "网络异常,请重试", Toast.LENGTH_SHORT).show();
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
            }
        });
    }


    private void b(final ObservableEmitter<String> observableEmitter, final Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
                //  {"Errcode":1002,"Message":"用户名或密码错误！","Data":null}
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        observableEmitter.onNext(body);
                    } else {
                        String body = response.errorBody().string();
                        ErrorBody errorBody = GsonUtil.fromJson(body, ErrorBody.class);
                        List<String> data = errorBody.getData();
                        if (data != null && data.size() > 0) {
                            Toast.makeText(context, data.get(0), Toast.LENGTH_SHORT).show();
                        } else {
                            if (!"已拒绝为此请求授权。".equals(errorBody.getMessage())) {
                                Toast.makeText(context, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(context, "网络异常,请重试", Toast.LENGTH_SHORT).show();
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
            }
        });
    }


    private void c(final ObservableEmitter<String> observableEmitter, final Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
                //  {"Errcode":1002,"Message":"用户名或密码错误！","Data":null}
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        observableEmitter.onNext(body);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(context, "网络异常,请重试", Toast.LENGTH_SHORT).show();
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
            }
        });
    }

}
