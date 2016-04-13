package cn.ibona.t1_core.test;

import java.io.Serializable;

/**
 * Created by busylee on 2015/12/1.
 */
public class ListClaa implements Serializable {

    private String name;
    private String level;
    private String price;
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
