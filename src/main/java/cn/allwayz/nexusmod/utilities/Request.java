package cn.allwayz.nexusmod.utilities;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author allwayz
 * @create 2024-04-16 22:45
 */
@Component
public class Request {
    @Resource
    RestTemplate restTemplate;

    private HttpHeaders getDefaultHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Referer", "2400");
//        https://www.nexusmods.com/stardewvalley/mods/2400?tab=files&file_id=90240
//        https://www.nexusmods.com/stardewvalley/mods/4   ?tab=files&file_id=92653
        return httpHeaders;
    }

    private ResponseEntity<String> sendRequest(String url, HttpMethod httpMethod) {
        return restTemplate.exchange(url, httpMethod, new HttpEntity<>(getDefaultHttpHeaders()), String.class);
    }

    public String GET(String url) {
        return sendRequest(url, HttpMethod.GET).getBody();
    }

    public String GET(String url, HttpHeaders httpHeaders) {
        return String.valueOf(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class));
    }

    public String POST(String url) {
        return sendRequest(url, HttpMethod.POST).getBody();
    }

    public String POST(String url, HttpHeaders httpHeaders) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), String.class).getBody();
    }

    public String POST(String url, HttpHeaders httpHeaders, Map<String, String> params) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (String key : params.keySet()) {
            map.put(key, Collections.singletonList(params.get(key)));
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        return restTemplate.postForEntity(url, request, String.class).getBody();
    }

}
