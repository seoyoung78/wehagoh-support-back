package com.duzon.lulu.service.MSC.MSC_050000.model;

import com.duzon.lulu.service.MSC.common.model.Exam.DetailQueryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TabDetailRequest extends DetailQueryKey {
    private String tabIndex;

    // Constructor
    public TabDetailRequest(String pid, String prsc_date, String prsc_sqno, String tabIndex) {
        super(pid, prsc_date, prsc_sqno);
        this.tabIndex = tabIndex;
    }
}
