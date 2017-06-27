package com.yingde.gaydcj.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.activity.HousingLocationActivity;
import com.yingde.gaydcj.entity.Door;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.iml.HouseModelIml;

import java.util.List;

/**
 * 项目名称：RecyclerViewTest
 * 创建人：Double2号
 * 创建时间：2016/4/18 8:12
 * 修改备注：
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<Door> mData;//数据
    private int max_count = 5;//最大显示数
    private Boolean isFootView = false;//是否添加了FootView
    private String footViewText = "";//FootView的内容

    //两个final int类型表示ViewType的两种类型
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1111;


    public RecyclerAdapter(Context context, List<Door> data) {
        mContext = context;
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvViewHolder;
        public LinearLayout llViewHolder;

        public TextView tvFootView;//footView的TextView属于独自的一个layout

        //初始化viewHolder，此处绑定后在onBindViewHolder中可以直接使用
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                tvViewHolder = (TextView) itemView.findViewById(R.id.tv_view_holder);
                llViewHolder = (LinearLayout) itemView;
            } else if (viewType == FOOT_TYPE) {
                tvFootView = (TextView) itemView;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View normal_views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rc_item, parent, false);
        View foot_view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.foot_view, parent, false);

        if (viewType == FOOT_TYPE)
            return new ViewHolder(foot_view, FOOT_TYPE);
        return new ViewHolder(normal_views, NORMAL_TYPE);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == max_count - 1) {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //建立起ViewHolder中试图与数据的关联
        Log.d("wh", getItemViewType(position) + "");
        //如果footview存在，并且当前位置ViewType是FOOT_TYPE
        if (isFootView && (getItemViewType(position) == FOOT_TYPE)) {
            holder.tvFootView.setText(footViewText);
            // 刷新太快 所以使用Hanlder延迟两秒
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                     Log.d("wh","run");
                    max_count += 5;
                    notifyDataSetChanged();
                }
            }, 2000);

        } else {
            holder.tvViewHolder.setText(mData.get(position).getMLPHXX());
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() < max_count) {
            return mData.size();
        }
        return max_count;
    }

    //创建一个方法来设置footView中的文字
    public void setFootViewText(String footViewText) {
        isFootView = true;
        this.footViewText = footViewText;
    }


}
