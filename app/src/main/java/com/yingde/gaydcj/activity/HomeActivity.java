package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.adapter.MenuAdapter;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.AppPermissions;
import com.yingde.gaydcj.entity.db.D_FWLX;
import com.yingde.gaydcj.entity.db.D_GJ;
import com.yingde.gaydcj.entity.db.D_HYZK;
import com.yingde.gaydcj.entity.db.D_JCW;
import com.yingde.gaydcj.entity.db.D_JZ;
import com.yingde.gaydcj.entity.db.D_JZFWLX;
import com.yingde.gaydcj.entity.db.D_LHSY;
import com.yingde.gaydcj.entity.db.D_MLPH;
import com.yingde.gaydcj.entity.db.D_MZ;
import com.yingde.gaydcj.entity.db.D_PCS;
import com.yingde.gaydcj.entity.db.D_PROVINCE;
import com.yingde.gaydcj.entity.db.D_RKLB;
import com.yingde.gaydcj.entity.db.D_ROAD;
import com.yingde.gaydcj.entity.db.D_WDDM;
import com.yingde.gaydcj.entity.db.D_WHCD;
import com.yingde.gaydcj.entity.db.D_XB;
import com.yingde.gaydcj.entity.db.D_XGY;
import com.yingde.gaydcj.entity.db.D_XX;
import com.yingde.gaydcj.entity.db.D_XZQH;
import com.yingde.gaydcj.entity.db.D_YFZGX;
import com.yingde.gaydcj.entity.db.D_YHZGX;
import com.yingde.gaydcj.entity.db.D_ZD;
import com.yingde.gaydcj.entity.db.D_ZJLX;
import com.yingde.gaydcj.entity.db.D_ZJXY;
import com.yingde.gaydcj.entity.db.D_ZYLB;
import com.yingde.gaydcj.entity.db.D_ZZMM;
import com.yingde.gaydcj.entity.db.Dictionary;
import com.yingde.gaydcj.entity.db.LABELS;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.DictionaryModel;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.DictionaryModelIml;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.GlideImageLoader;
import com.yingde.gaydcj.util.MyToolbar;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;


/**
 * 主页面
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.grid_home)
    GridView gridHome;
    TextView tv9;
    @BindView(R.id.banner)
    Banner banner;
    private List<Map<String, Object>> data_list = null;
    private SimpleAdapter sim_adapter = null;
    //app权限适配器
    private MenuAdapter menuAdapter;
    //    人员类
    UserModelIml userModel;

    HouseModel houseModel;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.people_collected, R.drawable.researchers_collected, R.drawable.collect_details,
            R.drawable.room_marked, R.drawable.add_room, R.drawable.query_room, R.drawable.job_account,
            R.drawable.notice, R.drawable.system_settings};
//    private String[] iconName = {"人员采集", "碎片采集", "采集详情", "人房标识", "新增房屋", "房屋定位查询",
//            "工作统计", "通知公告", "系统设置"};

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        userModel = new UserModelIml(mContext);
        //获取app权限
        getAppPermissions();
        initDictionary();
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        // 新建适配器
        String[] from = {"image"};
        int[] to = {R.id.ItemImage};
        sim_adapter = new SimpleAdapter(this, data_list,
                R.layout.home_grid_item, from, to);
        //轮播
        Integer[] images = {R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};
        banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();
    }

    private void startActivity() throws ClassNotFoundException {
        String className="HomeActivity.class";
        Intent intent=new Intent();
        intent.setClass(this,Class.forName(className));
        startActivity(intent);
    }

    public List<Map<String, Object>> getData() {
        // cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
//            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("主页面");
        Log.d("wh","主界面");
    }


    private void initDictionary() {
        //初始化字典表数据
        final DictionaryModel dictionaryModel = new DictionaryModelIml(mContext);
        dictionaryModel.getDictionaryList(new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                //解析得到的数据
                String json = JSON.toJSONString(o);
                final List<Dictionary> dictionaries = JSON.parseArray(json, Dictionary.class);
                Log.e("o",o.toString());
                Log.e("json",json.toString());
                PeopleApplication.getDaoInstant().getDictionaryDao().deleteAll();
                //插入多条记录
                PeopleApplication.getDaoInstant().getDictionaryDao().insertInTx(dictionaries);

                for (int i = 0; i < dictionaries.size(); i++) {
                    final Dictionary dictionary = dictionaries.get(i);
                    dictionaryModel.getDictionaryData(dictionary.getTABLENAME(), dictionary.getVERSION(), new RequestListener() {
                        @Override
                        public void onSuccess(Object o, String token) {
                            String json = JSON.toJSONString(o);
                            switch (dictionary.getTABLENAME()) {
                                case "D_JCW":
                                    List<D_JCW> jcws = JSON.parseArray(json, D_JCW.class);
                                    PeopleApplication.getDaoInstant().getD_JCWDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_JCWDao().insertInTx(jcws);
                                    break;
                                case "D_PCS":
                                    List<D_PCS> pcs = JSON.parseArray(json, D_PCS.class);
                                    PeopleApplication.getDaoInstant().getD_PCSDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_PCSDao().insertInTx(pcs);
                                    break;
                                case "D_JZ":
                                    List<D_JZ> jzs = JSON.parseArray(json, D_JZ.class);
                                    PeopleApplication.getDaoInstant().getD_JZDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_JZDao().insertInTx(jzs);
                                    break;
                                case "D_MLPH":
                                    List<D_MLPH> mlphs = JSON.parseArray(json, D_MLPH.class);
                                    PeopleApplication.getDaoInstant().getD_MLPHDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_MLPHDao().insertInTx(mlphs);
                                    break;
                                case "D_ROAD":
                                    List<D_ROAD> roads = JSON.parseArray(json, D_ROAD.class);
                                    PeopleApplication.getDaoInstant().getD_ROADDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ROADDao().insertInTx(roads);
                                    break;
//                                case "D_BYZK":
//                                    List<D_BYZK> byzks = JSON.parseArray(json, D_BYZK.class);
//                                    PeopleApplication.getDaoInstant().getD_BYZKDao().deleteAll();
//                                    PeopleApplication.getDaoInstant().getD_BYZKDao().insertInTx(byzks);
//                                    break;
                                case "D_FWLX":
                                    List<D_FWLX> fwlxs = JSON.parseArray(json, D_FWLX.class);
                                    PeopleApplication.getDaoInstant().getD_FWLXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_FWLXDao().insertInTx(fwlxs);
                                    break;
                                case "D_GJ":
                                    List<D_GJ> gjs = JSON.parseArray(json, D_GJ.class);
                                    PeopleApplication.getDaoInstant().getD_GJDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_GJDao().insertInTx(gjs);
                                    break;
                                case "D_HYZK":
                                    List<D_HYZK> hyzks = JSON.parseArray(json, D_HYZK.class);
                                    PeopleApplication.getDaoInstant().getD_HYZKDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_HYZKDao().insertInTx(hyzks);
                                    break;
                                case "D_JZFWLX":
                                    List<D_JZFWLX> jzfwlxs = JSON.parseArray(json, D_JZFWLX.class);
                                    PeopleApplication.getDaoInstant().getD_JZFWLXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_JZFWLXDao().insertInTx(jzfwlxs);
                                    break;
                                case "D_LHSY":
                                    List<D_LHSY> lhsys = JSON.parseArray(json, D_LHSY.class);
                                    PeopleApplication.getDaoInstant().getD_LHSYDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_LHSYDao().insertInTx(lhsys);
                                    break;
                                case "D_MZ":
                                    List<D_MZ> mzs = JSON.parseArray(json, D_MZ.class);
                                    PeopleApplication.getDaoInstant().getD_MZDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_MZDao().insertInTx(mzs);
                                    break;
                                case "D_PROVINCE":
                                    List<D_PROVINCE> procinces = JSON.parseArray(json, D_PROVINCE.class);
                                    PeopleApplication.getDaoInstant().getD_PROVINCEDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_PROVINCEDao().insertInTx(procinces);
                                    break;
                                case "D_RKLB":
                                    List<D_RKLB> rklbs = JSON.parseArray(json, D_RKLB.class);
                                    PeopleApplication.getDaoInstant().getD_RKLBDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_RKLBDao().insertInTx(rklbs);
                                    break;
                                case "D_WDDM":
                                    List<D_WDDM> wddms = JSON.parseArray(json, D_WDDM.class);
                                    PeopleApplication.getDaoInstant().getD_WDDMDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_WDDMDao().insertInTx(wddms);
                                    break;
                                case "D_WHCD":
                                    List<D_WHCD> whcds = JSON.parseArray(json, D_WHCD.class);
                                    PeopleApplication.getDaoInstant().getD_WHCDDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_WHCDDao().insertInTx(whcds);
                                    break;
                                case "D_XB":
                                    List<D_XB> xbs = JSON.parseArray(json, D_XB.class);
                                    PeopleApplication.getDaoInstant().getD_XBDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_XBDao().insertInTx(xbs);
                                    break;
                                case "D_XGY":
                                    List<D_XGY> xgys = JSON.parseArray(json, D_XGY.class);
                                    PeopleApplication.getDaoInstant().getD_XGYDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_XGYDao().insertInTx(xgys);
                                    break;
                                case "D_XX":
                                    List<D_XX> xxs = JSON.parseArray(json, D_XX.class);
                                    PeopleApplication.getDaoInstant().getD_XXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_XXDao().insertInTx(xxs);
                                    break;
                                case "D_XZQH":
                                    List<D_XZQH> xzqhs = JSON.parseArray(json, D_XZQH.class);
                                    PeopleApplication.getDaoInstant().getD_XZQHDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_XZQHDao().insertInTx(xzqhs);
                                    break;
                                case "D_YFZGX":
                                    List<D_YFZGX> yfzgxs = JSON.parseArray(json, D_YFZGX.class);
                                    PeopleApplication.getDaoInstant().getD_YFZGXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_YFZGXDao().insertInTx(yfzgxs);
                                    break;
                                case "D_YHZGX":
                                    List<D_YHZGX> yhzgxs = JSON.parseArray(json, D_YHZGX.class);
                                    PeopleApplication.getDaoInstant().getD_YHZGXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_YHZGXDao().insertInTx(yhzgxs);
                                    break;
                                case "D_ZD":
                                    List<D_ZD> zds = JSON.parseArray(json, D_ZD.class);
                                    PeopleApplication.getDaoInstant().getD_ZDDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ZDDao().insertInTx(zds);
                                    break;
                                case "D_ZJLX":
                                    List<D_ZJLX> zjlxs = JSON.parseArray(json, D_ZJLX.class);
                                    PeopleApplication.getDaoInstant().getD_ZJLXDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ZJLXDao().insertInTx(zjlxs);
                                    break;
                                case "D_ZJXY":
                                    List<D_ZJXY> zjxys = JSON.parseArray(json, D_ZJXY.class);
                                    PeopleApplication.getDaoInstant().getD_ZJXYDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ZJXYDao().insertInTx(zjxys);
                                    break;
                                case "D_ZYLB":
                                    List<D_ZYLB> zylbs = JSON.parseArray(json, D_ZYLB.class);
                                    PeopleApplication.getDaoInstant().getD_ZYLBDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ZYLBDao().insertInTx(zylbs);
                                    break;
                                case "D_ZZMM":
                                    List<D_ZZMM> zzmms = JSON.parseArray(json, D_ZZMM.class);
                                    PeopleApplication.getDaoInstant().getD_ZZMMDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getD_ZZMMDao().insertInTx(zzmms);
                                    break;
                                case "LABELS":
                                    List<LABELS> labels = JSON.parseArray(json, LABELS.class);
                                    PeopleApplication.getDaoInstant().getLABELSDao().deleteAll();
                                    PeopleApplication.getDaoInstant().getLABELSDao().insertInTx(labels);
                                    break;

                            }
                        }
                    });
                }
            }
        });
    }
    //二十九、获取APP操作模块（权限）
    private void getAppPermissions() {
        userModel.getAppPermissions(new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                try {
                    Log.d("wh", "获取APP操作模块（权限）");
                    String json = JSON.toJSONString(o);
                    //app权限数组
                    List<AppPermissions> permissions;  permissions = JSON.parseArray(json, AppPermissions.class);
                    // 配置适配器
                    menuAdapter=new MenuAdapter(permissions,HomeActivity.this);
                    gridHome.setAdapter(menuAdapter);
                } catch (Exception e) {
                  e.printStackTrace();
                  e.getMessage();
                }

            }

        });
    }

}
