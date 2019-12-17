package com.t31.app.dao.backend;

import com.t31.app.entity.DataDictionaryDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: BackDataDictionaryDao
 * date: 2019/12/16 19:13
 * author: 周吉涛
 * version: 1.0
 */
public interface BackDataDictionaryDao {
    public List<DataDictionaryDTO> selByTypeCode(@Param("typeCode") String typeCode);
}
