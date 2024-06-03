package com.duzon.lulu.service.MSC.MSC_100000.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MSC_100100Request {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchOpnn {
        private String keyword;
        private String opnnType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SaveGsspOpnn {
        private long exmn_opnn_sqno;
        private String exmn_dvcd;
        private String ends_exmn_dvcd;
        private String exmn_opnn_titl;
        private String exmn_opnn_cnts;

        private List<MSC_100100ObsrOpnn> obsr_opnn_list;
        private List<String> advc_matr;
        private String advc_matr_cnts;
        private String cncr_mdex_advc_matr;

        public MSC_100100ExmnAndEndsDetlList toMSC_100100ExmnAndEndsDetlList() {
            return MSC_100100ExmnAndEndsDetlList.builder()
                    .exmn_opnn_sqno(getExmn_opnn_sqno())
                    .exmn_dvcd(getExmn_dvcd())
                    .ends_exmn_dvcd(getEnds_exmn_dvcd())
                    .exmn_opnn_titl(getExmn_opnn_titl())
                    .exmn_opnn_cnts(getExmn_opnn_cnts())
                    .ends_detl_list(getObsr_opnn_list().stream()
                            .map(requestListItem -> requestListItem.toMSC_100100ObsrOpnn(getAdvc_matr(), getAdvc_matr_cnts(), getCncr_mdex_advc_matr() ))
                            .collect(Collectors.toList()))
                    .build();
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class MSC_100100ObsrOpnn {
            private long exmn_opnn_detl_sqno;
            private String obsr_opnn_cnts;
            private List<String> obsr_opnn_site_1;
            private String obsr_opnn;

            public MSC_100100EndsDetlModel toMSC_100100ObsrOpnn(List<String> advc_matr, String advc_matr_cnts, String cncr_mdex_advc_matr) {
                return MSC_100100EndsDetlModel.builder()
                        .exmn_opnn_detl_sqno(getExmn_opnn_detl_sqno())
                        .obsr_opnn(getObsr_opnn())
                        .obsr_opnn_cnts(getObsr_opnn_cnts())
                        .obsr_opnn_site_1(getObsr_opnn_site_1() == null || getObsr_opnn_site_1().size() == 0 ? null : String.join("|", getObsr_opnn_site_1()))
                        .advc_matr(advc_matr == null || advc_matr.size() == 0 ? null : String.join("|", advc_matr))
                        .advc_matr_cnts(advc_matr_cnts)
                        .cncr_mdex_advc_matr(cncr_mdex_advc_matr)
                        .build();
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveClnsOpnn {
        private long exmn_opnn_sqno;
        private String exmn_opnn_titl;
        private String exmn_dvcd;
        private String ends_exmn_dvcd;
        private long exmn_opnn_detl_sqno;
        private String etnl_obsr_opnn;
        private String dre_opnn;
        private String obsr_opnn_cnts;
        private String obsr_opnn_site_2;
        private String rslt_opnn_1;
        private String rslt_opnn_2;
        private String rslt_opnn_3;
        private String advc_matr_cnts;
        private String cncr_mdex_advc_matr;

        public MSC_100100ExmnAndEndsDetlList toMSC_100100ExmnAndEndsDetlList() {
            List<MSC_100100EndsDetlModel> endsDetlList = new ArrayList();
            MSC_100100EndsDetlModel endsDetl = MSC_100100EndsDetlModel.builder()
                    .exmn_opnn_detl_sqno(getExmn_opnn_detl_sqno())
                    .obsr_opnn_cnts(getObsr_opnn_cnts())
                    .obsr_opnn_site_2(getObsr_opnn_site_2())
                    .advc_matr_cnts(getAdvc_matr_cnts())
                    .etnl_obsr_opnn(getEtnl_obsr_opnn())
                    .dre_opnn(getDre_opnn())
                    .rslt_opnn_1(getRslt_opnn_1())
                    .rslt_opnn_2(getRslt_opnn_2())
                    .rslt_opnn_3(getRslt_opnn_3())
                    .cncr_mdex_advc_matr(getCncr_mdex_advc_matr())
                    .build();
            endsDetlList.add(endsDetl);
            return MSC_100100ExmnAndEndsDetlList.builder()
                    .exmn_opnn_sqno(getExmn_opnn_sqno())
                    .exmn_dvcd(getExmn_dvcd())
                    .ends_exmn_dvcd(getEnds_exmn_dvcd())
                    .exmn_opnn_titl(getExmn_opnn_titl())
                    .exmn_opnn_cnts(null)
                    .ends_detl_list(endsDetlList)
                    .build();
        }
    }
}
