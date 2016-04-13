package cn.ibona.t1.common.model.db.ormlite.obj_save;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by busylee on 2015/12/1.
 * Object 保存的表定义
 */
@DatabaseTable(tableName = "document_mod")
public class DocumentMod implements Serializable {

    @DatabaseField(generatedId = true)
    private int did;
    @DatabaseField
    private String model;//模块(以","分隔)
    @DatabaseField
    private String remark;//备注
    @DatabaseField
    private String syn_data;//序列化的类
    @DatabaseField
    private String create_d;//添加时间
    @DatabaseField
    private int isUsed;//是否已经获取（0否1是）

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSyn_data() {
        return syn_data;
    }

    public void setSyn_data(String syn_data) {
        this.syn_data = syn_data;
    }

    public String getCreate_d() {
        return create_d;
    }

    public void setCreate_d(String create_d) {
        this.create_d = create_d;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
