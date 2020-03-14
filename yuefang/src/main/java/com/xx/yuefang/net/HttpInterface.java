package com.xx.yuefang.net;


import com.xx.yuefang.bean.AddPareterBody;
import com.xx.yuefang.bean.AddSalerBody;
import com.xx.yuefang.bean.Appoint;
import com.xx.yuefang.bean.AppointSalepersonBean;
import com.xx.yuefang.bean.BackPsw;
import com.xx.yuefang.bean.ChangePsw;
import com.xx.yuefang.bean.CompanyRegisterBody;
import com.xx.yuefang.bean.EnterFor;
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
import com.xx.yuefang.ui.fragment.me.employee.AddSalerPerson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpInterface {

    @POST("/api/ManageApi/Authorization/RegisterUser")
    Call<ResponseBody> register(@Body RegisterBody body);

    @POST("/api/ManageApi/Authorization/UserPhoneNumberLogin")
    Call<ResponseBody> login(@Body LoginBody loginBody);

    @POST("/api/ManageApi/PremisesBaseApi/QueryPagesPremisesList")
    Call<ResponseBody> getPremisesList(@Body GetPremisesList getPremisesList);//获取楼盘列表

    @GET("/api/ManageApi/PremisesBaseApi/GetPremisesDetailById")
    Call<ResponseBody> getPremisesDetailById(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/GetPremisesMoreInfoById")
    Call<ResponseBody> getMoreHouseMessageById(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/GetPremisesById")
    Call<ResponseBody> getPremisesById(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/SetPremisesRise")
    Call<ResponseBody> rise(@Query("id") int id);//点赞

    @GET("/api/ManageApi/UserApi/GetUserById")
//根据Id查询用户
    Call<ResponseBody> getUserMsg(@Query("id") long id);

    @POST("/api/ManageApi/PremisesReservationApi/AddUserReservation")
    Call<ResponseBody> appoint(@Body Appoint appoint);

    @GET("/api/ManageApi/PremisesSalespersonApi/GetPremisesBySalespersonId")
    Call<ResponseBody> getSalepresonListById(@Query("id") int id);

    @GET("/api/ManageApi/EnumTypeApi/GetPickUpType")
    Call<ResponseBody> getPickUpType();

    @POST("/api/ManageApi/SMSAccessApi/SendCodeByMobile")
    Call<ResponseBody> getYzm(@Body HashMap<String, Object> map);

    @GET("/api/ManageApi/DeveloperApi/GetDeveloperReservationDaysById")
    Call<ResponseBody> getAppointDay(@Query("id") int id);

    @GET("/api/ManageApi/PremisesReservationApi/GetPremisesReservationByUserId")
    Call<ResponseBody> getFirstYuyue();

    @POST("/api/ManageApi/UserCollectionApi/QueryPagesUserCollectionByUserId")
    Call<ResponseBody> getCollectionList(@Body HashMap<String, Integer> map);


    @POST("/api/ManageApi/PremisesReservationApi/QueryPagesPremisesReservationByUserId")
    Call<ResponseBody> getAllApoint(@Body GetAllApoint getAllApoint);


    @GET("/api/ManageApi/DeveloperApi/GetDeveloperById")
    Call<ResponseBody> getDeveloperMsgById(@Query("id") long id);

    @GET("/api/ManageApi/SalespersonApi/GetSalespersonById")
    Call<ResponseBody> getSellerMsgById(@Query("id") long id);

    @GET("/api/ManageApi/EnumTypeApi/GetReservationType")
    Call<ResponseBody> getReservationType();

    @POST("/api/ManageApi/PremisesReservationApi/UpdatePremisesReservationState")
    Call<ResponseBody> appointOption(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PremisesSalespersonApi/QueryPagesPremisesListBySalespersonId")
    Call<ResponseBody> getHouseResourceListBySeller(@Body GetHouseResourcesList getHouseResourcesList);


    @POST("/api/ManageApi/DeveloperApi/QueryPagesPremisesListByDeveloperId")
    Call<ResponseBody> getHouseResourceListByDeveloper(@Body GetHouseResourcesList getHouseResourcesList);

    @POST("/api/ManageApi/PremisesBaseApi/SetPremisesIsActive")
    Call<ResponseBody> houseResourceOption(@Body HashMap<String, ArrayList<Integer>> map);

    @POST("/api/ManageApi/PremisesReservationApi/QueryPagesPremisesListByReservation")
    Call<ResponseBody> queryCommentList(@Body HashMap<String, Integer> map);

    @GET("/api/ManageApi/PremisesReservationApi/GetPremisesReservationCommentByReservationId")
    Call<ResponseBody> getCommentDetailById(@Query("id") int id);

    @POST("/api/ManageApi/SalespersonCommentApi/AddSalespersonComment")
    Call<ResponseBody> sellerComment(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserCommentApi/AddUserComment")
    Call<ResponseBody> userComment(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/QueryPagesSalesperson")
    Call<ResponseBody> getSellerList(@Body GetSellerList getSellerList);

    @POST("/api/ManageApi/PartnerApi/QueryPagesPartner")
    Call<ResponseBody> getParterList(@Body GetSellerList getSellerList);

    @DELETE("/api/ManageApi/SalespersonApi/DeleteSalespersonById")
    Call<ResponseBody> deleteSaleperson(@Query("id") int id);

    @DELETE("/api/ManageApi/PartnerApi/DeletePartnerById")
    Call<ResponseBody> deleteParter(@Query("id") int id);

    @POST("/api/ManageApi/SalespersonApi/DeleteSalespersonsByIds")
    Call<ResponseBody> deleteSalePersons(@Body HashMap<String, ArrayList<Integer>> map);

    @POST("/api/ManageApi/PartnerApi/DeletePartnersByIds")
    Call<ResponseBody> deleteParters(@Body HashMap<String, ArrayList<Integer>> map);

    @POST("/api/ManageApi/SalespersonApi/AddSalesperson")
    Call<ResponseBody> addSaler(@Body AddSalerBody addSalerBody);


    @POST("/api/ManageApi/PremisesReservationApi/DeletePremisesReservationById")
    Call<ResponseBody> deleteAppoint(@Body HashMap<String, ArrayList<Integer>> map);


    @GET("/api/ManageApi/PremisesReservationApi/GetPremisesReservationById")
    Call<ResponseBody> getReservationDetailById(@Query("id") int id);

    @GET("/api/ManageApi/UserCollectionApi/CollectOrCancelUserCollection")
    Call<ResponseBody> userCollection(@Query("id") int id);

    @POST("/api/ManageApi/UserBrowseApi/QueryPagesUserBrowse")
    Call<ResponseBody> getFootprint(@Body HashMap<String, Integer> map);

    @POST("/api/ManageApi/UserApi/UpdateUser")
    Call<ResponseBody> updateUser(@Body UpdateUser updateUser);


    @GET("/api/ManageApi/AdInfoApi/GetAllAdInfo")
    Call<ResponseBody> getAdList();


    @POST("/api/ManageApi/NewsInfoApi/QueryPagesNewsInfo")
    Call<ResponseBody> getNewsList(@Body GetNewsList getNewsList);


    @GET("/api/ManageApi/EnumTypeApi/GetNewsInfoType")
    Call<ResponseBody> getNewsType();


    @GET("/api/ManageApi/NewsInfoApi/GetNewsInfoById")
    Call<ResponseBody> getNewsDetailById(@Query("id") int id);


    @GET("/api/ManageApi/PremisesActivityApi/GetPremisesActivityById")
    Call<ResponseBody> getDynamicDetail(@Query("id") int id);


    @POST("/api/ManageApi/ActivityApplyApi/AddActivityApply")
    Call<ResponseBody> enterfor(@Body EnterFor enterFor);


    @POST("/api/ManageApi/UserBrowseApi/DeleteUserBrowseByIds")
    Call<ResponseBody> deleteBrowseRecords(@Body HashMap<String, ArrayList<Integer>> map);


    @POST("/api/ManageApi/UserCollectionApi/DeleteUserCollectionByIds")
    Call<ResponseBody> deleteCollectionRecords(@Body HashMap<String, ArrayList<Integer>> map);

    @POST("/api/ManageApi/PremisesCommentApi/AddPremisesComment")
    Call<ResponseBody> commentOn(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/QuestionsAnswersApi/AddQuestionsAnswers")
    Call<ResponseBody> ask(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/QuestionsAnswersApi/ReplyQuestionsAnswers")
    Call<ResponseBody> replay(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/QuestionsAnswersApi/QueryPagesQuestionsAnswers")
    Call<ResponseBody> getQaList(@Body HashMap<String, Object> map);


    @GET("/api/ManageApi/EnumTypeApi/GetPremisesSearchEnum")
    Call<ResponseBody> getSearchDatas();

    @Multipart
    @POST("/api/ManageApi/UserApi/UploadAvatar")
        //1产品图片 2 用户头像 3产品类别图片
    Call<ResponseBody> uploadPicture(@Query("imgurl") String imgurl, @Part MultipartBody.Part file);

    @POST("/api/ManageApi/Authorization/UserOpenIdRegister")
    Call<ResponseBody> opendIdRegister();

    @POST("/api/ManageApi/Authorization/SignOut")
    Call<ResponseBody> exit();

    @POST("/api/ManageApi/DeveloperApi/UpdateDeveloper")
    Call<ResponseBody> updateDeveloper(@Body UpdateDeveloper updateDeveloper);

    @POST("/api/ManageApi/SalespersonApi/UpdateSalesperson")
    Call<ResponseBody> updateSaler(@Body UpdateSaler updateSaler);


    @GET("/api/ManageApi/SystemSetupApi/GetSystemSetup")
    Call<ResponseBody> getSystemSetup();


    @POST("/api/ManageApi/DeveloperApi/GetSalespersonByDeveloperId")
    Call<ResponseBody> getAllSalePerson(@Body HashMap<String, Integer> map);


    @POST("/api/ManageApi/PremisesSalespersonApi/AddOrUpdatePremisesSalesperson")
    Call<ResponseBody> appointSalePerson(@Body AppointSalepersonBean appointSalepersonBean);


    @POST("/api/ManageApi/Authorization/ForgetPassword")
    Call<ResponseBody> backPsw(@Body BackPsw backPsw);

    @GET("/api/ManageApi/SystemNewsApi/QueryPagesSystemNews")
    Call<ResponseBody> getSystemNews();

    @GET("/api/ManageApi/PremisesCommentApi/GetPremisesCommentById")
    Call<ResponseBody> qyerryCommentByid(@Query("id") int id);

    @GET("/api/ManageApi/QuestionsAnswersApi/GetQuestionsAnswersById")
    Call<ResponseBody> querryWdById(@Query("id") int id);

    @POST("/api/ManageApi/PremisesCommentApi/ReplyPremisesComment")
    Call<ResponseBody> replayComment(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PremisesCommentApi/QueryPagesPremisesCommentByPremisesId")
    Call<ResponseBody> getCommentList(@Body HashMap<String, Object> map);

    @GET("/api/ManageApi/SystemNewsApi/AddSystemNewsLog")
    Call<ResponseBody> isReadSystemNews();

    @POST("/api/ManageApi/PremisesCommentApi/SetPremisesCommentRiseOrFall")
    Call<ResponseBody> commentRise(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserApi/UpdateUserPassword")
    Call<ResponseBody> changeUserPsw(@Body ChangePsw changePsw);

    @POST("/api/ManageApi/CompanyApi/UpdateCompanyPassword")
    Call<ResponseBody> changeCompanyPsw(@Body ChangePsw changePsw);

    @POST("/api/ManageApi/PartnerApi/UpdatePartnerPassword")
    Call<ResponseBody> changeParterPsw(@Body ChangePsw changePsw);

    @POST("/api/ManageApi/SalespersonApi/UpdateSalespersonPassword")
    Call<ResponseBody> changeSalepersonPsw(@Body ChangePsw changePsw);

    @POST("/api/ManageApi/DeveloperApi/UpdateDeveloperPassword")
    Call<ResponseBody> changeDeveloperPsw(@Body ChangePsw changePsw);

    @POST("/api/ManageApi/Authorization/UserOpenIdLogin")
    Call<ResponseBody> thridLogin(@Body HashMap<String, Object> map);


    @GET("/api/ManageApi/PremisesReservationApi/IsReservation")
    Call<ResponseBody> isReservation(@Query("id") int id);

    @POST("/api/ManageApi/Authorization/UserOpenIdRegister")
    Call<ResponseBody> thridRegister(@Body RegisterBody thridRegister);


    @POST("/api/ManageApi/PremisesBaseApi/QueryPagesPremisesById")
    Call<ResponseBody> querryPremisesList(@Body HashMap<String, Integer> map);


    @GET("/android.json")
    Call<ResponseBody> checkVersion();


    @POST("/api/ManageApi/UserApi/UserBindThirdParty")
    Call<ResponseBody> bindThird(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserApi/UserUnBindThirdParty")
    Call<ResponseBody> unBindThird(@Body HashMap<String, Integer> map);

    @GET("/sns/oauth2/access_token")
    Call<ResponseBody> wxdl(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String authorization_code);

    @GET("/sns/userinfo")
    Call<ResponseBody> getwxUserMsg(@Query("access_token") String accessToken, @Query("openid") String openId);


    @POST("/api/ManageApi/UserApi/UserIsWeChatSubscript")
    Call<ResponseBody> isConcernWxgzh();//判断是否关注了微信公众号


    @GET("/api/ManageApi/UserApi/ExistUserPassword")
    Call<ResponseBody> isPwdExit();

    @POST("/api/ManageApi/MyBrokerApi/QueryPagesMyBroker")
    Call<ResponseBody> querryMyAgent(@Body HashMap<String, Integer> map);


    @POST("/api/ManageApi/MyFansApi/QueryPagesMyFans")
    Call<ResponseBody> querryMyFans(@Body HashMap<String, Integer> map);


    @GET("/api/ManageApi/PremisesReservationApi/GetFansPremisesReservationByUserId")
    Call<ResponseBody> getFansDetail(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/GetTotalIndexById")
    Call<ResponseBody> getTotalById(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/GetPremisesDetailByLeftRightId")
    Call<ResponseBody> getPremisesDetailLeftOrRight(@Query("isLeft") boolean isLeft, @Query("id") int id);

    @POST("/api/ManageApi/MyBrokerApi/DeleteMyBrokerById")
    Call<ResponseBody> deleteAgent(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/MyBrokerApi/DelMyBrokerBySId")
    Call<ResponseBody> deleteAgentofPart(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/MyBrokerApi/GetMyBrokerBySalespersonId")
    Call<ResponseBody> getAgentMsg(@Body HashMap<String, Integer> map);

    @POST("/api/ManageApi/MyBrokerApi/AddMyBroker")
    Call<ResponseBody> addAgent(@Body HashMap<String, Integer> map);

    @GET("/api/ManageApi/PremisesBaseApi/GetPosterContentById")
    Call<ResponseBody> getPosterDetail(@Query("id") int id);

    @GET("/api/ManageApi/PremisesBaseApi/GetChatPremisesById")
    Call<ResponseBody> getPremisesMsg(@Query("Id") int id);


    @GET("/api/ManageApi/SMSAccessApi/WriteLog")
    Call<ResponseBody> writeLog(@Query("input") String s);

    @GET("/api/ManageApi/MyBrokerApi/GetSalespersonIdByPremisesId")
    Call<ResponseBody> getAgent(@Query("id") int id);

    @POST("/api/ManageApi/NewsInfoCommentApi/QueryPagesNewsInfoCommentByNewsInfoId")
    Call<ResponseBody> getNewsCommentList(@Body HashMap<String, Object> map);

    @GET("/api/ManageApi/NewsInfoCommentApi/GetNewsInfoCommentById")
    Call<ResponseBody> getNewsCommentDetailById(@Query("id") int id);

    @POST("/api/ManageApi/NewsInfoCommentApi/AddNewsInfoComment")
    Call<ResponseBody> addNewsComment(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/NewsInfoCommentApi/ReplyNewsInfoComment")
    Call<ResponseBody> replyNewsComment(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/NewsInfoCommentApi/SetNewsInfoCommentRise")
    Call<ResponseBody> riseNewsComment(@Body HashMap<String, Integer> map);


    @GET("/api/ManageApi/PremisesBaseApi/GetALLId")
    Call<ResponseBody> getAllPremisesId();


    @GET("/api/ManageApi/DeveloperApi/GetPremisesNum")
    Call<ResponseBody> getPremisesNum(@Query("premisesName") String name);


    @GET("/api/ManageApi/PremisesCommentApi/GetPremisesCommentByReplyId")
    Call<ResponseBody> getPremisesCommentByReplyId(@Query("id") int id);

    @POST("/api/ManageApi/PremisesReservationApi/GetReservationNum")
    Call<ResponseBody> getReservationNum(@Body GetReservationBody getReservationBody);

    @GET("/api/ManageApi/NewsInfoCommentApi/GetNewsInfoCommentByReplyId")
    Call<ResponseBody> getNewsInfoCommentByReplyId(@Query("id") int id);


    @GET("/api/ManageApi/PremisesBaseApi/GetPremisesNameByDId")
    Call<ResponseBody> getPremiseMsgByDdeveloperId();


    @POST("/api/ManageApi/PartnerApi/RegisterPartner")
    Call<ResponseBody> registerPartner(@Body ParterRegisterBody registerBody);

    @POST("/api/ManageApi/CompanyApi/RegisterCompany")
    Call<ResponseBody> registerCompany(@Body CompanyRegisterBody companyRegisterBody);

    @GET("/api/ManageApi/PartnerApi/GetPartnerById")
    Call<ResponseBody> getParterById();

    @GET("/api/ManageApi/CompanyApi/GetCompanyById")
    Call<ResponseBody> getCompanyById();

    @POST("/api/ManageApi/PremisesReservationApi/QueryPagesPremisesReservationByUserId")
    Call<ResponseBody> querryReservation(@Body QuerryReservationBody body);


    @POST("/api/ManageApi/ExamineInfoApi/GetExamineInfoNum")
    Call<ResponseBody> getReviewNum(@Body HashMap<String, int[]> map);

    @POST("/api/ManageApi/ExamineInfoApi/UpdateExamineState")
    Call<ResponseBody> updateRevieweState(@Body UpdateReviewBody body);

    @GET("/api/ManageApi/ExamineInfoApi/GetExamineDetailsById")
    Call<ResponseBody> getReviewDetailById(@Query("Id") int id);

    @POST("/api/ManageApi/ExamineInfoApi/QueryPagesExamineInfo")
    Call<ResponseBody> querryReviews(@Body QuerryReviewBody body);

    @GET("/api/ManageApi/MyBrokerApi/GetMyBrokerByPremisesId")
    Call<ResponseBody> getAgentByPremisesId(@Query("id") int id);

    @POST("/api/ManageApi/PartnerApi/BindingCompany")
    Call<ResponseBody> bindCompany(@Body HashMap<String, Integer> map);

    @POST("/api/ManageApi/PartnerApi/UntyingCompany")
    Call<ResponseBody> unBindCompany(@Body HashMap<String, Integer> map);

    @GET("/api/ManageApi/ExamineInfoApi/GetLastExamineByPartnerId")
    Call<ResponseBody> isBindCompany();


    @POST("/api/ManageApi/CompanyApi/UpdateCompany")
    Call<ResponseBody> updateCompany(@Body UpdateCompany updateCompany);

    @POST("/api/ManageApi/PartnerApi/UpdatePartner")
    Call<ResponseBody> updateParter(@Body UpdateParter updateParter);

    @POST("/api/ManageApi/PartnerApi/AddPartner")
    Call<ResponseBody> addParter(@Body AddPareterBody addPareterBody);


    @POST("/api/ManageApi/PartnerApi/UpdateNickName")
    Call<ResponseBody> updateParterName(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateSex")
    Call<ResponseBody> updateParterSex(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateMobile")
    Call<ResponseBody> updateParterPhone(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateEmail")
    Call<ResponseBody> updateParterWx(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateAddress")
    Call<ResponseBody> updateParterAddress(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateProfile")
    Call<ResponseBody> updateParterProfile(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateAvatar")
    Call<ResponseBody> updateParterAvater(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/PartnerApi/UpdateTelephone")
    Call<ResponseBody> updateParterTel(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/SalespersonApi/UpdateNickName")
    Call<ResponseBody> updateSalePersonName(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/UpdateTelephone")
    Call<ResponseBody> updateSalePersonTel(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/SalespersonApi/UpdateSex")
    Call<ResponseBody> updateSalePersonSex(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/SalespersonApi/UpdateMobile")
    Call<ResponseBody> updateSalePersonPhone(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/UpdateEmail")
    Call<ResponseBody> updateSalePersonWx(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/UpdateAddress")
    Call<ResponseBody> updateSalePersonAddress(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/UpdateProfile")
    Call<ResponseBody> updateSalePersonProfile(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/SalespersonApi/UpdateAvatar")
    Call<ResponseBody> updateSalePersonAvater(@Body HashMap<String, Object> map);


    @POST("/api/ManageApi/UserApi/UpdateAvatar")
    Call<ResponseBody> updateUserAvater(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserApi/UpdateNickName")
    Call<ResponseBody> updateUserName(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserApi/UpdateMobile")
    Call<ResponseBody> updateUserPhone(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/UserApi/UpdateSex")
    Call<ResponseBody> updateUserSex(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/CompanyApi/UpdateNickName")
    Call<ResponseBody> updateCompanyName(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/CompanyApi/UpdateAvatar")
    Call<ResponseBody> updateCompanyAvatar(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/CompanyApi/UpdateMobile")
    Call<ResponseBody> updateCompanyPhone(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/CompanyApi/UpdateIntroduce")
    Call<ResponseBody> updateCompanyIntroduce(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/DeveloperApi/UpdateNickName")
    Call<ResponseBody> updateDeveloperName(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/DeveloperApi/UpdateAvatar")
    Call<ResponseBody> updateDeveloperAvatar(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/DeveloperApi/UpdateMobile")
    Call<ResponseBody> updateDeveloperPhone(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/DeveloperApi/UpdateReservationDays")
    Call<ResponseBody> updateDeveloperReservationDays(@Body HashMap<String, Object> map);

    @POST("/api/ManageApi/DeveloperApi/UpdateIntroduce")
    Call<ResponseBody> updateDeveloperIntroduce(@Body HashMap<String, Object> map);

}