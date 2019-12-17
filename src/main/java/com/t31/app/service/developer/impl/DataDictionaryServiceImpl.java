package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DataDictionaryDAO;
import com.t31.app.entity.DataDictionaryDTO;
import com.t31.app.service.developer.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Autowired
    private DataDictionaryDAO dataDictionaryDAO;
    private final String statusCode="APP_STATUS";
    private final String flatFormCode = "APP_FLATFORM";
    @Override
    public List<DataDictionaryDTO> selectStatusList() {
        return dataDictionaryDAO.selectDictionaryList(statusCode);
    }

    public List<DataDictionaryDTO> selectFlatFormList(){
        return dataDictionaryDAO.selectDictionaryList(flatFormCode);
    }
}
