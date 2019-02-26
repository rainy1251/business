package com.service.business.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.GoodsOneBean;
import com.service.business.ui.activity.GoodsListActivity;
import com.service.business.ui.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

public class ChildView extends LinearLayout {

    private ListView regionListView;   //主ListView
    private GridView plateListView;    //子ListView

    public ArrayList<GoodsOneBean.DatasBean> getDataOne() {
        return dataOne;
    }

    public void setDataOne(ArrayList<GoodsOneBean.DatasBean> dataOne) {
        this.dataOne = dataOne;
    }

    private ArrayList<GoodsOneBean.DatasBean> dataOne=new ArrayList<>();
    private ArrayList<GoodsOneBean.DatasBean> dataTwo=new ArrayList<>();
    //主ListView每一个Item对应的text

    //子ListView每一个Item对应的text..采用了二维数组的实现方式.

    //添加主ListView中的数据信息
    private ArrayList<String> groups = new ArrayList<String>();

    //添加子ListView中的数据信息
    private LinkedList<String> childrenItem = new LinkedList<String>();

    //稀疏数组
    private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
    //为ListView设置适配器
    private mygridview plateListViewAdapter;
    private TextAdapter earaListViewAdapter;
    //监听事件的设置
    private OnSelectListener mOnSelectListener;

    private int tEaraPosition = 0;     //用于保存当前主ListView被点击的Item对应的Position.
    private int tBlockPosition = 0;       //用于保存当前子ListView被点击的Item对应的Position.
    private Context context ;

    private String showString = "";

    public ChildView(Context context) {
        super(context);
        this.context=context;
        init(context);

    }

    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(context);
    }

    private void init(Context context) {
        GoodsListActivity activity = (GoodsListActivity) context;
//        ArrayList<GoodsOneBean.DatasBean> dataOne = activity.getDataOne();
//        ArrayList<GoodsOneBean.DatasBean> dataTwo = activity.getDataTwo();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //加载布局,绑定ID.
        inflater.inflate(R.layout.view_region, this, true);
        regionListView = (ListView) findViewById(R.id.listView);
        plateListView = (GridView) findViewById(R.id.listView2);

        //初始化ListView中每一个item对应的text

        for (int i = 0; i < dataOne.size(); i++) {
            groups.add(dataOne.get(i).label);

            LinkedList<String> tItem = new LinkedList<String>();
            for (int j = 0; j < dataTwo.size(); j++) {

                tItem.add(dataTwo.get(j).label+"￥"+dataTwo.get(j).value);

            }
            children.put(i, tItem);
        }

        //主ListView列表项的适配器
        earaListViewAdapter = new TextAdapter(context, groups,
                R.drawable.choose,
                R.drawable.choose_eara_item_selector);
        earaListViewAdapter.setTextSize(12);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);

        regionListView.setAdapter(earaListViewAdapter);

        earaListViewAdapter
                .setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position < children.size()) {
                            childrenItem.clear();
                            //获取这一页的所有数据信息..然后唤醒适配器更新数据
                            childrenItem.addAll(children.get(position));
                            plateListViewAdapter.notifyDataSetChanged();
                        }
                    }
                });

        if (tEaraPosition < children.size())
            childrenItem.addAll(children.get(tEaraPosition));


        //子ListView的适配器
        plateListViewAdapter = new mygridview(context);

        plateListView.setAdapter(plateListViewAdapter);
        //设置当Item被点击后触发的监听.
        plateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showString = childrenItem.get(position);

                if (mOnSelectListener != null) {

                    mOnSelectListener.getValue(showString);
                }
            }
        });

        if (tBlockPosition < childrenItem.size())
            showString = childrenItem.get(tBlockPosition);
        setDefaultSelect();

    }

    //设置当前Item的Position.
    public void setDefaultSelect() {
        //默认选择的Item项
        regionListView.setSelection(tEaraPosition);
        plateListView.setSelection(tBlockPosition);
    }

    public String getShowText() {
        return showString;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String showText);
    }

    class mygridview extends BaseAdapter {
        Context context;

        public mygridview(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return childrenItem.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.layout_item, null);
            TextView viewById = view.findViewById(R.id.tv_goods);
            viewById.setText(childrenItem.get(position));
            return view;
        }
    }



}
