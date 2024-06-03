package com.duzon.lulu.service.MSC.MSC_090000.controller;


import com.duzon.common.controller.LuluBaseController;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_090000.service.IMSC_090100Service;
import com.duzon.lulu.service.MSC.MSC_090000.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@HealthRestController("/MSC_090100")
public class MSC_090100Controller extends LuluBaseController {
    @Autowired
    private IMSC_090100Service imsc_090100Service;


    /** 트리 조회 */
    @PostMapping("/selectTreeList")
    public Object selectTreeList() throws Exception { return this.imsc_090100Service.selectTreeList(); }

    /** 검체종류, 검체용기 조회 */
    @PostMapping("/selectCommonData")
    public Object selectCommonData() throws Exception { return this.imsc_090100Service.selectCommonData(); }

    /**
     * slip_cd에 속한 처방 리스트 조회
     * */
    @PostMapping("/selectPrscList")
    public Object selectPrscList(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.imsc_090100Service.selectPrscList(param);
    }

    /**
     * AutoComplete
     */
    @PostMapping("/selectSearchList")
    public Object selectSearchList(@RequestBody HashMap<String, Object> param) throws Exception {
        return imsc_090100Service.selectSearchList(param);
    }

    /**
     * 우측 폼 데이터 조회
     * */
    @PostMapping("/selectForm")
    public Object selectFrom(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.imsc_090100Service.selectForm(param);
    }


    /**
     * 검사명, 검사코드 자동완성 검색
     * */
    @PostMapping("/selectExmnList")
    public Object selectExmnList(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.imsc_090100Service.selectExmnList(param);
    }

    /**
     * 우측 폼 데이터 업데이트(처방코드마스터 업데이트)
     * */
    @PostMapping("/save")
    public Object save(@RequestBody Form param) throws Exception {
        return this.imsc_090100Service.save(param);
    }

    /**
     * 우측 폼 데이터 삭제(검사결과 서용 여부가 'Y'인 처방코드에 대해 삭제)
     * */
    @PostMapping("/delete")
    public Object delete(@RequestBody Form param) throws Exception {
        return this.imsc_090100Service.delete(param);
    }

    /**
     * 검사 참고치/CVR 조회
     */
    @PostMapping("/selectRefCvrList")
    public Object selectRefCvrList(@RequestBody HashMap<String, Object> param) throws Exception {
        return this.imsc_090100Service.selectRefCvrList(param);
    }

    @PostMapping("/saveRefCvr")
    public Object saveRefCvr(@RequestBody RefCvrParam param) throws Exception { return this.imsc_090100Service.saveRefCvr(param); }

    @PostMapping("/selectUnitList")
    public Object selectUnitList(@RequestBody HashMap<String, Object> param) throws Exception { return this.imsc_090100Service.selectUnitList(param); }

    @PostMapping("/saveUnit")
    public Object saveUnit(@RequestBody UnitParam param) throws Exception { return this.imsc_090100Service.saveUnit(param); }
}
