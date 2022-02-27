<template>
  <div class="app-container">
    <el-dialog title="选择门"
               :visible.sync="visible" width="60%"
               :before-close="handleClose"
               @close="closeDialog">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="门名称" prop="doorName">
        <el-input
          v-model="queryParams.doorName"
          placeholder="请输入门名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="控制器" prop="controllerName">
        <el-select
          v-model="queryParams.controllerName"
          placeholder="控制器"
          clearable
          filterable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="controller in controllerList"
            :key="controller.controllerId"
            :label="controller.controllerName"
            :value="controller.controllerName"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>-->

    <el-row>
      <el-table ref="dataTable"
                v-loading="loading"
                :data="doorList"
                @selection-change="handleSelectionChange"
                :row-key="getRowKey"
                lazy
                :load="loadChildren"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
        <el-table-column label="名称" align="center" prop="name" />
        <el-table-column label="类型" align="center" prop="type">
          <template slot-scope="scope">
            {{scope.row.type === '0' ? '控制器' : '门'}}
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" />
      </el-table>
<!--
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />-->
    </el-row>

    <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDoor,
  listNotInDoorGroup,
  getControllerAndDoorTree,
} from "@/api/device/door";
import { listCon } from "@/api/device/_con"
import {　add_door, getDoorIdsByGroupId　} from '@/api/group/door_group';

export default {
  name: "GrantRoom",
  props: {
    groupId: undefined,
  },
  components: {
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
      // 门禁门表格数据
      doorList: [],
      // 控制器列表
      controllerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        doorName: null,
        controllerName: null,
        status: null,
        groupId: undefined,
        type: 0,
        parentId: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 门id数组
      roomIds: undefined,
    };
  },
  created() {
    this.getControllerList();
    this.reset();
  },
  methods: {
    show() {
      this.reset();
      this.visible = true;
      this.queryParams.groupId = this.groupId;
      // 默认选择
/*
      Promise.all([this.getList(), this.getDoorsIds()]).then(()=>{
        this.doorList.forEach(row => {
          if (this.roomIds.indexOf(row.doorId) >=0) {
            this.$refs.dataTable.toggleRowSelection(row,true);
          }
        })
      })
*/
      this.getList()
    },
    /** 查询门禁门列表 */
    getList() {
      this.loading = true;
      return new Promise(((resolve, reject) => {
        /*listNotInDoorGroup(this.queryParams).then(response => {
          this.doorList = response.data;
          this.loading = false;
          resolve("success")
        });*/
        getControllerAndDoorTree(this.queryParams).then(response => {
          this.doorList = response.data;
          this.loading = false;
          resolve("success")
        });
      }))
    },
    /** 查询控制器列表*/
    getControllerList(){
      listCon().then(response =>{
        this.controllerList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.queryParams = {
        doorName: null,
        controllerName: null,
        status: null,
        type: 0,
        parentId: undefined,
      };
      this.doorList = undefined;
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
    handleSelectionChange(selection, row) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    submit() {
      add_door({groupId: this.groupId, doorList: this.ids}).then(resposne => {
        this.visible = false;
        this.msgSuccess("操作成功");
        this.callParent();
      })
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
      this.$emit('operationSuccess', {});
    },
    callParent() {
      this.$emit('operationSuccess', {});
    },
    loadChildren(tree, treeNode, resolve) {
      this.queryParams.type = 1;
      this.queryParams.parentId = tree.id;
      getControllerAndDoorTree(this.queryParams).then(response => {
        // this.doorList = response.data;
        resolve(response.data);
      });
    },
    getRowKey(row) {
      if (row.type === '0') {
        return "a" + row.id;
      } else {
        return "b" + row.id;
      }
    },
    // 控制是否能勾选
    selectable(row, index) {
      if (row.type === '0') {
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>
