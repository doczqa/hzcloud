<template>
  <div class="dashboard-editor-container">
    <div class="msg" v-if="msg">
      <span aria-hidden="true" style="margin-left: 10px;margin-right: 10px">
        {{title}}:&nbsp{{msg}}
      </span>
    </div>
    <panel-group @handleSetLineChartData="handleSetLineChartData" />

<!--    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>-->
    <!--    <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <raddar-chart />
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <pie-chart />
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="8">
            <div class="chart-wrapper">
              <bar-chart />
            </div>
          </el-col>
        </el-row>-->
<!--    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="4">
        <el-tree :data="controllerList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="20">
        <el-col :span="1" v-for="door in doorList" :key="door.id">
          &lt;!&ndash;    开门状态      &ndash;&gt;
          <svg-icon icon-class="openDoor" style="width: 40px; height: 40px" v-if="door.status == 0"/>
          &lt;!&ndash;    关门状态      &ndash;&gt;
          <svg-icon icon-class="closeDoor" style="width: 40px; height: 40px" v-if="door.status == 1"/>
          &lt;!&ndash;    离线状态      &ndash;&gt;
          <svg-icon icon-class="offline" style="width: 40px; height: 40px" v-if="door.status == 2"/>
          <div>{{door.doorName}}</div>
        </el-col>
      </el-col>
    </el-row>-->



  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import Device from '@/components/device';

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    Device,
  },
  mounted() {
    let data = window.localStorage.getItem("notice");
    if (data) {
      this.title = JSON.parse(data).title;
      this.msg = JSON.parse(data).msg;
      this.lang();
    }
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      msg: undefined,
      intervalId: null,
      webSocketPath: process.env.VUE_APP_WEB_SOCKET_PATH,
      socket: undefined,
      message: undefined,
      title: undefined,
      controllerList: undefined,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      doorList: [],
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    lang(){
      if(this.intervalId !== null){
        return;
      }
      var _this = this
      this.intervalId = setInterval(function(){
        // 获取到头的第一个字符
        var start = _this.msg.substring(0,1)
        // 获取到后面的所有字符
        var end = _this.msg.substring(1)
        // 重新拼接得到的字符串 并赋值给this.msg
        _this.msg = end + start
      },500)
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
.msg{
  display: block;
  flex-direction: row;
  border: 1px solid #D9D9D9;
  border-radius: 5px;
  align-items: center;
  white-space:nowrap;
  overflow: hidden;
  color: red;
  font-size: small;
  text-align: center;
  background-color: snow;
}
</style>
