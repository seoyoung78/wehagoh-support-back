package com.duzon.lulu.service.MSC.MSC_100000.service;

import com.duzon.lulu.service.MSC.MSC_100000.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IMSC_100100Service {
    List<MSC_100100ExmnDvcdModel> getExmnDvcd();

    List<MSC_100100OpnnModel> getOpnnList();

    List<MSC_100100OpnnModel> searchOpnn(MSC_100100Request.SearchOpnn param);

    MSC_100100OpnnModel getOpnn(long exmn_opnn_sqno);

    MSC_100100Response.GetEnds getEnds(long exmn_opnn_sqno);

    long saveExmnOpnn(MSC_100100OpnnModel param);

    long saveEndsOpnn(MSC_100100ExmnAndEndsDetlList param);

    int editExmnOpnn(MSC_100100OpnnModel param);

    int editEndsOpnn(MSC_100100ExmnAndEndsDetlList param);

    int deleteExmnOpnn(long exmn_opnn_sqno);

    int deleteEndsOpnn(long exmn_opnn_sqno);
}
