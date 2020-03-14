package com.xx.yuefang;


import android.widget.Toast;

import com.xx.yuefang.bean.ErrorBody;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

       String a="{\n" +
               "\t\"Errcode\": 0,\n" +
               "\t\"Message\": \"success\",\n" +
               "\t\"Data\": {\n" +
               "\t\t\"ParentName\": \"陈小姐\",\n" +
               "\t\t\"UserName\": \"丽丽\",\n" +
               "\t\t\"Avatar\": \"User/2/b2588d73570445a09e99292f2a6d78d1.jpg\",\n" +
               "\t\t\"Rise\": 0,\n" +
               "\t\t\"IsRise\": false,\n" +
               "\t\t\"Fall\": 0,\n" +
               "\t\t\"IsFall\": false,\n" +
               "\t\t\"Content\": \"哦哦哦\",\n" +
               "\t\t\"CreationTime\": \"2019-12-12T09:45:54.1972673+08:00\",\n" +
               "\t\t\"GradeType\": \"\",\n" +
               "\t\t\"LeadSee\": 0,\n" +
               "\t\t\"Turnover\": 0,\n" +
               "\t\t\"IsComment\": false,\n" +
               "\t\t\"Id\": 598\n" +
               "\t}\n" +
               "}";

       try {
           JSONObject jsonObject = new JSONObject(a);
           JSONObject data = jsonObject.getJSONObject("Data");
           System.out.println(data.toString());
       }catch (Exception e){

       }


    }


}