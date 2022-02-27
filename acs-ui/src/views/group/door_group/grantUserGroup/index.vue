<template>
  <div id="app-container">
    <el-dialog title="选择用户组"
               :visible.sync="visible" width="60%"
               :before-close="handleClose"
               @close="closeDialog">

      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="组名称" prop="groupName">
          <el-input
            v-model="queryParams.groupName"
            placeholder="请输入用户组名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table ref="dataTable"  v-loading="loading" :data="user_groupList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键" align="center" prop="groupId" />
        <el-table-column label="用户组名称" align="center" prop="groupName" />
      </el-table>

<!--      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />-->

      <span slot="footer" class="dialog-footer">
          <el-button @click="visible = false">取 消</el-button>
          <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser_group,
  listUser_group_listNotInDoorGroup,
  listUser_group_listInDoorGroup,
} from "@/api/group/user_group";

import {
  get_userGroupIdsByGroupId,
  add_userGroup,
  remove_userGroup
} from '@/api/group/door_group';

export default {
  name: 'GrantUserGroup',
  props: {
    groupId: undefined,
    type: undefined,
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户组表格数据
      user_groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupName: null,
        status: null,
        doorGroupId: null,
      },
      // 用户组ID列表
      userGroupIds: undefined,
    }
  },
  methods: {
    show() {
      this.visible = true;
      this.queryParams.doorGroupId = this.groupId;
      this.getList();
/*
      Promise.all([this.getList(), this.getUserGroupIds()]).then(() => {
        this.user_groupList.forEach(row => {
          if (this.userGroupIds.indexOf(row.groupId) >= 0) {
            this.$refs.dataTable.toggleRowSelection(row, true);
          }
        })
      })
*/
    },
    handleClose(done) {
      this.$confirm('确认关闭?')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    closeDialog() {
      this.$emit('closeArticle', this.ids);
    },
    submit() {
      if (this.type === 1) {
        add_userGroup({doorGroupId: this.groupId, userGroupIds: this.ids}).then(response => {
          this.visible = false;
          this.msgSuccess("操作成功");
          this.callParent();
        })
      } else if (this.type === 2) {
        remove_userGroup({doorGroupId: this.groupId, userGroupIds: this.ids}).then(response => {
          this.visible = false;
          this.msgSuccess("操作成功");
          this.callParent();
        })
      }
    },
    /** 查询用户组列表 */
    getList() {
      this.loading = true;
      return new Promise(((resolve, reject) => {
        if (this.type === 1) {
          listUser_group_listNotInDoorGroup(this.queryParams).then(response => {
            this.user_groupList = response.data;
            this.loading = false;
            resolve("success");
          });
        } else if (this.type === 2) {
          listUser_group_listInDoorGroup(this.queryParams).then(response => {
            this.user_groupList = response.data;
            this.loading = false;
            resolve("success");
          });
        }
      }))
    },
    /** 查询用户组ID列表*/
    getUserGroupIds() {
      return new Promise((resolve, reject) => {
        get_userGroupIdsByGroupId(this.groupId).then(response => {
          this.userGroupIds = response.data;
          resolve("success");
        })
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupId: null,
        groupName: null,
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.groupId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    callParent() {
      this.$emit('operationSuccess', {});
    },
  }
}
</script>
