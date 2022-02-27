package com.hzcloud.device.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.core.domain.entity.SysRole;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.bo.AcsDeviceAppGroupSettingInfo;
import com.hzcloud.device.bo.AcsDeviceDayTime;
import com.hzcloud.device.bo.AcsDeviceHolidayGroupInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayPlanInfo;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.bo.AcsDeviceTimeInterval;
import com.hzcloud.device.bo.AcsDeviceTimeIntervalInfo;
import com.hzcloud.device.bo.AcsDeviceTimeZoneInfo;
import com.hzcloud.device.bo.AcsDeviceWeekTemplateInfo;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.config.DeviceConStatus;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsCommandQueueMapper;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;
import com.hzcloud.common.core.domain.model.LoginUser;
import com.hzcloud.framework.web.service.TokenService;
import com.hzcloud.group.domain.AcsHolidayGroup;
import com.hzcloud.group.domain.AcsHolidayPlan;
import com.hzcloud.group.domain.AcsWeekTemplate;
import com.hzcloud.group.domain.AscDoorGroupUserGroup;
import com.hzcloud.group.mapper.AscDoorGroupMapper;
import com.hzcloud.group.mapper.AscDoorGroupUserGroupMapper;
import com.hzcloud.group.service.IAcsHolidayGroupService;
import com.hzcloud.group.service.IAcsHolidayPlanService;
import com.hzcloud.group.service.IAcsWeekTemplateService;
import com.hzcloud.system.domain.vo.UserVo;
import com.hzcloud.system.service.ISysUserService;
import com.hzcloud.v6.domain.AcsV6AppgroupHoliday;
import com.hzcloud.v6.domain.AcsV6AppgroupSetting;
import com.hzcloud.v6.domain.AcsV6AppgroupWeek;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.domain.AcsV6Holiday;
import com.hzcloud.v6.domain.AcsV6Interval;
import com.hzcloud.v6.domain.AcsV6Timezone;
import com.hzcloud.v6.mapper.AcsV6CardIndexMapper;
import com.hzcloud.v6.service.IAcsV6AppgroupHolidayService;
import com.hzcloud.v6.service.IAcsV6AppgroupSettingService;
import com.hzcloud.v6.service.IAcsV6AppgroupWeekService;
import com.hzcloud.v6.service.IAcsV6HolidayService;
import com.hzcloud.v6.service.IAcsV6IntervalService;
import com.hzcloud.v6.service.IAcsV6TimezoneService;
import com.hzcloud.common.utils.ServletUtils;

/**
 * 门禁控制器Controller
 * 
 * @author 张帆
 * @date 2021-04-26
 */
@Slf4j
@RestController
@RequestMapping("/device/con")
public class AcsDeviceConController extends BaseController
{
    @Autowired
    private IAcsDeviceConService acsDeviceConService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IAcsHolidayPlanService holidayPlanService;

    @Autowired
    private IAcsHolidayGroupService holidayGroupService;

    @Autowired
    private IAcsWeekTemplateService weekTemplateService;

    @Autowired
    private IAcsCommandQueueService commandQueueService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    @Autowired
    private IAcsV6AppgroupSettingService v6AppgroupSettingService;

    @Autowired
    private IAcsV6AppgroupWeekService v6AppgroupWeekService;

    @Autowired
    private IAcsV6AppgroupHolidayService v6AppgroupHolidayService;

    @Autowired
    private IAcsV6HolidayService v6HolidayService;

    @Autowired
    private IAcsV6TimezoneService v6TimezoneService;

    @Autowired
    private IAcsV6IntervalService v6IntervalService;

    @Autowired
    private AscDoorGroupMapper doorGroupMapper;

    @Autowired
    private AscDoorGroupUserGroupMapper ascDoorGroupUserGroupMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private AcsDeviceDoorMapper doorMapper;

    @Autowired
	private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private AcsV6CardIndexMapper acsV6CardIndexMapper;

    /**
     * 查询门禁控制器列表
     */
    @PreAuthorize("@ss.hasPermi('device:con:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsDeviceCon acsDeviceCon)
    {
        startPage();
        List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConList(acsDeviceCon);
        return getDataTable(list);
    }

    /**
     * 查询门禁控制器列表
     */
    @GetMapping("/listNoPage")
    public AjaxResult listNoPage(AcsDeviceCon acsDeviceCon)
    {
        List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConList(acsDeviceCon);
        return AjaxResult.success(list);
    }

    /**
     * 根据区域ID查询门禁控制器列表
     * @param zoneId
     * @return
     */
    @GetMapping("/listByZoneId/{zoneId}")
    public AjaxResult listByZoneId(@PathVariable("zoneId") Long zoneId) {
        List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConListByZoneId(zoneId);
        return AjaxResult.success(list);
    }

    /**
     * 查询门禁控制器列表过滤
     */
    @PreAuthorize("@ss.hasPermi('device:con:list')")
    @GetMapping("/listdf")
    public TableDataInfo listdf(AcsDeviceCon acsDeviceCon)
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
            List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConList(acsDeviceCon);
            return getDataTable(list);
        }else{
            List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConListDataFilter(acsDeviceCon);
            return getDataTable(list);
        }
    }

    /**
     * 查询门禁控制器列表过滤
     */
    @GetMapping("/listdfNoPage")
    public AjaxResult listDataFilterNoPage()
    {
        List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConListDataFilter(new AcsDeviceCon());
        return AjaxResult.success(list);
    }

    /**
     * 导出门禁控制器列表
     */
    @PreAuthorize("@ss.hasPermi('device:con:export')")
    @Log(title = "门禁控制器", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsDeviceCon acsDeviceCon)
    {
        List<AcsDeviceCon> list = acsDeviceConService.selectAcsDeviceConList(acsDeviceCon);
        ExcelUtil<AcsDeviceCon> util = new ExcelUtil<AcsDeviceCon>(AcsDeviceCon.class);
        return util.exportExcel(list, "门禁控制器数据");
    }

    /**
     * 获取门禁控制器详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:con:query')")
    @GetMapping(value = "/{controllerId}")
    public AjaxResult getInfo(@PathVariable("controllerId") Long controllerId)
    {
        return AjaxResult.success(acsDeviceConService.selectAcsDeviceConById(controllerId));
    }

    /**
     * 新增门禁控制器
     */
    @PreAuthorize("@ss.hasPermi('device:con:add')")
    @Log(title = "门禁控制器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsDeviceCon acsDeviceCon)
    {
        if (UserConstants.NOT_UNIQUE.equals(acsDeviceConService.checkControllerNameUnique(acsDeviceCon.getControllerName())))
        {
            return AjaxResult.error("新增控制器'" + acsDeviceCon.getControllerName()+ "'失败，控制器名称已存在");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceCon.setCreateBy(operName);
        return toAjax(acsDeviceConService.insertAcsDeviceCon(acsDeviceCon));
    }

    /**
     * 修改门禁控制器
     */
    @PreAuthorize("@ss.hasPermi('device:con:edit')")
    @Log(title = "门禁控制器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsDeviceCon acsDeviceCon)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        acsDeviceCon.setUpdateBy(operName);
        return toAjax(acsDeviceConService.updateAcsDeviceCon(acsDeviceCon));
    }

    /**
     * 删除门禁控制器
     */
    @PreAuthorize("@ss.hasPermi('device:con:remove')")
    @Log(title = "门禁控制器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{controllerIds}")
    public AjaxResult remove(@PathVariable Long[] controllerIds)
    {
        return toAjax(acsDeviceConService.deleteAcsDeviceConByIds(controllerIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.UPDATE)
    @PutMapping("/changealarmStatus")
    public AjaxResult changeStatus(@RequestBody AcsDeviceCon con)
    {
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        AcsDeviceResult ret = new AcsDeviceResult();
        if(con.getAlarmStatus().equals("1")){
            ret = controller.SetupAlarmChan();
        }else{
            ret = controller.CloseAlarmChan();
        }  
        if (ret.isCode()){
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            con.setUpdateBy(operName);
            return toAjax(acsDeviceConService.updateAcsDeviceConAlarmStatus(con));
        }
        return toAjax(ret.isCode());
    }

    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.OTHER)
    @GetMapping("/weektemplate/{controllerId}")
    public AjaxResult issuedWeektemplate(@PathVariable("controllerId") Long controllerId, @RequestParam("ip") String ip){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if(controller == null){
            return AjaxResult.error("控制器未连接");
        }
        SimpleDateFormat formatter  = new SimpleDateFormat("HH:mm:ss");
        //添加weektemplate命令
        List<AcsWeekTemplate> weekTemplateList =  weekTemplateService.selectAcsWeekTemplateList(null);
        AcsDeviceWeekTemplateInfo[] weekTemplateInfos = new AcsDeviceWeekTemplateInfo[weekTemplateList.size()];
        for(int i = 0; i != weekTemplateList.size(); i++){
            AcsWeekTemplate weekTemplate = weekTemplateList.get(i);
            AcsDeviceWeekTemplateInfo weekTemplateInfo = new AcsDeviceWeekTemplateInfo();
            weekTemplateInfo.setWeekTemplateNumber(weekTemplate.getTemplateId());
            weekTemplateInfo.setWeekTemplateName(weekTemplate.getTemplateName());
            weekTemplateInfo.setHolidayGroupNumber(weekTemplate.getHolidayGroupId());
            AcsDeviceDayTime monDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime tueDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime wenDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime turDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime friDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime satDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime sunDaytime = new AcsDeviceDayTime();
            AcsDeviceDayTime[] daytimes = new AcsDeviceDayTime[]{monDaytime,tueDaytime,wenDaytime,turDaytime,friDaytime,satDaytime,sunDaytime};
            weekTemplateInfo.setWeekTime(daytimes);
            AcsDeviceTimeInterval monTimeInterval1 = new AcsDeviceTimeInterval();
            monTimeInterval1.setBeginTime(formatter.format(weekTemplate.getMonStartTime1()));
            monTimeInterval1.setEndTime(formatter.format(weekTemplate.getMonEndTime1()));
            AcsDeviceTimeInterval monTimeInterval2 = new AcsDeviceTimeInterval();
            monTimeInterval2.setBeginTime(formatter.format(weekTemplate.getMonStartTime2()));
            monTimeInterval2.setEndTime(formatter.format(weekTemplate.getMonEndTime2()));
            AcsDeviceTimeInterval monTimeInterval3 = new AcsDeviceTimeInterval();
            monTimeInterval3.setBeginTime(formatter.format(weekTemplate.getMonStartTime3()));
            monTimeInterval3.setEndTime(formatter.format(weekTemplate.getMonEndTime3()));
            AcsDeviceTimeInterval[] monTimeIntervals =  new AcsDeviceTimeInterval[]{monTimeInterval1,monTimeInterval2,monTimeInterval3};
            monDaytime.setTimes(monTimeIntervals);
            AcsDeviceTimeInterval tueTimeInterval1 = new AcsDeviceTimeInterval();
            tueTimeInterval1.setBeginTime(formatter.format(weekTemplate.getTueStartTime1()));
            tueTimeInterval1.setEndTime(formatter.format(weekTemplate.getTueEndTime1()));
            AcsDeviceTimeInterval tueTimeInterval2 = new AcsDeviceTimeInterval();
            tueTimeInterval2.setBeginTime(formatter.format(weekTemplate.getTueStartTime2()));
            tueTimeInterval2.setEndTime(formatter.format(weekTemplate.getTueEndTime2()));
            AcsDeviceTimeInterval tueTimeInterval3 = new AcsDeviceTimeInterval();
            tueTimeInterval3.setBeginTime(formatter.format(weekTemplate.getTueStartTime3()));
            tueTimeInterval3.setEndTime(formatter.format(weekTemplate.getTueEndTime3()));
            AcsDeviceTimeInterval[] tueTimeIntervals =  new AcsDeviceTimeInterval[]{tueTimeInterval1,tueTimeInterval2,tueTimeInterval3};
            tueDaytime.setTimes(tueTimeIntervals);
            AcsDeviceTimeInterval wenTimeInterval1 = new AcsDeviceTimeInterval();
            wenTimeInterval1.setBeginTime(formatter.format(weekTemplate.getWenStartTime1()));
            wenTimeInterval1.setEndTime(formatter.format(weekTemplate.getWenEndTime1()));
            AcsDeviceTimeInterval wenTimeInterval2 = new AcsDeviceTimeInterval();
            wenTimeInterval2.setBeginTime(formatter.format(weekTemplate.getWenStartTime2()));
            wenTimeInterval2.setEndTime(formatter.format(weekTemplate.getWenEndTime2()));
            AcsDeviceTimeInterval wenTimeInterval3 = new AcsDeviceTimeInterval();
            wenTimeInterval3.setBeginTime(formatter.format(weekTemplate.getWenStartTime3()));
            wenTimeInterval3.setEndTime(formatter.format(weekTemplate.getWenEndTime3()));
            AcsDeviceTimeInterval[] wenTimeIntervals =  new AcsDeviceTimeInterval[]{wenTimeInterval1,wenTimeInterval2,wenTimeInterval3};
            wenDaytime.setTimes(wenTimeIntervals);
            AcsDeviceTimeInterval turTimeInterval1 = new AcsDeviceTimeInterval();
            turTimeInterval1.setBeginTime(formatter.format(weekTemplate.getTurStartTime1()));
            turTimeInterval1.setEndTime(formatter.format(weekTemplate.getTurEndTime1()));
            AcsDeviceTimeInterval turTimeInterval2 = new AcsDeviceTimeInterval();
            turTimeInterval2.setBeginTime(formatter.format(weekTemplate.getTurStartTime2()));
            turTimeInterval2.setEndTime(formatter.format(weekTemplate.getTurEndTime2()));
            AcsDeviceTimeInterval turTimeInterval3 = new AcsDeviceTimeInterval();
            turTimeInterval3.setBeginTime(formatter.format(weekTemplate.getTurStartTime3()));
            turTimeInterval3.setEndTime(formatter.format(weekTemplate.getTurEndTime3()));
            AcsDeviceTimeInterval[] turTimeIntervals =  new AcsDeviceTimeInterval[]{turTimeInterval1,turTimeInterval2,turTimeInterval3};
            turDaytime.setTimes(turTimeIntervals);
            AcsDeviceTimeInterval friTimeInterval1 = new AcsDeviceTimeInterval();
            friTimeInterval1.setBeginTime(formatter.format(weekTemplate.getMonStartTime1()));
            friTimeInterval1.setEndTime(formatter.format(weekTemplate.getMonEndTime1()));
            AcsDeviceTimeInterval friTimeInterval2 = new AcsDeviceTimeInterval();
            friTimeInterval2.setBeginTime(formatter.format(weekTemplate.getFriStartTime2()));
            friTimeInterval2.setEndTime(formatter.format(weekTemplate.getFriEndTime2()));
            AcsDeviceTimeInterval friTimeInterval3 = new AcsDeviceTimeInterval();
            friTimeInterval3.setBeginTime(formatter.format(weekTemplate.getFriStartTime3()));
            friTimeInterval3.setEndTime(formatter.format(weekTemplate.getFriEndTime3()));
            AcsDeviceTimeInterval[] friTimeIntervals =  new AcsDeviceTimeInterval[]{friTimeInterval1,friTimeInterval2,friTimeInterval3};
            friDaytime.setTimes(friTimeIntervals);
            AcsDeviceTimeInterval satTimeInterval1 = new AcsDeviceTimeInterval();
            satTimeInterval1.setBeginTime(formatter.format(weekTemplate.getSatStartTime1()));
            satTimeInterval1.setEndTime(formatter.format(weekTemplate.getSatEndTime1()));
            AcsDeviceTimeInterval satTimeInterval2 = new AcsDeviceTimeInterval();
            satTimeInterval2.setBeginTime(formatter.format(weekTemplate.getSatStartTime2()));
            satTimeInterval2.setEndTime(formatter.format(weekTemplate.getSatEndTime2()));
            AcsDeviceTimeInterval satTimeInterval3 = new AcsDeviceTimeInterval();
            satTimeInterval3.setBeginTime(formatter.format(weekTemplate.getSatStartTime3()));
            satTimeInterval3.setEndTime(formatter.format(weekTemplate.getSatEndTime3()));
            AcsDeviceTimeInterval[] satTimeIntervals =  new AcsDeviceTimeInterval[]{satTimeInterval1,satTimeInterval2,satTimeInterval3};
            satDaytime.setTimes(satTimeIntervals);
            AcsDeviceTimeInterval sunTimeInterval1 = new AcsDeviceTimeInterval();
            sunTimeInterval1.setBeginTime(formatter.format(weekTemplate.getSunStartTime1()));
            sunTimeInterval1.setEndTime(formatter.format(weekTemplate.getSunEndTime1()));
            AcsDeviceTimeInterval sunTimeInterval2 = new AcsDeviceTimeInterval();
            sunTimeInterval2.setBeginTime(formatter.format(weekTemplate.getSunStartTime2()));
            sunTimeInterval2.setEndTime(formatter.format(weekTemplate.getSunEndTime2()));
            AcsDeviceTimeInterval sunTimeInterval3 = new AcsDeviceTimeInterval();
            sunTimeInterval3.setBeginTime(formatter.format(weekTemplate.getSunStartTime3()));
            sunTimeInterval3.setEndTime(formatter.format(weekTemplate.getSunEndTime3()));
            AcsDeviceTimeInterval[] sunTimeIntervals =  new AcsDeviceTimeInterval[]{sunTimeInterval1,sunTimeInterval2,sunTimeInterval3};
            sunDaytime.setTimes(sunTimeIntervals);
            weekTemplateInfos[i] = weekTemplateInfo;
        }
        AcsCommandQueue commandQueue = new AcsCommandQueue();
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(ip);
        commandQueue.setCommand(ControllerCommandEnum.ADD_TEMPLATE.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(Arrays.toString(weekTemplateInfos));
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);

        //添加holidaygroup命令
        List<AcsHolidayGroup>  holidayGroups = holidayGroupService.selectAcsHolidayGroupList(null);
        AcsDeviceHolidayGroupInfo[] holidayGroupInfos = new AcsDeviceHolidayGroupInfo[holidayGroups.size()];
        for (int i = 0; i != holidayGroups.size(); i++ ){
            AcsHolidayGroup holidayGroup = holidayGroups.get(i);
            List<Long> planList = holidayPlanService.selectPlanListByGroupId(holidayGroup.getGroupId());
            Long[] planIds = new Long[planList.size()];
            planIds = planList.toArray(planIds);
            holidayGroup.setPlanIds(planIds);
            AcsDeviceHolidayGroupInfo holidayGroupInfo = new AcsDeviceHolidayGroupInfo();
            holidayGroupInfo.setHolidayGroupNumber(holidayGroup.getGroupId());
            holidayGroupInfo.setHolidayGroupName(holidayGroup.getGroupName());
            holidayGroupInfo.setHolidayPlanNumbers(holidayGroup.getPlanIds());
            holidayGroupInfos[i] = holidayGroupInfo;
        }
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(ip);
        commandQueue.setCommand(ControllerCommandEnum.ADD_HOLIDAY_GROUP.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(Arrays.toString(holidayGroupInfos));
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);
        
        //添加holidayPlan命令
        List<AcsHolidayPlan>  holidayPlans = holidayPlanService.selectAcsHolidayPlanList(null);
        AcsDeviceHolidayPlanInfo[] holidayPlanInfos = new AcsDeviceHolidayPlanInfo[holidayPlans.size()];
        for (int i = 0; i != holidayPlans.size(); i++ ){
            AcsHolidayPlan holidayPlan = holidayPlans.get(i);
            AcsDeviceHolidayPlanInfo holidayPlanInfo = new AcsDeviceHolidayPlanInfo();
            holidayPlanInfo.setHolidayPlanNumber(holidayPlan.getPlanId());
            holidayPlanInfo.setStartDate(holidayPlan.getStartDate());
            holidayPlanInfo.setEndDate(holidayPlan.getEndDate());
            AcsDeviceTimeInterval timeInterval1 = new AcsDeviceTimeInterval();
            timeInterval1.setBeginTime(formatter.format(holidayPlan.getStartTime1()));
            timeInterval1.setEndTime(formatter.format(holidayPlan.getEndTime1()));
            AcsDeviceTimeInterval timeInterval2 = new AcsDeviceTimeInterval();
            timeInterval2.setBeginTime(formatter.format(holidayPlan.getStartTime2()));
            timeInterval2.setEndTime(formatter.format(holidayPlan.getEndTime2()));
            AcsDeviceTimeInterval timeInterval3 = new AcsDeviceTimeInterval();
            timeInterval3.setBeginTime(formatter.format(holidayPlan.getStartTime3()));
            timeInterval3.setEndTime(formatter.format(holidayPlan.getEndTime3()));
            AcsDeviceTimeInterval[] timeIntervals =  new AcsDeviceTimeInterval[]{timeInterval1,timeInterval2,timeInterval3};
            holidayPlanInfo.setTimes(timeIntervals);
            holidayPlanInfos[i] = holidayPlanInfo;
        }
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(ip);
        commandQueue.setCommand(ControllerCommandEnum.ADD_HOLIDAY_PLAN.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(Arrays.toString(holidayPlanInfos));
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);
        //执行命令
        deviceControllerQueueService.execute(controllerId, ip ,new Integer[]{5,8,11});
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.OTHER)
    @GetMapping("/holidayplan/{controllerId}")
    public AjaxResult issuedHolidayPlan(@PathVariable("controllerId") Long controllerId, @RequestParam("ip") String ip){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        SimpleDateFormat formatter  = new SimpleDateFormat("HH:mm:ss");
        //添加holidayplan命令
        List<AcsHolidayPlan>  holidayPlans = holidayPlanService.selectAcsHolidayPlanList(null);
        AcsDeviceHolidayPlanInfo[] holidayPlanInfos = new AcsDeviceHolidayPlanInfo[holidayPlans.size()];
        for (int i = 0; i != holidayPlans.size(); i++ ){
            AcsHolidayPlan holidayPlan = holidayPlans.get(i);
            AcsDeviceHolidayPlanInfo holidayPlanInfo = new AcsDeviceHolidayPlanInfo();
            holidayPlanInfo.setHolidayPlanNumber(holidayPlan.getPlanId());
            holidayPlanInfo.setStartDate(holidayPlan.getStartDate());
            holidayPlanInfo.setEndDate(holidayPlan.getEndDate());
            AcsDeviceTimeInterval timeInterval1 = new AcsDeviceTimeInterval();
            timeInterval1.setBeginTime(formatter.format(holidayPlan.getStartTime1()));
            timeInterval1.setEndTime(formatter.format(holidayPlan.getEndTime1()));
            AcsDeviceTimeInterval timeInterval2 = new AcsDeviceTimeInterval();
            timeInterval2.setBeginTime(formatter.format(holidayPlan.getStartTime2()));
            timeInterval2.setEndTime(formatter.format(holidayPlan.getEndTime2()));
            AcsDeviceTimeInterval timeInterval3 = new AcsDeviceTimeInterval();
            timeInterval3.setBeginTime(formatter.format(holidayPlan.getStartTime3()));
            timeInterval3.setEndTime(formatter.format(holidayPlan.getEndTime3()));
            AcsDeviceTimeInterval[] timeIntervals =  new AcsDeviceTimeInterval[]{timeInterval1,timeInterval2,timeInterval3};
            holidayPlanInfo.setTimes(timeIntervals);
            holidayPlanInfos[i] = holidayPlanInfo;
        }
        AcsCommandQueue commandQueue = new AcsCommandQueue();
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(ip);
        commandQueue.setCommand(ControllerCommandEnum.ADD_HOLIDAY_PLAN.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(Arrays.toString(holidayPlanInfos));
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);
        //执行命令
        deviceControllerQueueService.execute(controllerId, ip,new Integer[]{8});
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.OTHER)
    @GetMapping("/appgroup/{controllerId}")
    public AjaxResult issuedAppGroup(@PathVariable("controllerId") Long controllerId){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        
        AcsDeviceAppGroupSettingInfo[] appgroupsettings = new AcsDeviceAppGroupSettingInfo[15];
        List<AcsV6AppgroupSetting> v6AppgroupSettings = v6AppgroupSettingService.selectAcsV6AppgroupSettingList(null);
        List<AcsV6AppgroupWeek> v6AppgroupWeeks = v6AppgroupWeekService.selectAcsV6AppgroupWeekList(null);
        List<AcsV6AppgroupHoliday> v6AppgroupHolidays = v6AppgroupHolidayService.selectAcsV6AppgroupHolidayList(null);
        for(int i = 0; i != 15; i++ ){
            AcsDeviceAppGroupSettingInfo appGroupSetting = new AcsDeviceAppGroupSettingInfo();
            appGroupSetting.setAppId(v6AppgroupSettings.get(i).getId());
            appGroupSetting.setControllerIndex(con.getControllerIndex());
            appGroupSetting.setOut(v6AppgroupSettings.get(i).getDoorOut().equals("1")?"F":"0");
            appGroupSetting.setReader(v6AppgroupSettings.get(i).getDoor());
            appGroupSetting.setPerPinTz(v6AppgroupSettings.get(i).getPerPinTz1()+v6AppgroupSettings.get(i).getPerPinTz2()+v6AppgroupSettings.get(i).getPerPinTz3()+v6AppgroupSettings.get(i).getPerPinTz4());
            appGroupSetting.setSecPinTz(v6AppgroupSettings.get(i).getSecPinTz1()+v6AppgroupSettings.get(i).getSecPinTz2()+v6AppgroupSettings.get(i).getSecPinTz3()+v6AppgroupSettings.get(i).getSecPinTz4());
            appGroupSetting.setHolidayTz(v6AppgroupHolidays.get(i).getPreHolidayTz1()+v6AppgroupHolidays.get(i).getPreHolidayTz2()+v6AppgroupHolidays.get(i).getPreHolidayTz3()+v6AppgroupHolidays.get(i).getPreHolidayTz4()+v6AppgroupHolidays.get(i).getPreHolidayTz5()+v6AppgroupHolidays.get(i).getPreHolidayTz6()+v6AppgroupHolidays.get(i).getPreHolidayTz7()+v6AppgroupHolidays.get(i).getPreHolidayTz8()+v6AppgroupHolidays.get(i).getHolidayTz1()+v6AppgroupHolidays.get(i).getHolidayTz2()+v6AppgroupHolidays.get(i).getHolidayTz3()+v6AppgroupHolidays.get(i).getHolidayTz4()+v6AppgroupHolidays.get(i).getHolidayTz5()+v6AppgroupHolidays.get(i).getHolidayTz6()+v6AppgroupHolidays.get(i).getHolidayTz7()+v6AppgroupHolidays.get(i).getHolidayTz8()+v6AppgroupHolidays.get(i).getPostHolidayTz1()+v6AppgroupHolidays.get(i).getPostHolidayTz2()+v6AppgroupHolidays.get(i).getPostHolidayTz3()+v6AppgroupHolidays.get(i).getPostHolidayTz4()+v6AppgroupHolidays.get(i).getPostHolidayTz5()+v6AppgroupHolidays.get(i).getPostHolidayTz6()+v6AppgroupHolidays.get(i).getPostHolidayTz7()+v6AppgroupHolidays.get(i).getPostHolidayTz8());
            appGroupSetting.setHolidayOut(v6AppgroupHolidays.get(i).getPreHolidayTz1Out()+v6AppgroupHolidays.get(i).getPreHolidayTz2Out()+v6AppgroupHolidays.get(i).getPreHolidayTz3Out()+v6AppgroupHolidays.get(i).getPreHolidayTz4Out()+v6AppgroupHolidays.get(i).getPreHolidayTz5Out()+v6AppgroupHolidays.get(i).getPreHolidayTz6Out()+v6AppgroupHolidays.get(i).getPreHolidayTz7Out()+v6AppgroupHolidays.get(i).getPreHolidayTz8Out()+v6AppgroupHolidays.get(i).getHolidayTz1Out()+v6AppgroupHolidays.get(i).getHolidayTz2Out()+v6AppgroupHolidays.get(i).getHolidayTz3Out()+v6AppgroupHolidays.get(i).getHolidayTz4Out()+v6AppgroupHolidays.get(i).getHolidayTz5Out()+v6AppgroupHolidays.get(i).getHolidayTz6Out()+v6AppgroupHolidays.get(i).getHolidayTz7Out()+v6AppgroupHolidays.get(i).getHolidayTz8Out()+v6AppgroupHolidays.get(i).getPostHolidayTz1Out()+v6AppgroupHolidays.get(i).getPostHolidayTz2Out()+v6AppgroupHolidays.get(i).getPostHolidayTz3Out()+v6AppgroupHolidays.get(i).getPostHolidayTz4Out()+v6AppgroupHolidays.get(i).getPostHolidayTz5Out()+v6AppgroupHolidays.get(i).getPostHolidayTz6Out()+v6AppgroupHolidays.get(i).getPostHolidayTz7Out()+v6AppgroupHolidays.get(i).getPostHolidayTz8Out());
            appGroupSetting.setWeekTz(v6AppgroupWeeks.get(i).getSunTz()+v6AppgroupWeeks.get(i).getMonTz()+v6AppgroupWeeks.get(i).getTueTz()+v6AppgroupWeeks.get(i).getWedTz()+v6AppgroupWeeks.get(i).getThuTz()+v6AppgroupWeeks.get(i).getFriTz()+v6AppgroupWeeks.get(i).getSatTz());
            appGroupSetting.setWeekOut(v6AppgroupWeeks.get(i).getSunTzOut()+v6AppgroupWeeks.get(i).getMonTzOut()+v6AppgroupWeeks.get(i).getTueTzOut()+v6AppgroupWeeks.get(i).getWedTzOut()+v6AppgroupWeeks.get(i).getThuTzOut()+v6AppgroupWeeks.get(i).getFriTzOut()+v6AppgroupWeeks.get(i).getSatTzOut());
            appgroupsettings[i]=appGroupSetting;
        }
        
        AcsCommandQueue commandQueue = new AcsCommandQueue();
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(con.getIp());
        commandQueue.setCommand(ControllerCommandEnum.ISSUED_APPGROUP.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(Arrays.toString(appgroupsettings));
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);
        //执行命令
        deviceControllerQueueService.execute(controllerId, con.getIp(),new Integer[]{14});
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.OTHER)
    @GetMapping("/dateandtime/{controllerId}")
    public AjaxResult issuedDateAndTime(@PathVariable("controllerId") Long controllerId){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        AcsDeviceTimeZoneInfo[] timezoneInfos = new AcsDeviceTimeZoneInfo[60];
        AcsDeviceTimeIntervalInfo[] intervalInfos = new AcsDeviceTimeIntervalInfo[32];
        AcsDeviceHolidayInfo[] holidayInfos = new AcsDeviceHolidayInfo[12];
        List<AcsV6Holiday> v6Holidays = v6HolidayService.selectAcsV6HolidayList(null);
        List<AcsV6Timezone> v6Timezones = v6TimezoneService.selectAcsV6TimezoneList(null);
        List<AcsV6Interval> v6Intervals = v6IntervalService.selectAcsV6IntervalList(null);
        for(int i=0; i!=60;i++){
            AcsDeviceTimeZoneInfo timezoneInfo = new AcsDeviceTimeZoneInfo();
            timezoneInfo.setControllerIndex(con.getControllerIndex());
            timezoneInfo.setNn(v6Timezones.get(i).getId());
            timezoneInfo.setTimeZone(v6Timezones.get(i).getInterval1()+v6Timezones.get(i).getInterval2()+v6Timezones.get(i).getInterval3());
            timezoneInfos[i] = timezoneInfo;
        }
        for(int i=0; i!=32;i++){
            AcsDeviceTimeIntervalInfo intervalInfo = new AcsDeviceTimeIntervalInfo();
            intervalInfo.setControllerIndex(con.getControllerIndex());
            intervalInfo.setNn(v6Intervals.get(i).getId());
            intervalInfo.setStart(v6Intervals.get(i).getStartTime());
            intervalInfo.setEnd(v6Intervals.get(i).getEndTime());
            intervalInfos[i] = intervalInfo;
        }
        for(int i=0; i!=12;i++){
            AcsDeviceHolidayInfo holidayInfo = new AcsDeviceHolidayInfo();
            holidayInfo.setControllerIndex(con.getControllerIndex());
            holidayInfo.setMm(v6Holidays.get(i).getMonth());
            holidayInfo.setH(v6Holidays.get(i).getHolidays());
            holidayInfos[i] = holidayInfo;
        }
        JSONObject json = new JSONObject();
        json.put("timezone", timezoneInfos);
        json.put("timeInterval",intervalInfos);
        json.put("holiday",holidayInfos);
        

        AcsCommandQueue commandQueue = new AcsCommandQueue();
        commandQueue.setControllerId(controllerId); 
        commandQueue.setIp(con.getIp());
        commandQueue.setCommand(ControllerCommandEnum.ISSUED_DATEANDTIME.command);
        commandQueue.setCreateTime(new Date());
        commandQueue.setData(json.toJSONString());
        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
        commandQueueService.insertAcsCommandQueue(commandQueue);
        //执行命令
        deviceControllerQueueService.execute(controllerId, con.getIp(),new Integer[]{15});
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('device:con:oper')")
    @Log(title = "门禁控制器", businessType = BusinessType.OTHER)
    @GetMapping("/issuedalluser/{controllerId}")
    public AjaxResult issuedAllUser(@PathVariable("controllerId") Long controllerId){
        AcsDeviceCon con = acsDeviceConService.selectAcsDeviceConById(controllerId);
        if(con == null){
            return AjaxResult.error("控制器未识别");
        }
        AcsDeviceController controller = DeviceConStatus.getDeviceController(con.getIp());
        if (controller == null){
            return AjaxResult.error("控制器未连接");
        }
        log.info("开始下载全部用户");
        //获取控制器对应的门禁组列表id
        List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
        List<Long> doorGroupIds = doorGroupMapper.selectGroupIdsByControllerId(controllerId);
        for(Long doorGroupId : doorGroupIds){
            AscDoorGroupUserGroup condition = new AscDoorGroupUserGroup();
            condition.setDoorGroupId(doorGroupId);
            List<AscDoorGroupUserGroup> doorGroupUserGroupList = ascDoorGroupUserGroupMapper.selectAscDoorGroupUserGroupList(condition);
            if (doorGroupUserGroupList != null && doorGroupUserGroupList.size() > 0) {
                //　查询门禁组中的用户信息
                List<UserVo> userVoList = userService.selectUserVoInDoorGroupAndUserGroup(doorGroupId);
                // 获取用户id数组
                List<Long> userIds = userVoList.stream().map(userVo -> userVo.getUserId()).collect(Collectors.toList());
                List<Long> cardIds = userVoList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());
                if (userIds != null && userIds.size() > 0) {
                    Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
                    for(UserVo user : userVoList){
                        if(user.getCardStatus().equals("2")){
                            usermap.put(user.getUserId(), new UserVo());
                        }else{
                            usermap.put(user.getUserId(), user);
                        }
                    }
                    Map<Long,String> cardIdIndexMap = new HashMap<Long,String>();
                    List<AcsV6CardIndex> cardIndexList = acsV6CardIndexMapper.selectCardIndexByCardIds(controllerId, cardIds);
                    for(Long cardId : cardIds){
                        cardIdIndexMap.put(cardId,null);
                    }
                    for(AcsV6CardIndex cardIndex : cardIndexList){
                        cardIdIndexMap.put(cardIndex.getCardId(),cardIndex.getCardIndex());
                    }
                    List<Long> nullKeys = new ArrayList<Long>();
                    cardIdIndexMap.entrySet().stream().filter(item->(item.getValue() == null)).forEach(item->nullKeys.add(item.getKey()));
                    List<String> freeCardIndexList = acsV6CardIndexMapper.batchGetFreeCardIndex(controllerId, nullKeys.size());
                    List<AcsV6CardIndex> needUpdateList = new ArrayList<AcsV6CardIndex>();
                    for(int i = 0; i != freeCardIndexList.size(); i++) {
                        cardIdIndexMap.put(nullKeys.get(i),freeCardIndexList.get(i));
                        AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                        updateCardIndex.setControllerId(controllerId);
                        updateCardIndex.setCardId(nullKeys.get(i));
                        updateCardIndex.setCardIndex(freeCardIndexList.get(i));
                        needUpdateList.add(updateCardIndex);
                    }
                    if(needUpdateList.size() > 0){
                        acsV6CardIndexMapper.barchUpdate(controllerId,needUpdateList);
                    }

                    //　查询门列表
                    List<AcsDeviceDoor> doorList = doorMapper.selectDoorsInDoorGroupByControllerId(doorGroupId, controllerId);
                    for (AcsDeviceDoor door : doorList) {
                        // 以门为单位存储队列数据
                        for(Long userId : userIds){
                            AcsCommandQueue commandQueue = new AcsCommandQueue();
                            commandQueue.setControllerId(door.getControllerId());
                            commandQueue.setIp(con.getIp());
                            commandQueue.setDoorPin(door.getPin());
                            commandQueue.setCommand(ControllerCommandEnum.ISSUED_USER_INFO.command);
                            commandQueue.setCreateTime(new Date());
                            IssueUserData userData = new IssueUserData();
                            userData.setControllerIndex(con.getControllerIndex());
                            String cardIndex = cardIdIndexMap.get(usermap.get(userId).getCardId());//acsV6CardIndexService.getAcsV6CardIndexCardIndex(door.getControllerId(), usermap.get(userId).getCardId());
                            if(cardIndex == null){
                                continue;
                            }
                            userData.setCardIndex(cardIndex);
                            userData.setUserName(usermap.get(userId).getNickName());
                            userData.setUserId(usermap.get(userId).getUserId());
                            userData.setCardNumber(usermap.get(userId).getCardNumber());
                            userData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                            userData.setEndTime(usermap.get(userId).getExpirationEndTime());
                            userData.setEnable(true);
                            userData.setFaceInfo(usermap.get(userId).getFaceInfo());
                            commandQueue.setData(userData.toString());
                            commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                            comlist.add(commandQueue);
                            if(usermap.get(userId).getCardStatus().equals("1")||usermap.get(userId).getCardStatus().equals("3")){
                                AcsCommandQueue enablecommandQueue = new AcsCommandQueue();
                                enablecommandQueue.setControllerId(controllerId);
                                enablecommandQueue.setIp(con.getIp());
                                enablecommandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                                IssueUserData enableuserData = new IssueUserData();
                                enableuserData.setControllerIndex(con.getControllerIndex());
                                enableuserData.setCardIndex(cardIndex);
                                enableuserData.setUserName(usermap.get(userId).getNickName());
                                enableuserData.setUserId(usermap.get(userId).getUserId());
                                enableuserData.setCardNumber(usermap.get(userId).getCardNumber());
                                enableuserData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                                enableuserData.setEndTime(usermap.get(userId).getExpirationEndTime());
                                enableuserData.setFaceInfo(usermap.get(userId).getFaceInfo());
                                enableuserData.setEnable(false);
                                enablecommandQueue.setData(enableuserData.toString());
                                enablecommandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                                comlist.add(enablecommandQueue);
                            }
                        }
                    }
                }
            }
        }
        log.info("批量插入任务");
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsCommandQueueMapper commandQueueMapperNew = sqlSession.getMapper(AcsCommandQueueMapper.class);
        int batchCount = 100;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < comlist.size();) {
            if (batchLastIndex >= comlist.size()) {
                batchLastIndex = comlist.size();
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                break;// 数据插入完毕，退出循环
            } else {
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index + batchCount;
            }
        }
        log.info("开始执行下载用户任务");
        deviceControllerQueueService.execute(controllerId, con.getIp(),new Integer[]{1,2});
        return AjaxResult.success();
    }
}