package com.yingde.gaydcj.activity;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.entity.MessageDataInfo;
import com.yingde.gaydcj.entity.MessagesInfo;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 通知公告
 */
public class ReminderWorkActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_remainder_woek_list)
    XRecyclerView rvRemainderWoekList;
    CommonAdapter adapter;
    private List<MessagesInfo> messagesInfo;
    HouseModel houseModel;
    String type = "Message";
    String Rows = "0";
    int pageIndex=1;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_reminder_work;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("通知公告");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
        messagesInfo = new ArrayList<MessagesInfo>();
        rvRemainderWoekList.setLayoutManager(new LinearLayoutManager(mContext));
        rvRemainderWoekList.setItemAnimator(new DefaultItemAnimator());
        rvRemainderWoekList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rvRemainderWoekList.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
                queryMessages();

            }
            //加载更多
            @Override
            public void onLoadMore() {
//                queryMessages();
            }
        });
        adapter = new CommonAdapter<MessagesInfo>(mContext, R.layout.listview_reminder_work_item, messagesInfo) {
            @Override
            protected void convert(ViewHolder holder, MessagesInfo messagesInfo, int position) {
                TextView text_read_state=holder.getView(R.id.text_read_state);
                if (messagesInfo != null) {
                    holder.setText(R.id.text_zt, queryIsEmpty(messagesInfo.getTITLE()));
                    holder.setText(R.id.text_message_type, queryIsEmpty(messagesInfo.getMessageType()));
                    holder.setText(R.id.text_issue_name, queryIsEmpty(messagesInfo.getPUBLISHER()));
                    holder.setText(R.id.text_issue_date, queryIsEmpty(messagesInfo.getSAVEDATE()));
//                        holder.setText(R.id.text_read_state, queryIsEmpty(messagesInfo.getISREADED()));
                    holder.setText(R.id.text_read_date, queryIsEmpty(messagesInfo.getREADDATE()));
                    if(!TextUtils.isEmpty(messagesInfo.getISREADED())){
                        if(messagesInfo.getISREADED().equals("1")){
                            text_read_state.setTextColor(Color.parseColor("#4682B4"));
                            holder.setText(R.id.text_read_state, "已阅读");
                        }else if(messagesInfo.getISREADED().equals("0")){
                            text_read_state.setTextColor(Color.parseColor("#FF0000"));
                            holder.setText(R.id.text_read_state, "未阅读");
                        }}
                }
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Log.e("messagesInfo.get(position - 1).getCODE()",messagesInfo.get(position - 1).getCODE());
                /**
                 * 获取通知通告清单
                 */
                houseModel.getMessageData(messagesInfo.get(position - 1).getCODE(), new RequestListener() {
                    @Override
                    public void onSuccess(Object o, String token) {
                        String json = JSON.toJSONString(o);
                        List<MessageDataInfo> messageDatas = JSON.parseArray(json, MessageDataInfo.class);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(false);
                        builder.setInverseBackgroundForced(true);
                        View view2 = getLayoutInflater().inflate(R.layout.listview_reminder_work_dialog, null);
                        builder.setView(view2);
                        final AlertDialog dialog = builder.show();
                        TextView text_num = (TextView) view2
                                .findViewById(R.id.text_num);
                        TextView text_zt = (TextView) view2
                                .findViewById(R.id.text_zt);
                        TextView text_content = (TextView) view2
                                .findViewById(R.id.text_content);
                        TextView text_message_type = (TextView) view2
                                .findViewById(R.id.text_message_type);
                        TextView text_issue_name = (TextView) view2
                                .findViewById(R.id.text_issue_name);
                        TextView text_issue_date = (TextView) view2
                                .findViewById(R.id.text_issue_date);
                        TextView text_read_state = (TextView) view2
                                .findViewById(R.id.text_read_state);
                        TextView text_read_date = (TextView) view2
                                .findViewById(R.id.text_read_date);
                        if (messageDatas != null) {
                            text_num.setText(queryIsEmpty(messageDatas.get(0).getCODE()));
                            text_zt.setText(queryIsEmpty(messageDatas.get(0).getTITLE()));
                            text_content.setText(queryIsEmpty(messageDatas.get(0).getMESSAGE()));
                            text_message_type.setText(queryIsEmpty(messageDatas.get(0).getTYPE()));
                            text_issue_name.setText(queryIsEmpty(messageDatas.get(0).getPUBLISHER()));
                            text_issue_date.setText(queryIsEmpty(messageDatas.get(0).getSAVEDATE()));
                            if(queryIsEmpty(messageDatas.get(0).getISREADED()).equals("1")){
                                text_read_state.setTextColor(Color.parseColor("#4682B4"));
                                text_read_state.setText("已阅读");
                            }else if(queryIsEmpty(messageDatas.get(0).getISREADED()).equals("0")){
                                text_read_state.setTextColor(Color.parseColor("#FF0000"));
                                text_read_state.setText("未阅读");
                            }
                            text_read_date.setText(queryIsEmpty(messageDatas.get(0).getREADDATE()));
                        }
                        //确定
                        Button btnCriturm = (Button) view2
                                .findViewById(R.id.btn_criturm);
                        //确定
                        btnCriturm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                queryMessages();
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvRemainderWoekList.setAdapter(adapter);
        queryMessages();
    }


    /**
     * 获取通知通告清单
     */
    private void queryMessages() {
        houseModel.getMessages("Message", "0", new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                rvRemainderWoekList.refreshComplete();

                String json = JSON.toJSONString(o);
                messagesInfo.clear();
                messagesInfo.addAll(JSON.parseArray(json, MessagesInfo.class));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
