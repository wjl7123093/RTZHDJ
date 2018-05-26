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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.app.utils.FileUtils;
import com.mytv.rtzhdj.app.utils.ImageTools;
import com.mytv.rtzhdj.app.utils.MultiPartParamsUtils;
import com.mytv.rtzhdj.app.utils.RouteSystemUIUtils;
import com.mytv.rtzhdj.di.component.DaggerMyReceiveWishComponent;
import com.mytv.rtzhdj.di.module.MyReceiveWishModule;
import com.mytv.rtzhdj.mvp.contract.MyReceiveWishContract;
import com.mytv.rtzhdj.mvp.presenter.MyReceiveWishPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.GridViewAdapter;
import com.mytv.rtzhdj.mvp.ui.fragment.MyReceiveWishFragment;
import com.mytv.rtzhdj.mvp.ui.widget.BottomDialog;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我认领的心愿界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-9
 * @update
 */
@Route(path = ARoutePath.PATH_MY_RECEIVE_WISH)
public class MyReceiveWishActivity extends BaseActivity<MyReceiveWishPresenter> implements MyReceiveWishContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.ll_btn_wish)
    LinearLayout mLlBtnWish;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

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

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyReceiveWishComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myReceiveWishModule(new MyReceiveWishModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my_receive_wish; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titles = new String[]{"心愿单", "未实现", "已实现"};

//        collapsingToolbar.setTitleEnabled(false);
        initTab();

        mBottomDialog = new BottomDialog(MyReceiveWishActivity.this, R.layout.dialog_publish,
                new int[]{R.id.tvCamera, R.id.tvAlbum, R.id.tvCancel});
        initDialog();

        mLlBtnWish.setOnClickListener(view -> {
//            showMessage("我要许愿");
            showDialog();
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

    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(MyReceiveWishFragment.newInstance(i));
        }


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 注释掉下面那行，解决 滑动卡顿 问题
//                super.destroyItem(container, position, object);
            }
        });
        tabLayout.setupWithViewPager(mViewPager);
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(() -> {

            try {
                //拿到tabLayout的mTabStrip属性
                Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                mTabStripField.setAccessible(true);

                LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

                int dp10 = DensityUtil.dp2px(10);

                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);

                    //拿到tabView的mTextView属性
                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);

                    TextView mTextView = (TextView) mTextViewField.get(tabView);

                    tabView.setPadding(0, 0, 0, 0);

                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    int width = 0;
                    width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }

                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                    params.width = width ;
                    params.topMargin = dp10;
                    params.leftMargin = dp10;
                    params.rightMargin = dp10;
                    tabView.setLayoutParams(params);

                    tabView.invalidate();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
//        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        initToolBar();
//        initTab();
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

    @Override
    public void loadData(List<MyWishEntity> myWishList) {

    }

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyReceiveWishActivity.this);
        View dialogView = LayoutInflater.from(MyReceiveWishActivity.this).inflate(R.layout.dialog_wish, null);
        Button mBtnWish = dialogView.findViewById(R.id.btn_wish);
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
                    "" + DataHelper.getIntergerSF(MyReceiveWishActivity.this, SharepreferenceKey.KEY_USER_ID)));
            params.put("WishDtail", RequestBody.create(MediaType.parse("text/plain"), mEdtWish.getText().toString().trim()));

            // 提交我的心愿
            mPresenter.callMethodOfPostMyWish(
                    params,
                    MultiPartParamsUtils.files2Parts("Pictures", list_path.toArray(new String[list_path.size()]), MediaType.parse("image/jpg")));
        });
        builder.setView(dialogView);
        builder.create();
        AlertDialog dialog0 = builder.show();
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
                        RouteSystemUIUtils.captureImage(MyReceiveWishActivity.this, IMAGE_CAPTURE);
                        break;
                    case R.id.tvAlbum:		// 从相册中选取
                        RouteSystemUIUtils.selectImage(MyReceiveWishActivity.this, IMAGE_SELECT);
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
