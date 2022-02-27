<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="控制器名" prop="controllerName">
        <el-input
          v-model="queryParams.controllerName"
          placeholder="请输入控制器名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="ip">
        <el-input
          v-model="queryParams.ip"
          placeholder="请输入IP地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择设备类型" clearable size="small">
          <el-option
            v-for="dict in controllerTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['device:con:add']"
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
          v-hasPermi="['device:con:edit']"
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
          v-hasPermi="['device:con:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:con:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="conList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="controllerId" />
      <el-table-column label="控制器名" align="center" prop="controllerName" />
      <el-table-column label="IP地址" align="center" prop="ip" />
      <el-table-column label="编号" align="center" prop="controllerIndex" />
      <el-table-column label="卡容量" align="center" prop="capacity" />
      <el-table-column label="设备类型" align="center" prop="type" :formatter="controllerTypeFormat"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
 <!-- 
      <el-table-column label="布防" align="center" key="alarmStatus">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.alarmStatus"
            active-value="1"
            inactive-value="0"
            @change="handleAlarmStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 1"
            @click="handleIssueUser(scope.row)"
            v-hasPermi="['device:con:oper']"
          >下发全部人员</el-button>
<!--
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 1"
            @click="handleIssueWeekTemplate(scope.row)"
            v-hasPermi="['device:con:oper']"
          >下发周计划模板</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 1"
            @click="handleIssueHolidayPlan(scope.row)"
            v-hasPermi="['device:con:oper']"
          >下发假日计划</el-button>
-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 1"
            @click="handleIssueAppGroup(scope.row)"
            v-hasPermi="['device:con:oper']"
          >下发应用群组</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 1"
            @click="handleIssueDateAndTime(scope.row)"
            v-hasPermi="['device:con:oper']"
          >下发时间设置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:con:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:con:remove']"
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

    <!-- 添加或修改门禁控制器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="控制器名" prop="controllerName">
              <el-input v-model="form.controllerName" placeholder="请输入控制器名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择设备类型">
                <el-option
                v-for="dict in controllerTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="编号" prop="controllerIndex">
              <el-input v-model="form.controllerIndex" placeholder="请输入控制器编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="卡容量" prop="capacity">
              <el-input v-model="form.capacity" placeholder="请输入卡容量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="IP地址" prop="ip">
              <el-input v-model="form.ip" placeholder="请输入IP地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口" prop="port">
              <el-input v-model="form.port" placeholder="请输入端口" />
            </el-form-item>
          </el-col>
        </el-row>
<!---
        <el-row>
          <el-col :span="12">
            <el-form-item label="登陆名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入登陆名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登陆密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入登陆密码" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
--->
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCon,listCondf, getCon, delCon, addCon, updateCon, exportCon, changeAlarmStatus, issueHolidayPlan, issueWeekTemplate, issueAppGroup, issueDateAndTime, issueAllUser} from "@/api/device/_con";

export default {
  name: "Con",
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
      // 门禁控制器表格数据
      conList: [],
      // 控制器类别
      controllerTypeOptions:[],
      // 门禁控制器状态字典
      statusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        controllerName: null,
        ip: null,
        type: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        controllerName: [
          { required: true, message: "控制器名不能为空", trigger: "blur" }
        ],
        ip: [
          { required: true, message: "IP地址不能为空", trigger: "blur" }
        ],
        port: [
          { required: true, message: "端口不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "设备类型不能为空", trigger: "change" }
        ],
        controllerIndex: [
          {required: true, message: "控制器编号不能为空", trigger:"blur"},
          {len: 4, message: '长度4个字符', trigger: 'blur' }
        ],
        capacity:[
          {required: true, message: "卡容量不能为空", trigger:"blur"},
          {pattern: /^[1-9]\d{0,3}$/, message: '请输入1-9999', trigger:"blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("controller_type").then(response => {
      this.controllerTypeOptions = response.data;
    });
    this.getDicts("device_controller_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询门禁控制器列表 */
    getList() {
      this.loading = true;
      listCondf(this.queryParams).then(response => {
        this.conList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //控制器类别类型字典翻译
    controllerTypeFormat(row,column){
      return this.selectDictLabel(this.controllerTypeOptions, row.type);
    },
    // 控制器状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 布防状态修改
    handleAlarmStatusChange(row) {
      let text = row.alarmStatus === "0" ? "撤防" : "布防";
      this.$confirm('确认要' + text + '' + row.controllerName + '吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeAlarmStatus(row.controllerId, row.alarmStatus);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.alarmStatus = row.alarmStatus === "0" ? "1" : "0";
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
        controllerId: null,
        controllerName: null,
        ip: null,
        port: null,
        type: null,
        userName: null,
        password: null,
        status: "0",
        alarmStatus:"0",
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
      this.ids = selection.map(item => item.controllerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加门禁控制器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const controllerId = row.controllerId || this.ids
      getCon(controllerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门禁控制器";
      });
    },
    /**下发人员信息*/
    handleIssueUser(row) {
      const controllerId = row.controllerId || this.ids
      this.$confirm('是否确认重新下载控制器: "' + row.controllerName + '" 的所有用户?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return issueAllUser(controllerId);
        }).then(() => {
          this.getList();
          this.msgSuccess("开始下发");
        })
    },
    /**下发周计划模板*/
    handleIssueWeekTemplate(row) {
      const controllerId = row.controllerId || this.ids
      issueWeekTemplate(controllerId,row.ip).then(response => {
        this.msgSuccess("开始下发");
      });
    },
    /**下发假日计划*/
    handleIssueHolidayPlan(row) {
      const controllerId = row.controllerId || this.ids
      issueHolidayPlan(controllerId,row.ip).then(response => {
        this.msgSuccess("开始下发");
      });
    },
    /**下发应用群组*/
    handleIssueAppGroup(row) {
      const controllerId = row.controllerId || this.ids
      issueAppGroup(controllerId).then(response => {
        this.msgSuccess("开始下发");
      });
    },
    /**下发时间设置*/
    handleIssueDateAndTime(row) {
      const controllerId = row.controllerId || this.ids
      issueDateAndTime(controllerId).then(response => {
        this.msgSuccess("开始下发");
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.controllerId != null) {
            updateCon(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCon(this.form).then(response => {
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
      const controllerIds = row.controllerId || this.ids;
      this.$confirm('是否确认删除门禁控制器编号为"' + controllerIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCon(controllerIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门禁控制器数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCon(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
