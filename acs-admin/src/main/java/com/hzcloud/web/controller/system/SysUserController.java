package com.hzcloud.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hzcloud.system.domain.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.card.service.IAcsCardService;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.core.domain.entity.SysRole;
import com.hzcloud.common.core.domain.entity.SysUser;
import com.hzcloud.common.core.domain.model.LoginUser;
import com.hzcloud.common.core.page.TableDataInfo;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.common.utils.SecurityUtils;
import com.hzcloud.common.utils.ServletUtils;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.device.bo.DeleteUserData;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsDeviceConMapper;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.framework.web.service.TokenService;
import com.hzcloud.system.service.ISysPostService;
import com.hzcloud.system.service.ISysRoleService;
import com.hzcloud.system.service.ISysUserService;
import com.hzcloud.v6.service.IAcsV6CardIndexService;
import com.hzcloud.web.bo.UserAndCard;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IAcsCardService cardService;

    @Autowired
    private AcsDeviceConMapper deviceConMapper;

    @Autowired
    private IAcsV6CardIndexService acsV6CardIndexService;

    @Autowired
    private IAcsCommandQueueService commandQueueService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            //ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody UserAndCard userAndCard) {
        SysUser user = new SysUser();
        user.setAddress(userAndCard.getAddress());
        user.setDeptId(userAndCard.getDeptId());
        user.setIdNumber(userAndCard.getIdNumber());
        user.setIdType(userAndCard.getIdType());
        user.setIdentityId(userAndCard.getIdentityId());
        user.setNickName(userAndCard.getNickName());
        user.setPassword(userAndCard.getPassword());
        user.setPhonenumber(userAndCard.getPhonenumber());
        user.setRemark(userAndCard.getRemark());
        user.setRoleIds(userAndCard.getRoleIds());
        user.setSex(userAndCard.getSex());
        user.setStatus(userAndCard.getStatus());
        user.setUserName(userAndCard.getUserName());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userAndCard.getUserName()))) {
            return AjaxResult.error("新增用户'" + userAndCard.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(userAndCard.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }else if(StringUtils.isNotEmpty(user.getIdNumber())
        && UserConstants.NOT_UNIQUE.equals(userService.checkIdNumberUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，证件号码已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        int ret = userService.insertUser(user);
        String cardNumber = userAndCard.getCardNumber();
        Date endTime = userAndCard.getExpirationEndTime();
        if((cardNumber != null) && ( cardService.selectCardByIDNumber(userAndCard.getIdNumber()) ==null)  && (cardService.selectCardByCardNumber(cardNumber) == null)){
            AcsCard card = new AcsCard();
            card.setAddress(userAndCard.getAddress());
            card.setCardNumber(cardNumber);
            card.setCreateBy(SecurityUtils.getUsername());
            card.setDeptId(userAndCard.getDeptId());
            card.setExpirationEndTime(endTime == null? new Date(199,11,31):endTime);
            card.setExpirationStartTime(new Date());
            card.setIdNumber(userAndCard.getIdNumber());
            card.setIdType(userAndCard.getIdType());
            card.setIdentityId(userAndCard.getIdentityId());
            card.setPhonenumber(userAndCard.getPhonenumber());
            card.setSex(userAndCard.getSex());
            card.setStatus("0");
            card.setUserId(user.getUserId());
            card.setUserName(user.getNickName());
            cardService.insertAcsCard(card);
        }
        return toAjax(ret);
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        AcsCard card = cardService.selectCardByIDNumber(user.getIdNumber());
        if(card != null){
            card.setDeptId(user.getDeptId());
            card.setIdentityId(user.getIdentityId());
            card.setAddress(user.getAddress());
            card.setPhonenumber(user.getPhonenumber());
            card.setSex(user.getSex());
            card.setStatus(user.getStatus().equals("1")?"2":card.getStatus());
            cardService.updateAcsCard(card);
            if(user.getStatus().equals("1")){
                List<Long> controllerIds  = deviceConMapper.selectConListByCardId(card.getCardId());
                //缓存每个控制器的注销命令
                for(Long controllerId : controllerIds ){
                    AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                    if(con == null){
                        continue;
                    }
                    AcsCommandQueue commandQueue = new AcsCommandQueue();
                    commandQueue.setControllerId(controllerId);
                    commandQueue.setIp(con.getIp());
                    commandQueue.setCommand(ControllerCommandEnum.CANCEL_USER_INFO.command);
                    DeleteUserData userData = new DeleteUserData();
                    userData.setCardNumber(card.getCardNumber());
                    userData.setControllerIndex(con.getControllerIndex());
                    String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, card.getCardId());
                    if(cardIndex == null){
                        cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                        if(cardIndex == null){
                            continue;
                        }
                        acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, card.getCardId(), cardIndex);
                    }
                    userData.setCardIndex(cardIndex);
                    commandQueue.setData(userData.toString());
                    commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                    commandQueueService.insertAcsCommandQueue(commandQueue);
                    deviceControllerQueueService.execute(controllerId,con.getIp(), new Integer[]{4});
                }
            }
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        AcsCard card = cardService.selectCardByIDNumber(user.getIdNumber());
        if(card != null){
            card.setStatus(user.getStatus().equals("1")?"2":card.getStatus());
            cardService.updateAcsCard(card);
            if(user.getStatus().equals("1")){
                List<Long> controllerIds  = deviceConMapper.selectConListByCardId(card.getCardId());
                //缓存每个控制器的注销命令
                for(Long controllerId : controllerIds ){
                    AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                    if(con == null){
                        continue;
                    }
                    AcsCommandQueue commandQueue = new AcsCommandQueue();
                    commandQueue.setControllerId(controllerId);
                    commandQueue.setIp(con.getIp());
                    commandQueue.setCommand(ControllerCommandEnum.CANCEL_USER_INFO.command);
                    DeleteUserData userData = new DeleteUserData();
                    userData.setCardNumber(card.getCardNumber());
                    userData.setControllerIndex(con.getControllerIndex());
                    String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, card.getCardId());
                    if(cardIndex == null){
                        cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                        if(cardIndex == null){
                            continue;
                        }
                        acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, card.getCardId(), cardIndex);
                    }
                    userData.setCardIndex(cardIndex);
                    commandQueue.setData(userData.toString());
                    commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                    commandQueueService.insertAcsCommandQueue(commandQueue);
                    deviceControllerQueueService.execute(controllerId,con.getIp(), new Integer[]{4});
                }
            }
        }
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 查询用户穿梭框数据
     *
     * @return
     */
    @GetMapping("/userTransferList")
    public AjaxResult selectUserTransferVoList(SysUser user) {
        return AjaxResult.success(userService.selectUserTransferVoList(user));
    }

    /**
     * 查询在用户组中的用户数据
     *
     * @param userGroupId
     * @return
     */
    @GetMapping("/userTransferListInUserGroup/{userGroupId}")
    public AjaxResult selectUserTransferVoListInUserGroup(@PathVariable("userGroupId") Long userGroupId) {
        return AjaxResult.success(userService.selectUserTransferVoListInUserGroup(userGroupId));
    }

    /**
     * 查询不在用户组中的用户数据
     *
     * @param userGroupId
     * @return
     */
    @GetMapping("/userTransferListNotInUserGroup/{userGroupId}")
    public AjaxResult selectUserTransferVoListNotInUserGroup(@PathVariable("userGroupId") Long userGroupId, SysUser user) {
        return AjaxResult.success(userService.selectUserTransferVoListNotInUserGroup(userGroupId, user));
    }


    /**
     * 查询门禁组中的用户
     *
     * @param doorGroupId 　门禁组ID
     * @return
     */
    @GetMapping("/doorGroup/{doorGroupId}")
    public TableDataInfo userVoInDoorGroupAndUserGroup(@PathVariable("doorGroupId") Long doorGroupId) {
        startPage();
        List<UserVo> userVos = userService.selectUserVoInDoorGroupAndUserGroup(doorGroupId);
        return getDataTable(userVos);
    }
}
