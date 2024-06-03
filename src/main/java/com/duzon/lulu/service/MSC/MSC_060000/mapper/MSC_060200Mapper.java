package com.duzon.lulu.service.MSC.MSC_060000.mapper;

import com.duzon.lulu.service.MSC.MSC_060000.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface MSC_060200Mapper {
    List<Patient> selectPatientList(PatientQueryParams param);

    List<Prescription> selectPrscList(SelectedPatient param);
    List<Prescription> selectPrintPatientList(PrintPatientsDate param);
    List<Prescription> selectPrintMdtr(SelectedPatients param);
}
