<template>
    <div>
      <el-menu id="nav-menu" class="fix" :default-active="activeIndex" mode="horizontal" background-color="#FAF0E6" active-text-color="#FF7F50" style="z-index: 99">
        <el-menu-item index="1" @click="navBarClickActive($event)">报修信息确认</el-menu-item>
        <el-menu-item index="2" @click="navBarClickActive($event)">确认信息管理</el-menu-item>
        <el-menu-item index="3" @click="navBarClickActive($event)">历史信息管理</el-menu-item>
        <el-submenu index="4">
          <template slot="title">欢迎您：{{loginUserInfo.name}}</template>
          <el-menu-item index="4-1">个人信息</el-menu-item>
          <el-menu-item index="4-2" @click="logoff">注销登录</el-menu-item>
        </el-submenu>
      </el-menu>
      <div id="main" style="overflow-y: auto">
        <div id="tree">
          <el-tree :data="treeData" node-key="id" :default-expanded-keys="[1]" style="margin-top: 50px; width: 80%; margin-right: auto; margin-left: auto"></el-tree>
          <el-button id="repairConfirmBatchlyButton" type="info" @click="repairConfirmBatchly">批量确认<i class="el-icon-d-arrow-left"></i><i class="el-icon-d-arrow-left"></i></el-button>
        </div>
        <div id="table">
          <el-table
            id="repairInfoTable"
            :data="repairInfoData"
            style="width: 98%; margin-left: auto; margin-right: auto; margin-top: 50px; text-align: left"
            max-height="850"
            min-width="900"
            :default-sort = "{prop: 'repairStatus', order: 'descending'}"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="30">
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="table-expand">
                  <el-form-item label="报修类别">
                    <span>{{ props.row.repairType }}</span>
                  </el-form-item>
                  <el-form-item label="维修地址">
                    <span>{{ props.row.repairAddress }}</span>
                  </el-form-item>
                  <el-form-item label="预约时间">
                    <span>{{ props.row.repairDate + " " + props.row.repairTime }}</span>
                  </el-form-item>
                  <el-form-item label="提交学号">
                    <span>{{ props.row.number }}</span>
                  </el-form-item>
                  <el-form-item label="报修时间">
                    <span>{{ props.row.submitTime }}</span>
                  </el-form-item>
                  <el-form-item label="操作人员">
                    <span>{{ props.row.messengerName }}</span>
                  </el-form-item>
                  <el-form-item label="确认时间">
                    <span>{{ props.row.confirmTime }}</span>
                  </el-form-item>
                  <el-form-item label="订单编号">
                    <span>{{ props.row.repairOrderNumber }}</span>
                  </el-form-item>
                  <el-form-item label="派单时间">
                    <span>{{ props.row.distributeTime }}</span>
                  </el-form-item>
                  <el-form-item label="维修工号">
                    <span>{{ props.row.repairNumber }}</span>
                  </el-form-item>
                  <el-form-item label="维修人员">
                    <span>{{ props.row.repairName }}</span>
                  </el-form-item>
                  <el-form-item label="完成时间">
                    <span>{{ props.row.finishTime }}</span>
                  </el-form-item>
                  <el-form-item label="最后更新">
                    <span>{{ props.row.updateTime }}</span>
                  </el-form-item>
                  <el-form-item label="故障原因">
                    <span>{{ props.row.repairContext }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              prop="submitTime"
              label="报修时间"
              width="180"
              sortable>
            </el-table-column>
            <el-table-column
              prop="repairType"
              label="报修类别"
              width="180"
              sortable>
            </el-table-column>
            <el-table-column
              prop="repairStatus"
              label="报修状态"
              width="180"
              sortable>
            </el-table-column>
            <el-table-column
              prop="repairAddress"
              label="维修地址"
              width="180">
            </el-table-column>
            <el-table-column
              prop="number"
              label="提交学号"
              width="180">
            </el-table-column>
            <el-table-column
              prop="tel"
              label="联系电话"
              width="180">
            </el-table-column>
            <el-table-column
              prop="repairDate"
              label="预约日期"
              width="180"
              sortable>
            </el-table-column>
            <el-table-column
              prop="repairTime"
              label="预约时间"
              width="180"
              sortable>
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <el-button
                  id="confirmButton"
                  ref="confirmButton"
                  size="mini"
                  type="text"
                  @click="repairConfirm(scope.row)">{{ button.confirmButton }}</el-button>
                <el-button
                  id="editButton"
                  ref="editButton"
                  type="text"
                  size="mini"
                  class="displayButton"
                  @click="repairEdit(scope.row)">{{ button.editButton }}</el-button>
                <el-button
                  id="cancelButton"
                  ref="cancelButton"
                  type="text"
                  size="mini"
                  class="displayButton"
                  @click="repairCancel(scope.row)">{{ button.cancelButton }}</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <el-dialog
        title="详细报修信息"
        :visible.sync="dialogVisible"
        width="30%"
        center
        :modal-append-to-body="false"
        :append-to-body="true"
        :before-close="editCancel">
        <el-form
          :label-position="labelPosition = 'right'"
          label-width="20%"
          :model="repairInfoRow">
          <el-form-item label="报修人信息">
            <el-input
              placeholder="报修人学号"
              v-model="repairInfoRow.number"
              style="width: 40%">
            </el-input>
            <el-input
              placeholder="联系电话"
              v-model="repairInfoRow.tel"
              style="width: 40%">
            </el-input>
          </el-form-item>
          <el-form-item label="预约时间" >
            <el-col :span="9">
              <el-form-item>
                <el-date-picker
                  action=""
                  v-model="repairInfoRow.repairDate"
                  :picker-options="datePickerOptions"
                  align="right"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                  style="width: 100%;">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col style="text-align: center" :span="1">-</el-col>
            <el-col :span="9">
              <el-form-item>
                <el-select
                  v-model="repairInfoRow.repairTime"
                  placeholder="选择预约时间"
                  style="width: 100%;"
                  prop="repairTime">
                  <el-option
                    v-for="item in timePickerOptions"
                    :key="item.value"
                    :label="item.value"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="报修地址">
            <el-input
              placeholder="报修地址"
              v-model="repairInfoRow.repairAddress"
              style="width: 61%">
            </el-input>
          </el-form-item>
          <el-form-item label="报修类别">
            <el-select
              placeholder="报修类别"
              v-model="repairCategories.selectedParent"
              style="width: 30%">
              <el-option
                v-for="item in repairCategories.options.parent"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-select
              placeholder="请选择分类"
              v-model="repairCategories.selectedChild"
              style="width: 30%">
              <el-option
                v-for="item in repairCategories.options.child"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细描述">
            <el-input
              placeholder="请详细描述故障情况"
              v-model="repairInfoRow.repairContext"
              style="width: 80%"
              type="textarea"
              autosize>
            </el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editCancel">取 消</el-button>
          <el-button type="primary" @click="editSave">保 存</el-button>
        </span>
      </el-dialog>
    </div>

</template>

<script>
  import {mapActions, mapState, mapGetters} from 'vuex';

  import {REPAIR} from '../common/api/apis/repair'

  import {errorCodeParse} from '../common/error/error';
  import {errorMessage} from '../common/error/error';
  import {errorInfo} from '../common/error/error';

  export default {
    name: 'Manage',
    data() {
      let self = this;

      /*初始化表单数据*/
      this.$http.get(REPAIR().INFO.cur, {}).then(response => {
        self.repairInfoData = self.listFormatDate(response, self);
      });

      /*初始化预约维修时间段信息*/
      this.$http.get(REPAIR().TIMEPICKER.all, {}).then(response => {
        let timePickerInfo = response['body']['data'];
        let timePickerOptions = [];
        for (let i = 0; i < timePickerInfo.length; i++) {
          let obj = {};
          obj.label = timePickerInfo[i]['startTime'] + '-' + timePickerInfo[i]['endTime'];
          obj.value = obj['label'];
          timePickerOptions.push(obj);
        }
        this.timePickerOptions = timePickerOptions
      });

      return {
        dialogVisible: false,     //模态框弹窗控制字段
        repairInfoData: [],
        treeData: [{              //树形区域数据
          id: 1,
          label: '报修服务',
          children: [{
            label: '报修申请'
          }, {
            label: '报修受理',
          }, {
            label: '数据维护',
          }, {
            label: '维修人员信息',
          }, {
            label: '构筑物信息',
          }, {
            label: '信息员受理',
          }, {
            label: '材料登记',
          }, {
            label: '维修报告单',
          }, {
            label: '维修报告单审批',
          }]
        }],
        activeIndex: '1',         //导航栏激活元素索引
        repairInfoRow: {},        //表格行数据
        repairInfoRowBack: {},    //表格行数据(深拷贝，用于撤销操作)
        repairInfoConfirmList: [],//确认行数据集合
        repairCategories: {       //报修分类信息
          selectedParent: '',     //父级分类选中(不可手动选择)
          selectedChild: '',      //子级分类选中(可以手动选择，未启用)
          options: {              //下拉菜单数据
            parent: [],
            child:[]
          }
        },
        datePickerOptions: {      //日期选择器配置信息
          disabledDate(time) {    //日期选择器禁用时间
            return time.getTime() < (Date.now() - 3600 * 1000 * 24);
          },
          shortcuts: [{
            text: '今天',         //"今天"快捷选项
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '明天',         //"明天"快捷选项
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '后天',         //"后天"快捷选项
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 2);
              picker.$emit('pick', date);
            }
          }]
        },
        timePickerOptions: [],
        rules: {      //表单校验规则
          number: [
            { required: true, message: '请输入学号', trigger: 'blur' },
          ],
          tel: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { min: 11, max: 11, message: '请输入11位手机号', trigger: 'blur' }
          ],
          repairDate: [],
          repairTime: [],
          repairAddress: [
            { required: true, message: '请输入报修地址', trigger: 'blur' },
          ],
          repairContext: [
            { required: true, message: '请输入报修描述', trigger: 'blur' },
          ]
        },
        button: {
          confirmButton: '确认',
          editButton: '编辑',
          cancelButton: '取消'
        }
      }
    },
    computed: { //计算属性不能使用ES6箭头函数
      ...mapState(['isLogin', 'loginUserInfo', 'classifyCardId']),
      selectedParent() {
        return this.$data.repairCategories.selectedParent;
      }
    },
    watch: {
      /*父级分类信息监听(监听特定属性时需要利用计算属性做跳板)*/
      selectedParent(newValue, oldValue) {
        let self = this;

        /*父级分类变化时清除自己分类选中信息*/
        if (oldValue !== '' && newValue !== oldValue) {
          self.repairCategories.selectedChild = ''
        }

        /*根据所选父级获得子级信息*/
        this.$http.get(REPAIR().CATEGORIES.child,
          {
            params: {
              parent: self.repairCategories.selectedParent
            }
          }
        ).then(response => {
          //将回应信息参数简化
          let categories = response['body']['data'];

          //传入模型中的repairCategories.options中的自定义数据
          let options = [];
          /*去除回应信息中为'/'的属性*/
          for (let key in categories) {
            let obj = {};
            if (categories.hasOwnProperty(key)) {
              if (categories[key] === '/') {
                delete categories[key];
              } else if (key !== 'id' && key !== 'parent') {
                /*将不为'/'的属性放入临时obj中*/
                obj.value = categories[key];
                obj.label = categories[key];
                options.push(obj);
              }
            }
          }

          //传入模型中的repairCategories.options.child中
          self.$set(self.$data.repairCategories.options, 'child', options);
        })
      }
    },
    methods: {
      /**
       * 注销登录点击函数
       */
      logoff() {
        /*注销登录跳转至登录页面*/
        let logoffPath = {};
        logoffPath.path = '/login';
        this.$router.push(logoffPath);

        /*清除sessionStorage和vuex中的登录信息和用户信息*/
        sessionStorage.removeItem('userCheck');
        sessionStorage.removeItem('userInfo');
        this.$store.dispatch('loginAction', false);
        this.$store.dispatch('storeUserAction', '');
      },

      /**
       * 时间戳格式化函数
       * @param inputTime   时间戳
       * @param isSubmit    是否将提交时间精确到小时
       * @returns {*}       格式化时间
       */
      formatDateTime(inputTime, isSubmit) {
        if (inputTime !== null) {
          let date = new Date(inputTime);
          let y = date.getFullYear();
          let m = date.getMonth() + 1;
          m = m < 10 ? ('0' + m) : m;
          let d = date.getDate();
          d = d < 10 ? ('0' + d) : d;
          let h = date.getHours();
          h = h < 10 ? ('0' + h) : h;
          let minute = date.getMinutes();
          let second = date.getSeconds();
          minute = minute < 10 ? ('0' + minute) : minute;
          second = second < 10 ? ('0' + second) : second;
          if (isSubmit) {
            return y + '-' + m + '-' + d;
          }
          return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
        } else {
          return null;
        }
      },

      /**
       * 反格式化时间函数
       * @param dateTime    格式化时间
       * @returns {*}       反格式化时间
       */
      reFormatDateTime(dateTime) {
        if (dateTime !== null) {
          return dateTime = new Date(Date.parse(dateTime.replace(/-/g, "/")));
        } else {
          return dateTime
        }

      },

      /**
       * 多选函数
       * @param val 多选行数据
       */
      handleSelectionChange(val) {
        this.$data.repairInfoConfirmList = val;
        if (val.length !== 0 && this.activeIndex === '1') {
          $('#repairConfirmBatchlyButton').css('display', 'inline');
        } else {
          $('#repairConfirmBatchlyButton').css('display', 'none');
        }
      },

      /**
       * 批量行确认函数
       */
      repairConfirmBatchly() {
        let self = this;

        //信息员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        let idAndRepairOrderNumberList = [];
        this.$data.repairInfoConfirmList.forEach(e => {
          let tempObj = {};
          tempObj.id = e['id'];
          tempObj.repairOrderNumber = e['repairOrderNumber'];
          tempObj.messengerName = loginUserInfo['name'];
          tempObj.messengerNumber = loginUserInfo['number'];
          idAndRepairOrderNumberList.push(tempObj);
        });

        self.$confirm('确认批量信息无误？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$http.post(REPAIR().SUBMIT.confirmList, JSON.stringify(idAndRepairOrderNumberList), {})
            .then(response => {
              if (response['body']['meta']['message'] === 'ok') {
                self.$message({
                  type: 'success',
                  message: '操作成功'
                });
                self.repairInfoData = self.listFormatDate(response, self);
              } else {
                errorInfo(response['body']['meta']['message'], self);
              }
            }).catch(response => {
              errorInfo(response['body']['meta']['message'], self);
            });
          }).catch(response => {
            self.$message({
              type: 'info',
              message: '已取消操作'
            });
          });
      },

      /**
       * 行编辑/行完成函数
       * @param row 行数据
       */
      repairEdit(row) {
        let self = this;

        //信息员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        if (this.activeIndex === '1') {

          /*获得当前报修分类信息*/
          let selectedSplit = row['repairType'].split("-");
          this.$set(this.$data.repairCategories, 'selectedParent', selectedSplit[0]);
          this.$set(this.$data.repairCategories, 'selectedChild', selectedSplit[1]);

          /*获得父级分类数据*/
          this.$http.get(REPAIR().CATEGORIES.all, {}).then(result => {
            let tempArray = [];
            for (let i = 0; i < result['body']['data'].length; i++) {
              let tempObj = {};
              tempObj.value = result['body']['data'][i]['parent'];
              tempObj.label = result['body']['data'][i]['parent'];
              tempArray.push(tempObj);
            }
            //传入模型中的repairCategories.options.parent中
            self.$set(self.$data.repairCategories.options, 'parent', tempArray);
          });

          this.$data.repairInfoRow = row;   //当前行数据
          this.$data.repairInfoRowBack = JSON.parse(JSON.stringify(row)); //深拷贝行数据
          this.$data.dialogVisible = true;  //打开弹窗
          //console.log(this.$data.repairInfoRow)
        } else if (this.activeIndex === '2') {
          /*if (row['repairStatus'] === '已派发') {
            self.$confirm('确认完成该条报修信息？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              self.$http.get(REPAIR().SUBMIT.finish,
                {
                  params: {
                    id: row.id,
                    repairOrderNumber: row.repairOrderNumber,
                    messengerName: loginUserInfo['name'],
                    messengerNumber: loginUserInfo['number']
                  }
                }
              ).then(response => {
                if (response['body']['meta']['message'] === 'ok') {
                  self.$message({
                    type: 'success',
                    message: '操作成功'
                  });
                  self.repairInfoData = self.listFormatDate(response, self);
                } else {
                  errorInfo(response['body']['meta']['message'], self);
                }
              }).catch(response => {
                errorInfo(response['body']['meta']['message'], self);
              });
            }).catch(response => {
              self.$message({
                type: 'info',
                message: '已取消操作'
              });
            });
          } else {
            self.$message({
              type: 'error',
              message: '不能完成一个未派发的报修单'
            });
          }*/
          self.$message({
            type: 'error',
            message: '该功能暂未开启'
          });
        }
      },

      /**
       * 行编辑保存函数
       */
      editSave() {
        let self = this;

        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        self.$confirm('是否保存修改的信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.repairCategories.selectedParent !== '' && this.repairCategories.selectedChild !== '') {
            /*反格式化时间格式->时间戳(可以不需要，在后台接收后并未修改相应字段，除了updateTime)*/
            this.repairInfoRow.submitTime = this.reFormatDateTime(self.repairInfoRow.submitTime);
            this.repairInfoRow.confirmTime = this.reFormatDateTime(self.repairInfoRow.confirmTime);
            this.repairInfoRow.createTime = this.reFormatDateTime(self.repairInfoRow.createTime);
            this.repairInfoRow.finishTime = this.reFormatDateTime(self.repairInfoRow.finishTime);
            this.repairInfoRow.updateTime = this.reFormatDateTime(self.repairInfoRow.updateTime);
            this.repairInfoRow.distributeTime = this.reFormatDateTime(self.repairInfoRow.distributeTime);

            this.repairInfoRow.repairType = this.repairCategories.selectedParent + '-' + this.repairCategories.selectedChild;
            this.repairInfoRow.messengerName = loginUserInfo['name'];
            this.repairInfoRow.messengerNumber = loginUserInfo['number'];
            /**
             * 提交信息平台编辑报修信息
             */
            this.$http.post(REPAIR().SUBMIT.edit, JSON.stringify(self.repairInfoRow), {})
              .then(response => {
                if (response['body']['meta']['message'] === 'ok') {
                  self.$message({
                    type: 'success',
                    message: '保存成功'
                  });
                  self.repairInfoData = self.listFormatDate(response, self);
                } else {
                  errorInfo(response['body']['meta']['message'], self);
                }
                self.dialogVisible = false;
              }).catch(response => {
                errorInfo(response['body']['meta']['message'], self);
              });
          } else {
            self.$message({
              type: 'error',
              message: '请选择分类'
            });
          }
        }).catch(error => {
          self.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
      },

      /**
       * 行编辑取消函数
       */
      editCancel() {
        let self = this;

        //关闭模态框
        this.dialogVisible = false;
        //撤销数据编辑(需要每个字段逐个还原)
        for (let key in self.$data.repairInfoRow) {
          if (self.$data.repairInfoRow.hasOwnProperty(key)) {
            self.$data.repairInfoRow[key] = self.$data.repairInfoRowBack[key];
          }
        }
      },

      /**
       * 行确认/行撤回函数
       * @param row 行数据
       */
      repairConfirm(row) {
        let self = this;

        //信息员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        let path, message;
        let params = {};

        if (this.activeIndex === '1') {
          path = REPAIR().SUBMIT.confirm;
          message = '确认信息无误？';
          params = {
            id: row.id,
            repairOrderNumber: row.repairOrderNumber,
            messengerName: loginUserInfo['name'],
            messengerNumber: loginUserInfo['number']
          };
        } else {
          if (row['repairStatus'] === '已派发' || row['repairStatus'] === '已完成') {
            self.$message({
              type: 'error',
              message: '不能撤回一个已派发/已完成的报修单，请联系修缮服务部'
            });
            return false;
          }
          path = REPAIR().SUBMIT.rollBack;
          message = '确认撤回信息？';
          params = {
            status: row.repairStatus,
            id: row.id,
            repairOrderNumber: row.repairOrderNumber,
            messengerName: loginUserInfo['name'],
            messengerNumber: loginUserInfo['number']
          }
        }

        self.$confirm(message, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log(row);
          self.$http.get(path,
            {
              params: params
            }
          ).then(response => {
            if (response['body']['meta']['message'] === 'ok') {
              self.$message({
                type: 'success',
                message: '操作成功'
              });
              self.repairInfoData = self.listFormatDate(response, self);
            } else {
              errorInfo(response['body']['meta']['message'], self);
            }
          }).catch(response => {
            errorInfo(response['body']['meta']['message'], self);
          });
        }).catch(response => {
          self.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      },

      /**
       * 行取消函数
       * @param row 行数据
       */
      repairCancel(row) {
        let self = this;

        //信息员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        if (row['repairStatus'] === '已派发') {
          self.$message({
            type: 'error',
            message: '不能取消一个已派发的报修单，请联系修缮服务部'
          });
          return false;
        }

        self.$confirm('是否确认取消该报修信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log(row);
          self.$http.get(REPAIR().SUBMIT.cancel,
            {
              params: {
                status : row.repairStatus,
                id: row.id,
                repairOrderNumber: row.repairOrderNumber,
                messengerName: loginUserInfo['name'],
                messengerNumber: loginUserInfo['number']
              }
            }
          ).then(response => {
            if (response['body']['meta']['message'] === 'ok') {
              self.$message({
                type: 'success',
                message: '操作成功'
              });
              self.repairInfoData = self.listFormatDate(response, self);
            } else {
              errorInfo(response['body']['meta']['message'], self);
            }
          }).catch(response => {
            errorInfo(response['body']['meta']['message'], self);
          });
        }).catch(response => {
          self.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      },

      /**
       * 导航栏切换函数
       * @param event 点击导航栏元素
       */
      navBarClickActive(event) {
        let self = this;

        let activeNav = $(event.$el).text();  //导航栏点击元素内容

        /*获取表单数据地址*/
        let dataPath;
        if (activeNav === '报修信息确认') {
          this.activeIndex = '1';
          dataPath = REPAIR().INFO.cur;
          self.button.confirmButton = '确认';
          self.button.editButton = '编辑';
          self.button.cancelButton = '取消';
          $(document).find('.displayButton').each(function () {
            $(this).css('display', 'inline');
          });
        } else if (activeNav === '确认信息管理') {
          this.activeIndex = '2';
          dataPath = REPAIR().INFO.curConfirm;
          self.button.confirmButton = '撤回';
          self.button.editButton = '完成';
          self.button.cancelButton = '取消';
          $(document).find('.displayButton').each(function () {
            $(this).css('display', 'inline');
          });
        } else if (activeNav === '历史信息管理') {
          this.activeIndex = '3';
          dataPath = REPAIR().INFO.log;
          self.button.confirmButton = '撤回';
          self.button.editButton = '';
          self.button.cancelButton = '';
          $(document).find('.displayButton').each(function () {
            $(this).css('display', 'none');
          });
        }

        /*切换表单数据*/
        this.$http.get(dataPath, {}).then(response => {
          self.repairInfoData = self.listFormatDate(response, self);

        });
      },

      /**
       * 集合时间格式化函数
       * @param response  回应信息传入
       * @param self      上下文
       * @returns {*}     格式化返回集合
       */
      listFormatDate(response, self) {
        for (let i = 0; i < response['body']['data'].length; i++) {
          response['body']['data'][i]['submitDate'] = self.formatDateTime(response['body']['data'][i]['submitTime'], true); //原意做筛选，目前未启用
          response['body']['data'][i]['submitTime'] = self.formatDateTime(response['body']['data'][i]['submitTime'], false);
          response['body']['data'][i]['confirmTime'] = self.formatDateTime(response['body']['data'][i]['confirmTime'], false);
          response['body']['data'][i]['createTime'] = self.formatDateTime(response['body']['data'][i]['createTime'], false);
          response['body']['data'][i]['finishTime'] = self.formatDateTime(response['body']['data'][i]['finishTime'], false);
          response['body']['data'][i]['updateTime'] = self.formatDateTime(response['body']['data'][i]['updateTime'], false);
          response['body']['data'][i]['distributeTime'] = self.formatDateTime(response['body']['data'][i]['distributeTime'], false);
        }

        return response['body']['data'];
      }
    }
  }
</script>

<style lang="css" rel="stylesheet/css" scoped>
  /*导航栏吸顶样式*/
  .fix {
    width: 100%;
    height: 60px;
    position: fixed;
    top: 0;
    left: 0;
  }

  #main {
    position: fixed;
    width: 100%;
    height: calc(100% - 60px);
    top: 60px;
  }

  #tree {
    width: 10%;
    height: 100%;
    overflow-x: auto;
    overflow-y: auto;
    float: left;
  }

  #table {
    float: left;
    width: 90%;
    height: 100%;
  }

  #repairInfoTable {
    position: relative;
    top: 50%;
    transform: translateY(-50%);
    border-bottom: none;
  }

  #repairInfoTable .el-form-item {
    display: inline-block;
    text-align: left;
    margin-right: 0;
    margin-top: 0;
    margin-bottom: 0;
    width: 49%;
  }

  #repairConfirmBatchlyButton {
    margin-top: 50px;
    display: none;
    transition: all 1s ease 0s !important;
  }

  .displayButton {
    display: inline;
  }

</style>
