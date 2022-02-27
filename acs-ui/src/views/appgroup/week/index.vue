<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="weekList" >
      <el-table-column label="应用群组" align="center" prop="id" />
      <el-table-column label="周日" align="center">
        <el-table-column label="通行时区" align="center" prop="sunTz"> 
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.sunTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.sunTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.sunTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.sunTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="sunTzOut" :formatter="sunTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周一" align="center">
        <el-table-column label="通行时区" align="center" prop="monTz">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.monTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.monTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.monTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.monTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="monTzOut" :formatter="monTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周二" align="center">
        <el-table-column label="通行时区" align="center" prop="tueTz"> 
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.tueTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.tueTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.tueTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.tueTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="tueTzOut" :formatter="tueTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周三" align="center">
        <el-table-column label="通行时区" align="center" prop="wedTz">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.wedTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.wedTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.wedTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.wedTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="wedTzOut" :formatter="wedTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周四" align="center">
        <el-table-column label="通行时区" align="center" prop="thuTz">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.thuTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.thuTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.thuTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.thuTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="thuTzOut" :formatter="thuTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周五" align="center">
        <el-table-column label="通行时区" align="center" prop="friTz">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.friTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.friTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.friTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.friTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="friTzOut" :formatter="friTzOutFormat"/>
      </el-table-column>
      <el-table-column label="周六" align="center">
        <el-table-column label="通行时区" align="center" prop="satTz">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.satTz, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.satTz, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.satTz, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.satTz }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="直接外出" align="center" prop="satTzOut" :formatter="satTzOutFormat"/>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['appgroup:week:edit']"
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

    <!-- 添加或修改周计划模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用群组" prop="id">
          <el-input v-model="form.id" placeholder="请输入应用群组" :disabled="true"/>
        </el-form-item>
        <el-divider content-position="left">周日</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="sunTz">
              <el-select
                v-model="form.sunTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="sunTzOut">
              <el-radio-group v-model="form.sunTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周一</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="monTz">
              <el-select
                v-model="form.monTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="monTzOut">
              <el-radio-group v-model="form.monTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周二</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="tueTz">
              <el-select
                v-model="form.tueTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="tueTzOut">
              <el-radio-group v-model="form.tueTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周三</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="wedTz">
              <el-select
                v-model="form.wedTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="wedTzOut">
              <el-radio-group v-model="form.wedTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周四</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="thuTz">
              <el-select
                v-model="form.thuTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="thuTzOut">
              <el-radio-group v-model="form.thuTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周五</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="friTz">
              <el-select
                v-model="form.friTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="friTzOut">
              <el-radio-group v-model="form.friTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周六</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="satTz">
              <el-select
                v-model="form.satTz"
                placeholder="通行时区"
                size="small"
              >
                <el-option
                  v-for="item in timezoneOptions"
                  :key="item.id"
                  :label="getTimeInterval(item.id, 1) +'/' + getTimeInterval(item.id, 2) + '/' + getTimeInterval(item.id, 3)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="直接外出" prop="satTzOut">
              <el-radio-group v-model="form.satTzOut">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
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
import { listWeek, getWeek, delWeek, addWeek, updateWeek, exportWeek } from "@/api/appgroup/week";
import { listTimezone } from "@/api/timezone/timezone";
import { listInterval } from "@/api/timezone/interval";

export default {
  name: "Week",
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
      // 周计划模板表格数据
      weekList: [],
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
      //时区options
      timezoneOptions:[],
      //时段列表
      intervalOptions: [],
      //单选选项
      choiceOptions:[]
    };
  },
  created() {
    this.getDicts("sys_oper_choice").then(response => {
      this.choiceOptions = response.data;
    });
    this.getList();
    this.getTimeZoneOptions();
    this.getIntervalList();
  },
  methods: {
    /** 查询周计划模板列表 */
    getList() {
      this.loading = true;
      listWeek(this.queryParams).then(response => {
        this.weekList = response.rows;
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
        sunTz: null,
        sunTzOut: null,
        monTz: null,
        monTzOut: null,
        tueTz: null,
        tueTzOut: null,
        wedTz: null,
        wedTzOut: null,
        thuTz: null,
        thuTzOut: null,
        friTz: null,
        friTzOut: null,
        satTz: null,
        satTzOut: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWeek(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改周计划模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWeek(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWeek(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 获取时区设置*/
    getTimeZoneOptions(){
      listTimezone().then(response => {
        this.timezoneOptions = response.rows;
      });
    },
    /** 查询时段列表*/
    getIntervalList() {
      listInterval().then(response => {
        this.intervalOptions = response.rows;
      });
    },
    /** 获取具体时段值*/
    getTimeInterval(timezoneid,intervalid){
      let timezone = this.timezoneOptions.find(item => item.id == timezoneid)
      console.log(timezone);
      if(intervalid === 1){
        let interval = this.intervalOptions.find(item => item.id == timezone.interval1);
        console.log(interval);
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
      if(intervalid === 2){
        let interval = this.intervalOptions.find(item => item.id == timezone.interval2);
        console.log(interval);
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
      if(intervalid === 3){
        let interval = this.intervalOptions.find(item => item.id == timezone.interval3);
        console.log(interval);
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
    },
    //周日直接外出字典翻译
    sunTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.sunTzOut).dictLabel
    },
    //周一直接外出字典翻译
    monTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.monTzOut).dictLabel
    },
    //周二直接外出字典翻译
    tueTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.tueTzOut).dictLabel
    },
    //周三直接外出字典翻译
    wedTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.wedTzOut).dictLabel
    },
    //周四直接外出字典翻译
    thuTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.thuTzOut).dictLabel
    },
    //周五直接外出字典翻译
    friTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.friTzOut).dictLabel
    },
    //周六直接外出字典翻译
    satTzOutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.satTzOut).dictLabel
    },
  }
};
</script>
