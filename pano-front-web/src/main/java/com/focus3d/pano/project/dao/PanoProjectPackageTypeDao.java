package com.focus3d.pano.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoProjectPackageTypeModel;
import com.focus3d.pano.model.ibator.PanoProjectPackageTypeCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoProjectPackageTypeDao extends CommonDao<PanoProjectPackageTypeModel> {
	/**
	 * 
	 * *
	 * @param housePackageSn
	 * @return
	 */
	public List<PanoProjectPackageTypeModel> listByHousePackage(long housePackageSn) {
		PanoProjectPackageTypeCriteria criteria = new PanoProjectPackageTypeCriteria();
		criteria.createCriteria().andHousePackageSnEqualTo(housePackageSn);
		return selectByCriteria(criteria, PanoProjectPackageTypeModel.class);
	}

}
