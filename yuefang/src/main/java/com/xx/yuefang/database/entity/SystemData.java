package com.xx.yuefang.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SystemData {
    @Id
    private Long id;
    private String HotKeywords;
    private String UserQuickChat;
    private String SalespersonQuickChat;
    @Generated(hash = 616869047)
    public SystemData(Long id, String HotKeywords, String UserQuickChat,
            String SalespersonQuickChat) {
        this.id = id;
        this.HotKeywords = HotKeywords;
        this.UserQuickChat = UserQuickChat;
        this.SalespersonQuickChat = SalespersonQuickChat;
    }
    @Generated(hash = 965238204)
    public SystemData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHotKeywords() {
        return this.HotKeywords;
    }
    public void setHotKeywords(String HotKeywords) {
        this.HotKeywords = HotKeywords;
    }
    public String getUserQuickChat() {
        return this.UserQuickChat;
    }
    public void setUserQuickChat(String UserQuickChat) {
        this.UserQuickChat = UserQuickChat;
    }
    public String getSalespersonQuickChat() {
        return this.SalespersonQuickChat;
    }
    public void setSalespersonQuickChat(String SalespersonQuickChat) {
        this.SalespersonQuickChat = SalespersonQuickChat;
    }



}
