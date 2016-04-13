package cn.ibona.t1.common.model.db.ormlite.obj_save.tools;

import android.content.Context;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Timer;

import cn.ibona.t1.common.model.db.ormlite.obj_save.DocumentMoDao;
import cn.ibona.t1.common.model.db.ormlite.obj_save.DocumentMod;


/**
 * Created by busylee on 2015/12/4.
 * 将对象的保存工具化，对外只需提供简单的对象和Key数组
 */
public class CacheObjectUtil {

    private DocumentMoDao dao = null;
    private Gson gson = null;

    public CacheObjectUtil(){

    }

    public CacheObjectUtil(Context context){
        dao = new DocumentMoDao(context);
    }


    /**
     * 一周清一次缓存
     * @param context
     * @deprecated
     */
    public void clearCacheTimer(Context context){
        Timer timer = new Timer(true);
        timer.schedule(ClearObjectCacheTask.getInstance(context),0,7*24*60*60*1000);
    }

    /**
     * 缓存
     * @param obj
     * @return
     */
    public boolean save(Serializable obj, String... model){
        String data = toString(model);
        if (data.equals(""))
            return false;
        gson = new Gson();
        String syn = gson.toJson(obj);
        DocumentMod mod = new DocumentMod();
        mod.setModel(data);
        mod.setCreate_d(dao.getCurrentDateM());
        mod.setSyn_data(syn);
        int flag = dao.add(mod);
        if (flag==1)
            return true;
        return false;
    }

    /**
     * 获取方法
     * @param model
     * @param type
     * @param <T>
     * @return
     */
    public <T> T get(Type type, String... model){
        String data = toString(model);
        if (data.equals(""))
            return null;
        gson = new Gson();
        DocumentMod mod = dao.queryFor(data);
        if(mod!=null){
            mod.setIsUsed(1);
            dao.update(mod);//标记数据已使用
            return gson.fromJson(mod.getSyn_data(), type);
        }
        return null;
    }

    /**
     * 删除方法
     * @return 是否删除成功
     */
    public boolean delete(String... model){
        String data = toString(model);
        if (data.equals(""))
            return false;
        int flag = dao.delete(data);
        if (flag==1)
            return true;
        return false;
    }


    private String toString(String... model){
        if(model==null || model.length==0)
            return  "";
        StringBuilder sb = new StringBuilder();
        for (String temp:model){
            sb.append(temp).append(",");
        }
        String data = sb.toString();
        data = data.substring(0,data.lastIndexOf(","));
        return data;
    }



}
