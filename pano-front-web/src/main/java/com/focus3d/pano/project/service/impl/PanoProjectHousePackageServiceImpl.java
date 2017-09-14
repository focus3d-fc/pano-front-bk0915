package com.focus3d.pano.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProjectBaseStyleModel;
import com.focus3d.pano.model.PanoProjectHouseModel;
import com.focus3d.pano.model.PanoProjectHousePackageModel;
import com.focus3d.pano.model.PanoProjectHouseStyleModel;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.model.PanoProjectPackageModel;
import com.focus3d.pano.model.PanoProjectStyleModel;
import com.focus3d.pano.project.dao.PanoProjectBaseStyleDao;
import com.focus3d.pano.project.dao.PanoProjectDao;
import com.focus3d.pano.project.dao.PanoProjectHouseDao;
import com.focus3d.pano.project.dao.PanoProjectHousePackageDao;
import com.focus3d.pano.project.dao.PanoProjectHouseStyleDao;
import com.focus3d.pano.project.dao.PanoProjectPackageDao;
import com.focus3d.pano.project.dao.PanoProjectStyleDao;
import com.focus3d.pano.project.service.PanoProjectHousePackageService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProjectHousePackageServiceImpl extends CommonServiceImpl<PanoProjectHousePackageModel> implements PanoProjectHousePackageService<PanoProjectHousePackageModel> {
	@Autowired
	private PanoProjectHousePackageDao housePackageDao;
	@Autowired
	private PanoProjectPackageDao packageDao;
	@Autowired
	private PanoProjectHouseStyleDao houseStyleDao;
	@Autowired
	private PanoProjectHouseDao houseDao;
	@Autowired
	private PanoProjectStyleDao projectStyleDao;
	@Autowired
	private PanoProjectBaseStyleDao baseStyleDao;
	@Autowired
	private PanoProjectDao projectDao;
	@Override
	public CommonDao<PanoProjectHousePackageModel> getDao() {
		return housePackageDao;
	}
	
	@Override
	public PanoProjectHousePackageModel getDetail(long housePackageSn) {
		PanoProjectHousePackageModel housePackage = housePackageDao.getBySn(housePackageSn);
		if(housePackage != null){
			PanoProjectPackageModel projectPackage = packageDao.getBySn(housePackage.getPackageSn());
			housePackage.setName(projectPackage.getName());
			//户型风格关系
			Long houseStyleSn = housePackage.getHouseStyleSn();
			PanoProjectHouseStyleModel houseStyle = houseStyleDao.getBySn(houseStyleSn);
			//户型
			Long houseSn = houseStyle.getHouseSn();
			PanoProjectHouseModel house = houseDao.getBySn(houseSn);
			if(house != null){
				housePackage.setHouse(house);
			}
			//风格
			Long projectStyleSn = houseStyle.getStyleSn();
			PanoProjectStyleModel projectStyle = projectStyleDao.getBySn(projectStyleSn);
			if(projectStyle != null){
				Long baseStyleSn = projectStyle.getStyleSn();
				PanoProjectBaseStyleModel baseStyle = baseStyleDao.getBySn(baseStyleSn);
				projectStyle.setName(baseStyle.getName());
				housePackage.setStyle(projectStyle);
			}
			Long projectSn = houseStyle.getProjectSn();
			PanoProjectModel project = projectDao.getBySn(projectSn);
			if(project != null){
				housePackage.setProject(project);
			}
		}
		return housePackage;
	}

	@Override
	public List<PanoProjectHousePackageModel> listByProject(long projectSn, long houseSn, long styleSn) {
		return housePackageDao.listByProject(projectSn, houseSn, styleSn);
	}

}
