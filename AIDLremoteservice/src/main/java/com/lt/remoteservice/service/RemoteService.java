package com.lt.remoteservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.lt.aidl.IRemoteService;
import com.lt.aidl.Person;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lt on 2016/3/6.
 */
public class RemoteService extends Service{

    private String[] names = {"吕布","关羽","赵子龙","张飞"};

    private List<Person> persons = new ArrayList<Person>();


    /**
     * 返回一个RemoteService代理对象IBinder给客户端使用
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        persons.add(new Person("吕布",18));
        persons.add(new Person("关羽",19));
        persons.add(new Person("赵子龙",20));
        persons.add(new Person("张飞",21));
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("basicTypes aDouble: " + aDouble +" anInt: " + anInt+" aBoolean " + aBoolean+" aString " + aString);
        }

        @Override
        public int getPid() throws RemoteException {
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("RemoteService getPid ");
            return android.os.Process.myPid();
        }

        @Override
        public String getName(int id) throws RemoteException {
            return names[id];
        }

        @Override
        public Person getPerson(int id) throws RemoteException {
            return persons.get(id);
        }
    };
}
