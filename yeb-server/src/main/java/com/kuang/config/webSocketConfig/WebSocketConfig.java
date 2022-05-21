package com.kuang.config.webSocketConfig;


import com.kuang.config.jwtConfig.JwtTokenUtils;
import com.kuang.pojo.Admin;
import com.kuang.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${jwt.tokenHead}")
    private  String tokenHead;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    IAdminService adminService;
    /**
     * 前端根据这个连接
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //跨域
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();

    }

    /**
     * 没有jwt的话是不需要配置
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    //判断是否为登录
                    String firstNativeHeader = accessor.getFirstNativeHeader("Auth-Token");
                    if (!StringUtils.isEmpty(firstNativeHeader)) {
                        String authToken = firstNativeHeader.substring(tokenHead.length() + 1);
                        String username = jwtTokenUtils.getUsernameFromToken(authToken);
                        if (StringUtils.isEmpty(username)) {
                            Admin admin = adminService.getAdminByName(username);
                            if(jwtTokenUtils.validateToken(authToken,admin)) {
                                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
                                accessor.setUser(usernamePasswordAuthenticationToken);
                                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     * 推送给前端
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/queue");

    }
}
