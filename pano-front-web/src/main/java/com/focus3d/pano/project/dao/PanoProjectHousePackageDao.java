package com.focus3d.pano.project.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoProjectHousePackageModel;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoProjectHousePackageDao extends CommonDao<PanoProjectHousePackageModel> {
	private static final String LIST_BY_PROJECT = "c_pano_project_house_package.listByProject";
	/**
	 * 
	 * *
	 * @param projectSn
	 * @return
	 */
	public List<PanoProjectHousePackageModel> listByProject(long projectSn, long houseSn, long styleSn){
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("projectSn", projectSn);
		parameter.put("houseSn", houseSn);
		parameter.put("styleSn", styleSn);
		try {
			return getSqlMapClient().queryForList(LIST_BY_PROJECT, parameter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
} 
