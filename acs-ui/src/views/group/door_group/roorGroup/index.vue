<template>
  <div class="app-container">
    <el-dialog title="设置门禁组"
               :visible.sync="visible" width="500px"
               :before-close="handleClose"
               @close="closeDialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="门禁组" prop="groupName">
              <el-input v-model="form.groupName" placeholder="请输入门禁组名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" :flat="true" :normalizer="my_normalizer" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
<!---        
        <el-row>
          <el-col :span="24">
            <el-form-item label="周计划" prop="weekTemplateId">
              <el-select
              v-model="form.weekTemplateId"
              placeholder="周计划"
              clearable
              size="small"
              style="width: 240px"
              >
                <el-option
                  v-for="template in weekTemplateOptions"
                  :key="template.templateId"
                  :label="template.templateName"
                  :value="template.templateId"
                  :disabled="template.status == 1"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
---->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import {treeselect} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {addUser, updateUser} from "@/api/system/user";
import {
  listDoor_group,
  getDoor_group,
  delDoor_group,
  addDoor_group,
  updateDoor_group,
  exportDoor_group,
  remove_door,
  distribution,
} from "@/api/group/door_group";
import {listTemplate} from "@/api/week/template"

export default {
  name: "RoomGroup",
  components: {
    Treeselect
  },
  props: {
    groupId: undefined,
  },
  mounted() {
  },
  data() {
    return {
      visible: false,
      //身份列表
      weekTemplateOptions: [],
      form: {},
      showSearch: true,
      // 部门树选项
      deptOptions: undefined,
      // 表单校验
      rules: {
        groupName: [
          {required: true, message: "节点名称不能为空", trigger: "blur"}
        ],
        deptId: [
          {required: true, message: "部门不能为空", trigger: "blur"}
        ],
      },
    }
  },
  methods: {
    show() {
      this.visible = true;
      if (this.groupId) {
        this.getDoorGroup();
      }
      this.getTreeselect();
      //this.getWeekTemplateList() 
    },
    handleClose(done) {
      this.$confirm('确认关闭?')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    closeDialog() {

    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupId != undefined) {
            updateDoor_group(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.visible = false;
              this.callParent()
            });
          } else {
            addDoor_group(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.visible = false;
              this.callParent()
            });
          }
        }
      });
    },
    cancel() {
      this.visible = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupId: null,
        groupName: null,
        deptId: null,
        weekTemplateId: null,
      };
      this.resetForm("form");
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    my_normalizer(node) {
      return {
        isDisabled: node.id == 100 ? true : false
      };
    },
    callParent() {
      this.$emit('operationSuccess', {});
    },
    getDoorGroup() {
      getDoor_group(this.groupId).then(response => {
        this.form = response.data;
      })
    },
    /** 查询身份列表*/
    getWeekTemplateList() {
      listTemplate().then(response => {
        this.weekTemplateOptions = response.rows;
      });
    },
  },
}
</script>

<style scoped>

</style>
