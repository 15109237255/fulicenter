package com.example.angela.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angela.fulicenter.FuLiCenterApplication;
import com.example.angela.fulicenter.I;
import com.example.angela.fulicenter.R;
import com.example.angela.fulicenter.bean.Result;
import com.example.angela.fulicenter.bean.User;
import com.example.angela.fulicenter.dao.SharePrefrenceUtils;
import com.example.angela.fulicenter.net.NetDao;
import com.example.angela.fulicenter.net.OkHttpUtils;
import com.example.angela.fulicenter.utlis.CommonUtils;
import com.example.angela.fulicenter.utlis.ImageLoader;
import com.example.angela.fulicenter.utlis.L;
import com.example.angela.fulicenter.utlis.MFGT;
import com.example.angela.fulicenter.utlis.OnSetAvatarListener;
import com.example.angela.fulicenter.utlis.ResultUtils;
import com.example.angela.fulicenter.view.DisplayUtils;

import java.io.File;

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
    User user=null;
    OnSetAvatarListener mOnSetAvatarListener;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onActivityResult,requestCode="+requestCode+",resultCode"+resultCode);
        if (resultCode!=RESULT_OK){
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,mtvUserAvatarImage);
        if (requestCode==I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }

        if (requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }
    }
//file=/storage/emulated/0/Android/data/com.example.angela.fulicenter/files/Pictures
// /storage/emulated/0/Android/data/com.example.angela.fulicenter/files/Pictures/user_avatar/a123456.jpg
    private void updateAvatar() {
        File file=new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath()+"/"+user.getMuserName()
                +I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        final ProgressDialog pd=new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(mContext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s"+s);
                Result result= ResultUtils.getResultFromJson(s,User.class);
                L.e("result="+result);
                if (result==null){
                    CommonUtils.showLongToast(R.string.update_user_avatar_file);
                }else {
                    User  u= (User) result.getRetData();
                    if (result.isRetMsg()){
                        FuLiCenterApplication.setUser(u );
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u),mContext,mtvUserAvatarImage);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    }else {
                        CommonUtils.showLongToast(R.string.update_user_avatar_file);

                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(R.string.update_user_avatar_file);
                L.e("error="+error);
            }
        });
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
                mOnSetAvatarListener=new OnSetAvatarListener(mContext,R.id.layout_upload_avatar,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.rl_personal_center_username:
                CommonUtils.showLongToast(R.string.register_fail_unupdate);
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
