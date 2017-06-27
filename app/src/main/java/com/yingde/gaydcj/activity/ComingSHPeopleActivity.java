package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tianditu.android.maps.MyLocationOverlay;

import android.content.SharedPreferences;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.tianditu.android.maps.MapView;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.adapter.PsyManageGetPoliceNameAdapter;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.db.PeopleHandleWayDao;
import com.yingde.gaydcj.entity.Door;
import com.yingde.gaydcj.entity.StreetRoadAlley;
import com.yingde.gaydcj.entity.db.D_MLPH;
import com.yingde.gaydcj.entity.db.D_ROAD;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.adapter.PsyManageGetPoliceNameAdapter.pcsmcCallClass;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 人员采集
 */
public class ComingSHPeopleActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.select_nowStreets)
    TextView selectNowStreets;
    @BindView(R.id.edt_nong)
    EditText edtNong;
    @BindView(R.id.edt_zhinong)
    EditText edtZhinong;
    @BindView(R.id.edt_hao)
    EditText edtHao;
    @BindView(R.id.edt_shi)
    EditText edtShi;
    //精确查询布局
    @BindView(R.id.linear_accurate)
    LinearLayout linearAccurate;
    @BindView(R.id.edt_select_chshliveraddr)
    AutoCompleteTextView edtSelectChshliveraddr;
    //模糊查询布局
    @BindView(R.id.mohu)
    LinearLayout mohu;
    @BindView(R.id.btn_select_readHouse)
    Button btnSelectReadHouse;
    //    @BindView(R.id.edt_distance_mlp)
//    EditText edtDistanceMlp;
    @BindView(R.id.btn_gis_mlp)
    Button btnGisMlp;
    //    @BindView(R.id.edt_distance_jlx)
//    EditText edtDistanceJlx;
    @BindView(R.id.btn_gis_jlx)
    Button btnGisJlx;
    private MapView mMapView;
    //是否切换
    private boolean isMoHu = false;

    //声明mLocationOption对象
//    public AMapLocationClientOption mLocationOption = null;
//    AMapLocationClient mlocationClient;

    HouseModel houseModel;
    double latitude;//获取纬度
    double longitude;//获取经度
    //联想输出
    private PsyManageGetPoliceNameAdapter pcsmcAdapter = null;
    public String[] pcsmcStr = null;
    private List<D_ROAD> dictionaryRoadList = new ArrayList<D_ROAD>();
    private List<D_MLPH> dictionaryMlphList = new ArrayList<D_MLPH>();
    private PeopleHandleWayDao PeopleHandleWayDao;
    private String chshliveroad;
    private static final String FILE_NAME = "PEOPLE_PD";
    private String scope;
    XRecyclerView rvRemainderWoekList;
    Button tv_next, tv_up;
    //页数
    private int index = 1;
    //返回结果集
    List<Door> ltdoors = new ArrayList<Door>();
    CommonAdapter adapter;
    private Dialog mDownloadDialog;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_sh_people;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("房屋列表查询");
//        toolbar.showChangeBtn(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isMoHu) {
//                    isMoHu = false;
//                    mohu.setVisibility(View.VISIBLE);
//                    linearAccurate.setVisibility(View.GONE);
//                } else {
//                    isMoHu = true;
//                    mohu.setVisibility(View.GONE);
//                    linearAccurate.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        try {
            //不显示地图，直接自己建一个对象
            mMapView = new MapView(mContext, "");
            MyLocationOverlay myLocation = new MyLocationOverlay(this, mMapView);
            myLocation.enableMyLocation(); //显示我的位置
            mMapView.addOverlay(myLocation);
            //获取纬度
            latitude = myLocation.getMyLocation().getLongitudeE6() / 1E6;
            //获取经度
            //获取经度
            longitude = myLocation.getMyLocation().getLatitudeE6() / 1E6;
            //获取查询范围
//            scope=  SharedPreferencesUtil.getSetting(FILE_NAME,mContext,"scope");
            SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
            scope = sp.getString("scope", null);
            if (scope == null || scope.endsWith("")) {
                scope = "20";
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            scope = "20";
        }
        houseModel = new HouseModelIml(mContext);
        if (PeopleHandleWayDao == null) {
            PeopleHandleWayDao = new PeopleHandleWayDao();
        }
//        initMap();
        //居住地门牌号
        inputNameOrLive();
        //街镇路
        inputNameOrIDcard();
    }

    private void setList() {
        if (selectNowStreets == null || selectNowStreets.equals("")) {
            Toast.makeText(this, "请检查你输入的街道(路)是否正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMoHu) {
            if (TextUtils.isEmpty(edtSelectChshliveraddr.getText().toString()
                    .trim())) {
                Toast.makeText(this, "请输入居住地门牌号", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (TextUtils.isEmpty(edtHao.getText().toString().trim())) {
                Toast.makeText(this, "请输入号", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(edtShi.getText().toString().trim())) {
                Toast.makeText(this, "请输入室", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(ComingSHPeopleActivity.this,
                ComingSHPeopleListActivity.class);
        intent.putExtra("gisOrPzType", PeopleApplication.PZ_TYPE_1003);
        intent.putExtra("selectNowStreets", chshliveroad);
        intent.putExtra("edtSelectChshliveraddr", edtSelectChshliveraddr.getText().toString().trim());
        startActivity(intent);
    }

    //居住地门牌号
    private void inputNameOrLive() {
        // 控件的文本改变事件
        edtSelectChshliveraddr.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {// 文本改变
                // // 当文本框中有文字时隐藏 请输入姓名和身份证号内容
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {// 文本改变前
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 文本改变后 。。。。。
                // // 当文本框中有文字时隐藏 请输入姓名和身份证号内容
                // 拿到派出所名称
                String edPsyName = arg0.toString().trim();
                if (!TextUtils.isEmpty(edPsyName)) {
                    // 数据库模糊查询语句
//                    dictionaryList = peopleDao.queryDictionary(edPsyName);
                    dictionaryMlphList = PeopleHandleWayDao.queryMLPHDictionary(edPsyName);
                    // 拿到数组长度
                    pcsmcStr = new String[dictionaryMlphList.size()];
                    for (int pcsmcStrNum = 0; pcsmcStrNum < pcsmcStr.length; pcsmcStrNum++) {
                        // 根据dictionaryList数据库查到的数 拿到派出所名称
                        pcsmcStr[pcsmcStrNum] = dictionaryMlphList.get(pcsmcStrNum).getMLPH();
                    }
                    if (dictionaryMlphList.size() > 0) {
                        pcsmcAdapter = new PsyManageGetPoliceNameAdapter(ComingSHPeopleActivity.this, pcsmcCallMlph, pcsmcStr);
                        edtSelectChshliveraddr.setAdapter(pcsmcAdapter);
                        pcsmcAdapter.notifyDataSetChanged();
                        // 根据文本长度 控制光标位置
                        edtSelectChshliveraddr.setSelection(edtSelectChshliveraddr
                                .getText().length());
                    } else {
                        if (pcsmcAdapter != null) {
                            dictionaryMlphList.clear();
                            pcsmcAdapter.notifyDataSetChanged();
                        }
                    }
                }
                if (pcsmcStr.toString().trim() != null) {
                    pcsmcAdapter = new PsyManageGetPoliceNameAdapter(
                            ComingSHPeopleActivity.this, pcsmcCallMlph, pcsmcStr);
                    edtSelectChshliveraddr.setAdapter(pcsmcAdapter);
                    pcsmcAdapter.notifyDataSetChanged();
                }
                // 根据文本长度 控制光标位置
                edtSelectChshliveraddr.setSelection(edtSelectChshliveraddr.getText()
                        .length());
            }
        });
    }

    // 街镇路名称显示
    private void inputNameOrIDcard() {

//        // 控件的文本改变事件
//        selectNowStreets.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//                                      int arg3) {// 文本改变
//                // // 当文本框中有文字时隐藏 请输入姓名和身份证号内容
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1,
//                                          int arg2, int arg3) {// 文本改变前
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // 文本改变后 。。。。。
//                // // 当文本框中有文字时隐藏 请输入姓名和身份证号内容
//                // 拿到派出所名称
//                String edPsyName = arg0.toString().trim();
//                if (!TextUtils.isEmpty(edPsyName)) {
//                    // 数据库模糊查询语句
//                    dictionaryRoadList = PeopleHandleWayDao.queryRoadDictionary(edPsyName);
//                    // 拿到数组长度
//                    pcsmcStr = new String[dictionaryRoadList.size()];
//                    for (int pcsmcStrNum = 0; pcsmcStrNum < pcsmcStr.length; pcsmcStrNum++) {
//                        // 根据dictionaryList数据库查到的数 拿到派出所名称
//                        pcsmcStr[pcsmcStrNum] = dictionaryRoadList.get(pcsmcStrNum).getMC();
//                    }
//                    if (dictionaryRoadList.size() > 0) {
//                        pcsmcAdapter = new PsyManageGetPoliceNameAdapter(ComingSHPeopleActivity.this, pcsmcCallRoad, pcsmcStr);
//                        selectNowStreets.setAdapter(pcsmcAdapter);
//                        pcsmcAdapter.notifyDataSetChanged();
//                        // 根据文本长度 控制光标位置
//                        selectNowStreets.setSelection(selectNowStreets
//                                .getText().length());
//                        btnSelectReadHouse.setEnabled(false);
//                    } else {
//                        if (pcsmcAdapter != null) {
//                            dictionaryRoadList.clear();
//                            pcsmcAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//                if (pcsmcStr.toString().trim() != null) {
//                    pcsmcAdapter = new PsyManageGetPoliceNameAdapter(
//                            ComingSHPeopleActivity.this, pcsmcCallRoad, pcsmcStr);
//                    selectNowStreets.setAdapter(pcsmcAdapter);
//                    pcsmcAdapter.notifyDataSetChanged();
//                }
//                // 根据文本长度 控制光标位置
//                selectNowStreets.setSelection(selectNowStreets.getText()
//                        .length());
//
//            }
//        });
        selectNowStreets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComingSHPeopleActivity.this,
                        ComingSHPeopleAddSelectActivity.class);
                intent.putExtra("fieldsource", "D_ROAD_SELECT");
                startActivityForResult(intent, PeopleApplication.SELECT_SIMPLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单选
        if (data != null && resultCode == PeopleApplication.SELECT_SIMPLE_BACK) {
            int position = data.getIntExtra("position", -1);
            String name = data.getStringExtra("name");
            String code = data.getStringExtra("code");
            selectNowStreets.setText(name);
            chshliveroad = code;

        }
    }

    //    // 派出所名称回调方法
//    pcsmcCallClass pcsmcCallRoad = new pcsmcCallClass() {
//        public void myonClick(int position, View arg0) {
//            selectNowStreets.setText("");
//            String dm = "";
//            // 这种拿值写在前面
//            dm = dictionaryRoadList.get(position).getDM();
//            selectNowStreets.setText(dictionaryRoadList.get(position).getMC());//
//            // 这个就写在后面第二句
//            chshliveroad = dm.toString().trim();
//            // 关闭下拉框
//            selectNowStreets.dismissDropDown();
//            btnSelectReadHouse.setEnabled(true);
//        }
//    };
    // 派出所名称回调方法
    pcsmcCallClass pcsmcCallMlph = new pcsmcCallClass() {
        public void myonClick(int position, View arg0) {
            edtSelectChshliveraddr.setText("");
            edtSelectChshliveraddr.setText(dictionaryMlphList.get(position).getMLPH());//
            // 这个就写在后面第二句
            // 关闭下拉框
            edtSelectChshliveraddr.dismissDropDown();
        }
    };

    protected void onResume() {
        super.onResume();
        btnSelectReadHouse.setEnabled(true);
    }
//    public void initMap() {
//        mlocationClient = new AMapLocationClient(mContext);
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位监听
//        mlocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(final AMapLocation aMapLocation) {
//                if (aMapLocation != null) {
//                    if (aMapLocation.getErrorCode() == 0) {
//                        //定位成功回调信息，设置相关消息
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        aMapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        df.format(date);//定位时间
//                        latitude = aMapLocation.getLatitude();//获取纬度
//                        longitude = aMapLocation.getLongitude();//获取经度
//                    } else {
//                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError", "location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());
//                    }
//                }
//            }
//        });
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(5000);
//        //设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//        // 在定位结束后，在合适的生命周期调用onDestroy()方法
//        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//        //启动定位
//        mlocationClient.startLocation();
//
//    }

    private void getshowDoorList() {
        //数据操作
        final List<String> list = new ArrayList<String>();
        Observable.from(ltdoors)
                .map(new Func1<Door, String>() {
                    @Override
                    public String call(Door door) {
                        return door.getMLPHXX() + door.getJLXMC() + door.getMLPH();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        list.add(s);
                    }
                });
        Log.d("wh", "长度=" + ltdoors.size());
        if (ltdoors.size() != 0) {
//            mlocationClient.stopLocation();
            Log.d("wh", "信息=" + ltdoors.get(0).getMLPHXX());
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("定位门弄牌");
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.jlxbm_list, null);
            rvRemainderWoekList = (XRecyclerView) v.findViewById(R.id.rv_mplbm_lt);
            tv_next = (Button) v.findViewById(R.id.tv_next);
            tv_up = (Button) v.findViewById(R.id.tv_up);

            /**
             * 加载列表（房屋门牌弄编码） List<Door> ltdoors
             */
            Log.d("wh", "加载列表");
            Log.d("wh", "ltdoors" + ltdoors.size());
            rvRemainderWoekList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleActivity.this));
            rvRemainderWoekList.setItemAnimator(new DefaultItemAnimator());
            rvRemainderWoekList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleActivity.this, DividerItemDecoration.VERTICAL));

            adapter = new CommonAdapter<Door>(ComingSHPeopleActivity.this, R.layout.simple_layout, ltdoors) {
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
//                 intent.putExtra("mlpbm", doors.get(position).getJLXDM() );
                    intent.putExtra("mlpbm", "312530750005080000000000460000");
                    intent.setClass(ComingSHPeopleActivity.this, ComingSHPeopleListActivity.class);
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            //加载适配器
            rvRemainderWoekList.setAdapter(adapter);
            builder.setView(v);
            //下一页
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ltdoors.size() < 5) {
                        ToastUtil.showToast(ComingSHPeopleActivity.this, "已经是最后一页了");
                        tv_next.setEnabled(false);
                        tv_next.setFocusable(false);
                        return;
                    }
                    index++;
                    getDoorByGIS("121.59620806", " 31.12745559", scope, index + "");
////                getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                }
            });
            //上一页
            tv_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index--;
                    if (index < 1) {
                        index = 1;
                    }
                    getDoorByGIS("121.59620806", " 31.12745559", scope, index + "");
                    //getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                }
            });
            mDownloadDialog = builder.create();
            mDownloadDialog.show();

//            new AlertDialog.Builder(mContext)
//                    .setTitle("小贴士")
//                    .setItems(list.toArray(new String[]{}), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String mlpbm = doors.get(which).getMLPBM();
//                            Intent intent = new Intent(ComingSHPeopleActivity.this,
//                                    ComingSHPeopleListActivity.class);
//                            intent.putExtra("gisOrPzType", PeopleApplication.GIS_TYPE_1004);
//                            intent.putExtra("mlpbm", mlpbm);
//                            startActivity(intent);
//                        }
//                    })
//                    .show();
        }
    }

    private void getshowStreetRoadAlleyList(final List<StreetRoadAlley> streetRoadAlley) {
        //数据操作
        final List<String> list = new ArrayList<String>();
        Observable.from(streetRoadAlley)
                .map(new Func1<StreetRoadAlley, String>() {
                    @Override
                    public String call(StreetRoadAlley streetRoadAlleys) {
                        return streetRoadAlleys.getJLXDM() + streetRoadAlleys.getJLXMC();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        list.add(s);
                    }
                });

        if (list.size() != 0) {


            new AlertDialog.Builder(mContext)
                    .setTitle("小贴士")
                    .setItems(list.toArray(new String[]{}), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }


    @OnClick({R.id.btn_gis_mlp, R.id.btn_gis_jlx, R.id.btn_select_readHouse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_readHouse:
                setList();
                break;
            case R.id.btn_gis_mlp:
                //不显示地图，直接自己建一个对象
                Log.d("wh", "定位门弄牌");
                mMapView = new MapView(ComingSHPeopleActivity.this, "");
                MyLocationOverlay myLocation = new MyLocationOverlay(ComingSHPeopleActivity.this, mMapView);
                myLocation.enableMyLocation(); //显示我的位置
                mMapView.addOverlay(myLocation);
                try {
                    //获取纬度
                    latitude = myLocation.getMyLocation().getLongitudeE6() * 1E6;
                    //获取经度
                    longitude = myLocation.getMyLocation().getLatitudeE6() * 1E6;
                } catch (Exception e) {
                    Log.d("wh", "异常");
                    e.getMessage();
                    e.printStackTrace();
                }
                index = 1;
                int code=  getDoorByGIS("121.59620806", " 31.12745559", scope, index + "");
//                getDoorByGIS(String.valueOf(latitude/1E6 ),String.valueOf(longitude/1E6),scope,index+"");
                if (code==1) {
                    getshowDoorList();
                }
                break;
            case R.id.btn_gis_jlx:
                //不显示地图，直接自己建一个对象
                mMapView = new MapView(mContext, "");
                MyLocationOverlay myLocationSteet = new MyLocationOverlay(this, mMapView);
                myLocationSteet.enableMyLocation(); //显示我的位置
                mMapView.addOverlay(myLocationSteet);
                try {
                    //获取纬度
                    latitude = myLocationSteet.getMyLocation().getLongitudeE6() * 1E6;
                    //获取经度
                    longitude = myLocationSteet.getMyLocation().getLatitudeE6() * 1E6;
                } catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                }

                //根据GIS查找街路巷信息
                houseModel.getStreetByGIS(longitude + "", latitude + "", scope, new RequestListener() {
                    @Override
                    public void onSuccess(Object o, String token) {
                        Log.d("wh", "人员采集" + longitude + "");
                        Log.d("wh", "人员采集" + latitude + "");
                        Log.d("wh", "人员采集" + scope);
                        String json = JSON.toJSONString(o);
                        List<StreetRoadAlley> streetRoadAlley = JSON.parseArray(json, StreetRoadAlley.class);
                        getshowStreetRoadAlleyList(streetRoadAlley);
                    }
                });
                break;

        }
    }

    private int getDoorByGIS(String gis_x, String gis_y, String distance, String index) {
        Log.d("wh", "gis_x=" + gis_x);
        Log.d("wh", "gis_y=" + gis_y);
        Log.d("wh", "distance=" + distance);
        Log.d("wh", "index=" + index);
        //通过GIS获取门弄牌列表
        houseModel.getDoorByGIS(gis_x + "", gis_y + "", distance, index, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                Log.d("wh", "通过GIS获取门弄牌列表");
                String json = JSON.toJSONString(o);
                Log.d("wh", "json" + json);
                List<Door> doors = JSON.parseArray(json, Door.class);
                ltdoors.clear();
                ltdoors.addAll(doors);
//                getshowDoorList();
                adapter.notifyDataSetChanged();
            }
        });
        return 1;
    }

}
