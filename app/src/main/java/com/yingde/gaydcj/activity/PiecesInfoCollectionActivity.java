package com.yingde.gaydcj.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.entity.AddFragmentation;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.util.Validation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PiecesInfoCollectionActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.edt_select_name)
    EditText edtSelectName;//证件号码
    @BindView(R.id.edt_select_idCard)
    EditText edtSelectIdCard;//被登记人姓名
    @BindView(R.id.edt_select_address)
    EditText edtSelectAddress;//地址
    @BindView(R.id.edt_select_info_zt)
    EditText edtSelectInfoZt;//碎片化主题
    @BindView(R.id.edt_select_info_input)
    EditText edtSelectInfoInput;//碎片化内容
    @BindView(R.id.sp_select_type)
    Spinner spSelectType;//碎片化分类
    @BindView(R.id.ly_pieces_info_add)
    LinearLayout lyPiecesInfoAdd;
    @BindView(R.id.btn_select_save)
    Button btnSelectSave;
    private ArrayAdapter<String> adapter_xgy;
    private List<String> xgyss;
    private  HouseModel houseModel;
    private  AddFragmentation addFragmentation;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_pieces_info_collection;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("碎片信息采集");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
        if(addFragmentation==null){
            addFragmentation=new AddFragmentation();
        }
        xgyss = new ArrayList<String>();
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.fragment_type);
        adapter_xgy = new ArrayAdapter<String>(this, R.layout.spinner_style,
                mItems);
        adapter_xgy
                .setDropDownViewResource(R.layout.spinner_dropdown_item);
        spSelectType.setAdapter(adapter_xgy);
    }

    @OnClick({R.id.ly_pieces_info_add, R.id.btn_select_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_pieces_info_add:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.btn_select_save:
                addFragmentation.setZJHM(edtSelectName.getText().toString().trim());
                addFragmentation.setNAME(edtSelectIdCard.getText().toString().trim());
                addFragmentation.setLOCATION(edtSelectAddress.getText().toString().trim());
                addFragmentation.setTITLE(edtSelectInfoZt.getText().toString().trim());
                addFragmentation.setMEMO(edtSelectInfoInput.getText().toString().trim());
                addFragmentation.setFRAGTYPE(spSelectType.getSelectedItem().toString().trim());
                // 表单验证
                if (!isFrom()) {
                    return;
                }
                AddFragmentations(addFragmentation);
                break;
        }
    }
    /**
     * 表单验证
     *
     * @return
     */
    public boolean isFrom() {
        if (!Validation.isIdCard(addFragmentation.getZJHM())) {
            ToastUtil.showToast(mContext, "请检查身份证是否有误");
            return false;
        }
        if (TextUtils.isEmpty(addFragmentation.getNAME())) {
            ToastUtil.showToast(mContext, "请输入被登记人姓名");
            return false;
        }
        if (TextUtils.isEmpty(addFragmentation.getLOCATION())) {
            ToastUtil.showToast(mContext, "请输入地址");
            return false;
        }
        if (TextUtils.isEmpty(addFragmentation.getTITLE())) {
            ToastUtil.showToast(mContext, "请输入碎片化主题");
            return false;
        }
        if (TextUtils.isEmpty(addFragmentation.getMEMO())) {
            ToastUtil.showToast(mContext, "请输入碎片化主题");
            return false;
        }
        if (TextUtils.isEmpty(addFragmentation.getFRAGTYPE())) {
            ToastUtil.showToast(mContext, "请选择碎片化分类");
            return false;
        }
        return true;
    }
    /**
     *碎片化信息新增接口
     */
    private void AddFragmentations( AddFragmentation addFragmentation) {
        houseModel.getAddFragmentation(addFragmentation, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                ToastUtil.showToast(mContext, "碎片信息采集成功");
                finish();
            }
        });
    }
}
