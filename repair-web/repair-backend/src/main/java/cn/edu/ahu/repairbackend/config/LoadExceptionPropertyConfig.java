package cn.edu.ahu.repairbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Author: WangMiao
 * Date: 2018/1/14
 * Time: 21:39
 * Created with IntelliJ IDEA
 * Description: 加载properties文件键值对配置类
 */
@Component
@PropertySource(value = "classpath:config/exception.properties", encoding = "utf-8")
public class LoadExceptionPropertyConfig {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
