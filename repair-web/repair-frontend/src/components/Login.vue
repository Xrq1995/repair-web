<template>
  <div id="login">
    <div class="wrapper">
      <div id="container"></div>
    </div>
    <div class="box blurred-background tinted">
      <div class="content">
        <h1 id="draggable-header">欢迎来到安徽大学报修系统</h1>
        <h2 id="login-message">{{ loginInput.message }}</h2>
        <div id="i">
          <el-input class="login-input" v-model="loginInput.number" placeholder="学号"></el-input>
          <el-tooltip class="item" effect="light" content="请输入身份证后六位(含有字母向前推进一位)" placement="top">
            <el-input class="login-input" v-model="loginInput.password" placeholder="身份证后六位(不包括字母)" type="password"></el-input>
          </el-tooltip>
          <p><button class="login-button" @click="login">登录</button></p>
        </div>
        <p class="related"><a>Property Management & Service Center Anhui University</a></p>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState, mapGetters} from 'vuex';

  import {USER} from '../common/api/apis/user.js';

  import {errorCodeParse} from '../common/error/error';
  import {errorMessageDisplay} from '../common/error/error';

  export default {
    name: 'Login',
    data() {
      return {
        loginInput: {     //登录信息输入
          number: '',     //学号
          password: '',   //密码(身份证后六位)
          message: ''     //登录提示信息
        }
      }
    },
    mounted() {
      $(function() {
        $( '.box' ).draggable({
          //revert: true,
          //proxy: 'clone',
          handle: '#draggable-header',

        });
      });

      (function() {
        let WIDTH, HEIGHT, canvas, con, g;
        let pxs = [];
        let rint = 50;

        $.fn.astral = function () {
          this.append($('<canvas id="astral"></canvas>'));
          setup(this);
        };

        function setup (container) {
          let windowSize = function() {
            WIDTH = $(window).width() - 50;
            HEIGHT = $(window).height() - 50;
            canvas = container.find('#astral');
            canvas.attr('width', WIDTH).attr('height', HEIGHT);
          };

          windowSize();

          $(window).resize(function() {
            windowSize();
          });

          con = canvas[0].getContext('2d');

          for (let i = 0; i < 100; i++) {
            pxs[i] = new Circle();
            pxs[i].reset();
          }

          requestAnimationFrame(draw);
        }

        function draw () {
          con.clearRect(0, 0, WIDTH, HEIGHT);
          con.globalCompositeOperation = "lighter";

          for (let i = 0; i < pxs.length; i++) {
            pxs[i].fade();
            pxs[i].move();
            pxs[i].draw();
          }

          requestAnimationFrame(draw);
        }

        function Circle() {
          this.s = {
            ttl: 15000,
            xmax: 5,
            ymax: 2,
            rmax: 7,
            rt: 1,
            xdef: 960,
            ydef: 540,
            xdrift: 4,
            ydrift: 4,
            random: true,
            blink: true
          };

          this.reset = function() {
            this.x = (this.s.random ? WIDTH * Math.random() : this.s.xdef);
            this.y = (this.s.random ? HEIGHT * Math.random() : this.s.ydef);
            this.r = ((this.s.rmax - 1) * Math.random()) + 1;

            this.dx = (Math.random() * this.s.xmax) * (Math.random() < 0.5 ? -1 : 1);
            this.dy = (Math.random() * this.s.ymax) * (Math.random() < 0.5 ? -1 : 1);

            this.hl = (this.s.ttl / rint) * (this.r / this.s.rmax);
            this.rt = Math.random() * this.hl;

            this.stop = Math.random() * 0.2 + 0.4;

            this.s.rt = Math.random() + 1;
            this.s.xdrift *= Math.random() * (Math.random() < 0.5 ? -1 : 1);
            this.s.ydrift *= Math.random() * (Math.random() < 0.5 ? -1 : 1);
          };

          this.fade = function() {
            this.rt += this.s.rt;
          };

          this.draw = function() {
            let newo, cr;

            if (this.s.blink && (this.rt <= 0 || this.rt >= this.hl)) {
              this.s.rt = this.s.rt * -1;
            }
            else if (this.rt >= this.hl) {
              this.reset();
            }

            newo = 1 - (this.rt / this.hl);

            con.beginPath();
            con.arc(this.x, this.y, this.r, 0, Math.PI * 2, true);
            con.closePath();

            cr = this.r * newo;

            g = con.createRadialGradient(this.x, this.y, 0, this.x, this.y, (cr <= 0 ? 1 : cr));
            g.addColorStop(0.0, 'rgba(193,254,254,' + newo + ')');
            g.addColorStop(this.stop, 'rgba(193,254,254,' + (newo * 0.2) + ')');
            g.addColorStop(1.0, 'rgba(193,254,254,0)');

            con.fillStyle = g;
            con.fill();
          };

          this.move = function() {
            this.x += (this.rt / this.hl) * this.dx;
            this.y += (this.rt / this.hl) * this.dy;
            if (this.x > WIDTH || this.x < 0) this.dx *= -1;
            if (this.y > HEIGHT || this.y < 0) this.dy *= -1;
          };

          this.getX = function() {
            return this.x;
          };

          this.getY = function() {
            return this.y;
          };
        }
      })();

      $('#container').astral();
    },
    computed: {
      //vuex中state解构赋值
      ...mapState(['isLogin', 'loginUserInfo']),
    },
    methods: {
      //vuex中actions解构赋值
      ...mapActions(['loginAction', 'storeUserAction', 'storeUserUnfinishedAction']),

      /**
       * 登录按钮执行函数
       */
      login: function () {
        /*发送登录请求*/
        this.$http.get(USER().LOGIN.login,
          {
            params: {
              number: this.loginInput.number,
              idCardLastSix: this.loginInput.password
            }
          }
        ).then(response => {
          /*将回应信息参数简化*/
          let afterLoginPath = {},
            resMsg = response['body']['meta'],
            resData = response['body']['data'];

          /*判断是否登陆成功*/
          if (resMsg['success'] && resData['userCheck']) {

            /*登陆成功则将登陆状态和用户信息存储在sessionStorage中，
            * 在vuex初始化时从sessionStorage中获取，仅存在vuex中会
            * 在刷新页面时丢失数据*/
            sessionStorage.setItem('userCheck', resData['userCheck']);
            //在sessionStorage中操作一定要序列化
            sessionStorage.setItem('userInfo', JSON.stringify(resData['userInfo']));
            //在sessionStorage中操作一定要序列化
            //sessionStorage.setItem('userUnfinished', JSON.stringify(resData['userUnfinished']));

            /*第一次登陆时改变vuex中的登陆状态和用户信息，否则无法跳转；以及未完成信息[在主页面组件中执行]*/
            this.$store.dispatch('loginAction', true);
            //保存登陆信息，不要序列化
            this.$store.dispatch('storeUserAction', resData['userInfo']);
            //保存未完成信息，不要序列化
            //this.$store.dispatch('storeUserUnfinishedAction', resData['userUnfinished']);

            //根据登录跳转不同页面
            if (resData['userInfo']['role'] !== '学生' && resData['userInfo']['college'] === '修缮服务部') {
              afterLoginPath.path = '/distribute';
            } else if (resData['userInfo']['role'] !== '学生' && resData['userInfo']['college'] === '信息平台') {
              afterLoginPath.path = '/manage';
            } else if (resData['userInfo']['role'] === '学生') {
              afterLoginPath.path = '/main';
            }

            //路由跳转
            this.$router.push(afterLoginPath);
          }
        }).catch(error => {
          console.log(error);
          let errorCode = error['body']['meta']['message'];
          let errorData = error['body']['data'];

          errorMessageDisplay(errorCodeParse(errorCode, errorData), $('#login-message'));
        })
      }
    }
  }
</script>

<style lang="scss" rel="stylesheet/scss"> //其中修改了el-input的样式，scoped会导致全局样式不能被污染，修改的样式不起作用(请尽量使用scoped)
  /*登录页面背景样式 ↓↓*/
  #container {
    background-color: rgba(0,0,0,0.5);
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0;
    left: 0;
  }

  .wrapper {
    position: relative;
    width: 100%;
    height: 100vh;
    background: url('../common/image/login.jpg') no-repeat;
    background-size: 100%, 100%;
  }
  /*登录页面背景样式 ↑↑*/

  /*登录框样式 ↓↓*/
  $normal-img: '../common/image/login.jpg';

  .blurred-background {
    background-repeat:no-repeat;
    background-size:cover;
    background-attachment: fixed;

    &.tinted {
      background-image: linear-gradient(0deg, rgba(255,255,255,.2),rgba(255,255,255,.2)),url($normal-img);
    }
  }

  .login-input .el-input__inner { //污染全局样式时，限定污染元素的类别，避免所有元素都被污染
    width: 50%;
    margin: 8px;
    background-color: rgba(255, 255, 255, 0.2);
    border: 0;
  }

  .login-button {
    font-size: 0.8em;
    letter-spacing: 0.08em;
    color: rgba(255, 255, 255, 0.9);
    line-height: 15px;
    padding: 10px 20px;
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 2em;
    transition: all 0.3s ease;
    background: transparent;

    &:hover {
      background: white;
      color: Gray;
    }

    &:active {
    }
  }

  .box {
    width: 500px;
    height: 320px;
    left: -webkit-calc(50% - 250px);
    top: 20%;
    position: absolute;
    border-radius: 5px;
    box-shadow: 0 20px 30px rgba(0, 0, 0, 0.6);
    border: 1px solid rgba(255, 255, 255, 0.3);
    padding: 20px;
    text-align: center;
    box-sizing: border-box;
    display: flex;
    transition: box-shadow 0.3s ease;

    &:active {
      cursor: move;
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.9);
    }

    .content {
      margin: auto;
    }
  }

  @mixin text-shadow() {
    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  }

  @mixin text-color() {
    color: black;
  }

  #login h1 {
    font-weight: 900;
    text-shadow: 0 2px 2px rgba(0, 0, 0, 0.4);
    color: #FFEFD5;
  }

  #login h2 {
    color: #D87093;
    font-size: 8px;
    font-weight: 600;
    height: 8px;
  }

  #login p {
    margin: 10px;

  &.related{
    font-weight: 700;

    a {
      color: #FDF5E6;
      @include text-shadow;
      font-weight: 600;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
  /*登录框样式 ↑↑*/
</style>
