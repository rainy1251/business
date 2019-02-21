package com.service.business.ui.fragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.StateBean;
import com.service.business.model.UserInfoBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.base.BaseFragment;
import com.service.business.ui.record.PlaybackDialogFragment;
import com.service.business.ui.record.RecordAudioDialogFragment;
import com.service.business.ui.record.RecordingItem;
import com.service.business.ui.utils.Constants;
import com.service.business.ui.utils.MyLog;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


public class SystemFragment extends BaseFragment {

    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_play_record)
    TextView tvPlayRecord;
    @BindView(R.id.tv_upload_record)
    TextView tvUploadRecord;
    Unbinder unbinder;
    private String filePath;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_system;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_record, R.id.tv_play_record, R.id.tv_upload_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record:
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getActivity().getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });
                break;
            case R.id.tv_play_record:
                RecordingItem recordingItem = new RecordingItem();
                SharedPreferences sharePreferences = getActivity().getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                filePath = sharePreferences.getString("audio_path", "");
                long elpased = sharePreferences.getLong("elpased", 0);
                recordingItem.setFilePath(filePath);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getActivity().getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
                break;
            case R.id.tv_upload_record:
                SharedPreferences sharePreferences2 = getActivity().getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                filePath = sharePreferences2.getString("audio_path", "");
                if (filePath.contains(".mp3")) {
                    String token = SPUtils.getString("token");
                    MyLog.show(token);
                    File compressfile = new File(filePath);
                    OkHttpUtils.getInstance()
                            .post()
                            .addHeader("Content-Disposition", "form-data;filename=enctype")
                            .addHeader("X-communityapp-Token", token)
                            .url(Constants.BASE_URL + "/admin/storage/upload")
                            .addFile("file", compressfile.getName(), compressfile)
                            .build().execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator()) {
                        @Override
                        public void onResponse(StateBean response, int id) {
                            if (response.errno == 0) {
                                MyToast.show("上传成功");
                            }

                        }
                    });
                } else {
                    MyToast.show("文件不存在");
                }
                break;
        }
    }
}
