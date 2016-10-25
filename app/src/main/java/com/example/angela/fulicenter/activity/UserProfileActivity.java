package com.example.angela.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angela.fulicenter.FuLiCenterApplication;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.User;
import com.example.angela.fulicenter.dao.SharePrefrenceUtils;
import com.example.angela.fulicenter.utlis.CommonUtils;
import com.example.angela.fulicenter.utlis.ImageLoader;
import com.example.angela.fulicenter.utlis.MFGT;
import com.example.angela.fulicenter.view.DisplayUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends BaseActivity {
    //mtvUserAvatarImage
    @BindView(R.id.tv_user_avatar_image)
    ImageView mtvUserAvatarImage;
    @BindView(R.id.tv_user_username)
    TextView mtvUserUsername;
    @BindView(R.id.tv_user_usernick)
    TextView mtvUserUsernick;
    UserProfileActivity mContext;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.user_profile));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            finish();
            return;
        }
        showInfo();
    }

    @Override
    protected void setListener() {

    }


    private void logouet() {
        if (user != null) {
            SharePrefrenceUtils.getInstance(mContext).removeUser();
            FuLiCenterApplication.setUser(null);
            MFGT.gotoLogin(mContext);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    private void showInfo() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mtvUserAvatarImage);
            mtvUserUsername.setText(user.getMuserName());
            mtvUserUsernick.setText(user.getMuserNick());
        }
    }

    @OnClick({R.id.backClickArea, R.id.rl_personal_center_avatar, R.id.rl_personal_center_username, R.id.rl_personal_center_usernick, R.id.bt_back_personal_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(this);
                break;
            case R.id.rl_personal_center_avatar:
                break;
            case R.id.rl_personal_center_username:
                CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
                break;
            case R.id.rl_personal_center_usernick:
                MFGT.gotoUpdateNick(mContext);
                break;
            case R.id.bt_back_personal_center:
                logouet();
                break;
        }
    }
}
