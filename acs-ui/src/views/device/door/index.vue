<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="门区" prop="pin">
        <el-input
          v-model="queryParams.pin"
          placeholder="请输入门区"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['device:door:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['device:door:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['device:door:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:door:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="doorId" />
      <el-table-column label="门名称" align="center" prop="doorName" />
      <el-table-column label="控制器" align="center" prop="controllerName" />
      <el-table-column label="门区" align="center" prop="pin" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-row>
            <el-button
              v-if="scope.row.status == 0 || scope.row.status == 4"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDoorOpen(scope.row)"
              v-hasPermi="['device:door:oper']"
            >开门</el-button>
            <el-button
              v-if="scope.row.status == 0 || scope.row.status == 4"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDoorClose(scope.row)"
              v-hasPermi="['device:door:oper']"
            >关门</el-button>
            <el-button
              v-if="scope.row.status == 0 || scope.row.status == 4"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDoorOpenAlways(scope.row)"
              v-hasPermi="['device:door:oper']"
            >常开</el-button>
            <el-button
              v-if="scope.row.status == 0 || scope.row.status == 4"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDoorCloseAlways(scope.row)"
              v-hasPermi="['device:door:oper']"
            >常闭</el-button>
            <el-button
              v-if="scope.row.status == 1 || scope.row.status == 2"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDoorNormal(scope.row)"
              v-hasPermi="['device:door:oper']"
            >恢复</el-button>
          </el-row>
          <el-row>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['device:door:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['device:door:remove']"
            >删除</el-button>
          </el-row>
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

    <!-- 添加或修改门禁门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门名称" prop="doorName">
          <el-input v-model="form.doorName" placeholder="请输入门名称" />
        </el-form-item>
       <el-form-item label="控制器" prop="controllerName">
        <el-select
          v-model="form.controllerName"
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
        <el-form-item label="门区" prop="pin">
          <el-input v-model="form.pin" placeholder="请输入门区" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDoor, listDoordf, getDoor, delDoor, addDoor, updateDoor, exportDoor, controlDoor } from "@/api/device/door";
import { listCon } from "@/api/device/_con"

export default {
  name: "Door",
  components: {
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
      // 门禁门表格数据
      doorList: [],
      // 门状态字典
      statusOptions: [],
      // 控制器列表
      controllerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doorName: null,
        controllerName: null,
        pin: null,
        status: null,
      },
      controlDoorParams:{
        controllerId:null,
        doorId:null,
        doorPin:null,
        cmd:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        doorName: [
          { required: true, message: "门名称不能为空", trigger: "blur" }
        ],
        controllerName: [
          { required: true, message: "控制器不能为空", trigger: "blur" }
        ],
        pin: [
          { required: true, message: "门区不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getControllerList();
    this.getDicts("device_door_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getList();
  },
  methods: {
    // 卡状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    /** 查询门禁门列表 */
    getList() {
      this.loading = true;
      listDoordf(this.queryParams).then(response => {
        this.doorList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.form = {
        doorId: null,
        doorName: null,
        controllerId: null,
        controllerName: null,
        pin: null,
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.ids = selection.map(item => item.doorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加门禁门";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const doorId = row.doorId || this.ids
      getDoor(doorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门禁门";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.controllerList.forEach(item =>{
            if (item.controllerName == this.form.controllerName){
              this.form.controllerId = item.controllerId;
            }
          });
          if (this.form.doorId != null) {
            updateDoor(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoor(this.form).then(response => {
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
      const doorIds = row.doorId || this.ids;
      this.$confirm('是否确认删除门禁门编号为"' + doorIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDoor(doorIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门禁门数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDoor(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    //开门
    handleDoorOpen(row){
      this.controlDoorParams.controllerId = row.controllerId
      this.controlDoorParams.doorId = row.doorId
      this.controlDoorParams.doorPin = row.pin
      this.controlDoorParams.cmd = 1
      controlDoor(this.controlDoorParams).then(response => {
        this.msgSuccess("开门成功");
      });
    },
    //关门
    handleDoorClose(row){
      this.controlDoorParams.controllerId = row.controllerId
      this.controlDoorParams.doorId = row.doorId
      this.controlDoorParams.doorPin = row.pin
      this.controlDoorParams.cmd = 0
      controlDoor(this.controlDoorParams).then(response => {
        this.msgSuccess("关门成功");
      });
    },
    //常开
    handleDoorOpenAlways(row){
      this.controlDoorParams.controllerId = row.controllerId
      this.controlDoorParams.doorId = row.doorId
      this.controlDoorParams.doorPin = row.pin
      this.controlDoorParams.cmd = 2
      controlDoor(this.controlDoorParams).then(response => {
        this.getList();
        this.msgSuccess("常开成功");
      });
    },
    //常闭
    handleDoorCloseAlways(row){
      this.controlDoorParams.controllerId = row.controllerId
      this.controlDoorParams.doorId = row.doorId
      this.controlDoorParams.doorPin = row.pin
      this.controlDoorParams.cmd = 3
      controlDoor(this.controlDoorParams).then(response => {
        this.getList();
        this.msgSuccess("常闭成功");
      });
    },
    //恢复
    handleDoorNormal(row){
      this.controlDoorParams.controllerId = row.controllerId
      this.controlDoorParams.doorId = row.doorId
      this.controlDoorParams.doorPin = row.pin
      this.controlDoorParams.cmd = 4
      controlDoor(this.controlDoorParams).then(response => {
        this.getList();
        this.msgSuccess("恢复成功");
      });
    }
  }
};
</script>
