package com.focus3d.pano.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProjectStyleModel;
import com.focus3d.pano.project.dao.PanoProjectStyleDao;
import com.focus3d.pano.project.service.PanoProjectStyleService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProjectStyleServiceImpl extends CommonServiceImpl<PanoProjectStyleModel> implements PanoProjectStyleService<PanoProjectStyleModel> {
	@Autowired
	private PanoProjectStyleDao projectStyleDao;
	@Override
	public CommonDao<PanoProjectStyleModel> getDao() {
		return projectStyleDao;
	}
	@Override
	public List<PanoProjectStyleModel> listByProject(long projectSn) {
		return projectStyleDao.listByProject(projectSn);
	}


}
