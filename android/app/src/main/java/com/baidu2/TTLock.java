package com.baidu2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.content.Intent;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;

import com.facebook.react.bridge.WritableMap;
import com.ttlock.bl.sdk.api.TTLockAPI;
import com.ttlock.bl.sdk.callback.TTLockCallback;
import com.ttlock.bl.sdk.entity.DeviceInfo;
import com.ttlock.bl.sdk.entity.Error;
import com.ttlock.bl.sdk.entity.LockData;
import com.ttlock.bl.sdk.scanner.ExtendedBluetoothDevice;

public class TTLock extends ReactContextBaseJavaModule {

    public static Context mContext;
    public static ExtendedBluetoothDevice mExtendedBluetoothDevice;
    private Promise mTTLockPromise;
    /**
     * TTLockAPI
     */
    public static TTLockAPI mTTLockAPI;

    // 2 Instantiate TTLockCallback Object
    public TTLock(ReactApplicationContext reactContext) {
        super(reactContext);
        TTLockSendEvent.myContext = getReactApplicationContext();
    }


    // 3 Init TTLockAPI Object
    @ReactMethod
    public void init() {
        mContext = getReactApplicationContext();
        initTTLock();
    }

    // 4 Turn on Bluetooth
    @ReactMethod
    public void requestBleEnable() {
        mTTLockAPI.requestBleEnable(getCurrentActivity());
    }

    // 5 Start Bluetooth Service
    @ReactMethod
    public void startBleService() {
        mTTLockAPI.startBleService(mContext);
    }

    // 6 Start Bluetooth Scan
    @ReactMethod
    public void startBTDeviceScan() {
        mTTLockAPI.startBTDeviceScan();
    }
    // 通过mac地址连接设备
    @ReactMethod
    public void connect(String address) {
        mTTLockAPI.connect(address);

    }
    // 通过ExtendedBluetoothDevice对象连接设备 本次用不到 不写
    @ReactMethod
    public void connect(ExtendedBluetoothDevice device){

    }
    // 断开蓝牙连接
    @ReactMethod
    public void disconnect(){
        mTTLockAPI.disconnect();
        TTLockSendEvent event = new TTLockSendEvent();
        event.sendEvent(getReactApplicationContext(), "disconnectSuccess",null);
    }
    // Lock Initialize 用不到 不实现
    @ReactMethod
    public void lockInitialize(ExtendedBluetoothDevice extendedBluetoothDevice){

    }
    // Ekey 解锁
    @ReactMethod
    public void unlockByUser(int uid,String lockVersion,int startDate, int endDate, String unlockKey, int lockFlagPos, String aesKeyStr, int timezoneOffset){
        mTTLockAPI.unlockByUser(mExtendedBluetoothDevice,uid,lockVersion,new Long((long)startDate),new Long((long)endDate),unlockKey,lockFlagPos,aesKeyStr,new Long((long)timezoneOffset));
    }
    /**
     * TTLock initial
     */
    private void initTTLock() {
        Log.d("TGA", "initTTLock create TTLockAPI");
        mTTLockAPI = new TTLockAPI(mContext, mTTLockCallback);
    }

    /**
     * TTLock Callback
     */
    private TTLockCallback mTTLockCallback = new TTLockCallback() {
        @Override
        public void onFoundDevice(ExtendedBluetoothDevice extendedBluetoothDevice) {
            Log.d("TGA", "onFoundDevice");
            WritableMap map = Arguments.createMap();
            map.putString("name", extendedBluetoothDevice.getName());
            map.putString("address", extendedBluetoothDevice.getAddress());
            map.putInt("remoteUnlockSwitch", extendedBluetoothDevice.getRemoteUnlockSwitch());
            map.putInt("lockType", extendedBluetoothDevice.getLockType());
            map.putInt("date", (int) extendedBluetoothDevice.getDate());
            map.putInt("parkStatus", extendedBluetoothDevice.getParkStatus());
            TTLockSendEvent event = new TTLockSendEvent();
            event.sendEvent(getReactApplicationContext(), "onFoundDevice", map);
        }

        @Override
        public void onDeviceConnected(ExtendedBluetoothDevice extendedBluetoothDevice) {
            mExtendedBluetoothDevice = extendedBluetoothDevice;
            TTLockSendEvent event = new TTLockSendEvent();
            event.sendEvent(getReactApplicationContext(), "onDeviceConnected",null);
        }

        @Override
        public void onDeviceDisconnected(ExtendedBluetoothDevice extendedBluetoothDevice) {
            mExtendedBluetoothDevice = null;
            TTLockSendEvent event = new TTLockSendEvent();
            event.sendEvent(getReactApplicationContext(), "onDeviceDisconnected",null);
        }

        @Override
        public void onGetLockVersion(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, int i3, int i4, Error error) {

        }

        @Override
        public void onLockInitialize(ExtendedBluetoothDevice extendedBluetoothDevice, LockData lockData, Error error) {

        }

        @Override
        public void onResetEKey(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onSetLockName(ExtendedBluetoothDevice extendedBluetoothDevice, String s, Error error) {

        }

        @Override
        public void onSetAdminKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, String s, Error error) {

        }

        @Override
        public void onSetDeletePassword(ExtendedBluetoothDevice extendedBluetoothDevice, String s, Error error) {

        }

        @Override
        public void onUnlock(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, long l, Error error) {
            TTLockSendEvent event = new TTLockSendEvent();
            event.sendEvent(getReactApplicationContext(), "onUnlock",null);
        }

        @Override
        public void onSetLockTime(ExtendedBluetoothDevice extendedBluetoothDevice, Error error) {

        }

        @Override
        public void onGetLockTime(ExtendedBluetoothDevice extendedBluetoothDevice, long l, Error error) {

        }

        @Override
        public void onResetKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, String s, long l, Error error) {

        }

        @Override
        public void onSetMaxNumberOfKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onResetKeyboardPasswordProgress(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onResetLock(ExtendedBluetoothDevice extendedBluetoothDevice, Error error) {

        }

        @Override
        public void onAddKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, long l, long l1, Error error) {

        }

        @Override
        public void onModifyKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, String s1, Error error) {

        }

        @Override
        public void onDeleteOneKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, Error error) {

        }

        @Override
        public void onDeleteAllKeyboardPassword(ExtendedBluetoothDevice extendedBluetoothDevice, Error error) {

        }

        @Override
        public void onGetOperateLog(ExtendedBluetoothDevice extendedBluetoothDevice, String s, Error error) {

        }

        @Override
        public void onSearchDeviceFeature(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, Error error) {

        }

        @Override
        public void onAddICCard(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, long l, Error error) {

        }

        @Override
        public void onModifyICCardPeriod(ExtendedBluetoothDevice extendedBluetoothDevice, int i, long l, long l1, long l2, Error error) {

        }

        @Override
        public void onDeleteICCard(ExtendedBluetoothDevice extendedBluetoothDevice, int i, long l, Error error) {

        }

        @Override
        public void onClearICCard(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onSetWristbandKeyToLock(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onSetWristbandKeyToDev(Error error) {

        }

        @Override
        public void onSetWristbandKeyRssi(Error error) {

        }

        @Override
        public void onAddFingerPrint(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, long l, Error error) {

        }

        @Override
        public void onAddFingerPrint(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, long l, int i2, Error error) {

        }

        @Override
        public void onFingerPrintCollection(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onFingerPrintCollection(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, Error error) {

        }

        @Override
        public void onModifyFingerPrintPeriod(ExtendedBluetoothDevice extendedBluetoothDevice, int i, long l, long l1, long l2, Error error) {

        }

        @Override
        public void onDeleteFingerPrint(ExtendedBluetoothDevice extendedBluetoothDevice, int i, long l, Error error) {

        }

        @Override
        public void onClearFingerPrint(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onSearchAutoLockTime(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, int i3, Error error) {

        }

        @Override
        public void onModifyAutoLockTime(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, Error error) {

        }

        @Override
        public void onReadDeviceInfo(ExtendedBluetoothDevice extendedBluetoothDevice, DeviceInfo deviceInfo, Error error) {

        }

        @Override
        public void onEnterDFUMode(ExtendedBluetoothDevice extendedBluetoothDevice, Error error) {

        }

        @Override
        public void onGetLockSwitchState(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, Error error) {

        }

        @Override
        public void onLock(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, long l, Error error) {

        }

        @Override
        public void onScreenPasscodeOperate(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, Error error) {

        }

        @Override
        public void onRecoveryData(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onSearchICCard(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, Error error) {

        }

        @Override
        public void onSearchFingerPrint(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, Error error) {

        }

        @Override
        public void onSearchPasscode(ExtendedBluetoothDevice extendedBluetoothDevice, String s, Error error) {

        }

        @Override
        public void onSearchPasscodeParam(ExtendedBluetoothDevice extendedBluetoothDevice, int i, String s, long l, Error error) {

        }

        @Override
        public void onOperateRemoteUnlockSwitch(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, int i3, Error error) {

        }

        @Override
        public void onGetElectricQuantity(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }

        @Override
        public void onOperateAudioSwitch(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, Error error) {

        }

        @Override
        public void onOperateRemoteControl(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, Error error) {

        }

        @Override
        public void onOperateDoorSensorLocking(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, int i2, Error error) {

        }

        @Override
        public void onGetDoorSensorState(ExtendedBluetoothDevice extendedBluetoothDevice, int i, int i1, Error error) {

        }

        @Override
        public void onSetNBServer(ExtendedBluetoothDevice extendedBluetoothDevice, int i, Error error) {

        }
    };

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {

        }
    };

    @Override
    public String getName() {
        return "TTLock";
    }


}