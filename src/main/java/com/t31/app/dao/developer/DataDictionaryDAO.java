package com.t31.app.dao.developer;

import com.t31.app.entity.DataDictionaryDTO;

import java.util.List;

public interface DataDictionaryDAO {
        public List<DataDictionaryDTO> selectAppStatusList();
}
