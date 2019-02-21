package com.service.business.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.ui.base.BaseFragment;
import com.service.business.ui.record.PlaybackDialogFragment;
import com.service.business.ui.record.RecordAudioDialogFragment;
import com.service.business.ui.record.RecordingItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


public class ServiceFragment extends BaseFragment {

    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_play_record)
    TextView tvPlayRecord;
    @BindView(R.id.tv_upload_record)
    TextView tvUploadRecord;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recent;
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
                final String filePath = sharePreferences.getString("audio_path", "");
                long elpased = sharePreferences.getLong("elpased", 0);
                recordingItem.setFilePath(filePath);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getActivity().getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
                break;
            case R.id.tv_upload_record:

                break;
        }
    }
}
