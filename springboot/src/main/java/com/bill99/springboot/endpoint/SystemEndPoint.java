package com.bill99.springboot.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="endpoints.customsystem")
public class SystemEndPoint extends AbstractEndpoint<Map<String, Object>> {

    public SystemEndPoint(){
        super("customsystem");
    }
    @Override
    public Map<String, Object> invoke() {
        Map<String,Object> result= new HashMap<>();
        Map<String, String> map = System.getenv();
        result.put("username",map.get("USERNAME"));
        result.put("computername",map.get("COMPUTERNAME"));
        result.put("userdomain",map.get("USERDOMAIN"));
        return result;
    }
}
