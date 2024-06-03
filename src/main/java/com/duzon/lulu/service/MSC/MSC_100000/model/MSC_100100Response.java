package com.duzon.lulu.service.MSC.MSC_100000.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MSC_100100Response {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetEnds {
        @Builder.Default
        private long exmn_opnn_sqno = -1;
        private String exmn_dvcd;
        private String ends_exmn_dvcd;
        private String exmn_opnn_titl;
        private String exmn_opnn_cnts;
        @Builder.Default
        private List<EndsDetl> ends_detl_list = new ArrayList<>();

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class EndsDetl {
            private long exmn_opnn_detl_sqno;
            private String obsr_opnn;
            private String obsr_opnn_cnts;
            private List<String> obsr_opnn_site_1;
            private String obsr_opnn_site_2;
            private List<String> advc_matr;
            private String advc_matr_cnts;
            private String etnl_obsr_opnn;
            private String dre_opnn;
            private String rslt_opnn_1;
            private String rslt_opnn_2;
            private String rslt_opnn_3;
            private String cncr_mdex_advc_matr;
        }

        public static MSC_100100Response.GetEnds fromMSC100100EndsModelList(List<MSC_100100OpnnEndsModel> list) {
            if (list.size() == 0) return null;
            MSC_100100OpnnEndsModel item = list.get(0);
            return GetEnds.builder()
                    .exmn_opnn_sqno(item.getExmn_opnn_sqno())
                    .exmn_dvcd(item.getExmn_dvcd())
                    .ends_exmn_dvcd(item.getEnds_exmn_dvcd())
                    .exmn_opnn_titl(item.getExmn_opnn_titl())
                    .exmn_opnn_cnts(item.getExmn_opnn_cnts())
                    .ends_detl_list(list.stream().map(MSC_100100Response.GetEnds::fromMSC100100OpnnEndsModel).collect(Collectors.toList()))
                    .build();
        }

        public static MSC_100100Response.GetEnds.EndsDetl fromMSC100100OpnnEndsModel(MSC_100100OpnnEndsModel param) {
            return EndsDetl.builder()
                    .exmn_opnn_detl_sqno(param.getExmn_opnn_detl_sqno())
                    .obsr_opnn(param.getObsr_opnn())
                    .obsr_opnn_cnts(param.getObsr_opnn_cnts())
                    .obsr_opnn_site_1(param.getObsr_opnn_site_1() == null || "".equals(param.getObsr_opnn_site_1())
                            ? new ArrayList<>()
                            : Arrays.stream(param.getObsr_opnn_site_1().split("\\|")).collect(Collectors.toList()))
                    .obsr_opnn_site_2(param.getObsr_opnn_site_2())
                    .advc_matr(
                            param.getAdvc_matr() == null || "".equals(param.getAdvc_matr())
                                ? new ArrayList<>()
                                : Arrays.stream(param.getAdvc_matr().split("\\|")).collect(Collectors.toList()))
                    .advc_matr_cnts(param.getAdvc_matr_cnts())
                    .etnl_obsr_opnn(param.getEtnl_obsr_opnn())
                    .dre_opnn(param.getDre_opnn())
                    .rslt_opnn_1(param.getRslt_opnn_1())
                    .rslt_opnn_2(param.getRslt_opnn_2())
                    .rslt_opnn_3(param.getRslt_opnn_3())
                    .cncr_mdex_advc_matr(param.getCncr_mdex_advc_matr())
                    .build();
        }
    }
}
