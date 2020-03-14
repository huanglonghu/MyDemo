package com.xx.yuefang.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xx.yuefang.bean.WxUerMsg;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.ThridPartLogin;

import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public int WX_LOGIN = 1;
    private IWXAPI iwxapi;
    private SendAuth.Resp resp;
    private String APP_ID = "wx4685b2feaa7ac93a";
    private String APP_Secret = "2f2325e8efc2fa72a156a86049831a74";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        iwxapi.handleIntent(this.getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.log("=========baseResp=========");
        if (baseResp.getType() == WX_LOGIN) {
            resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    HttpUtil.getInstance().wxdl(code).subscribe(
                            srt -> {
                                JSONObject jo = new JSONObject(srt);
                                String access_token = jo.getString("access_token");
                                String openid = jo.getString("openid");
                                if (resp.state.equals("login")) {
                                    HttpUtil.getInstance().getwxUserMsg(access_token, openid).subscribe(
                                            str -> {
                                                try {
                                                    WxUerMsg wxUerMsg = GsonUtil.fromJson(srt, WxUerMsg.class);
                                                    ThridPartLogin.getInstance().requestThirdLogin(1, openid, wxUerMsg.getNickname(), wxUerMsg.getSex() == 1 ? "男" : "女", wxUerMsg.getHeadimgurl());
                                                } catch (Exception e) {
                                                    LogUtil.log("==========ddfdsds============" + e.toString());
                                                }
                                            }
                                    );
                                } else {
                                    ThridPartLogin.getInstance().getClickSureListener().openIdCallBack(1, openid);
                                }


                            }
                    );
                    //获取用户信息
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    Toast.makeText(WXEntryActivity.this, "用户取消登录", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
        finish();

    }

}