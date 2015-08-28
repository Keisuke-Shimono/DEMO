package com.example.kouki.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.io.FileDescriptor;

/**
 * Created by Kouki on 2015/05/28.
 */
public class MyService extends Service{
   @Override
   public void onCreate() {
       Log.d("Service", "onCreate");
       Toast.makeText(this, "onCreate" , Toast.LENGTH_SHORT).show();
   }

   @Override
   public int onStartCommand(Intent intent , int flags , int startId) {
        Log.d("Service","StartCommand");
        Toast.makeText(this,"StartCommand",Toast.LENGTH_SHORT).show();
        return START_STICKY;
   }

   @Override
   public void onDestroy() {
       Log.d("Service","onDestroy");
       Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
   }


    @Override
    public IBinder onBind(Intent intent) {
        return new IBinder() {
            @Override
            public String getInterfaceDescriptor() throws RemoteException {
                return null;
            }

            @Override
            public boolean pingBinder() {
                return false;
            }

            @Override
            public boolean isBinderAlive() {
                return false;
            }

            @Override
            public IInterface queryLocalInterface(String descriptor) {
                return null;
            }

            @Override
            public void dump(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                return false;
            }

            @Override
            public void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {

            }

            @Override
            public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
                return false;
            }
        };
    }

    public void startService(){

    }


    public void stopService() {

    }

}
