<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="假日计划" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入假日计划名称"
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
          v-hasPermi="['holiday:plan:add']"
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
          v-hasPermi="['holiday:plan:edit']"
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
          v-hasPermi="['holiday:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['holiday:plan:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleSlice"
          v-hasPermi="['holiday:plan:add']"
        >设置假日时段</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="planId" />
      <el-table-column label="假日计划" align="center" prop="planName" />
      <el-table-column label="起始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时段一" align="center" prop="Time1" width="180"/>
      <el-table-column label="时段二" align="center" prop="Time2" width="180"/>
      <el-table-column label="时段三" align="center" prop="Time3" width="180"/>
      <el-table-column label="状态" align="center" prop="status" >
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['holiday:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['holiday:plan:remove']"
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

    <!-- 添加或修改假日计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="假日计划" prop="planName">
          <el-input v-model="form.planName" placeholder="请输入假日计划名称" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="起始日期" prop="startDate">
              <el-date-picker clearable size="small"
                v-model="form.startDate"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 200px;"
                placeholder="选择起始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker clearable size="small"
                v-model="form.endDate"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 200px;"
                placeholder="选择结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>       
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
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

        <!-- 添加或修改假日时间段对话框 -->
    <el-dialog title="设置时间段" :visible.sync="sliceopen" width="600px" append-to-body>
      <el-form ref="sliceform" :model="sliceform" :rules="slicerules" label-width="80px">
        <el-form-item label="时段一" prop="Time1">
          <el-time-picker clearable size="small"
            is-range
            v-model="sliceform.Time1"
            value-format="HH:mm:ss"
            style="width: 200px;"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="时段二" prop="Time2">
          <el-time-picker clearable size="small"
            is-range
            v-model="sliceform.Time2"
            value-format="HH:mm:ss"
            style="width: 200px;"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="时段三" prop="Time3">
          <el-time-picker clearable size="small"
            is-range
            v-model="sliceform.Time3"
            value-format="HH:mm:ss"
            style="width: 200px;"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitSliceForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPlan, getPlan, delPlan, addPlan, updatePlan, exportPlan, updateSlice, getSlice } from "@/api/holiday/plan";

export default {
  name: "Plan",
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
      // 假日计划表格数据
      planList: [],
      // 状态数据字典
      statusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示时间段弹出层
      sliceopen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planName: [
          { required: true, message: "区域名称不能为空", trigger: "blur" }
        ],
        startDate: [
          { required: true, message: "开始日期不能为空", trigger: "blur" }
        ],
        endDate: [
          { required: true, message: "结束日期不能为空", trigger: "blur" }
        ]
      },
      // 表单参数
      sliceform: {},
      // 表单校验
      slicerules: {
        Time1: [
          { required: true, message: "时间段一不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getList();
  },
  methods: {
    /** 查询假日计划列表 */
    getList() {
      this.loading = true;
      listPlan(this.queryParams).then(response => {
        response.rows.forEach((row,index)=>{
          response.rows[index].Time1 = (row.startTime1 == null?"00:00:00":row.startTime1) + " - " + (row.endTime1 == null?"00:00:00":row.endTime1);
          response.rows[index].Time2 = (row.startTime2 == null?"00:00:00":row.startTime2) + " - " + (row.endTime2 == null?"00:00:00":row.endTime2);
          response.rows[index].Time3 = (row.startTime3 == null?"00:00:00":row.startTime3) + " - " + (row.endTime3 == null?"00:00:00":row.endTime3);
        })
        this.planList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.sliceopen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        planId: null,
        planName: null,
        startDate: null,
        endDate: null,        
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
      this.sliceform = {
        startTime1: null,
        endTime1: null,
        startTime2: null,
        endTime2: null,
        startTime3: null,
        endTime3: null,
        Time1: ["00:00:00","23:59:59"],
        Time2: ["00:00:00","00:00:00"],
        Time3: ["00:00:00","00:00:00"]
      }
      this.resetForm("sliceform");
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
      this.ids = selection.map(item => item.planId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加假日计划";
    },
    /** 设置时间段按钮操作 */
    handleSlice() {
      this.reset();
      getSlice().then(response =>{
        if(response.data != null){
          this.sliceform =response.data;
          this.sliceform.Time1 = [this.sliceform.startTime1,this.sliceform.endTime1];
          this.sliceform.Time2 = [this.sliceform.startTime2,this.sliceform.endTime2];
          this.sliceform.Time3 = [this.sliceform.startTime3,this.sliceform.endTime3];
        }
        this.sliceopen = true;
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids
      getPlan(planId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改假日计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.StartTime1 = this.form.Time1 == null ? "00:00:00": this.form.Time1[0];
          this.form.EndTime1 =  this.form.Time1 == null ? "00:00:00": this.form.Time1[1];
          this.form.StartTime2 = this.form.Time2 == null ? "00:00:00": this.form.Time2[0];
          this.form.EndTime2 =  this.form.Time2 == null ? "00:00:00": this.form.Time2[1];
          this.form.StartTime3 = this.form.Time3 == null ? "00:00:00": this.form.Time3[0];
          this.form.EndTime3 =  this.form.Time3 == null ? "00:00:00": this.form.Time3[1];
          if (this.form.planId != null) {
            updatePlan(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPlan(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitSliceForm() {
      this.$refs["sliceform"].validate(valid => {
        if (valid) {
          this.sliceform.startTime1 = this.sliceform.Time1 == null ? "00:00:00": this.sliceform.Time1[0];
          this.sliceform.endTime1 =  this.sliceform.Time1 == null ? "00:00:00": this.sliceform.Time1[1];
          this.sliceform.startTime2 = this.sliceform.Time2 == null ? "00:00:00": this.sliceform.Time2[0];
          this.sliceform.endTime2 =  this.sliceform.Time2 == null ? "00:00:00": this.sliceform.Time2[1];
          this.sliceform.startTime3 = this.sliceform.Time3 == null ? "00:00:00": this.sliceform.Time3[0];
          this.sliceform.endTime3 =  this.sliceform.Time3 == null ? "00:00:00": this.sliceform.Time3[1];
          updateSlice(this.sliceform).then(response => {
              this.msgSuccess("修改成功");
              this.sliceopen = false;
              this.getList();
          });
          
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const planIds = row.planId || this.ids;
      this.$confirm('是否确认删除假日计划编号为"' + planIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPlan(planIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有假日计划数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPlan(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
