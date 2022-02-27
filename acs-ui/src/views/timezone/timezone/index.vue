<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="timezoneList" >
      <el-table-column label="时区" align="center" prop="id" />
      <el-table-column label="时段一" align="center" prop="interval1" :formatter="interval1Format"/>
      <el-table-column label="时段二" align="center" prop="interval2" :formatter="interval2Format"/>
      <el-table-column label="时段三" align="center" prop="interval3" :formatter="interval3Format"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['timezone:timezone:edit']"
          >修改</el-button>
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

    <!-- 添加或修改时区设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="时段一" prop="interval1">
          <el-select
            v-model="form.interval1"
            placeholder="时段一"
            size="small"
          >
            <el-option
              v-for="item in intervalOptions"
              :key="item.id"
              :label="item.startTime.slice(0,2) + ':' + item.startTime.slice(2) + ' - ' + item.endTime.slice(0,2) + ':' + item.endTime.slice(2)"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="时段二" prop="interval2">
          <el-select
            v-model="form.interval2"
            placeholder="时段二"
            size="small"
          >
            <el-option
              v-for="item in intervalOptions"
              :key="item.id"
              :label="item.startTime.slice(0,2) + ':' + item.startTime.slice(2) + ' - ' + item.endTime.slice(0,2) + ':' + item.endTime.slice(2)"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="时段三" prop="interval3">
          <el-select
            v-model="form.interval3"
            placeholder="时段三"
            size="small"
          >
            <el-option
              v-for="item in intervalOptions"
              :key="item.id"
              :label="item.startTime.slice(0,2) + ':' + item.startTime.slice(2) + ' - ' + item.endTime.slice(0,2) + ':' + item.endTime.slice(2)"
              :value="item.id"
            />
          </el-select>
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
import { listTimezone, getTimezone, delTimezone, addTimezone, updateTimezone, exportTimezone } from "@/api/timezone/timezone";
import { listInterval } from "@/api/timezone/interval";

export default {
  name: "Timezone",
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
      // 时区设置表格数据
      timezoneList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //时段列表
      intervalOptions: [],
    };
  },
  created() {
    this.getList();
    this.getIntervalList();
  },
  methods: {
    /** 查询时区设置列表 */
    getList() {
      this.loading = true;
      listTimezone(this.queryParams).then(response => {
        this.timezoneList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        id: null,
        interval1: null,
        interval2: null,
        interval3: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTimezone(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改时区设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTimezone(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } 
        }
      });
    },
    //时段一字典翻译
    interval1Format(row, column) {
      let interval = this.intervalOptions.find(item => item.id == row.interval1);
      return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
    },
    //时段二字典翻译
    interval2Format(row, column) {
      let interval = this.intervalOptions.find(item => item.id == row.interval2);
      return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
    },
    //时段三字典翻译
    interval3Format(row, column) {
      let interval = this.intervalOptions.find(item => item.id == row.interval3);
      return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
    },
    /** 查询时段列表*/
    getIntervalList() {
      listInterval().then(response => {
        this.intervalOptions = response.rows;
      });
    },
  }
};
</script>
