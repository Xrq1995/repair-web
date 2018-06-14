import {apiBase} from '../api.config';

export function USER() {
  return {
    'LOGIN': {  //登录接口
      'login': apiBase() + '/user/login',
      'logout': apiBase()+ '/user/logout'
    }
  }
}
