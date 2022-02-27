<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="卡号" prop="cardNumber">
        <el-input
          v-model="queryParams.cardNumber"
          placeholder="请输入卡号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="持卡人" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入持卡人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <treeselect v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门"
                    class="treeselect-main"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择卡状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="证件号" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入证件号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份类型" prop="identityId">
        <el-select
          v-model="queryParams.identityId"
          placeholder="身份类型"
          clearable
          size="small"
        >
          <el-option
            v-for="post in postOptions"
            :key="post.postId"
            :label="post.postName"
            :value="post.postId"
            :disabled="post.status == 1"
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
          v-hasPermi="['card:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleFileImport"
          v-hasPermi="['card:import']"
        >文件导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleUserImport"
          v-hasPermi="['card:import']"
        >用户导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['card:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cardList">
      <el-table-column label="序号" align="center" prop="cardId"/>
      <el-table-column label="卡号" align="center" prop="cardNumber"/>
      <el-table-column label="持卡人" align="center" prop="userName"/>
      <el-table-column label="身份类型" align="center" prop="identityId" :formatter="identityFormat"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="有效期开始时间" align="center" prop="expirationStartTime"/>
      <el-table-column label="有效期结束时间" align="center" prop="expirationEndTime">
        <template slot-scope="scope">
          <el-date-picker size="small"
                  v-model="scope.row.expirationEndTime"
                  type="date"
                  style="width: 200px;"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                  v-if="scope.row.seen"
                  @blur="loseFocus(scope.$index, scope.row)"
                  >
                </el-date-picker>

          <span style="margin-left: 10px" v-else @click="cellClick(scope.row)">{{ scope.row.expirationEndTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-lock"
            @click="handleFrozen(scope.row)"
            v-hasPermi="['card:oper']"
          >冻结
          </el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-unlock"
            @click="handleUnfreeze(scope.row)"
            v-hasPermi="['card:oper']"
          >解冻
          </el-button>
          <el-button
            v-if="scope.row.status !== '2'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleCancel(scope.row)"
            v-hasPermi="['card:oper']"
          >注销
          </el-button>
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

    <!-- 添加或修改卡片管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="持卡人" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入持卡人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="卡号" prop="cardNumber">
              <el-input v-model="form.cardNumber" placeholder="请输入卡号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="证件类型" prop="idType">
              <el-select v-model="form.idType" placeholder="请选择证件类型">
                <el-option
                  v-for="dict in idTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件号" prop="idNumber">
              <el-input v-model="form.idNumber" placeholder="请输入证件号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="身份类型" prop="identityId">
              <el-select
                v-model="form.identityId"
                placeholder="身份类型"
                clearable
                size="small"
              >
                <el-option
                  v-for="item in visitorOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" :flat="true"
                          :normalizer="my_normalizer" placeholder="请选择归属部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-form-item label="有效期" prop="expirationEndTime">
              <el-date-picker size="small"
                v-model="form.expirationEndTime"
                type="date"
                style="width: 200px;"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入电话"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址"/>
        </el-form-item> 
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <!--:action="upload.url + '?updateSupport=' + upload.updateSupport"-->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
          <el-upload
            ref="upload"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="upload.headers"
            :action="upload.url + '?updateSupport=' + upload.updateSupport"
            :disabled="upload.isUploading"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :auto-upload="false"
            drag
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或
              <em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
            </div>
            <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
          </el-upload>
          <div align="right">
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
            <el-button @click="upload.open = false">取 消</el-button>
          </div>
    </el-dialog>

    <el-dialog title="用户导入" :visible.sync="uopen" width="1000px" append-to-body>   
      <el-form :model="queryUserParams" ref="queryUserForm" :inline="true" v-show="showSearch" label-width="68px">

            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="queryUserParams.deptId" :options="deptOptions" :show-count="true" :flat="true"
                          :normalizer="my_normalizer" placeholder="请选择归属部门" class="treeselect-main"/>
            </el-form-item>  
            <el-form-item label="身份类型" prop="identityId">
              <el-select
                v-model="queryUserParams.identityId"
                placeholder="身份类型"
                clearable
                size="small"
              >
                <el-option
                  v-for="post in postOptions"
                  :key="post.postId"
                  :label="post.postName"
                  :value="post.postId"
                  :disabled="post.status == 1"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-table v-show="usertotal>0" v-loading="loading" :data="userList" @selection-change="handleUserSelectionChange">
            <el-table-column type="selection" width="50" align="center"/>
            <el-table-column label="用户姓名"  align="center" key="nickName" prop="nickName" :show-overflow-tooltip="true"/>
            <el-table-column label="证件号"  align="center" key="idNumber" prop="idNumber" />
            <el-table-column label="卡号"  align="center" key="cardNumber" prop="cardNumber">
              <template slot-scope="scope">
                <el-input v-model="scope.row.cardNumber"/>
              </template>
            </el-table-column>
            <el-table-column label="有效期" align="center" key="expirationEndTime" prop="expirationEndTime">
              <template slot-scope="scope">
                <el-date-picker size="small"
                  v-model="scope.row.expirationEndTime"
                  type="date"
                  style="width: 200px;"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="usertotal>0"
            :total="usertotal"
            :page.sync="queryUserParams.pageNum"
            :limit.sync="queryUserParams.pageSize"
            @pagination="getUserList"
          />
          <div align="right" id="btn">
            <el-button v-show="usertotal>0" type="primary" @click="importByUser">导 入</el-button>
            <el-button @click="uopen = false">取 消</el-button>
          </div>
    </el-dialog>
  </div>
</template>

<style>
  #btn {
    margin-top: 30px;
  }
</style>

<script>
  import {
    listCard,
    getCard,
    delCard,
    addCard,
    exportCard,
    cancelCard,
    frozenCard,
    unfreezeCard,
    importTemplate,
    importDateByUser,
    updateCardexpirationEndTime
  } from "@/api/card/card";
  import {listUser} from "@/api/system/user";
  import {listPost} from "@/api/system/post"
  import {getToken} from "@/utils/auth";
  import {treeselect} from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import XLSX from "xlsx";

  export default {
    name: "Card",
    components: {
      Treeselect
    },
    data() {
      return {
        // excel 文件
        excelFiles: [],
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
        // 用户总条数
        usertotal: 0,
        // 卡片管理表格数据
        cardList: [],
        // 用户管理表格数据
        userList: [],
        //选取的用户表格数据
        selectionUserList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 用户导入弹出层
        uopen: false,
        // 性别字典
        sexOptions: [],
        // 部门树选项
        deptOptions: [],
        // 卡状态字典
        statusOptions: [],
        //证件类型字典
        idTypeOptions: [],
        //身份列表
        postOptions: [],
        //临时身份
        visitorOptions:[{
          value:2,
          label:"临时卡"
        }],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          cardNumber: null,
          userId: null,
          userName: null,
          deptId: null,
          status: null,
          idNumber: null,
          identityId: null,
        },
        // 查询用户参数
        queryUserParams: {
          pageNum: 1,
          pageSize: 10,
          deptId: undefined
        },
        // 表单参数
        form: {},
        // 文件导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/card/importData"
        },
        // 表单校验
        rules: {
          userName: [
            {required: true, message: "持卡人不能为空", trigger: "blur"}
          ],
          idType: [
            {required: true, message: "证件类型不能为空", trigger: "change"}
          ],
          idNumber: [
            {required: true, message: "证件号不能为空", trigger: "blur"}
          ],
          identityId: [
            {required: true, message: "身份类型不能为空", trigger: "change"}
          ],
          deptId:[
            {required: true, message: "部门不能为空", trigger: "blur"}
          ],
        },
        lastCardNumber: '',
      };
    },
    created() {
      this.getPostList();
      this.getList();
      this.getTreeselect();
      this.getDicts("card_status_type").then(response => {
        this.statusOptions = response.data;
      });
      this.getDicts("sys_user_sex").then(response => {
        this.sexOptions = response.data;
      });
      this.getDicts("sys_id_type").then(response => {
        this.idTypeOptions = response.data;
      });
    },
    methods: {
      /** 查询卡片管理列表 */
      getList() {
        this.loading = true;
        listCard(this.queryParams).then(response => {
          this.cardList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询身份列表*/
      getPostList() {
        listPost().then(response => {
          this.postOptions = response.rows;
        });
      },
      // 性别字典翻译
      sexFormat(row, column) {
        return this.selectDictLabel(this.sexOptions, row.sex);
      },
      // 卡状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },
      //身份类型字典翻译
      identityFormat(row, column) {
        let post = this.postOptions.find(item => item.postId == row.identityId);
        return post.postName
      },
      my_normalizer(node) {
        return {
          isDisabled: node.id == 100 ? true : false
        };
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          cardId: null,
          cardNumber: null,
          userId: null,
          userName: null,
          deptId: null,
          status: 0,
          idType: null,
          idNumber: null,
          sex: null,
          phonenumber: null,
          address: null,
          identityId: null,
          creatTime: null,
          createBy: null,
          cancellingTime: null,
          cancellingBy: null,
          expirationStartTime: this.CurentTimeStr(),
          expirationEndTime: "2030-01-01"
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
      handleUserSelectionChange(selection) {
        this.selectionUserList = selection
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加卡片管理";
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.cardId != null) {
              updateCard(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addCard(this.form).then(response => {
                this.msgSuccess("添加成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },      
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有卡片管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportCard(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      },

      /** 注销按钮操作 */
      handleCancel(row) {
        const ids = row.cardId || this.ids;
        this.$confirm('是否确认注销卡片管理编号为"' + ids + '"的卡片?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return cancelCard(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("注销成功");
        })
      },
      /** 冻结按钮操作 */
      handleFrozen(row) {
        const ids = row.cardId || this.ids;
        this.$confirm('是否确认冻结卡片管理编号为"' + ids + '"的卡片?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return frozenCard(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("冻结成功");
        })
      },
      /** 解冻按钮操作 */
      handleUnfreeze(row) {
        const ids = row.cardId || this.ids;
        this.$confirm('是否确认解冻卡片管理编号为"' + ids + '"的卡片?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return unfreezeCard(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("解冻结成功");
        })
      },
      /** 文件导入按钮操作 */
      handleFileImport() {
        this.upload.title = "卡片导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then(response => {
          this.download(response.msg);
        });
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      handleUserImport() {
        this.uopen = true;
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      /** 用户搜索按钮操作 */
      handleUserQuery() {
        this.queryUserParams.pageNum = 1;
        this.getUserList();
      },
      /** 用户重置按钮操作 */
      resetUserQuery() {
        this.resetForm("queryUserForm");
        this.usertotal = 0;
      },
      /** 查询用户列表 */
      getUserList() {
        this.loading = true;
        listUser(this.queryUserParams).then(response => {
            this.userList = response.rows;
            this.usertotal = response.total;
            this.loading = false;
          }
        );
      },
      /** 从用户列表批量导入 */
      importByUser() {
        importDateByUser(this.selectionUserList).then(response => {
          this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
          this.uopen = false; 
          this.usertotal = 0;
          this.resetForm("queryUserForm");
          this.getList();
        })
      },
      CurentTimeStr()
      { 
        var now = new Date();
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = now.getFullYear() + "-";
        if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day ;
        return(clock); 
      },
      loseFocus(index, row) {   //当 input 失去焦点 时,input 切换为 span，并且让下方 表格消失（注意，与点击表格事件的执行顺序）
        this.$confirm('是否确认修改卡片的有效期?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return updateCardexpirationEndTime(row.cardId,row.expirationEndTime);
        }).then(() => {
          this.getList();
          this.msgSuccess("修改成功");
        }).catch(()=>{
          row.seen = false;
          this.getList();
        })
        
      },
      cellClick(row, column) {
        row.seen=true;
      },
    }
  };
</script>
