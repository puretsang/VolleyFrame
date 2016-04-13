package cn.ibona.t1_core.test;

import android.test.AndroidTestCase;

import cn.ibona.t1.common.model.db.ormlite.obj_save.tools.CacheObjectUtil;


/**
 * Created by busylee on 2015/12/5.
 */
public class Demo extends AndroidTestCase {

    public void testSave(){
        CacheObjectUtil cache = new CacheObjectUtil(getContext());
        ListClaa claa = new ListClaa();
        claa.setName("法拉利");
        claa.setPrice("123.45w万美元");
        claa.setLevel("一级");
        assertTrue(cache.save(claa,"工作报告","月报","2"));
    }

    public  void testGet(){
        CacheObjectUtil cache = new CacheObjectUtil(getContext());
        ListClaa claa = cache.get(ListClaa.class,"工作报告","月报","2");
        assertNotNull(claa);
    }


}
