package cn.waikey.volleyframe;

import java.util.HashMap;
import java.util.Map;

import cn.ibona.t1.common.model.data_center.request.ParsedStringRequest;
import cn.ibona.t1.common.model.net.BaseNetBean;

/**
 * Created by Tsang on 16/4/8.
 */
public class LoginRequest extends ParsedStringRequest<BaseNetBean<UserLogin>> {

    private static final String URL = "http://182.254.228.211:9000/index.php/api/Index/login";

    private String phoneNumber;
    private String password;


    public LoginRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("cellphone", phoneNumber);
        params.put("password", password);
        return params;
    }

}
