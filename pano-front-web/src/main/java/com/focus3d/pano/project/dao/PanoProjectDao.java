package com.focus3d.pano.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.model.ibator.PanoProjectCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoProjectDao extends CommonDao<PanoProjectModel> {

	public List<PanoProjectModel> list() {
		PanoProjectCriteria criteria = new PanoProjectCriteria();
		criteria.createCriteria().andPublishEqualTo(1);
		return selectByCriteria(criteria, PanoProjectModel.class);
	}

}
