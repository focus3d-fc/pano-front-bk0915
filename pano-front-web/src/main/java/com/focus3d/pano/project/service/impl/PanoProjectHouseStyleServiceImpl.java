package com.focus3d.pano.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProjectHouseStyleModel;
import com.focus3d.pano.project.dao.PanoProjectHouseStyleDao;
import com.focus3d.pano.project.service.PanoProjectHouseStyleService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProjectHouseStyleServiceImpl extends CommonServiceImpl<PanoProjectHouseStyleModel> implements PanoProjectHouseStyleService<PanoProjectHouseStyleModel> {
	@Autowired
	private PanoProjectHouseStyleDao houseStyleDao;
	@Override
	public CommonDao<PanoProjectHouseStyleModel> getDao() {
		return houseStyleDao;
	}

}
