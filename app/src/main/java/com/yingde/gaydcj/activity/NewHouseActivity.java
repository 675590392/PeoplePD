package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.db.PeopleHandleWayDao;
import com.yingde.gaydcj.entity.PeopleAddShow;
import com.yingde.gaydcj.entity.StreetRoadAlley;
import com.yingde.gaydcj.entity.db.LABELS;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.util.Validation;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class NewHouseActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_add_submit_list)
    RecyclerView rvAddSubmitList;
    @BindView(R.id.btn_add_submit)
    Button btnAddSubmit;

    CommonAdapter adapter;
    private List<PeopleAddShow> peopleAddShow;
    private List<PeopleAddShow> peopleAddShowList;
    Bitmap bitmap;
    // 定义6个记录当前时间的变量
    private int query_year = 0;
    private int query_month = 0;
    private int query_day = 0;
    //声明mLocationOption对象
//    public AMapLocationClientOption mLocationOption = null;
//    AMapLocationClient mlocationClient;

    HouseModel houseModel;
    double latitude;//获取纬度
    double longitude;//获取经度
    //radiogroup选择
    private HashMap<Integer, String> hashMap = new HashMap<>();
    ;
    private HashMap<Integer, Integer> radioGroupMap = new HashMap<>();
    //时间选择
    private HashMap<Integer, String> time_map = new HashMap<>();
    //输入框
    private HashMap<Integer, String> edit_map = new HashMap<>();
    //拍照
    private HashMap<Integer, Bitmap> picture_map = new HashMap<>();
    //单选项选择带回来的值
    private HashMap<Integer, String> my_map = new HashMap<>();
    //单选项选择带回来的code
    private HashMap<Integer, String> code_map = new HashMap<>();
    //多选项选择带回来的值
    private HashMap<Integer, String> my_more_map = new HashMap<>();
    //多选项选择带回来的code
    private HashMap<Integer, String> code_more_map = new HashMap<>();
    //标识
    private HashMap<Integer, String> hashMapLabelCode = new HashMap<>();
    int pictureInt = 0;
    //数据库操作
    private PeopleHandleWayDao peopleHandleWayDao;
    //标识
    private List<LABELS> labelsList;
    CommonAdapter adapterLabel;
    private MapView mMapView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_new_house;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("新增房屋");
    }

    @Override
    protected void initData() {
        //不显示地图，直接自己建一个对象
        mMapView = new MapView(mContext, "");
        MyLocationOverlay myLocation = new MyLocationOverlay(this, mMapView);
        myLocation.enableMyLocation(); //显示我的位置
        mMapView.addOverlay(myLocation);
        try {
            //获取纬度
            latitude = myLocation.getMyLocation().getLongitudeE6() / 1E6;
            //获取经度
            longitude = myLocation.getMyLocation().getLatitudeE6() / 1E6;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        houseModel = new HouseModelIml(NewHouseActivity.this);
        if (peopleHandleWayDao == null) {
            peopleHandleWayDao = new PeopleHandleWayDao();
        }
        labelsList = new ArrayList<LABELS>();
        labelsList.clear();
        labelsList.addAll(peopleHandleWayDao.queryLABELSDictionaryHOUSELABLE());
//        initMap();

        queryStreetByGIS();

    }

    private void initAdapter(final String jlxdm, final String jlxmc) {
        peopleAddShow = new ArrayList<PeopleAddShow>();
        peopleAddShowList = new ArrayList<PeopleAddShow>();
        rvAddSubmitList.setLayoutManager(new LinearLayoutManager(NewHouseActivity.this));
        rvAddSubmitList.setItemAnimator(new DefaultItemAnimator());
        rvAddSubmitList.addItemDecoration(new DividerItemDecoration(NewHouseActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<PeopleAddShow>(NewHouseActivity.this, R.layout.listview_add_people_item, peopleAddShow) {
            @Override
            protected void convert(ViewHolder holder, final PeopleAddShow peopleAddShows, final int position) {
                //布局
                LinearLayout lin_add_item = holder.getView(R.id.lin_add_item);
                //*号
                TextView text_add_asterisk = holder.getView(R.id.text_add_asterisk);
                //选择框
                TextView textAddName = holder.getView(R.id.text_add_srname);
                //输入框
                final EditText editAddName = holder.getView(R.id.edit_add_srname);
                //图片
                ImageView imagAddName = holder.getView(R.id.imge_add_srname);
                TextView leftAddName = holder.getView(R.id.text_add_name);
                Button btnAddName = holder.getView(R.id.btn_add_name);
                //日期
                final TextView text_add_date = holder.getView(R.id.text_add_date);
                //是否
                LinearLayout line_radiogroup = holder.getView(R.id.line_radiogroup);
                RadioGroup radioGroup = holder.getView(R.id.radioGroup);
                final RadioButton radioButton1 = holder.getView(R.id.radioButton1);
                final RadioButton radioButton2 = holder.getView(R.id.radioButton2);
                //标识按钮
                TextView textAddSrLabel = holder.getView(R.id.text_add_srLabel);
                RecyclerView rvPersonLabelListItem = holder.getView(R.id.rv_add_more_select_list);


                //是否为必填项
                if (peopleAddShows.getFIELDMUST().equals("0")) {
                    text_add_asterisk.setVisibility(View.GONE);
                } else if (peopleAddShows.getFIELDMUST().equals("1")) {
                    text_add_asterisk.setVisibility(View.VISIBLE);
                }

                if (peopleAddShows.getFIELDTYPE().equals("01")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.VISIBLE);
                    leftAddName.setVisibility(View.VISIBLE);
                    imagAddName.setVisibility(View.GONE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    editAddName.setTag(position);
                    editAddName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!TextUtils.isEmpty(s.toString())) {
                                int index = (int) editAddName.getTag();
                                edit_map.put(index, s.toString());
                                peopleAddShow.get(index).setFIELDVALUE(s.toString());
                            }
                        }
                    });
                    String value = edit_map.get(position);
                    if (value == null) {
                        if (!TextUtils.isEmpty(peopleAddShows.getFIELDDEFAULTVALUE())) {
                            editAddName.setText(peopleAddShows.getFIELDDEFAULTVALUE());
                        }
                        if (peopleAddShows.getFIELDNAME().equals("JLXDM")) { //居住地门牌号
                            if (TextUtils.isEmpty(jlxdm)) {
                                editAddName.setText(jlxdm);
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("JLXMC")) { //居住地门牌号
                            if (TextUtils.isEmpty(jlxmc)) {
                                editAddName.setText(jlxmc);
                            }
//                            editAddName.setText(jlxmc);
                        }


                    }
                    String value2 = edit_map.get(position);
                    if (value2 != null) {
                        editAddName.setText(value2);
                    } else {
                        editAddName.setText("");
                        editAddName.setHint("请输入");
                    }

                } else if (peopleAddShows.getFIELDTYPE().equals("02")) {
                    textAddName.setVisibility(View.VISIBLE);
                    editAddName.setVisibility(View.GONE);
                    imagAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.VISIBLE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    if (!TextUtils.isEmpty(peopleAddShows.getFIELDDEFAULTVALUE())) {
                        textAddName.setText(peopleAddShows.getFIELDDEFAULTVALUE());
                    }
                    //是否从前面获取
//                    if (peopleAddShows.getAUTOFETCH().equals("1")) {


                    if (my_map.size() != 0) {
                        String name = my_map.get(position);
                        if (name != null) {
                            textAddName.setText(name);
                        } else {
                            if (peopleAddShows.getFIELDNAME().equals("JLXDM")) { //居住地门牌号
                                if (TextUtils.isEmpty(jlxdm)) {
                                    textAddName.setText(jlxdm);
                                } else {
                                    textAddName.setText("");
                                    textAddName.setHint("请选择");
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("JLXMC")) { //居住地门牌号
                                if (TextUtils.isEmpty(jlxmc)) {
                                    textAddName.setText(jlxmc);
                                } else {
                                    textAddName.setText("");
                                    textAddName.setHint("请选择");
                                }
                            } else {
                                textAddName.setText("");
                                textAddName.setHint("请选择");
                            }
                        }
                    } else {
                        if (peopleAddShows.getFIELDNAME().equals("JLXDM")) { //居住地门牌号
                            if (TextUtils.isEmpty(jlxdm)) {
                                textAddName.setText(jlxdm);
                            } else {
                                textAddName.setText("");
                                textAddName.setHint("请选择");
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("JLXMC")) { //居住地门牌号
                            if (TextUtils.isEmpty(jlxmc)) {
                                textAddName.setText(jlxmc);
                            } else {
                                textAddName.setText("");
                                textAddName.setHint("请选择");
                            }
                        } else {
                            textAddName.setText("");
                            textAddName.setHint("请选择");
                        }
                    }

                    textAddName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(NewHouseActivity.this,
                                    ComingSHPeopleAddSelectActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("fieldsource", peopleAddShows.getFIELDSOURCE());
                            startActivityForResult(intent, PeopleApplication.SELECT_SIMPLE);
                        }
                    });
                    if (code_map.size() != 0) {
                        String code = code_map.get(position);
                        if (code != null) {
                            peopleAddShow.get(position).setFIELDVALUE(code);
                        } else {
                            peopleAddShow.get(position).setFIELDVALUE(textAddName.getText().toString().trim());
                        }
                    } else {
                        peopleAddShow.get(position).setFIELDVALUE(textAddName.getText().toString().trim());
                    }
                } else if (peopleAddShows.getFIELDTYPE().equals("03")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.VISIBLE);
                    imagAddName.setVisibility(View.GONE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.VISIBLE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    radioButton1.setText("是");
                    radioButton2.setText("否");
//                    if (peopleAddShows.getFIELDDEFAULTVALUE().equals("1")) {
//                        radioButton1.setText("男");
//                    } else if (peopleAddShows.getFIELDDEFAULTVALUE().equals("2")) {
//                        radioButton2.setText("女");
//                    }
                    radioGroup.setTag(position);
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroups, int checkedId) {
                            int index = (int) radioGroups.getTag();
                            radioGroupMap.put(index, checkedId);
                            PeopleAddShow peopleAddShowStr = peopleAddShow.get(index);
                            switch (checkedId) {
                                case R.id.radioButton1:
                                    hashMap.put(index, radioButton1.getText().toString().trim());
                                    peopleAddShow.get(index).setFIELDVALUE("1");
                                    break;
                                case R.id.radioButton2:
                                    hashMap.put(index, radioButton2.getText().toString().trim());
                                    peopleAddShow.get(index).setFIELDVALUE("0");
                                    break;
                            }
                        }
                    });
                    if (radioGroupMap.get(position) != null) {
                        radioGroup.check(radioGroupMap.get(position));
                    } else {
                        radioGroup.clearCheck();
                    }
                } else if (peopleAddShows.getFIELDTYPE().equals("04")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.VISIBLE);
                    imagAddName.setVisibility(View.GONE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.VISIBLE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    if (!TextUtils.isEmpty(peopleAddShows.getFIELDDEFAULTVALUE())) {
                        text_add_date.setText(peopleAddShows.getFIELDDEFAULTVALUE());
                    }
                    text_add_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //日期选择
                            openTimeDialog(position, text_add_date);
                        }
                    });

                    String value = time_map.get(position);
                    if (value != null) {
                        text_add_date.setText(value);
                    } else {
                        text_add_date.setText("");
                        text_add_date.setHint("请选择日期");
                    }

                } else if (peopleAddShows.getFIELDTYPE().equals("05")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    imagAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.VISIBLE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.VISIBLE);
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));


                    getLABELS(position, rvPersonLabelListItem);

                } else if (peopleAddShows.getFIELDTYPE().equals("07")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.GONE);
                    imagAddName.setVisibility(View.VISIBLE);
                    btnAddName.setVisibility(View.VISIBLE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    btnAddName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pictureInt = position;
                            //拍照
//                            takePhoto();
                        }
                    });

                    Bitmap pictureValue = picture_map.get(position);
//                    if (bitmap != null) {
//                        imagAddName.setImageBitmap(bitmap);
//                    }
                    if (pictureValue != null) {
                        imagAddName.setImageBitmap(pictureValue);
                    } else {
                        imagAddName.setImageBitmap(null);
                    }
                } else if (peopleAddShows.getFIELDTYPE().equals("08")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.VISIBLE);
                    imagAddName.setVisibility(View.GONE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    rvPersonLabelListItem.setVisibility(View.GONE);
                    lin_add_item.setBackgroundColor(Color.parseColor("#B0E0E6"));
                    leftAddName.setTextColor(Color.parseColor("#FF0000"));
                }

                //1展示在界面上 0不展示在界面上
                if (peopleAddShows.getISSHOW().equals("0")) {
                    textAddName.setVisibility(View.GONE);
                    editAddName.setVisibility(View.GONE);
                    leftAddName.setVisibility(View.GONE);
                    imagAddName.setVisibility(View.GONE);
                    btnAddName.setVisibility(View.GONE);
                    line_radiogroup.setVisibility(View.GONE);
                    text_add_date.setVisibility(View.GONE);
                    textAddSrLabel.setVisibility(View.GONE);
                    text_add_asterisk.setVisibility(View.GONE);
                } //是否为必填项
                else if (peopleAddShows.getFIELDMUST().equals("0")) {
                    text_add_asterisk.setVisibility(View.GONE);
                } else if (peopleAddShows.getFIELDMUST().equals("1")) {
                    text_add_asterisk.setVisibility(View.VISIBLE);
                }
                //*号
                holder.setText(R.id.text_add_asterisk, "*");
                //左边名称
                holder.setText(R.id.text_add_name, peopleAddShows.getFIELDNOTE());
//                //右边输入框
//                holder.setText(R.id.edit_add_srname,"12341");

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        rvAddSubmitList.setAdapter(adapter);
        queryHouseAddList();
    }

    // 时间弹出框
    private void openTimeDialog(final int pos, final TextView text_add_date) {
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker =
                new DatePickerDialog(NewHouseActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String value = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
                        time_map.put(pos, value);
                        peopleAddShow.get(pos).setFIELDVALUE(value);
                        adapter.notifyDataSetChanged();
                    }
                }, query_year, query_month, query_day);
        datePicker.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单选
        if (data != null && resultCode == PeopleApplication.SELECT_SIMPLE_BACK) {
            int position = data.getIntExtra("position", -1);
            String name = data.getStringExtra("name");
            String code = data.getStringExtra("code");
            my_map.put(position, name);
            code_map.put(position, code);
            adapter.notifyDataSetChanged();
        }
    }

    //获取标识展示
    private void getLABELS(int labelPosition, RecyclerView rvAddMoreSelectList) {

        rvAddMoreSelectList.setLayoutManager(new LinearLayoutManager(NewHouseActivity.this));
        rvAddMoreSelectList.addItemDecoration(new DividerItemDecoration(NewHouseActivity.this, DividerItemDecoration.VERTICAL));
        adapterLabel = new CommonAdapter<LABELS>(NewHouseActivity.this, R.layout.listview_add_more_select_item, labelsList) {
            @Override
            protected void convert(ViewHolder holder, final LABELS comingSHPeopleAdd, final int position) {
                Log.d("wh", "适配测试");

                CheckBox textSelect = holder.getView(R.id.chebox_item_more_select);
                Log.d("wh", "选中状态" + textSelect.isChecked());

                //*号
                holder.setText(R.id.chebox_item_more_select, comingSHPeopleAdd.getNAME());
                if (hashMapLabelCode.size() != 0) {
                    //获取选择的编码
                    Iterator iteratorCode = hashMapLabelCode.keySet().iterator();
                    String datasCode = "";
                    while (iteratorCode.hasNext()) {
                        int id = (int) iteratorCode.next();
                        String strAnswer = hashMapLabelCode.get(id);
                        if (position == id) {
                            textSelect.setChecked(true);
                        }
                    }
                }
                textSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
//                            hashMapLabels.put(position, comingSHPeopleAdd.getNAME());
                            hashMapLabelCode.put(position, comingSHPeopleAdd.getDM());
                        } else {
//                            hashMapLabels.remove(position);
                            hashMapLabelCode.remove(position);
                        }
                    }
                });
            }
        };
        adapterLabel.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddMoreSelectList.setAdapter(adapterLabel);


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

    private void getshowDoorList(final List<StreetRoadAlley> doors) {
        //数据操作
        final List<String> list = new ArrayList<String>();
        if (null == doors) {
            initAdapter("", "");
        } else {
            if (doors.size() != 0) {
                Observable.from(doors)
                        .map(new Func1<StreetRoadAlley, String>() {
                            @Override
                            public String call(StreetRoadAlley door) {
                                return door.getJLXMC();
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                list.add(s);
                            }
                        });
//            mlocationClient.stopLocation();
                new AlertDialog.Builder(mContext)
                        .setTitle("街路巷名称")
                        .setItems(list.toArray(new String[]{}), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initAdapter(doors.get(which).getJLXDM(), doors.get(which).getJLXMC());
                            }
                        })
                        .show();
            } else {
                initAdapter("", "");
            }

        }
    }

    /**
     * 表单验证
     *
     * @return
     */
    public boolean isFrom() {
        for (int addPosition = 0; addPosition < peopleAddShow.size(); addPosition++) {
            if (hashMapLabelCode.size() != 0) {
                //获取选择的编码
                Iterator iteratorCode = hashMapLabelCode.keySet().iterator();
                String datasCode = "";
                while (iteratorCode.hasNext()) {
                    int id = (int) iteratorCode.next();
                    String strAnswer = hashMapLabelCode.get(id);
                    datasCode = strAnswer + "&" + datasCode;
                }
                //截取最后一个字符
                String datasLastCode = datasCode.substring(datasCode.length() - 1, datasCode.length());
                String datasStrCode = null;
                if (datasLastCode.equals("&")) {
                    // 最后一个字符被截取掉
                    datasStrCode = datasCode.substring(0, datasCode.length() - 1);
                }
                if (peopleAddShow.get(addPosition).getFIELDNAME().equals("BIAOQIANCODE")) {
                    peopleAddShow.get(addPosition).setFIELDVALUE(datasStrCode);
                }

            }
            if (peopleAddShow.get(addPosition).getFIELDMUST().equals("1")) {
                if (peopleAddShow.get(addPosition).getFIELDNAME().equals("FZZJHM")) { //公民身份证号码
                    if (!Validation.isIdCard(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                        ToastUtil.showToast(NewHouseActivity.this, "请检查房主身份证号码是否有误");
                        return false;
                    }
                } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("FZLXDH")) { //联系电话
                    if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                        if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                                && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                            ToastUtil.showToast(NewHouseActivity.this, "请检查房主联系电话是否有误");
                            return false;
                        }
                    } else {
                        ToastUtil.showToast(NewHouseActivity.this, "请输入房主联系电话");
                        return false;
                    }
                } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("ZAZRRLXDH")) { //手机
                    if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                        if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                                && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                            ToastUtil.showToast(NewHouseActivity.this, "请检查治安责任人联系电话是否有误");
                            return false;
                        }

                    } else {
                        ToastUtil.showToast(NewHouseActivity.this, "请输入治安责任人联系电话");
                        return false;
                    }
                } else if (TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    ToastUtil.showToast(NewHouseActivity.this, peopleAddShow.get(addPosition).getFIELDCNNAME() + "未输入");
                    return false;
                }
            } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("FZLXDH")) { //联系电话
                if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                            && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                        ToastUtil.showToast(NewHouseActivity.this, "请检查房主联系电话是否有误");
                        return false;
                    }
                }
            } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("ZAZRRLXDH")) { //手机
                if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                            && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                        ToastUtil.showToast(NewHouseActivity.this, "请检查治安责任人联系电话是否有误");
                        return false;
                    }

                }
            }
        }
        return true;
    }

    /**
     * 获取房屋采集项目字段列表
     */
    private void queryHouseAddList() {
        houseModel.getHouseAddList(new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                peopleAddShow.clear();
                peopleAddShow.addAll(JSON.parseArray(json, PeopleAddShow.class));
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 根据GIS获取街弄巷信息
     */
    private void queryStreetByGIS() {
//        String longitude = "121.584991";
//        String latitude = "31.150048";
        houseModel.getStreetByGIS(longitude + "", latitude + "", "10", new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                List<StreetRoadAlley> streetRoadAlley = JSON.parseArray(json, StreetRoadAlley.class);
                getshowDoorList(streetRoadAlley);
            }
        });
    }

    @OnClick(R.id.btn_add_submit)
    public void onViewClicked() {
        if (peopleAddShow == null) {
            ToastUtil.showToast(mContext, "请填写数据!!!");
            return;
        }
        // 表单验证
        if (!isFrom()) {
            return;
        }
        if (null != peopleAddShow) {
            peopleAddShowList.clear();
            for (int i = 0; i < peopleAddShow.size(); i++) {
                peopleAddShowList.add(peopleAddShow.get(i));
            }
        }
        for (int addPosition = 0; addPosition < peopleAddShowList.size(); addPosition++) {
            if (peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_BASEINFO")) {
                peopleAddShowList.remove(addPosition);
            }
            if (peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_ASSISTINFO")) {
                peopleAddShowList.remove(addPosition);
            }
            if (peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_LABELINFO")) {
                peopleAddShowList.remove(addPosition);
            }
        }

        //提交
        houseModel.getAddHouse(peopleAddShowList, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
//                String json = JSON.toJSONString(o);
                ToastUtil.showToast(mContext, "数据提交成功");
                finish();
            }
        });


    }
}
