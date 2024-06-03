package com.duzon.lulu.service.MSC.MSC_020000.model;

import java.util.Map;
import java.util.Objects;

import com.duzon.lulu.service.MSC.util.MSLC.MSLCUtil;
import com.duzon.lulu.service.MSC.util.MSLC.PkModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpcmPkModel extends PkModel {
	private String spcm_no;

	@Override
	public int hashCode() {
		return Objects.hash(spcm_no);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpcmPkModel other = (SpcmPkModel) obj;
		return Objects.equals(spcm_no, other.spcm_no);
	}

	@Override
	public void set(Map<String, Object> param) {
		spcm_no = param.get("spcm_no").toString();
	}

	@Override
	public void set(Object... param) {
		spcm_no = MSLCUtil.parseString(param[0]);
	}

	@Override
	public String rtrvModelName() {
		return "SPCM";
	}
}
