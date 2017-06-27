package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class RoomIdentiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.ly_reseach_people)
    LinearLayout lyReseachPeople;
    @BindView(R.id.ly_build_ligo)
    LinearLayout lyBuildLigo;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_room_identi;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("人房标识");
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.ly_reseach_people, R.id.ly_build_ligo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_reseach_people:
                //人员标识
                Intent intent = new Intent();
                        intent.setClass(RoomIdentiActivity.this, ResearchersIdentiActivity.class);
                startActivity(intent);
                break;
            case R.id.ly_build_ligo:
                //房屋标识
                Intent intents = new Intent();
                intents.setClass(RoomIdentiActivity.this, BuildingLogoActivity.class);
                startActivity(intents);
                break;
        }
    }
}
