package sage.lu6gmail.com.posxing;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nld.cloudpos.aidl.AidlDeviceService;
import com.nld.cloudpos.aidl.printer.AidlPrinter;
import com.nld.cloudpos.aidl.printer.AidlPrinterListener;
import com.nld.cloudpos.aidl.printer.PrintItemObj;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static sage.lu6gmail.com.posxing.R.drawable.b;

public class MainActivity extends AppCompatActivity {
    private HandWrite handwriteview;
    private Button clear;
    private ImageView iv_smallcat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handwriteview = (HandWrite) findViewById(R.id.handwriteview);
        iv_smallcat = (ImageView) findViewById(R.id.iv_smallcat);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handwriteview.clear();
//                toXingPos();
//                toPrintBitmap();
                toPrintText();
            }
        });


        // 连接绑定远程服务
        Intent intent = new Intent();
        intent.setAction("nld_cloudpos_device_service");
        intent.setPackage("com.newland.deviceservice.suoersi");
        isConnSuccess = bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void toPrintText() {
        if (isConnSuccess) {
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            try {
                ArrayList<PrintItemObj> printItemObjs = new ArrayList<>();
                PrintItemObj printItemObj = new PrintItemObj("1232124654654654646\nzhigniadfadgandgsang");
                printItemObjs.add(printItemObj);
                aidlPrinter.open();
                aidlPrinter.printText(printItemObjs);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), b);
                aidlPrinter.printImage(2, bitmap);
                aidlPrinter.start(new AidlPrinterListener.Stub() {
                    @Override
                    public void onError(int i) throws RemoteException {

                    }

                    @Override
                    public void onPrintFinish() throws RemoteException {
                        Toast.makeText(MainActivity.this, "onPrintFinish", Toast.LENGTH_SHORT).show();
                    }
                });

                aidlPrinter.paperSkip(2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    AidlPrinter aidlPrinter;
    boolean isConnSuccess;

    private void toPrintBitmap() {

        if (isConnSuccess) {
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), b);
                aidlPrinter.open();
                aidlPrinter.printImage(2, bitmap);
                aidlPrinter.start(new AidlPrinterListener.Stub() {

                    @Override
                    public void onError(int i) throws RemoteException {

                    }

                    @Override
                    public void onPrintFinish() throws RemoteException {
                        Toast.makeText(MainActivity.this, "onPrintFinish", Toast.LENGTH_SHORT).show();
                    }
                });
                aidlPrinter.paperSkip(2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        }

    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AidlDeviceService aidlDeviceService = AidlDeviceService.Stub.asInterface(service);
            try {
                aidlPrinter = AidlPrinter.Stub.asInterface(aidlDeviceService.getPrinter());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        //
        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidlPrinter = null;
        }
    };

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    private void toXingPos() {
        //YYYYMMDDhhmmss
        try {
            String stringDate = getStringDate();
            ComponentName component = new ComponentName("com.newland.caishen", "com.newland.caishen.ui.activity.MainActivity");
            Intent intent = new Intent();
            intent.setComponent(component);

            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0200");//0300 查询   0200 消费和撤销
            bundle.putString("pay_tp", "0");
            bundle.putString("proc_tp", "00");
            bundle.putString("proc_cd", "200000");//撤销
//            bundle.putString("proc_cd", "000000");//消费
//            bundle.putString("amt", "0.01");
//            bundle.putString("order_no", "100012345678932101");
            bundle.putString("appid", "sage.lu6gmail.com.posxing");
            bundle.putString("time_stamp", stringDate);
//            bundle.putString("print_info", "订单商品明细单价等xxxxxx");
            intent.putExtras(bundle);
            this.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            //TODO:
        } catch (Exception e) {
            //TODO:
        }

    }
   public String getPZH(){
       String s="{\"mername\":\"杭州有云科技有限公司\",\"merid\":\"85733014812L002\",\"termid\":\"95006486\",\"acqno\":\"48570000   \",\"iisno\":\"04233310   \",\"expdate\":\"2701\",\"batchno\":\"000004\",\"systraceno\":\"000070\",\"authcode\":\"000000\",\"priaccount\":\"623061********3673\",\"refernumber\":\"071994203174\",\"translocaltime\":\"151833\",\"translocaldate\":\"20170719\",\"transamount\":\"0.01\"}";
       JSONObject jsonObject = new JSONObject();


       return "";
   }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        if (requestCode == 1 & bundle != null) {
            switch (resultCode) {
                // 支付成功
                case Activity.RESULT_OK:
                    String msgTp = bundle.getString("msg_tp");
                    if (TextUtils.equals(msgTp, "0210")) {
                        // TODO:
                        String txndetail = bundle.getString("txndetail");
                        String order_no = bundle.getString("order_no");
                        Log.e("txndetail", "txndetail==" + txndetail);
                        Log.e("txndetail", "order_no==" + order_no);
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    }
                    break;
                // 支付取消
                case Activity.RESULT_CANCELED:
                    String reason = bundle.getString("reason");
                    if (reason != null) {
                        // TODO:
                        Toast.makeText(MainActivity.this, "QUXIAO"+reason, Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    // TODO:
                    break;
            }
        }

    }
}
