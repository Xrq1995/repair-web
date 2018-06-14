package cn.edu.ahu.repairbackend.util;

import cn.edu.ahu.repairbackend.exception.RequestException;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 16:30
 * Created with IntelliJ IDEA
 * Description: 请求参数检测类（旧式异常处理，未启用）
 */
public class RequestParamCheck {

    /**
     * List 检测
     * @param list  List
     * @param <T>   List类型
     */
    public static <T> void checkList(List<T> list, String paramName){
        if (list==null)
            throw new RequestException(paramName + " 为 null");
        if (list.isEmpty())
            throw new RequestException(paramName + " 为 empty");
    }

    /**
     * null 检测
     * @param parameter 参数
     * @param <T>       参数类型
     */
    public static <T> void checkNull(T parameter,String paramName){
        if (parameter==null)
            throw new RequestException(paramName + " 为 null");
    }

}
