package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.ui.activity.MembershipCredentialsActivity;

import org.raphets.roundimageview.RoundImageView;


@ActivityScope
public class MembershipCredentialsPresenter extends BasePresenter<MembershipCredentialsContract.Model, MembershipCredentialsContract.View>
    implements MembershipCredentialsContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MembershipCredentialsActivity mActivity;

    @Inject
    public MembershipCredentialsPresenter(MembershipCredentialsContract.Model model, MembershipCredentialsContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void setActivity(MembershipCredentialsActivity activity) {
        mActivity = activity;
    }

    @Override
    public void initHeader(PartyMemberEntity partyMemberEntity, RoundImageView ivHeader,
                           TextView tvName, TextView tvDuty, TextView tvPartyBranch) {
        tvName.setText("姓名: " + partyMemberEntity.getName());
        tvDuty.setText("职务: " + partyMemberEntity.getDuty());
        tvPartyBranch.setText("所属支部: " + partyMemberEntity.getParty_branch());

        mImageLoader.loadImage(mActivity,
                ImageConfigImpl
                        .builder()
                        .errorPic(R.mipmap.ic_error)
                        .placeholder(R.mipmap.ic_placeholder)
                        .url(partyMemberEntity.getUrl())
                        .imageView(ivHeader)
                        .build());
    }
}
