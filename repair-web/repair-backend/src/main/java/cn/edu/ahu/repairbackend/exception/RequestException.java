package cn.edu.ahu.repairbackend.exception;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 16:31
 * Created with IntelliJ IDEA
 * Description: 请求异常处理类（主要在RequestParamCheck中使用，旧式异常处理，未启用）
 */
public class RequestException extends ApplicationException {

    public RequestException(String message) {
        super(message);
    }

}
