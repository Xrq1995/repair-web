package cn.edu.ahu.repairbackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: WangMiao
 * Date: 2018/3/12
 * Time: 10:59
 * Created with IntelliJ IDEA
 * Description: 自定义参数检测注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {

    /**
     * 是否非空，默认不能为空
     * @return true
     */
    boolean notNull() default true;

}
