package cn.edu.ahu.repairbackend.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author: WangMiao
 * Date: 2018/5/11
 * Time: 15:19
 * Created with IntelliJ IDEA
 * Description: 订单号生成类
 */
public class CreateOrderNumber {

    /**
     * 订单号生成方法
     * @param date  系统时间
     * @param id    用户id
     * @return      23位订单号
     */
    public static String createOrderNumber(Date date, Integer id) {
        Random random = new Random();
        Integer randomNumber = random.nextInt(900) + 100;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        //订单号格式：14位格式化时间(19700101000000)+6位用户id(补零)+3位随机数
        return simpleDateFormat.format(date) + String.format("%06d", id) + randomNumber.toString();
    }

}
