package com.irainbot.action.wifi;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 扫描热点，找到符合规格的热点并连接
 * 
 */

public class WifiScanRsultBroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
	}
//
//	private Context mContext;
//	private WifiApAdminActivity wifiApAdminActivity;
//	private WifiManager mWifimanager;
//
//	private String ssID;
//	
//	private List<WifiConfiguration> configurations = new ArrayList<WifiConfiguration>();
//	 // 网络连接列表  
//    private List<WifiConfiguration> mWifiConfiguration; 
//	private List<ScanResult> wifiList;
//
//    
//	public WifiScanRsultBroadCast(Context context){
//		
//		this.mContext = context;
//		wifiApAdminActivity = (WifiApAdminActivity)context;
//		
//	}
//	
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		Log.i("WifiBroadCast", "into onReceive(Context context, Intent intent)");
//		if(intent.getAction().equalsIgnoreCase(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
//			mWifimanager = BaseActivity.mWifimanager;
//			wifiList = mWifimanager.getScanResults();
//			Log.i("WifiBroadCast", "into onReceive(Context context, Intent intent) wifiList= "+wifiList);
//			if (wifiList == null || wifiList.size() == 0||WifiApAdminActivity.isConnecting){
//				return;
//			}
//			configurations = BaseActivity.mWifimanager.getConfiguredNetworks();
//			for(WifiConfiguration config :configurations){
//                Log.i("WifiBroadCast config pro 优先级", "  "+config.priority+"  config SSID="+config.SSID);	
//                Log.i("WifiBroadCast config statu =", "  "+config.status);	
// 			}
//		   onReceiveNewNetworks(wifiList);
//		Log.i("WifiBroadCast", "out onReceive(Context context, Intent intent)");
//	  }
//   }
//	
//	
//	/*当搜索到新的wifi热点时判断该热点是否符合规格*/
//	public void onReceiveNewNetworks(List<ScanResult> wifiList){
//	Log.i("WifiBroadCast", "into onReceiveNewNetworks(List<ScanResult> wifiList)");
//	    for(ScanResult result:wifiList){
//	       System.out.println(result.SSID);
//	       if((result.SSID).contains(Global.SSID)){
//	    	   synchronized (this) {
//	    		    ssID =result.SSID;
//	    		    new Thread(new Runnable() {
//						@Override
//						public void run() {
//			    			connectToHotpot();
//						}
//					}).start();
//	    	  }
//	     }
//	 }
//  }
//	
//	/*连接到热点*/
//	public void connectToHotpot(){
//		
//		Log.i("WifiBroadCast", "into  connectToHotpot()");
//		if(ssID==null||ssID.equals("")){
//			return;
//		}
//		WifiConfiguration config = WifiApConfigurationAdmin.getInstance(mContext).CreateWifiInfo(ssID,Global.PASSWORD,1);
////		WifiConfiguration config = createConnectWifiConfig();
//		wifiApAdminActivity.enableNetwork(config);
//		Log.i("WifiBroadCast", "out  connectToHotpot()");
//		
//	}
} 