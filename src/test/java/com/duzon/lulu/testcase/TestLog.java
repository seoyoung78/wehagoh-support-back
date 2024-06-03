package com.duzon.lulu.testcase;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.duzon.common.model.LogModel;
import com.duzon.common.model.LuluResult;
import com.duzon.common.model.SelectLogModel;
import com.duzon.common.util.StringUtil;
import com.duzon.common.util.ValidationUtil;
import com.duzon.lulu.common.constants.LogCase;
import com.duzon.lulu.common.constants.WehagoLogCode;
import com.duzon.lulu.common.service.InnerGatewayService;
import com.duzon.lulu.Logger.service.LogMessageService;
import com.duzon.lulu.common.service.WehagoLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/context-*.xml"})
@WebAppConfiguration
public class TestLog {

	@Autowired
	private WehagoLogService wehagoLogService;
	@Autowired
	private InnerGatewayService innerGatewayService;
	@Autowired
	private LogMessageService logMessageService;

	protected Logger logger = LogManager.getLogger(this.getClass());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		HashMap<String, Object> param = new HashMap<String, Object>();
		
		
		/**************************************
		 * 로그 등록
		 * 참고 : http://wiki.duzon.com:8080/pages/viewpage.action?pageId=13735802
		 **************************************/
		// 기본 INFO 등록
/*	//주석 해제후사용
		param.put("sample", "로그등록 테스트");
		param.put("sample_type", "테스트");
		param.put("desc", "테스트설명");
		insertInfoLog(param);
		*/
		param.clear();
		
		
		// 기본 DIFF 등록
/*	//주석 해제후사용
		param.put("before_sample", "로그등록 테스트");
		param.put("before_sample_type", "테스트");
		param.put("before_desc", "테스트설명");
		param.put("sample", "로그수정 테스트");
		param.put("sample_type", "수정 테스트");
		param.put("desc", "테스트설명");
		insertDiffLog(param);
		*/
		
		param.clear();//
		/**************************************
		 * 로그 조회
		 * 참고 : http://wiki.duzon.com:8080/pages/viewpage.action?pageId=13735802
		 **************************************/
//		param.put("menuCode", MenuCode.SAMPLE);
		searchLog(param);
	}

	public LuluResult insertInfoLog(HashMap<String, Object> param){
		LuluResult result = new LuluResult();
		LogModel logModel = new LogModel();
//		logModel.setMenuCode(MenuCode.SAMPLE);//필수
//		logModel.setMenuGroup(MenuGroup.SETTING_GROUP);//선택-그룹 코드가 있을시
//		logModel.setActionCode(ActionCode.TEST_INSERT);//필수
		logModel.setLogCase(LogCase.INFO);//필수
		 
		HashMap logData = new HashMap();
		logData.put("sample", StringUtil.fixNull(param.get("sample")));
		logData.put("sample_type", StringUtil.fixNull(param.get("sample_type")));
		logModel.setData(logData);
		 
		//logMessageService.insertLog(logModel);//실사용
		logMessageService.insertTestLog(logModel);//테스트용
		return result;
	}
	public LuluResult insertDiffLog(HashMap<String, Object> param){
		LuluResult result = new LuluResult();
		LogModel logModel = new LogModel();
//		logModel.setMenuCode(MenuCode.SAMPLE);//필수
//		logModel.setMenuGroup(MenuGroup.SETTING_GROUP);//선택-그룹 코드가 있을시
//		logModel.setActionCode(ActionCode.TEST_UPDATE);//필수
		logModel.setLogCase(LogCase.DIFF);//필수
		
		HashMap logData = new HashMap();
		logData.put("sample", StringUtil.fixNull(param.get("sample")));
		logData.put("sample_type",StringUtil.fixNull(param.get("sample_type")));
		logData.put("desc",StringUtil.fixNull(param.get("desc")));
		logModel.setData(logData);

		HashMap before = new HashMap();
		before.put("sample", StringUtil.fixNull(param.get("before_sample")));
		before.put("sample_type", StringUtil.fixNull(param.get("before_sample_type")));
		before.put("desc", StringUtil.fixNull(param.get("before_desc")));
		logModel.setBeforeData(before);
		
		//logMessageService.insertLog(logModel);//실사용
		logMessageService.insertTestLog(logModel);//테스트용
		return result;
	}
	public LuluResult searchLog(HashMap<String, Object> param){
		LuluResult result = new LuluResult();
		HashMap<String, Object> conditionParam = new HashMap<String, Object>();
		try {
			SelectLogModel selectLogModel = new SelectLogModel();
			selectLogModel.setWehagoIndex(WehagoLogCode.INDEX_WEHAGO_LOGSTASH);//필수
			selectLogModel.setWehagoType(WehagoLogCode.TYPE_SERVICE);//필수

			/**
			 * 기본 검색 조건 셋팅
			 */
			if (ValidationUtil.checkContainsKey("page", param)) {
				selectLogModel.setPage(Integer.parseInt(StringUtil.fixNull(param.get("page"),"1")));// 조회할 페이지
			}
			if (ValidationUtil.checkContainsKey("pageSize", param)) {
				selectLogModel.setPageSize(Integer.parseInt(StringUtil.fixNull(param.get("pageSize"),"1000")));//한 페이지에 조회할 데이터
			}
			if (ValidationUtil.checkContainsKey("orderKey", param)) {
				selectLogModel.setOrderKey(StringUtil.fixNull(param.get("orderKey")));//정렬할 키값 / 필수아님
			}
			if (ValidationUtil.checkContainsKey("order", param)) {
				selectLogModel.setOrder(StringUtil.fixNull(param.get("order")));//정렬방법 / 필수아님 (asc/desc)
			}
			if (ValidationUtil.checkContainsKey("fromDate", param)) {
				selectLogModel.setFromDate(StringUtil.fixNull(param.get("fromDate")));//조회 시작 날짜 / 필수아님 (2017-04-02)
			}
			if (ValidationUtil.checkContainsKey("toDate", param)) {
				selectLogModel.setToDate(StringUtil.fixNull(param.get("toDate")));//조회 종료 날짜 / 필수아님 (2017-04-02)
			}
			/**
			 * 검색어 셋팅 사용자 자유입력 한가지입력 필수
			 */
			if (ValidationUtil.checkContainsKey("actionCode", param)) {
				conditionParam.put("action_code", StringUtil.fixNull(param.get("actionCode")));
			}
			if (ValidationUtil.checkContainsKey("menuGroup", param)) {
				conditionParam.put("menu_group", StringUtil.fixNull(param.get("menuGroup")));
			}
			if (ValidationUtil.checkContainsKey("menuCode", param)) {
				conditionParam.put("menu_code", StringUtil.fixNull(param.get("menuCode")));
			}
			/**
			 * 검색어 추가 셋팅(자유검색)
			 */
/*			conditionParam.put("portal_id", StringUtil.fixNull(param.get("portal_id")));*/

			if(conditionParam.size() > 0){
				
				selectLogModel.setSearchParam(conditionParam);
				List<HashMap<String, Object>> history = wehagoLogService.selectLog(selectLogModel);
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa",Locale.US);
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS",Locale.US);
				SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM",Locale.US);
				SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				List<HashMap<String, Object>> monthHistory = new ArrayList<HashMap<String, Object>>();
				for (int i=0; i < history.size(); i++) {
					HashMap<String, Object> histroyItem = history.get(i);
					try {
						String originalDate = StringUtil.fixNull(histroyItem.get("insert_timestamp"));
						String currentMonth = "";
						String displayDate = "";
						try{
							currentMonth = monthFormat.format(simpleDateFormat.parse(originalDate));
							displayDate = displayDateFormat.format(simpleDateFormat.parse(originalDate));
						}catch (ParseException e) {
							currentMonth = monthFormat.format(simpleDateFormat2.parse(originalDate));
							displayDate = displayDateFormat.format(simpleDateFormat2.parse(originalDate));
						}
						histroyItem.put("yearMonth", currentMonth);
						histroyItem.put("displayDate", displayDate);

						monthHistory.add(histroyItem);
						System.out.println(currentMonth+" / "+displayDate);
						hashMapPrinter(histroyItem);//로그 출력
						System.out.println("########################################################################");
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("총 개수 : "+monthHistory.size());
				result.setResultData(monthHistory);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public void hashMapPrinter(HashMap<String, Object> param){
		String keyAttribute = null;
		Iterator itr = param.keySet().iterator();
		while(itr.hasNext()){
			keyAttribute = (String) itr.next();
			System.out.println(keyAttribute+" : "+StringUtil.fixNull(param.get(keyAttribute)));
		}
	}
}
