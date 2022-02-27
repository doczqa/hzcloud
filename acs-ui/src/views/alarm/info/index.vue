<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-form-item label="报警时间">
          <el-date-picker
            v-model="daterangeAlarmTime"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="报警类型" prop="alarmType">
          <el-select v-model="queryParams.alarmType" placeholder="请选择报警类型" clearable size="small">
             <el-option
              v-for="dict in alarmTypeOptions"
              :key="dict.dictSort"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="控制器" prop="controllerName">
          <el-input
            v-model="queryParams.controllerName"
            placeholder="请输入控制器"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="门名称" prop="doorName">
          <el-input
            v-model="queryParams.doorName"
            placeholder="请输入门名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="报警信息" prop="alarmDetail">
          <el-input
            v-model="queryParams.alarmDetail"
            placeholder="请输入报警信息"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="卡号" prop="cardNumber">
          <el-input
            v-model="queryParams.cardNumber"
            placeholder="请输入卡号"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['alarm:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList">
      <el-table-column label="报警时间" align="center" prop="alarmTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.alarmTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报警类型" align="center" prop="alarmType" :formatter="alarmTypeFormat"/>
      <el-table-column label="控制器" align="center" prop="controllerName" />
      <el-table-column label="门名称" align="center" prop="doorName" />
      <el-table-column label="详情" align="center" prop="alarmDetail" />
      <el-table-column label="用户" align="center" prop="userName" />
      <el-table-column label="卡号" align="center" prop="cardNumber" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import { listInfo, exportInfo } from "@/api/alarm/info";

export default {
  name: "Info",
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
      // 报警信息表格数据
      infoList: [],
      // 报警时间时间范围
      daterangeAlarmTime: [],
      // 报警类型字典
      alarmTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        alarmTime: null,
        alarmType: null,
        controllerName: null,
        doorName: null,
        alarmDetail: null,
        userName: null,
        cardNumber: null
      },
      timer: '',
    };
  },
  created() {
    this.getDicts("alarm_type").then(response => {
      this.alarmTypeOptions = response.data;
    });
    this.getList();
    this.timer = setInterval(this.getList, 10000);
  },
  methods: {
    /** 查询报警信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeAlarmTime && '' != this.daterangeAlarmTime) {
        this.queryParams.params["beginAlarmTime"] = this.daterangeAlarmTime[0];
        this.queryParams.params["endAlarmTime"] = this.daterangeAlarmTime[1];
      }
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
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
      this.daterangeAlarmTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报警信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    // 报警类型字典翻译
    alarmTypeFormat(row, column) {
      return this.selectDictLabel(this.alarmTypeOptions, row.alarmType);
    },
  },
  beforeDestroy() {
      clearInterval(this.timer);
  }
};
</script>
