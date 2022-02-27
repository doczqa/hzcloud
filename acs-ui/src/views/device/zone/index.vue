<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区域名称" prop="zoneName">
        <el-input
          v-model="queryParams.zoneName"
          placeholder="请输入区域名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
         <treeselect  v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" class="treeselect-main"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择区域状态" clearable size="small">
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
          v-hasPermi="['device:zone:add']"
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
          v-hasPermi="['device:zone:edit']"
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
          v-hasPermi="['device:zone:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:zone:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zoneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="zoneId" />
      <el-table-column label="区域名称" align="center" prop="zoneName" />
      <el-table-column label="所属部门" align="center" prop="deptName" />
      <el-table-column label="状态" align="center" key="status">
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
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['device:zone:list']"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:zone:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:zone:remove']"
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

    <!-- 添加或修改区域对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="区域名称" prop="zoneName">
          <el-input v-model="form.zoneName" placeholder="请输入区域名称" />
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <treeselect  v-model="form.deptId" :options="deptOptions" :show-count="true" :flat="true" :normalizer="my_normalizer" placeholder="请选择归属部门" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="控制器">
          <el-transfer
            filterable
            filter-placeholder="请输入控制器名称"
            v-model="form.controllerIds"
            :titles="titles"
            :data="controllerList">
          </el-transfer>
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

    <!-- 区域日志详细 -->
    <el-dialog title="区域详细" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="区域编号：">{{ form.zoneId }}</el-form-item>
            <el-form-item label="所属部门：">{{ form.deptName }}</el-form-item>
            <el-form-item label="创建者：">{{ form.createBy }}</el-form-item>
            <el-form-item label="更新者：">{{ form.updateBy }}</el-form-item>
          </el-col>
          <el-col :span="12">            
            <el-form-item label="区域名称：">{{ form.zoneName }}</el-form-item>
            <el-form-item label="状态：">{{ form.status === "1" ? "停用":"启用" }}</el-form-item>
            <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
            <el-form-item label="更新时间：">{{ form.updateTime }}</el-form-item>
          </el-col>
        </el-row> 
        <el-row>
          <el-form-item label="下辖控制器："> 
            <span v-html="showControllerList()"/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注：">{{ form.remark }}</el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listZone, getZone, delZone, addZone, updateZone, exportZone, changeZoneStatus } from "@/api/device/zone";
import { listCon } from "@/api/device/_con";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Zone",
  components: { Treeselect },
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
      // 区域表格数据
      zoneList: [],
      // 状态数据字典
      statusOptions: [],
      // 部门树选项
      deptOptions: undefined,
      // 控制器列表
      controllerList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示详情层
      openView: false,
      //穿梭框标题
      titles:["控制器","已选择"],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        zoneName: null,
        deptId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        zoneName: [
          { required: true, message: "区域名称不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "所属部门不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getTreeselect();
    this.getControllerList();
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询区域列表 */
    getList() {
      this.loading = true;
      listZone(this.queryParams).then(response => {
        this.zoneList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    my_normalizer(node) {
      return {
        isDisabled: node.id==100 ? true : false
      };
    },
    //获取控制器列表
    getControllerList(){
      listCon().then(response => {
        response.rows.forEach(item =>{
           this.controllerList.push(
             {
               key:item.controllerId,
               label:item.controllerName
             }
           );
        });
      });
    },
    // 区域状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + '' + row.zoneName + '区域吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeZoneStatus(row.zoneId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
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
        zoneId: null,
        zoneName: null,
        deptId: null,
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        controllerIds:[]
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
      this.ids = selection.map(item => item.zoneId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加区域";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const zoneId = row.zoneId || this.ids
      getZone(zoneId).then(response => {
        this.form = response.data;
        if (this.form.controllerIds == null){
          this.form.controllerIds = [];
        }
        this.open = true;
        this.title = "修改区域";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.zoneId != null) {
            updateZone(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addZone(this.form).then(response => {
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
      const zoneIds = row.zoneId || this.ids;
      this.$confirm('是否确认删除区域编号为"' + zoneIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delZone(zoneIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有区域数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportZone(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /** 任务详细信息 */
    handleView(row) {
      getZone(row.zoneId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    showControllerList(){
      let str = "";
      if(!this.form.controllerIds){
        return;
      }
      this.form.controllerIds.forEach(id =>{
          str += this.controllerList.find(item => item.key == id ).label;
          str += " <br> ";
        });
      return str
    }
  }
};
</script>
