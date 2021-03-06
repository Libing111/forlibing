/* <p>文件名称: AssociationController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-31</p>
 * <p>完成日期：2013-5-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-31 下午2:48:06
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.service.SecurityService;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    private String systemCode = "somp";

    private String subsystemMoc = "subsystem";

    private String subsystemCode = "somp.security";

    private String moduleMoc = "module";

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // response.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");

        List<Resource> subsystemList = securityService.getResourcesOfCurrentUserDesc(request, systemCode, subsystemMoc);
        List<Resource> moduleList = securityService.getResourcesOfCurrentUser(request, subsystemCode, moduleMoc);

        modelMap.put("subsystemList", subsystemList);
        modelMap.put("moduleList", moduleList);

        return "index";
    }
}
