package com.duzon.lulu.service.MSC.MSC_060000.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.MSC_060000.model.PatientQueryParams;
import com.duzon.lulu.service.MSC.MSC_060000.model.PrintPatientsDate;
import com.duzon.lulu.service.MSC.MSC_060000.model.SelectedPatient;
import com.duzon.lulu.service.MSC.MSC_060000.model.SelectedPatients;

public interface IMSC_060200Service {
    LuluResult selectPatientList(PatientQueryParams param);
    LuluResult selectPrscList(SelectedPatient param);
    LuluResult selectPrintPatientList(PrintPatientsDate param);
    LuluResult selectPrintMdtr(SelectedPatients param);
}
