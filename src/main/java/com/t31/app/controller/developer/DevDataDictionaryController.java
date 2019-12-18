package com.t31.app.controller.developer;

import com.t31.app.entity.DataDictionaryDTO;
import com.t31.app.service.backend.BackDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description: DevDataDictionaryController
 * date: 2019/12/17 20:13
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/data")
public class DevDataDictionaryController {
    @Autowired
    private BackDataDictionaryService dictionaryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<DataDictionaryDTO> list(String tcode){//根据code查询对应的平台，状态。。。
        return dictionaryService.selByTypeCode(tcode);
    }
}
