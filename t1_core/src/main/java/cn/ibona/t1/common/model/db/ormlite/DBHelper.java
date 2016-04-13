package cn.ibona.t1.common.model.db.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.ibona.t1.common.model.db.ormlite.obj_save.DocumentMod;


/**
 * 功能：数据库帮助类
 * @author devilwwj
 *
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    /**
     * 数据库名字
     */
    private static final String DB_NAME = "test.db";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;

    /**
     * 用来存放Dao的地图
     */
    private Map<String, Dao> daos = new HashMap<String, Dao>();



    private static DBHelper instance;


    /**
     * 获取单例
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }


    /**
     * 构造方法
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 这里创建表
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        // 创建表
        try {
            TableUtils.createTable(connectionSource, DocumentMod.class);//TODO 如何将创建的表与core解耦，让使用者自行扩展

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里进行更新表操作
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
            int newVersion) {
        try
        {
            TableUtils.dropTable(connectionSource, DocumentMod.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao=daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }





}