package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.db.PeopleHandleWayDao;
import com.yingde.gaydcj.entity.DownloadPictureJG;
import com.yingde.gaydcj.entity.PeopleAddShow;
import com.yingde.gaydcj.entity.PersonLabel;
import com.yingde.gaydcj.entity.User;
import com.yingde.gaydcj.entity.db.D_JCW;
import com.yingde.gaydcj.entity.db.D_JZ;
import com.yingde.gaydcj.entity.db.D_MZ;
import com.yingde.gaydcj.entity.db.D_PCS;
import com.yingde.gaydcj.entity.db.D_WDDM;
import com.yingde.gaydcj.entity.db.D_XGY;
import com.yingde.gaydcj.entity.db.LABELS;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.UserModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.util.IdCardToInfo;
import com.yingde.gaydcj.util.ImageCompressUtil;
import com.yingde.gaydcj.util.MyDateUtil;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.SharedPreferencesUtil;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.util.Validation;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.senter.model.IdentityCardZ;

import static cn.com.senter.sdkdefault.helper.a.i;

/**
 * 人口居住登记表
 */
public class ComingSHPeopleAddActivity extends TakePhotoActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_add_submit_list)
    RecyclerView rvAddSubmitList;
    //    @BindView(R.id.rv_person_label_list)
//    RecyclerView rvPersonLabelList;
    @BindView(R.id.btn_add_submit)
    Button btnAddSubmit;

    CommonAdapter adapter;

    private List<PeopleAddShow> peopleAddShow;
    private List<PeopleAddShow> peopleAddShowList;
    private List<DownloadPictureJG> downloadPictureJG;
    //人房标识
    private List<PersonLabel> personLabel;
    String base64Str;
    //单选项选择带回来的值
    private HashMap<Integer, String> my_map = new HashMap<>();
    //单选项选择带回来的code
    private HashMap<Integer, String> code_map = new HashMap<>();
    //多选项选择带回来的值
    private HashMap<Integer, String> my_more_map = new HashMap<>();
    //多选项选择带回来的code
    private HashMap<Integer, String> code_more_map = new HashMap<>();
    HouseModel houseModel;
    UserModel userModel;
    // 定义6个记录当前时间的变量
    private int query_year = 0;
    private int query_month = 0;
    private int query_day = 0;
    private String regcode;
    private String fwbm;
    private String selectChidcard;
    private String selectChname;
    private IdentityCardZ idCard = null;

    //身份证获取生日年龄等
    private String sex;//性别
    private int age;//年龄
    private String nian;//年
    private String yue;//月
    private String ri;//日
    private String sr;//生日

    //    居住地门牌号
    private String edtSelectChshliveraddr;
    LinearLayout linearPeopleLabel;
    //radiogroup选择
    private HashMap<Integer, String> hashMap = new HashMap<>();
    private HashMap<Integer, Integer> radioGroupMap = new HashMap<>();
    //时间选择
    private HashMap<Integer, String> time_map = new HashMap<>();
    //时间转成  MMMMYYRR
    private HashMap<Integer, String> time_date_map = new HashMap<>();
    //输入框
    private HashMap<Integer, String> edit_map = new HashMap<>();
    //拍照
    private HashMap<Integer, Bitmap> picture_map = new HashMap<>();
    //标识
//    private HashMap<Integer, String> hashMapLabel = new HashMap<>();
    //标识
//    private HashMap<Integer, String> hashMapLabels = new HashMap<>();
    private HashMap<Integer, String> hashMapLabelCode = new HashMap<>();
    private int[] tagPosition = new int[]{};
    int pictureInt = 0;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    //用戶登錄信息
    User user;
    //数据库操作
    private PeopleHandleWayDao peopleHandleWayDao;
    //协管员
    private List<D_XGY> xgyId;
    //派出所
    private List<D_PCS> pcsId;
    //街镇
    private List<D_JZ> jzId;
    //居村委
    private List<D_JCW> jcwId;
    //网点
    private List<D_WDDM> wdId;
    //民族
    private List<D_MZ> mzId;
    //标识
    private List<LABELS> labelsList;

    CommonAdapter adapterLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_shpeople_add);
        ButterKnife.bind(this);
        if (toolbar != null) {
            initTitle();
        }
        if (peopleHandleWayDao == null) {
            peopleHandleWayDao = new PeopleHandleWayDao();
        }
        labelsList = new ArrayList<LABELS>();
        labelsList.clear();
        labelsList.addAll(peopleHandleWayDao.queryLABELSDictionaryPERSONLABLE());
        initData();
    }
    //切换点击事件
    private void initData() {

        if (getIntent().getExtras() != null) {
            regcode = getIntent().getExtras().getString("regcode", "");
            fwbm = getIntent().getExtras().getString("fwbm", "");
            edtSelectChshliveraddr = getIntent().getStringExtra("edtSelectChshliveraddr");
            selectChidcard = getIntent().getExtras().getString("selectChidcard", "");
            selectChname = getIntent().getStringExtra("selectChname");
            Bundle bun = this.getIntent().getExtras();
            idCard = (IdentityCardZ) bun.getSerializable("idCard");
        }

        if (!TextUtils.isEmpty(selectChidcard)) {
            sex = IdCardToInfo.getGenderByIdCard(selectChidcard);
            age = IdCardToInfo.getAgeByIdCard(selectChidcard);
            nian = IdCardToInfo.getYearByIdCard(selectChidcard);
            yue = IdCardToInfo.getMonthByIdCard(selectChidcard);
            ri = IdCardToInfo.getDateByIdCard(selectChidcard);
            sr = IdCardToInfo.getBirthByIdCard(selectChidcard);
        }
        houseModel = new HouseModelIml(ComingSHPeopleAddActivity.this);
        userModel = new UserModelIml(ComingSHPeopleAddActivity.this);
        user = CommonUtil.getUserEntity(ComingSHPeopleAddActivity.this);
        if (null != user) {
            if (!TextUtils.isEmpty(user.getUSERCODE())) {
                //协管员
                xgyId = peopleHandleWayDao.queryD_XGYDictionaryToDM(user.getUSERCODE());
            }
            if (!TextUtils.isEmpty(user.getUSERCODE())) {
                //派出所
                pcsId = peopleHandleWayDao.queryD_PCSDictionaryToDM(user.getPOLICEID());
            }
            if (!TextUtils.isEmpty(user.getUSERCODE())) {
                //街镇
                jzId = peopleHandleWayDao.queryD_JZDictionaryToDM(user.getJZDM());
            }
            if (!TextUtils.isEmpty(user.getUSERCODE())) {
                //居村委
                jcwId = peopleHandleWayDao.queryD_JCWDictionaryToDM(user.getJCWDM());
            }
            if (!TextUtils.isEmpty(user.getUSERCODE())) {
                //网点
                wdId = peopleHandleWayDao.queryD_WDDMDictionaryToDM(user.getAPPLATTICE());
            }
        }
        if(null!=idCard){
            if(TextUtils.isEmpty(idCard.ethnicity)){
                mzId=  peopleHandleWayDao.queryD_MZDictionaryToDM(idCard.ethnicity);
            }
        }
        // 人员采集列表
        addSubmitList();


    }

    // 人员采集列表
    private void addSubmitList() {
        peopleAddShow = new ArrayList<PeopleAddShow>();
        peopleAddShowList=new ArrayList<PeopleAddShow>();
        rvAddSubmitList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddActivity.this));
        rvAddSubmitList.setItemAnimator(new DefaultItemAnimator());
        rvAddSubmitList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<PeopleAddShow>(ComingSHPeopleAddActivity.this, R.layout.listview_add_people_item, peopleAddShow) {
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
                        //是否从前面获取
//                        if (peopleAddShows.getAUTOFETCH().equals("1")) {

                        if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEADDR")) { //居住地门牌号
                            if (!TextUtils.isEmpty(edtSelectChshliveraddr)) {
                                editAddName.setText(edtSelectChshliveraddr);
                                peopleAddShow.get(position).setFIELDVALUE(edtSelectChshliveraddr);
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEJUWEI")) {  //居住地居村委

                            if (jcwId.size() == 0) {
                            } else {
                                editAddName.setText(jcwId.get(0).getJCWMC());
                                peopleAddShow.get(position).setFIELDVALUE(jcwId.get(0).getJCWDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVESTREET")) { // 居住地所属街镇
                            if (jzId.size() == 0) {
                            } else {
                                editAddName.setText(jzId.get(0).getJZMC());
                                peopleAddShow.get(position).setFIELDVALUE(jzId.get(0).getJZDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEAREA")) {//居住地所在区县
                            if (null != user) {
                                if (!TextUtils.isEmpty(user.getCOUNTYID())) {
                                    if (user.getCOUNTYID().equals("15")) {
                                        editAddName.setText("浦东新区");
                                        peopleAddShow.get(position).setFIELDVALUE(user.getCOUNTYID());
                                    }
                                }
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("APPLATTICE")) { //受理网点

                            if (wdId.size() == 0) {
                            } else {
                                editAddName.setText(wdId.get(0).getMC());
                                peopleAddShow.get(position).setFIELDVALUE(wdId.get(0).getDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("POLICEID")) {  //所属派出所代码
                            if (pcsId.size() == 0) {
                            } else {
                                editAddName.setText(pcsId.get(0).getPCSMC());
                                peopleAddShow.get(position).setFIELDVALUE(pcsId.get(0).getPCS6());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("FWBM")) {  //房屋编码
                            if (!TextUtils.isEmpty(fwbm)) {
                                editAddName.setText(fwbm);
                                peopleAddShow.get(position).setFIELDVALUE(fwbm);
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("XGYID")) {  //协管员id
                            if (xgyId.size() == 0) {
                            } else {
                                editAddName.setText(xgyId.get(0).getMC());
                                peopleAddShow.get(position).setFIELDVALUE(xgyId.get(0).getDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHNAME")) {  //姓名
                            if(null==idCard){
                                if (!TextUtils.isEmpty(selectChname)) {
                                    editAddName.setText(selectChname);
                                    peopleAddShow.get(position).setFIELDVALUE(selectChname);
                                    editAddName.setEnabled(false);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.name)){
                                    editAddName.setText(idCard.name);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.name);
                                    editAddName.setEnabled(false);
                                }
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHIDCARD")) {  //公民身份证号码
                            if(null==idCard){
                                if (!TextUtils.isEmpty(selectChidcard)) {
                                    editAddName.setText(selectChidcard);
                                    peopleAddShow.get(position).setFIELDVALUE(selectChidcard);
                                    editAddName.setEnabled(false);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.cardNo)){
                                    editAddName.setText(idCard.cardNo);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.cardNo);
                                    editAddName.setEnabled(false);
                                }
                            }


                        } else if (peopleAddShows.getFIELDNAME().equals("CHSEX")) {//性别
                            if(null==idCard){
                                if (!TextUtils.isEmpty(sex)) {
                                    editAddName.setText(sex);
                                    peopleAddShow.get(position).setFIELDVALUE(sex);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.sex)){
                                    editAddName.setText(idCard.sex);
                                    if(idCard.sex.equals("男")){
                                        peopleAddShow.get(position).setFIELDVALUE("1");
                                    }else if(idCard.sex.equals("女")){
                                        peopleAddShow.get(position).setFIELDVALUE("2");
                                    }
                                }
                            }

                        } else if (peopleAddShows.getFIELDNAME().equals("DTBIRTHDAY")) {//出生日期
                            if(null==idCard){
                                if (!TextUtils.isEmpty(nian) && !TextUtils.isEmpty(yue) && !TextUtils.isEmpty(ri)) {
                                    editAddName.setText(nian + "年" + yue + "月" + ri + "日");
                                    peopleAddShow.get(position).setFIELDVALUE(nian + yue + ri);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.birth)){
                                    editAddName.setText(idCard.birth);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.birth);
                                }
                            }

                        }else if(peopleAddShows.getFIELDNAME().equals("CHFOLK")){//民族
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.ethnicity)){
                                    editAddName.setText(idCard.ethnicity);
                                    if(mzId.size()!=0){
                                        peopleAddShow.get(position).setFIELDVALUE(mzId.get(0).getDM());
                                    }
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("HJADDR")){//户籍所在地详址
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.address)){
                                    editAddName.setText(idCard.address);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.address);
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("QFJG")){//签发机关
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.authority)){
                                    editAddName.setText(idCard.authority);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.authority);
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("YXQX")){//有效日期
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.period)){
                                    editAddName.setText(idCard.period);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.period);
                                }
                            }
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
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    if (!TextUtils.isEmpty(peopleAddShows.getFIELDDEFAULTVALUE())) {
                        if(peopleAddShows.getFIELDNAME().equals("CHFOLK")){//民族
                            List<D_MZ> mzIds=  peopleHandleWayDao.queryD_MZDictionaryToMC(peopleAddShows.getFIELDDEFAULTVALUE());
                            if(mzIds.size()!=0){
                                textAddName.setText(mzIds.get(0).getMC());
                                peopleAddShow.get(position).setFIELDVALUE(mzIds.get(0).getDM());}
                        }else{
                            textAddName.setText(peopleAddShows.getFIELDDEFAULTVALUE());}
                    }
                    if (my_map.size() != 0) {
                        String name = my_map.get(position);
                        if (name != null) {
                            textAddName.setText(name);
                        } else {
//                            textAddName.setText("");
//                            textAddName.setHint("请选择");
                            if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEADDR")) { //居住地门牌号
                                if (!TextUtils.isEmpty(edtSelectChshliveraddr)) {
                                    textAddName.setText(edtSelectChshliveraddr);
                                    peopleAddShow.get(position).setFIELDVALUE(edtSelectChshliveraddr);
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEJUWEI")) {  //居住地居村委

                                if (jcwId.size() == 0) {
                                } else {
                                    textAddName.setText(jcwId.get(0).getJCWMC());
                                    peopleAddShow.get(position).setFIELDVALUE(jcwId.get(0).getJCWDM());
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVESTREET")) { // 居住地所属街镇

                                if (jzId.size() == 0) {
                                } else {
                                    textAddName.setText(jzId.get(0).getJZMC());
                                    peopleAddShow.get(position).setFIELDVALUE(jzId.get(0).getJZDM());
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEAREA")) {//居住地所在区县
                                if (null != user) {
                                    if (!TextUtils.isEmpty(user.getCOUNTYID())) {
                                        if (user.getCOUNTYID().equals("15")) {
                                            textAddName.setText("浦东新区");
                                            peopleAddShow.get(position).setFIELDVALUE(user.getCOUNTYID());
                                        }
                                    }
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("APPLATTICE")) { //受理网点

                                if (wdId.size() == 0) {
                                } else {
                                    textAddName.setText(wdId.get(0).getMC());
                                    peopleAddShow.get(position).setFIELDVALUE(wdId.get(0).getDM());
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("POLICEID")) {  //所属派出所代码
                                if (pcsId.size() == 0) {
                                } else {
                                    textAddName.setText(pcsId.get(0).getPCSMC());
                                    peopleAddShow.get(position).setFIELDVALUE(pcsId.get(0).getPCS6());
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("FWBM")) {  //房屋编码
                                if (!TextUtils.isEmpty(fwbm)) {
                                    textAddName.setText(fwbm);
                                    peopleAddShow.get(position).setFIELDVALUE(fwbm);
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("XGYID")) {  //协管员id
                                if (xgyId.size() == 0) {
                                } else {
                                    textAddName.setText(xgyId.get(0).getMC());
                                    peopleAddShow.get(position).setFIELDVALUE(xgyId.get(0).getDM());
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("CHNAME")) {  //姓名
                                if(null==idCard){
                                    if (!TextUtils.isEmpty(selectChname)) {
                                        editAddName.setText(selectChname);
                                        peopleAddShow.get(position).setFIELDVALUE(selectChname);
                                        editAddName.setEnabled(false);
                                    }
                                }else{
                                    if(!TextUtils.isEmpty(idCard.name)){
                                        editAddName.setText(idCard.name);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.name);
                                        editAddName.setEnabled(false);
                                    }
                                }
                            } else if (peopleAddShows.getFIELDNAME().equals("CHIDCARD")) {  //公民身份证号码
                                if(null==idCard){
                                    if (!TextUtils.isEmpty(selectChidcard)) {
                                        editAddName.setText(selectChidcard);
                                        peopleAddShow.get(position).setFIELDVALUE(selectChidcard);
                                        editAddName.setEnabled(false);
                                    }
                                }else{
                                    if(!TextUtils.isEmpty(idCard.cardNo)){
                                        editAddName.setText(idCard.cardNo);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.cardNo);
                                        editAddName.setEnabled(false);
                                    }
                                }


                            } else if (peopleAddShows.getFIELDNAME().equals("CHSEX")) {//性别
                                if(null==idCard){
                                    if (!TextUtils.isEmpty(sex)) {
                                        editAddName.setText(sex);
                                        peopleAddShow.get(position).setFIELDVALUE(sex);
                                    }
                                }else{
                                    if(!TextUtils.isEmpty(idCard.sex)){
                                        editAddName.setText(idCard.sex);
                                        if(idCard.sex.equals("男")){
                                            peopleAddShow.get(position).setFIELDVALUE("1");
                                        }else if(idCard.sex.equals("女")){
                                            peopleAddShow.get(position).setFIELDVALUE("2");
                                        }
                                    }
                                }

                            } else if (peopleAddShows.getFIELDNAME().equals("DTBIRTHDAY")) {//出生日期
                                if(null==idCard){
                                    if (!TextUtils.isEmpty(nian) && !TextUtils.isEmpty(yue) && !TextUtils.isEmpty(ri)) {
                                        editAddName.setText(nian + "年" + yue + "月" + ri + "日");
                                        peopleAddShow.get(position).setFIELDVALUE(nian + yue + ri);
                                    }
                                }else{
                                    if(!TextUtils.isEmpty(idCard.birth)){
                                        editAddName.setText(idCard.birth);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.birth);
                                    }
                                }

                            }else if(peopleAddShows.getFIELDNAME().equals("CHFOLK")){//民族
                                if(null!=idCard){
                                    if(!TextUtils.isEmpty(idCard.ethnicity)){
                                        editAddName.setText(idCard.ethnicity);
                                        if(mzId.size()!=0){
                                            peopleAddShow.get(position).setFIELDVALUE(mzId.get(0).getDM());
                                        }
                                    }
                                }
                            }else if(peopleAddShows.getFIELDNAME().equals("HJADDR")){//户籍所在地详址
                                if(null!=idCard){
                                    if(!TextUtils.isEmpty(idCard.address)){
                                        editAddName.setText(idCard.address);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.address);
                                    }
                                }
                            }else if(peopleAddShows.getFIELDNAME().equals("QFJG")){//签发机关
                                if(null!=idCard){
                                    if(!TextUtils.isEmpty(idCard.authority)){
                                        editAddName.setText(idCard.authority);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.authority);
                                    }
                                }
                            }else if(peopleAddShows.getFIELDNAME().equals("YXQX")){//有效日期
                                if(null!=idCard){
                                    if(!TextUtils.isEmpty(idCard.period)){
                                        editAddName.setText(idCard.period);
                                        peopleAddShow.get(position).setFIELDVALUE(idCard.period);
                                    }
                                }
                            }else {
                                textAddName.setText("");
                                textAddName.setHint("请选择");
                            }
                        }
                    } else {
                        if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEADDR")) { //居住地门牌号
                            if (!TextUtils.isEmpty(edtSelectChshliveraddr)) {
                                textAddName.setText(edtSelectChshliveraddr);
                                peopleAddShow.get(position).setFIELDVALUE(edtSelectChshliveraddr);
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEJUWEI")) {  //居住地居村委

                            if (jcwId.size() == 0) {
                            } else {
                                textAddName.setText(jcwId.get(0).getJCWMC());
                                peopleAddShow.get(position).setFIELDVALUE(jcwId.get(0).getJCWDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVESTREET")) { // 居住地所属街镇

                            if (jzId.size() == 0) {
                            } else {
                                textAddName.setText(jzId.get(0).getJZMC());
                                peopleAddShow.get(position).setFIELDVALUE(jzId.get(0).getJZDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHSHLIVEAREA")) {//居住地所在区县
                            if (null != user) {
                                if (!TextUtils.isEmpty(user.getCOUNTYID())) {
                                    if (user.getCOUNTYID().equals("15")) {
                                        textAddName.setText("浦东新区");
                                        peopleAddShow.get(position).setFIELDVALUE(user.getCOUNTYID());
                                    }
                                }
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("APPLATTICE")) { //受理网点

                            if (wdId.size() == 0) {
                            } else {
                                textAddName.setText(wdId.get(0).getMC());
                                peopleAddShow.get(position).setFIELDVALUE(wdId.get(0).getDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("POLICEID")) {  //所属派出所代码
                            if (pcsId.size() == 0) {
                            } else {
                                textAddName.setText(pcsId.get(0).getPCSMC());
                                peopleAddShow.get(position).setFIELDVALUE(pcsId.get(0).getPCS6());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("FWBM")) {  //房屋编码
                            if (!TextUtils.isEmpty(fwbm)) {
                                textAddName.setText(fwbm);
                                peopleAddShow.get(position).setFIELDVALUE(fwbm);
                            }

                        } else if (peopleAddShows.getFIELDNAME().equals("XGYID")) {  //协管员id
                            if (xgyId.size() == 0) {
                            } else {
                                textAddName.setText(xgyId.get(0).getMC());
                                peopleAddShow.get(position).setFIELDVALUE(xgyId.get(0).getDM());
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHNAME")) {  //姓名
                            if(null==idCard){
                                if (!TextUtils.isEmpty(selectChname)) {
                                    textAddName.setText(selectChname);
                                    peopleAddShow.get(position).setFIELDVALUE(selectChname);
                                    textAddName.setEnabled(false);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.name)){
                                    textAddName.setText(idCard.name);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.name);
                                    textAddName.setEnabled(false);
                                }
                            }
                        } else if (peopleAddShows.getFIELDNAME().equals("CHIDCARD")) {  //公民身份证号码
                            if(null==idCard){
                                if (!TextUtils.isEmpty(selectChidcard)) {
                                    textAddName.setText(selectChidcard);
                                    peopleAddShow.get(position).setFIELDVALUE(selectChidcard);
                                    textAddName.setEnabled(false);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.cardNo)){
                                    textAddName.setText(idCard.cardNo);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.cardNo);
                                    textAddName.setEnabled(false);
                                }
                            }


                        } else if (peopleAddShows.getFIELDNAME().equals("CHSEX")) {//性别
                            if(null==idCard){
                                if (!TextUtils.isEmpty(sex)) {
                                    textAddName.setText(sex);
                                    peopleAddShow.get(position).setFIELDVALUE(sex);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.sex)){
                                    textAddName.setText(idCard.sex);
                                    if(idCard.sex.equals("男")){
                                        peopleAddShow.get(position).setFIELDVALUE("1");
                                    }else if(idCard.sex.equals("女")){
                                        peopleAddShow.get(position).setFIELDVALUE("2");
                                    }
                                }
                            }

                        } else if (peopleAddShows.getFIELDNAME().equals("DTBIRTHDAY")) {//出生日期
                            if(null==idCard){
                                if (!TextUtils.isEmpty(nian) && !TextUtils.isEmpty(yue) && !TextUtils.isEmpty(ri)) {
                                    textAddName.setText(nian + "年" + yue + "月" + ri + "日");
                                    peopleAddShow.get(position).setFIELDVALUE(nian + yue + ri);
                                }
                            }else{
                                if(!TextUtils.isEmpty(idCard.birth)){
                                    textAddName.setText(idCard.birth);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.birth);
                                }
                            }

                        }else if(peopleAddShows.getFIELDNAME().equals("CHFOLK")){//民族
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.ethnicity)){
                                    textAddName.setText(idCard.ethnicity);
                                    if(mzId.size()!=0){
                                        peopleAddShow.get(position).setFIELDVALUE(mzId.get(0).getDM());
                                    }
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("HJADDR")){//户籍所在地详址
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.address)){
                                    textAddName.setText(idCard.address);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.address);
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("QFJG")){//签发机关
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.authority)){
                                    textAddName.setText(idCard.authority);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.authority);
                                }
                            }
                        }else if(peopleAddShows.getFIELDNAME().equals("YXQX")){//有效日期
                            if(null!=idCard){
                                if(!TextUtils.isEmpty(idCard.period)){
                                    textAddName.setText(idCard.period);
                                    peopleAddShow.get(position).setFIELDVALUE(idCard.period);
                                }
                            }
                        } else {
                            textAddName.setText("");
                            textAddName.setHint("请选择");
                        }
                    }

                    textAddName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ComingSHPeopleAddActivity.this,
                                    ComingSHPeopleAddSelectActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("fieldsource", peopleAddShows.getFIELDSOURCE().toString().trim());
                            startActivityForResult(intent, PeopleApplication.SELECT_SIMPLE);
                        }
                    });
                    if (code_map.size() != 0) {
                        String code = code_map.get(position);
                        if (code != null) {
                            peopleAddShow.get(position).setFIELDVALUE(code);
                        }
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
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    if (peopleAddShows.getFIELDNAME().equals("CHSEX")) {
                        radioButton1.setText("男");
                        radioButton2.setText("女");
                    } else {
                        radioButton1.setText("是");
                        radioButton2.setText("否");
                    }

                    if (peopleAddShows.getFIELDNAME().equals("CHSEX")) {//性别
                        if(null==idCard){
                            if (!TextUtils.isEmpty(sex)) {
                                if (sex.equals("1")) {
                                    radioButton1.setChecked(true);
                                    peopleAddShow.get(position).setFIELDVALUE(sex);
                                } else if (sex.equals("2")) {
                                    radioButton2.setChecked(true);
                                    peopleAddShow.get(position).setFIELDVALUE(sex);
                                }

                            }
                        }else{
                            if(!TextUtils.isEmpty(idCard.sex)){
                                if(idCard.sex.equals("男")){
                                    radioButton1.setChecked(true);
                                    peopleAddShow.get(position).setFIELDVALUE("1");
                                }else if(idCard.sex.equals("女")){
                                    radioButton2.setChecked(true);
                                    peopleAddShow.get(position).setFIELDVALUE("2");
                                }
                            }
                        }

                    }
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
                                    peopleAddShow.get(index).setFIELDVALUE("2");
                                    break;
                            }
                        }
                    });
                    if (radioGroupMap.get(position) != null) {
                        radioGroup.check(radioGroupMap.get(position));
                    } else {
                        if (!radioButton1.isChecked() && !radioButton2.isChecked()) {
                            radioGroup.clearCheck();
                        }
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
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    if (!TextUtils.isEmpty(peopleAddShows.getFIELDDEFAULTVALUE())) {

                        text_add_date.setText(peopleAddShows.getFIELDDEFAULTVALUE());
                    }
                    if (peopleAddShows.getFIELDNAME().equals("DTBIRTHDAY")) {//出生日期
                        if(null==idCard){
                            if (!TextUtils.isEmpty(nian) && !TextUtils.isEmpty(yue) && !TextUtils.isEmpty(ri)) {
                                text_add_date.setText(nian + "年" + yue + "月" + ri + "日");
                            }
                        }else{
                            if(!TextUtils.isEmpty(idCard.birth)){
                                text_add_date.setText(idCard.birth);
                            }
                        }

                    }else  if(peopleAddShows.getFIELDNAME().equals("YXQX")){
                        if(null!=idCard){
                            if(!TextUtils.isEmpty(idCard.period)){
                                text_add_date.setText(idCard.period);
                            } }
                    }

                    if(peopleAddShows.getFIELDNAME().equals("YXQX")){
                        text_add_date.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //日期选择
                                openTimeDialogToMore(position, text_add_date);
                            }
                        });
                        //日期展示到控件上
                        String value = time_map.get(position);
                        if (value != null) {
                            text_add_date.setText(value);
                        } else {
                            if (!TextUtils.isEmpty(text_add_date.getText().toString().trim())) {
                                peopleAddShow.get(position).setFIELDVALUE(text_add_date.getText().toString().trim());
                            }else{
                                text_add_date.setText("");
                                text_add_date.setHint("请选择日期");
                            }
                        }
                    }else{
                        text_add_date.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //日期选择
                                openTimeDialog(position, text_add_date);
                            }
                        });
                        //日期展示到控件上
                        String value = time_map.get(position);
                        if (value != null) {
                            text_add_date.setText(value);
                        } else {
                            if (TextUtils.isEmpty(text_add_date.getText().toString().trim())) {
                                text_add_date.setText("");
                                text_add_date.setHint("请选择日期");
                            }
                        }

                        //取时间戳
                        String valueDate = time_date_map.get(position);
                        if (valueDate != null) {
                            peopleAddShow.get(position).setFIELDVALUE(valueDate);
                        } else {
                            if(!peopleAddShows.getFIELDNAME().equals("YXQX")){
                                if (!TextUtils.isEmpty(text_add_date.getText().toString().trim())) {
                                    String valueDateStr = MyDateUtil.getMyDateb(text_add_date.getText().toString().trim());
                                    peopleAddShow.get(position).setFIELDVALUE(valueDateStr);
                                }}
                        }
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
                    leftAddName.setTextColor(Color.parseColor("#292421"));
                    lin_add_item.setBackgroundColor(Color.parseColor("#FFFAFA"));
                    btnAddName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pictureInt = position;
                            //拍照
                            takePhoto();
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
        queryPeopleAddList();
    }


    // 时间弹出框
    private void openTimeDialog(final int pos, final TextView text_add_date) {
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker =
                new DatePickerDialog(ComingSHPeopleAddActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String value = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
                        String valueDate = MyDateUtil.getMyDateb(value);
                        time_map.put(pos, value);
                        time_date_map.put(pos, valueDate);
//                        peopleAddShow.get(pos).setFIELDVALUE(valueDate);
                        adapter.notifyDataSetChanged();
                    }
                }, query_year, query_month, query_day);
        datePicker.show();
    }
    // 时间弹出框  开始时间-结束时间
    private void openTimeDialogToMore(final int pos, final TextView text_add_date) {
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);
        final DatePicker startTime = (DatePicker) view.findViewById(R.id.st);
        final DatePicker endTime = (DatePicker) view.findViewById(R.id.et);
        startTime.updateDate(startTime.getYear(), startTime.getMonth(), 01);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int month = startTime.getMonth() + 1;
                String st = startTime.getYear() + "年" + month + "月" + startTime.getDayOfMonth() + "日";
                int month1 = endTime.getMonth() + 1;
                String et = endTime.getYear()+ "年" +month1 + "月" + endTime.getDayOfMonth() + "日";
                String value = st+"-"+et;
                time_map.put(pos, value);
                peopleAddShow.get(pos).setFIELDVALUE(value);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();

        dialog.show();
        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        } else if (data != null && resultCode == PeopleApplication.SELECT_MORE_BACK) {//多选
            int position = data.getIntExtra("position", -1);
            String nameMore = data.getStringExtra("name");
            String codeMore = data.getStringExtra("code");
            my_more_map.put(position, nameMore);
            code_more_map.put(position, codeMore);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        File file = new File(Environment.getExternalStorageDirectory(), PeopleApplication.IMAG_NAME + "/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        new AlertDialog.Builder(ComingSHPeopleAddActivity.this)
                .setTitle("人员拍照")
                .setItems(new String[]{"用户相册", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        configCompress(getTakePhoto());
                        if (which == 0) {
                            getTakePhoto().onPickFromGallery();
                        } else {
                            getTakePhoto().onPickFromCapture(imageUri);
                        }
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    /**
     * 压缩图片配置
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1 * 1000 * 1000;//200kb
        int width = 400;
        int height = 800;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(true)//保留源文件
                .create();

        takePhoto.onEnableCompress(config, false);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String filepath = result.getImage().getCompressPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.out.println("图片压缩前大小：" + stream.toByteArray().length + "byte");
        if (bitmap != null) {
            ImageCompressUtil.compressByQuality(bitmap, 30, 200, 300, stream);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        }
        byte[] bytes = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        base64Str = new String(Base64.encode(bytes, Base64.DEFAULT));
        picture_map.put(pictureInt, bitmap);
        System.out.println("图片压缩后大小：" + stream.toByteArray().length + "byte");
        peopleAddShow.get(pictureInt).setFIELDVALUE(base64Str);
        adapter.notifyDataSetChanged();
    }
    //获取标识展示
    private void getLABELS(int labelPosition, RecyclerView rvAddMoreSelectList) {

        rvAddMoreSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddActivity.this));
        rvAddMoreSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddActivity.this, DividerItemDecoration.VERTICAL));
        adapterLabel = new CommonAdapter<LABELS>(ComingSHPeopleAddActivity.this, R.layout.listview_add_more_select_item, labelsList) {
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


    /**
     * 子activity用此方法代替
     * getSupportActionBar().setTitle("");
     */
    protected void initTitle() {
        if (toolbar != null) {
            toolbar.setTitle("人口居住登记表");
//            toolbar.setNavigationIcon(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            return;
        }
    }

    /**
     * 判断为空
     */
    protected String queryIsEmpty(String textValue) {
        if (!TextUtils.isEmpty(textValue)) {
            return textValue;
        } else {
            return "";
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
                Log.e("datasCode", datasCode);
                //截取最后一个字符
                String datasLastCode = datasCode.substring(datasCode.length() - 1, datasCode.length());
                String datasStrCode = null;
                if (datasLastCode.equals("&")) {
                    // 最后一个字符被截取掉
                    datasStrCode = datasCode.substring(0, datasCode.length() - 1);
                }
                Log.e("datasStrCode", datasStrCode);
                if(peopleAddShow.get(addPosition).getFIELDNAME().equals("BIAOQIANCODE")){
                    peopleAddShow.get(addPosition).setFIELDVALUE(datasStrCode);
                }

            }
            if (peopleAddShow.get(addPosition).getFIELDMUST().equals("1")) {
                if (peopleAddShow.get(addPosition).getFIELDNAME().equals("CHIDCARD")) { //公民身份证号码
                    if (!Validation.isIdCard(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                        ToastUtil.showToast(ComingSHPeopleAddActivity.this, "请检查身份证是否有误");
                        return false;
                    }
                } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("CHSEX")) { //性别
                    if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                        if (!peopleAddShow.get(addPosition).getFIELDVALUE().equals("1") &&
                                !peopleAddShow.get(addPosition).getFIELDVALUE().equals("2")) {
                            ToastUtil.showToast(ComingSHPeopleAddActivity.this, "请检查性别是否有误");
                            return false;
                        }

                    } else {
                        ToastUtil.showToast(ComingSHPeopleAddActivity.this, "请选择性别");
                        return false;
                    }
                } else if (TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    ToastUtil.showToast(ComingSHPeopleAddActivity.this, peopleAddShow.get(addPosition).getFIELDCNNAME() + "未输入");
                    return false;
                }
            }
            if (peopleAddShow.get(addPosition).getFIELDNAME().equals("CHSHHANDPHONE")) { //联系电话
                if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                            && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                        ToastUtil.showToast(ComingSHPeopleAddActivity.this, "请检查联系电话是否有误");
                        return false;
                    }
                }
            } else if (peopleAddShow.get(addPosition).getFIELDNAME().equals("MOBILE")) { //手机
                if (!TextUtils.isEmpty(peopleAddShow.get(addPosition).getFIELDVALUE())) {
                    if (peopleAddShow.get(addPosition).getFIELDVALUE().length() != 11
                            && peopleAddShow.get(addPosition).getFIELDVALUE().length() != 8) {
                        ToastUtil.showToast(ComingSHPeopleAddActivity.this, "请检查手机号码是否有误");
                        return false;
                    }
                }
            }


        }

        return true;
    }


    @OnClick(R.id.btn_add_submit)
    public void onClick() {
        getPeopleAdd();
//        uploadPicture();
    }

    /**
     * 获取人员采集项目字段列表
     */
    private void queryPeopleAddList() {
        houseModel.getPeopleAddList(new RequestListener() {
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
     * 照片下载(可以不需要，并在根据房屋编码查找对应房屋中人员信息接口中)
     */
    private void downloadPicture() {
        userModel.downloadPicture(regcode, fwbm, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                downloadPictureJG.clear();
                downloadPictureJG.addAll(JSON.parseArray(json, DownloadPictureJG.class));

            }
        });

    }


    /**
     * 照片上传（可以不需要，并在新增人员接收接口中）
     */
    private void uploadPicture() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        final String data = format.format(new Date());
        userModel.uploadPicture(regcode, regcode + data, base64Str, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                ToastUtil.showToast(ComingSHPeopleAddActivity.this, "照片提交成功" + regcode + data);
            }
        });

    }

    /**
     * 新增人员接收接口
     */
    private void getPeopleAdd() {
//         表单验证
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
            if(peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_BASEINFO")){
                peopleAddShowList.remove(addPosition);
            }
            if(peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_ASSISTINFO")){
                peopleAddShowList.remove(addPosition);
            }
            if(peopleAddShowList.get(addPosition).getFIELDNAME().equals("LAB_LABELINFO")){
                peopleAddShowList.remove(addPosition);
            }
        }
        //提交
        houseModel.getPeopleAdd(peopleAddShowList, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
//                String json = JSON.toJSONString(o);
                Intent intent = new Intent();
                setResult(PeopleApplication.ADD_PEOPLE_BACK, intent);
                ToastUtil.showToast(ComingSHPeopleAddActivity.this, "数据提交成功");
                finish();
            }

            @Override
            public void onFail() {
                super.onFail();
                queryPeopleAddList();
            }


        });

    }
}
