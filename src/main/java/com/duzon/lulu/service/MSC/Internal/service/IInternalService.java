package com.duzon.lulu.service.MSC.Internal.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.Internal.model.*;

public interface IInternalService {
    LuluResult getDidPatientList(DidParam param) throws Exception;
}
