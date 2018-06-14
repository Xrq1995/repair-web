package cn.edu.ahu.repairbackend.exception;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 16:26
 * Created with IntelliJ IDEA
 * Description: 自定义异常类
 */
public class CustomException extends RuntimeException {

    private String code;
    private String message;

    /**
     * 抛出自定义异常码和异常信息
     * @param code      异常码
     * @param message   异常信息
     */
    public CustomException(String code, String message) {
        super(code);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
