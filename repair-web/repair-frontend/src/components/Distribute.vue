<template>
  <div>
    <el-menu id="nav-menu" class="fix" :default-active="activeIndex" mode="horizontal" background-color="#FAF0E6" active-text-color="#FF7F50" style="z-index: 99">
      <el-menu-item index="1" @click="navBarClickActive($event)" class="displayNav">报修信息派发</el-menu-item>
      <el-menu-item index="2" @click="navBarClickActive($event)">已派信息管理</el-menu-item>
      <el-menu-item index="3" @click="navBarClickActive($event)">完成信息管理</el-menu-item>
      <el-submenu index="4">
        <template slot="title">欢迎您：{{loginUserInfo.name}}</template>
        <el-menu-item index="4-1">个人信息</el-menu-item>
        <el-menu-item index="4-2" @click="logoff">注销登录</el-menu-item>
      </el-submenu>
    </el-menu>
    <div id="main" style="overflow-y: auto">
      <div id="tree">
        <el-tree :data="treeData" node-key="id" :default-expanded-keys="[1]" style="margin-top: 50px; width: 80%; margin-right: auto; margin-left: auto"></el-tree>
      </div>
      <div id="table">
        <el-table
          id="repairInfoTable"
          :data="repairInfoData"
          style="width: 98%; margin-left: auto; margin-right: auto; margin-top: 50px; text-align: left"
          max-height="850"
          min-width="900"
          :default-sort = "{prop: 'submitTime', order: 'descending'}">
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
            width="180">
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
                size="mini"
                type="text"
                class="displayButton"
                ref="distribute"
                @click="distribute(scope.row)">{{ button.child1 }}</el-button>
              <el-button
                type="text"
                size="mini"
                class="displayButton"
                ref="rollBack"
                @click="rollBackRepair(scope.row)">{{ button.child2 }}</el-button>
              <el-button
                type="text"
                size="mini"
                class="displayButton"
                ref="hangUp"
                @click="hangUpRepair(scope.row)">{{ button.child3 }}</el-button>
              <el-button
                type="text"
                size="mini"
                ref="finish"
                class="displayButton"
                @click="finishRepair(scope.row)">{{ button.child4 }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog
      title="维修单派发"
      :visible.sync="dialogVisible"
      width="30%"
      center
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="distributeCancel">
      <el-cascader
        :options="options"
        expand-trigger="hover"
        v-model="selectedWorker"
        placeholder="请选择要派发的维修工"
        style="width: 200px; position: relative; left: 50%; margin-left: -100px">
      </el-cascader>
      <span slot="footer" class="dialog-footer">
          <el-button @click="distributeCancel">取 消</el-button>
          <el-button type="primary" @click="distributeConfirm">派 发</el-button>
        </span>
    </el-dialog>
  </div>

</template>

<script>
  import {mapActions, mapState, mapGetters} from 'vuex';

  import {DISTRIBUTE} from '../common/api/apis/distribute';
  import {REPAIR} from '../common/api/apis/repair';
  import {USER} from '../common/api/apis/user';

  import {errorCodeParse} from '../common/error/error';
  import {errorMessage} from '../common/error/error';
  import {errorInfo} from '../common/error/error';

  export default {
    name: 'Distribute',
    data() {
      let self = this;

      /*初始化表单数据*/
      this.$http.get(DISTRIBUTE().INFO.curConfirm, {}).then(response => {
        self.repairInfoData = self.listFormatDate(response, self);
      });

      /*初始化派发维修工备选信息*/
      let options = [];
      this.$http.get(DISTRIBUTE().INFO.allWorkers, {}).then(response => {
        //前台回应为HashMap
        let workers = response['body']['data'];
        /*组装cascader所需数据格式*/
        for (let key in workers) {
          if (workers.hasOwnProperty(key)) {
            let objParent = {}, listChild = [];
            objParent.value = key;
            objParent.label = key;
            for (let i in workers[key]) {
              if (workers[key].hasOwnProperty(i)) {
                let objChild = {};
                objChild.value = workers[key][i];
                objChild.label = workers[key][i]['name'];
                listChild.push(objChild);
              }
            }
            objParent.children = listChild;
            options.push(objParent);
          }
        }
        self.options = options;
      });

      return {
        dialogVisible: false,     //模态框弹窗控制字段
        repairInfoData: [],
        treeData: [{              //树形区域数据
          id: 1,
          label: '报修服务',
          children: [{
            label: '报修受理',
          }, {
            label: '维修报告单',
          }]
        }],
        activeIndex: '1',         //导航栏激活元素索引
        options: [],              //维修工备选信息
        selectedWorker: [],       //维修工选定信息
        row: {},                  //当前行数据
        admini: null,             //是否是管理员登录
        button: {
          child1: '派发',
          child2: '撤回',
          child3: '挂起',
          child4: '完成'
        }
      }
    },
    computed: { //计算属性不能使用ES6箭头函数
      ...mapState(['isLogin', 'loginUserInfo', 'classifyCardId'])
    },
    mounted() {
      let self = this;

      //挂载后组件未必已在document中，要利用updated
      if (this.loginUserInfo['role'] === '班长' || this.loginUserInfo['role'] === '维修工') {
        this.admini = false;
        this.activeIndex = '2';
        this.$http.get(DISTRIBUTE().INFO.curDistribute, {}).then(response => {
          self.repairInfoData = self.listFormatDate(response, self);
        });
        return true;
      }
    },
    updated() {
      if (this.admini === false) {
        $(document).find('.displayButton').each(function () {
          if ($(this).text() !== '完成') {
            $(this).css('display', 'none');
          }
        });
        $(document).find('.displayNav').each(function () {
            $(this).css('display', 'none');
        });
      } else {
        if (this.activeIndex === '1') {
          $(document).find('.displayButton').each(function () {
            if ($(this).text() === '完成') {
              $(this).css('display', 'none');
            } else {
              if ($(this).text() === '撤回') {
                $(this).css('margin-left', '10px');
              }
              $(this).css('display', 'inline');
            }
          });
        } else if (this.activeIndex === '2') {
          $(document).find('.displayButton').each(function () {
            if ($(this).text() === '派发') {
              $(this).css('display', 'none');
            } else {
              if ($(this).text() === '撤回') {
                $(this).css('margin-left', '0px');
              }
              $(this).css('display', 'inline');
            }
          });
        } else if (this.activeIndex ==='3') {
          $(document).find('.displayButton').each(function () {
            if ($(this).text() === '撤回') {
              $(this).css('display', 'inline');
              $(this).css('margin-left', '0px');
            } else {
              $(this).css('display', 'none');
            }
          });
        }
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
      },

      /**
       * 点击派发按钮函数
       * @param row 行数据
       */
      distribute(row) {
        if (row['repairStatus'] !== '已确认') {
          this.$message({
            type: 'error',
            message: '不能派发当前维修信息'
          });

          return false;
        }
        let self = this;
        self.dialogVisible = true;
        self.row = row;
      },

      /**
       * 派发对话框关闭函数
       */
      distributeCancel() {
        this.dialogVisible = false;
        this.selectedWorker = [];
      },

      /**
       * 确认派发函数
       */
      distributeConfirm() {
        let self = this;

        //修缮管理员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        if (self.selectedWorker.length === 0) {
          self.$message({
            type: 'error',
            message: '请选择要派发的维修人员'
          });

          return false;
        }

        self.$confirm('确认派发？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$http.get(DISTRIBUTE().SUBMIT.distribute,
            {
              params: {
                id: self.row.id,
                repairOrderNumber: self.row.repairOrderNumber,
                messengerName: loginUserInfo['name'],
                messengerNumber: loginUserInfo['number'],
                repairNumber: self.selectedWorker[1]['number'],
                repairName: self.selectedWorker[1]['name']
              }
            }
          ).then(response => {
            if (response['body']['meta']['message'] === 'ok') {
              self.$message({
                type: 'success',
                message: '派发成功'
              });
              self.dialogVisible = false;
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
       * 导航栏点击函数
       * @param event   点击事件元素
       */
      navBarClickActive(event) {
        let self = this;

        //获得点击组件元素
        let $e = $(event.$el);
        //获得点击元素名称
        let buttonName  = $e.text();
        /*设置置顶或置底效果*/
        if (buttonName === '报修信息派发') {
          self.activeIndex = '1';
          this.$http.get(DISTRIBUTE().INFO.curConfirm,
            {

            }
          ).then(response => {
            self.repairInfoData = self.listFormatDate(response, self);
          });
        } else if (buttonName === '已派信息管理') {
          self.activeIndex = '2';
          this.$http.get(DISTRIBUTE().INFO.curDistribute,
            {
              params: {
                messengerNumber: this.loginUserInfo['number'],
                messengerName: this.loginUserInfo['name']
              }
            }
          ).then(response => {
            self.repairInfoData = self.listFormatDate(response, self);
          });
        } else if (buttonName === '完成信息管理') {
          self.activeIndex = '3';
          this.$http.get(DISTRIBUTE().INFO.curFinish,
            {
              params: {
                messengerNumber: this.loginUserInfo['number'],
                messengerName: this.loginUserInfo['name']
              }
            }
          ).then(response => {
            self.repairInfoData = self.listFormatDate(response, self);
          });
        }
      },

      /**
       * 撤回报修信息函数
       * @param row 行数据
       */
      rollBackRepair(row) {
        let self = this;

        //修缮管理员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        /*不允许报修状态为已确认的信息在修缮部撤回*/
        if (self.activeIndex === '1' && row['repairStatus'] === '已确认') {
          self.$message({
            type: 'error',
            message: '不能撤回一个已确认的报修信息，请联系信息平台'
          });
          return false;
        }

        self.$confirm('是否撤回当前报修信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$http.get(DISTRIBUTE().INFO.rollBack,
            {
              params: {
                status: row.repairStatus,
                id: row.id,
                repairOrderNumber: row.repairOrderNumber,
                messengerName: loginUserInfo['name'],
                messengerNumber: loginUserInfo['number']
              }
            }).then(response => {
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
       * 完成报修信息函数
       * @param row 行数据
       */
      finishRepair(row) {
        let self = this;

        //修缮管理员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        if (row['repairStatus'] !== '已派发') {
          self.$message({
            type: 'error',
            message: '不能完成当前维修信息'
          });

          return false;
        }

        self.$confirm('是否确认完成？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$http.get(DISTRIBUTE().INFO.finish,
            {
              params: {
                id: row.id,
                repairOrderNumber: row.repairOrderNumber,
                messengerName: loginUserInfo['name'],
                messengerNumber: loginUserInfo['number']
              }
            }).then(response => {
              console.log(response)
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
       * 挂起报修信息函数
       * @param row
       */
      hangUpRepair(row) {
        this.$message({
          type: 'error',
          message: '该功能暂未开启'
        });
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


  .displayButton {
    display: inline;
  }

  .displayNav {
    display: inline;
  }

</style>
