package com.duzon.lulu.Logger.service.impl;

import com.duzon.common.model.LogModel;
import com.duzon.common.util.StringUtil;
import com.duzon.lulu.Logger.service.LogMessageService;
import com.duzon.lulu.common.service.WehagoLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * WehagoLogService
 * @author 이용상
 * @since 2017.08.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  
 *
 * </pre>
 */
@Service("logMessageService")
public class LogMessageServiceImpl implements LogMessageService {
	
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private WehagoLogService wehagoLogService;
/*	@Autowired
	private TLogMessageMapper tLogMessageMapper;*/
	@Autowired
	private Properties luluProperties;
	
	@SuppressWarnings("unchecked")
	public void insertLog(LogModel logModel){
		//실제 사용시 아래 주석 해제후 사용
		// Transaction를 완료하기위하여 DB 조회후 비동기 처리
/*		TLogMessageKey tLogMessageKey = new TLogMessageKey();
		tLogMessageKey.setMenu_code(logModel.getMenuCode());
		tLogMessageKey.setAction_code(logModel.getActionCode());
		TLogMessage tLogMessage = tLogMessageMapper.selectByPrimaryKey(tLogMessageKey);
		if (tLogMessage != null){
			
			logModel.setActionCodeText(tLogMessage.getAction_code_text());
			logModel.setLogText(tLogMessage.getLog_text());
			logModel.setLogInfoText(tLogMessage.getLog_info_text());
		}*/
		
		if("ASYNC".equals(StringUtil.fixNull( luluProperties.getProperty("globals.LOG_MESSAGE_SYNC_ASYNC")))){
			logModel = wehagoLogService.setSessionData(logModel);//비동기 처리시 request 만료 가 되므로 처리전 세션값 추출
		}
		wehagoLogService.insertLog(logModel);
	}
	public void insertTestLog(LogModel logModel){
		  
		switch (logModel.getLogCase()) {
			case "INFO" :
				logModel.setActionCodeText("테스트 등록 로그");
				logModel.setLogText("[#sample#]가 등록되었습니다.");
				logModel.setLogInfoText("등록된 서비스명: [#sample#]</br>사용용도 : [#sample_type#]");
				wehagoLogService.insertLog(logModel);
				
				break;
			case "DIFF" :
				logModel.setActionCodeText("테스트 수정 로그");
				logModel.setLogText("[#sample#]가 수정 되었습니다.");
				logModel.setLogInfoText("");
				wehagoLogService.insertLog(logModel);
				
				break;

		}
	}
}
