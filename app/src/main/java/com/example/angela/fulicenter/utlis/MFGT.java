package com.example.angela.fulicenter.utlis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.activity.BoutiqueChildActivity;
import com.example.angela.fulicenter.activity.CategoryChildActivity;
import com.example.angela.fulicenter.activity.GoodsDetailActivity;
import com.example.angela.fulicenter.activity.LoginActivity;
import com.example.angela.fulicenter.activity.MainActivity;
import com.example.angela.fulicenter.activity.RegisterActivity;
import com.example.angela.fulicenter.bean.BoutiqueBean;
import com.example.angela.fulicenter.bean.CategoryChildBean;

import java.util.ArrayList;

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }
    public static void gotoGoodsDetailsActivity(Context context, int goodId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodId);
        startActivity(context,intent);
    }
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }

    /**
     * 跳转到登录页面
     * @param context
     */
    public static void gotoLogin(Activity context){
        startActivity(context, LoginActivity.class);
    }

    /**
     * 跳转到注册页面
     * @param context
     */
    public static void gotoRegister(Activity context){
        Intent intent=new Intent();
        intent.setClass(context,RegisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
    }
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }


}
