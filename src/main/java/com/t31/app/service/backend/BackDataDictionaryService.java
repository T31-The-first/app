package com.t31.app.service.backend;

import com.t31.app.entity.DataDictionaryDTO;

import java.util.List;

/**
 * description: BackDataDictionaryService
 * date: 2019/12/16 19:16
 * author: 周吉涛
 * version: 1.0
 */
public interface BackDataDictionaryService {
    public List<DataDictionaryDTO> selByTypeCode(String typeCode);
}
