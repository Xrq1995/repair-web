import {apiBase} from '../api.config';

export function REPAIR() {
  return {
    'CATEGORIES': { //报修分类信息接口
      all: apiBase() + '/categories/all',     //获得所有分类信息接口
      child: apiBase() + '/categories/child'  //根据父级分类信息获得子级分类信息接口
    },
    'TIMEPICKER': { //报修预约时间信息接口
      all: apiBase() + '/timePicker/all'      //获得所有预约维修时间段信息接口
    },
    'SUBMIT': {     //报修信息提交接口
      repairInfo: apiBase() + '/repairInfo/submit',                   //提交报修信息接口
      edit: apiBase() + '/infoPlatform/editRepairCur',                //提交信息平台编辑信息接口
      confirm: apiBase() + '/infoPlatform/confirmRepairCur',          //确认报修信息接口
      cancel: apiBase() + '/infoPlatform/cancelRepairCur',            //取消报修信息接口
      confirmList: apiBase() + '/infoPlatform/confirmRepairCurList',  //批量确认报修信息接口
      rollBack: apiBase() + '/infoPlatform/rollBackRepairInfo',       //撤回报修信息状态接口
      finish: apiBase() + '/infoPlatform/finishRepairCur',            //完成报修信息接口
      cancelStu: apiBase() + '/repairInfo/cancelRepair'               //学生端取消报修信息接口
    },
    'INFO': {   //报修信息获取接口
      unfinished: apiBase() + '/repairInfo/userUnfinished',           //获取用户未完成报修信息接口
      all: apiBase() + '/repairInfo/all',                             //获取所有报修信息接口
      cur: apiBase() + '/infoPlatform/getAllRepairCur',               //获取所有当前未确认报修信息接口
      curConfirm: apiBase() + '/infoPlatform/getAllRepairCurConfirm', //获取所有当前已确认报修信息接口
      log: apiBase() + '/infoPlatform/getAllRepairLog',               //获取所有历史报修信息接口
    }
  }
}
