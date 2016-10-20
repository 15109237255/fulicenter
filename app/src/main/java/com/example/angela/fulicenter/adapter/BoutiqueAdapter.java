package com.example.angela.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.BoutiqueBean;
import com.example.angela.fulicenter.utlis.ImageLoader;
import com.example.angela.fulicenter.utlis.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angela on 2016/10/19.
 */

/**
 * 精选首页
 */
public class BoutiqueAdapter extends Adapter<BoutiqueAdapter.BoutiqueViewHodler> {

    Context mContext;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public BoutiqueViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHodler holder = new BoutiqueViewHodler(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        return holder;
    }
    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BoutiqueViewHodler holder, int position) {
        BoutiqueBean boutiqueBean = mList.get(position);
        holder.mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
        holder.mTvBoutiqueName.setText(boutiqueBean.getName());
        holder.mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
        ImageLoader.downloadImg(mContext,holder.mIvBoutiqueImg,boutiqueBean.getImageurl());
        holder.mLayoutBoutiqueItem.setTag(boutiqueBean);

    }



    @Override
    public int getItemCount() {
        return mList != null ? mList.size(): 0;
    }



    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class BoutiqueViewHodler extends ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        //精选页面的二级跳转方法
        @OnClick(R.id.layout_boutique_item)
        public void onBoutiqueClick(){
            BoutiqueBean bean = (BoutiqueBean) mLayoutBoutiqueItem.getTag();
            MFGT.gotoBoutiqueChildActivity(mContext,bean);
        }
    }
}
