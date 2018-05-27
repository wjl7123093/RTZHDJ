package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.utils.FileUtils;
import com.mytv.rtzhdj.app.utils.ImageTools;
import com.mytv.rtzhdj.app.utils.MultiPartParamsUtils;
import com.mytv.rtzhdj.di.component.DaggerMyJoinComponent;
import com.mytv.rtzhdj.di.module.MyJoinModule;
import com.mytv.rtzhdj.mvp.contract.MyJoinContract;
import com.mytv.rtzhdj.mvp.presenter.MyJoinPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.GridViewAdapter;
import com.mytv.rtzhdj.mvp.ui.widget.BottomDialog;

import net.qiujuer.genius.ui.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
 * 参加志愿服务 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-2
 * @update 2018-5-9     对接了 postJoinVolunteerService 接口
 */
@Route(path = ARoutePath.PATH_MY_JOIN)
public class MyJoinActivity extends BaseActivity<MyJoinPresenter> implements MyJoinContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_comment_num)
    TextView mTvCommentNum;
    @BindView(R.id.tv_star_num)
    TextView mTvStarNum;
    @BindView(R.id.edt_address)
    EditText mEdtAddress;
    @BindView(R.id.iv_loc)
    ImageView mIvLoc;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.gv_publish)
    GridView mGvPublish;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Autowired
    int contentId;
    @Autowired
    String title;
    @Autowired
    int digs;

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

    private Map<String, RequestBody> params = new HashMap<>();

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyJoinComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myJoinModule(new MyJoinModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_my_join; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mBottomDialog = new BottomDialog(MyJoinActivity.this, R.layout.dialog_publish,
                new int[]{R.id.tvCamera, R.id.tvAlbum, R.id.tvCancel});
        initDialog();

        mTvTitle.setText(title);
        mTvStarNum.setText(digs + "");
        mTvCommentNum.setVisibility(View.GONE);

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

        mBtnSubmit.setOnClickListener(view -> {
            params.put("UserId", RequestBody.create(MediaType.parse("text/plain"),
                    "" + DataHelper.getIntergerSF(MyJoinActivity.this, SharepreferenceKey.KEY_USER_ID)));
            params.put("ContentID", RequestBody.create(MediaType.parse("text/plain"), contentId + ""));
            params.put("Address", RequestBody.create(MediaType.parse("text/plain"), mEdtAddress.getText().toString().trim()));
            params.put("Feelings", RequestBody.create(MediaType.parse("text/plain"), mEdtContent.getText().toString().trim()));

            mPresenter.callMethodOfPostJoinVolunteerService(
                    params,
                    MultiPartParamsUtils.files2Parts("Pictures", list_path.toArray(new String[list_path.size()]), MediaType.parse("image/jpg")));

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
                    list_pic.add(fileName);

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
                            list_pic.add(fileName);

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
                        captureImage(FileUtils.SDPATH);
                        break;
                    case R.id.tvAlbum:		// 从相册中选取
                        selectImage();
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

    /**
     * 拍照
     *
     * @param path
     *            照片存放的路径
     */
    public void captureImage(String path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            doTakePhotoIn7(new File(Environment.getExternalStorageDirectory(), "image.jpg").getAbsolutePath());
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")));
            startActivityForResult(intent, IMAGE_CAPTURE);
        }

        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, IMAGE_CAPTURE);*/
    }

    /**
     * 从图库中选取图片
     */
    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, IMAGE_SELECT);
    }

    //在Android7.0以上拍照
    private void doTakePhotoIn7(String path) {
        Uri mCameraTempUri;
        try {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            values.put(MediaStore.Images.Media.DATA, path);
            mCameraTempUri = MyJoinActivity.this.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePhoto(IMAGE_CAPTURE, mCameraTempUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePhoto(int requestCode, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }
        startActivityForResult(intent, requestCode);
    }

}
