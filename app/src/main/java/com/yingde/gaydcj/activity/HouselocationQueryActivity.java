//package com.yingde.gaydcj.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
////import com.amap.api.location.AMapLocation;
////import com.amap.api.location.AMapLocationClient;
////import com.amap.api.location.AMapLocationClientOption;
////import com.amap.api.location.AMapLocationListener;
////import com.amap.api.maps2d.AMap;
////import com.amap.api.maps2d.CameraUpdateFactory;
////import com.amap.api.maps2d.LocationSource;
////import com.amap.api.maps2d.MapView;
////import com.amap.api.maps2d.UiSettings;
////import com.amap.api.maps2d.model.BitmapDescriptorFactory;
////import com.amap.api.maps2d.model.LatLng;
////import com.amap.api.maps2d.model.Marker;
////import com.amap.api.maps2d.model.MarkerOptions;
////import com.amap.api.maps2d.model.MyLocationStyle;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.yingde.gaydcj.R;
//import com.yingde.gaydcj.application.PeopleApplication;
//import com.yingde.gaydcj.entity.Door;
//import com.yingde.gaydcj.http.RequestListener;
//import com.yingde.gaydcj.model.HouseModel;
//import com.yingde.gaydcj.model.iml.HouseModelIml;
//import com.yingde.gaydcj.util.MyToolbar;
//import com.jcodecraeer.xrecyclerview.XRecyclerView;
//import com.zhy.adapter.recyclerview.CommonAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//
//import static android.R.attr.id;
//
///**
// * 房屋定位查询
// */
//public class HouselocationQueryActivity extends BaseActivity implements LocationSource, AMapLocationListener {
//    CommonAdapter adapter;
//    @BindView(R.id.toolbar)
//    MyToolbar toolbar;
//    //        @BindView(R.id.rv_house_location)
//    XRecyclerView rvHouseLocation;
//    private List<Door> doors;
//    //声明mLocationOption对象
////    public AMapLocationClientOption mLocationOption = null;
////    AMapLocationClient mlocationClient;
//    HouseModel houseModel;
//    double latitude;//获取纬度
//    double longitude;//获取经度
//    private Context mContext;
//    //显示地图需要的变量
//    private MapView mapView;//地图控件
//    private AMap aMap;//地图对象
//    //定位需要的声明
//    private AMapLocationClient mLocationClient = null;//定位发起端
//    private AMapLocationClientOption mLocationOption = null;//定位参数
//    private OnLocationChangedListener mListener = null;//定位监听器
//
//    //标识，用于判断是否只显示一次定位信息和用户重新定位
//    private boolean isFirstLoc = true;
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d("wh", "mapView=" + mapView);
//        //加载地图控件
//        loadingMap(savedInstanceState);
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected int getLayoutResID() {
//        return R.layout.activity_houselocation_query;
//    }
//
//    @Override
//    protected void initTitle() {
//        mContext = HouselocationQueryActivity.this;
//        super.initTitle();
//        toolbar.setTitle("房屋定位查询");
//
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    /**
//     * 加载地图
//     *
//     * @param savedInstanceState
//     */
//    private void loadingMap(Bundle savedInstanceState) {
//        //显示地图
//        mapView = (MapView) findViewById(R.id.map);
//        //必须要写
//        mapView.onCreate(savedInstanceState);
//        //获取地图对象
//        aMap = mapView.getMap();
//        //设置显示定位按钮 并且可以点击
//        UiSettings settings = aMap.getUiSettings();
//        //设置定位监听
//        aMap.setLocationSource(this);
//        // 是否显示定位按钮
//        settings.setMyLocationButtonEnabled(true);
//        // 是否可触发定位并显示定位层
//        aMap.setMyLocationEnabled(true);
//
//
//        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
////        MyLocationStyle myLocationStyle1 = myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        myLocationStyle.radiusFillColor(android.R.color.transparent);
//        myLocationStyle.strokeColor(android.R.color.transparent);
//        aMap.setMyLocationStyle(myLocationStyle);
//
//        //开始定位
//        initLoc();
//    }
//
//    //定位
//    private void initLoc() {
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(this);
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(false);
//        //设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
//    }
//
//    //定位回调函数
//    @Override
//    public void onLocationChanged(AMapLocation amapLocation) {
//
//        if (amapLocation != null) {
//            if (amapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码
//
//                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
//                if (isFirstLoc) {
//                    //设置缩放级别
//                    aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
//                    //将地图移动到定位点
//                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
//                    //点击定位按钮 能够将地图的中心移动到定位点
//                    mListener.onLocationChanged(amapLocation);
//                    //添加图钉
////                    aMap.addMarker(getMarkerOptions(amapLocation));
//                    //获取定位信息
//                    StringBuffer buffer = new StringBuffer();
//                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
//                    Log.d("wh", "当前位置=" + buffer.toString());
//                    isFirstLoc = false;
//                    //获取经纬度
////                   String gis_x1 =amapLocation.getLatitude() + "";
////                   String  gis_y1 =  amapLocation.getLongitude() + "";
////                    Toast.makeText(getApplicationContext(), gis_x1, Toast.LENGTH_LONG).show();
////                    Toast.makeText(getApplicationContext(), gis_y1, Toast.LENGTH_LONG).show();
//                    String gis_x1 = "121.584986";
//                    String gis_y1 = "31.150052";
//                    String distance1 = "10";
//                    //通过GIS获取门弄牌列表
//                    getDoorByGIS(gis_x1, gis_y1, distance1);
//
//                }
//
//
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e("AmapError", "location Error, ErrCode:"
//                        + amapLocation.getErrorCode() + ", errInfo:"
//                        + amapLocation.getErrorInfo());
//
//                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
////    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
////    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
////        //设置图钉选项
////        MarkerOptions options = new MarkerOptions();
////        //图标
//////        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.add_room));
////        //位置
////        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
////        StringBuffer buffer = new StringBuffer();
////        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
////        //标题
////        options.title(buffer.toString());
////        //子标题
////        options.snippet("这里好火");
////        //设置多少帧刷新一次图片资源
////        options.period(60);
////
////        return options;
////
////    }
//
//
//    //激活定位
//    @Override
//    public void activate(OnLocationChangedListener listener) {
//        mListener = listener;
//
//    }
//
//    //停止定位
//    @Override
//    public void deactivate() {
//        mListener = null;
//    }
//
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//
//    //通过GIS获取门弄牌列表
//    private void getDoorByGIS(String gis_x, String gis_y, String distance) {
//        Log.d("wh", "gis_x=" + gis_x);
//        Log.d("wh", "gis_y=" + gis_y);
//        houseModel = new HouseModelIml(mContext);
//        houseModel.getDoorByGIS(gis_x, gis_y, distance, new RequestListener() {
//            @Override
//            public void onSuccess(Object o, String token) {
//                try {
//                    String json = JSON.toJSONString(o);
//                    if (json != null) {
//                        Log.d("wh", "根据GIS查找街路巷信息");
//                        Log.d("wh", "json" + json);
//                        List<Door> doors = JSON.parseArray(json, Door.class);
//                        Log.d("wh", "streetRoadAlley.size()" + doors.size());
//                        Log.d("wh", "期" + doors.get(20).getMLPHXX());
//                        // 绘制自定义mark
////                        customMark(doors);
////                        LatLng latLng = new LatLng(121.62013864, 31.14671704);
////                        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng));
//                        for (int i = 0; i < doors.size(); i++) {
//                            //绘制mark
//                            LatLng latLng = new LatLng(Double.valueOf(doors.get(i).getGIS_Y()), Double.valueOf(doors.get(i).getGIS_X()));
//                            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(doors.get(i).getMLPHXX()).snippet(doors.get(i).getMLPBM()));
////                          Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(drawbitmap())));
//                        }
//                        // 绑定 Marker 被点击事件
//                        aMap.setOnMarkerClickListener(markerClickListener);
//                        //绑定信息窗点击事件
////                        aMap.setOnInfoWindowClickListener(listener);
//                    } else {
//                        Toast.makeText(HouselocationQueryActivity.this, "当前位置不在查询范围内", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.getMessage();
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    // 定义 Marker 点击事件监听
//    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
//        // marker 对象被点击时回调的接口
//        // 返回 true 则表示接口已响应事件，否则返回false
//        @Override
//        public boolean onMarkerClick(Marker marker) {
//            Log.d("wh", "Marker被点击了");
//            Log.d("wh", "Period=" + marker.getPosition().longitude + "");
//            Log.d("wh", "Period=" + marker.getPosition().latitude + "");
//            Log.d("wh", "Period=" + marker.getPeriod() + "");
//            Log.d("wh", "Snippet=" + marker.getSnippet() + "");
//            Intent intent = new Intent();
//            intent.putExtra("gisOrPzType", PeopleApplication.GIS_TYPE_1004);
////            //门弄牌编码
////            intent.putExtra("mlpbm", arg0.getSnippet());
//            intent.putExtra("mlpbm", "31252771000000000000000055333X");
////            //街路巷代码
////            intent.putExtra("selectNowStreets", doors.get(0).getJLXDM());
//            //门牌号
////            intent.putExtra("edtSelectChshliveraddr",arg0.getSnippet());
//            intent.setClass(HouselocationQueryActivity.this, ComingSHPeopleListActivity.class);
//            startActivity(intent);
//            return true;
//        }
//    };
//
//    //窗口点击事件
//    AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {
//
//        @Override
//        public void onInfoWindowClick(Marker arg0) {
//            Log.d("wh", "窗口被点击了");
//            Log.d("wh", "Period=" + arg0.getPeriod() + "");
//            Log.d("wh", "Snippet=" + arg0.getSnippet() + "");
//            Intent intent = new Intent();
//            intent.putExtra("gisOrPzType", PeopleApplication.GIS_TYPE_1004);
////            //门弄牌编码
////            intent.putExtra("mlpbm", arg0.getSnippet());
//            intent.putExtra("mlpbm", "31252771000000000000000055333X");
////            //街路巷代码
////            intent.putExtra("selectNowStreets", doors.get(0).getJLXDM());
//            //门牌号
////            intent.putExtra("edtSelectChshliveraddr",arg0.getSnippet());
//            intent.setClass(HouselocationQueryActivity.this, ComingSHPeopleListActivity.class);
//            startActivity(intent);
//
//        }
//
//    };
//
//    //自定义mark
//    private void customMark(List<Door> doors) {
//        for (int i = 0; i < doors.size(); i++) {
//            View view = View.inflate(mContext, R.layout.map_mark, null);
////            TextView text_title = (TextView) view.findViewById(R.id.text_title);
////            TextView text_snippet = (TextView) view.findViewById(R.id.text_snippet);
////            text_title.setText(doors.get(i).getMLPHXX());
////            text_snippet.setText(doors.get(i).getMLPBM());
//            Bitmap bitmap = HouselocationQueryActivity.convertViewToBitmap(view);
//            LatLng latLng = new LatLng(Double.valueOf(doors.get(i).getGIS_Y()), Double.valueOf(doors.get(i).getGIS_X()));
//            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)).title(doors.get(i).getMLPHXX()).snippet(doors.get(i).getMLPBM()));
//        }
//
//    }
//
//    //view 转bitmap
//    public static Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        return bitmap;
//    }
//
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("HouselocationQuery Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
//}