package cn.edu.ahu.repairbackend.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Author: WangMiao
 * Date: 2018/1/14
 * Time: 21:30
 * Created with IntelliJ IDEA
 * Description: 自定义异常管理类
 */
@Component
public class ExceptionManager {

    //程序环境
    private final Environment environment;

    @Autowired
    public ExceptionManager(Environment environment) {
        this.environment = environment;
    }

    /**
     * 根据错误码抛出自定义异常
     * @param code 错误码
     * @return  自定义异常
     */
    public CustomException createException(String code) {
        return new CustomException(code, environment.getProperty(code));
    }

}
