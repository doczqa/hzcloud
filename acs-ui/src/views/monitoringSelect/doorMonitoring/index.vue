<template>
<!--  <div class="app-container">
    <el-col :xs="24" :sm="24" :lg="4" style="height: 600px">
      <div>
        工作区
      </div>
      <el-tree :data="spaceList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" style="border: solid;height: 300px">
      <el-col :span="1" v-for="door in doorList" :key="door.id">
        <svg-icon icon-class="openDoor" style="width: 40px; height: 40px" v-if="door.status == 0"/>
        <svg-icon icon-class="closeDoor" style="width: 40px; height: 40px" v-if="door.status == 1"/>
        <svg-icon icon-class="offline" style="width: 40px; height: 40px" v-if="door.status == 2"/>
        <div>{{door.doorName}}</div>
      </el-col>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" style="border: solid; height: 300px">
      <el-col :span="1" v-for="door in doorList" :key="door.id">
        <svg-icon icon-class="openDoor" style="width: 40px; height: 40px" v-if="door.status == 0"/>
        <svg-icon icon-class="closeDoor" style="width: 40px; height: 40px" v-if="door.status == 1"/>
        <svg-icon icon-class="offline" style="width: 40px; height: 40px" v-if="door.status == 2"/>
        <div>{{door.doorName}}</div>
      </el-col>
    </el-col>
  </div>-->
  <el-container style="height: 850px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu>
<!--        <el-menu-item v-for="zone in zoneList" :index="zone.id">{{zone.zoneName}}</el-menu-item>-->
        <el-tree :data="zoneList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </el-menu>
    </el-aside>

    <el-container>
      <el-main>
        <!--   控制器列表     -->
        <div style="height:200px;border: 1px solid #eee;">
          <div style="height:200px">
            <el-col :span="2" v-for="controller in controllerList" :key="controller.controllerId">
              <!--    在线          -->
              <div>
                <svg-icon icon-class="controller_online" style="width: 40px; height: 40px;cursor:pointer" v-if="controller.status == 1" @click="handlerControllerClick(controller.controllerId)"/>
                <!--    离线          -->
                <svg-icon icon-class="controller_offline" style="width: 40px; height: 40px;cursor:pointer" v-if="controller.status == 0" @click="handlerControllerClick(controller.controllerId)"/>
                <div style="font-size: x-small;width: 60px">{{controller.controllerName}}</div>
              </div>
            </el-col>
          </div>
        </div>
        <!--   门列表     -->
        <div style="height:200px;border: 1px solid #eee; margin-top: 5px">
          <el-col :span="2" v-for="door in doorList" :key="door.id">
            <!--     开门状态       -->
            <svg-icon icon-class="openDoor" style="width: 40px; height: 40px;cursor:pointer" v-if="door.status == 1 || door.status == 4" @click="handlerDoorClick(door.controllerId, door.pin)"/>
            <!--     关门状态       -->
            <svg-icon icon-class="closeDoor" style="width: 40px; height: 40px;cursor:pointer" v-if="door.status == 0 || door.status == 2" @click="handlerDoorClick(door.controllerId, door.pin)"/>
            <!--     离线状态       -->
            <svg-icon icon-class="offline" style="width: 40px; height: 40px;cursor:pointer" v-if="door.status == 3" @click="handlerDoorClick(door.controllerId, door.pin)"/>
            <div style="font-size: x-small;width: 60px">{{door.doorName}}</div>
          </el-col>
        </div>
        <!--    开关门记录    -->
        <div style="margin-top: 5px">
          <el-table :data="recordList" height="350px" border="border">
            <el-table-column label="报警时间" align="center" prop="alarmTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.alarmTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="报警类型" align="center" prop="alarmType" :formatter="alarmTypeFormat"/>
            <el-table-column label="控制器" align="center" prop="controllerName" />
            <el-table-column label="门名称" align="center" prop="doorName" />
            <el-table-column label="详情" align="center" prop="alarmDetail" />
            <el-table-column label="用户" align="center" prop="userName" />
            <el-table-column label="卡号" align="center" prop="cardNumber" />
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="selectRecordList"
          />
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>
<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
<script>
import {listZoneNoPage} from '@/api/device/zone';
import {listDoorNoPage, listDoordfnp, listDoorByControllerIds} from '@/api/device/door';
import {listConByZoneId, listCondfnp} from '@/api/device/_con';
import { listByConAndDoor } from "@/api/alarm/info";
export default {
  name: "DoorMonitoring",
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };

    return {
      zoneList: [],
      spaceList: [],
      controllerList: [],
      doorList: [],
      recordList: [],
      defaultProps: {
        children: 'children',
        label: 'zoneName'
      },
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        controllerIds:[],
        doorPin:0,
      },
      zoneId:0,
      controllerIds:[],
      doorPin:0,
      // 报警类型字典
      alarmTypeOptions: [],
      timer: '',
    }
  },
  mounted() {
    this.getDicts("alarm_type").then(response => {
      this.alarmTypeOptions = response.data;
    });
    this.selectZone();
    this.timer = setInterval(this.refresh, 10000);
  },
  methods: {
    // 查询区域列表
    selectZone() {
      this.controllerIds = [];
      listZoneNoPage().then(response => {
        this.zoneList = response.data;
      })
      listCondfnp().then(response => {
        this.controllerList = response.data;
        this.controllerList.forEach(controller =>{
          this.controllerIds.push(controller.controllerId);
        });
        this.selectRecordList();
      })
      listDoordfnp().then(response =>{
        this.doorList = response.data;
      })
    },
    // 点击树节点
    handleNodeClick(node) {
      this.controllerList = [];
      this.doorList = [];
      this.recordList = [];
      this.total = 0;
      this.zoneId = node.zoneId;
      this.controllerIds = [];
      this.doorPin = 0;
      this.queryParams.pageNum = 1;
      listConByZoneId(node.zoneId).then(response => {
        this.controllerList = response.data;
        this.controllerList.forEach(controller =>{
          this.controllerIds.push(controller.controllerId);
        });
        if(this.controllerIds.length != 0){
          listDoorByControllerIds(this.controllerIds).then(response =>{
            this.doorList = response.data;
          });
          this.selectRecordList();
        }
      });
    },
    // 点击控制器
    handlerControllerClick(controllerId) {
      this.controllerIds = [controllerId];
      this.doorPin = 0;
      listDoorNoPage({controllerId: controllerId}).then(response => {
        this.doorList = response.data;
      });
      this.selectRecordList();
    },
    // 点击门
    handlerDoorClick(controllerId,doorPin) {
      this.controllerIds = [controllerId];
      this.doorPin = doorPin;
      this.selectRecordList();
    },
    // 查询流水
    selectRecordList() {
      this.queryParams.controllerIds = this.controllerIds;
      this.queryParams.doorPin = this.doorPin;
      listByConAndDoor(this.queryParams).then(response =>{
        this.recordList = response.rows;
        this.total = response.total;
      })
    },
    // 报警类型字典翻译
    alarmTypeFormat(row, column) {
      return this.selectDictLabel(this.alarmTypeOptions, row.alarmType);
    },
    refresh(){
      if(this.zoneId == 0){
        this.selectZone();
      }else{
        listConByZoneId(this.zoneId).then(response => {
          this.controllerList = response.data;
          if(this.controllerIds.length != 0){
            listDoorByControllerIds(this.controllerIds).then(response =>{
              this.doorList = response.data;
            });
            this.selectRecordList();
          }
        });
      }
    }
  },
  beforeDestroy() {
      clearInterval(this.timer);
  }
}
</script>

<style scoped>

</style>
