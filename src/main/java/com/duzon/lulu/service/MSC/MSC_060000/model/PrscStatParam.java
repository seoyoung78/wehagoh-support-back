package com.duzon.lulu.service.MSC.MSC_060000.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class PrscStatParam {
    private List<HashMap<String, Object>> detailsList;
    private String type;
}
