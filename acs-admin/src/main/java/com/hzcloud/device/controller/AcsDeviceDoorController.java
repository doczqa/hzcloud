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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.core.domain.entity.SysRole;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.bo.AcsDeviceCommandInfo;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.config.DeviceConStatus;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IAcsDeviceDoorService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;
import com.hzcloud.common.core.domain.model.LoginUser;
import com.hzcloud.framework.web.service.TokenService;
import com.hzcloud.common.utils.ServletUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 门禁门Controller
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
@RestController
@RequestMapping("/device/door")
public class AcsDeviceDoorController extends BaseController
{
    @Autowired
    private IAcsDeviceDoorService acsDeviceDoorService;

    @Autowired
    private IAcsDeviceConService acsDeviceConService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询门禁门列表
     */
    @PreAuthorize("@ss.hasPermi('device:door:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsDeviceDoor acsDeviceDoor)
    {
        startPage();
        List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorList(acsDeviceDoor);
        return getDataTable(list);
    }

    /**
     * 查询门禁门列表不分页
     * @param acsDeviceDoor
     * @return
     */
    @GetMapping("/listNoPage")
    public AjaxResult listNoPage(AcsDeviceDoor acsDeviceDoor)
    {
        List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorList(acsDeviceDoor);
        return AjaxResult.success(list);
    }

    /**
     * 查询门禁门列表
     */
    @PreAuthorize("@ss.hasPermi('device:door:list')")
    @GetMapping("/listdf")
    public TableDataInfo listdf(AcsDeviceDoor acsDeviceDoor)
    {   
        boolean admin = false;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        for(SysRole role : loginUser.getUser().getRoles()){
            if(role.getRoleId() == 1){
                admin = true;
            }
        }
        startPage();
        if(admin){
            List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorList(acsDeviceDoor);
            return getDataTable(list);
        } else{
            List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorListDataFilter(acsDeviceDoor);
            return getDataTable(list);
        }
    }

    /**
     * 查询门禁门列表
     */
    @GetMapping("/listdfNoPage")
    public AjaxResult listDataFilterNoPage()
    {
        List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorListDataFilter(new AcsDeviceDoor());
        return AjaxResult.success(list);
    }

    @PostMapping("/listbyconids")
    public AjaxResult listByControllerIds(@RequestBody Long[] controllerIds){
        List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorByControllerIds(controllerIds);
        return AjaxResult.success(list);
    }

    /**
     * 导出门禁门列表
     */
    @PreAuthorize("@ss.hasPermi('device:door:export')")
    @Log(title = "门禁门", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsDeviceDoor acsDeviceDoor)
    {
        List<AcsDeviceDoor> list = acsDeviceDoorService.selectAcsDeviceDoorList(acsDeviceDoor);
        ExcelUtil<AcsDeviceDoor> util = new ExcelUtil<AcsDeviceDoor>(AcsDeviceDoor.class);
        return util.exportExcel(list, "门禁门数据");
    }

    /**
     * 获取门禁门详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:door:query')")
    @GetMapping(value = "/{doorId}")
    public AjaxResult getInfo(@PathVariable("doorId") Long doorId)
    {
        return AjaxResult.success(acsDeviceDoorService.selectAcsDeviceDoorById(doorId));
    }

    /**
     * 新增门禁门
     */
    @PreAuthorize("@ss.hasPermi('device:door:add')")
    @Log(title = "门禁门", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsDeviceDoor acsDeviceDoor)
    {
        if (UserConstants.NOT_UNIQUE.equals(acsDeviceDoorService.checkDoorNameUnique(acsDeviceDoor.getDoorName())))
        {
            return AjaxResult.error("新增门'" + acsDeviceDoor.getDoorName()+ "'失败，门名称已存在");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceDoor.setCreateBy(operName);
        return toAjax(acsDeviceDoorService.insertAcsDeviceDoor(acsDeviceDoor));
    }

    /**
     * 修改门禁门
     */
    @PreAuthorize("@ss.hasPermi('device:door:edit')")
    @Log(title = "门禁门", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsDeviceDoor acsDeviceDoor)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceDoor.setUpdateBy(operName);
        return toAjax(acsDeviceDoorService.updateAcsDeviceDoor(acsDeviceDoor));
    }

    /**
     * 删除门禁门
     */
    @PreAuthorize("@ss.hasPermi('device:door:remove')")
    @Log(title = "门禁门", businessType = BusinessType.DELETE)
	@DeleteMapping("/{doorIds}")
    public AjaxResult remove(@PathVariable Long[] doorIds)
    {
        return toAjax(acsDeviceDoorService.deleteAcsDeviceDoorByIds(doorIds));
    }

    /**
     * 获取门禁组中的门
     * @param groupId
     * @return
     */
    @GetMapping("/list/{groupId}")
    public TableDataInfo selectListInDoorGroup(@PathVariable("groupId") Long groupId) {
        startPage();
        List<AcsDeviceDoor> doorsByGroupId = acsDeviceDoorService.selectListInDoorGroup(groupId);
        return getDataTable(doorsByGroupId);
    }

    /**
     * 控制门
     */
    @PreAuthorize("@ss.hasPermi('device:door:oper')")
    @GetMapping("/cmd")
    public AjaxResult controlDoor(@RequestParam("controllerId") Long controllerId,@RequestParam("doorId") Long doorId,@RequestParam("doorPin") Integer doorPin, @RequestParam("cmd") Integer cmd){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        AcsDeviceCommandInfo command = new AcsDeviceCommandInfo();
        command.setControllerIndex(con.getControllerIndex());
        command.setDoor(doorPin);
        command.setCmd(cmd);
        AcsDeviceResult ret = controller.DoorControl(command);
        if (ret.isCode()){
            AcsDeviceDoor door =  acsDeviceDoorService.selectAcsDeviceDoorById(doorId);
            if(door == null){
                return AjaxResult.error("门区未识别");
            }
            switch(cmd){
            case 2:
                door.setStatus("1");
                acsDeviceDoorService.updateAcsDeviceDoor(door);
                break;
            case 3:
                door.setStatus("2");
                acsDeviceDoorService.updateAcsDeviceDoor(door);
                break;
            case 4:
                door.setStatus("0");
                acsDeviceDoorService.updateAcsDeviceDoor(door);
                break;
            }
        }

        return toAjax(ret.isCode());
    }

    /**
     * 查询不在门禁组中的门　
     * @param acsDeviceDoor
     * @return
     */
    @Deprecated
    @GetMapping("/listNotInDoorGroup")
    public AjaxResult selectListNotInDoorGroup(AcsDeviceDoor acsDeviceDoor) {
        return AjaxResult.success(acsDeviceDoorService.selectListNotInDoorGroup(acsDeviceDoor));
    }

    /**
     * 查询不在门禁组中的控制器与门的树
     * @param groupId 门禁组id
     * @return
     */
    @GetMapping("/controllerAndDoorTree")
    public AjaxResult selectControllerAndDoorTree(@RequestParam("type") String type,
                                                  @RequestParam(value = "groupId", required = false) Long groupId,
                                                  @RequestParam(value = "parentId", required = false) Long parentId) {
        return AjaxResult.success(acsDeviceDoorService.selectControllerAndDoorTree(groupId, type, parentId));
    }
}
