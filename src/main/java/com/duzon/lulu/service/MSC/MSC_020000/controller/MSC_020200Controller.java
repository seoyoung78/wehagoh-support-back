package com.duzon.lulu.service.MSC.MSC_020000.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.MSC_020000.service.IMSC_020200Service;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCController;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCParamWrapper;

import lombok.RequiredArgsConstructor;

@HealthRestController("/MSC_020200")
@RequiredArgsConstructor
public class MSC_020200Controller extends MSLCController {

	private final IMSC_020200Service iMSC_020200ServiceImpl;

	@PostMapping("/rtrvEntsInstList")
	public LuluResult rtrvEntsInstList() throws Exception {
		return iMSC_020200ServiceImpl.rtrvEntsInstList();
	}

	/* 위탁검사처방 조회 */
	@PostMapping("/rtrvEntsExmnPrscList")
	public LuluResult rtrvEntsExmnPrscList(@RequestBody HashMap<String, Object> param) {
		return iMSC_020200ServiceImpl.rtrvEntsExmnPrscList(param);
	}

	/* 위탁검사처방 전송 */
	@PostMapping("/trmsEntsExmnPrsc")
	public LuluResult trmsEntsExmnPrsc(@RequestBody MSLCParamWrapper param) {
		return iMSC_020200ServiceImpl.trmsEntsExmnPrsc(param.getMslcMapList());
	}

	/* 위탁검사처방 전송 취소 */
	@PostMapping("/cnclTrmsEntsExmnPrsc")
	public LuluResult cnclTrmsEntsExmnPrsc(@RequestBody MSLCParamWrapper param) {
		return iMSC_020200ServiceImpl.cnclTrmsEntsExmnPrsc(param.getMslcMapList());
	}

	/* 위탁검사처방 회신 */
	@PostMapping("/rplyEntsExmnPrsc")
	public Object rplyEntsExmnPrsc() {
		return iMSC_020200ServiceImpl.rplyEntsExmnPrsc();
	}

}
