package com.hzcloud.group.controller;

import java.util.ArrayList;
import java.util.List;

import com.hzcloud.device.annotation.AcsDataChange;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.group.bo.AscDoorGroupAddDoorBo;
import com.hzcloud.group.bo.AscDoorGroupRemoveDoorBo;
import com.hzcloud.group.bo.AscDoorGroupAddUserGroupBo;
import com.hzcloud.group.vo.DoorGroupTreeVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.group.domain.AscDoorGroup;
import com.hzcloud.group.service.IAscDoorGroupService;
import com.hzcloud.common.utils.poi.ExcelUtil;

/**
 * 门禁组Controller
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@RestController
@RequestMapping("/group/door_group")
public class AscDoorGroupController extends BaseController
{
    @Autowired
    private IAscDoorGroupService ascDoorGroupService;

    /**
     * 查询门禁组列表
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:list')")
    @GetMapping("/list")
    public AjaxResult list(AscDoorGroup ascDoorGroup)
    {
        List<AscDoorGroup> list = ascDoorGroupService.selectAscDoorGroupList(ascDoorGroup);
        List<DoorGroupTreeVo> trees = new ArrayList<>();
        for (AscDoorGroup group : list) {
            DoorGroupTreeVo doorGroupTreeVo = new DoorGroupTreeVo(Integer.parseInt(String.valueOf(group.getGroupId())), group.getGroupName(), true);
            trees.add(doorGroupTreeVo);
        }
        return AjaxResult.success(trees);
    }

    /**
     * 导出门禁组列表
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:export')")
    @Log(title = "门禁组", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AscDoorGroup ascDoorGroup)
    {
        List<AscDoorGroup> list = ascDoorGroupService.selectAscDoorGroupList(ascDoorGroup);
        ExcelUtil<AscDoorGroup> util = new ExcelUtil<AscDoorGroup>(AscDoorGroup.class);
        return util.exportExcel(list, "门禁组数据");
    }

    /**
     * 获取门禁组详细信息
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(ascDoorGroupService.selectAscDoorGroupById(groupId));
    }

    /**
     * 新增门禁组
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:add')")
    @Log(title = "门禁组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AscDoorGroup ascDoorGroup)
    {
        return toAjax(ascDoorGroupService.insertAscDoorGroup(ascDoorGroup));
    }

    /**
     * 修改门禁组
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:edit')")
    @Log(title = "门禁组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AscDoorGroup ascDoorGroup)
    {
        return toAjax(ascDoorGroupService.updateAscDoorGroup(ascDoorGroup));
    }

    /**
     * 删除门禁组
     */
    @PreAuthorize("@ss.hasPermi('group:door_group:remove')")
    @Log(title = "门禁组", businessType = BusinessType.DELETE)
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(ascDoorGroupService.deleteAscDoorGroupByIds(groupIds));
    }

    /**
     * 门禁组添加门
     * @param doorGroupAddDoorBo
     * @return
     */
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
    @PostMapping("/add_door")
    public AjaxResult addDoor(@RequestBody AscDoorGroupAddDoorBo doorGroupAddDoorBo) {
        return AjaxResult.success(ascDoorGroupService.addDoorForGroup(doorGroupAddDoorBo));
    }

    /**
     * 获取门禁组中的门id数组
     * @param groupId
     * @return
     */
    @GetMapping("/get_doorIds/{groupId}")
    public AjaxResult getDoorIdsByGroupId(@PathVariable("groupId") Long groupId) {
        return AjaxResult.success(ascDoorGroupService.getDoorIdsByGroupId(groupId));
    }

    /**
     * 移除门
     * @param doorGroupRemoveDoorBo
     * @return
     */
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
    @DeleteMapping("/remove_door")
    public AjaxResult removeDoor(@Validated @RequestBody AscDoorGroupRemoveDoorBo doorGroupRemoveDoorBo) {
        return toAjax(ascDoorGroupService.removeDoor(doorGroupRemoveDoorBo));
    }

    /**
     * 门禁组添加用户组
     * @param ascDoorGroupAddUserGroupBo
     * @return
     */
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
    @PostMapping("/add_userGroup")
    public AjaxResult addUser(@Validated @RequestBody AscDoorGroupAddUserGroupBo ascDoorGroupAddUserGroupBo) {
        return toAjax(ascDoorGroupService.addUserGroupForGroup(ascDoorGroupAddUserGroupBo));
    }

    /**
     * 门禁组移除用户组
     * @param ascDoorGroupAddUserGroupBo
     * @return
     */
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
    @PostMapping("/remove_userGroup")
    public AjaxResult removeUser(@Validated @RequestBody AscDoorGroupAddUserGroupBo ascDoorGroupAddUserGroupBo) {
        return toAjax(ascDoorGroupService.removeUserGroupForGroup(ascDoorGroupAddUserGroupBo));
    }


    /**
     * 获取门禁组中的用户组ID数组
     * @param groupId
     * @return
     */
    @GetMapping("/get_userGroupIds/{groupId}")
    public AjaxResult getUserGroupIdsByGroupId(@PathVariable("groupId") Long groupId) {
        return AjaxResult.success(ascDoorGroupService.getUserGroupIdsByGroupId(groupId));
    }

}
