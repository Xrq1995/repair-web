<template>
  <div>
    <el-menu id="nav-menu" :default-active="activeIndex" mode="horizontal" background-color="#FAF0E6" active-text-color="#FF7F50" style="z-index: 99">
      <el-menu-item @click="navRepairClick($event)" index="1">报修申请提交</el-menu-item>
      <el-menu-item index="2" @click="navRepairClick($event)" :disabled="disabled">历史报修</el-menu-item>
      <el-submenu index="3">
        <template slot="title">报修单管理</template>
        <template v-for="(unfinished, index) in userUnfinishedLastThree">
          <el-menu-item :index="'3-' + index">[{{unfinished.repairStatus}}]{{unfinished.repairAddress}}-{{unfinished.repairType}}</el-menu-item>
        </template>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">欢迎您：{{loginUserInfo.name}}</template>
        <el-menu-item index="4-1">个人信息</el-menu-item>
        <el-menu-item index="4-2" @click="logoff">注销登录</el-menu-item>
      </el-submenu>
    </el-menu>
    <div id="scroll-animate">
      <div id="scroll-animate-main">
        <div class="wrapper-parallax">
          <section class="header">
            <classify class="classify"></classify>
          </section>
          <section class="content">
            <el-table
              id="repairInfoTable"
              :data="repairInfo"
              style="width: 80%; margin-left: auto; margin-right: auto; text-align: left"
              max-height="500"
              :default-sort = "{prop: 'submitTime', order: 'descending'}">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form label-position="left" inline>
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
                style="text-align: center"
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
                prop="repairContext"
                label="故障原因"
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
                label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    @click="cancelRepair(scope.row)">取消报修</el-button>
                </template>
              </el-table-column>
            </el-table>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState, mapGetters} from 'vuex';
  import Classify from '../components/Classify.vue';

  import {REPAIR} from '../common/api/apis/repair'

  import {errorCodeParse} from '../common/error/error';
  import {errorMessage} from '../common/error/error';
  import {errorInfo} from '../common/error/error';

  export default {
    name: 'Main',
    components: {
      Classify
    },
    data() {
      let self = this;
      let loginUserInfo = this.$store.getters.getLoginUserInfo; //获得保存的登录信息
      let userUnfinished = [];          //未完成报修信息
      let userUnfinishedLastThree = []; //最近三条未完成的报修信息
      let userUnfinishedIndex;          //数据组装索引
      let repairInfo = [];              //报修信息表单数据

      /*获得未完成信息*/
      this.$http.get(REPAIR().INFO.all,
        {
          params:
            {
              number: loginUserInfo['number']
            }
        }
      ).then(response => {
        /*组装导航栏快捷报修单管理数据*/
        userUnfinished = response['body']['data']['repairCurEntityList'];
        userUnfinishedIndex = userUnfinished.length < 3 ? userUnfinished.length : 3;
        //默认最多三条
        for (let i = 0; i < userUnfinishedIndex; i++) {
          userUnfinishedLastThree.push(userUnfinished[userUnfinished.length - i - 1]);
        }
        this.userUnfinishedLastThree = userUnfinishedLastThree;
        //在sessionStorage中操作一定要序列化
        sessionStorage.setItem('userUnfinished', JSON.stringify(userUnfinished));
        //保存未完成信息，不要序列化
        this.$store.dispatch('storeUserUnfinishedAction', userUnfinished);

        /*历史报修页面表单数据组装*/
        repairInfo = userUnfinished.concat(response['body']['data']['repairLogEntityList']);
        //console.log(repairInfo);
        /*格式化表单数据时间戳*/
        for (let i = 0; i < repairInfo.length; i++) {
          repairInfo[i]['submitDate'] = self.formatDateTime(repairInfo[i]['submitTime'], true); //原意做筛选，目前未启用
          repairInfo[i]['submitTime'] = self.formatDateTime(repairInfo[i]['submitTime'], false);
          repairInfo[i]['confirmTime'] = self.formatDateTime(repairInfo[i]['confirmTime'], false);
          repairInfo[i]['createTime'] = self.formatDateTime(repairInfo[i]['createTime'], false);
          repairInfo[i]['finishTime'] = self.formatDateTime(repairInfo[i]['finishTime'], false);
          repairInfo[i]['updateTime'] = self.formatDateTime(repairInfo[i]['updateTime'], false);
          repairInfo[i]['distributeTime'] = self.formatDateTime(repairInfo[i]['distributeTime'], false);
        }
        /*有历史报修信息时开放历史报修导航栏*/
        if (repairInfo.length > 0) {
          this.disabled = false;
        }
        this.repairInfo = repairInfo;
      });

      return {
        activeIndex: '1',               //导航栏激活元素索引
        disabled: true,                 //导航栏历史报修元素是否可用
        scroll: null,                   //滚动距离
        windowHeight: null,             //窗口大小
        userUnfinishedLastThree: [],    //未完成报修信息
        repairInfo: [],                 //所有报修信息
      }
    },
    mounted() {
      //保存应用上下文信息
      let self = this;

      /**
       * 滑动背景立即执行函数
       */
      (function() {
        let windowHeight = $(window).height(),
          contentHeight = $('.content').height(),
          heightDocument = (windowHeight) + ($('.content').height());

        //将窗口高度放入模型数据中
        self.windowHeight = windowHeight;

        /*各分块初始定位 */
        $('#scroll-animate, #scroll-animate-main').css({
          'height':  heightDocument + 'px'
        });
        $('.header').css({
          'height': windowHeight + 'px',
        });
        $('.wrapper-parallax').css({
          'margin-top' : windowHeight + 'px'
        });
        //scrollFooter(window.scrollY, footerHeight);

        /*滚动时触发函数*/
        window.onscroll = function() {
          //获得纵向滚动距离
          let scroll = window.scrollY;
          //将纵向滚动距离放入模型数据中
          self.scroll = scroll;

          /*各滑块滚动效果*/
          //导航栏吸顶效果
          if (scroll > 0) {
            $('#nav-menu').addClass('fix')
          }
          //主页面上移效果
          $('.content').css({
            'top': '-' + scroll + 'px'
          });
          //头部块背景跟随效果
          $('.header').css({
            'background-position-y' : 50 - (scroll * 100 / heightDocument) + '%'
          });

          //分类卡片跟随效果
          $('.classify').css({
            'padding-top': (scroll * 100 / heightDocument) / 4 + '%'
          });
        };


      })();
    },
    computed: {
      ...mapState(['loginUserInfo'])
    },
    watch: {
      /**
       * 根据滚动位置切换导航栏激活元素
       * @param curVal  当前滚动值
       * @param oldVal  旧滚动值
       */
      scroll(curVal, oldVal) {
        /*滚动超过0.8个屏幕时切换下一个激活元素*/
        if (curVal >= this.windowHeight * 0.8) {
          this.activeIndex = '2';
        } else {
          this.activeIndex = '1';
        }
      }
    },
    methods: {
      ...mapActions(['storeUserUnfinishedAction']),

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
       * 导航栏点击函数
       * @param event   点击事件元素
       */
      navRepairClick(event) {
        //获得点击组件元素
        let $e = $(event.$el);
        //获得点击元素名称
        let buttonName  = $e.text();
        /*设置置顶或置底效果*/
        if (buttonName === '报修申请提交') {
          $('html').stop(true,false).animate({scrollTop: 0}, 800);
        } else if (buttonName === '历史报修') {
          $('html').stop(true,false).animate({scrollTop: document.body.scrollHeight}, 800);
        }
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
       * 取消函数
       * @param row  行数据
       */
      cancelRepair(row) {
        let self = this;

        //信息员登录信息
        let loginUserInfo = this.$store.getters.getLoginUserInfo;

        if (row['repairStatus'] !== '待处理') {
          self.$message({
            type: 'error',
            message: '已确认后的报修信息不能取消，请联系信息平台'
          });

          return false;
        }

        self.$confirm('是否确认取消该报修信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log(row);
          self.$http.get(REPAIR().SUBMIT.cancelStu,
            {
              params: {
                id: row.id,
                repairOrderNumber: row.repairOrderNumber,
                name: loginUserInfo['name'],
                number: loginUserInfo['number']
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

<style lang="scss" rel="stylesheet/scss" scoped>
  /*滑动背景样式 ↓↓*/
  #scroll-animate {
    overflow: hidden;
    overflow-y: scroll;scrollbar-base-color: transparent;scrollbar-darkshadow-color: transparent;scrollbar-highlight-color: transparent;scrollbar-face-color: transparent;
  }

  #scroll-animate-main {
    width: 100%;
    left: 0;
    position: fixed;
  }

  #heightPage,
  #heightScroll {
    width: 10px;
    top: 0;
    position: absolute;
    z-index: 99;
  }

  #heightPage {
    left: 0;
  }

  #heightScroll {
    right: 0;
  }

  .header {
    width: 100%;
    height: 100%;
    background: url(../common/image/main-head.jpg) no-repeat 100% 100%;
    top: 0;
    position: fixed;
    z-index: -1;
  }

  .content {
    height: 100vh;
    min-height: 100vh;
    background: #ffffff;
    position: relative;
    z-index: 1;
  }

  .wrapper-parallax {
    margin-top: 100%;
    margin-bottom: 300px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
  }

  @mixin h1() {
    width: 100%;
    height: 100%;
    padding: 0;
    margin: 0;
    text-transform: uppercase;
    text-align: center;
    font-family: Helvetica, sans-serif;
    color: #fff;
  }

  .header h1 {
    font-size: 50px;
    @include h1();
  }

  .content h1 {
    line-height: 1000px;
    color: #999;
    @include h1();
  }

  .header,
  #scroll-animate-main {
    -webkit-transition-property: all;
    -moz-transition-property: all;
    transition-property: all;

    -webkit-transition-duration: 0.4s;
    -moz-transition-duration: 0.4s;
    transition-duration: 0.4s;

    -webkit-transition-timing-function: cubic-bezier(0, 0, 0, 1);
    -moz-transition-timing-function: cubic-bezier(0, 0, 0, 1);
    transition-timing-function: cubic-bezier(0, 0, 0, 1);
  }

  @mixin vertical-align() {
    position: relative;
    top: 50%;
    -webkit-transform: translateY(-50%);
    -ms-transform: translateY(-50%);
    transform: translateY(-50%);
  }

  .classify {
    @include vertical-align();
  }
  /*滑动背景样式 ↑↑*/
</style>

<style lang="css" rel="stylesheet/css" scoped>
  /*导航栏吸顶样式*/
  .fix {
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
  }
  /*表格样式 ↓↓*/
  #repairInfoTable {
    position: relative;
    top: 50%;
    transform: translateY(-50%);
    border-bottom: none;
  }

  #repairInfoTable .el-form-item {
    text-align: left;
    margin-right: 0;
    margin-bottom: 0;
    width: 49%;
  }
  /*表格样式 ↑↑*/
</style>
