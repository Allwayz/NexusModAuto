package cn.allwayz.nexusmod.controller;

import cn.allwayz.nexusmod.service.impl.WebServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author allwayz
 * @create 2024-04-16 22:40
 */
@RestController
@RequestMapping("nexus")
public class WebController {
    @Resource
    WebServiceImpl webService;

    @GetMapping("generate_download_link")
    public String generateDownloadLink() {

        return webService.getDownloadLinkMap().toString();
    }

    @PostMapping("download")
    public String downloadModByIds(@RequestBody List<String> ids){
        webService.downloadModByIds(ids);
        return ids.toString();
    }
}
