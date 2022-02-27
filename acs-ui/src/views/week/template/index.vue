<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入名称"
          
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态"  size="small">
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
          v-hasPermi="['week:template:add']"
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
          v-hasPermi="['week:template:edit']"
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
          v-hasPermi="['week:template:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['week:template:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" :cell-class-name="dateColumn" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="序号" align="center" prop="templateId" />-->
      <el-table-column label="名称" align="center" prop="templateName" fixed/>
      <el-table-column label="周一" align="center">
        <el-table-column label="时段一" align="center" width="130px" prop="monTime1"/>
        <el-table-column label="时段二" align="center" prop="monTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="monTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周二" align="center">
        <el-table-column label="时段一" align="center" prop="tueTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="tueTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="tueTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周三" align="center">
        <el-table-column label="时段一" align="center" prop="wenTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="wenTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="wenTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周四" align="center">
        <el-table-column label="时段一" align="center" prop="turTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="turTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="turTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周五" align="center">
        <el-table-column label="时段一" align="center" prop="friTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="friTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="friTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周六" align="center">
        <el-table-column label="时段一" align="center" prop="satTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="satTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="satTime3" width="130px"/>
      </el-table-column>
      <el-table-column label="周日" align="center">
        <el-table-column label="时段一" align="center" prop="sunTime1" width="130px"/>
        <el-table-column label="时段二" align="center" prop="sunTime2" width="130px"/>
        <el-table-column label="时段三" align="center" prop="sunTime3" width="130px"/>
        </el-table-column>
      </el-table-column>
      <el-table-column label="假日组" align="center" prop="holidayGroupId"/>
      <el-table-column label="状态" align="center" prop="status" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['week:template:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['week:template:remove']"
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

    <!-- 添加或修改周计划模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="名称" prop="templateName">
              <el-input v-model="form.templateName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="假日组" prop="holidayGroupId">
              <el-select
                v-model="form.holidayGroupId"
                placeholder="假日组"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="group in groupList"
                  :key="group.groupId"
                  :label="group.groupName"
                  :value="group.groupId"
                  :disabled="group.status == 1"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周一</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="monTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.monTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="monTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.monTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="monTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.monTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周二</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="tueTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.tueTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="tueTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.tueTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="tueTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.tueTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周三</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="wenTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.wenTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="wenTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.wenTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="wenTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.wenTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周四</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="turTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.turTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="turTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.turTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="turTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.turTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周五</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="friTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.friTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="friTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.friTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="friTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.friTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周六</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="satTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.satTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="satTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.satTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="satTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.satTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">周日</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="时段一" prop="sunTime1">
              <el-time-picker  size="mini"
                is-range
                v-model="form.sunTime1"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段二" prop="sunTime2">
              <el-time-picker  size="mini"
                is-range
                v-model="form.sunTime2"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="时段三" prop="sunTime3">
              <el-time-picker  size="mini"
                is-range
                v-model="form.sunTime3"
                value-format="HH:mm:ss"
                style="width: 200px;"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围">
              </el-time-picker>
            </el-form-item>
          </el-col>          
        </el-row>

        <el-row>
        <el-form-item label="备注" prop="remake">
          <el-input v-model="form.remake" type="textarea" placeholder="请输入内容" />
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

<style rel="stylesheet/scss" lang="scss">
</style>

<script>
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate, exportTemplate } from "@/api/week/template";
import { listGroup } from "@/api/holiday/group" 

export default {
  name: "Template",
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
      templateList: [],
      // 假日组
      groupList:[],
      // 状态数据字典
      statusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        templateName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getGroupList();
    this.getList();
  },
  methods: {
    /** 查询周计划模板列表 */
    getList() {
      this.loading = true;
      listTemplate(this.queryParams).then(response => {
          response.rows.forEach((row, index) =>{
            response.rows[index].monTime1 = (row.monStartTime1 == null?"00:00:00":row.monStartTime1) + " - " + (row.monEndTime1 == null?"00:00:00":row.monEndTime1);
            response.rows[index].monTime2 = (row.monStartTime2 == null?"00:00:00":row.monStartTime2) + " - " + (row.monEndTime2 == null?"00:00:00":row.monEndTime2);
            response.rows[index].monTime3 = (row.monStartTime3 == null?"00:00:00":row.monStartTime3) + " - " + (row.monEndTime3 == null?"00:00:00":row.monEndTime3);

            response.rows[index].tueTime1 = (row.tueStartTime1 == null?"00:00:00":row.tueStartTime1) + " - " + (row.tueEndTime1 == null?"00:00:00":row.tueEndTime1);
            response.rows[index].tueTime2 = (row.tueStartTime2 == null?"00:00:00":row.tueStartTime2) + " - " + (row.tueEndTime2 == null?"00:00:00":row.tueEndTime2);
            response.rows[index].tueTime3 = (row.tueStartTime3 == null?"00:00:00":row.tueStartTime3) + " - " + (row.tueEndTime3 == null?"00:00:00":row.tueEndTime3);

            response.rows[index].wenTime1 = (row.wenStartTime1 == null?"00:00:00":row.wenStartTime1) + " - " + (row.wenEndTime1 == null?"00:00:00":row.wenEndTime1);
            response.rows[index].wenTime2 = (row.wenStartTime2 == null?"00:00:00":row.wenStartTime2) + " - " + (row.wenEndTime2 == null?"00:00:00":row.wenEndTime2);
            response.rows[index].wenTime3 = (row.wenStartTime3 == null?"00:00:00":row.wenStartTime3) + " - " + (row.werEndTime3 == null?"00:00:00":row.wenEndTime3);

            response.rows[index].turTime1 = (row.turStartTime1 == null?"00:00:00":row.turStartTime1) + " - " + (row.turEndTime1 == null?"00:00:00":row.turEndTime1);
            response.rows[index].turTime2 = (row.turStartTime2 == null?"00:00:00":row.turStartTime2) + " - " + (row.turEndTime2 == null?"00:00:00":row.turEndTime2);
            response.rows[index].turTime3 = (row.turStartTime3 == null?"00:00:00":row.turStartTime3) + " - " + (row.turEndTime3 == null?"00:00:00":row.turEndTime3);

            response.rows[index].friTime1 = (row.friStartTime1 == null?"00:00:00":row.friStartTime1) + " - " + (row.friEndTime1 == null?"00:00:00":row.friEndTime1);
            response.rows[index].friTime2 = (row.friStartTime2 == null?"00:00:00":row.friStartTime2) + " - " + (row.friEndTime2 == null?"00:00:00":row.friEndTime2);
            response.rows[index].friTime3 = (row.friStartTime3 == null?"00:00:00":row.friStartTime3) + " - " + (row.friEndTime3 == null?"00:00:00":row.friEndTime3);

            response.rows[index].satTime1 = (row.satStartTime1 == null?"00:00:00":row.satStartTime1) + " - " + (row.satEndTime1 == null?"00:00:00":row.satEndTime1);
            response.rows[index].satTime2 = (row.satStartTime2 == null?"00:00:00":row.satStartTime2) + " - " + (row.satEndTime2 == null?"00:00:00":row.satEndTime2);
            response.rows[index].satTime3 = (row.satStartTime3 == null?"00:00:00":row.satStartTime3) + " - " + (row.satEndTime3 == null?"00:00:00":row.satEndTime3);

            response.rows[index].sunTime1 = (row.sunStartTime1 == null?"00:00:00":row.sunStartTime1) + " - " + (row.sunEndTime1 == null?"00:00:00":row.sunEndTime1);
            response.rows[index].sunTime2 = (row.sunStartTime2 == null?"00:00:00":row.sunStartTime2) + " - " + (row.sunEndTime2 == null?"00:00:00":row.sunEndTime2);
            response.rows[index].sunTime3 = (row.sunStartTime3 == null?"00:00:00":row.sunStartTime3) + " - " + (row.sunEndTime3 == null?"00:00:00":row.sunEndTime3);
          });
        this.templateList = response.rows;
        console.log(this.templateList);
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取假日组
    getGroupList(){
      listGroup().then(response =>{
        this.groupList = response.rows;
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
        templateId: null,
        templateName: null,
        holidayGroupId: null,
        monStartTime1: null,
        monEndTime1: null,
        monStartTime2: null,
        monEndTime2: null,
        monStartTime3: null,
        monEndTime3: null,
        tueStartTime1: null,
        tueEndTime1: null,
        tueStartTime2: null,
        tueEndTime2: null,
        tueStartTime3: null,
        tueEndTime3: null,
        wenStartTime1: null,
        wenEndTime1: null,
        wenStartTime2: null,
        wenEndTime2: null,
        wenStartTime3: null,
        wenEndTime3: null,
        turStartTime1: null,
        turEndTime1: null,
        turStartTime2: null,
        turEndTime2: null,
        turStartTime3: null,
        turEndTime3: null,
        friStartTime1: null,
        friEndTime1: null,
        friStartTime2: null,
        friEndTime2: null,
        friStartTime3: null,
        friEndTime3: null,
        satStartTime1: null,
        satEndTime1: null,
        satStartTime2: null,
        satEndTime2: null,
        satStartTime3: null,
        satEndTime3: null,
        sunStartTime1: null,
        sunEndTime1: null,
        sunStartTime2: null,
        sunEndTime2: null,
        sunStartTime3: null,
        sunEndTime3: null,
        monTime1:["00:00:00","23:59:59"],
        monTime2:["00:00:00","00:00:00"],
        monTime3:["00:00:00","00:00:00"],
        tueTime1:["00:00:00","23:59:59"],
        tueTime2:["00:00:00","00:00:00"],
        tueTime3:["00:00:00","00:00:00"],
        wenTime1:["00:00:00","23:59:59"],
        wenTime2:["00:00:00","00:00:00"],
        wenTime3:["00:00:00","00:00:00"],
        turTime1:["00:00:00","23:59:59"],
        turTime2:["00:00:00","00:00:00"],
        turTime3:["00:00:00","00:00:00"],
        friTime1:["00:00:00","23:59:59"],
        friTime2:["00:00:00","00:00:00"],
        friTime3:["00:00:00","00:00:00"],
        satTime1:["00:00:00","23:59:59"],
        satTime2:["00:00:00","00:00:00"],
        satTime3:["00:00:00","00:00:00"],
        sunTime1:["00:00:00","23:59:59"],
        sunTime2:["00:00:00","00:00:00"],
        sunTime3:["00:00:00","00:00:00"],
        status: "0",
        delFlag: null,
        remake: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    // 区域状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + '' + row.templateName + '模板吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeTmplateStatus(row.templateId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
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
      this.ids = selection.map(item => item.templateId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加周计划模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const templateId = row.templateId || this.ids
      getTemplate(templateId).then(response => {
        this.form = response.data;
        this.form.monTime1 = [this.form.monStartTime1,this.form.monEndTime1];
        this.form.monTime2 = [this.form.monStartTime2,this.form.monEndTime2];
        this.form.monTime3 = [this.form.monStartTime3,this.form.monEndTime3];
        this.form.tueTime1 = [this.form.tueStartTime1,this.form.tueEndTime1];
        this.form.tueTime2 = [this.form.tueStartTime2,this.form.tueEndTime2];
        this.form.tueTime3 = [this.form.tueStartTime3,this.form.tueEndTime3];
        this.form.wenTime1 = [this.form.wenStartTime1,this.form.wenEndTime1];
        this.form.wenTime2 = [this.form.wenStartTime2,this.form.wenEndTime2];
        this.form.wenTime3 = [this.form.wenStartTime3,this.form.wenEndTime3];
        this.form.turTime1 = [this.form.turStartTime1,this.form.turEndTime1];
        this.form.turTime2 = [this.form.turStartTime2,this.form.turEndTime2];
        this.form.turTime3 = [this.form.turStartTime3,this.form.turEndTime3];
        this.form.friTime1 = [this.form.friStartTime1,this.form.friEndTime1];
        this.form.friTime2 = [this.form.friStartTime2,this.form.friEndTime2];
        this.form.friTime3 = [this.form.friStartTime3,this.form.friEndTime3];
        this.form.satTime1 = [this.form.satStartTime1,this.form.satEndTime1];
        this.form.satTime2 = [this.form.satStartTime2,this.form.satEndTime2];
        this.form.satTime3 = [this.form.satStartTime3,this.form.satEndTime3];
        this.form.sunTime1 = [this.form.sunStartTime1,this.form.sunEndTime1];
        this.form.sunTime2 = [this.form.sunStartTime2,this.form.sunEndTime2];
        this.form.sunTime3 = [this.form.sunStartTime3,this.form.sunEndTime3];
        this.open = true;
        this.title = "修改周计划模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.monStartTime1 = this.form.monTime1 == null ? "00:00:00": this.form.monTime1[0];
          this.form.monEndTime1 =  this.form.monTime1 == null ? "00:00:00": this.form.monTime1[1];
          this.form.monStartTime2 = this.form.monTime2 == null ? "00:00:00": this.form.monTime2[0];
          this.form.monEndTime2 =  this.form.monTime2 == null ? "00:00:00": this.form.monTime2[1];
          this.form.monStartTime3 = this.form.monTime3 == null ? "00:00:00": this.form.monTime3[0];
          this.form.monEndTime3 =  this.form.monTime3 == null ? "00:00:00": this.form.monTime3[1];

          this.form.tueStartTime1 = this.form.tueTime1 == null ? "00:00:00": this.form.tueTime1[0];
          this.form.tueEndTime1 =  this.form.tueTime1 == null ? "00:00:00": this.form.tueTime1[1];
          this.form.tueStartTime2 = this.form.tueTime2 == null ? "00:00:00": this.form.tueTime2[0];
          this.form.tueEndTime2 =  this.form.tueTime2 == null ? "00:00:00": this.form.tueTime2[1];
          this.form.tueStartTime3 = this.form.tueTime3 == null ? "00:00:00": this.form.tueTime3[0];
          this.form.tueEndTime3 =  this.form.tueTime3 == null ? "00:00:00": this.form.tueTime3[1];

          this.form.wenStartTime1 = this.form.wenTime1 == null ? "00:00:00": this.form.wenTime1[0];
          this.form.wenEndTime1 =  this.form.wenTime1 == null ? "00:00:00": this.form.wenTime1[1];
          this.form.wenStartTime2 = this.form.wenTime2 == null ? "00:00:00": this.form.wenTime2[0];
          this.form.wenEndTime2 =  this.form.wenTime2 == null ? "00:00:00": this.form.wenTime2[1];
          this.form.wenStartTime3 = this.form.wenTime3 == null ? "00:00:00": this.form.wenTime3[0];
          this.form.wenEndTime3 =  this.form.wenTime3 == null ? "00:00:00": this.form.wenTime3[1];

          this.form.turStartTime1 = this.form.turTime1 == null ? "00:00:00": this.form.turTime1[0];
          this.form.turEndTime1 =  this.form.turTime1 == null ? "00:00:00": this.form.turTime1[1];
          this.form.turStartTime2 = this.form.turTime2 == null ? "00:00:00": this.form.turTime2[0];
          this.form.turEndTime2 =  this.form.turTime2 == null ? "00:00:00": this.form.turTime2[1];
          this.form.turStartTime3 = this.form.turTime3 == null ? "00:00:00": this.form.turTime3[0];
          this.form.turEndTime3 =  this.form.turTime3 == null ? "00:00:00": this.form.turTime3[1];

          this.form.friStartTime1 = this.form.friTime1 == null ? "00:00:00": this.form.friTime1[0];
          this.form.friEndTime1 =  this.form.friTime1 == null ? "00:00:00": this.form.friTime1[1];
          this.form.friStartTime2 = this.form.friTime2 == null ? "00:00:00": this.form.friTime2[0];
          this.form.friEndTime2 =  this.form.friTime2 == null ? "00:00:00": this.form.friTime2[1];
          this.form.friStartTime3 = this.form.friTime3 == null ? "00:00:00": this.form.friTime3[0];
          this.form.friEndTime3 =  this.form.friTime3 == null ? "00:00:00": this.form.friTime3[1];

          this.form.satStartTime1 = this.form.satTime1 == null ? "00:00:00": this.form.satTime1[0];
          this.form.satEndTime1 =  this.form.satTime1 == null ? "00:00:00": this.form.satTime1[1];
          this.form.satStartTime2 = this.form.satTime2 == null ? "00:00:00": this.form.satTime2[0];
          this.form.satEndTime2 =  this.form.satTime2 == null ? "00:00:00": this.form.satTime2[1];
          this.form.satStartTime3 = this.form.satTime3 == null ? "00:00:00": this.form.satTime3[0];
          this.form.satEndTime3 =  this.form.satTime3 == null ? "00:00:00": this.form.satTime3[1];

          this.form.sunStartTime1 = this.form.sunTime1 == null ? "00:00:00": this.form.sunTime1[0];
          this.form.sunEndTime1 =  this.form.sunTime1 == null ? "00:00:00": this.form.sunTime1[1];
          this.form.sunStartTime2 = this.form.sunTime2 == null ? "00:00:00": this.form.sunTime2[0];
          this.form.sunEndTime2 =  this.form.sunTime2 == null ? "00:00:00": this.form.sunTime2[1];
          this.form.sunStartTime3 = this.form.sunTime3 == null ? "00:00:00": this.form.sunTime3[0];
          this.form.sunEndTime3 =  this.form.sunTime3 == null ? "00:00:00": this.form.sunTime3[1];

          if (this.form.templateId != null) {
            updateTemplate(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplate(this.form).then(response => {
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
      const templateIds = row.templateId || this.ids;
      this.$confirm('是否确认删除周计划模板编号为"' + templateIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTemplate(templateIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有周计划模板数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTemplate(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /**时分秒转Date*/
    handleDate(str) {
      let sdate = str.split(':');
      let date =new Date(0,0,0,sdate[0],sdate[1],sdate[2]);
      return date;
    },
    dateColumn() {
      return 'dateClass'
    }
  }
};
</script>
