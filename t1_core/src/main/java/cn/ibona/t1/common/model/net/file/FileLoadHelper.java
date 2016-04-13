package cn.ibona.t1.common.model.net.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author qun
 */
public class FileLoadHelper {

	private boolean DEBUG = false;

	public static final String CACHE_SUFFIXES = ".tmp";

	public static final int START = 0; //开始下载
	public static final int REFRES = 1; //刷新进度
	public static final int END = 2; //下载完成
	public static final int ERROR = 3; //发生异常

	private String mUrl;
	private String mFullPath;
	private String mPath;
	private String mFileName;
	private boolean mDeleteExist = false;

	private int mFileSize;

	private LoadListener mListener;

	public FileLoadHelper(String url, String path, String fileName, LoadListener listener) {
		init(url, path, fileName, listener);
	}

	public FileLoadHelper(String url, String fullPath, LoadListener listener){
		int index = fullPath.lastIndexOf(File.separator);
		String fileName = fullPath.substring(index+1);
		String path = fullPath.substring(0, index);
		init(url, path, fileName, listener);
	}

	private void init(String url, String path, String fileName, LoadListener listener){
		mUrl = url;
		mFileName = fileName;
		mPath = path;

		mFullPath = mPath + File.separator + mFileName;
		mListener = listener;
	}

	/**
	 * 删除已存在
	 */
	public void setDeleteExist(boolean deleteExist){
		mDeleteExist = deleteExist;
	}

	public void load(){
		new Thread(){
			public void run(){
				if(DEBUG) Log.e("FileLoadHelper", "下载位置：" + mFullPath + " # 来源：" + mUrl);
				loadFile(mUrl, mFullPath, mDeleteExist);
			}
		}.start();
	}

	public void loadFile(String url,String fullPath, boolean deleteExist){

		//如果存在，直接返回成功
		File file = new File(fullPath);
		if(file.exists() ){
			if(deleteExist){
				file.delete();
			}else{
				sendMsg(END, fullPath);
				return;
			}
		}

		String cachePath = fullPath+CACHE_SUFFIXES;
		//缓存文件，如果存在，直接删除
		file = new File(cachePath);
		if(file.exists()){
			file.delete();
		}

		InputStream is = null;
		FileOutputStream fos = null;

		try{
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			is = conn.getInputStream();
			mFileSize = conn.getContentLength();//根据响应获取文件大小
			if (mFileSize <= 0) {
				throw new RuntimeException("无法获知文件大小 ");
			}
			if (is == null) throw new RuntimeException("stream is null");
			fos = new FileOutputStream(cachePath);

			//把数据存入路径+文件名
			byte buf[] = new byte[1024];
			mLoadSize = 0;

			sendMsg(START, null);
			do{
				int numread = is.read(buf);
				if (numread == -1) break;
				fos.write(buf, 0, numread);
				mLoadSize += numread;
				sendMsg(REFRES, null);//更新进度条
			} while (true);
			boolean success = new File(cachePath).renameTo(new File(fullPath));//改名
			if(success)	sendMsg(END, fullPath);//通知下载完成
			else sendMsg(ERROR, "file rename fail!");

		}catch (Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
			}
		}
	}

	private void sendMsg(int flag, Object obj)
	{
		Message msg = new Message();
		msg.what = flag;
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	private int mLoadSize;

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{//定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted())
			{
				switch (msg.what)
				{
					case START:
						if(mListener!=null) mListener.onStart(mFileSize);
						if(DEBUG) Log.e("FileLoadHelper", "load# size= " + mFileSize);
					case REFRES:
						if(mListener!=null) mListener.onRefres(mLoadSize, mFileSize);

						int result = mLoadSize * 100 / mFileSize;
						if(DEBUG) Log.e("FileLoadHelper", "load#" + result +"%");
						break;
					case END:
						if(mListener!=null) mListener.onEnd((String)msg.obj);
						if(DEBUG) Log.e("FileLoadHelper", "load#" + "end");
						break;
					case ERROR:
						if(mListener!=null) mListener.onError((String)msg.obj);
						if(DEBUG) Log.e("FileLoadHelper", "load#" + "error");
						break;
				}
			}
			super.handleMessage(msg);
		}
	};

	public void setLoadListener(LoadListener listener){
		mListener = listener;
	}

	public static interface LoadListener{
		public void onStart(int fileSize);
		public void onRefres(int progress, int fileSize);
		public void onEnd(String file);
		public void onError(String msg);
	}

}

