package cn.edu.ahu.repairbackend.exception;

/**
 * Author: WangMiao
 * Date: 2018/1/14
 * Time: 22:31
 * Created with IntelliJ IDEA
 * Description: 服务层异常类
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

}
