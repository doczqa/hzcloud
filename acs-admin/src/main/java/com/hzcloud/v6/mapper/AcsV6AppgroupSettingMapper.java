package com.hzcloud.v6.mapper;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6AppgroupSetting;

/**
 * 基本设置Mapper接口
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public interface AcsV6AppgroupSettingMapper 
{
    /**
     * 查询基本设置
     * 
     * @param id 基本设置ID
     * @return 基本设置
     */
    public AcsV6AppgroupSetting selectAcsV6AppgroupSettingById(String id);

    /**
     * 查询基本设置列表
     * 
     * @param acsV6AppgroupSetting 基本设置
     * @return 基本设置集合
     */
    public List<AcsV6AppgroupSetting> selectAcsV6AppgroupSettingList(AcsV6AppgroupSetting acsV6AppgroupSetting);

    /**
     * 新增基本设置
     * 
     * @param acsV6AppgroupSetting 基本设置
     * @return 结果
     */
    public int insertAcsV6AppgroupSetting(AcsV6AppgroupSetting acsV6AppgroupSetting);

    /**
     * 修改基本设置
     * 
     * @param acsV6AppgroupSetting 基本设置
     * @return 结果
     */
    public int updateAcsV6AppgroupSetting(AcsV6AppgroupSetting acsV6AppgroupSetting);

    /**
     * 删除基本设置
     * 
     * @param id 基本设置ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupSettingById(String id);

    /**
     * 批量删除基本设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupSettingByIds(String[] ids);
}
