package com.focus3d.pano.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.project.dao.PanoProjectDao;
import com.focus3d.pano.project.service.PanoProjectService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProjectServiceImpl extends CommonServiceImpl<PanoProjectModel> implements PanoProjectService<PanoProjectModel> {
	@Autowired
	private PanoProjectDao projectDao;
	@Override
	public CommonDao<PanoProjectModel> getDao() {
		return projectDao;
	}
	@Override
	public List<PanoProjectModel> list() {
		return projectDao.list();
	}

	
}
