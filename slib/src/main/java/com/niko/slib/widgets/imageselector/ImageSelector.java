package com.niko.slib.widgets.imageselector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.niko.slib.R;
import com.niko.slib.config.Constants;
import com.niko.slib.utils.DimenUtils;
import com.niko.slib.utils.FileUtils;
import com.niko.slib.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niko on 15/9/18.
 */
public class ImageSelector extends LinearLayout {

    private ImageView mIvAddImage;
    private LinearLayout mGalleryView;
    private HorizontalScrollView mHorizontalScrollView;
    private ImageAdapter mAdapter;
    private OnItemOperateListener mOnItemOperateListener;
    //相机拍照后的存放地址,一般文件名要加时间戳,所以文件名是自定义的
    private String mDefaultImagePathOfCamera;
    private ImagePathProvider mImagePathProvider;//图片保存地址生成器
    private Fragment mUsedFromFragment;//是否在Fragment中使用,为了在Fragment的onActivityResult方法中收到数据
    private List<File> mFileList = new ArrayList<>();
    private int mMaxCount = -1;

    public ImageSelector(Context context) {
        super(context);
        init(context);
    }

    public ImageSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_galley_horizontal, this);
        mHorizontalScrollView = view.findViewById(R.id.horizontal_gallery_view);
        mGalleryView = view.findViewById(R.id.gallery_view);
        mIvAddImage = view.findViewById(R.id.iv_add_image);
        mIvAddImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMaxCount > 0 && mFileList.size() >= mMaxCount) {
                    Toast.makeText(getContext(), "最多选择" + mMaxCount + "张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mOnItemOperateListener != null) {
                    addImage();
                } else {
                    Toast.makeText(getContext(), "请先设置OnItemOperateListener", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        setAdapter(imageAdapter);
        imageAdapter.setImageSelector(this);
    }

    public void setUsedFromFragment(Fragment usedFromFragment) {
        mUsedFromFragment = usedFromFragment;
    }

    public void setAddIcon(int resId) {
        mIvAddImage.setBackgroundResource(resId);
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    public void setAdapter(ImageAdapter adapter) {
        if (adapter == null) {
            Toast.makeText(getContext(), "ImageAdapter不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(new MyDataSetObserver());
    }

    public void setImagePathProvider(ImagePathProvider pathProvider) {
        mImagePathProvider = pathProvider;
    }

    public void setOnItemOperateListener(OnItemOperateListener onItemOperateListener) {
        mOnItemOperateListener = onItemOperateListener;
        if (mOnItemOperateListener != null) {
            if (mAdapter != null) {
                mAdapter.setOnItemOperate(onItemOperateListener);
            } else {
                Toast.makeText(getContext(), "请先设置ImageAdapter", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addImage() {
        AlertDialog dialog = new AlertDialog
                .Builder(getContext())
                .setItems(new String[]{"拍照上传", "选择图片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                startCamera();
                                break;
                            case 1:
                                selectPhoto();
                                break;
                        }
                    }
                }).create();
        dialog.show();
    }

    public void startCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定图片的存放地址
        if (mImagePathProvider != null) {
            mDefaultImagePathOfCamera = mImagePathProvider.getImagePath();
        } else {
            mDefaultImagePathOfCamera = FileUtils.getDefaultImageName("slib", "IMG_" + System.currentTimeMillis() + ".png");
        }

        Uri uri = Uri.parse("file://" + mDefaultImagePathOfCamera);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mDefaultImagePathOfCamera)));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (mUsedFromFragment != null) {
            mUsedFromFragment.startActivityForResult(intent, Constants.REQUEST_CODE_FOR_TAKE_PHOTO);
        } else {
            ((Activity) getContext()).startActivityForResult(intent, Constants.REQUEST_CODE_FOR_TAKE_PHOTO);
        }
    }

    public void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (mUsedFromFragment != null) {
            mUsedFromFragment.startActivityForResult(intent, Constants.REQUEST_CODE_FOR_SELECT_IMAGE);
        } else {
            ((Activity) getContext()).startActivityForResult(intent, Constants.REQUEST_CODE_FOR_SELECT_IMAGE);
        }
    }

    //设置是否可以增加图片
    public void setAddable(boolean addable) {
        if (addable) {
            mIvAddImage.setVisibility(VISIBLE);
        } else {
            mIvAddImage.setVisibility(GONE);
        }
    }

    //设置是否可以删除图片
    public void setDeletable(boolean deletable) {
        mAdapter.setDeletable(deletable);
    }

    public interface OnItemOperateListener extends ImageAdapter.OnItemOperate {
        void onTakePhoto(String filePathCameraOnly);//拍照后的回调

        void onSelectImage(Uri uri);//选择图片的回调

        void onAddImage(File file, String imagePath, Uri uri);//拍照或者选择图片后都会回调

        File onAddFile(File file);//返回待上传的文件，可以对其进行压缩等处理再返回一个新的文件
    }

    public interface ImagePathProvider {
        String getImagePath();
    }

    public ImageAdapter getAdapter() {
        return mAdapter;
    }

    public void interceptActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE_FOR_TAKE_PHOTO) {
                String path = "file://" + mDefaultImagePathOfCamera;
                getAdapter().getDataList().add(path);
                mOnItemOperateListener.onTakePhoto(path);
                File file = new File(mDefaultImagePathOfCamera);
                mFileList.add(mOnItemOperateListener.onAddFile(file));
                mOnItemOperateListener.onAddImage(file, path, null);
                getAdapter().notifyDataSetChanged();
                mHorizontalScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            } else if (requestCode == Constants.REQUEST_CODE_FOR_SELECT_IMAGE) {
                Uri uri = data.getData();
                String p = getPath(uri);
                if(TextUtils.isEmpty(p)){
                    return;
                }
                getAdapter().getDataList().add(uri.toString());
                mOnItemOperateListener.onSelectImage(uri);
                File file = new File(p);
                mFileList.add(mOnItemOperateListener.onAddFile(file));
                mOnItemOperateListener.onAddImage(file, null, uri);
                getAdapter().notifyDataSetChanged();
                mHorizontalScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        }
    }

    List<String> getImagePathList() {
        return getAdapter().getDataList();
    }

    public List<File> getFileList() {
        return mFileList;
    }

    public String getPath(Uri uri) {
        String path = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            ToastUtils.toast("图片不在本地或已损坏");
        }
        return path;
    }

    private class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mGalleryView.removeAllViewsInLayout();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View view = mAdapter.getView(i, null, null);
                view.setPadding(0, 0, DimenUtils.dp2Px(getContext(), 10), 0);
                mGalleryView.addView(view);
            }
            mGalleryView.requestLayout();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}
