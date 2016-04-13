package cn.ibona.t1.common.model.net.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ibona.t1.common.control.utils.LogUtil;

public class MultipartRequest<T> extends Request<T> {

    boolean DEBUG = true;

    private MultipartEntity entity = new MultipartEntity();

    private final Response.Listener<T> mListener;

    private List<File> mFileParts;
    private String mFilePartName;
    private Map<String, String> mParams;

    private final Gson gson = new Gson();
    private final Class<T> clazz;

    /**
     * 单个文件
     * @param url
     * @param errorListener
     * @param listener
     * @param filePartName
     * @param file
     * @param params
     */
    public MultipartRequest(String url, Class<T> clazz, Response.ErrorListener errorListener,
                            Response.Listener<T> listener, String filePartName, File file,
                            Map<String, String> params) {
        super(Method.POST, url, errorListener);

        mFileParts = new ArrayList<File>();
        if (file != null) {
            mFileParts.add(file);
        }
        mFilePartName = filePartName;
        mListener = listener;
        mParams = params;
        this.clazz = clazz;
        buildMultipartEntity();
    }
    /**
     * 多个文件，对应一个key
     * @param url
     * @param errorListener
     * @param listener
     * @param filePartName
     * @param files
     * @param params
     */
    public MultipartRequest(String url, Class<T> clazz,  Response.ErrorListener errorListener,
                            Response.Listener<T> listener, String filePartName,
                            List<File> files, Map<String, String> params) {
        super(Method.POST, url, errorListener);
        mFilePartName = filePartName;
        mListener = listener;
        mFileParts = files;
        mParams = params;
        this.clazz = clazz;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        if (mFileParts != null && mFileParts.size() > 0) {
            for (File file : mFileParts) {
                LogUtil.i("MultipartEntity 上传的文件 " + file.getAbsolutePath() + " 文件大小" + file.length()/1024 +"k");
                entity.addPart(mFilePartName, new FileBody(file));
            }
            long l = entity.getContentLength();
//            CLog.log(mFileParts.size()+"个，长度："+l);
        }

        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    entity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        byte[] array = bos.toByteArray();
        LogUtil.i(getUrl() + "\nMultipartEntity body总size " + array.length /1024 + "k");
        return array;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, "utf-8");
            LogUtil.json(json);

            json = json.substring(json.indexOf("{"));

            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        return headers;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
