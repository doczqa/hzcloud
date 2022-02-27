package com.hzcloud.card.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.card.bo.AcsUserImportInfo;
import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.card.service.IAcsCardService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.device.bo.DeleteUserData;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsDeviceConMapper;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.common.core.page.TableDataInfo;
import com.hzcloud.common.core.domain.model.LoginUser;
import com.hzcloud.framework.web.service.TokenService;
import com.hzcloud.system.domain.vo.UserVo;
import com.hzcloud.system.mapper.SysUserMapper;
import com.hzcloud.v6.service.IAcsV6CardIndexService;
import com.hzcloud.common.core.domain.entity.SysUser;
import com.hzcloud.common.utils.ServletUtils;

/**
 * 卡片管理Controller
 * 
 * @author zhangfan
 * @date 2021-04-18
 */
@RestController
@RequestMapping("/card")
public class AcsCardController extends BaseController
{
    @Autowired
    private IAcsCardService acsCardService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AcsDeviceConMapper deviceConMapper;

    @Autowired
    private IAcsCommandQueueService commandQueueService;
    
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IAcsV6CardIndexService acsV6CardIndexService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;
    /**
     * 查询卡片管理列表
     */
    @PreAuthorize("@ss.hasPermi('card:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsCard acsCard)
    {
        startPage();
        List<AcsCard> list = acsCardService.selectAcsCardList(acsCard);
        return getDataTable(list);
    }

    /**
     * 导出卡片管理列表
     */
    @PreAuthorize("@ss.hasPermi('card:export')")
    @Log(title = "卡片管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsCard acsCard)
    {
        List<AcsCard> list = acsCardService.selectAcsCardList(acsCard);
        ExcelUtil<AcsCard> util = new ExcelUtil<AcsCard>(AcsCard.class);
        return util.exportExcel(list, "card");
    }

    /**
     * 获取卡片管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('card:query')")
    @GetMapping(value = "/{cardId}")
    public AjaxResult getInfo(@PathVariable("cardId") Long cardId)
    {
        return AjaxResult.success(acsCardService.selectAcsCardById(cardId));
    }

    /**
     * 新增卡片管理
     */
    @PreAuthorize("@ss.hasPermi('card:add')")
    @Log(title = "卡片管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsCard acsCard)
    {
        if(acsCardService.selectCardByCardNumber(acsCard.getCardNumber()) != null){
            return AjaxResult.error("添加卡片'" + acsCard.getCardNumber() + "'失败，卡片已存在");
        }
        if(acsCardService.selectCardByIDNumber(acsCard.getIdNumber()) != null){
            return AjaxResult.error("添加卡片'" + acsCard.getCardNumber() + "'失败，证件号已存在");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsCard.setCreateBy(operName);
        acsCard.setExpirationStartTime(new Date());
        if(acsCard.getIdentityId() == 2){
            SysUser user = new SysUser();
            user.setDeptId(acsCard.getDeptId());
            user.setUserName(acsCard.getUserName());
            user.setNickName(acsCard.getUserName());
            user.setIdNumber(acsCard.getIdNumber());
            user.setIdType(acsCard.getIdType());
            user.setSex(acsCard.getSex());
            user.setAddress(acsCard.getAddress());
            user.setPhonenumber(acsCard.getPhonenumber());
            user.setIdentityId(acsCard.getIdentityId());
            user.setStatus("0");
            sysUserMapper.insertUser(user);
            acsCard.setUserId(user.getUserId());
        }
        return toAjax(acsCardService.insertAcsCard(acsCard));
    }

    /**
     * 注销卡片管理
     */
    @PreAuthorize("@ss.hasPermi('card:oper')")
    @Log(title = "卡片管理", businessType = BusinessType.UPDATE)
	@GetMapping("/cancel/{ids}")
    public AjaxResult cancel(@PathVariable Long[] ids)
    {
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        for(Long cardId : ids){
            AcsCard card = acsCardService.selectAcsCardById(cardId);
            if(card == null){
                continue;
            }
            List<Long> controllerIds  = deviceConMapper.selectConListByCardId(cardId);
            //缓存每个控制器的注销命令
            for(Long controllerId : controllerIds ){
                AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                if(con == null){
                    continue;
                }
                conIdIpMap.put(controllerId,con.getIp());
                AcsCommandQueue commandQueue = new AcsCommandQueue();
                commandQueue.setControllerId(controllerId);
                commandQueue.setIp(con.getIp());
                commandQueue.setCommand(ControllerCommandEnum.CANCEL_USER_INFO.command);
                DeleteUserData userData = new DeleteUserData();
                userData.setCardNumber(card.getCardNumber());
                userData.setControllerIndex(con.getControllerIndex());
                String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, cardId);
                if(cardIndex != null){
                    acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, (long)0, cardIndex);
                }
                userData.setCardIndex(cardIndex);
                commandQueue.setData(userData.toString());
                commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                commandQueueService.insertAcsCommandQueue(commandQueue);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{4});
            }
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        return toAjax(acsCardService.cancelAcsCardByIds(ids,operName));
    }

    /**
     * 冻结卡片管理
     */
    @PreAuthorize("@ss.hasPermi('card:oper')")
    @Log(title = "卡片管理", businessType = BusinessType.UPDATE)
	@GetMapping("/frozen/{ids}")
    public AjaxResult frozen(@PathVariable Long[] ids)
    {
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        for(Long cardId : ids){
            UserVo user = sysUserMapper.selectUserVoByCardId(cardId);
            if(user == null){
                continue;
            }
            List<Long> controllerIds  = deviceConMapper.selectConListByCardId(cardId);
            for(Long controllerId : controllerIds){
                AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                if(con == null){
                    continue;
                }
                conIdIpMap.put(controllerId,con.getIp());
                AcsCommandQueue commandQueue = new AcsCommandQueue();
                commandQueue.setControllerId(controllerId);
                commandQueue.setIp(con.getIp());
                commandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                IssueUserData userData = new IssueUserData();
                userData.setControllerIndex(con.getControllerIndex());
                String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, cardId);
                if(cardIndex == null){
                    cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                    if(cardIndex == null){
                        continue;
                    }
                    acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, cardId, cardIndex);      
                }
                userData.setCardIndex(cardIndex);
                userData.setUserName(user.getNickName());
                userData.setUserId(user.getUserId());
                userData.setCardNumber(user.getCardNumber());
                userData.setBeginTime(user.getExpirationStartTime());
                userData.setEndTime(user.getExpirationEndTime());
                userData.setFaceInfo(user.getFaceInfo());
                userData.setEnable(false);
                commandQueue.setData(userData.toString());
                commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                commandQueueService.insertAcsCommandQueue(commandQueue);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{2});
            }
        }
        return toAjax(acsCardService.frozenAcsCardByIds(ids));
    }

    /**
     * 解冻卡片管理
     */
    @PreAuthorize("@ss.hasPermi('card:oper')")
    @Log(title = "卡片管理", businessType = BusinessType.UPDATE)
	@GetMapping("/unfreeze/{ids}")
    public AjaxResult unfreeze(@PathVariable Long[] ids)
    {
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        for(Long cardId : ids){
            UserVo user = sysUserMapper.selectUserVoByCardId(cardId);
            if(user == null){
                continue;
            }
            List<Long> controllerIds  = deviceConMapper.selectConListByCardId(cardId);
            for(Long controllerId : controllerIds){
                AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                if(con == null){
                    continue;
                }
                conIdIpMap.put(controllerId,con.getIp());
                AcsCommandQueue commandQueue = new AcsCommandQueue();
                commandQueue.setControllerId(controllerId);
                commandQueue.setIp(con.getIp());
                commandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                IssueUserData userData = new IssueUserData();
                userData.setControllerIndex(con.getControllerIndex());
                String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, cardId);
                if(cardIndex == null){
                    cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                    if(cardIndex == null){
                        continue;
                    }
                    acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, cardId, cardIndex);
                }
                userData.setCardIndex(cardIndex);
                userData.setUserName(user.getNickName());
                userData.setUserId(user.getUserId());
                userData.setCardNumber(user.getCardNumber());
                userData.setBeginTime(user.getExpirationStartTime());
                userData.setEndTime(user.getExpirationEndTime());
                userData.setFaceInfo(user.getFaceInfo());
                userData.setEnable(true);
                commandQueue.setData(userData.toString());
                commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                commandQueueService.insertAcsCommandQueue(commandQueue);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{2});
            }
        }
        return toAjax(acsCardService.unfreezeAcsCardByIds(ids));
    }

    @Log(title = "卡片管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('card:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<AcsCard> util = new ExcelUtil<AcsCard>(AcsCard.class);
        List<AcsCard> cardList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = acsCardService.importCard(cardList, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<AcsCard> util = new ExcelUtil<AcsCard>(AcsCard.class);
        return util.importTemplateExcel("卡片数据");
    }

    @Log(title = "卡片管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('card:import')")
    @PutMapping("/importByUser")
    public AjaxResult importDataByUser(@RequestBody List<AcsUserImportInfo> userlist) throws Exception
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = acsCardService.importCardByUser(userlist, operName);
        return AjaxResult.success(message);
    }

    @PreAuthorize("@ss.hasPermi('card:oper')")
    @Log(title = "卡片管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updatetime")
    public AjaxResult updateTime(@RequestBody AcsCard acsCard){
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        Date endDate = acsCard.getExpirationEndTime();
        if((endDate.compareTo(new Date()) > 0) && acsCard.getStatus() == "3"  ){
            acsCard.setStatus("0");
            List<Long> controllerIds  = deviceConMapper.selectConListByCardId(acsCard.getCardId());
            for(Long controllerId : controllerIds){
                AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                if(con == null){
                    continue;
                }
                conIdIpMap.put(controllerId,con.getIp());
                AcsCommandQueue commandQueue = new AcsCommandQueue();
                commandQueue.setControllerId(controllerId);
                commandQueue.setIp(con.getIp());
                commandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                IssueUserData userData = new IssueUserData();
                userData.setControllerIndex(con.getControllerIndex());
                String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, acsCard.getCardId());
                if(cardIndex == null){
                    cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                    if(cardIndex == null){
                        continue;
                    }
                    acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, acsCard.getCardId(), cardIndex);
                }
                userData.setCardIndex(cardIndex);
                userData.setEnable(true);
                commandQueue.setData(userData.toString());
                commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                commandQueueService.insertAcsCommandQueue(commandQueue);
            }
            if(!conIdIpMap.isEmpty()){
                for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                    deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{2});
                }
            }
        }
        return toAjax(acsCardService.updateAcsCard(acsCard));
    }
}
