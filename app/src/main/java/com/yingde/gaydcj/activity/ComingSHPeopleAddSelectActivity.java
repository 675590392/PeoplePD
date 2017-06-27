package com.yingde.gaydcj.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.db.PeopleHandleWayDao;
import com.yingde.gaydcj.entity.db.D_BYZK;
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
import com.yingde.gaydcj.util.MyToolbar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ComingSHPeopleAddSelectActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.select_nowStreets)
    EditText selectNowStreets;
    @BindView(R.id.rv_add_select_list)
    RecyclerView rvAddSelectList;
    @BindView(R.id.btn_select_search)
    Button btnSelectSearch;
    int pos;
    String  fieldsource;
    CommonAdapter adapter;
    private List<D_JCW> jcws;
    private List<D_PCS> pcss;
    private List<D_JZ> jzs;
    private List<D_MLPH> mlphs;
    private List<D_ROAD> roads;
    private List<D_BYZK> byzks;
    private List<D_FWLX> fwlxs;
    private List<D_GJ> gjs;
    private List<D_HYZK> hyzks;
    private List<D_JZFWLX> jzfwlxs;
    private List<D_LHSY> lhsys;
    private List<D_MZ> mzs;
    private List<D_PROVINCE> provinces;
    private List<D_RKLB> rklbs;
    private List<D_WDDM> wddms;
    private List<D_WHCD> whcds;
    private List<D_XB> xbs;
    private List<D_XGY> xgys;
    private List<D_XX> xxs;
    private List<D_YFZGX> yfzgxs;
    private List<D_XZQH> xzqhs;
    private List<D_YHZGX> yhzgxs;
    private List<D_ZD> zds;
    private List<D_ZJLX> zjlxs;
    private List<D_ZJXY> zjxys;
    private List<D_ZYLB> zylbs;
    private List<D_ZZMM> zzmms;
    private List<D_ROAD> roadSelect;



    private PeopleHandleWayDao peopleHandleWayDao;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_shpeople_add_select;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("选择项");

    }

    private void getJCW() {
        jcws = new ArrayList<D_JCW>();
        jcws.clear();
        jcws.addAll(peopleHandleWayDao.queryJCWDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_JCW>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, jcws) {
            @Override
            protected void convert(ViewHolder holder, D_JCW comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getJCWMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", jcws.get(position).getJCWMC());
                intent.putExtra("code", jcws.get(position).getJCWDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getPCS() {
        pcss = new ArrayList<D_PCS>();
        pcss.clear();
        pcss.addAll(peopleHandleWayDao.queryPCSDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_PCS>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, pcss) {
            @Override
            protected void convert(ViewHolder holder, D_PCS comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getPCSMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", pcss.get(position).getPCSMC());
                intent.putExtra("code", pcss.get(position).getPCS12());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getJZ() {
        jzs = new ArrayList<D_JZ>();
        jzs.clear();
        jzs.addAll(peopleHandleWayDao.queryJZDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_JZ>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, jzs) {
            @Override
            protected void convert(ViewHolder holder, D_JZ comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getJZMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", jzs.get(position).getJZMC());
                intent.putExtra("code", jzs.get(position).getJZDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getMLPH() {
        mlphs = new ArrayList<D_MLPH>();
        mlphs.clear();
        mlphs.addAll(peopleHandleWayDao.queryMLPHDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_MLPH>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, mlphs) {
            @Override
            protected void convert(ViewHolder holder, D_MLPH comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMLPH());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", mlphs.get(position).getMLPH());
                intent.putExtra("code", mlphs.get(position).getMLPBM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getROAD() {
        roads = new ArrayList<D_ROAD>();
        roads.clear();
        roads.addAll(peopleHandleWayDao.queryROADDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ROAD>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, roads) {
            @Override
            protected void convert(ViewHolder holder, D_ROAD comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", roads.get(position).getMC());
                intent.putExtra("code", roads.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getBYZK() {
        byzks = new ArrayList<D_BYZK>();
        byzks.clear();
        byzks.addAll(peopleHandleWayDao.queryBYZKDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_BYZK>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, byzks) {
            @Override
            protected void convert(ViewHolder holder, D_BYZK comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", byzks.get(position).getMC());
                intent.putExtra("code", byzks.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }


    private void getFWLX() {
        fwlxs = new ArrayList<D_FWLX>();
        fwlxs.clear();
        fwlxs.addAll(peopleHandleWayDao.queryFWLXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_FWLX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, fwlxs) {
            @Override
            protected void convert(ViewHolder holder, D_FWLX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", fwlxs.get(position).getMC());
                intent.putExtra("code", fwlxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getGJ() {
        gjs = new ArrayList<D_GJ>();
        gjs.clear();
        gjs.addAll(peopleHandleWayDao.queryGJDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_GJ>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, gjs) {
            @Override
            protected void convert(ViewHolder holder, D_GJ comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", gjs.get(position).getMC());
                intent.putExtra("code", gjs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getHYZK() {
        hyzks = new ArrayList<D_HYZK>();
        hyzks.clear();
        hyzks.addAll(peopleHandleWayDao.queryHYZKDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_HYZK>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, hyzks) {
            @Override
            protected void convert(ViewHolder holder, D_HYZK comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", hyzks.get(position).getMC());
                intent.putExtra("code", hyzks.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getJZFWLX() {
        jzfwlxs = new ArrayList<D_JZFWLX>();
        jzfwlxs.clear();
        jzfwlxs.addAll(peopleHandleWayDao.queryJZFWLXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_JZFWLX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, jzfwlxs) {
            @Override
            protected void convert(ViewHolder holder, D_JZFWLX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", jzfwlxs.get(position).getMC());
                intent.putExtra("code", jzfwlxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getLHSY() {
        lhsys = new ArrayList<D_LHSY>();
        lhsys.clear();
        lhsys.addAll(peopleHandleWayDao.queryLHSYDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_LHSY>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, lhsys) {
            @Override
            protected void convert(ViewHolder holder, D_LHSY comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", lhsys.get(position).getMC());
                intent.putExtra("code", lhsys.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getMZ() {
        mzs = new ArrayList<D_MZ>();
        mzs.clear();
        mzs.addAll(peopleHandleWayDao.queryMZDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_MZ>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, mzs) {
            @Override
            protected void convert(ViewHolder holder, D_MZ comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", mzs.get(position).getMC());
                intent.putExtra("code", mzs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getPROVINCE() {
        provinces = new ArrayList<D_PROVINCE>();
        provinces.clear();
        provinces.addAll(peopleHandleWayDao.queryPROVINCEDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_PROVINCE>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, provinces) {
            @Override
            protected void convert(ViewHolder holder, D_PROVINCE comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", provinces.get(position).getMC());
                intent.putExtra("code", provinces.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getRKLB() {
        rklbs = new ArrayList<D_RKLB>();
        rklbs.clear();
        rklbs.addAll(peopleHandleWayDao.queryRKLBDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_RKLB>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, rklbs) {
            @Override
            protected void convert(ViewHolder holder, D_RKLB comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", rklbs.get(position).getMC());
                intent.putExtra("code", rklbs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getWDDM() {
        wddms = new ArrayList<D_WDDM>();
        wddms.clear();
        wddms.addAll(peopleHandleWayDao.queryWDDMDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_WDDM>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, wddms) {
            @Override
            protected void convert(ViewHolder holder, D_WDDM comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", wddms.get(position).getMC());
                intent.putExtra("code", wddms.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getWHCD() {
        whcds = new ArrayList<D_WHCD>();
        whcds.clear();
        whcds.addAll(peopleHandleWayDao.queryWHCDDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_WHCD>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, whcds) {
            @Override
            protected void convert(ViewHolder holder, D_WHCD comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", whcds.get(position).getMC());
                intent.putExtra("code", whcds.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getXB() {
        xbs = new ArrayList<D_XB>();
        xbs.clear();
        xbs.addAll(peopleHandleWayDao.queryXBDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_XB>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, xbs) {
            @Override
            protected void convert(ViewHolder holder, D_XB comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", xbs.get(position).getMC());
                intent.putExtra("code", xbs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getXGY() {
        xgys = new ArrayList<D_XGY>();
        xgys.clear();
        xgys.addAll(peopleHandleWayDao.queryXGYDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_XGY>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, xgys) {
            @Override
            protected void convert(ViewHolder holder, D_XGY comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", xgys.get(position).getMC());
                intent.putExtra("code", xgys.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getXX() {
        xxs = new ArrayList<D_XX>();
        xxs.clear();
        xxs.addAll(peopleHandleWayDao.queryXXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_XX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, xxs) {
            @Override
            protected void convert(ViewHolder holder, D_XX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", xxs.get(position).getMC());
                intent.putExtra("code", xxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getYFZGX() {
        yfzgxs = new ArrayList<D_YFZGX>();
        yfzgxs.clear();
        yfzgxs.addAll(peopleHandleWayDao.queryYFZGXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_YFZGX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, yfzgxs) {
            @Override
            protected void convert(ViewHolder holder, D_YFZGX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", yfzgxs.get(position).getMC());
                intent.putExtra("code", yfzgxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getXZQH() {
        xzqhs = new ArrayList<D_XZQH>();
        xzqhs.clear();
        xzqhs.addAll(peopleHandleWayDao.queryXZQHDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_XZQH>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, xzqhs) {
            @Override
            protected void convert(ViewHolder holder, D_XZQH comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", xzqhs.get(position).getMC());
                intent.putExtra("code", xzqhs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getYHZGX() {
        yhzgxs = new ArrayList<D_YHZGX>();
        yhzgxs.clear();
        yhzgxs.addAll(peopleHandleWayDao.queryYHZGXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_YHZGX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, yhzgxs) {
            @Override
            protected void convert(ViewHolder holder, D_YHZGX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", yhzgxs.get(position).getMC());
                intent.putExtra("code", yhzgxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getZD() {
        zds = new ArrayList<D_ZD>();
        zds.clear();
        zds.addAll(peopleHandleWayDao.queryZDDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ZD>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, zds) {
            @Override
            protected void convert(ViewHolder holder, D_ZD comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getTABLENAME());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", zds.get(position).getTABLENAME());
                intent.putExtra("code", zds.get(position).getTABLEID());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getZJLX() {
        zjlxs = new ArrayList<D_ZJLX>();
        zjlxs.clear();
        zjlxs.addAll(peopleHandleWayDao.queryZJLXDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ZJLX>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, zjlxs) {
            @Override
            protected void convert(ViewHolder holder, D_ZJLX comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", zjlxs.get(position).getMC());
                intent.putExtra("code", zjlxs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getZJXY() {
        zjxys = new ArrayList<D_ZJXY>();
        zjxys.clear();
        zjxys.addAll(peopleHandleWayDao.queryZJXYDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ZJXY>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, zjxys) {
            @Override
            protected void convert(ViewHolder holder, D_ZJXY comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", zjxys.get(position).getMC());
                intent.putExtra("code", zjxys.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }

    private void getZYLB() {
        zylbs = new ArrayList<D_ZYLB>();
        zylbs.clear();
        zylbs.addAll(peopleHandleWayDao.queryZYLBDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ZYLB>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, zylbs) {
            @Override
            protected void convert(ViewHolder holder, D_ZYLB comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", zylbs.get(position).getMC());
                intent.putExtra("code", zylbs.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getZZMM() {
        zzmms = new ArrayList<D_ZZMM>();
        zzmms.clear();
        zzmms.addAll(peopleHandleWayDao.queryZZMMDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ZZMM>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, zzmms) {
            @Override
            protected void convert(ViewHolder holder, D_ZZMM comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", zzmms.get(position).getMC());
                intent.putExtra("code", zzmms.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    private void getROAD_SELECT() {
        roadSelect = new ArrayList<D_ROAD>();
        roadSelect.clear();
        roadSelect.addAll(peopleHandleWayDao.queryROADDictionaryAll());
        rvAddSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddSelectActivity.this));
        rvAddSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<D_ROAD>(ComingSHPeopleAddSelectActivity.this, R.layout.listview_add_select_item, roadSelect) {
            @Override
            protected void convert(ViewHolder holder, D_ROAD comingSHPeopleAdd, int position) {
                TextView textSelect = holder.getView(R.id.textview_item_select);
                //*号
                holder.setText(R.id.textview_item_select, comingSHPeopleAdd.getMC());

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("position", pos);
                intent.putExtra("name", roadSelect.get(position).getMC());
                intent.putExtra("code", roadSelect.get(position).getDM());
                setResult(PeopleApplication.SELECT_SIMPLE_BACK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddSelectList.setAdapter(adapter);
    }
    @Override
    protected void initData() {
        if(peopleHandleWayDao==null){
            peopleHandleWayDao=new PeopleHandleWayDao();
        }
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
//            regcode = getIntent().getExtras().getString("regcode", "");
//            fwbm = getIntent().getExtras().getString("fwbm", "");
            pos = intent.getIntExtra("position", -1);
            fieldsource=intent.getStringExtra("fieldsource");
        }

        /**
         * 各字典适配器调用方法
         */
        if(!TextUtils.isEmpty(fieldsource)){
            switch (fieldsource.toString().trim()) {
                case "D_JCW":
                    getJCW();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_PCS":
                    getPCS();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_JZ":
                    getJZ();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_MLPH":
                    getMLPH();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ROAD":
                    getROAD();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_BYZK":
                    getBYZK();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_FWLX":
                    getFWLX();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_GJ":
                    getGJ();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_HYZK":
                    getHYZK();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_JZFWLX":
                    getJZFWLX();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_LHSY":
                    getLHSY();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_MZ":
                    getMZ();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_PROVINCE":
                    getPROVINCE();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_RKLB":
                    getRKLB();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_WDDM":
                    getWDDM();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_WHCD":
                    getWHCD();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_XB":
                    getXB();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_XGY":
                    getXGY();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_XX":
                    getXX();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_YFZGX":
                    getYFZGX();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_XZQH":
                    getXZQH();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_YHZGX":
                    getYHZGX();
                    adapter.notifyDataSetChanged();
                    break;

                case "D_ZD":
                    getZD();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ZJLX":
                    getZJLX();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ZJXY":
                    getZJXY();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ZYLB":
                    getZYLB();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ZZMM":
                    getZZMM();
                    adapter.notifyDataSetChanged();
                    break;
                case "D_ROAD_SELECT":
                    getROAD_SELECT();
                    adapter.notifyDataSetChanged();
                    break;
                case "YWZT":
                    getYWZT();
                    break;

                case "null":

                    break;



            }

        }
    }

    private void getYWZT() {

    }

    @OnClick({R.id.btn_select_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_search:
//                String entity= "D_JCW";
                if(!TextUtils.isEmpty(fieldsource)){
                    switch (fieldsource.toString().trim()) {
                        case "D_JCW":
                            jcws.clear();
                            jcws.addAll(peopleHandleWayDao.queryJCWDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_PCS":
                            pcss.clear();
                            pcss.addAll(peopleHandleWayDao.queryPCSDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_JZ":
                            jzs.clear();
                            jzs.addAll(peopleHandleWayDao.queryJZDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();

                            break;
                        case "D_MLPH":
                            mlphs.clear();
                            mlphs.addAll(peopleHandleWayDao.queryMLPHDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ROAD":
                            roads.clear();
                            roads.addAll(peopleHandleWayDao.queryROADDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_BYZK":
                            byzks.clear();
                            byzks.addAll(peopleHandleWayDao.queryBYZKDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_FWLX":
                            fwlxs.clear();
                            fwlxs.addAll(peopleHandleWayDao.queryFWLXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_GJ":
                            gjs.clear();
                            gjs.addAll(peopleHandleWayDao.queryGJDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_HYZK":
                            hyzks.clear();
                            hyzks.addAll(peopleHandleWayDao.queryHYZKDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_JZFWLX":
                            jzfwlxs.clear();
                            jzfwlxs.addAll(peopleHandleWayDao.queryJZFWLXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_LHSY":
                            lhsys.clear();
                            lhsys.addAll(peopleHandleWayDao.queryLHSYDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_MZ":
                            mzs.clear();
                            mzs.addAll(peopleHandleWayDao.queryMZDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_PROVINCE":
                            provinces.clear();
                            provinces.addAll(peopleHandleWayDao.queryPROVINCEDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_RKLB":
                            rklbs.clear();
                            rklbs.addAll(peopleHandleWayDao.queryRKLBDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_WDDM":
                            wddms.clear();
                            wddms.addAll(peopleHandleWayDao.queryWDDMDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_WHCD":
                            whcds.clear();
                            whcds.addAll(peopleHandleWayDao.queryWHCDDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_XB":
                            xbs.clear();
                            xbs.addAll(peopleHandleWayDao.queryXBDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_XGY":
                            xgys.clear();
                            xgys.addAll(peopleHandleWayDao.queryXGYDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_XX":
                            xxs.clear();
                            xxs.addAll(peopleHandleWayDao.queryXXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_YFZGX":
                            yfzgxs.clear();
                            yfzgxs.addAll(peopleHandleWayDao.queryYFZGXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_XZQH":
                            xzqhs.clear();
                            xzqhs.addAll(peopleHandleWayDao.queryXZQHDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_YHZGX":
                            yhzgxs.clear();
                            yhzgxs.addAll(peopleHandleWayDao.queryYHZGXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ZD":
                            zds.clear();
                            zds.addAll(peopleHandleWayDao.queryZDDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ZJLX":
                            zjlxs.clear();
                            zjlxs.addAll(peopleHandleWayDao.queryZJLXDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ZJXY":
                            zjxys.clear();
                            zjxys.addAll(peopleHandleWayDao.queryZJXYDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ZYLB":
                            zylbs.clear();
                            zylbs.addAll(peopleHandleWayDao.queryZYLBDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ZZMM":
                            zzmms.clear();
                            zzmms.addAll(peopleHandleWayDao.queryZZMMDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "D_ROAD_SELECT":
                            roadSelect.clear();
                            roadSelect.addAll(peopleHandleWayDao.queryRoadDictionary(selectNowStreets.getText().toString().trim()));
                            adapter.notifyDataSetChanged();
                            break;
                        case "null":
                            break;

                    }

                }
                break;
        }
    }

}
