package com.duzon.lulu.service.MSC.ExternalInterface.Saerom.controller;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.ExternalInterface.Saerom.service.ISaeromService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Slf4j
@HealthRestController("/saerom")
public class SaeromController {

    @Autowired
    private ISaeromService iSaeromService;

    /** 접수 정보 조회 */
    @PostMapping("/getExmprsList")
    public Object getExmprsList(@RequestBody HashMap<String, String> sParam) throws Exception {
        Object result = iSaeromService.getExmprsList(sParam);
        return result;
    }

    /** 검사 결과 조회 2 */
    @PostMapping("/getLmerstList")
    public Object getLmerstList(@RequestBody HashMap<String, String> sParam) throws Exception {
        Object result = iSaeromService.getLmerstList(sParam);
        return result;
    }

    /** 검사 결과 업데이트 */
    @PostMapping("/setLmerstData")
    public Object setLmerstData(@RequestBody HashMap<String, String> sParam) throws Exception {
        Object result = iSaeromService.setLmerstData(sParam);
        return result;
    }

    /**
     * 검사 결과 조회
     * @author khgkjg12 강현구A
     * @date Apr 16, 2024
     * @param param pid, rcpn_no
     * @return code, rlst(오타아님)
     */
   @PostMapping("/rtrvRptgList")
   public LuluResult rtrvRptgList(@RequestBody HashMap<String, Object> param) {
	   return iSaeromService.rtrvRptgList(param);
   }
}
