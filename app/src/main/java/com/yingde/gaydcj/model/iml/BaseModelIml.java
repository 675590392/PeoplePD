package com.yingde.gaydcj.model.iml;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.yingde.gaydcj.entity.Entity;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.http.RetrofitClient;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.util.ToastUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tanghao on 2017/4/6.
 */

public class BaseModelIml {

    protected Context mContext;
    protected ProgressDialog progressDialog;
    protected String tokenId;
    protected boolean isShowDialog = true;


    public BaseModelIml(Context mContext) {
        this.mContext = mContext;
        tokenId = CommonUtil.getTokenId(mContext);

        initDialog();
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    private ProgressDialog initDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle(null);
            progressDialog.setMessage("加载中");
            progressDialog.setCancelable(false);
        }
        return progressDialog;
    }

    public void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected void checkEntity(Entity entity, RequestListener requestListener) {
        if (entity.getState() != null && entity.getState().equals("100")) {
            if (requestListener != null)
                requestListener.onSuccess(entity.getData(), entity.getToken());
        } else if(entity.getState() != null && entity.getState().equals("101")){
            if (requestListener != null){
                if (entity.getMsg() != null && !entity.getMsg().equals("") && !entity.getMsg().equals("null")) {
                    ToastUtil.showToast(mContext, entity.getMsg());
                }else{
                    ToastUtil.showToast(mContext, "请求失败");
                }}
        }else{
            if (requestListener != null)
                requestListener.onFail();
        }
        if (entity.getMsg() != null && !entity.getMsg().equals("") && !entity.getMsg().equals("null"))
            ToastUtil.showToast(mContext, entity.getMsg());
    }

    protected void doError(Throwable e, RequestListener requestListener) {
        Log.v("wh", "请求失败--->>>" + e.getMessage());
        ToastUtil.showToast(mContext, "请求数据失败");

        if (requestListener != null)
            requestListener.onFail();

    }

    /**
     * 检查是否有网络
     *
     * @return
     */
    protected boolean checkNetwork() {
        ConnectivityManager cwjManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        boolean flag = info != null && info.isAvailable();
        if (!flag)
//            ToastUtil.showToast(this, "无法连接网络，请检查设置");
            new AlertDialog.Builder(mContext)
                    .setTitle("小贴士")
                    .setMessage("网络异常，请检查网络")
                    .setPositiveButton("确定", null)
                    .show();

        return flag;
    }

    /**
     * 发送常规请求
     *
     * @param urlPath
     * @param jsonParas
     * @param requestListener
     */
    protected void sendRequest(String urlPath, String jsonParas, RequestListener requestListener) {
        if (checkNetwork())
            RetrofitClient.getCommonApi().request(tokenId, urlPath, jsonParas)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseModelIml.SubscriberIml<Entity>(requestListener));
    }

    public class SubscriberIml<T> extends Subscriber<T> {

        private RequestListener requestListener;

        public SubscriberIml(RequestListener requestListener) {
            this.requestListener = requestListener;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (isShowDialog)
                showProgressDialog();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (isShowDialog)
                dismissProgressDialog();
            doError(e, requestListener);
        }

        @Override
        public void onNext(T t) {
            if (isShowDialog)
                dismissProgressDialog();
            checkEntity((Entity) t, requestListener);
        }

    }
}
