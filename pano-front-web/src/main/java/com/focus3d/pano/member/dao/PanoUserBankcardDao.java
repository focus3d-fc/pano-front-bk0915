package com.focus3d.pano.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoUserBankcardModel;
import com.focus3d.pano.model.ibator.PanoUserBankcardCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoUserBankcardDao extends CommonDao<PanoUserBankcardModel> {

	public List<PanoUserBankcardModel> listByUser(long userSn) {
		PanoUserBankcardCriteria criteria = new PanoUserBankcardCriteria();
		criteria.createCriteria().andUserSnEqualTo(userSn);
		return selectByCriteria(criteria, PanoUserBankcardModel.class);
	}

	public PanoUserBankcardModel getByCardNo(long userSn, String cardNo) {
		PanoUserBankcardCriteria criteria = new PanoUserBankcardCriteria();
		criteria.createCriteria().andUserSnEqualTo(userSn).andCardNoEqualTo(cardNo);
		return selectFirstByExample(criteria, PanoUserBankcardModel.class);
	}

}
