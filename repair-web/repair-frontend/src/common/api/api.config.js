export function apiBase() {
  let hostName = window.location.hostname,
    API_BASE_URL = 'http://localhost:8080';
  if (hostName === 'localhost') {
    API_BASE_URL = 'http://localhost:8080';
  } else if (hostName === '101.76.160.197') {
    API_BASE_URL = 'http://101.76.160.197:8080'
  }

  return API_BASE_URL;
}

/**
 * 公共API接口
 * @constructor
 */
export function COMMON_API() {

}
