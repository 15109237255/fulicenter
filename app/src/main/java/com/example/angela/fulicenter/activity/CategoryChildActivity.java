package com.example.angela.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.adapter.GoodsAdapter;
import com.example.angela.fulicenter.bean.NewGoodsBean;
import com.example.angela.fulicenter.net.NetDao;
import com.example.angela.fulicenter.net.OkHttpUtils;
import com.example.angela.fulicenter.utlis.CommonUtils;
import com.example.angela.fulicenter.utlis.ConvertUtils;
import com.example.angela.fulicenter.utlis.L;
import com.example.angela.fulicenter.utlis.MFGT;
import com.example.angela.fulicenter.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends BaseActivity {

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    CategoryChildActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    GridLayoutManager glm;
    int catId;
    @BindView(R.id.btn_sort_price)
    Button mBtnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button mBtnSortAddtime;

    boolean addTimeAsc=false;
    boolean priceAsc=false;
    int sortBy=I.SORT_BY_ADDTIME_DESC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext, mList);
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void setListener() {
        setPullUpListener();//上拉刷新
        setPullDownListener();//下拉刷新
    }


    /**
     * 上拉刷新
     */
    private void setPullUpListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadCategoryGoods(I.ACTION_PULL_UP);
            }
        });
    }

    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(mContext, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e("error: " + error);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void setPullDownListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 再次请求数据，请求下一页数据
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downloadCategoryGoods(I.ACTION_PULL_DOWN);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition == 0);
            }
        });
    }

    @Override
    protected void initData() {
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
    }


    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc){
                    sortBy=I.SORT_BY_PRICE_ASC;
                    right=getResources().getDrawable(R.mipmap.arrow_order_up);
                }else {
                    sortBy=I.SORT_BY_PRICE_DESC;
                    right=getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                mBtnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,right,null);
                priceAsc=!priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc){
                    sortBy=I.SORT_BY_ADDTIME_ASC;
                    right=getResources().getDrawable(R.mipmap.arrow_order_up);
                }else {
                    sortBy=I.SORT_BY_ADDTIME_DESC;
                    right=getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                mBtnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,right,null);
                addTimeAsc=!addTimeAsc;
                break;
        }
        mAdapter.setSoryBy(sortBy);
    }
}
