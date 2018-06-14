package cn.edu.ahu.repairbackend.exception;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 18:30
 * Created with IntelliJ IDEA
 * Description: 资源不存在异常类
 */
public class NotFoundException extends ApplicationException{

    public NotFoundException(String message) {
        super("未找到当前资源: " + message);
    }

}
