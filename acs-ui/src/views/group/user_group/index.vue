<template>
  <div class="app-container">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['group:user_group:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['group:user_group:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['group:user_group:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['group:user_group:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="user_groupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="groupId" />
      <el-table-column label="用户组名称" align="center" prop="groupName">
        <template slot-scope="scope">
          <el-input v-model="scope.row.groupName"
                    v-if="scope.row.seen"
                    @blur="loseFocus(scope.$index, scope.row)"
                    @keyup.enter.native="loseFocus(scope.$index, scope.row)"
          />

          <span style="margin-left: 10px" v-else @click="cellClick(scope.row)">{{ scope.row.groupName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" align="center" prop="deptName"/>
      <el-table-column label="是否分配到门禁组" align="center" prop="su">
        <template slot-scope="scope">
          <span>{{scope.row.su > 0 ? '是' : '否'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdateIsInUserGroup(scope.row)"
            v-hasPermi="['group:user_group:edit']"
          >查看已有</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdateIsNotInUserGroup(scope.row)"
            v-hasPermi="['group:user_group:edit']"
          >添加用户</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['group:user_group:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户组对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1150px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入用户组名称" />
        </el-form-item>
        <el-form-item label="归属部门" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" :flat="true" :normalizer="my_normalizer" placeholder="请选择归属部门" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="人员">
          <div class="edit_dev">
            <el-col :span="6">
              <el-row>
                <el-form-item label="部门">
                  <treeselect v-model="queryTransferParam.deptId" :options="deptOptions" :show-count="true" :flat="true" :normalizer="my_normalizer" placeholder="请选择归属部门" />
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="身份类型" prop="identityId">
                  <el-select
                    v-model="queryTransferParam.identityId"
                    placeholder="身份类型"
                    clearable
                    size="small"
                  >
                    <el-option
                      v-for="post in postList"
                      :key="post.postId"
                      :label="post.postName"
                      :value="post.postId"
                      :disabled="post.status == 1"
                    />
                  </el-select>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="姓名">
                  <el-input v-model="queryTransferParam.nickName" placeholder="请输入姓名"/>
                </el-form-item>
              </el-row>
              <el-row>
                <el-button type="primary" icon="el-icon-search" @click="selectUserTransfer">搜索</el-button>
              </el-row>
          </el-col>
          <el-col :span="18" style="padding-left: 5px">
            <el-transfer
              style="text-align: left; display: inline-block"
              v-model="value"
              filterable
              :titles="['待选择', '已选择']"
              :format="{
                noChecked: '${total}',
                hasChecked: '${checked}/${total}'
              }"
              @change="handleChange"
              :data="transferData">
            </el-transfer>
          </el-col>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="chooseUserTitle" :visible.sync="chooseUserOpen" width="60%" append-to-body>
      <el-form ref="searchTransferForm" :model="queryTransferParam" label-width="80px" v-if="this.chooseUserForm.type === 2">
        <el-row>
          <el-col :span="6">
            <el-form-item label="部门">
              <treeselect v-model="queryTransferParam.deptId" :options="deptOptions" :show-count="true" :flat="true" :normalizer="my_normalizer" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="身份类型" prop="identityId">
              <el-select
                v-model="queryTransferParam.identityId"
                placeholder="身份类型"
                clearable
                size="small"
              >
                <el-option
                  v-for="post in postList"
                  :key="post.postId"
                  :label="post.postName"
                  :value="post.postId"
                  :disabled="post.status == 1"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="姓名">
              <el-input v-model="queryTransferParam.nickName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-search" @click="handleQueryTransferData" style="margin-left: 22px">搜索</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-form ref="chooseUserForm" :model="chooseUserForm" :rules="rules" label-width="80px">
        <el-form-item label="人员">
          <div class="edit_dev">
            <el-transfer
              style="text-align: left; display: inline-block"
              v-model="value"
              :titles="['待选择', '已选择']"
              :format="{
                noChecked: '${total}',
                hasChecked: '${checked}/${total}'
              }"
              @change="handleChange"
              :data="transferData">
            </el-transfer>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="chooseUserSubmitForm">确 定</el-button>
        <el-button @click="chooseUserCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<style scoped>
.edit_dev >>> .el-transfer-panel {
  width:300px;
}
</style>

<script>
import {
  listUser_group,
  getUser_group,
  delUser_group,
  addUser_group,
  updateUser_group,
  exportUser_group,
  getUserIds,
  updateUser_group_status,
  updateUser_group_groupName,
  updateUser_group_updateUser,
} from "@/api/group/user_group";
import {
  selectUserTransferVoList,
  selectUserTransferVoListInUserGroup,
  selectUserTransferVoListNotInUserGroup
} from '@/api/system/user';
import Template from "@/views/week/template";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { treeselect} from "@/api/system/dept";
import { listPost} from "@/api/system/post";

export default {
  name: "User_group",
  components: {
    Template,
    Treeselect
  },
  data() {
    return {
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
      chooseUserTitle: "",
      // 是否显示弹出层
      open: false,
      chooseUserOpen: false,
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupName: null,
        status: null,
      },
      // 表单参数
      form: {
        userIds: []
      },
      chooseUserForm: {
        userIds: [],
        type: 0,
        groupId: undefined
      },
      // 表单校验
      rules: {
        groupName: [
          { required: true, message: "用户组名称不能为空", trigger: "blur" }
        ],
        deptId: [
          {required: true, message: "部门不能为空", trigger: "blur"}
        ],
      },
      transferData: undefined,
      value: [],
      transferDataIds: [],
      // 部门树选项
      deptOptions: undefined,
      // 身份list
      postList:undefined,
      // 查询穿梭框参数
      queryTransferParam: {
        identityId: undefined,
        deptId: undefined,
        nickName: undefined,
        groupId: undefined,
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询用户组列表 */
    getList() {
      this.loading = true;
      listUser_group(this.queryParams).then(response => {
        this.user_groupList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    chooseUserCancel() {
      this.chooseUserOpen = false;
      this.reset();
      this.clearTransferValue();
      this.resetForm("searchTransferForm");
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
      this.transferData = [];

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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户组";
      this.getTreeselect();
      //this.selectUserTransfer();
      this.getPostList();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupId = row.groupId || this.ids
      getUser_group(groupId).then(response => {
        this.form = response.data;
        this.chooseUserOpen = true;
      });
      this.selectUserTransfer();
      this.selectUserIdsInUserGroup(groupId);
    },
    /** 查看已有操作 */
    handleUpdateIsInUserGroup(row) {
      this.chooseUserTitle = "查看已有"
      this.chooseUserForm.type = 1;
      this.reset();
      const groupId = row.groupId || this.ids
      this.value = [];
      selectUserTransferVoListInUserGroup(groupId).then(response => {
        this.chooseUserOpen = true;
        this.transferData = response.data;
        this.chooseUserForm.groupId = groupId;
        this.value = this.transferData.map(data => data.key)
        this.transferDataIds = this.transferData.map(data => data.key);
      })
    },
    /** 添加用户操作 */
    handleUpdateIsNotInUserGroup(row) {
      this.chooseUserTitle = "添加用户"
      this.chooseUserForm.type = 2;
      this.reset();
      this.value = [];
      const groupId = row.groupId || this.ids;
      this.queryTransferParam.groupId = groupId;
      /*selectUserTransferVoListNotInUserGroup(groupId, this.queryTransferParam).then(response => {
        this.chooseUserOpen = true;
        this.transferData = response.data;
        this.chooseUserForm.groupId = groupId;
      })*/
      this.chooseUserOpen = true;
      this.getTreeselect();
      this.getPostList();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.userIds = this.value;
          if (this.form.groupId != null) {
            updateUser_group(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.clearTransferValue();
            });
          } else {
            addUser_group(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.clearTransferValue();
            });
          }
        }
      });
    },
    // 修改用户组用户提交
    chooseUserSubmitForm() {
      this.$refs["chooseUserForm"].validate(valid => {
        if (valid) {
          const a = this.value
          if (this.chooseUserForm.type === 1) {
            this.chooseUserForm.userIds = Array.from(new Set([...this.transferDataIds].filter(x => !this.value.includes(x))));
          } else {
            this.chooseUserForm.userIds = this.value;
          }
          updateUser_group_updateUser(this.chooseUserForm).then(response => {
            this.msgSuccess("操作成功");
            this.chooseUserOpen = false;
            this.getList();
            this.clearTransferValue();
            this.resetForm("searchTransferForm");
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$confirm('是否确认删除用户组编号为"' + groupIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUser_group(groupIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户组数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUser_group(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    handleChange(value, direction, movedKeys) {
    },
    // 新增查询穿梭框数据
    selectUserTransfer() {
      this.clearTransferValue();
      selectUserTransferVoList(this.queryTransferParam).then(response => {
        this.transferData = response.data;
      })
    },
    // 查询用户组中已有用户数据
    selectUserIdsInUserGroup(groupId) {
      getUserIds(groupId).then(response => {
        this.value = response.data;
      })
    },
    // 清除穿梭框数据
    clearTransferValue() {
      this.value = [];
      this.transferData = [];
    },
    //　修改用户组状态
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + '' + row.groupId + '用户组吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return updateUser_group_status({groupId: row.groupId, status: row.status});
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    loseFocus(index, row) {   //当 input 失去焦点 时,input 切换为 span，并且让下方 表格消失（注意，与点击表格事件的执行顺序）
      row.seen = false;
      updateUser_group_groupName({groupId: row.groupId, groupName: row.groupName}).then(response => {
        this.getList();
      })
    },
    cellClick(row, column) {
      row.seen=true;
    },
    my_normalizer(node) {
      return {
        isDisabled: node.id == 100 ? true : false
      };
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询身份列表*/
    getPostList(){
      listPost().then(response =>{
        this.postList = response.rows;
      });
    },
    // 查询不在用户组中的用户
    handleQueryTransferData() {
      this.clearTransferValue();
      selectUserTransferVoListNotInUserGroup(this.queryTransferParam).then(response => {
        this.transferData = response.data;
        this.chooseUserForm.groupId = this.queryTransferParam.groupId;
      })
    }
  }
};
</script>
