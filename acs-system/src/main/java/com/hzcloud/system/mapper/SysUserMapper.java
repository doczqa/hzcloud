package com.hzcloud.system.mapper;

import java.util.List;

import com.hzcloud.system.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import com.hzcloud.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
@Repository
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     * 
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     * 
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验证件号码是否唯一
     *
     * @param idNumber 手机号码
     * @return 结果
     */
    public SysUser checkIdNumberUnique(String idNumber);

    /**
     * 通过身份ID查询身份使用数量
     * 
     * @param postId 身份ID
     * @return 结果
     */
    public int countUserPostById(Long postId);

    /**
     * 查询用户vo
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tsu.user_id as userId,\n" +
            "\tsu.nick_name as nickName,\n" +
            "\tsu.status as status,\n" +
            "\tsu.id_type as idType,\n" +
            "\tsu.id_number as idNumber,\n" +
            "\tsd.dept_name as deptName,\n" +
            "\tsp.post_name as postName,\n" +
            "\tsu.face_info AS faceInfo,\n" +
            "\tac.card_number AS cardNumber,\n" +
            "\tac.card_id AS cardId,\n" +
            "\tac.expiration_start_time AS expirationStartTime,\n" +
            "\tac.expiration_end_time AS expirationEndTime,\n" +
            "\tac.status AS cardStatus \n" +
            "FROM\n" +
            "\tsys_user su\n" +
            "\tLEFT JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
            "\tLEFT JOIN sys_post sp ON su.identity_id = sp.post_id \n" +
            "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
            "WHERE\n" +
            "\tsu.del_flag =0" +
            "<if test=\"user.identityId != null\">\tAND su.identity_id= #{user.identityId}</if>" +
            "<if test=\"user.nickName != null and user.nickName != ''\">\tAND su.nick_name LIKE concat('%', #{user.nickName}, '%')</if>" +
            "<if test=\"user.deptId != null and user.deptId != 0\">\tAND (su.dept_id = #{user.deptId} OR su.dept_id IN (SELECT dept_id FROM sys_dept t WHERE find_in_set(#{user.deptId}, ancestors)))</if>" +
            "</script>")
    List<UserVo> selectUserVo(@Param("user") SysUser user);

    /**
     * 查询在用户组中的用户数据
     * @param userGroupId
     * @return
     */
    @Select("SELECT\n" +
            "\tsu.user_id AS userId,\n" +
            "\tsu.nick_name AS nickName,\n" +
            "\tsu.status AS status,\n" +
            "\tsu.id_type as idType,\n" +
            "\tsu.id_number as idNumber,\n" +
            "\tsd.dept_name AS deptName,\n" +
            "\tsp.post_name AS postName,\n" +
            "\tsu.face_info AS faceInfo,\n" +
            "\tac.card_number AS cardNumber,\n" +
            "\tac.card_id AS cardId,\n" +
            "\tac.expiration_start_time AS expirationStartTime,\n" +
            "\tac.expiration_end_time AS expirationEndTime,\n" +
            "\tac.status AS cardStatus \n" +
            "FROM\n" +
            "\tsys_user su\n" +
            "\tLEFT JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
            "\tLEFT JOIN sys_post sp ON su.identity_id = sp.post_id\n" +
            "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
            "WHERE\n" +
            "\tEXISTS ( SELECT * FROM asc_user_group_user b WHERE b.user_id = su.user_id AND b.user_group_id = #{userGroupId} ) \n" +
            "\tAND su.del_flag = 0")
    List<UserVo> selectUserTransferVoListInUserGroup(Long userGroupId);


    /**
     * 查询不在用户组中的用户数据
     * @param userGroupId
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tsu.user_id AS userId,\n" +
            "\tsu.nick_name AS nickName,\n" +
            "\tsu.status AS status,\n" +
            "\tsu.id_type as idType,\n" +
            "\tsu.id_number as idNumber,\n" +
            "\tsd.dept_name AS deptName,\n" +
            "\tsp.post_name AS postName,\n" +
            "\tsu.face_info AS faceInfo,\n" +
            "\tac.card_number AS cardNumber,\n" +
            "\tac.card_id AS cardId,\n" +
            "\tac.expiration_start_time AS expirationStartTime,\n" +
            "\tac.expiration_end_time AS expirationEndTime,\n" +
            "\tac.status AS cardStatus \n" +
            "FROM\n" +
            "\tsys_user su\n" +
            "\tLEFT JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
            "\tLEFT JOIN sys_post sp ON su.identity_id = sp.post_id\n" +
            "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM asc_user_group_user b WHERE b.user_id = su.user_id AND b.user_group_id = #{userGroupId} ) \n" +
            "\tAND su.del_flag = 0" +
            "<if test=\"user.identityId != null\">\tAND su.identity_id= #{user.identityId}</if>" +
            "<if test=\"user.nickName != null and user.nickName != ''\">\tAND su.nick_name LIKE concat('%', #{user.nickName}, '%')</if>" +
            "<if test=\"user.deptId != null and user.deptId != 0\">\tAND (su.dept_id = #{user.deptId} OR su.dept_id IN (SELECT t.dept_id FROM sys_dept t WHERE find_in_set(#{user.deptId}, ancestors)))</if>" +
            "</script>")
    List<UserVo> selectUserTransferVoListNotInUserGroup(@Param("userGroupId") Long userGroupId, @Param("user") SysUser user);

    /**
     * 查询门禁组中的用户
     * @param doorGroupId　门禁组ID
     * @return
     */
    @Select("SELECT DISTINCT\n" +
            "\tsu.user_id AS userId,\n" +
            "\tsu.nick_name AS nickName,\n" +
            "\tsu.status AS status,\n" +
            "\tsu.id_type as idType,\n" +
            "\tsu.id_number as idNumber,\n" +
            "\tsd.dept_name AS deptName,\n" +
            "\tsp.post_name AS postName,\n" +
            "\tsu.face_info AS faceInfo,\n" +
            "\tac.card_number AS cardNumber,\n" +
            "\tac.card_id AS cardId,\n" +
            "\tac.expiration_start_time AS expirationStartTime,\n" +
            "\tac.expiration_end_time AS expirationEndTime,\n" +
            "\tac.status AS cardStatus \n" +
            "FROM\n" +
            "\tsys_user su\n" +
            "\tLEFT JOIN asc_user_group_user augu ON su.user_id = augu.user_id\n" +
            "\tLEFT JOIN asc_door_group_user_group adgug ON augu.user_group_id = adgug.user_group_id\n" +
            "\tLEFT JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
            "\tLEFT JOIN sys_post sp ON su.identity_id = sp.post_id \n" +
            "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
            "WHERE\n" +
            "\tadgug.door_group_id = #{doorGroupId}")
    List<UserVo> selectUserVoInDoorGroupAndUserGroup(@Param("doorGroupId") Long doorGroupId);

    /** 查询用户包含卡片信息 */
    @Select("SELECT \n" +
        "\tsu.user_id AS userId,\n" +
        "\tsu.nick_name AS nickName,\n" +
        "\tsu.status AS stuaus,\n" +
        "\tsu.id_type as idType,\n" +
        "\tsu.id_number as idNumber,\n" +
        "\tsu.face_info AS faceInfo,\n" +
        "\tac.card_number AS cardNumber,\n" +
        "\tac.card_id AS cardId,\n" +
        "\tac.expiration_start_time AS expirationStartTime,\n" +
        "\tac.expiration_end_time AS expirationEndTime,\n" +
        "\tac.status AS cardStatus \n" +
        "FROM\n" +
        "\tsys_user su\n" +
        "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
        "WHERE\n" +
        "\tsu.user_id = #{userId}")
    UserVo selectUserVoByUserId(@Param("userId") Long userId);

    //批量查询用户卡片信息
    @Select("<script>SELECT su.user_id AS userId, su.nick_name AS nickName, su.status AS status, su.id_type AS idType, su.id_number AS idNumber, su.face_info AS faceInfo, ac.card_number AS cardNumber, ac.card_id AS cardId, ac.expiration_start_time AS expirationStartTime, ac.expiration_end_time AS expirationEndTime, ac.status AS cardStatus FROM sys_user su LEFT JOIN acs_card ac ON su.user_id = ac.user_id  WHERE su.user_id in"+
            "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach></script>")
    List<UserVo> selectUserVoByUserIds(List<Long> list);

    @Select("SELECT \n" +
        "\tsu.user_id AS userId,\n" +
        "\tsu.nick_name AS nickName,\n" +
        "\tsu.status AS status,\n" +
        "\tsu.id_type as idType,\n" +
        "\tsu.id_number as idNumber,\n" +
        "\tsu.face_info AS faceInfo,\n" +
        "\tac.card_number AS cardNumber,\n" +
        "\tac.card_id AS cardId,\n" +
        "\tac.expiration_start_time AS expirationStartTime,\n" +
        "\tac.expiration_end_time AS expirationEndTime,\n" +
        "\tac.status AS cardStatus \n" +
        "FROM\n" +
        "\tsys_user su\n" +
        "\tLEFT JOIN acs_card ac ON su.user_id = ac.user_id \n" +
        "WHERE\n" +
        "\tac.card_id = #{cardId}")
    UserVo selectUserVoByCardId(@Param("cardId") Long cardId);

    /**
     * 通过证件号获取用户
     */
    public SysUser selectUserByIDNumber(@Param("idNumber")String idNumber);
}
