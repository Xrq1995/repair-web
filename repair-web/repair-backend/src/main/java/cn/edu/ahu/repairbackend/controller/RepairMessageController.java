package cn.edu.ahu.repairbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: WangMiao
 * Date: 2018/1/2
 * Time: 14:21
 * Created with IntelliJ IDEA
 * Description: 维修消息推送控制类
 */
@RestController
public class RepairMessageController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RepairMessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

}
