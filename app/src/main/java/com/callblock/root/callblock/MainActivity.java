package com.callblock.root.callblock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity {
    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB= new DBhelper(this);
        TelephonyManager telephonyManager= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    PhoneStateListener phoneStateListener= new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // super.onCallStateChanged(state, incomingNumber);
            String number = incomingNumber;
            String num="864497";
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Toast.makeText(getApplicationContext(), "phone is ringing", Toast.LENGTH_LONG).show();
                if(number.contains(num)) {
                    TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    Class clazz;
                    try {
                        clazz = Class.forName(telephony.getClass().getName());
                        Method method = clazz.getDeclaredMethod("getITelephony");
                        method.setAccessible(true);
                        ITelephony telephonyService = (ITelephony) method.invoke(telephony);
                        telephonyService.endCall();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Toast.makeText(getApplicationContext(), "phone is on a call", Toast.LENGTH_LONG).show();
            }
            if (state == TelephonyManager.CALL_STATE_IDLE) {
                Toast.makeText(getApplicationContext(), "phone is idle", Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
