package com.focus3d.pano.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.model.Lable;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.model.Style;
import com.focus3d.pano.model.pano_ad;
import com.focus3d.pano.project.component.ProjectSelect;
import com.focus3d.pano.project.component.ProjectSelectLeaf;
import com.focus3d.pano.project.component.ProjectSelectNode;
import com.focus3d.pano.project.service.PanoProjectService;
import com.focus3d.pano.usersside.service.UsersSideService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.StringUtils;

/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{
	@Autowired
	private UsersSideService usersSideService;
	@Autowired
	private PanoProjectService<PanoProjectModel> projectService;
	/**
	 * *
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest request){
		String checkProjectSn = HttpUtil.sv(request, "checkProjectSn");
		//查询全部楼盘，默认第一个楼盘显示首页
		PanoProjectModel showProject = null;
		List<PanoProjectModel> projectList = projectService.list();
		if(StringUtils.isEmpty(checkProjectSn)){
			//默认首页显示数据库里第一个楼盘
			showProject = projectList.get(0);
			checkProjectSn = showProject.getEncryptSn();
		} else {
			//条件查询
			for (PanoProjectModel project : projectList) {
				if(project.getEncryptSn().equals(checkProjectSn)){
					showProject = project;
					break;
				}
			}
		}
		modelMap.put("projectSelectData", buildProjectSelectTree(projectList));
		long projectSn = showProject.getSn();
		modelMap.addAttribute("showProject", showProject);
		//风格
		List<pano_ad> adList = usersSideService.selectAdImg_sn(projectSn);
		modelMap.addAttribute("adList",adList);
		List<Style> styleList = usersSideService.selectStyleByProject_sn(projectSn);
		//添加标签
		for(Style style : styleList) {    
			Long style_sn = style.getId();
			style.setEncryptSn(EncryptUtil.encode(style_sn));
			List<Lable> lableList = usersSideService.selectLableByStyle_sn(style_sn);
			style.setLableList(lableList);
		}    
		modelMap.addAttribute("styleList",styleList);
		modelMap.put("checkProjectSn", checkProjectSn);
		return "/pub/index";
	}
	/**
	 * 构建楼盘选择联动数据
	 * *
	 * @return
	 */
	private String buildProjectSelectTree(List<PanoProjectModel> projectList){
		if(ListUtils.isNotEmpty(projectList)){
			ProjectSelect root = new ProjectSelect("-1", "数据");
			for (PanoProjectModel project : projectList) {
				String province = project.getProvince();
				ProjectSelectNode provinceNode = root.find(province);
				if(provinceNode == null){
					provinceNode = new ProjectSelect(province);
					root.add(provinceNode);
				}
				String city = project.getCity();
				ProjectSelectNode cityNode = provinceNode.find(city);
				if(cityNode == null){
					cityNode = new ProjectSelect(city);
					provinceNode.add(cityNode);
				}
				String area = project.getArea();
				ProjectSelectNode areaNode = cityNode.find(area);
				if(areaNode == null){
					areaNode = new ProjectSelect(area);
					cityNode.add(areaNode);
				}
				String projectName = project.getName();
				ProjectSelectNode projectNode = areaNode.find(projectName);
				if(projectNode == null){
					projectNode = new ProjectSelectLeaf(project.getEncryptSn(), projectName);
					areaNode.add(projectNode);
				}
			}
			JSONObject jo = (JSONObject)root.toJson();
			return jo.getString("children");
		}
		return "";
	}
}
