package com.duzon.lulu.service.MSC.MSC_020000.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.duzon.common.mapper.LuluAbstractMapper;
import com.duzon.lulu.service.MSC.MSC_020000.model.CVRNotiDetailModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltHstrModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.ExmnRsltPkModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.IptnRsltParamModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RptgRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.LockSpcmWithEntsRsltModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.RsltSttsModel;
import com.duzon.lulu.service.MSC.MSC_020000.model.SpcmPkModel;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCBatchSupport;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCError;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCRuntimeException;
import com.duzon.lulu.service.MSC.util.MSLC.MSLCSessionHelper;

/**
 * 진단검사 결과 mapper
 * 
 * @author khgkjg12 강현구A
 */
@Repository
public class MSC_020300Mapper extends LuluAbstractMapper {

	private final String NAME_SPACE = "com.duzon.lulu.service.MSC.MSC_020000.mapper.MSC_020300Mapper.";
	private final String CRTN_PRSC_HSTR = NAME_SPACE + "crtnRsltHstr";

	@Autowired
	private MSLCBatchSupport batchSupport;
	@Autowired
	private MSLCSessionHelper session;
	
	//삭제 예정.
	public boolean crtnExmnRsltHstr(Object param) {
		return true;
	}
	

	/**
	 * 진단검사 결과 MST그리드 조회.
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 * @return
	 */
	public List<RsltSttsModel> rtrvRsltSttsList(Map<String, Object> param) {
		return selectList(NAME_SPACE + "rtrvRsltSttsList", param);
	}

	/**
	 * 진단검사 결과 DTL 그리드 조회.
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 * @return
	 */
	public List<ExmnRsltModel> rtrvExmnRsltList(List<String> param) {
		return selectList(NAME_SPACE + "rtrvExmnRsltList", param);
	}

	/**
	 * 검사결과 이력 생성
	 * @author khgkjg12 강현구A
	 * @date Jan 17, 2024
	 * @param param
	 */
	public void crtnRsltHstr(Collection<? extends ExmnRsltPkModel> param) {
		if (insert(CRTN_PRSC_HSTR, session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("crtnRsltHstr"));
		}
	}
	
	public List<LockSpcmWithEntsRsltModel> lockSpcmWithEntsRslt(Collection<? extends SpcmPkModel> param) {
		return selectList(NAME_SPACE+"lockSpcmWithEntsRslt", param);
	}
	

	public List<LockSpcmWithRsltModel> lockSpcmWithRslt(Collection<? extends SpcmPkModel> param) {
		return selectList(NAME_SPACE+"lockSpcmWithRslt", param);
	}

	/**
	 * 검사결과 중간보고.
	 * @author khgkjg12 강현구A
	 * @date Jan 31, 2024
	 * @param param
	 * @return 판독된 검사처방 PK
	 */
	public void iptnRslt(Collection<? extends IptnRsltParamModel> param) {
		if (!batchSupport.updateBatchWithResult(NAME_SPACE + "iptnRslt", session.getBatchParamModel(param), 1)) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("iptnRslt"));
		}
	}
	/**
	 * 검사결과 중간보고.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 31, 2024
	 * @param param
	 * @return
	 */
	public void cnclIptnRslt(Collection<? extends IptnRsltParamModel> param) {
		if (!batchSupport.updateBatchWithResult(NAME_SPACE + "cnclIptnRslt", session.getBatchParamModel(param), 1)) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclIptnRslt"));
		}
	}

	/**
	 * 검사결과 최종보고 sessionForMap으로 감싼 RsltPkModel or Map Collection
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 31, 2024
	 * @param param
	 * @return
	 */
	public List<RptgRsltModel> rptgRslt(Collection<? extends ExmnRsltPkModel> param) {
		List<RptgRsltModel> result = selectList(NAME_SPACE + "rptgRslt", session.getParamModel(param));
		if (result.size() != param.size())
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("rptgRslt"));
		return result;
	}

	/**
	 * 검사결과 최종보고 취소 sessionForMap으로 감싼 RsltPkModel or Map Collection
	 * 
	 * @author khgkjg12 강현구A
	 * @date Jan 31, 2024
	 * @param param
	 * @return
	 */
	public void cnclRptgRslt(Collection<? extends ExmnRsltPkModel> param) {
		if (update(NAME_SPACE + "cnclRptgRslt", session.getParamModel(param)) != param.size())
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclRptgRslt"));
	}

	/* 검사결과 이력 목록 조회 */
	public List<ExmnRsltHstrModel> rtrvExmnRsltHstrList(Map<String, Object> param) {
		return selectList(NAME_SPACE + "rtrvExmnRsltHstrList", param);
	}

	/**
	 * 진료지원 결과 테이블 삽입.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 */
	public void rptgMscRslt(Collection<? extends ExmnRsltPkModel> param) {
		if (insert(NAME_SPACE + "rptgMscRslt", session.getParamModel(param)) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("rptgMscRslt"));
		}
	}

	/**
	 * 진료지원 결과 테이블 삭제.
	 * 
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 */
	public void cnclRptgMscRslt(Collection<? extends ExmnRsltPkModel> param) {
		if (insert(NAME_SPACE + "cnclRptgMscRslt", param) != param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("cnclRptgMscRslt"));
		}
	}


	/**
	 * 검체 테이블 상태를 검사결과 테이블에 맞춤.
	 * @author khgkjg12 강현구A
	 * @date Mar 15, 2024
	 * @param param
	 */
	public void syncSpcm(Collection<? extends SpcmPkModel> param) {
		if(update(NAME_SPACE + "syncSpcm", session.getParamModel(param))!=param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("syncSpcm"));
		}
	}

	/**
	 * 처방 테이블 상태를 검체 테이블에 맞춤.
	 * @author khgkjg12 강현구A
	 * @date Jan 31, 2024
	 * @param param
	 * @return
	 */
	public void syncPrsc(Collection<? extends SpcmPkModel> param) {
		if(update(NAME_SPACE + "syncPrsc", session.getParamModel(param))<param.size()) {
			throw new MSLCRuntimeException(MSLCError.EXCT_IMPB_STAT.withBody("syncPrsc"));
		}
	}

	public List<CVRNotiDetailModel> rtrvCvrNotiDetailList(Collection<? extends ExmnRsltPkModel> param){
		return selectList(NAME_SPACE+"rtrvCvrNotiDetailList", param);
	}
}
