<template>
  <div class="app-container">

    <el-row>
      <el-row>
        <el-col :span="4" style=" border: 1px solid #eee; height: 800px">
          <div class="grid-content bg-purple">
            <el-tree
              class="treeitems"
              :data="door_groupList"
              node-key="id"
              @node-click="handleNodeClick"
              draggable
              ref="tree"
              default-expand-all
              :expand-on-click-node="false"
              :check-on-click-node="true"
            >
             <span class="custom-tree-node" slot-scope="{ node, data }">
               <span>{{ node.label }}</span>
               <span>
                 <i v-if="data.id == 0" @click="() => append(node,data)" class="el-icon-plus"></i><!--增加门禁组-->
                 <!-- 根节点不需要删除和重命名 -->
                 <i v-if="data.id !== 0" @click="() => deletes(node,data)" class="el-icon-delete"></i><!--删除门禁组-->
                 <i v-if="data.id !== 0" @click="() => rename(node,data)" class="el-icon-edit"></i><!--编辑门禁组-->
               </span>
             </span>
            </el-tree>
          </div>
        </el-col>
        <el-col :span="10" style="padding-right: 5px; border: 1px solid #eee;height: 800px">
          <span>门列表</span>
          <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handlerOpenGrantDoor"
          :disabled="doorGroupId ? false :true"
          style="margin-left: 5px;"
          v-hasPermi="['group:door_group:add']"
        >添加门
        </el-button>
          <div class="grid-content bg-purple" style="margin-top: 5px;">
            <el-table v-loading="loading" :data="doors" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"/>
<!--              <el-table-column label="主键" align="center" prop="doorId"/>-->
              <el-table-column label="门名称" align="center" prop="doorName" fixed/>
              <el-table-column label="控制器名称" align="center" prop="controllerName"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="remoteDoor(scope.row)"
                    v-hasPermi="['group:door_group:remove']"
                  >移除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination
              v-show="doorTotal>0"
              :total="doorTotal"
              :page.sync="doorQueryParams.pageNum"
              :limit.sync="doorQueryParams.pageSize"
              @pagination="getDoorList"
            />
          </div>
        </el-col>
        <el-col :span="10" style="padding-right: 5px; border: 1px solid #eee;height: 800px">
          <span>用户列表</span>
          <el-button
          type="success"
          icon="el-icon-plus"
          :disabled="doorGroupId ? false :true"
          @click="handlerSubmitChooseUserGroup(1)"
          style="margin-left: 5px;">添加用户组</el-button>
          <el-button
          type="success"
          icon="el-icon-minus"
          :disabled="doorGroupId ? false :true"
          @click="handlerSubmitChooseUserGroup(2)"
          style="margin-left: 5px;">删除用户组</el-button>
          <div class="grid-content bg-purple"  style="margin-top: 5px;">
            <el-table v-loading="loading" :data="users" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="用户名称" align="center" prop="nickName" fixed/>
              <el-table-column label="部门" align="center" prop="deptName"/>
              <el-table-column label="岗位" align="center" prop="postName"/>
<!--              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['group:door_group:remove']"
                  >移除
                  </el-button>
                </template>
              </el-table-column>-->
            </el-table>

            <pagination
              v-show="userTotal>0"
              :total="userTotal"
              :page.sync="userQueryParams.pageNum"
              :limit.sync="userQueryParams.pageSize"
              @pagination="getList"
            />
          </div>
        </el-col>
      </el-row>
    </el-row>

    <!--  选择门,展示出不在门禁组中的门  -->
    <grant-room ref="grantRoomDialog" :group-id="doorGroupId" @operationSuccess="operationSuccess"/>

    <!--  选择用户组,展示出不在门禁组中的用户组  -->
    <grant-user-group ref="grantUserGroupDialog" :group-id="doorGroupId" :type="type" @operationSuccess="operationSuccess"/>

    <!--  添加门禁组节点  -->
    <room-group ref="roomGroupDialog" :group-id="doorGroupId" @operationSuccess="operationSuccess"/>

    <el-dialog title="选择操作"
               :visible.sync="chooseOperationVisible"
               width="400px">
      <el-row>
        <el-col :span="24">
          <el-button
            type="success"
            @click="handlerSubmitChooseUserGroup(1)"
            style="width: 200px; margin-left: 75px;">添加</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-button
            type="success"
            @click="handlerSubmitChooseUserGroup(2)"
            style="width: 200px; margin-left: 75px;">移除</el-button>
        </el-col>
      </el-row>

    </el-dialog>
  </div>
</template>

<style>
.el-row {
  margin-bottom: 20px;
}
:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding-right: 10px;
  background-color: #f9fafc;
}
</style>

<script>
import {
  listDoor_group,
  getDoor_group,
  delDoor_group,
  addDoor_group,
  updateDoor_group,
  exportDoor_group,
  remove_door,
  distribution,
} from "@/api/group/door_group";
import GrantRoom from './grantRoom';
import GrantUserGroup from './grantUserGroup';
import RoomGroup from './roorGroup';
import { getDoorsByGroupId } from '@/api/device/door'
import { getUserVoInDoorGroupAndUserGroup} from '@/api/system/user';

export default {
  name: "Door_group",
  components: {
    GrantUserGroup,
    GrantRoom,
    RoomGroup
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
      // 门禁组表格数据
      door_groupList: [{id: 0, label: '门禁组', children: []}],
      doors: [],
      // 总条数
      doorTotal: 0,
      doorQueryParams: {
        pageNum: 1,
        pageSize: 10,
        doorGroupId: undefined,
      },
      users: [],
      userTotal: 0,
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        doorGroupId: undefined,
      },
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
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupName: [
          {required: true, message: "名称不能为空", trigger: "blur"}
        ],
      },
      filterText: '',
      // 分配用户组
      openGrantGroupUserDialog: false,
      // 门禁组id
      doorGroupId: undefined,
      chooseOperationVisible: undefined,
      type: undefined,
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询门禁组列表 */
    getList() {
      listDoor_group(this.queryParams).then(response => {
        this.door_groupList[0].children = response.data;
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加门禁组";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupId = row.groupId || this.ids
      getDoor_group(groupId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门禁组";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupId != null) {
            updateDoor_group(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoor_group(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$confirm('是否确认删除门禁组编号为"' + groupIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDoor_group(groupIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门禁组数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportDoor_group(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    // 添加节点
    append(node, data) {
     /* this.$prompt('节点名字', '增加节点', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        addDoor_group({groupName: value}).then(response => {
          this.getList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消修改'
        });
      });*/
      this.$refs.roomGroupDialog.show();
    },
    // 删除点
    deletes(node, data) {
      this.$confirm('是否确认删除该节点', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDoor_group(data.id);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    // 重命名节点
    rename(node, data) {
      /*this.$prompt('节点名字', '修改节点', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: node.label
      }).then(({value}) => {
        updateDoor_group({groupId: data.id, groupName: value}).then(response => {
          this.getList();
          this.queryTable();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消修改'
        });
      });*/
      this.doorGroupId = data.id;
      this.$nextTick(() => {
        this.$refs.roomGroupDialog.show();
      });
    },
    // 点击节点
    handleNodeClick(node) {
      this.doorGroupId = node.id
      this.queryTable()
    },
    // 开启分配门dialog
    handlerOpenGrantDoor() {
      this.$refs.grantRoomDialog.show();
    },
    // 开启分用户组dialog
    handlerOpenGrantUserGroup() {
      this.$refs.grantUserGroupDialog.show();
    },
    // 查询门禁组中的门
    getDoorList() {
      this.doorQueryParams.doorGroupId = this.doorGroupId;
      getDoorsByGroupId(this.doorQueryParams).then(response => {
        this.doors = response.rows;
        this.doorTotal = response.total;
      })
    },
    // 移除门
    remoteDoor(row) {
      this.$confirm('是否确认删除该门', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        const param = {doorId: row.doorId, groupId: this.doorGroupId};
        return remove_door(param);
      }).then(() => {
        this.getDoorList()
        this.msgSuccess("删除成功");
      })
    },
    // 查询门禁组中对应的用户
    getUserList() {
      this.userQueryParams.doorGroupId = this.doorGroupId;
      getUserVoInDoorGroupAndUserGroup(this.userQueryParams).then(response => {
        this.users = response.rows;
        this.userTotal = response.total;
      })
    },
    queryTable() {
      this.getDoorList();
      this.getUserList();
    },
    // 子页面操作完通知父页面
    operationSuccess() {
      this.getList();
    },
    // 选择用户组操作页面
    handlerChooseOperationDialog() {
      this.chooseOperationVisible = true;
    },
    // 点击选择按钮
    handlerSubmitChooseUserGroup(type) {
      this.type = type;
      this.chooseOperationVisible = false;
      this.$nextTick(() => {
        this.handlerOpenGrantUserGroup();
      })
    },
  }
};
</script>
