package com.mytv.rtzhdj.mvp.ui.activity;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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
import com.mytv.rtzhdj.app.utils.FileUtils;
import com.mytv.rtzhdj.app.utils.ImageTools;
import com.mytv.rtzhdj.app.utils.RouteSystemUIUtils;
import com.mytv.rtzhdj.di.component.DaggerDonateComponent;
import com.mytv.rtzhdj.di.module.DonateModule;
import com.mytv.rtzhdj.mvp.contract.DonateContract;
import com.mytv.rtzhdj.mvp.presenter.DonatePresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.GridViewAdapter;
import com.mytv.rtzhdj.mvp.ui.widget.BottomDialog;

import net.qiujuer.genius.ui.widget.Button;

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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我要捐赠界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update 2018-5-8     对接 postDonateSubmit 接口
 */
@Route(path = ARoutePath.PATH_DONATE)
public class DonateActivity extends BaseActivity<DonatePresenter> implements DonateContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_donate_theme)
    EditText mEdtDonateTheme;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_donate_content)
    EditText mEdtDonateContent;
    @BindView(R.id.gv_publish)
    GridView mGvPublish;
    @BindView(R.id.btn_donate)
    Button mBtnDonate;

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
        DaggerDonateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .donateModule(new DonateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_donate; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mBottomDialog = new BottomDialog(DonateActivity.this, R.layout.dialog_publish,
                new int[]{R.id.tvCamera, R.id.tvAlbum, R.id.tvCancel});
        initDialog();

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

        // 我要捐赠
        mBtnDonate.setOnClickListener(view -> {
            params.put("UserId", RequestBody.create(MediaType.parse("text/plain"), DataHelper.getIntergerSF(DonateActivity.this,
                    SharepreferenceKey.KEY_USER_ID) + ""));
            params.put("Topic", RequestBody.create(MediaType.parse("text/plain"), mEdtDonateTheme.getText().toString().trim()));
            params.put("TelePhone", RequestBody.create(MediaType.parse("text/plain"), mEdtPhone.getText().toString().trim()));
            params.put("Content", RequestBody.create(MediaType.parse("text/plain"), mEdtDonateContent.getText().toString().trim()));

            // 调用 我要捐赠 api
            mPresenter.callMethodOfPostDonateSubmit(
                    params,
                    files2Parts("ImageUrls", list_path.toArray(new String[list_path.size()]), MediaType.parse("image/jpg")));

            /*String url = Api.APP_DOMAIN + "postDonateSubmit";
            OkHttpUtils.post().url(url)
                    .addFile("ImageUrls", list_pic.get(0), new File(list_path.get(0)))
                    .addParams("UserId", "8")
                    .addParams("Topic", mEdtDonateTheme.getText().toString().trim())
                    .addParams("TelePhone", mEdtPhone.getText().toString().trim())
                    .addParams("Content", mEdtDonateContent.getText().toString().trim())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showMessage(e.getMessage());

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            showMessage(response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getInt("code") == 200) {
                                    showMessage("提交成功");
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
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
                        RouteSystemUIUtils.captureImage(DonateActivity.this, IMAGE_CAPTURE);
                        break;
                    case R.id.tvAlbum:		// 从相册中选取
                        RouteSystemUIUtils.selectImage(DonateActivity.this, IMAGE_SELECT);
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
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key, String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);

        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key, String[] filePaths, MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();

    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 参数名（name属性）
     * @param value 文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts, String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     * @param builder 用于构建MultipartBody的Builder
     * @param key 参数名（name属性）
     * @param value 文本内容
     */
    public static MultipartBody.Builder addTextPart(MultipartBody.Builder builder, String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key, null, requestBody);
        return builder; }




}
