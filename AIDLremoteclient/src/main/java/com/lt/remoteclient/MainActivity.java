package com.lt.remoteclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.aidl.IRemoteService;
import com.lt.aidl.Person;

public class MainActivity extends Activity {

    private IRemoteService mRemoteService;
    private TextView mTv_result;
    private EditText mEditText;
    private boolean isConnSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.editText);
        mTv_result = (TextView) findViewById(R.id.tv_result);

        // 连接绑定远程服务
        Intent intent = new Intent();
        intent.setAction("lt.test.aidl");
        intent.setPackage("com.lt.remoteservice");
        isConnSuccess = bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void search(View view){
        if(isConnSuccess){
            // 连接成功
            int id = Integer.valueOf(mEditText.getText().toString());
            try {
                String name = mRemoteService.getName(id);
                mTv_result.setText(name);
            }catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println("连接失败!");
        }
    }

    public void searchObject(View view){
        if(isConnSuccess){
            // 连接成功
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            int id = Integer.valueOf(mEditText.getText().toString());
            try {
                Person person = mRemoteService.getPerson(id);
                mTv_result.setText("姓名："+person.getName()+"   年龄："+person.getAge());
            }catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println("连接失败!");
            Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 这里的IBinder对象service是代理对象，所以必须调用下面的方法转换成AIDL接口对象
            mRemoteService = IRemoteService.Stub.asInterface(service);
            int pid = 0;
            try {
                pid = mRemoteService.getPid();
                int currentPid = android.os.Process.myPid();
                System.out.println("currentPID: " + currentPid +"  remotePID: " + pid);
                mRemoteService.basicTypes(12, 1223, true, 12.2f, 12.3, "有梦就要去追，加油！");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("bind success! " + mRemoteService.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
            System.out.println(mRemoteService.toString() +" disconnected! ");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
