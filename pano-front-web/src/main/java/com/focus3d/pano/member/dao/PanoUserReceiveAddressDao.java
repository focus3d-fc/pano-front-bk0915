package com.focus3d.pano.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoUserReceiveAddressModel;
import com.focus3d.pano.model.ibator.PanoUserReceiveAddressCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoUserReceiveAddressDao extends CommonDao<PanoUserReceiveAddressModel> {
	/**
	 * 
	 * *
	 * @param userSn
	 * @return
	 */
	public List<PanoUserReceiveAddressModel> listByUser(long userSn) {
		PanoUserReceiveAddressCriteria criteria = new PanoUserReceiveAddressCriteria();
		criteria.createCriteria().andUserSnEqualTo(userSn);
		return selectByCriteria(criteria, PanoUserReceiveAddressModel.class);
	}

}
