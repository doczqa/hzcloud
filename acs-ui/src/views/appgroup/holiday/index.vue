<template>
  <div class="app-container">
  
  <div id="customizedCalendar">
   <el-button-group style="float: left;">
      <el-button
        v-if="!editmode"
        type="primary"
        size="mini"
        @click="enterEditMode()"
        v-hasPermi="['appgroup:holiday:edit']"
        >修改
      </el-button>
      <el-button
        v-if="editmode"
        type="primary"
        size="mini"
        @click="editHoliday()">保存
      </el-button>
      <el-button
        v-if="editmode"
        type="primary"
        size="mini"
        @click="leaveHoliday()">取消
      </el-button>
    </el-button-group>
    <el-button-group style="float: right;">
      <el-button
        type="plain"
        size="mini"
        :disabled="value.getMonth() === 0"
        @click="prewmonth()">上月
      </el-button>
      <el-button
        type="plain"
        size="mini"
        :disabled="value.getMonth() === 11"
        @click="postmonth()">下月
      </el-button>
    </el-button-group>
    <el-calendar 
      :first-day-of-week = 7
      v-model = "value">
      <template
        slot="dateCell"
        slot-scope="{date, data}">
        <p :class="dateInList(data.day) ? 'is-selected' : ''" @click="handleClick(data.day)">
        {{ data.day.split('-').slice(1).join('-') }} <br> {{getTypeOfDate(data.day) !== "0" ? "假日型式:" + getTypeOfDate(data.day): ""}}
      </p>
      </template>
    </el-calendar>
  </div>
 
    <!-- 修改节假日管理 -->
    <el-dialog title="假日设定" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="holidaySettingForm" :rules="rules" label-width="80px">
        <el-form-item label="假日时间" prop="holiday">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="holidaySettingForm.holiday"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="假日型式" prop="type">
          <el-select v-model="holidaySettingForm.type" placeholder="请选择类型" clearable :style="{ width: '200px' }">
            <el-option
              v-for="dict in holidayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="setHoliday">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<style>
    .is-selected {
      color: #1989FA;
    }
</style>

<script>
import { listHoliday, getHoliday, delHoliday, addHoliday, updateHoliday, exportHoliday } from "@/api/appgroup/holiday";

export default {
  name: "Holiday",
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
      // 假日设置表格数据
      holidayList: [],
      // 假日选型选项
      holidayOptions:[{"dictValue":"0","dictLabel":"0"},{"dictValue":"1","dictLabel":"1"},{"dictValue":"2","dictLabel":"2"},{"dictValue":"3","dictLabel":"3"},{"dictValue":"4","dictLabel":"4"},{"dictValue":"5","dictLabel":"5"},{"dictValue":"6","dictLabel":"6"},{"dictValue":"7","dictLabel":"7"},{"dictValue":"8","dictLabel":"8"}],
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
      // 假日表单
      holidaySettingForm:{},
      // 表单校验
      rules: {
      },
      value: new Date(),
      editmode:false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询假日设置列表 */
    getList() {
      this.loading = true;
      listHoliday(this.queryParams).then(response => {
        this.holidayList = response.data;
        this.loading = false;
        console.log(this.holidayList);
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
        month: null,
        holidays: null
      };
      this.resetForm("form");
      this.holidaySettingForm = {
        holiday: null,
        type:null
      };
      this.resetForm("holidaySettingForm");
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.month != null) {
            updateHoliday(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHoliday(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    //上月按钮
    prewmonth() {
       this.value = new Date(this.value.setMonth(this.value.getMonth() - 1));
    },
    //下月按钮
    postmonth() {
       this.value = new Date(this.value.setMonth(this.value.getMonth() + 1));
    },
    handleClick(day) {
      if(this.editmode){
        this.holidaySettingForm.holiday = day;
        this.holidaySettingForm.type = this.getTypeOfDate(day);
        this.open = true;
      }
    },
    setHoliday(){
      console.log(this.holidayList);
      var tmp = {};
      tmp.holiday = this.holidaySettingForm.holiday;
      tmp.type = this.holidaySettingForm.type
      this.holidayList.push(tmp);
      this.open = false;
    },
    enterEditMode(){
      this.editmode = true;
    },
    editHoliday(){
      updateHoliday(this.holidayList).then(response => {
              this.msgSuccess("设置成功");
              this.open = false;
              this.getList();
              this.editmode = false;
            });
    },
    leaveHoliday(){
      this.getList();
       this.editmode = false;
    },
    dateInList(date){
      for(let i = 0; i !== this.holidayList.length; i++){
        if(date === this.holidayList[i].holiday){
          return true;
        }
      }
      return false;
    },
    getTypeOfDate(date){
      for(let i = 0; i !== this.holidayList.length; i++){
        if(date === this.holidayList[i].holiday){
          return this.holidayList[i].type;
        }
      }
      return "0";
    }
  }
};
</script>

<style lang="scss" scoped>
#customizedCalendar {
 
  ::v-deep .el-calendar__header {
    display: none;
  }

}

</style>
