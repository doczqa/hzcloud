package com.hzcloud.device.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.device.domain.AcsDeviceZone;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IAcsDeviceZoneService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.common.core.domain.model.LoginUser;
import com.hzcloud.framework.web.service.TokenService;
import com.hzcloud.common.utils.ServletUtils;

/**
 * 区域Controller
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
@RestController
@RequestMapping("/device/zone")
public class AcsDeviceZoneController extends BaseController
{
    @Autowired
    private IAcsDeviceZoneService acsDeviceZoneService;

    @Autowired
    private IAcsDeviceConService acsDeviceConService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询区域列表
     */
    @PreAuthorize("@ss.hasPermi('device:zone:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsDeviceZone acsDeviceZone)
    {
        startPage();
        List<AcsDeviceZone> list = acsDeviceZoneService.selectAcsDeviceZoneList(acsDeviceZone);
        return getDataTable(list);
    }

    /**
     * 查询区域列表不分页
     * @param acsDeviceZone
     * @return
     */
    @GetMapping("/listNoPage")
    public AjaxResult listNoPage(AcsDeviceZone acsDeviceZone)
    {
        List<AcsDeviceZone> list = acsDeviceZoneService.selectAcsDeviceZoneList(acsDeviceZone);
        return AjaxResult.success(list);
    }

    /**
     * 导出区域列表
     */
    @PreAuthorize("@ss.hasPermi('device:zone:export')")
    @Log(title = "区域", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsDeviceZone acsDeviceZone)
    {
        List<AcsDeviceZone> list = acsDeviceZoneService.selectAcsDeviceZoneList(acsDeviceZone);
        ExcelUtil<AcsDeviceZone> util = new ExcelUtil<AcsDeviceZone>(AcsDeviceZone.class);
        return util.exportExcel(list, "区域数据");
    }

    /**
     * 获取区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:zone:query')")
    @GetMapping(value = "/{zoneId}")
    public AjaxResult getInfo(@PathVariable("zoneId") Long zoneId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(zoneId))
        {
            AcsDeviceZone zone = acsDeviceZoneService.selectAcsDeviceZoneById(zoneId);
            if(zone != null){
                List<Long> conList = acsDeviceConService.selectConListByZoneId(zoneId);
                if(conList.size() > 0){
                    Long[] controllerIds = new Long[conList.size()];
                    controllerIds = conList.toArray(controllerIds);
                    zone.setControllerIds(controllerIds);
                }
            }
            ajax.put(AjaxResult.DATA_TAG, zone);
        }
        return ajax;
    }

    /**
     * 新增区域
     */
    @PreAuthorize("@ss.hasPermi('device:zone:add')")
    @Log(title = "区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsDeviceZone acsDeviceZone)
    {
        if (UserConstants.NOT_UNIQUE.equals(acsDeviceZoneService.checkZoneNameUnique(acsDeviceZone.getZoneName())))
        {
            return AjaxResult.error("新增区域'" + acsDeviceZone.getZoneName() + "'失败，区域名称已存在");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceZone.setCreateBy(operName);
        return toAjax(acsDeviceZoneService.insertAcsDeviceZone(acsDeviceZone));
    }

    /**
     * 修改区域
     */
    @PreAuthorize("@ss.hasPermi('device:zone:edit')")
    @Log(title = "区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsDeviceZone acsDeviceZone)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceZone.setUpdateBy(operName);
        return toAjax(acsDeviceZoneService.updateAcsDeviceZone(acsDeviceZone));
    }

    /**
     * 删除区域
     */
    @PreAuthorize("@ss.hasPermi('device:zone:remove')")
    @Log(title = "区域", businessType = BusinessType.DELETE)
	@DeleteMapping("/{zoneIds}")
    public AjaxResult remove(@PathVariable Long[] zoneIds)
    {
        return toAjax(acsDeviceZoneService.deleteAcsDeviceZoneByIds(zoneIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('device:zone:edit')")
    @Log(title = "区域", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AcsDeviceZone zone)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        zone.setUpdateBy(operName);
        return toAjax(acsDeviceZoneService.updateZoneStatus(zone));
    }
}
