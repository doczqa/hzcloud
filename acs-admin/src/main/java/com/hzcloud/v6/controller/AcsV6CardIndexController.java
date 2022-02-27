package com.hzcloud.v6.controller;

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
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.service.IAcsV6CardIndexService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 卡片索引Controller
 * 
 * @author zhangfan
 * @date 2021-07-20
 */
@RestController
@RequestMapping("/card/index")
public class AcsV6CardIndexController extends BaseController
{
    @Autowired
    private IAcsV6CardIndexService acsV6CardIndexService;

    /**
     * 查询卡片索引列表
     */
    @PreAuthorize("@ss.hasPermi('card:index:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6CardIndex acsV6CardIndex)
    {
        startPage();
        List<AcsV6CardIndex> list = acsV6CardIndexService.selectAcsV6CardIndexList(acsV6CardIndex);
        return getDataTable(list);
    }

    /**
     * 导出卡片索引列表
     */
    @PreAuthorize("@ss.hasPermi('card:index:export')")
    @Log(title = "卡片索引", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6CardIndex acsV6CardIndex)
    {
        List<AcsV6CardIndex> list = acsV6CardIndexService.selectAcsV6CardIndexList(acsV6CardIndex);
        ExcelUtil<AcsV6CardIndex> util = new ExcelUtil<AcsV6CardIndex>(AcsV6CardIndex.class);
        return util.exportExcel(list, "卡片索引数据");
    }

    /**
     * 获取卡片索引详细信息
     */
    @PreAuthorize("@ss.hasPermi('card:index:query')")
    @GetMapping(value = "/{controllerId}")
    public AjaxResult getInfo(@PathVariable("controllerId") Long controllerId)
    {
        return AjaxResult.success(acsV6CardIndexService.selectAcsV6CardIndexById(controllerId));
    }

    /**
     * 新增卡片索引
     */
    @PreAuthorize("@ss.hasPermi('card:index:add')")
    @Log(title = "卡片索引", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6CardIndex acsV6CardIndex)
    {
        return toAjax(acsV6CardIndexService.insertAcsV6CardIndex(acsV6CardIndex));
    }

    /**
     * 修改卡片索引
     */
    @PreAuthorize("@ss.hasPermi('card:index:edit')")
    @Log(title = "卡片索引", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6CardIndex acsV6CardIndex)
    {
        return toAjax(acsV6CardIndexService.updateAcsV6CardIndex(acsV6CardIndex));
    }

    /**
     * 删除卡片索引
     */
    @PreAuthorize("@ss.hasPermi('card:index:remove')")
    @Log(title = "卡片索引", businessType = BusinessType.DELETE)
	@DeleteMapping("/{controllerIds}")
    public AjaxResult remove(@PathVariable Long[] controllerIds)
    {
        return toAjax(acsV6CardIndexService.deleteAcsV6CardIndexByIds(controllerIds));
    }

    @Log(title = "卡片索引", businessType = BusinessType.OTHER)
    @GetMapping("/getindex")
    public AjaxResult getCardIndex(@PathVariable("controllerId") Long controllerId, @PathVariable("cardId") Long cardId){
        return  AjaxResult.success(acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, cardId));
    }
}
