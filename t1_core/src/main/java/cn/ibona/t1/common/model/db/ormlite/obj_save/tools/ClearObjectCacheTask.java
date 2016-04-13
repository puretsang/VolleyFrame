package cn.ibona.t1.common.model.db.ormlite.obj_save.tools;

import android.content.Context;
import android.util.Log;

import java.util.TimerTask;

import cn.ibona.t1.common.model.db.ormlite.obj_save.DocumentMoDao;


public class ClearObjectCacheTask extends TimerTask {
	private static boolean isRunning = false;
	private static ClearObjectCacheTask intance = null;
	private  static DocumentMoDao dao = null;

	private ClearObjectCacheTask(){

	}

	/** 返回RequestQueue单例 **/
	public static ClearObjectCacheTask getInstance(Context context) {
		if (intance == null) {
			synchronized (ClearObjectCacheTask.class) {
				if (intance == null) {
					intance = new ClearObjectCacheTask();
					dao = new DocumentMoDao(context);
				}
			}
		}
		return intance;
	}

	@Override
	public void run() {
		clearCache();
	}

	public  synchronized void clearCache(){
		try {
			if(!isRunning){
				isRunning = true;
				boolean flag = dao.deleteForClearCache();
				if (flag)
					Log.e("timer","任务完成！");
				isRunning = false;
				
			}else{
				Log.w("timer", "上一次任务未完成！");
			}
		} catch (Exception e) {
			Log.e("timer",e.getMessage());
			isRunning = false;
		}
	}

	
}
