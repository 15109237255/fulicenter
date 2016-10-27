package com.example.angela.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.CartBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class CardAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;

    public CardAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new CardViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        CardViewHolder ch = (CardViewHolder) holder;
//        CartBean mCartBean = mList.get(position);
//        GoodsDetailsBean goods = mCartBean.getGoods();
//        if(goods!=null) {
//            ImageLoader.downloadImg(mContext,ch.ivCardThumb,goods.getGoodsThumb());
//            ch.ivCardGoodName.setText(goods.getGoodsName());
//            ch.tvCartPrice.setText(goods.getCurrencyPrice());
//        }
//        ch.tvCardGoodsCount.setText("("+mCartBean.getCount()+")");
//        ch.cbCartChecked.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }


    static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox cbCartChecked;
        @BindView(R.id.iv_cart_thumb)
        ImageView ivCardThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView ivCardGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView ivCardAdd;
        @BindView(R.id.tv_cart_count)
        TextView tvCardGoodsCount;
        @BindView(R.id.iv_cart_del)
        ImageView ivCartDel;
        @BindView(R.id.tv_cart_price)
        TextView tvCartPrice;


        CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
