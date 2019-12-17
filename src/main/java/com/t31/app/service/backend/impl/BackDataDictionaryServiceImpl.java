package com.t31.app.service.backend.impl;

import com.t31.app.dao.backend.BackDataDictionaryDao;
import com.t31.app.entity.DataDictionaryDTO;
import com.t31.app.service.backend.BackDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description: DataDictionaryServiceImpl
 * date: 2019/12/16 19:22
 * author: 周吉涛
 * version: 1.0
 */
@Service
@Transactional
public class BackDataDictionaryServiceImpl implements BackDataDictionaryService {
    @Autowired
    private BackDataDictionaryDao dictionaryDao;


    @Override
    public List<DataDictionaryDTO> selByTypeCode(String typeCode) {
        return dictionaryDao.selByTypeCode(typeCode);
    }
}
