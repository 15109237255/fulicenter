package com.example.angela.fulicenter.fregment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angela.fulicenter.FuLiCenterApplication;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.activity.MainActivity;
import com.example.angela.fulicenter.bean.User;
import com.example.angela.fulicenter.utlis.ImageLoader;
import com.example.angela.fulicenter.utlis.L;
import com.example.angela.fulicenter.utlis.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angela on 2016/10/24.
 */

public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG=PersonalCenterFragment.class.getSimpleName();
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    MainActivity mContext;
    User user=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        initOrderList();
    }



    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"user="+user);
        if (user==null){
            MFGT.gotoLogin(mContext);
        }else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }
    }
    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"user="+user);
        if (user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }
    }

    @OnClick({R.id.tv_center_settings,R.id.center_user_info})
    public void gotoSetting() {
        MFGT.gotoSetting(mContext);
    }
    private void initOrderList() {

    }

}
