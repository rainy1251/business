package com.service.business.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.service.business.R;
import com.service.business.model.DistributorBean;
import com.service.business.ui.adapter.base.BaseHolder;
import com.service.business.ui.adapter.base.DefaultAdapter;
import com.service.business.ui.event.MessageDistributorEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DistributorAdapter extends DefaultAdapter {

    Context context;

    public DistributorAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new ViewHolder();
    }


    public class ViewHolder extends BaseHolder<DistributorBean.ItemBean.ListBean> {


        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.tv_choose)
        TextView tvChoose;

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.layout_item_distributor, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        protected void refreshView(final DistributorBean.ItemBean.ListBean resultBean, final int position) {
            Glide.with(context).load(resultBean.avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivAvatar);
            tvName.setText(resultBean.nickname);
            tvMobile.setText(resultBean.mobile);
            tvChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MessageDistributorEvent(resultBean.id));
                }
            });
        }
    }

}
