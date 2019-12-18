package com.t31.app.service.developer;

import com.t31.app.entity.DataDictionaryDTO;

import java.util.List;

public interface DataDictionaryService {
            //状态列表
            public List<DataDictionaryDTO> selectStatusList();
            //平台列表
            public List<DataDictionaryDTO> selectFlatFormList();


    public int selByCodeAndTypeName(String statusName);
}
