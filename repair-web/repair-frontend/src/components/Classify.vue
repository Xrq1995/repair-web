<template>
  <div style="height: 100%">
    <div class="loading" style="height: 100%">
      <section style="height: 100%;padding-top: 20vh">
        <main style="height: 100%" >
          <a class="tilter tilter--1" style="float: left" @click="dialogDisplay($event)">
            <figure class="tilter__figure">
              <img class="tilter__image" src="../common/animate/img/1.jpg" alt="img01" />
              <div class="tilter__deco tilter__deco--shine"><div></div></div>
              <figcaption class="tilter__caption">
                <h3 class="tilter__title">用水故障</h3>
                <p class="tilter__description">Water</p>
              </figcaption>
              <svg class="tilter__deco tilter__deco--lines" viewBox="0 0 300 415">
                <path d="M20.5,20.5h260v375h-260V20.5z" ></path>
              </svg>
            </figure>
          </a>
          <a class="tilter tilter--1" style="float: left" @click="dialogDisplay($event)">
            <figure class="tilter__figure">
              <img class="tilter__image" src="../common/animate/img/2.jpg" alt="img02" />
              <div class="tilter__deco tilter__deco--shine"><div></div></div>
              <figcaption class="tilter__caption">
                <h3 class="tilter__title">用电故障</h3>
                <p class="tilter__description">Electric</p>
              </figcaption>
              <svg class="tilter__deco tilter__deco--lines" viewBox="0 0 300 415">
                <path d="M20.5,20.5h260v375h-260V20.5z" ></path>
              </svg>
            </figure>
          </a>
          <a class="tilter tilter--1" style="float: left" @click="dialogDisplay($event)">
            <figure class="tilter__figure">
              <img class="tilter__image" src="../common/animate/img/3.jpg" alt="img02" />
              <div class="tilter__deco tilter__deco--shine"><div></div></div>
              <figcaption class="tilter__caption">
                <h3 class="tilter__title">漏水故障</h3>
                <p class="tilter__description">Leakage</p>
              </figcaption>
              <svg class="tilter__deco tilter__deco--lines" viewBox="0 0 300 415">
                <path d="M20.5,20.5h260v375h-260V20.5z" ></path>
              </svg>
            </figure>
          </a>
          <a class="tilter tilter--1" style="float: left" @click="dialogDisplay($event)">
            <figure class="tilter__figure">
              <img class="tilter__image" src="../common/animate/img/4.jpg" alt="img02" />
              <div class="tilter__deco tilter__deco--shine"><div></div></div>
              <figcaption class="tilter__caption">
                <h3 class="tilter__title">家具故障</h3>
                <p class="tilter__description">Furniture</p>
              </figcaption>
              <svg class="tilter__deco tilter__deco--lines" viewBox="0 0 300 415">
                <path d="M20.5,20.5h260v375h-260V20.5z" ></path>
              </svg>
            </figure>
          </a>
          <a class="tilter tilter--1" style="float: left" @click="dialogDisplay($event)">
            <figure class="tilter__figure">
              <img class="tilter__image" src="../common/animate/img/5.jpg" alt="img02" />
              <div class="tilter__deco tilter__deco--shine"><div></div></div>
              <figcaption class="tilter__caption">
                <h3 class="tilter__title">其他故障</h3>
                <p class="tilter__description">Other</p>
              </figcaption>
              <svg class="tilter__deco tilter__deco--lines" viewBox="0 0 300 415">
                <path d="M20.5,20.5h260v375h-260V20.5z" ></path>
              </svg>
            </figure>
          </a>
        </main>
      </section>
    </div>
    <el-dialog
      title="详细报修信息"
      :visible.sync="dialogVisible"
      width="30%"
      center
      :modal-append-to-body="false"
      :append-to-body="true">
      <el-form
        :label-position="labelPosition = 'right'"
        label-width="20%"
        :model="repairInfo"
        :rules="rules"
        ref="repairInfoForm">
        <el-form-item label="报修人信息" prop="tel" required>
          <el-input
            placeholder="报修人姓名"
            v-model="repairInfo.name"
            style="width: 30%">
          </el-input>
          <el-input
            placeholder="报修人学号"
            v-model="repairInfo.number"
            style="width: 30%">
          </el-input>
          <el-input
            placeholder="联系电话"
            v-model="repairInfo.tel"
            style="width: 30%">
          </el-input>
        </el-form-item>
        <el-form-item label="预约时间" required>
          <el-col :span="9">
            <el-form-item prop="date">
              <el-date-picker
                action=""
                v-model="repairInfo.date"
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
            <el-form-item prop="time">
              <el-select
                v-model="repairInfo.time"
                placeholder="选择预约时间"
                style="width: 100%;">
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
        <el-form-item label="报修地址" prop="address">
          <el-input
            placeholder="报修地址"
            v-model="repairInfo.address"
            style="width: 61%">
          </el-input>
        </el-form-item>
        <el-form-item label="报修类别" prop="selectedChild">
          <el-input
            placeholder="报修类别"
            v-model="repairInfo.selectedParent"
            :disabled="true"
            style="width: 30%">
          </el-input>
          <el-select
            placeholder="请选择分类"
            v-model="repairInfo.selectedChild"
            style="width: 30%">
            <el-option
              v-for="item in repairCategories.options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细描述" prop="context">
          <el-input
            placeholder="请详细描述故障情况"
            v-model="repairInfo.context"
            style="width: 80%"
            type="textarea"
            autosize>
          </el-input>
        </el-form-item>
        <el-form-item required>
          <el-upload
            :limit="1"
            ref="upload"
            :auto-upload="false"
            list-type="picture"
            :file-list="fileList"
            :data="repairInfo"
            :action="submitPath"
            :on-exceed="repairImageOverLimit"
            :on-change="repairImageChange"
            :before-remove="repairImageRemove"
            :on-success="repairInfoSubmitSuccess"
            :on-error="repairInfoSubmitError">
            <span slot="tip" style="padding-left: 10px; color: #C0C0C0">必须上传jpg/png图片，帮助更快定位问题</span>
            <el-button slot="trigger" size="small" type="info">选取文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="repairInfoSubmit('repairInfoForm')">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import anime from 'animejs';  //非常重要的一步，先cnpm install animejs --save安装依赖包，再引入anime为动画立即执行函数提供参数
  import '../common/animate/js/imagesloaded.pkgd.min.js'; //图片预加载库

  import {mapState, mapGetters, mapActions} from 'vuex';

  import {REPAIR} from '../common/api/apis/repair'

  import {errorCodeParse} from '../common/error/error';
  import {errorMessage} from '../common/error/error';


  export default {
    name: 'Classify',
    data() {
      //从store中拿取登录用户信息
      let loginUserInfo = this.$store.getters.getLoginUserInfo;
      let repairInfo = {};

      /*初始化报修信息字段*/
      repairInfo.id = loginUserInfo['id'];
      repairInfo.name = loginUserInfo['name'];
      repairInfo.number = loginUserInfo['number'];
      repairInfo.tel = loginUserInfo['tel'];
      repairInfo.address = loginUserInfo['address'];
      repairInfo.selectedParent = '';
      repairInfo.selectedChild = '';
      repairInfo.context = '';
      repairInfo.date = '';
      repairInfo.time = '';
      repairInfo.submitTime = '';
      this.repairInfo = repairInfo;

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

      /*自定义验证规则*/

      /*手机号验证*/
      let checkTel = (rule, value, callback) => {
        // language=JSRegexp
        let telReg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!telReg.test(value)) {
          return callback(new Error('请输入正确的11位手机号'))
        } else {
          callback();
        }
      };

      return {
        dialogVisible: false,     //模态框弹窗控制字段
        repairCategories: {       //报修分类信息
          selectedParent: '',     //父级分类选中(不可手动选择)
          selectedChild: '',      //子级分类选中(可以手动选择，未启用)
          options: []             //下拉菜单数据
        },
        datePickerOptions: {      //日期选择器配置信息
          disabledDate(time) {    //日期选择器禁用时间
            let date = Date.now();
            return time.getTime() < (date - 3600 * 1000 * 24);
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
        repairInfo: repairInfo,   //报修信息
        fileList: [],
        submitPath: REPAIR().SUBMIT.repairInfo,  //报修信息提交接口地址
        rules: {
          tel: [
            {validator: checkTel, trigger: 'blur'}
          ],
          date: [
            {required: true, message: '请选择预约日期', trigger: 'blur' }
          ],
          time: [
            {required: true, message: '请选择预约时间', trigger: 'change' }
          ],
          address: [
            {required: true, message: '请输入报修地址', trigger: 'blur' }
          ],
          selectedChild: [
            {required: true, message: '请选择分类', trigger: 'change' }
          ],
          context: [
            {required: true, message: '请输入报修详细描述', trigger: 'blur' }
          ]
        }
      }
    },
    mounted() {
      //页面初始化立即执行函数
      (function() {
        let tiltSettings = [
          {},
          {
            movement: {
              imgWrapper : {
                translation : {x: 10, y: 10, z: 30},
                rotation : {x: 0, y: -10, z: 0},
                reverseAnimation : {duration : 200, easing : 'easeOutQuad'}
              },
              lines : {
                translation : {x: 10, y: 10, z: [0,70]},
                rotation : {x: 0, y: 0, z: -2},
                reverseAnimation : {duration : 2000, easing : 'easeOutExpo'}
              },
              caption : {
                rotation : {x: 0, y: 0, z: 2},
                reverseAnimation : {duration : 200, easing : 'easeOutQuad'}
              },
              overlay : {
                translation : {x: 10, y: -10, z: 0},
                rotation : {x: 0, y: 0, z: 2},
                reverseAnimation : {duration : 2000, easing : 'easeOutExpo'}
              },
              shine : {
                translation : {x: 100, y: 100, z: 0},
                reverseAnimation : {duration : 200, easing : 'easeOutQuad'}
              }
            }
          },
          {
            movement: {
              imgWrapper : {
                rotation : {x: -5, y: 10, z: 0},
                reverseAnimation : {duration : 900, easing : 'easeOutCubic'}
              },
              caption : {
                translation : {x: 30, y: 30, z: [0,40]},
                rotation : {x: [0,15], y: 0, z: 0},
                reverseAnimation : {duration : 1200, easing : 'easeOutExpo'}
              },
              overlay : {
                translation : {x: 10, y: 10, z: [0,20]},
                reverseAnimation : {duration : 1000, easing : 'easeOutExpo'}
              },
              shine : {
                translation : {x: 100, y: 100, z: 0},
                reverseAnimation : {duration : 900, easing : 'easeOutCubic'}
              }
            }
          },
          {
            movement: {
              imgWrapper : {
                rotation : {x: -5, y: 10, z: 0},
                reverseAnimation : {duration : 50, easing : 'easeOutQuad'}
              },
              caption : {
                translation : {x: 20, y: 20, z: 0},
                reverseAnimation : {duration : 200, easing : 'easeOutQuad'}
              },
              overlay : {
                translation : {x: 5, y: -5, z: 0},
                rotation : {x: 0, y: 0, z: 6},
                reverseAnimation : {duration : 1000, easing : 'easeOutQuad'}
              },
              shine : {
                translation : {x: 50, y: 50, z: 0},
                reverseAnimation : {duration : 50, easing : 'easeOutQuad'}
              }
            }
          },
          {
            movement: {
              imgWrapper : {
                translation : {x: 0, y: -8, z: 0},
                rotation : {x: 3, y: 3, z: 0},
                reverseAnimation : {duration : 1200, easing : 'easeOutExpo'}
              },
              lines : {
                translation : {x: 15, y: 15, z: [0,15]},
                reverseAnimation : {duration : 1200, easing : 'easeOutExpo'}
              },
              overlay : {
                translation : {x: 0, y: 8, z: 0},
                reverseAnimation : {duration : 600, easing : 'easeOutExpo'}
              },
              caption : {
                translation : {x: 10, y: -15, z: 0},
                reverseAnimation : {duration : 900, easing : 'easeOutExpo'}
              },
              shine : {
                translation : {x: 50, y: 50, z: 0},
                reverseAnimation : {duration : 1200, easing : 'easeOutExpo'}
              }
            }
          },
          {
            movement: {
              lines : {
                translation : {x: -5, y: 5, z: 0},
                reverseAnimation : {duration : 1000, easing : 'easeOutExpo'}
              },
              caption : {
                translation : {x: 15, y: 15, z: 0},
                rotation : {x: 0, y: 0, z: 3},
                reverseAnimation : {duration : 1500, easing : 'easeOutElastic', elasticity : 700}
              },
              overlay : {
                translation : {x: 15, y: -15, z: 0},
                reverseAnimation : {duration : 500,easing : 'easeOutExpo'}
              },
              shine : {
                translation : {x: 50, y: 50, z: 0},
                reverseAnimation : {duration : 500, easing : 'easeOutExpo'}
              }
            }
          },
          {
            movement: {
              imgWrapper : {
                translation : {x: 5, y: 5, z: 0},
                reverseAnimation : {duration : 800, easing : 'easeOutQuart'}
              },
              caption : {
                translation : {x: 10, y: 10, z: [0,50]},
                reverseAnimation : {duration : 1000, easing : 'easeOutQuart'}
              },
              shine : {
                translation : {x: 50, y: 50, z: 0},
                reverseAnimation : {duration : 800, easing : 'easeOutQuart'}
              }
            }
          },
          {
            movement: {
              lines : {
                translation : {x: 40, y: 40, z: 0},
                reverseAnimation : {duration : 1500, easing : 'easeOutElastic'}
              },
              caption : {
                translation : {x: 20, y: 20, z: 0},
                rotation : {x: 0, y: 0, z: -5},
                reverseAnimation : {duration : 1000, easing : 'easeOutExpo'}
              },
              overlay : {
                translation : {x: -30, y: -30, z: 0},
                rotation : {x: 0, y: 0, z: 3},
                reverseAnimation : {duration : 750, easing : 'easeOutExpo'}
              },
              shine : {
                translation : {x: 100, y: 100, z: 0},
                reverseAnimation : {duration : 750, easing : 'easeOutExpo'}
              }
            }
          }];

        function init() {
          let idx = 0;
          [].slice.call($(document).find('a.tilter')).forEach(function(el, pos) {
            //idx = pos % 2 === 0 ? idx + 1 : idx;
            new TiltFx(el, tiltSettings[0]); //所有模块设置相同动画效果
          });
        }

        //图片预加载
        imagesLoaded($(document).find('main'), function() {
          $(document).find('.loading').each(function () {
            $(this).removeClass('loading');
          });
          init();
        });
      })();

      //动画立即执行函数
      (function(window) {
        function extend(a, b) {
          for (let key in b) {
            if(b.hasOwnProperty(key)) {
              a[key] = b[key];
            }
          }
          return a;
        }

        /**
         * 鼠标定位函数
         * @param e 鼠标元素
         */
        function getMousePos(e) {
          let posx = 0, posy = 0;
          if (!e) var e = window.event;
          if (e.pageX || e.pageY) {
            posx = e.pageX;
            posy = e.pageY;
          }
          else if (e.clientX || e.clientY) 	{
            posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
            posy = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
          }
          return { x: posx, y: posy }
        }

        /**
         * 倾斜适配函数
         * @param el      动画元素
         * @param options 动画设置
         */
        function TiltFx(el, options) {
          this.DOM = {};
          this.DOM.el = el;
          this.options = extend({}, this.options);
          extend(this.options, options);
          this._init();
        }



        TiltFx.prototype.options = {
          movement: {
            imgWrapper : {
              translation : {x: 0, y: 0, z: 0},
              rotation : {x: -5, y: 5, z: 0},
              reverseAnimation : {
                duration : 1200,
                easing : 'easeOutElastic',
                elasticity : 600
              }
            },
            lines : {
              translation : {x: 10, y: 10, z: [0,10]},
              reverseAnimation : {
                duration : 1000,
                easing : 'easeOutExpo',
                elasticity : 600
              }
            },
            caption : {
              translation : {x: 20, y: 20, z: 0},
              rotation : {x: 0, y: 0, z: 0},
              reverseAnimation : {
                duration : 1500,
                easing : 'easeOutElastic',
                elasticity : 600
              }
            },
            /*
             overlay : {
                      translation : {x: 10, y: 10, z: [0,50]},
                      reverseAnimation : {
                          duration : 500,
                          easing : 'easeOutExpo'
                      }
                  },
             */
            shine : {
              translation : {x: 50, y: 50, z: 0},
              reverseAnimation : {
                duration : 1200,
                easing : 'easeOutElastic',
                elasticity: 600
              }
            }
          }
        };

        /**
         * 初始化函数
         */
        TiltFx.prototype._init = function() {
          this.DOM.animatable = {};
          this.DOM.animatable.imgWrapper = this.DOM.el.querySelector('.tilter__figure');
          this.DOM.animatable.lines = this.DOM.el.querySelector('.tilter__deco--lines');
          this.DOM.animatable.caption = this.DOM.el.querySelector('.tilter__caption');
          this.DOM.animatable.overlay = this.DOM.el.querySelector('.tilter__deco--overlay');
          this.DOM.animatable.shine = this.DOM.el.querySelector('.tilter__deco--shine > div');
          this._initEvents();
        };

        /**
         * 初始化绑定事件
         */
        TiltFx.prototype._initEvents = function() {
          let self = this;

          this.mouseenterFn = function() {
            for(let key in self.DOM.animatable) {
              anime.remove(self.DOM.animatable[key]);
            }
          };

          this.mousemoveFn = function(ev) {
            requestAnimationFrame(function() { self._layout(ev); });
          };

          this.mouseleaveFn = function(ev) {
            requestAnimationFrame(function() {
              for(let key in self.DOM.animatable) {
                if(self.options.movement[key] === undefined) {continue;}
                anime({
                  targets: self.DOM.animatable[key],
                  duration: self.options.movement[key].reverseAnimation !== undefined ? self.options.movement[key].reverseAnimation.duration || 0 : 1,
                  easing: self.options.movement[key].reverseAnimation !== undefined ? self.options.movement[key].reverseAnimation.easing || 'linear' : 'linear',
                  elasticity: self.options.movement[key].reverseAnimation !== undefined ? self.options.movement[key].reverseAnimation.elasticity || null : null,
                  scaleX: 1,
                  scaleY: 1,
                  scaleZ: 1,
                  translateX: 0,
                  translateY: 0,
                  translateZ: 0,
                  rotateX: 0,
                  rotateY: 0,
                  rotateZ: 0
                });
              }
            });
          };

          this.DOM.el.addEventListener('mousemove', this.mousemoveFn);
          this.DOM.el.addEventListener('mouseleave', this.mouseleaveFn);
          this.DOM.el.addEventListener('mouseenter', this.mouseenterFn);
        };

        TiltFx.prototype._layout = function(ev) {
          // 鼠标相对于文本元素的定位
          let mousepos = getMousePos(ev),
            // 文本元素滚动.
            docScrolls = {left : document.body.scrollLeft + document.documentElement.scrollLeft, top : document.body.scrollTop + document.documentElement.scrollTop},
            bounds = this.DOM.el.getBoundingClientRect(),
            // 鼠标相对于主文本元素(this.DOM.el)的定位
            relmousepos = { x : mousepos.x - bounds.left - docScrolls.left, y : mousepos.y - bounds.top - docScrolls.top };

          // 动画元素的动作设置
          for(let key in this.DOM.animatable) {
            if( this.DOM.animatable[key] === undefined || this.options.movement[key] === undefined ) {
              continue;
            }
            let t = this.options.movement[key] !== undefined ? this.options.movement[key].translation || {x:0,y:0,z:0} : {x:0,y:0,z:0},
              r = this.options.movement[key] !== undefined ? this.options.movement[key].rotation || {x:0,y:0,z:0} : {x:0,y:0,z:0},
              setRange = function (obj) {
                for(var k in obj) {
                  if( obj[k] === undefined ) {
                    obj[k] = [0,0];
                  }
                  else if( typeof obj[k] === 'number' ) {
                    obj[k] = [-1*obj[k],obj[k]];
                  }
                }
              };

            setRange(t);
            setRange(r);

            let transforms = {
              translation : {
                x: (t.x[1]-t.x[0])/bounds.width*relmousepos.x + t.x[0],
                y: (t.y[1]-t.y[0])/bounds.height*relmousepos.y + t.y[0],
                z: (t.z[1]-t.z[0])/bounds.height*relmousepos.y + t.z[0],
              },
              rotation : {
                x: (r.x[1]-r.x[0])/bounds.height*relmousepos.y + r.x[0],
                y: (r.y[1]-r.y[0])/bounds.width*relmousepos.x + r.y[0],
                z: (r.z[1]-r.z[0])/bounds.width*relmousepos.x + r.z[0]
              }
            };
            if (this.DOM.animatable[key] !== null) {
              this.DOM.animatable[key]['style'].WebkitTransform = this.DOM.animatable[key]['style'].transform = 'translateX('
                + transforms.translation.x + 'px) translateY(' + transforms.translation.y + 'px) translateZ('
                + transforms.translation.z + 'px) rotateX(' + transforms.rotation.x + 'deg) rotateY('
                + transforms.rotation.y + 'deg) rotateZ(' + transforms.rotation.z + 'deg)';
            }
          }
        };

        window.TiltFx = TiltFx;

      })(window);
    },
    computed: { //计算属性不能使用ES6箭头函数
      ...mapState(['isLogin', 'loginUserInfo', 'classifyCardId'])
    },
    watch: {
      /*深度监测repairCategories的属性变化*/
      repairCategories: {
        deep: true,
        handler() {
          //点击的分类卡片父级选中类别(selectedParent)变化时，修改repairInfo中的分类信息(触发视图更新)
          this.$set(this.repairInfo, 'selectedParent', this.repairCategories.selectedParent);
        }
      },

      /*监测dialogVisible值的变化，目的是想解决背景侧移的问题（未解决）*/
      dialogVisible() {
        //console.log(this.$data.dialogVisible);
        if (this.$data.dialogVisible) {
          let w1, w2;
          $(document).find('html').each(function () {
            w1 = $(window).width();
            $(this).addClass('fancybox-lock-test');
            w2 = $(window).width();
            $(this).removeClass('fancybox-lock-test');
            //console.log(w1, w2);
          });
          $(document).find('.header').each(function () {
            $(this).css('padding-right', (w2 - w1) + 'px');
            //$(this).append('<style type="text/css"> .fancybox-margin { margin-right:' + (w2 - w1) + 'px; } </style>');
          });
        } else {}
      }
    },
    methods: {
      /**
       * 点击分类卡片显示对话框函数
       * @param e 点击元素
       */
      dialogDisplay(e) {
        //每次点击分类卡片时清除上一次子级选中类别
        this.repairInfo.selectedChild = '';

        //保存应用上下文信息
        let self = this;

        /*异步处理，根据点击事件获得父级、子级分类信息后才能打开对话框*/
        let getCategories = new Promise((resolve, reject) => {
          //获得点击事件的元素(e.target为当前点击的元素,e.currentTarget为事件绑定元素)
          let $e = $(e.target);
          //console.log($e);
          $e.find('h3').each(function() {
            //获得点击的分类卡片文本信息(即父级选中类别)
            self.repairCategories.selectedParent = $(this).text();
          });

          /*根据点击的分类卡片文本信息获得该分类下子级分类数据*/
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
            //传入模型中的repairCategories.options中
            self.$set(this.$data.repairCategories, 'options', options);
          });

          //改变promise状态
          resolve();
        });

        //待promise状态后，打开对话框
        getCategories.then(() => {
          this.dialogVisible = true;
        });
      },

      /**
       * 报修图片数量限制函数(对应属性:limit="1")
       * @param file      当前上传的图片
       * @param fileList  图片List集合
       */
      repairImageOverLimit(file, fileList) {
        this.$message.error('只能上传1张图片');
      },

      /**
       * 报修图片变化函数
       * @param file
       * @param fileList
       * @returns {boolean}
       */
      repairImageChange(file, fileList) {
        //图片类型
        let isJPGPNG = (file['raw']['type'] === 'image/jpeg' || file['raw']['type'] === 'image/png');
        //图片大小
        let isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPGPNG) {
          this.$message.error('上传头像图片只能是 jpg 或 png 格式!');
          this.fileList = []; //这一步是为了防止提示后依然显示上传的图片
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
          this.fileList = []; //这一步是为了防止提示后依然显示上传的图片
        }

        return isJPGPNG && isLt2M;
      },

      /**
       * 报修图片移除函数
       * @param file      当前上传的图片
       * @param fileList  图片List集合
       * @returns {Promise<MessageBoxData> | *} 确认对话框
       */
      repairImageRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }?`);
      },

      /**
       * 提交图片及表单信息函数
       * @des 这里需要特别说明的是，本身表单的数据是作为图片上传模块的额外参数进行同时提交的，
       *      在后台要进行相应适配(见controller)；并且这里禁止了自动上传，改为手动上传。
       */
      repairInfoSubmit(formName) {
        let self = this;

        /*先验证表单*/
        this.$refs[formName].validate((valid) => {
          if (valid) {
            self.repairInfo.submitTime = new Date();    //添加提交时间
            self.$refs.upload.submit();                 //手动提交
          } else {
            return false;
          }
        });

      },

      /**
       * 提交信息成功函数
       * @param response  后台成功回应
       * @param file      当前上传的图片
       * @param fileList  图片List集合
       */
      repairInfoSubmitSuccess(response, file, fileList) {
        /*在保证后台回应保存信息完成后执行操作*/
        if (response['meta']['message'] === 'ok') {
          this.$refs.upload.clearFiles();             //成功后清除所有上传图片
          this.dialogVisible = false;                 //关闭对话框
          this.$message.success('提交报修信息成功！'); //提交成功通知
          location.reload();
        }
      },

      /**
       * 提交信息失败函数
       * @param err       后台错误回应Error类
       * @param file      当前上传的图片
       * @param fileList  图片List集合
       */
      repairInfoSubmitError(err, file, fileList) {
        err = JSON.parse(err['message']); //错误信息JSON解析
        let errorCode = err['meta']['message'];
        let errorData = err['data'];
        errorMessage(errorCodeParse(errorCode, errorData), this);
      }
    }
  }
</script>

<style lang="scss" rel="stylesheet/scss">
  @import '../common/animate/css/component.css';

  //去除默认上传组件
  input[type="file"] {
    display: none !important;
  }

  //图片缩略显示区域大小
  .el-upload-list--picture .el-upload-list__item {
    width: 80% !important;
  }

  //图片缩略显示文字样式
  a {
    text-decoration: none !important;
  }

  .fancybox-lock-test {
    overflow-y: hidden !important;
  }
</style>
