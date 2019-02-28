package com.service.business.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.AllCategoryBean;
import com.service.business.model.GoodsOneBean;
import com.service.business.ui.adapter.base.BaseHolder;
import com.service.business.ui.adapter.base.DefaultAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwoAdapter extends DefaultAdapter {

    Context context;

    public TwoAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new ViewHolder();
    }


    public class ViewHolder extends BaseHolder<AllCategoryBean.DatasBean.TreeBean.ChildrenBean> {


        @BindView(R.id.tv_goods)
        TextView tvGoods;

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.layout_item_two, null);
            ButterKnife.bind(this,view);
            return view;
        }

        @Override
        protected void refreshView(final AllCategoryBean.DatasBean.TreeBean.ChildrenBean resultBean, int position) {
            tvGoods.setText(resultBean.label);
        }
    }


}
