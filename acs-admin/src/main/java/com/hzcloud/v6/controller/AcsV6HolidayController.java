package com.hzcloud.v6.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.v6.bo.AcsV6HolidayBo;
import com.hzcloud.v6.domain.AcsV6Holiday;
import com.hzcloud.v6.service.IAcsV6HolidayService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 假日设置Controller
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@RestController
@RequestMapping("/appgroup/holiday")
public class AcsV6HolidayController extends BaseController
{
    @Autowired
    private IAcsV6HolidayService acsV6HolidayService;

    /**
     * 查询假日设置列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:list')")
    @GetMapping("/list")
    public AjaxResult list(AcsV6Holiday acsV6Holiday)
    {
        List<AcsV6Holiday> holidayList = acsV6HolidayService.selectAcsV6HolidayList(null);
        List<AcsV6HolidayBo>  list =  new ArrayList<AcsV6HolidayBo>();
        for(AcsV6Holiday holiday : holidayList){
            String month = holiday.getMonth();
            byte[] holidayArray = holiday.getHolidays().getBytes();
            for(int i = 0; i != 31 ; i++){
                if (holidayArray[i] != '0'){
                    AcsV6HolidayBo hbo = new AcsV6HolidayBo();
                    hbo.setType(String.valueOf((char)holidayArray[i]));
                    hbo.setHoliday(new Date( new Date().getYear() , new Integer(month).intValue() - 1, i+1));
                    list.add(hbo);
                }
            }  
        }
        return  AjaxResult.success(list);
    }

    /**
     * 导出假日设置列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:export')")
    @Log(title = "假日设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6Holiday acsV6Holiday)
    {
        List<AcsV6Holiday> list = acsV6HolidayService.selectAcsV6HolidayList(acsV6Holiday);
        ExcelUtil<AcsV6Holiday> util = new ExcelUtil<AcsV6Holiday>(AcsV6Holiday.class);
        return util.exportExcel(list, "假日设置数据");
    }

    /**
     * 获取假日设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:query')")
    @GetMapping(value = "/{month}")
    public AjaxResult getInfo(@PathVariable("month") String month)
    {
        return AjaxResult.success(acsV6HolidayService.selectAcsV6HolidayById(month));
    }

    /**
     * 新增假日设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:add')")
    @Log(title = "假日设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6Holiday acsV6Holiday)
    {
        return toAjax(acsV6HolidayService.insertAcsV6Holiday(acsV6Holiday));
    }

    /**
     * 修改假日设置
     */
    // @PreAuthorize("@ss.hasPermi('appgroup:holiday:edit')")
    // @Log(title = "假日设置", businessType = BusinessType.UPDATE)
    // @PutMapping
    // public AjaxResult edit(@RequestBody AcsV6Holiday acsV6Holiday)
    // {
    //     return toAjax(acsV6HolidayService.updateAcsV6Holiday(acsV6Holiday));
    // }

    /**
     * 修改假日设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:edit')")
    @Log(title = "假日设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6HolidayBo[] acsV6HolidayBos)
    {
        Map<String,byte[]> holidayMap = new HashMap<>();
        for(AcsV6HolidayBo hbo : acsV6HolidayBos){
           String month = String.format("%02d",hbo.getHoliday().getMonth() + 1);
           if(!holidayMap.containsKey(month)){
               byte[] holidayInit = "0000000000000000000000000000000".getBytes();
               holidayMap.put(month,holidayInit);
           }
           int index = hbo.getHoliday().getDate();
           byte[] holidays = holidayMap.get(month);
           holidays[index - 1] = hbo.getType().getBytes()[0];
           holidayMap.put(month,holidays);
        }
        holidayMap.forEach((key, value) -> {
            AcsV6Holiday v6Holiday = new AcsV6Holiday();
            v6Holiday.setMonth(key);
            v6Holiday.setHolidays(new String(value));
            acsV6HolidayService.updateAcsV6Holiday(v6Holiday);
        });

        return AjaxResult.success();
    }

    /**
     * 删除假日设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holiday:remove')")
    @Log(title = "假日设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{months}")
    public AjaxResult remove(@PathVariable String[] months)
    {
        return toAjax(acsV6HolidayService.deleteAcsV6HolidayByIds(months));
    }
}
