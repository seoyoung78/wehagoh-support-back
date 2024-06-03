package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Map;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;
import com.duzon.lulu.service.MSC.util.MSLC.PkModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author khgkjg12 강현구A
 * 
 *         검사처방 pk 모델
 */
@Setter
@Getter
@NoArgsConstructor
public class ExmnPrscPkModel extends PkModel {

	private String pid;
	private String prsc_date;
	private long prsc_sqno;

	@Override
	public void set(Map<String, Object> param) {
		prsc_date = param.get("prsc_date").toString();
		prsc_sqno = (long) param.get("prsc_sqno");
		pid = param.get("pid").toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExmnPrscPkModel))
			return false;
		ExmnPrscPkModel dst = (ExmnPrscPkModel) o;
		if (prsc_date == null || prsc_sqno < 1 || pid == null || dst.getPrsc_date() == null || dst.getPrsc_sqno() < 1
				|| dst.getPid() == null)
			return false;
		return prsc_date.equals(dst.getPrsc_date()) && prsc_sqno == dst.getPrsc_sqno() && pid.equals(dst.getPid());
	}

	@Override
	public int hashCode() {
		if (prsc_date == null || prsc_sqno < 1 || pid == null)
			return super.hashCode();
		return (pid + "/" + prsc_date + "/" + prsc_sqno).hashCode();
	}

	public ExmnPrscPkModel(String pid, String prsc_date, long prsc_sqno) {
		this.pid = pid;
		this.prsc_date = prsc_date;
		this.prsc_sqno = prsc_sqno;
	}

	@Override
	public void set(Object... param) {
		this.pid = MSLCUtil.parseString(param[0]);
		this.prsc_date = MSLCUtil.parseString(param[1]);
		this.prsc_sqno = MSLCUtil.parseLong(param[2]);

	}

	@Override
	public String rtrvModelName() {
		return "PRSC";
	}
}
