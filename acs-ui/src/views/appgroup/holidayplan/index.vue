<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="holidayList">
      <el-table-column label="应用群组" align="center" prop="id" fixed/>
      <el-table-column label="假日前一天" align="center">
        <el-table-column label="型式一" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz1">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz1, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz1, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz1, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz1 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz1Out" :formatter="preHolidayTz1OutFormat"/>
        </el-table-column>
        <el-table-column label="型式二" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz2">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz2, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz2, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz2, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz2 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz2Out" :formatter="preHolidayTz2OutFormat"/>
        </el-table-column>
        <el-table-column label="型式三" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz3">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz3, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz3, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz3, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz3 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz3Out" :formatter="preHolidayTz3OutFormat"/>
        </el-table-column>
        <el-table-column label="型式四" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz4">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz4, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz4, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz4, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz4 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz4Out" :formatter="preHolidayTz4OutFormat"/>
        </el-table-column>
        <el-table-column label="型式五" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz5">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz5, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz5, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz5, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz5 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz5Out" :formatter="preHolidayTz5OutFormat"/>
        </el-table-column>
        <el-table-column label="型式六" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz6">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz6, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz6, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz6, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz6 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz6Out" :formatter="preHolidayTz6OutFormat"/>
        </el-table-column>
        <el-table-column label="型式七" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz7">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz7, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz7, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz7, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz7 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz7Out" :formatter="preHolidayTz7OutFormat"/>
        </el-table-column>
        <el-table-column label="型式八" align="center">
          <el-table-column label="通行时区" align="center" prop="preHolidayTz8">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.preHolidayTz8, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.preHolidayTz8, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.preHolidayTz8, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.preHolidayTz8 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="preHolidayTz8Out" :formatter="preHolidayTz8OutFormat"/>
        </el-table-column>
      </el-table-column>
      <el-table-column label="假日当天" align="center">
        <el-table-column label="型式一" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz1">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz1, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz1, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz1, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz1 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz1Out" :formatter="holidayTz1OutFormat"/>
        </el-table-column>
        <el-table-column label="型式二" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz2">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz2, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz2, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz2, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz2 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz2Out" :formatter="holidayTz2OutFormat"/>
        </el-table-column>
        <el-table-column label="型式三" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz3">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz3, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz3, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz3, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz3 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz3Out" :formatter="holidayTz3OutFormat"/>
        </el-table-column>
        <el-table-column label="型式四" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz4">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz4, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz4, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz4, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz4 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz4Out" :formatter="holidayTz4OutFormat"/>
        </el-table-column>
        <el-table-column label="型式五" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz5">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz5, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz5, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz5, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz5 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz5Out" :formatter="holidayTz5OutFormat"/>
        </el-table-column>
        <el-table-column label="型式六" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz6">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz6, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz6, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz6, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz6 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz6Out" :formatter="holidayTz6OutFormat"/>
        </el-table-column>
        <el-table-column label="型式七" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz7">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz7, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz7, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz7, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz7 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz7Out" :formatter="holidayTz7OutFormat"/>
        </el-table-column>
        <el-table-column label="型式八" align="center">
          <el-table-column label="通行时区" align="center" prop="holidayTz8">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.holidayTz8, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.holidayTz8, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.holidayTz8, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.holidayTz8 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="holidayTz8Out" :formatter="holidayTz8OutFormat"/>
        </el-table-column>
      </el-table-column>
      <el-table-column label="假日后一天" align="center">
        <el-table-column label="型式一" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz1">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz1, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz1, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz1, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz1 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz1Out" :formatter="postHolidayTz1OutFormat"/>
        </el-table-column>
        <el-table-column label="型式二" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz2">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz2, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz2, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz2, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz2 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz2Out" :formatter="postHolidayTz2OutFormat"/>
        </el-table-column>
        <el-table-column label="型式三" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz3">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz3, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz3, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz3, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz3 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz3Out" :formatter="postHolidayTz3OutFormat"/>
        </el-table-column>
        <el-table-column label="型式四" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz4">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz4, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz4, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz4, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz4 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz4Out" :formatter="postHolidayTz4OutFormat"/>
        </el-table-column>
        <el-table-column label="型式五" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz5">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz5, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz5, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz5, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz5 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz5Out" :formatter="postHolidayTz5OutFormat"/>
        </el-table-column>
        <el-table-column label="型式六" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz6">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz6, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz6, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz6, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz6 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz6Out" :formatter="postHolidayTz6OutFormat"/>
        </el-table-column>
        <el-table-column label="型式七" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz7">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz7, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz7, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz7, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz7 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="接外出" align="center" prop="postHolidayTz7Out" :formatter="postHolidayTz7OutFormat"/>
        </el-table-column>
        <el-table-column label="型式八" align="center">
          <el-table-column label="通行时区" align="center" prop="postHolidayTz8">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>时段一: {{ getTimeInterval(scope.row.postHolidayTz8, 1) }}</p>
                <p>时段二: {{ getTimeInterval(scope.row.postHolidayTz8, 2) }}</p>
                <p>时段三: {{ getTimeInterval(scope.row.postHolidayTz8, 3) }}</p>
                <div slot="reference" >
                  {{ scope.row.postHolidayTz8 }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="直接外出" align="center" prop="postHolidayTz8Out" :formatter="postHolidayTz8OutFormat"/>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['appgroup:holidayplan:edit']"
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

    <!-- 添加或修改假日计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用群组" prop="id">
          <el-input v-model="form.id" placeholder="请输入应用群组" :disabled="true"/>
        </el-form-item>
        <el-divider content-position="center">假日前一天</el-divider>
        <el-divider content-position="left">型式一</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz1">
              <el-select
                v-model="form.preHolidayTz1"
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
            <el-form-item label="直接外出" prop="preHolidayTz1Out">
              <el-radio-group v-model="form.preHolidayTz1Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式二</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz2">
              <el-select
                v-model="form.preHolidayTz2"
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
            <el-form-item label="直接外出" prop="preHolidayTz2Out">
              <el-radio-group v-model="form.preHolidayTz2Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式三</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz3">
              <el-select
                v-model="form.preHolidayTz3"
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
            <el-form-item label="直接外出" prop="preHolidayTz3Out">
              <el-radio-group v-model="form.preHolidayTz3Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式四</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz4">
              <el-select
                v-model="form.preHolidayTz4"
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
            <el-form-item label="直接外出" prop="preHolidayTz4Out">
              <el-radio-group v-model="form.preHolidayTz4Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式五</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz5">
              <el-select
                v-model="form.preHolidayTz5"
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
            <el-form-item label="直接外出" prop="preHolidayTz5Out">
              <el-radio-group v-model="form.preHolidayTz5Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式六</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz6">
              <el-select
                v-model="form.preHolidayTz6"
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
            <el-form-item label="直接外出" prop="preHolidayTz6Out">
              <el-radio-group v-model="form.preHolidayTz6Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式七</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz7">
              <el-select
                v-model="form.preHolidayTz7"
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
            <el-form-item label="直接外出" prop="preHolidayTz7Out">
              <el-radio-group v-model="form.preHolidayTz7Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式八</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="preHolidayTz8">
              <el-select
                v-model="form.preHolidayTz8"
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
            <el-form-item label="直接外出" prop="preHolidayTz8Out">
              <el-radio-group v-model="form.preHolidayTz8Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">假日当天</el-divider>
        <el-divider content-position="left">型式一</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz1">
              <el-select
                v-model="form.holidayTz1"
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
            <el-form-item label="直接外出" prop="holidayTz1Out">
              <el-radio-group v-model="form.holidayTz1Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式二</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz2">
              <el-select
                v-model="form.holidayTz2"
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
            <el-form-item label="直接外出" prop="holidayTz2Out">
              <el-radio-group v-model="form.holidayTz2Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式三</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz3">
              <el-select
                v-model="form.holidayTz3"
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
            <el-form-item label="直接外出" prop="holidayTz3Out">
              <el-radio-group v-model="form.holidayTz3Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式四</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz4">
              <el-select
                v-model="form.holidayTz4"
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
            <el-form-item label="直接外出" prop="holidayTz4Out">
              <el-radio-group v-model="form.holidayTz4Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式五</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz5">
              <el-select
                v-model="form.holidayTz5"
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
            <el-form-item label="直接外出" prop="holidayTz5Out">
              <el-radio-group v-model="form.holidayTz5Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式六</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz6">
              <el-select
                v-model="form.holidayTz6"
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
            <el-form-item label="直接外出" prop="holidayTz6Out">
              <el-radio-group v-model="form.holidayTz6Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式七</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz7">
              <el-select
                v-model="form.holidayTz7"
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
            <el-form-item label="直接外出" prop="holidayTz7Out">
              <el-radio-group v-model="form.holidayTz7Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式八</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="holidayTz8">
              <el-select
                v-model="form.holidayTz8"
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
            <el-form-item label="直接外出" prop="holidayTz8Out">
              <el-radio-group v-model="form.holidayTz8Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">假日后一天</el-divider>
        <el-divider content-position="left">型式一</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz1">
              <el-select
                v-model="form.postHolidayTz1"
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
            <el-form-item label="直接外出" prop="postHolidayTz1Out">
              <el-radio-group v-model="form.postHolidayTz1Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式二</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz2">
              <el-select
                v-model="form.postHolidayTz2"
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
            <el-form-item label="直接外出" prop="postHolidayTz2Out">
              <el-radio-group v-model="form.postHolidayTz2Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式三</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz3">
              <el-select
                v-model="form.postHolidayTz3"
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
            <el-form-item label="直接外出" prop="postHolidayTz3Out">
              <el-radio-group v-model="form.postHolidayTz3Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式四</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz4">
              <el-select
                v-model="form.postHolidayTz4"
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
            <el-form-item label="直接外出" prop="postHolidayTz4Out">
              <el-radio-group v-model="form.postHolidayTz4Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式五</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz5">
              <el-select
                v-model="form.postHolidayTz5"
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
            <el-form-item label="直接外出" prop="postHolidayTz5Out">
              <el-radio-group v-model="form.postHolidayTz5Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式六</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz6">
              <el-select
                v-model="form.postHolidayTz6"
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
            <el-form-item label="直接外出" prop="postHolidayTz6Out">
              <el-radio-group v-model="form.postHolidayTz6Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式七</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz7">
              <el-select
                v-model="form.postHolidayTz7"
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
            <el-form-item label="直接外出" prop="postHolidayTz7Out">
              <el-radio-group v-model="form.postHolidayTz7Out">
                <el-radio
                  v-for="dict in choiceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">型式八</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通行时区" prop="postHolidayTz8">
              <el-select
                v-model="form.postHolidayTz8"
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
            <el-form-item label="直接外出" prop="postHolidayTz8Out">
              <el-radio-group v-model="form.postHolidayTz8Out">
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
import { listHoliday, getHoliday, delHoliday, addHoliday, updateHoliday, exportHoliday } from "@/api/appgroup/holidayplan";
import { listTimezone } from "@/api/timezone/timezone";
import { listInterval } from "@/api/timezone/interval";

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
      // 假日计划表格数据
      holidayList: [],
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
    /** 查询假日计划列表 */
    getList() {
      this.loading = true;
      listHoliday(this.queryParams).then(response => {
        this.holidayList = response.rows;
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
        preHolidayTz1: null,
        preHolidayTz1Out: null,
        preHolidayTz2: null,
        preHolidayTz2Out: null,
        preHolidayTz3: null,
        preHolidayTz3Out: null,
        preHolidayTz4: null,
        preHolidayTz4Out: null,
        preHolidayTz5: null,
        preHolidayTz5Out: null,
        preHolidayTz6: null,
        preHolidayTz6Out: null,
        preHolidayTz7: null,
        preHolidayTz7Out: null,
        preHolidayTz8: null,
        preHolidayTz8Out: null,
        holidayTz1: null,
        holidayTz1Out: null,
        holidayTz2: null,
        holidayTz2Out: null,
        holidayTz3: null,
        holidayTz3Out: null,
        holidayTz4: null,
        holidayTz4Out: null,
        holidayTz5: null,
        holidayTz5Out: null,
        holidayTz6: null,
        holidayTz6Out: null,
        holidayTz7: null,
        holidayTz7Out: null,
        holidayTz8: null,
        holidayTz8Out: null,
        postHolidayTz1: null,
        postHolidayTz1Out: null,
        postHolidayTz2: null,
        postHolidayTz2Out: null,
        postHolidayTz3: null,
        postHolidayTz3Out: null,
        postHolidayTz4: null,
        postHolidayTz4Out: null,
        postHolidayTz5: null,
        postHolidayTz5Out: null,
        postHolidayTz6: null,
        postHolidayTz6Out: null,
        postHolidayTz7: null,
        postHolidayTz7Out: null,
        postHolidayTz8: null,
        postHolidayTz8Out: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHoliday(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改假日计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHoliday(this.form).then(response => {
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
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
      if(intervalid === 2){
        let interval = this.intervalOptions.find(item => item.id == timezone.interval2);
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
      if(intervalid === 3){
        let interval = this.intervalOptions.find(item => item.id == timezone.interval3);
        return interval.startTime.slice(0,2) + ":" + interval.startTime.slice(2) + " - " + interval.endTime.slice(0,2) + ":" + interval.endTime.slice(2);
      }
    },
    preHolidayTz1OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz1Out).dictLabel
    },
    preHolidayTz2OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz2Out).dictLabel
    },
    preHolidayTz3OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz3Out).dictLabel
    },
    preHolidayTz4OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz4Out).dictLabel
    },
    preHolidayTz5OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz5Out).dictLabel
    },
    preHolidayTz6OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz6Out).dictLabel
    },
    preHolidayTz7OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz7Out).dictLabel
    },
        preHolidayTz8OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.preHolidayTz8Out).dictLabel
    },
    holidayTz1OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz1Out).dictLabel
    },
    holidayTz2OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz2Out).dictLabel
    },
    holidayTz3OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz3Out).dictLabel
    },
    holidayTz4OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz4Out).dictLabel
    },
    holidayTz5OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz5Out).dictLabel
    },
    holidayTz6OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz6Out).dictLabel
    },
    holidayTz7OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz7Out).dictLabel
    },
    holidayTz8OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.holidayTz8Out).dictLabel
    },
    postHolidayTz1OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz1Out).dictLabel
    },
    postHolidayTz2OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz2Out).dictLabel
    },
    postHolidayTz3OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz3Out).dictLabel
    },
    postHolidayTz4OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz4Out).dictLabel
    },
    postHolidayTz5OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz5Out).dictLabel
    },
    postHolidayTz6OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz6Out).dictLabel
    },
    postHolidayTz7OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz7Out).dictLabel
    },
    postHolidayTz8OutFormat(row, column){
      return this.choiceOptions.find(item => item.dictValue == row.postHolidayTz8Out).dictLabel
    },
  }
};
</script>
