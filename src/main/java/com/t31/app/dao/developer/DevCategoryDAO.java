package com.t31.app.dao.developer;

import com.t31.app.entity.AppCategoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevCategoryDAO {
        //查询一级菜单
        //查询二级菜单
        //查询三级菜单
        public List<AppCategoryDTO> selectCategoryLevel(@Param("parendId") int parendId);
}
