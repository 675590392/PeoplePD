package com.yingde.gaydcj.activity;

import android.os.Bundle;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/19.
 * 人员轨迹
 */

public class ResearchersTrackActivity extends BaseActivity {
    private MapView mMapView;
    private MapController mMapController;
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    HouseModel houseModel;
    int latitude;//获取纬度
    int longitude;//获取经度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_researcherstrack;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("人员轨迹");
    }

    @Override
    protected void initData() {
        //初始化控件
        intview();

    }

    @Override
    protected void onRestart() {
        intview();
        super.onRestart();
    }


    /**
     * 初始化地图控件
     */
    private void intview() {
        mMapView = (MapView) findViewById(R.id.mapview);
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
            ToastUtil.showToast(ResearchersTrackActivity.this,"定位失败请检查手机定位配置");
            e.getMessage();
            e.printStackTrace();
        }

    }


}
