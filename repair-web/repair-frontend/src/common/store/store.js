import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const state = {
  isLogin: sessionStorage.getItem('userCheck'),                   //登录状态
  loginUserInfo: JSON.parse(sessionStorage.getItem('userInfo')),  //登录的用户信息(这里放的是序列化的值，如果直接在这里解析，在第一次登陆时，通过getter拿到的依旧是序列化的值)
  userUnfinished: JSON.parse(sessionStorage.getItem('userUnfinished')), //未完成报修信息
  classifyCardId: ''                                              //点击的分类卡片Id
};

const getters = {
  /**
   * 获得登录状态
   * @param state 存在的所有状态数据
   * @returns {*} 登录状态
   */
  getLogin: state => {
    return state.isLogin;
  },

  /**
   * 获得登录的用户信息
   * @param state 存在的所有状态数据
   * @returns {*} 登录的用户信息
   */
  getLoginUserInfo: state => {
    return state.loginUserInfo;
  },

  /**
   * 获得点击的分类卡片Id
   * @param state       存在的所有状态数据
   * @returns {null|*}  点击的分类卡片Id
   */
  getClassifyCardId: state => {
    return state.classifyCardId
  },

  /**
   * 获得未完成的报修信息
   * @param state     存在的所有状态数据
   * @returns {Array} 未完成的报修信息
   */
  getUserUnfinished: state => {
    return state.userUnfinished
  }
};

const mutations = {
  /**
   * 登录状态改变中转
   * @param state   存在的所有状态数据
   * @param status  改变的登录状态值
   */
  changeLogin(state, status) {
    state.isLogin = status;
  },

  /**
   * 登陆的用户信息保存中转
   * @param state     存在的所有状态数据
   * @param userInfo  待保存的用户信息
   */
  changeLoginUserInfo(state, userInfo) {
    state.loginUserInfo = userInfo
  },

  /**
   * 点击的分类卡片Id改变中转
   * @param state
   * @param id
   */
  changeClassifyCardId(state, id) {
    state.classifyCardId = id
  },

  /**
   * 未完成的报修信息改变中转
   * @param state           存在的所有状态数据
   * @param userUnfinished  未完成的报修信息
   */
  changeUserUnfinished(state, userUnfinished) {
    state.userUnfinished = userUnfinished
  }
};

const actions = {
  /**
   * 登录状态改变行为
   * @param commit  提交的状态值改变请求
   * @param status  改变的登录状态值
   */
  loginAction({commit}, status) {
    commit('changeLogin', status);
  },

  /**
   * 保存用户信息行为
   * @param commit    提交的状态值改变请求
   * @param userInfo  待保存的用户信息
   */
  storeUserAction({commit}, userInfo) {
    commit('changeLoginUserInfo', userInfo)
  },

  /**
   * 改变点击的分类卡片Id行为
   * @param commit    提交的状态值改变请求
   * @param id        点击的分类卡片Id
   */
  changeClassifyCardIdAction({commit}, id) {
    commit('changeClassifyCardId', id)
  },

  /**
   * 保存未完成的报修信息行为
   * @param commit          提交的状态值改变请求
   * @param userUnfinished  未完成的报修信息
   */
  storeUserUnfinishedAction({commit}, userUnfinished) {
    commit('changeUserUnfinished', userUnfinished)
  }

};

export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations
})
