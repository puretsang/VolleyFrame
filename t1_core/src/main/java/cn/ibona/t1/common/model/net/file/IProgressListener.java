package cn.ibona.t1.common.model.net.file;

/**
 * 文件相关请求进度监听器
 */
public interface IProgressListener {
    /**
     * 用户自定义进度处理类必须实现的方法
     *
     * @param key     文件的保存文件名
     * @param percent 进度，取值范围[0, 1.0]
     */
    void progress(String key, double percent);
}
