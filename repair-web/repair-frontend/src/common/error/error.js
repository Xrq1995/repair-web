/**
 * 异常码解析
 * @param errorCode   异常码
 * @param errorData   异常数据
 * @returns {{}}  异常信息
 */
export function errorCodeParse(errorCode, errorData) {
  let errorInfo = {};
  errorInfo.errorMessage = '';
  errorInfo.errorLabel = errorData;

  switch (errorCode) {
    case '1001':
      errorInfo.errorMessage = '该用户不存在！';
      break;
    case '1002':
      errorInfo.errorMessage = '用户名或密码错误！';
      break;
    case '1003':
      errorInfo.errorMessage = '该用户异常状态，请联系管理员！';
      break;
    case '2001':
      errorInfo.errorMessage = '上传图片出错，请重试！';
    default:
      break;
  }

  return errorInfo;
}

export function errorMessageDisplay(errorInfo, obj) {
  obj.text(errorInfo['errorMessage']);
}

export function errorMessage(errorInfo, self) {
  self.$message.error(errorInfo['errorMessage']);
}

export function errorInfo(errorInfo, self) {
  self.$message.error(errorInfo);
}
