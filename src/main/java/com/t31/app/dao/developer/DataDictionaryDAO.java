package com.t31.app.dao.developer;

import com.t31.app.entity.DataDictionaryDTO;

import java.util.List;

public interface DataDictionaryDAO {
        //查询状态列表
        public List<DataDictionaryDTO> selectDictionaryList(String dictionaryCode);

        public int selByCodeAndTypeName(String statusName);
}
