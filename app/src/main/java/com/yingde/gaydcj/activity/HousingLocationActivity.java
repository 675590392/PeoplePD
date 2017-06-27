package com.yingde.gaydcj.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.alibaba.fastjson.JSON;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.adapter.RecyclerAdapter;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.Door;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/9.
 * 房屋定位查询
 */

public class HousingLocationActivity extends BaseActivity {

    private MapView mMapView;
    private MapController mMapController;
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_mplbm_list)
    XRecyclerView rvRemainderWoekList;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;
    TextView tv_nextpage;
    CommonAdapter adapter;
    HouseModel houseModel;
    int latitude;//获取纬度
    int longitude;//获取经度
    //查询范围
    private  String scope;
    //页数
    private  int index=1;
    //返回结果集
    List<Door> ltdoors;
   //测试下拉加载
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<Door> mData = new ArrayList<Door>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_houselocation;
    }

    @Override
    protected void initTitle() {
        mContext = HousingLocationActivity.this;
        super.initTitle();
        toolbar.setTitle("房屋定位查询");
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        scope = sp.getString("scope", "20");
        //初始化控件
        intview();

    }

    @Override
    protected void onRestart() {
        intview();
        super.onRestart();
    }

    /**
     * 加载列表（房屋门牌弄编码） List<Door> ltdoors
     */
    private  void loadingHousingiIformation(){
        Log.d("wh","加载列表");
        ltdoors=new ArrayList<Door>();
        rvRemainderWoekList.setLayoutManager(new LinearLayoutManager(HousingLocationActivity.this));
        rvRemainderWoekList.setItemAnimator(new DefaultItemAnimator());
        rvRemainderWoekList.addItemDecoration(new DividerItemDecoration(HousingLocationActivity.this, DividerItemDecoration.VERTICAL));
        rvRemainderWoekList.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
                index=1;
                //通过GIS获取门弄牌列表
                getDoorByGIS("121.59620806", " 31.12745559", scope,index+"");
//                getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                rvRemainderWoekList.refreshComplete();
            }
            //加载更多
            @Override
            public void onLoadMore() {
                index++;
                //通过GIS获取门弄牌列表
                getDoorByGIS("121.59620806", " 31.12745559", scope,index+"");
//                getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                rvRemainderWoekList.loadMoreComplete();//加载更多完成
//                rvRemainderWoekList.setSelected(true);
//                rvRemainderWoekList.smoothScrollToPosition(4);
            }
        });

        adapter = new CommonAdapter<Door>(HousingLocationActivity.this, R.layout.simple_layout , ltdoors) {
            @Override
            protected void convert(ViewHolder holder, Door doors, int position) {
                if (doors != null) {
                    holder.setText(R.id.tv_HousingInformation, queryIsEmpty(doors.getMLPHXX()));
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("gisOrPzType", PeopleApplication.GIS_TYPE_1004);
                //门弄牌编码
//                intent.putExtra("mlpbm", ltdoors.get(position).getJLXDM() );
                intent.putExtra("mlpbm",  "312530750005080000000000460000");
                intent.setClass(HousingLocationActivity.this, ComingSHPeopleListActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //加载适配器
        rvRemainderWoekList.setAdapter(adapter);

    }

    /**
     * 初始化地图控件
     */
    private void intview() {
        mMapView = (MapView) findViewById(R.id.map1);
        //设置启用内置的缩放控件
        mMapView.setBuiltInZoomControls(true);
        //得到mMapView的控制权,可以用它控制和驱动平移和缩放
        mMapController = mMapView.getController();
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
        //设置地图中心点
        mMapController.setCenter(point);
        //设置地图zoom级别
        mMapController.setZoom(16);
        //展示地图
        mapshow();
    }

    /**
     * 展示地图
     */
    private void mapshow() {
        mMapView.setSatellite(false);
        //地名的隐藏功能只有在卫星地图模式下才起作用：
        mMapView.setPlaceName(true);  //隐藏地名
        //地图放大缩小
        mMapView.setDoubleTapEnable(true);
        //去掉放大缩小按钮
        mMapView.setBuiltInZoomControls(false);
       //展示当前位置
        MyLocationOverlay myLocation = new MyLocationOverlay(this, mMapView);
        myLocation.enableMyLocation(); //显示我的位置
        mMapView.addOverlay(myLocation);
        try {
            //获取纬度
            latitude = myLocation.getMyLocation().getLongitudeE6();
            //获取经度
            longitude = myLocation.getMyLocation().getLatitudeE6();
            //显示当前位置
            mMapController.animateTo(new GeoPoint(myLocation.getMyLocation().getLatitudeE6(), myLocation.getMyLocation().getLongitudeE6()));
        } catch (Exception e) {
            ToastUtil.showToast(HousingLocationActivity.this,"定位失败请检查手机定位配置");
            e.getMessage();
            e.printStackTrace();
        }
        loadingHousingiIformation();
        //获取门牌弄编码
//        getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
          getDoorByGIS("121.59620806", " 31.12745559", scope,index+"");

    }

    //通过GIS获取门弄牌列表
    private void getDoorByGIS(String gis_x, String gis_y, String distance, final String index) {
        Log.d("wh", "gis_x=" + gis_x);
        Log.d("wh", "gis_y=" + gis_y);
        Log.d("wh", "distance=" + distance);
        Log.d("wh", "index=" + index);
        houseModel = new HouseModelIml(HousingLocationActivity.this);
        houseModel.getDoorByGIS(gis_x, gis_y, distance,index, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                try {
                    String json = JSON.toJSONString(o);
                    if (json != null) {
                        Log.d("wh", "根据GIS查找街路巷信息");
                        Log.d("wh", "json" + json);
                        List<Door> doors = JSON.parseArray(json, Door.class);
                        if (index.endsWith("1")&&doors.size()==0){
                            tv_nodata.setVisibility(View.VISIBLE);
                            rvRemainderWoekList.setVisibility(View.GONE);
                        }
                        //加载房屋信息
//                        ltdoors.clear();
                        ltdoors.addAll(doors);
//                        mData.addAll(doors);
                        Log.d("wh","ltdoors长度="+ltdoors.size());
//                        loadingHousingiIformation(ltdoors);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HousingLocationActivity.this, "当前位置不在查询范围内", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.tv_nodata})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nodata:
                getDoorByGIS("121.59620806", " 31.12745559", scope,index+"");
//                getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                break;
        }

    }

private void  DownloadMap(){
//    //设置离线地图存放路径
//    TOfflineMapManager  offlineMapMng = new TOfflineMapManager();
//    offlineMapMng.setMapPath("/sdcard/tianditu/");
//    //启动下载，下载北京的矢量地图
//    offlineMapMng.startDownload("北京", MapView.TMapType.MAP_TYPE_VEC);
//    //暂停下载
//    offlineMapMng.pauseDownload();
//    //监听下载进度
//    TOfflineMapInfo info = offlineMapMng.getDownloadInfo(etCity.getText().toString()
//            MapView.TMapType.MAP_TYPE_VEC);
//    //判断是否下载完成
//    if(info == null || info.getDledState()!=TOfflineMapManager.OFFLINEMAP_DOWNLOADING)
//        return;
//    mProBar.setProgress(info.getDledSize()*100/info.getSize());
////处理下载完成
//    if(info.getDledState() == TOfflineMapManager.OFFLINEMAP_DOWNLOAD_FINISHED)
//    {
//        mMapView.setOfflineMaps(offlineMapMng.searchMaps());
//    }
}


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HousingLocationActivity.this));

        mRecyclerAdapter = new RecyclerAdapter(HousingLocationActivity.this,mData);
        mRecyclerAdapter.setFootViewText("加载中。。。");
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

//    private void addTestData() {
//        //增加测试数据
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//        mData.add("Recycler");
//    }
}
