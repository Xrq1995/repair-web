package cn.edu.ahu.repairbackend.exception;

/**
 * Author: WangMiao
 * Date: 2018/3/12
 * Time: 11:07
 * Created with IntelliJ IDEA
 * Description: 自定义参数异常类
 */
public class ParamCheckException extends ApplicationException {

    private final String parameterName;     //参数名
    private final String parameterType;     //参数类型

    public ParamCheckException(String parameterName, String parameterType, String message) {
        //写入异常信息
        super("类型为" + parameterType + "的参数" + parameterName + message);
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final String getParameterType() {
        return this.parameterType;
    }

}
