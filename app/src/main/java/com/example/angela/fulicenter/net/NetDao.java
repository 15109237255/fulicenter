package com.example.angela.fulicenter.net;

import android.content.Context;

import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.bean.BoutiqueBean;
import com.example.angela.fulicenter.bean.CategoryChildBean;
import com.example.angela.fulicenter.bean.CategoryGroupBean;
import com.example.angela.fulicenter.bean.GoodsDetailsBean;
import com.example.angela.fulicenter.bean.NewGoodsBean;
import com.example.angela.fulicenter.bean.Result;
import com.example.angela.fulicenter.utlis.MD5;

/**
 * Created by Angela on 2016/10/17.
 */

public class NetDao {
    /**
     * 下载新品页面请求
     * @param context
     * @param catId
     * @param pageId
     * @param listener
     */
    public  static void downloadNewGoods(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,pageId+"")

                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

}

    /**
     * 商品详情请求
     * @param context
     * @param goodsId
     * @param listener
     */
    public static void downloadGoodsDetail(Context context,int goodsId,OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils<GoodsDetailsBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    /**
     * 下载精选页面的请求
     * @param context
     * @param listener
     */
    public static void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    /**
     * 下载大类的请求
     * @param context
     * @param listener
     */
    public static void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils<CategoryGroupBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    /**
     * 下载小类的请求
     * @param context
     * @param parentId
     * @param listener
     */
    public static void downloadCategoryChild(Context context,int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils<CategoryChildBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    /**
     * 分类页面的分类列表
     * @param context
     * @param catId
     * @param pageId
     * @param listener
     */
    public  static void downloadCategoryGoods(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    /**
     * 注册页面
     * @param context
     * @param username
     * @param nickname
     * @param password
     * @param listener
     */
    public static void register(Context context, String username, String nickname, String password, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nickname)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }
}
