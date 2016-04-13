package cn.ibona.t1.common.model.db.ormlite.obj_save;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.ibona.t1.common.model.db.ormlite.DBHelper;


/**
 * Created by busylee on 2015/12/1.
 * Object 保存表的Dao
 */
public class DocumentMoDao {

    private Dao<DocumentMod, Integer> modDao;
    private DBHelper dbHelper;

    public DocumentMoDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            modDao = dbHelper.getDao(DocumentMod.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(DocumentMod mode) {
        int flag = 0;
        try {
            flag = modDao.create(mode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int delete(DocumentMod mode) {
        int flag = 0;
        try {
            flag = modDao.delete(mode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int delete(String key) {
        int flag = 0;
        try {
            DeleteBuilder builder = modDao.deleteBuilder();
            builder.where().eq("model", key);
            flag =builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int update(DocumentMod mode) {
        int flag = 0;
        try {
            flag = modDao.update(mode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteForClearCache() {
        boolean flag = false;
        try {
            DeleteBuilder builder = modDao.deleteBuilder();
            builder.where().lt("create_d",getCurrentDateM()).and().eq("isUsed",1);
            int index =builder.delete();
            if (index==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  flag;
    }

    public DocumentMod findById(int did) {
        DocumentMod mod = null;
        try {
            mod = modDao.queryForId(did);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mod;
    }

    public List<DocumentMod> querAll() {
        List<DocumentMod> mods = null;
        try {
            mods=  modDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mods;
    }

    public DocumentMod queryFor(String model) {
        DocumentMod mod = null;
        try {
            QueryBuilder builder = modDao.queryBuilder();
            builder.orderBy("create_d", false).limit((long)1).where().like("model", "%"+model+"%");
            mod = (DocumentMod) builder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mod;
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public String getCurrentDateM() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(now);
    }
}
