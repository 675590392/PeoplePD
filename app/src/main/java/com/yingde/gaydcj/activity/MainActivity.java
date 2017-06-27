package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.nfc.BlueReaderHelper;
import com.yingde.gaydcj.nfc.NFCReaderHelper;
import com.yingde.gaydcj.nfc.OTGReaderHelper;
import com.yingde.gaydcj.util.MyToolbar;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.senter.helper.ConsantHelper;
import cn.com.senter.helper.ShareReferenceSaver;
import cn.com.senter.model.IdentityCardZ;
import cn.com.senter.sdkdefault.helper.Error;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;

    private final static String SERVER_KEY1 = "CN.COM.SENTER.SERVER_KEY1";
    private final static String PORT_KEY1 = "CN.COM.SENTER.PORT_KEY1";
    private final static String BLUE_ADDRESSKEY = "CN.COM.SENTER.BLUEADDRESS";
    private final static String KEYNM = "CN.COM.SENTER.KEY";


    private TextView tv_info;
    private TextView nameTextView;
    private TextView sexTextView;
    private TextView folkTextView;
    private TextView birthTextView;
    private TextView addrTextView;
    private TextView codeTextView;
    private TextView policyTextView;
    private TextView validDateTextView;
    //	private TextView dnTextView;
    private ImageView photoView;
    private TextView samidTextView;

    long counttime = 0;
    long starttime = 0;
    long averagetime = 0;

    int testcount = 0;
    int successcount = 0;
    int failedcount = 0;

    private Button buttonNFC;
    //	private Button mbuttonAuto;
    private TextView readsamtype;

    private TextView mplaceHolder;

    private String server_address = "10.90.6.146";
    private int server_port = 10002;

    public static Handler uiHandler;

    private NFCReaderHelper mNFCReaderHelper;
    private OTGReaderHelper mOTGReaderHelper;
    private BlueReaderHelper mBlueReaderHelper;

    private AsyncTask<Void, Void, String> nfcTask = null;
    private int iselectNowtype = 1;
    private boolean bSelServer = false;

    private int totalcount;
    private int failecount;

    private boolean isbule;
    private boolean isNFC;
    private boolean openblueok;
    private PowerManager.WakeLock wakeLock = null;
    String et_mmsnumber = "";
    String et_imsinumber = "";
    private boolean bIsAuto;
    private IdentityCardZ myidentityCard;
    Bitmap bm;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("读取身份证");
    }

    @Override
    protected void initData() {
        uiHandler = new MyHandler(this);
        //setTitle("信通身份证阅读软件--OTG读卡");

        totalcount = 0;
        failecount = 0;
        bIsAuto = false;
        isbule = false;
        openblueok = false;

        mNFCReaderHelper = new NFCReaderHelper(this, uiHandler);
        mOTGReaderHelper = new OTGReaderHelper(this, uiHandler);
        mBlueReaderHelper = new BlueReaderHelper(this, uiHandler);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initViews();
        initShareReference();
//		LogcatFileManager.getInstance().startLogcatManager(MainActivity.this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("MainActivity", "NFC 返回调用 onNewIntent");
        super.onNewIntent(intent);

        if (mNFCReaderHelper.isNFC(intent)) {

            if (nfcTask == null) {
                buttonNFC.setBackgroundResource(R.drawable.frame_button_p);
                Log.e("MainActivity", "返回的intent可用");
                nfcTask = new NFCReadTask(intent).executeOnExecutor(Executors
                        .newCachedThreadPool());
            }
        } else {
            Log.e("MainActivity", "返回的intent不可用");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("blue", "activity onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.e("blue", "activity onStop");
        isNFC = false;

        super.onStop();
    }


    @Override
    public void onPause() {
        isNFC = false;
        Log.e("blue", "onPause");

        super.onPause();
    }

    private static final int REQUEST_CONNECT_DEVICE = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("MAIN", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        switch (requestCode) {
            case 2:
                if (resultCode == 100) {

                    this.server_address = data.getExtras().getString("address");
                    this.server_port = data.getExtras().getInt("port");

                    Log.e("MAIN", "onActivityResult: " + server_address);
                    Log.e("MAIN", "onActivityResult: " + server_port);

                    initShareReference();

                    bSelServer = false;
                }
                break;
        }
    }

    private void initShareReference() {

        if (this.server_address.length() <= 0) {
            if (ShareReferenceSaver.getData(this, SERVER_KEY1).trim().length() <= 0) {
                this.server_address = "senter-online.cn";//
            } else {
                this.server_address = ShareReferenceSaver.getData(this, SERVER_KEY1);
            }
            if (ShareReferenceSaver.getData(this, PORT_KEY1).trim().length() <= 0) {
                this.server_port = 10002;
            } else {
                this.server_port = Integer.valueOf(ShareReferenceSaver.getData(this, PORT_KEY1));
            }
        }

        mNFCReaderHelper.setServerAddress(this.server_address);
        mNFCReaderHelper.setServerPort(this.server_port);

//        mOTGReaderHelper.setServerAddress(this.server_address);
//        mOTGReaderHelper.setServerPort(this.server_port);

        //----实例化help类---
        mBlueReaderHelper.setServerAddress(this.server_address);
        mBlueReaderHelper.setServerPort(this.server_port);

    }

    private void initViews() {
        tv_info = (TextView) findViewById(R.id.tv_info);
        nameTextView = (TextView) findViewById(R.id.tv_name);
        sexTextView = (TextView) findViewById(R.id.tv_sex);
        folkTextView = (TextView) findViewById(R.id.tv_ehtnic);
        birthTextView = (TextView) findViewById(R.id.tv_birthday);
        addrTextView = (TextView) findViewById(R.id.tv_address);
        codeTextView = (TextView) findViewById(R.id.tv_number);
        policyTextView = (TextView) findViewById(R.id.tv_signed);
        validDateTextView = (TextView) findViewById(R.id.tv_validate);

        photoView = (ImageView) findViewById(R.id.iv_photo);
        buttonNFC = (Button) findViewById(R.id.buttonNFC);
        mplaceHolder = (TextView) findViewById(R.id.placeHolder);
        //屏幕大小
        WindowManager wm = this.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams p = mplaceHolder.getLayoutParams();
        p.height = height / 6;
        mplaceHolder.setLayoutParams(p);

        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams w = addrTextView.getLayoutParams();
        w.width = (width / 2) - 10;
        addrTextView.setLayoutParams(w);

        tv_info.setTextColor(Color.rgb(240, 65, 85));

        buttonNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isNFC = true;
                isbule = false;
                iselectNowtype = 1;
                buttonNFC.setBackgroundResource(R.drawable.frame_button_p);

                readCardNFC();

            }
        });

    }


    public void ButtondefDrawable() {
        buttonNFC.setBackgroundResource(R.drawable.frame_button_d);
        switch (iselectNowtype) {
            case 1:
                buttonNFC.setBackgroundResource(R.drawable.frame_button_p);
                break;
        }

    }

    /**
     * NFC 方式读卡
     */
    protected void readCardNFC() {
        mNFCReaderHelper.read();
    }

    /**
     * OTG方式读卡
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    protected void readCardOTG() {
        if (isNFC) {
            mNFCReaderHelper.disable();
            isNFC = false;
        }
    }


    private class NFCReadTask extends AsyncTask<Void, Void, String> {

        private Intent mIntent = null;

        public NFCReadTask(Intent i) {
            mIntent = i;
        }

        @Override
        protected String doInBackground(Void... params) {

            String strCardInfo = mNFCReaderHelper.readCardWithIntent(mIntent);
            return strCardInfo;
        }

        @Override
        protected void onPostExecute(String strCardInfo) {

            if (TextUtils.isEmpty(strCardInfo)) {
                failecount++;
                uiHandler.sendEmptyMessage(ConsantHelper.READ_CARD_FAILED);
                ButtondefDrawable();
                nfcTask = null;
                return;
            }

            if (strCardInfo.length() <= 2) {
                failecount++;
                readCardFailed(strCardInfo);
                ButtondefDrawable();
                nfcTask = null;
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            IdentityCardZ mIdentityCardZ = new IdentityCardZ();

            try {
                mIdentityCardZ = (IdentityCardZ) objectMapper.readValue(
                        strCardInfo, IdentityCardZ.class);
            } catch (Exception e) {

                e.printStackTrace();
                nfcTask = null;
                return;
            }
            totalcount++;
            readCardSuccess(mIdentityCardZ);

            try {

                bm = BitmapFactory.decodeByteArray(mIdentityCardZ.avatar,
                        0, mIdentityCardZ.avatar.length);


                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                photoView.setMinimumHeight(dm.heightPixels);
                photoView.setMinimumWidth(dm.widthPixels);
                photoView.setImageBitmap(bm);
                Log.e(ConsantHelper.STAGE_LOG, "图片成功");
            } catch (Exception e) {
                Log.e(ConsantHelper.STAGE_LOG, "图片失败" + e.getMessage());
            }

            ButtondefDrawable();
            nfcTask = null;
            super.onPostExecute(strCardInfo);
        }

    }

    public void resetUI() {
        this.nameTextView.setText("");
        this.sexTextView.setText("");
        this.folkTextView.setText("");
        this.birthTextView.setText("");
        this.codeTextView.setText("");
        this.policyTextView.setText("");
        this.addrTextView.setText("");
        this.validDateTextView.setText("");
        this.tv_info.setText("");

        this.photoView.setImageResource(android.R.color.transparent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//    	LogcatFileManager.getInstance().stopLogcatManager();
        finish();
    }

    class MyHandler extends Handler {
        private MainActivity activity;

        MyHandler(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case ConsantHelper.READ_CARD_SUCCESS:
                    buttonNFC.setEnabled(true);
                    ButtondefDrawable();
                    break;

                case ConsantHelper.SERVER_CANNOT_CONNECT:
                    activity.tv_info.setText("服务器连接失败! 请检查网络。");
                    buttonNFC.setEnabled(true);
                    ButtondefDrawable();
                    break;

                case ConsantHelper.READ_CARD_FAILED:
                    activity.tv_info.setText("无法读取信息请重试!");
                    buttonNFC.setEnabled(true);
                    ButtondefDrawable();
                    break;

                case ConsantHelper.READ_CARD_WARNING:
                    String str = (String) msg.obj;

                    if (str.indexOf("card") > -1) {
                        activity.tv_info.setText("读卡失败: 卡片丢失,或读取错误!");
                    } else {
                        String[] datas = str.split(":");

                        activity.tv_info.setText("网络超时 错误码: " + Integer.toHexString(new Integer(datas[1])));
                    }
                    //activity.tv_info.setText("请移动卡片在合适位置!");
                    buttonNFC.setEnabled(true);
                    ButtondefDrawable();
                    break;

                case ConsantHelper.READ_CARD_PROGRESS:
                    int progress_value = (Integer) msg.obj;
                    activity.tv_info.setText("正在读卡......");//,进度：+ progress_value + "%"
                    break;

                case ConsantHelper.READ_CARD_START:
                    activity.resetUI();
                    activity.tv_info.setText("正在读卡......");
                    break;
                case Error.ERR_CONNECT_SUCCESS:
                    String devname = (String) msg.obj;
                    activity.tv_info.setText(devname + "连接成功!");
                    //mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" + String.format("%d", failecount));
                    break;
                case Error.ERR_CONNECT_FAILD:
                    String devname1 = (String) msg.obj;
                    activity.tv_info.setText(devname1 + "连接失败!");
                    //mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" + String.format("%d", failecount));
                    break;
                case Error.ERR_CLOSE_SUCCESS:
                    activity.tv_info.setText((String) msg.obj + "断开连接成功");
                    break;
                case Error.ERR_CLOSE_FAILD:
                    activity.tv_info.setText((String) msg.obj + "断开连接失败");
                    break;
                case Error.RC_SUCCESS:
                    String devname12 = (String) msg.obj;
                    activity.tv_info.setText(devname12 + "连接成功!");
                    break;

            }
        }

    }

    private void readCardFailed(String strcardinfo) {
        int bret = Integer.parseInt(strcardinfo);
        switch (bret) {
            case -1:
                tv_info.setText("服务器连接失败!");
                break;
            case 1:
                tv_info.setText("读卡失败!");
                break;
            case 2:
                tv_info.setText("读卡失败!");
                break;
            case 3:
                tv_info.setText("网络超时!");
                break;
            case 4:
                tv_info.setText("读卡失败!");
                break;
            case -2:
                tv_info.setText("读卡失败!");
                break;
            case 5:
                tv_info.setText("照片解码失败!");
                break;
        }

    }

    private void readCardSuccess(IdentityCardZ identityCard) {
        if (identityCard != null) {
            myidentityCard = identityCard;
            nameTextView.setText(identityCard.name);
            sexTextView.setText(identityCard.sex);
            folkTextView.setText(identityCard.ethnicity);
            birthTextView.setText(identityCard.birth);
            codeTextView.setText(identityCard.cardNo);
            policyTextView.setText(identityCard.authority);
            addrTextView.setText(identityCard.address);
            //dnTextView.setText(identityCard.dn);
            //samidTextView.setText(identityCard.SAMID);
            validDateTextView.setText(identityCard.period);
            tv_info.setText("读取成功!");
        }

    }
    @OnClick(R.id.button_confirm)
    public void onClick() {
        Intent intent = new Intent();
        if (myidentityCard != null) {
            intent.putExtra("name", myidentityCard.name);//姓名
            intent.putExtra("ethnicity", myidentityCard.ethnicity);//民族
            intent.putExtra("cardNo", myidentityCard.cardNo);//身份证
            intent.putExtra("address", myidentityCard.address);//地址
            intent.putExtra("sex", myidentityCard.sex);//性别
            intent.putExtra("authority", myidentityCard.authority);//签发机关
            intent.putExtra("birth", myidentityCard.birth);//出生日期
            intent.putExtra("period", myidentityCard.period);//签发日期
            intent.putExtra("avatar", bm);//照片
        }
        setResult(2122, intent);
        finish();
    }

}
