package cn.allwayz.nexusmod.service;

import java.util.List;
import java.util.Map;

/**
 * @author allwayz
 * @create 2024-04-16 22:44
 */
public interface WebService {
    Map<String, String> getDownloadLinkMap();

    Map<String, String> getDownloadLinkMap(List<String> ids);
}
