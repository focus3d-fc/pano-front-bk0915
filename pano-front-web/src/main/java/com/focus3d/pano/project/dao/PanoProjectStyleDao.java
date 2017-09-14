package com.focus3d.pano.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoProjectStyleModel;
import com.focus3d.pano.model.ibator.PanoProjectStyleCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoProjectStyleDao extends CommonDao<PanoProjectStyleModel> {
	/**
	 * 
	 * *
	 * @param projectSn
	 * @return
	 */
	public List<PanoProjectStyleModel> listByProject(long projectSn) {
		PanoProjectStyleCriteria criteria = new PanoProjectStyleCriteria();
		criteria.createCriteria().andProjectSnEqualTo(projectSn);
		return selectByCriteria(criteria, PanoProjectStyleModel.class);
	}

}
