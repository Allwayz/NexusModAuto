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
    static final String COOKIE = "fwroute=1713531197.616.55.239162|b295758090068ae543818c1ba2aeea3e; _pk_ref.1.3564=%5B%22%22%2C%22%22%2C1713531198%2C%22https%3A%2F%2Fwww.google.com.hk%2F%22%5D; _pk_id.1.3564=b4a0838a22aff14e.1713531198.; _pk_ses.1.3564=1; _gid=GA1.2.839910991.1713531198; cf_clearance=CnEwRSVyA4CgRr0n.p9BQ.r9cuiarjYDCXhBoCjIR7E-1713531199-1.0.1.1-xd7.inQuymblA0BXKVMfc936TaDC5piwkCY8aCgHPmjH8_UMLY5QxT6wDL3yC9LqKUtro_96JzElEz2t6IZFlg; _hjSession_1264276=eyJpZCI6IjM1ZTFkZjFkLTcxYzQtNDllYy1iNzEwLTNjZDVmM2IzZjVjYyIsImMiOjE3MTM1MzExOTk2MDgsInMiOjAsInIiOjAsInNiIjowLCJzciI6MCwic2UiOjAsImZzIjoxLCJzcCI6MX0=; __qca=P0-1986178106-1713531198168; _hjSessionUser_1264276=eyJpZCI6IjIzYjcxOWVmLTU5NjctNTM2Ni05NjA5LWQ1YzM2YTJmNDg3YSIsImNyZWF0ZWQiOjE3MTM1MzExOTk2MDYsImV4aXN0aW5nIjp0cnVlfQ==; nexusmods_session=9764d63a9684f3139b8288e7a0c4185a; nexusmods_session_refresh=1713531219; jwt_fingerprint=ca34f172c75d9be6d94e584d577daee7; member_id=198967310; pass_hash=60aca4882aeb99ca51041c56886cfb76; sid_develop=%7B%22mechanism%22%3A%22defuse_compact%22%2C%22ciphertext%22%3A%223vUCANzb5Nbiol2DUSLkcQ19WxFMB-uAqeFMM8IRKtK4jN5V29ybzBGPB3S3OPme5GVX9KhZGYtTqpbfgBxeyFGhOa_taLtHu-8Spx_pWbMfzfG9PmFIkol0QcOyxe4H9eH83lGWqGh4akB3YFX8IaFo-fl0ZbO3GejI-JuZPSSc0C_z00V6NgGz_XE8v9IUyJzNzX5Qa6oL7oTSyySeV6LDSuyBbvp23b4h3GMb8WyOUexiXxIcOl18Ewq-1L95jdeE4Evwh9MRUra8BBVIe00V2N902KAQmc9EkO-yHuf9-wbN91VjCY7HEpaVoBTJu7ezM75HWp4GnkF4I9NUcp3uFqmj9pPIlxEYih12sLwnSdpJuqgClJjxoRGNrc42neXBD4gCFMrWk9eg0Qm2PO4S_R3WVt8FAorAOXg5gHzKHVAkQRTZ_R_A0UAtexUdbXio_gAIdgFkhiM7o0Gcmdome2eJKGI9kHD5fAje5HvVSKbIyPO7P-QzuZqJqTREg04Gc1xcLT5rOXIzeNqJ39GI-CDgulDq1IXGRm3G0Duu-oDbL_vuZ2u6_xeN6RwHf7iples7ZiDqEbl2HSibJCDJjndOxFqxMsWERxdkmrBRuL3FV8C_VgCbqGICd_vQZyV07Qgguvt1kZQ0Lo8YUvJsE9JL61g-a4pfsT7EDMrEFixLGXGlHBHSFznArlbXrMx_wVYUOMVDhuDRYX-Skx7MGdFjGAMVqKnlbniqa9ppb3LC3dAd5o7OSwNPf-GFwPzRM2y_h6WUYn7EH6b6uMtUtCNszq6Fux5y854stXyip2szyI87lGmOg7GD-GYZ6EXLaykBqKt-3C8hgbp33gmuqNhxgtry8FptsCuj_867h4JhLP6JAK5-xdjeN4aWy4hb8PuWPeSXV67z5joZNQ%22%7D; ab=2|1713531521; _ga=GA1.1.1127371938.1713531198; _ga_N0TELNQ37M=GS1.1.1713531198.1.1.1713531292.0.0.0";

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
