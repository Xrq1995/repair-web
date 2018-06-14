package cn.edu.ahu.repairbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Author: WangMiao
 * Date: 2018/1/2
 * Time: 11:13
 * Created with IntelliJ IDEA
 * Description: 消息推送配置类
 */
@Configuration
@EnableWebSocketMessageBroker //开启使用STOMP协议来传输基于代理的消息
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的节点，并指定映射的URL
     * @param stompEndpointRegistry 注册的STOMP协议节点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //添加服务端点
        stompEndpointRegistry.addEndpoint("/repairMessageEndPoint");
        //当浏览器支持sockjs时执行该配置
        stompEndpointRegistry.addEndpoint("/repairMessageEndPoint").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 消息发送配置方法
     * @param config 信息代理配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/repairMessage");
        config.setApplicationDestinationPrefixes("/socket");
    }

}
