<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="settingList">
      <el-table-column label="应用群组" align="center" prop="id" />
      <el-table-column label="控制门区" align="center" prop="doorDetail" />
      <el-table-column label="直接外出" align="center" key="doorOut" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.doorOut"
            active-value="1"
            inactive-value="0"
            @change="handleDoorOutChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="安全密码检查时区" align="center">
        <el-table-column label="门区一" align="center" prop="secPinTz1" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.secPinTz1, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.secPinTz1, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.secPinTz1, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.secPinTz1 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区二" align="center" prop="secPinTz2"> 
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.secPinTz2, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.secPinTz2, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.secPinTz2, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.secPinTz2 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区三" align="center" prop="secPinTz3" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.secPinTz3, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.secPinTz3, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.secPinTz3, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.secPinTz3 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区四" align="center" prop="secPinTz4" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.secPinTz4, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.secPinTz4, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.secPinTz4, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.secPinTz4 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="个人密码检查时区" align="center">
        <el-table-column label="门区一" align="center" prop="perPinTz1" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.perPinTz1, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.perPinTz1, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.perPinTz1, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.perPinTz1 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区二" align="center" prop="perPinTz2" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.perPinTz2, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.perPinTz2, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.perPinTz2, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.perPinTz2 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区三" align="center" prop="perPinTz3" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.perPinTz3, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.perPinTz3, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.perPinTz3, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.perPinTz3 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="门区四" align="center" prop="perPinTz4" >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>时段一: {{ getTimeInterval(scope.row.perPinTz4, 1) }}</p>
              <p>时段二: {{ getTimeInterval(scope.row.perPinTz4, 2) }}</p>
              <p>时段三: {{ getTimeInterval(scope.row.perPinTz4, 3) }}</p>
              <div slot="reference" >
                {{ scope.row.perPinTz4 }}
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['appgroup:setting:edit']"
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

    <!-- 添加或修改基本设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用群组" prop="id">
          <el-input v-model="form.id" placeholder="请输入应用群组" :disabled="true"/>
        </el-form-item>
        <el-form-item label="控制门区" prop="doorDetail">
          <el-input v-model="form.doorDetail" placeholder="请输入控制门区" :disabled="true" />
        </el-form-item>
        <el-form-item label="直接外出" prop="doorOut">
          <el-radio-group v-model="form.doorOut">
            <el-radio
              v-for="dict in choiceOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>           
        </el-form-item>
        <el-divider content-position="left">安全密码检查时区</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="门区一" prop="secPinTz1">
              <el-select
                v-model="form.secPinTz1"
                placeholder="门区一"
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
            <el-form-item label="门区二" prop="secPinTz2">
              <el-select
                v-model="form.secPinTz2"
                placeholder="门区二"
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
        </el-row>
        <el-row>
          <el-col :span="12">  
            <el-form-item label="门区三" prop="secPinTz3">
              <el-select
                v-model="form.secPinTz3"
                placeholder="门区三"
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
            <el-form-item label="门区四" prop="secPinTz4">
              <el-select
                v-model="form.secPinTz4"
                placeholder="门区四"
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
        </el-row>
        <el-divider content-position="left">个人密码检查时区</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="门区一" prop="perPinTz1">
              <el-select
                v-model="form.perPinTz1"
                placeholder="门区一"
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
            <el-form-item label="门区二" prop="perPinTz2">
              <el-select
                v-model="form.perPinTz2"
                placeholder="门区二"
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
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="门区三" prop="perPinTz3">
              <el-select
                v-model="form.perPinTz3"
                placeholder="门区三"
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
            <el-form-item label="门区四" prop="perPinTz4">
              <el-select
                v-model="form.perPinTz4"
                placeholder="门区四"
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
import { listSetting, getSetting, delSetting, addSetting, updateSetting, exportSetting} from "@/api/appgroup/setting";
import { listTimezone } from "@/api/timezone/timezone";
import { listInterval } from "@/api/timezone/interval";

export default {
  name: "Setting",
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
      // 基本设置表格数据
      settingList: [],
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
    /** 查询基本设置列表 */
    getList() {
      this.loading = true;
      listSetting(this.queryParams).then(response => {
        this.settingList = response.rows;
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
        door: null,
        doorOut: null,
        secPinTz1: null,
        secPinTz2: null,
        secPinTz3: null,
        secPinTz4: null,
        perPinTz1: null,
        perPinTz2: null,
        perPinTz3: null,
        perPinTz4: null
      };
      this.resetForm("form");
    },
     /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSetting(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基本设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSetting(this.form).then(response => {
              this.msgSuccess("修改成功");
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
     // 直接外出状态修改
    handleDoorOutChange(row) {
      let text = row.doorOut === "0" ? "不可" : "可以";
      this.$confirm('确认非有效时间段内' + text + '直接外出吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return updateSetting(row);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.doorOut = row.doorOut === "0" ? "1" : "0";
        });
    },
  }
};
</script>
