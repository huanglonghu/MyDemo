package com.xx.yuefang.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id
    private Long id;
    private String PhoneNumber;
    private String NickName;
    private String Avatar;
    private int Credit;
    private double Score;
    private int userType;
    private String CommanyName;
    private int RoleId;
    private String RoleName;
    private String Introduce;
    private String BusinessCardName;
    private String Email;
    private String Address;
    private String sex;
    private int reservationDays;
    private int developerId;
    private String token;
    private boolean IsbindQQ;
    private boolean IsbindWeChat;
    private boolean IsbindFacebook;
    private String Profile;
    private int CompanyId;
    private String IDCardPositive;
    private String IDCardReverseSide;
    private String BusinessLicense;
    private String Telephone;
    @Generated(hash = 1140722584)
    public UserBean(Long id, String PhoneNumber, String NickName, String Avatar,
            int Credit, double Score, int userType, String CommanyName, int RoleId,
            String RoleName, String Introduce, String BusinessCardName,
            String Email, String Address, String sex, int reservationDays,
            int developerId, String token, boolean IsbindQQ, boolean IsbindWeChat,
            boolean IsbindFacebook, String Profile, int CompanyId,
            String IDCardPositive, String IDCardReverseSide, String BusinessLicense,
            String Telephone) {
        this.id = id;
        this.PhoneNumber = PhoneNumber;
        this.NickName = NickName;
        this.Avatar = Avatar;
        this.Credit = Credit;
        this.Score = Score;
        this.userType = userType;
        this.CommanyName = CommanyName;
        this.RoleId = RoleId;
        this.RoleName = RoleName;
        this.Introduce = Introduce;
        this.BusinessCardName = BusinessCardName;
        this.Email = Email;
        this.Address = Address;
        this.sex = sex;
        this.reservationDays = reservationDays;
        this.developerId = developerId;
        this.token = token;
        this.IsbindQQ = IsbindQQ;
        this.IsbindWeChat = IsbindWeChat;
        this.IsbindFacebook = IsbindFacebook;
        this.Profile = Profile;
        this.CompanyId = CompanyId;
        this.IDCardPositive = IDCardPositive;
        this.IDCardReverseSide = IDCardReverseSide;
        this.BusinessLicense = BusinessLicense;
        this.Telephone = Telephone;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return this.PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    public String getNickName() {
        return this.NickName;
    }
    public void setNickName(String NickName) {
        this.NickName = NickName;
    }
    public String getAvatar() {
        return this.Avatar;
    }
    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    public int getCredit() {
        return this.Credit;
    }
    public void setCredit(int Credit) {
        this.Credit = Credit;
    }
    public double getScore() {
        return this.Score;
    }
    public void setScore(double Score) {
        this.Score = Score;
    }
    public int getUserType() {
        return this.userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }
    public String getCommanyName() {
        return this.CommanyName;
    }
    public void setCommanyName(String CommanyName) {
        this.CommanyName = CommanyName;
    }
    public int getRoleId() {
        return this.RoleId;
    }
    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }
    public String getRoleName() {
        return this.RoleName;
    }
    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }
    public String getIntroduce() {
        return this.Introduce;
    }
    public void setIntroduce(String Introduce) {
        this.Introduce = Introduce;
    }
    public String getBusinessCardName() {
        return this.BusinessCardName;
    }
    public void setBusinessCardName(String BusinessCardName) {
        this.BusinessCardName = BusinessCardName;
    }
    public String getEmail() {
        return this.Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getAddress() {
        return this.Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getReservationDays() {
        return this.reservationDays;
    }
    public void setReservationDays(int reservationDays) {
        this.reservationDays = reservationDays;
    }
    public int getDeveloperId() {
        return this.developerId;
    }
    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean getIsbindQQ() {
        return this.IsbindQQ;
    }
    public void setIsbindQQ(boolean IsbindQQ) {
        this.IsbindQQ = IsbindQQ;
    }
    public boolean getIsbindWeChat() {
        return this.IsbindWeChat;
    }
    public void setIsbindWeChat(boolean IsbindWeChat) {
        this.IsbindWeChat = IsbindWeChat;
    }
    public boolean getIsbindFacebook() {
        return this.IsbindFacebook;
    }
    public void setIsbindFacebook(boolean IsbindFacebook) {
        this.IsbindFacebook = IsbindFacebook;
    }
    public String getProfile() {
        return this.Profile;
    }
    public void setProfile(String Profile) {
        this.Profile = Profile;
    }
    public int getCompanyId() {
        return this.CompanyId;
    }
    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }
    public String getIDCardPositive() {
        return this.IDCardPositive;
    }
    public void setIDCardPositive(String IDCardPositive) {
        this.IDCardPositive = IDCardPositive;
    }
    public String getIDCardReverseSide() {
        return this.IDCardReverseSide;
    }
    public void setIDCardReverseSide(String IDCardReverseSide) {
        this.IDCardReverseSide = IDCardReverseSide;
    }
    public String getBusinessLicense() {
        return this.BusinessLicense;
    }
    public void setBusinessLicense(String BusinessLicense) {
        this.BusinessLicense = BusinessLicense;
    }
    public String getTelephone() {
        return this.Telephone;
    }
    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    
 
}
