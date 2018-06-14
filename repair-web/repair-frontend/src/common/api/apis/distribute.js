import {apiBase} from '../api.config';

export function DISTRIBUTE() {
  return {
    'INFO': {   //报修信息获取接口
      curConfirm: apiBase() + '/distribute/getAllRepairCurConfirm',       //获取所有当前已确认报修信息接口
      curDistribute: apiBase() + '/distribute/getAllRepairCurDistribute', //获取所有当前已派发报修信息接口
      curFinish: apiBase() + '/distribute/getAllRepairFinish',            //获取所有当前已完成报修信息接口
      allWorkers: apiBase() + '/distribute/allWorkers',                   //获取所有维修工信息(按工种分类)接口
      rollBack: apiBase() + '/distribute/rollBack',                       //撤回当前维修信息状态接口
      finish: apiBase() + '/distribute/finishRepairCur',                  //完成当前维修信息接口
    },
    'SUBMIT': {
      distribute: apiBase() + '/distribute/distributeRepairCur',          //派发订单接口
    }
  }
}
