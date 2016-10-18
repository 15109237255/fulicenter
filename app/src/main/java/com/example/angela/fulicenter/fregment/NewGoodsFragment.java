package com.example.angela.fulicenter.fregment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.activity.MainActivity;
import com.example.angela.fulicenter.adapter.GoodsAdapter;
import com.example.angela.fulicenter.bean.NewGoodsBean;
import com.example.angela.fulicenter.net.NetDao;
import com.example.angela.fulicenter.net.OkHttpUtils;
import com.example.angela.fulicenter.utlis.CommonUtils;
import com.example.angela.fulicenter.utlis.ConvertUtils;
import com.example.angela.fulicenter.utlis.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by xiaomiao on 2016/10/17.
 */

public class NewGoodsFragment extends Fragment {

    @BindView(R.id.tv_refresh)
    TextView mtvRfresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;

    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId=1;
    GridLayoutManager glm;



    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext,mList);
        initView();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullUpListener();//上拉刷新
        setPullDownListener();//下拉刷新
    }

    /**
     * 上拉刷新
     */
    private void   setPullUpListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setRefreshing(true);
                mtvRfresh.setVisibility(View.VISIBLE);
                pageId=1;
                downloadNewGoods(I.ACTION_PULL_UP);
            }
        });

    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                msrl.setRefreshing(false);
                mtvRfresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                        mAdapter.addData(list);
                    }
                    mAdapter.initData(list);
                    if (list.size()<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }
                }else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                msrl.setRefreshing(false);
                mtvRfresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e("error: "+error);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void setPullDownListener() {
        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 再次请求数据，请求下一页数据
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE
                        &&lastPosition==mAdapter.getItemCount()-1
                        &&mAdapter.isMore()){
                    pageId++;
                    downloadNewGoods(I.ACTION_PULL_DOWN);

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int firstPosition = glm.findFirstVisibleItemPosition();
//                msrl.setEnabled(firstPosition==0);
            }
        });
    }


    private void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    private void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
    }

}
