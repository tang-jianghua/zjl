package com.mfangsoft.zhuangjialong.system.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.mfangsoft.zhuangjialong.core.utils.PageInfo;
import com.mfangsoft.zhuangjialong.system.model.SysLog;
import com.mfangsoft.zhuangjialong.system.service.LogService;

/**
 * @description：日志管理
 * @author：zhixuan.wang
 * @date：2015/10/30 18:06
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private LogService logService;


    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/admin/syslog";
    }


    @RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo dataGrid(SysLog sysLog, Integer page, Integer rows) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = Maps.newHashMap();
        pageInfo.setCondition(condition);
        logService.findDataGrid(pageInfo);
        return pageInfo;
    }
}
