<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="控制器ID" prop="controllerId">
        <el-input
          v-model="queryParams.controllerId"
          placeholder="请输入控制器ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="命令" prop="command">
        <el-input
          v-model="queryParams.command"
          placeholder="请输入命令"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下发时间">
        <el-date-picker
          v-model="daterangeIssueTime"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="处理时间">
        <el-date-picker
          v-model="daterangeProcessTime"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['device:queue:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRePlay"
          v-hasPermi="['device:queue:replay']"
        >重新执行</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="queueList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"  />
      <el-table-column label="控制器" align="center" prop="controllerId" width="180" :formatter="controllerFormat"/>
      <el-table-column label="门" align="center" prop="doorPin" width="180" :formatter="doorFormat"/>
      <el-table-column label="命令" align="center" prop="command" width="120" :formatter="cmdFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下发时间" align="center" prop="issueTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issueTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="processTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.processTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结果信息" align="center" prop="resultMsg" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['device:queue:list']"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:queue:remove']"
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

    <!-- 区域日志详细 -->
    <el-dialog title="区域详细" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-form-item label="命令详情："> {{ form.data }}</el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listQueue, listQueuedf, getQueue, delQueue, replay } from "@/api/device/queue";
import { listCon} from "@/api/device/_con";
import { listDoor} from "@/api/device/door";

export default {
  name: "Queue",
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
      // 命令列表表格数据
      queueList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示详情层
      openView: false,
      // 下发时间时间范围
      daterangeIssueTime: [],
      // 处理时间时间范围
      daterangeProcessTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        controllerId: null,
        doorPin: null,
        command: null,
        issueTime: null,
        processTime: null,
      },
      // 表单参数
      form: {},
      // 控制器列表
      controllerList:[],
      // 门列表
      doorList:[],
      //命令options
      cmdOptions:[{"cmd":1,"label":"下发人员信息"},{"cmd":2,"label":"使能人员信息"},{"cmd":3,"label":"删除人员信息"},{"cmd":4,"label":"注销人员信息"},{"cmd":5,"label":"下发周计划模板"},{"cmd":8,"lable":"下发假日计划"},{"cmd":11,"label":"下发假日组"},{"cmd":14,"label":"下载应用群组"},{"cmd":15,"label":"下载时间设置"},{"cmd":16,"label":"设置卡片卡号"}]
    };
  },
  created() {
    this.getDoorList();
    this.getControllerList();
    this.getList();
  },
  methods: {
    /** 查询命令列表列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeIssueTime && '' != this.daterangeIssueTime) {
        this.queryParams.params["beginIssueTime"] = this.daterangeIssueTime[0];
        this.queryParams.params["endIssueTime"] = this.daterangeIssueTime[1];
      }
      if (null != this.daterangeProcessTime && '' != this.daterangeProcessTime) {
        this.queryParams.params["beginProcessTime"] = this.daterangeProcessTime[0];
        this.queryParams.params["endProcessTime"] = this.daterangeProcessTime[1];
      }
      listQueuedf(this.queryParams).then(response => {
        this.queueList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeIssueTime = [];
      this.daterangeProcessTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const taskIds = row.taskId || this.ids;
      this.$confirm('是否确认删除命令列表编号为"' + taskIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQueue(taskIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 查询控制器列表*/
    getControllerList(){
      listCon().then(response =>{
        this.controllerList = response.rows;
      });
    },
    /** 查询门列表*/
    getDoorList(){
      listDoor().then(response =>{
        this.doorList = response.rows;
      });
    },
    //控制器翻译
    controllerFormat(row, column) {
      let controller = this.controllerList.find(item => item.controllerId == row.controllerId);
      return controller.controllerName
    },
    //门翻译
    doorFormat(row, column) {
      let door = this.doorList.find(item => (item.controllerId == row.controllerId && item.pin == row.doorPin));
      if(null != door){
        return door.doorName
      }
      return ""
    },
    //命令翻译
    cmdFormat(row, column) {
      let cmd = this.cmdOptions.find(item => item.cmd == row.command );
      return cmd.label
    },
    //重新执行失败任务
    handleRePlay(){
       replay().then(response => {
        this.msgSuccess("开始下发");
      });
    },
     /** 任务详细信息 */
    handleView(row) {
      getQueue(row.queueId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
  }
};
</script>
