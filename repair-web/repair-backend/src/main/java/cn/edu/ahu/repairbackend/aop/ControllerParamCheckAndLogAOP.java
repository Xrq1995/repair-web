package cn.edu.ahu.repairbackend.aop;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.exception.ParamCheckException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


/**
 * Author: WangMiao
 * Date: 2018/3/12
 * Time: 19:18
 * Created with IntelliJ IDEA
 * Description: controller层切面参数检测和日志管理类
 */
@Component
@Aspect
public class ControllerParamCheckAndLogAOP {

    //日志记录
    private static Logger logger = LoggerFactory.getLogger(ControllerParamCheckAndLogAOP.class);
    //进入切点时间
    private long startTime;

    /**
     * 定义切点，包含controller层下所有类
     */
    @Pointcut("execution(public * cn.edu.ahu.repairbackend.controller..*.*(..))")
    public void controllerParamCheckAndLog() {
    }

    /**
     * 切入前执行
     * @param joinPoint 切点
     * @throws Throwable 可抛出类
     */
    @Before("controllerParamCheckAndLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    /**
     * 返回值后执行日志记录
     * @param result 返回值
     * @throws Throwable 可抛出类
     */
    @AfterReturning(returning = "result", pointcut = "controllerParamCheckAndLog()")
    public void doAfterReturning(Object result) throws Throwable {
        logger.info("返回值：" + result);
        logger.info("耗时：" + (System.currentTimeMillis() - startTime));
        logger.info("请求完成");
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint 程序连接点
     * @return 启动目标方法执行
     * @throws Throwable 可抛出类
     */
    @Around("controllerParamCheckAndLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获得系统当前时间
        startTime = System.currentTimeMillis();

        /*获得请求信息*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        /*记录请求信息*/
        logger.info("请求开始-------------------------------------------------------");
        logger.info("请求地址：" + request.getRequestURL().toString());
        logger.info("请求类型：" + request.getMethod());
        logger.info("访问类及方法：" + proceedingJoinPoint.getSignature().getDeclaringTypeName() + proceedingJoinPoint.getSignature().getName());
        logger.info("请求参数：" + Arrays.toString(proceedingJoinPoint.getArgs()));

        MethodSignature signature = ((MethodSignature) proceedingJoinPoint.getSignature());
        //得到拦截方法
        Method method = signature.getMethod();
        //获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return proceedingJoinPoint.proceed();
        }

        //获得方法参数名
        String[] paramNames = signature.getParameterNames();
        //获得方法参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();
        //获得方法参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                //如果该参数前面的注解是@ParamCheck的实例，并且notNull()=true,则进行非空校验
                if (parameterAnnotations[i][j] != null && parameterAnnotations[i][j] instanceof ParamCheck &&
                        ((ParamCheck) parameterAnnotations[i][j]).notNull()) {
                    paramCheck(paramNames[i], paramValues[i], parameterTypes[i] == null ? null : parameterTypes[i].getName());
                    break;
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    /**
     * 参数检测(非List)
     * @param paramName 参数名称
     * @param value 参数值
     * @param parameterType 参数类型
     * @param <T> 输入参数类型
     */
    private <T> void paramCheck(String paramName, T value, String parameterType) {
        if (value == null || "".equals(value.toString().trim())) {
            throw new ParamCheckException(paramName, parameterType, "为null");
        }
    }

    /**
     * 参数检测(List)
     * @param paramName 参数名称
     * @param value 参数值
     * @param parameterType 参数类型
     * @param <T> 输入参数类型
     */
    private <T> void paramCheck(String paramName, List<T> value, String parameterType) {
        if (value == null)
            throw new ParamCheckException(paramName, parameterType, "为null");
        if (value.isEmpty())
            throw new ParamCheckException(paramName, parameterType, "为empty");
    }

}
