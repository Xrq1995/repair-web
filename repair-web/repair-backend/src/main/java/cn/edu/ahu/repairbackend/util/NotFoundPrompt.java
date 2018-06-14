package cn.edu.ahu.repairbackend.util;

import cn.edu.ahu.repairbackend.exception.NotFoundException;

/**
 * Author: WangMiao
 * Date: 2018/1/14
 * Time: 22:42
 * Created with IntelliJ IDEA
 * Description: 未找到当前资源信息类（未启用）
 */
public class NotFoundPrompt {

    public static <T> void notFoundPrompt(String pageName) {
        throw new NotFoundException(pageName);
    }

}
