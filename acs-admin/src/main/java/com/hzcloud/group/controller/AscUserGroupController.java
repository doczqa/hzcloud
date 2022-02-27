package com.hzcloud.group.controller;

import java.util.List;

import com.hzcloud.device.annotation.AcsDataChange;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.group.bo.AcsUserGroupEditGroupNameBo;
import com.hzcloud.group.bo.AcsUserGroupEditStatusBo;
import com.hzcloud.group.bo.AcsUserGroupUpdateUserBo;
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
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.group.domain.AscUserGroup;
import com.hzcloud.group.service.IAscUserGroupService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 用户组Controller
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@RestController
@RequestMapping("/group/user_group")
public class AscUserGroupController extends BaseController
{
    @Autowired
    private IAscUserGroupService ascUserGroupService;

    /**
     * 查询用户组列表
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:list')")
    @GetMapping("/list")
    public TableDataInfo list(AscUserGroup ascUserGroup)
    {
        startPage();
        List<AscUserGroup> list = ascUserGroupService.selectAscUserGroupList(ascUserGroup);
        return getDataTable(list);
    }

    /**
     * 导出用户组列表
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:export')")
    @Log(title = "用户组", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AscUserGroup ascUserGroup)
    {
        List<AscUserGroup> list = ascUserGroupService.selectAscUserGroupList(ascUserGroup);
        ExcelUtil<AscUserGroup> util = new ExcelUtil<AscUserGroup>(AscUserGroup.class);
        return util.exportExcel(list, "用户组数据");
    }

    /**
     * 获取用户组详细信息
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(ascUserGroupService.selectAscUserGroupById(groupId));
    }

    /**
     * 新增用户组
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:add')")
    @Log(title = "用户组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AscUserGroup ascUserGroup)
    {
        if (UserConstants.NOT_UNIQUE.equals(ascUserGroupService.checkUserGroupNameUnique(ascUserGroup.getGroupName())))
        {
            return AjaxResult.error("新增用户组'" + ascUserGroup.getGroupName()+ "'失败，用户组名称已存在");
        }
        return toAjax(ascUserGroupService.insertAscUserGroup(ascUserGroup));
    }

    /**
     * 修改用户组
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:edit')")
    @Log(title = "用户组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AscUserGroup ascUserGroup)
    {
        return toAjax(ascUserGroupService.updateAscUserGroup(ascUserGroup));
    }

    /**
     * 删除用户组
     */
    @PreAuthorize("@ss.hasPermi('group:user_group:remove')")
    @Log(title = "用户组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(ascUserGroupService.deleteAscUserGroupByIds(groupIds));
    }

    /**
     * 修改状态
     * @param bo
     * @return
     */
    @PutMapping("/editStatus")
    public AjaxResult editStatus(@Validated @RequestBody AcsUserGroupEditStatusBo bo) {
        return toAjax(ascUserGroupService.editStatus(bo));
    }

    /**
     * 修改用户组名称
     * @param bo
     * @return
     */
    @PutMapping("/editGroupName")
    public AjaxResult editGroupName(@Validated @RequestBody AcsUserGroupEditGroupNameBo bo) {
        return toAjax(ascUserGroupService.editGroupName(bo));
    }

    /**
     * 查询用户组中的用户ID数组
     * @param groupId
     * @return
     */
    @GetMapping("/userIds/{groupId}")
    public AjaxResult userIds(@PathVariable("groupId") Long groupId) {
        return AjaxResult.success(ascUserGroupService.selectUserIdsByGroupId(groupId));
    }

    /**
     * 更新用户组内用户
     * @param bo
     * @return
     */
    @PutMapping("/updateUser")
    public AjaxResult updateUser(@RequestBody AcsUserGroupUpdateUserBo bo) {
        return toAjax(ascUserGroupService.updateUser(bo));
    }

    /**
     * 查询不在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    @GetMapping("/listNotInDoorGroup")
    public AjaxResult selectListNotInDoorGroup(AscUserGroup ascUserGroup) {
        return AjaxResult.success(ascUserGroupService.selectListNotInDoorGroup(ascUserGroup));
    }

    /**
     * 查询在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    @GetMapping("/listInDoorGroup")
    public AjaxResult selectListInDoorGroup(AscUserGroup ascUserGroup) {
        return AjaxResult.success(ascUserGroupService.selectListInDoorGroup(ascUserGroup));
    }

}
