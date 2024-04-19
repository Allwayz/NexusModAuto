package cn.allwayz.nexusmod.service.impl;

import cn.allwayz.nexusmod.service.WebService;
import cn.allwayz.nexusmod.utilities.Request;
import cn.allwayz.nexusmod.utilities.SystemOperations;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author allwayz
 * @create 2024-04-17 00:08
 */
@Service
public class WebServiceImpl implements WebService {
    static final String COOKIE = "";

    @Resource
    Request request;
    @Resource
    SystemOperations systemOperations;

    @Override
    public Map<String, String> getDownloadLinkMap() {
        String[] modIds = {"4", "93", "2400", "541", "5098"};
        return download(modIds);
    }

    @Override
    public Map<String, String> getDownloadLinkMap(List<String> ids) {
        String[] stringArray = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            stringArray[i] = ids.get(i);
        }
        return download(stringArray);
    }

    private Map<String, String> download(String[] modIds) {
        String url = "https://www.nexusmods.com/Core/Libs/Common/Managers/Downloads?GenerateDownloadUrl";
        System.out.println("一共 " + modIds.length + " 个mod");
        Map<String, String> modMap = new HashMap<>();
        int count = 0;
        for (String modId : modIds) {
            count++;
            System.out.println("正在解析第 " + count + " 个...");
            String[] properties = getFileIdByModId(modId);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("cookie", COOKIE);
            String refererUrl = "https://www.nexusmods.com/stardewvalley/mods/" + modId + "?tab=files&file_id=" + properties[1];
            httpHeaders.set("referer", refererUrl);
            httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
            Map<String, String> params = new HashMap<>();
            params.put("fid", properties[1]);
            params.put("game_id", "1303");
            modMap.put(properties[0], request.POST(url, httpHeaders, params).split("\"")[3]);
        }
        downloadMod(modMap);
        return modMap;
    }

    private void downloadMod(Map<String, String> modMap) {
        System.out.println("开始下载mod");
        String filePath = "./mod/";
        for (String key : modMap.keySet()) {
            System.out.println("正在下载 " + key);
            System.out.println(modMap.get(key).replaceAll("\\\\", ""));
            String fileName = key.replaceAll(" ", "_") + ".zip";
            systemOperations.wget(modMap.get(key).replaceAll("\\\\", ""), "-O", filePath + fileName);
        }
        System.out.println("正在下载 " + "UIInfoSuite2");
        systemOperations.wget("https://github.com/Annosz/UIInfoSuite2/releases/download/v2.3.1/UIInfoSuite2.v2.3.1.zip","-O", filePath + "UIInfoSuite2.v2.3.1.zip");
        systemOperations.sh("unzip.sh");
    }

    private String[] getFileIdByModId(String modId) {
        String url = "https://www.nexusmods.com/stardewvalley/mods/" + modId + "?tab=files";
        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", COOKIE);
        String html = request.GET(url, headers);
        String fileId = "null";
        HashMap<String, String> map = new HashMap<>();
        int count = 0;
        for (String line : html.split("\n")) {
            if (line.contains("file-expander-header")) {
                line = line.substring(4, line.length() - 1);
                char[] chars = line.toCharArray();
                int index = 0, keyStart = 0, keyEnd, valueStart = 0, valueEnd;
                boolean equal = false;
                boolean flagOpen = false;
                String key = "";
                String value = "";
                for (char c : chars) {
                    if (!equal & c == '=') {
                        keyEnd = index;
                        key = line.substring(keyStart, keyEnd);
                        equal = true;
                    } else if (equal & c == '"' & !flagOpen) {
                        valueStart = index + 1;
                        flagOpen = true;
                    } else if (equal & c == '"' & flagOpen) {
                        valueEnd = index;
                        flagOpen = false;
                        equal = false;
                        value = line.substring(valueStart, valueEnd);
                        map.put(key, value);
                        keyStart = index + 2;
                    }
                    index++;
                }
                break;
            }
            count++;
        }
        return new String[]{map.get("data-name"), map.get("data-id")};
    }
}
