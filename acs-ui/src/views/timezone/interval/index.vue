<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="intervalList">
      <el-table-column label="时段" align="center" prop="id" />
      <el-table-column label="开始时间" align="center" prop="startTime" :formatter="startTimeFormat"/>
      <el-table-column label="结束时间" align="center" prop="endTime" :formatter="endTimeFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.id !== '00' && scope.row.id !== '31'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['timezone:interval:edit']"
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

    <!-- 添加或修改时段设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="开始时间" prop="startTime">
          <el-time-picker clearable size="small"
            v-model="form.startTime"
            value-format="HH:mm"
             :picker-options="{
              format: 'HH:mm'
            }"
          >
          </el-time-picker>
        </el-form-item>
         <el-form-item label="结束时间" prop="endTime">
          <el-time-picker clearable size="small"
            v-model="form.endTime"
            value-format="HH:mm"            
            :picker-options="{
              minTime: startTime,
              format: 'HH:mm'
            }"
          >
          </el-time-picker>
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
import { listInterval, getInterval, delInterval, addInterval, updateInterval, exportInterval } from "@/api/timezone/interval";

export default {
  name: "Interval",
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
      // 时段设置表格数据
      intervalList: [],
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
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询时段设置列表 */
    getList() {
      this.loading = true;
      listInterval(this.queryParams).then(response => {
        this.intervalList = response.rows;
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
        startTime: null,
        endTime: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInterval(id).then(response => {
        this.form = response.data;
        this.form.startTime = this.form.startTime.slice(0,2) + ":" + this.form.startTime.slice(2);
        this.form.endTime = this.form.endTime.slice(0,2) + ":" + this.form.endTime.slice(2);
        this.open = true;
        this.title = "修改时段设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.startTime = this.form.startTime.slice(0,2) + this.form.startTime.slice(3,5);
            this.form.endTime = this.form.endTime.slice(0,2) + this.form.endTime.slice(3,5);
            updateInterval(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } 
        }
      });
    },   
    // 开始时间翻译
    startTimeFormat(row, column) {
      return row.startTime.slice(0,2) + ":" + row.startTime.slice(2);
    },
    // 结束时间翻译
    endTimeFormat(row, column) {
      return row.endTime.slice(0,2) + ":" + row.endTime.slice(2);
    },
  }
};
</script>
