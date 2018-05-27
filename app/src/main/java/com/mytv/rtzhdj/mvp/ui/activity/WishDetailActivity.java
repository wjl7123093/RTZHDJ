package com.mytv.rtzhdj.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;
import com.mytv.rtzhdj.app.utils.FileUtils;
import com.mytv.rtzhdj.app.utils.ImageTools;
import com.mytv.rtzhdj.app.utils.MultiPartParamsUtils;
import com.mytv.rtzhdj.app.utils.RouteSystemUIUtils;
import com.mytv.rtzhdj.di.component.DaggerWishDetailComponent;
import com.mytv.rtzhdj.di.module.WishDetailModule;
import com.mytv.rtzhdj.mvp.contract.WishDetailContract;
import com.mytv.rtzhdj.mvp.presenter.WishDetailPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.GridViewAdapter;
import com.mytv.rtzhdj.mvp.ui.widget.BottomDialog;


import net.qiujuer.genius.ui.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lib.kingja.switchbutton.SwitchMultiButton;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 愿望详情 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-28
 * @update
 */
@Route(path = ARoutePath.PATH_WISH_DETAIL)
public class WishDetailActivity extends BaseActivity<WishDetailPresenter> implements WishDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.toolbar_menu_tv)
    TextView mTvToolbarMenu;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_datetime)
    TextView mTvDatetime;
    @BindView(R.id.tv_mobile_phone)
    TextView mTvMobile;
    @BindView(R.id.switchmultibutton)
    SwitchMultiButton mSwitchButton;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @BindView(R.id.ll_bottom_btn)
    LinearLayout mLlBottomBtn;
    @BindView(R.id.btn_edit)
    Button mBtnEdit;
    @BindView(R.id.btn_delete)
    Button mBtnDelte;

    @Autowired
    int wishId;

    private WishDetailEntity mWishDetailEntity;

    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题

    // 图片 九宫格适配器
    private GridViewAdapter gvAdapter;
    private BottomDialog mBottomDialog;
    /** 是否选择照片上传 */
    private boolean isSelectedPhoto = false;
    // 拍照
    public static final int IMAGE_CAPTURE = 1;
    // 从相册选择
    public static final int IMAGE_SELECT = 2;
    // 照片缩小比例
    private static final int SCALE = 5;
    // 用于保存图片资源文件
    private List<Bitmap> lists = new ArrayList<Bitmap>();
    private List<String> list_pic = new ArrayList<String>();
    // 用于保存图片路径
    private List<String> list_path = new ArrayList<String>();
    private String uploadPicture = "";
    private String uploadPictureFromList = "";

    private Map<String, RequestBody> params = new HashMap<>();
    private AlertDialog dialog0 = null;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWishDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .wishDetailModule(new WishDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_wish_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);
        mTvToolbarMenu.setVisibility(View.VISIBLE);
        mTvToolbarMenu.setText("编辑");
        mTvToolbarMenu.setOnClickListener(view -> mLlBottomBtn.setVisibility(View.VISIBLE));

        mBottomDialog = new BottomDialog(WishDetailActivity.this, R.layout.dialog_publish,
                new int[]{R.id.tvCamera, R.id.tvAlbum, R.id.tvCancel});
        initDialog();

        mPresenter.setActivity(WishDetailActivity.this);
        mPresenter.callMethodOfPostWishDetail(wishId, false);

        // switchbutton 切换
        mSwitchButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (position) {
                    case 0:	// TA的心愿
                        mTvContent.setText(mWishDetailEntity.getContent());
                        break;
                    case 1:	// 谁实现了
                        mTvContent.setText("");
                        break;
                }

//				Toast.makeText(PunishListActivity.this, tabText, Toast.LENGTH_SHORT).show();
            }
        });
        // 拨打电话
        mTvMobile.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(mWishDetailEntity.getPhone())) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mWishDetailEntity.getPhone());
                intent.setData(data);
                startActivity(intent);
            }
        });
        // 编辑
        mBtnEdit.setOnClickListener(view -> {
            showDialog();
        });
        // 删除
        mBtnDelte.setOnClickListener(view -> {
            mPresenter.callMethodOfPostDeleteMyWish(wishId);
        });

    }


    @Override
    public void showLoading() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在加载...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void loadData(WishDetailEntity wishDetailEntity) {
        this.mWishDetailEntity = wishDetailEntity;
        mTvTitle.setText(wishDetailEntity.getWishPublisher() + "的心愿");
        mTvDatetime.setText("许愿时间  " + wishDetailEntity.getWishTime());
        mTvMobile.setText("联系电话  " + wishDetailEntity.getPhone());
        mTvContent.setText(mWishDetailEntity.getContent());

    }

    @Override
    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WishDetailActivity.this);
        View dialogView = LayoutInflater.from(WishDetailActivity.this).inflate(R.layout.dialog_wish, null);
        android.widget.Button mBtnWish = dialogView.findViewById(R.id.btn_wish);
        EditText mEdtWish = dialogView.findViewById(R.id.edt_wish_content);
        GridView mGvPublish = dialogView.findViewById(R.id.gv_publish);
        gvAdapter = new GridViewAdapter(this, lists,list_pic, 0);
        mGvPublish.setAdapter(gvAdapter);
        gvAdapter.setList(lists, false);
        mGvPublish.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == lists.size()) {
                    // 弹出对话框
                    mBottomDialog.show();
                }

            }
        });
        mBtnWish.setOnClickListener(view -> {
            params.put("UserID", RequestBody.create(MediaType.parse("text/plain"),
                    "" + DataHelper.getIntergerSF(WishDetailActivity.this, SharepreferenceKey.KEY_USER_ID)));
            params.put("WishDtail", RequestBody.create(MediaType.parse("text/plain"), mEdtWish.getText().toString().trim()));
            params.put("WishID", RequestBody.create(MediaType.parse("text/plain"), wishId+""));

            // 修改 我的心愿
            mPresenter.callMethodOfPostEditMyWish(
                    params,
                    MultiPartParamsUtils.files2Parts("Pictures", list_path.toArray(new String[list_path.size()]), MediaType.parse("image/jpg")));
        });
        builder.setView(dialogView);
        builder.create();
        dialog0 = builder.show();

    }

    @Override
    public void dismissDialog() {
        dialog0.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && resultCode != RESULT_CANCELED) {	// 拍照/相册

            String fileName;
            switch (requestCode) {
                case IMAGE_CAPTURE:// 拍照返回
                    // 将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();
                    // 生成一个图片文件名
                    fileName = String.valueOf(System.currentTimeMillis());
                    // 将处理过的图片添加到缩略图列表并保存到本地
                    ImageTools.savePhotoToSDCard(newBitmap, FileUtils.SDPATH,fileName);
                    lists.add(newBitmap);
                    list_path.add(FileUtils.SDPATH + fileName + ".jpg");
                    list_pic.add(fileName + ".jpg");

                    // 上传图片流
                    try {
//                        uploadPicture += Base64Utils.encodeFile(FileUtils.SDPATH + fileName + ".jpg") + ",";
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    for (int i = 0; i < list_path.size(); i++) {
                        Log.i("UPLOAD", "第"+i+"张照片的地址："+list_path.get(i));
                    }

                    // 更新GrideView
                    gvAdapter.setList(lists, false);

                    // 选择照片后，标记
                    isSelectedPhoto = true;
                    break;

                case IMAGE_SELECT:// 选择照片返回
                    ContentResolver resolver = getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        // 使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,originalUri);
                        if (photo != null) {
                            // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo,photo.getWidth() / SCALE, photo.getHeight()/ SCALE);
                            // 释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();
                            // 生成一个图片文件名
                            fileName = String.valueOf(System.currentTimeMillis());
                            // 将处理过的图片添加到缩略图列表并保存到本地
                            ImageTools.savePhotoToSDCard(smallBitmap, FileUtils.SDPATH,fileName);
                            lists.add(smallBitmap);
                            list_path.add(FileUtils.SDPATH + fileName + ".jpg");
                            list_pic.add(fileName + ".jpg");

                            // 上传图片流
                            try {
//                                uploadPicture += Base64Utils.encodeFile(FileUtils.SDPATH + fileName + ".jpg") + ",";
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            for (int i = 0; i < list_path.size(); i++) {
                                Log.i("UPLOAD", "第"+i+"照片的地址："+list_path.get(i));
                            }

                            // 更新GrideView
                            gvAdapter.setList(lists, false);

                            // 选择照片后，标记
                            isSelectedPhoto = true;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                default:
                    break;
            }
        }
    }


    /**
     * 初始化对话框
     */
    private void initDialog() {
        mBottomDialog.setOnBottomMenuItemClickListener(new BottomDialog.OnBottomMenuItemClickListener() {

            @Override
            public void onBottomMenuItemClick(BottomDialog dialog, View view) {
                switch (view.getId()) {
                    case R.id.tvCamera:		// 拍照
                        RouteSystemUIUtils.captureImage(WishDetailActivity.this, IMAGE_CAPTURE);
                        break;
                    case R.id.tvAlbum:		// 从相册中选取
                        RouteSystemUIUtils.selectImage(WishDetailActivity.this, IMAGE_SELECT);
                        break;
                    case R.id.tvCancel:		// 取消
                        dialog.dismiss();
                        break;

                    default:
                        break;
                }

            }
        });
    }
}
