package com.yingde.gaydcj.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.PeopleInfo;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.Base64Coder;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.util.MUtilIdCheckUtils;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.view.ListViewCompat;
import com.yingde.gaydcj.view.SlideView;

import android.view.View.OnClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.senter.model.IdentityCardZ;

/**
 * 房屋内居住人
 */
public class ComingSHPeopleResidentialActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnClickListener,
        SlideView.OnSlideListener {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.btn_add_coming)
    Button btnAddComing;
    @BindView(R.id.img_cs)
    ImageView imgCs;
    @BindView(R.id.rv_add_list)
    ListViewCompat mListView;

    private EditText selectChname;
    private EditText selectChidcard;
    private List<PeopleInfo> peopleInfo;
    SlideAdapter adapter;
    private static final String TAG = "ComingSHPeopleResidentialActivity";
    private SlideView mLastSlideViewWithStatusOn;
    private IdentityCardZ idCard = null;
    private String fwbm;
    private String edtSelectChshliveraddr;
    HouseModel houseModel;
    UserModelIml userModel;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_shpeople_residential;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("房屋内居住人");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
        userModel = new UserModelIml(mContext);

        if (getIntent().getExtras() != null) {
            fwbm = getIntent().getExtras().getString("fwbm", "");
            edtSelectChshliveraddr = getIntent().getStringExtra("edtSelectChshliveraddr");
        }
        peopleInfo = new ArrayList<PeopleInfo>();
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        mListView.setOnItemClickListener(ComingSHPeopleResidentialActivity.this);
        queryPeopleInfoListByFWBM();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_add_coming)
    public void onClick() {
//        Intent intent = new Intent(ComingSHPeopleResidentialActivity.this,
//                                ComingSHPeopleAddActivity.class);
//                        startActivityForResult(intent, PeopleApplication.ADD_PEOPLE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
//        builder.setTitle("外来人员可受理业务查询");
        builder.setInverseBackgroundForced(true);
        View view2 = getLayoutInflater().inflate(R.layout.buidle_dqsfz, null);
        builder.setView(view2);
        final AlertDialog dialog = builder.show();
        // NFC读取身份证
        Button btnSelectReadCard = (Button) view2
                .findViewById(R.id.btn_select_readCard);
        //取消
        Button btnCancal = (Button) view2
                .findViewById(R.id.btn_cancal);
        //确定
        Button btnCriturm = (Button) view2
                .findViewById(R.id.btn_criturm);

        // NFC读取身份证
        Button btnPothoReadCard = (Button) view2
                .findViewById(R.id.btn_potho_readCard);
        selectChname = (EditText) view2.findViewById(R.id.select_Chname);
        selectChidcard = (EditText) view2.findViewById(R.id.select_Chidcard);

        // NFC读取身份证
        btnSelectReadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComingSHPeopleResidentialActivity.this,
                        MainActivity.class);
                startActivityForResult(intent, 2121);

            }
        });
        // 拍照读取身份证
        btnPothoReadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //取消
        btnCancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        btnCriturm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getRegCode(selectChidcard.getText().toString(), selectChname.getText().toString(), fwbm);


                if (!"".equals(selectChname.getText().toString())
                        && !"".equals(selectChidcard.getText().toString())) {
                    String chidcard = selectChidcard.getText().toString();
                    MUtilIdCheckUtils idCheckUtils = new MUtilIdCheckUtils();
                    Pattern pattern = Pattern
                            .compile("(\\d{14}[0-9A-Z])|(\\d{17}[0-9A-Z])");
                    Matcher matcher = pattern.matcher(chidcard);


                    if (TextUtils.isEmpty(idCheckUtils
                            .validateIDNum(selectChidcard.getText().toString()
                                    .trim()))) {
                        if (idCard != null) {
                            selectChidcard.setText(idCard.cardNo);
                            selectChname.setText(idCard.name);
                        }
                        Intent intent = new Intent(ComingSHPeopleResidentialActivity.this,
                                ComingSHPeopleAddActivity.class);
                        intent.putExtra("fwbm", fwbm);
                        intent.putExtra("edtSelectChshliveraddr", edtSelectChshliveraddr);
                        intent.putExtra("selectChidcard", selectChidcard.getText().toString().trim());
                        intent.putExtra("selectChname", selectChname.getText().toString().trim());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("idCard",(Serializable) idCard);
                        startActivityForResult(intent, PeopleApplication.ADD_PEOPLE);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ComingSHPeopleResidentialActivity.this, "请填写正确的身份证号码", Toast.LENGTH_SHORT)
                                .show();
                        // 设置点击对话框外部区域不关闭对话框
                        builder.setCancelable(false);
                    }
                } else {
                    Toast.makeText(ComingSHPeopleResidentialActivity.this, "请填写身份证信息", Toast.LENGTH_SHORT).show();
                    // 设置点击对话框外部区域不关闭对话框
                    builder.setCancelable(false);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getStringExtra("name") != null
                && !data.getStringExtra("name").equals("")&& resultCode == 2122) {
            String name = data.getStringExtra("name").trim();
            String ethnicity = data.getStringExtra("ethnicity").trim();
            String cardNo = data.getStringExtra("cardNo").trim();
            String address = data.getStringExtra("address").trim();
            String sex = data.getStringExtra("sex").trim();
            String authority = data.getStringExtra("authority").trim();
            String birth = data.getStringExtra("birth").trim();
            String period = data.getStringExtra("period").trim();
            Bitmap avatar = data.getParcelableExtra("avatar");
            if (name != null) {
                name = name.trim();
            }
            if (ethnicity != null) {
                ethnicity = ethnicity.trim();
            }
            if (cardNo != null) {
                cardNo = cardNo.trim();
            }
            if (address != null) {
                address = address.trim();
            }
            if (sex != null) {
                sex = sex.trim();
            }
            if (authority != null) {
                authority = authority.trim();
            }
            if (birth != null) {
                birth = birth.trim();
            }
            if (period != null) {
                period = period.trim();
            }

            idCard = new IdentityCardZ();
            idCard.name = name;
            idCard.ethnicity = ethnicity;
            idCard.cardNo = cardNo;
            idCard.address = address;
            idCard.sex = sex;
            idCard.authority = authority;
            idCard.birth = birth;
            idCard.period = period;
            imgCs.setImageBitmap(avatar);
            // idCard.avatar = address;
            // idCard.idCard = address;
            // idCard.data = address;
            // idCard.authority = address;
            if(!TextUtils.isEmpty(name)){
                selectChname.setText(name);
            }
            if(!TextUtils.isEmpty(cardNo)){
                selectChidcard.setText(cardNo);
            }
        }else if(data != null && resultCode == PeopleApplication.ADD_PEOPLE_BACK){
            queryPeopleInfoListByFWBM();
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 根据门弄牌编码查找房屋内居住人
     */
    private void queryPeopleInfoListByFWBM() {
        houseModel.getPeopleInfoListByFWBM(fwbm, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                peopleInfo.clear();
                peopleInfo.addAll(JSON.parseArray(json, PeopleInfo.class));
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 注销人员接收接口
     */
    private void getCancelPerson(String regcode, String chidcard, String chname, String fwbm, final int position) {
        houseModel.getCancelPerson(regcode, chidcard, chname, fwbm, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                peopleInfo.remove(position);
//                queryPeopleInfoListByFWBM();
                adapter.notifyDataSetChanged();
                ToastUtil.showToast(ComingSHPeopleResidentialActivity.this, "注销成功");
            }
        });
    }


    /**
     * 保存登记号
     */
    private void getRegCode(String chidcard, String chname, String houseseq) {
        String token = CommonUtil.getTokenId(mContext);
        userModel.setTokenId(token);
        userModel.getRegCode("chidcard", "chname", "houseseq", new RequestListener<List<Map>>() {
            @Override
            public void onSuccess(List<Map> maps, String token) {
                String regcode = (String) maps.get(0).get("REGCODE");//登记号
                CommonUtil.setRegCode(mContext, regcode);

                Intent intent = new Intent(ComingSHPeopleResidentialActivity.this,
                        ComingSHPeopleAddActivity.class);
                intent.putExtra("regcode", regcode);
                intent.putExtra("fwbm", fwbm);
                intent.putExtra("edtSelectChshliveraddr", edtSelectChshliveraddr);

                startActivity(intent);

            }
        });
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return peopleInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return peopleInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.listview_fwnjz_detailr_item, null);
                slideView = new SlideView(ComingSHPeopleResidentialActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(ComingSHPeopleResidentialActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            PeopleInfo item = peopleInfo.get(position);
            item.slideView = slideView;
            item.slideView.shrink();
            holder.name_residential.setText(item.getChname());
            Bitmap bitmap = null;
            if (!TextUtils.isEmpty(item.getImagephoto())) {
                // 将字符串转换成Bitmap类型
                try {
                    byte[] bitmapArray;
                    bitmapArray = Base64Coder.decode(item.getImagephoto());
                    bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                    holder.photo_residential.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            holder.sex_residential.setText(item.getSex());
            holder.ehtnic_residential.setText(item.getMz());
            holder.birthday_residential.setText(item.getBirthday());
            holder.address_residential.setText(item.getAddress());
            holder.number_residential.setText(item.getChidcard());
            holder.signed_residential.setText(item.getRegcode());
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new android.app.AlertDialog.Builder(ComingSHPeopleResidentialActivity.this)
                            .setTitle("是否注销此条信息")
                            .setItems(new String[]{"确认", "取消"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        getCancelPerson(queryIsEmpty(peopleInfo.get(position).getRegcode()),
                                                queryIsEmpty(peopleInfo.get(position).getChidcard()),
                                                queryIsEmpty(peopleInfo.get(position).getChname()),
                                                fwbm,position);
                                    } else {
                                        dialog.dismiss();
                                    }
                                }

                            })
//                        .setNegativeButton("取消", null)
                            .show();
                }
            });
            return slideView;
        }
    }

    private static class ViewHolder {
        public TextView name_residential;
        public ImageView photo_residential;
        public TextView sex_residential;
        public TextView ehtnic_residential;
        public TextView birthday_residential;
        public TextView address_residential;
        public TextView number_residential;
        public TextView signed_residential;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            name_residential = (TextView) view.findViewById(R.id.tv_name_residential);
            photo_residential = (ImageView) view.findViewById(R.id.iv_photo_residential);
            sex_residential = (TextView) view.findViewById(R.id.tv_sex_residential);
            ehtnic_residential = (TextView) view.findViewById(R.id.tv_ehtnic_residential);
            birthday_residential = (TextView) view.findViewById(R.id.tv_birthday_residential);
            address_residential = (TextView) view.findViewById(R.id.tv_address_residential);
            number_residential = (TextView) view.findViewById(R.id.tv_number_residential);
            signed_residential = (TextView) view.findViewById(R.id.tv_signed_residential);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        Log.d("wh","滑动mLastSlideViewWithStatusOn"+mLastSlideViewWithStatusOn);
        Log.d("wh","滑动status"+status);
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            Log.d("wh","滑动1");
            mLastSlideViewWithStatusOn.shrink();
        }
        if (status == SLIDE_STATUS_ON) {
            Log.d("wh","滑动2");
            mLastSlideViewWithStatusOn = (SlideView) view;
            mLastSlideViewWithStatusOn.shrink();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "onClick v=" + v);
        }
    }
}
