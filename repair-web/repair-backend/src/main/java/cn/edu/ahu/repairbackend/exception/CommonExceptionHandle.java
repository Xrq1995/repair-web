package cn.edu.ahu.repairbackend.exception;

import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 16:42
 * Created with IntelliJ IDEA
 * Description: 通用异常解析类
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandle {

    //日志记录
    private static Logger logger = LoggerFactory.getLogger(CommonExceptionHandle.class);
    //程序环境
    private final Environment environment;

    @Autowired
    public CommonExceptionHandle(Environment environment) {
        this.environment = environment;
    }

    /**
     * 400 - Bad Request    请求参数验证异常处理
     * @param request       请求
     * @param exception     异常
     * @return              400错误Json信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamCheckException.class)
    public ResultMsg RequestExceptionHandler(HttpServletRequest request, ParamCheckException exception) {
        logger.error("*** 请求参数验证异常 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage());
    }

    /**
     * 400 - Bad Request    请求参数验证异常处理（旧式异常处理，未启用）
     * @param request       请求
     * @param exception     异常
     * @return              400错误Json信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestException.class)
    public ResultMsg RequestExceptionHandler(HttpServletRequest request, RequestException exception) {
        logger.error("*** 请求参数验证异常 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage());
    }

    /**
     * 404 - Not Found      未找到当前资源异常处理
     * @param request       请求
     * @param exception     异常
     * @return              404错误Json信息
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResultMsg NotFoundExceptionHandler(HttpServletRequest request, NotFoundException exception) {
        logger.error("*** 未找到当前资源 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage());
    }

    /**
     * 500 - Internal Server Error  业务逻辑异常处理
     * @param request       请求
     * @param exception     异常
     * @return              500错误Json信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public ResultMsg ExceptionHandler(HttpServletRequest request, ApplicationException exception) {
        logger.error("*** 业务逻辑异常 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage());
    }

    /**
     * 500 - Internal Server Error  自定义异常处理
     * @param request       请求
     * @param exception     异常
     * @return              500错误Json信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)
    public ResultMsg ExceptionHandler(HttpServletRequest request, CustomException exception) {
        logger.error("*** 自定义异常 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage(), environment.getProperty(exception.getMessage()));
    }

    /**
     * 500 - Internal Server Error  未知异常处理
     * @param request       请求
     * @param exception     异常
     * @return              500错误Json信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultMsg ExceptionHandler(HttpServletRequest request, Exception exception) {
        logger.error("*** 未知异常 - " + exception.getMessage() + " ***");
        return new ResultMsg().failure(exception.getMessage());
    }

}
