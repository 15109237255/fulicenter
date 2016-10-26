package com.example.angela.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.angela.fulicenter.FuLiCenterApplication;
import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.CollectBean;
import com.example.angela.fulicenter.bean.MessageBean;
import com.example.angela.fulicenter.net.NetDao;
import com.example.angela.fulicenter.net.OkHttpUtils;
import com.example.angela.fulicenter.utlis.CommonUtils;
import com.example.angela.fulicenter.utlis.ImageLoader;
import com.example.angela.fulicenter.utlis.L;
import com.example.angela.fulicenter.utlis.MFGT;
import com.example.angela.fulicenter.view.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angela on 2016/10/17.
 */

/**
 * 新品首页
 */

public class CollectsAdapter extends Adapter {
    List<CollectBean> mList;
    Context mContext;
    int soryBy=I.SORT_BY_ADDTIME_DESC;

    boolean isMore;//默认为true


    public CollectsAdapter(Context context, List<CollectBean> list) {
        this.mContext=context;
        mList = new ArrayList<>();
        mList.addAll(list);

    }
    public void setSoryBy(int soryBy){
        this.soryBy=soryBy;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
             holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder vh= (FooterViewHolder) holder;
            vh.mTvFooter.setText(getFooterStrint());

        }else {
            CollectsViewHolder vh= (CollectsViewHolder) holder;
            CollectBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext,vh.mIvGoodsThumb,goods.getGoodsThumb());
            vh.mTvGoodsName.setText(goods.getGoodsName());
           vh.mLayoutGoods.setTag(goods);
        }
    }

    private int getFooterStrint() {

        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CollectsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout mLayoutGoods;

        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick(){
            CollectBean goods = (CollectBean) mLayoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext,goods.getGoodsId());
        }
        @OnClick(R.id.iv_collect_del)
        public void deleteCollect(){
            final CollectBean goods = (CollectBean) mLayoutGoods.getTag();
            String username=FuLiCenterApplication.getUser().getMuserName();
            NetDao.deleteCollect(mContext, username, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result!=null&&result.isSuccess()){
                        mList.remove(goods);
                        notifyDataSetChanged();
                    }else {
                        CommonUtils.showLongToast(result!=null?result.getMsg():mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                }

                @Override
                public void onError(String error) {
                    L.e("error"+error);
                    CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));

                }
            });
        }
    }


}
