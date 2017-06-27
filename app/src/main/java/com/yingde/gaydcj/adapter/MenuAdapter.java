package com.yingde.gaydcj.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.activity.ComingSHPeopleActivity;
import com.yingde.gaydcj.activity.HomeActivity;
//import com.yingde.gaydcj.activity.HouselocationQueryActivity;
import com.yingde.gaydcj.activity.HousingLocationActivity;
import com.yingde.gaydcj.activity.NewHouseActivity;
import com.yingde.gaydcj.activity.PiecesInfoCollectionActivity;
import com.yingde.gaydcj.activity.ReminderWorkActivity;
import com.yingde.gaydcj.activity.ResearchersTrackActivity;
import com.yingde.gaydcj.activity.RoomIdentiActivity;
import com.yingde.gaydcj.activity.SelectAddDetailActivity;
import com.yingde.gaydcj.activity.SetupActivity;
import com.yingde.gaydcj.activity.WorkloadStatisticActivity;
import com.yingde.gaydcj.entity.AppPermissions;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 * app权限配置适配
 */

public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<AppPermissions> list;
    private int[] icon = {R.drawable.people_collected, R.drawable.researchers_collected, R.drawable.collect_details,
            R.drawable.room_marked, R.drawable.add_room, R.drawable.query_room, R.drawable.job_account,
            R.drawable.notice, R.drawable.system_settings};

    public MenuAdapter(List<AppPermissions> list, Context context) {
        super();
        this.list = list;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int code;
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.home_grid_item, null);
            holder.ItemImage = (ImageView) convertView
                    .findViewById(R.id.ItemImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        code=Integer.valueOf(list.get(position).getSORTID().substring(0,1));
        holder.ItemImage.setBackgroundResource(icon[code]);
        holder.ItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (list.get(position).getCODE()) {
                    case "APP_NEWPERSON": {
                        //人员采集
                        Log.d("wh","人员采集");
                        Intent intent = new Intent();
                        intent.setClass( mContext, ComingSHPeopleActivity.class);
                        mContext.startActivity(intent);
                    }
                    break;
                    case "APP_FRAGMENT": {
                        //碎片采集
                        Intent intent = new Intent();
                        intent.setClass( mContext, PiecesInfoCollectionActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_CJXQ": {
                        //采集详情
                        Intent intent = new Intent();
                        intent.setClass( mContext, SelectAddDetailActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_LABEL": {
                        //人房标识
                        Intent intent = new Intent();
                        intent.setClass( mContext, RoomIdentiActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_NEWHOUSE": {
                        //新增房屋
                        Intent intent = new Intent();
                        intent.setClass( mContext, NewHouseActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_FWDW": {
                        //房屋定位查询
                        Log.d("wh","房屋定位查询");
                        Intent intent = new Intent();
                        intent.setClass( mContext, HousingLocationActivity.class);
//                        intent.setClass( mContext, HouselocationQueryActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_GZTJ": {
                        Log.d("wh","工作统计");
                        //工作统计
                        Intent intent = new Intent();
                        intent.setClass( mContext, WorkloadStatisticActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_NOTICE": {
                        //通知公告
                        Intent intent = new Intent();
                        intent.setClass( mContext, ReminderWorkActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
                    case "APP_SETTING": {
                        //系统设置
                        Intent intent = new Intent();
                        intent.setClass (mContext, SetupActivity.class);
//                        intent.setClass (mContext, ResearchersTrackActivity.class);
                        mContext. startActivity(intent);
                    }
                    break;
            }

        }
    });
        return convertView;
    }

    private class ViewHolder {
        ImageView ItemImage;
    }
}
