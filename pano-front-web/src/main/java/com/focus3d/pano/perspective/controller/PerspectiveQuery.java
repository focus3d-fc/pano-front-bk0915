package com.focus3d.pano.perspective.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.focus3d.pano.admin.service.IPerspectiveService;
import com.focus3d.pano.admin.service.IProductAdmService;
import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.model.PanoProjectHouseStyleModel;
import com.focus3d.pano.model.PanoProjectPackageTypeModel;
import com.focus3d.pano.model.Product;
import com.focus3d.pano.model.ibator.PanoPerspectiveElementModel;
import com.focus3d.pano.model.ibator.PanoPerspectiveElementProduct;
import com.focus3d.pano.model.ibator.PanoPerspectiveElementProductModel;
import com.focus3d.pano.model.ibator.PanoPerspectiveLayerModel;
import com.focus3d.pano.model.ibator.PanoPerspectiveViewModel;
import com.focus3d.pano.project.service.PanoProjectHouseStyleService;
import com.focus3d.pano.project.service.PanoProjectPackageTypeService;
import com.focustech.cief.filemanage.client.api.IFileReadClient;
import com.focustech.cief.filemanage.client.constant.FileAttributeEnum;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.JsonUtils;
import com.focustech.common.utils.TCUtil;

@Controller
@RequestMapping("/perspective")
public class PerspectiveQuery extends BaseController {
	@Autowired
	IPerspectiveService _service;
	@Autowired
	IProductAdmService product_service;
	@Autowired
	private IFileReadClient client;
	@Autowired
	private PanoProjectPackageTypeService<PanoProjectPackageTypeModel> packageTypeService;
	@Autowired
	private PanoProjectHouseStyleService<PanoProjectHouseStyleModel> houseStyleService;

	/**
	 * @param map
	 * @param request
	 * @return
	 */
	// 查询户型空间信息
	@RequestMapping("query")
	public String QueryInfo(PanoPerspectiveViewModel model, ModelMap map) {
		List<Map<String, Object>> list;
		try {
			list = _service.QuerySpace(model.getHouseStyleSn());
			String data = JsonUtils.arrayToJson(list.toArray());
			map.put("house_style_sn", model.getHouseStyleSn());
			map.put("space", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/perspective/Perspective";
	}

	// 查询空间视角信息
	@RequestMapping("viewQuery")
	public void QueryViewInfo(HttpServletResponse response,
			PanoPerspectiveViewModel model) {
		try {
			List<PanoPerspectiveViewModel> list = _service.QueryViewInfo(model);
			ajaxOutput(response, JsonUtils.arrayToJson(list.toArray()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("queryElement")
	public void QueryElement(HttpServletResponse response,
			PanoPerspectiveElementModel model) {
		try {
			List<Map<String, Object>> list = _service.QueryElement(model);
			JSONArray array = new JSONArray();

			for (Map<String, Object> child : list) {
				JSONObject json = new JSONObject();
				json.put("elementId", child.get("id").toString());
				json.put("name", child.get("name").toString());
				json.put("packageTypeSn", child.get("packageTypeSn").toString());
				json.put("elementOrder", child.get("elementOrder").toString());
				String elementProductSn = child.get("elementProductSn") != null ? child
						.get("elementProductSn").toString() : "";
				json.put("elementProductSn", elementProductSn);
				json.put("layerSn", child.get("layerSn").toString());

				if (child.get("mapid") != null) {
					Long mapid = Long.parseLong(child.get("mapid").toString());
					Map<String, String> map = client.getFile(mapid);
					json.put("url",
							map.get(FileAttributeEnum.VISIT_ADDR.name()));
					json.put("width", map.get(FileAttributeEnum.WIDTH.name()));
					json.put("height", map.get(FileAttributeEnum.HEIGHT.name()));
					json.put("position", child.get("position").toString());
					json.put("scale", child.get("scale").toString());
					json.put("repeating", child.get("repeating").toString());
				}

				array.add(json);
			}

			ajaxOutput(response, array.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("queryElementProduct")
	public void QueryElementProduct(HttpServletResponse response,
			PanoPerspectiveElementModel model) {
		try {
			List<Map<String, Object>> list = _service.queryElementProduct(model);

			JSONArray array = new JSONArray();

			for (Map<String, Object> child : list) {
				JSONObject json = new JSONObject();

				json.put("sn", child.get("sn").toString());
				json.put("id", child.get("id").toString());
				json.put("name", child.get("name").toString());
				json.put("summary", child.get("summary").toString());
				json.put("venderName", child.get("venderName").toString());
				json.put("dimension", child.get("dimension").toString());
				
				if(child.get("longImgSn")!=null){
					Long longImgSn = Long.parseLong(child.get("longImgSn").toString());
					String longImgUrl = client.getFile(longImgSn, FileAttributeEnum.VISIT_ADDR);
					json.put("longImgUrl", longImgUrl);
				}else{
					json.put("longImgUrl", "");
				}
				
				if (child.get("elementProductSn") != null) {
					Long mapid = Long.parseLong(child.get("mapid").toString());
					Map<String, String> map = client.getFile(mapid);
					json.put("elementProductSn", child.get("elementProductSn").toString());
					json.put("mapid", EncryptUtil.encode(mapid));
					json.put("url",map.get(FileAttributeEnum.VISIT_ADDR.name()));
					json.put("width", map.get(FileAttributeEnum.WIDTH.name()));
					json.put("height", map.get(FileAttributeEnum.HEIGHT.name()));
					json.put("position", child.get("position").toString());
					json.put("scale", child.get("scale").toString());
					json.put("repeating", child.get("repeating").toString());
				}
				array.add(json);
			}

			ajaxOutput(response, array.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("queryElementProductMap")
	public void QueryElementProductInfo(HttpServletResponse response,
			PanoPerspectiveElementProductModel model) {
		try {
			PanoPerspectiveElementProduct product = _service
					.QueryElementProductInfo(model.getSn());

			Map<String, String> map = client.getFile(product.getProductMap());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sn", product.getSn());
			jsonObject.put("url", map.get(FileAttributeEnum.VISIT_ADDR.name()));
			jsonObject.put("width", map.get(FileAttributeEnum.WIDTH.name()));
			jsonObject.put("height", map.get(FileAttributeEnum.HEIGHT.name()));

			ajaxOutput(response, jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("queryPackageTypeName")
	public void QueryPackageTypeName(HttpServletResponse response,
			PanoPerspectiveViewModel model) {
		try {
			List<Map<String, Object>> list = _service.QueryPackageTypeName(model);
			List<String> used_list = _service.QueryUsedPackageTypeName(model.getSn());
			
			JSONObject result = new JSONObject();
			
			JSONObject json = new JSONObject();
			for (Map<String, Object> map : list) {
				JSONObject child = new JSONObject();
				String id = map.get("id").toString();
				String name = map.get("name").toString();
				child.put("id", id);
				child.put("name", name);
				json.put(id, child);
			}
			
			result.put("used",used_list);
			result.put("all", json);
			
			ajaxOutput(response, result.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("viewUpdate")
	public void UpdateViewInfo(HttpServletResponse response,
			PanoPerspectiveViewModel model) {
		try {
			Long mapid = EncryptUtil.decode(model.getMapid());
			model.setBaseMap(mapid);

			Map<String, String> map = client.getFile(mapid);

			String value = _service.UpdateView(model);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", value);
			jsonObject.put("name", model.getViewName());
			jsonObject.put("mapid", mapid);
			jsonObject.put("url", map.get(FileAttributeEnum.VISIT_ADDR.name()));
			jsonObject.put("width", map.get(FileAttributeEnum.WIDTH.name()));
			jsonObject.put("height", map.get(FileAttributeEnum.HEIGHT.name()));
			ajaxOutput(response, jsonObject.toString());
		} catch (IOException e) {

		} catch (SQLException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("layerUpdate")
	public void UpdateLayerInfo(HttpServletResponse response,
			PanoPerspectiveLayerModel model) {
		try {
			String value = _service.UpdateLayer(model);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", value);
			jsonObject.put("name", model.getLayerName());
			jsonObject.put("viewSn", model.getViewSn());
			ajaxOutput(response, jsonObject.toString());
		} catch (IOException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	@RequestMapping("elementUpdate")
	public void UpdateElementInfo(HttpServletResponse response,
			PanoPerspectiveElementModel model) {
		try {
			String value = _service.UpdateElement(model);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("elementId", value);
			jsonObject.put("packageTypeSn", model.getPackageTypeSn());
			jsonObject.put("layerSn", model.getLayerSn());
			jsonObject.put("name", model.getElementName());
			jsonObject.put("elementOrder", model.getElementOrder());
			ajaxOutput(response, jsonObject.toString());
		} catch (IOException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	@RequestMapping("elementExhibitionMapUpdate")
	public void UpdateElementExhibitionMapInfo(HttpServletResponse response,
			PanoPerspectiveElementModel model) {
		try {
			int value = _service.UpdateElementProductSn(model);
			JSONObject jsonObject = new JSONObject();
			Long elementProductSn = model.getElementProductSn();
			if (elementProductSn != null) {
				PanoPerspectiveElementProduct product = _service.QueryElementProductInfo(elementProductSn);
				Map<String, String> map = client.getFile(product
						.getProductMap());
				jsonObject.put("elementProductSn", product.getSn());
				jsonObject.put("url",
						map.get(FileAttributeEnum.VISIT_ADDR.name()));
				jsonObject
						.put("width", map.get(FileAttributeEnum.WIDTH.name()));
				jsonObject.put("height",
						map.get(FileAttributeEnum.HEIGHT.name()));
				jsonObject.put("position", product.getPosition());
				jsonObject.put("scale", product.getScale());
				jsonObject.put("repeating", product.getRepeating());
			} else {
				jsonObject.put("elementProductSn", "");
				jsonObject.put("url", "");
				jsonObject.put("width", "");
				jsonObject.put("height", "");
				jsonObject.put("position", "");
				jsonObject.put("scale", "");
				jsonObject.put("repeating", "");
			}

			ajaxOutput(response, jsonObject.toString());
		} catch (IOException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("elementProductUpdate")
	public void UpdateElementProductInfo(HttpServletResponse response,
			PanoPerspectiveElementProductModel model) {
		try {
			Long mapid = EncryptUtil.decode(model.getMapid());
			model.setProductMap(mapid);

			String value = _service.UpdateElementProduct(model);

			Map<String, String> map = client.getFile(mapid);
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("sn", model.getProductSn().toString());
			jsonObject.put("elementProductSn", model.getSn());
			jsonObject.put("mapid", model.getMapid());
			jsonObject.put("url", map.get(FileAttributeEnum.VISIT_ADDR.name()));
			jsonObject.put("width", map.get(FileAttributeEnum.WIDTH.name()));
			jsonObject.put("height", map.get(FileAttributeEnum.HEIGHT.name()));
			jsonObject.put("position", model.getPosition());
			jsonObject.put("scale", model.getScale());
			jsonObject.put("repeating", model.getRepeating());

			ajaxOutput(response, jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("deleteElement")
	public void DeleteElement(HttpServletResponse response,
			PanoPerspectiveElementModel model) {
		try {
			JSONObject jsonObject = new JSONObject();
			int value = _service.DeleteElement(model);
			ajaxOutput(response, jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("deleteLayer")
	public void DeleteLayer(HttpServletResponse response,
			PanoPerspectiveLayerModel model) {
		try {
			JSONObject jsonObject = new JSONObject();
			int value = _service.DeleteLayer(model);
			ajaxOutput(response, jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("deleteView")
	public void DeleteView(HttpServletResponse response,
			PanoPerspectiveViewModel model) {
		try {
			JSONObject jsonObject = new JSONObject();
			int value = _service.DeleteView(model);
			ajaxOutput(response, jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("updateElementOrder")
	public void UpdateElementOrder(HttpServletResponse response, String pre,
			String next, String layerSn) {
		try {
			JSONObject json = new JSONObject();
			_service.ExchangeElementOrder(pre, next);

			json.put("pre", pre);
			json.put("next", next);
			json.put("layerSn", layerSn);
			ajaxOutput(response, json.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("updateLayerOrder")
	public void UpdateLayerOrder(HttpServletResponse response, String pre,
			String next) {
		try {
			JSONObject json = new JSONObject();
			_service.ExchangeLayerOrder(pre, next);

			json.put("pre", pre);
			json.put("next", next);
			ajaxOutput(response, json.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("QueryPerspectiveByProductSn")
	public List<Map<String, Object>> QueryPerspectiveByProductSn(String houseStyleSn,
			String packageTypeSn, String productSn) {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("houseStyleSn", houseStyleSn);
			map.put("packageTypeSn", packageTypeSn);
			map.put("productSn", productSn);
			list = _service.QueryPerspectiveInfo(map);

			for (Map<String, Object> child : list){
				child.put("productSn", productSn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return list;
		}
	}

	@RequestMapping("ValidatePerspective")
	public void ValidatePerspective(HttpServletResponse response, ModelMap model_map,String houseStyleSn,String packageTypeSn,String productSn) {
		// 验证有没有透视图
		try{
			Long _houseStyleSn = EncryptUtil.decode(houseStyleSn);
			Long _packageTypeSn = EncryptUtil.decode(packageTypeSn);
			Long _productSn = EncryptUtil.decode(productSn);
			Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("HOUSE_STYLE_SN", _houseStyleSn);
			map.put("USER_SN", userSn);
			List<LinkedHashMap<String, Object>> list = _service.QueryRelation(map);
			
			//List<Map<String, Object>> list = QueryPerspectiveByProductSn(_houseStyleSn.toString(),_packageTypeSn.toString(),_productSn.toString());
			
			JSONObject json = new JSONObject();
			JSONObject param = new JSONObject();
			param.put("houseStyleSn", _houseStyleSn);
			param.put("packageTypeSn", _packageTypeSn);
			param.put("productSn", _productSn);
			json.put("num", list.size());
			json.put("param", param);
			ajaxOutput(response, json.toJSONString());
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("QueryPerspective")
	public String QueryPerspective(HttpServletResponse response, ModelMap model_map,String houseStyleSn,String packageTypeSn,String productSn,ModelMap result) {
		try{		
			Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
			HashMap<String,Object> param = new HashMap<String,Object>();
			Long _packageTypeSn = Long.parseLong(packageTypeSn);
			PanoProjectPackageTypeModel packageType = packageTypeService.getBySn(_packageTypeSn);
			
			Long spaceSn = packageType.getSpaceSn();
			
			param.put("HOUSE_STYLE_SN", houseStyleSn);
			param.put("SPACE_SN", spaceSn);
			param.put("PACKAGE_TYPE_SN", packageTypeSn);
			param.put("USER_SN", userSn);
			
			List<LinkedHashMap<String, Object>> list = _service.QueryRelation(param);
			
			List<Map<String,Object>> product_view = _service.QueryProductView(param);
			
			//List<Map<String, Object>> list = QueryPerspectiveByProductSn(houseStyleSn,packageTypeSn,productSn);
			//Product product = product_service.getProductBySn(productSn);
			
			PanoProjectHouseStyleModel houseStyleModel = houseStyleService.getBySn(TCUtil.lv(houseStyleSn));
			Long projectSn = houseStyleModel.getProjectSn();
			Long styleSn = houseStyleModel.getStyleSn();
			result.put("styleId", styleSn);
			result.put("projectSn", EncryptUtil.encode(projectSn));
			result.put("viewlist",JsonUtils.arrayToJson(list.toArray()));
			//result.put("product", JsonUtils.objectToJson(product));
			result.put("packageTypeSn", packageTypeSn);
			result.put("houseStyleSn", houseStyleSn);
			result.put("userSn", userSn);
			result.put("spaceSn", spaceSn);
			result.put("viewSn","");
			result.put("productView", JsonUtils.arrayToJson(product_view.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "perspective/pro";
	}
	
	@RequestMapping("QueryPerspectiveDetail")
	public void QueryPerspectiveDetail(HttpServletResponse response, ModelMap model_map,String houseStyleSn,String userSn,String viewSn,ModelMap result){
		try{			
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("HOUSE_STYLE_SN", houseStyleSn);
			param.put("USER_SN", userSn);
			param.put("VIEW_SN", viewSn);
			
			List<LinkedHashMap<String, Object>> list = _service.QueryPerspective(param);
			
			for (LinkedHashMap<String, Object> map : list) {
				if (map.get("viewMap") != null) {
					Long viewMapKey = Long.parseLong(map.get("viewMap")
							.toString());

					Map<String, String> viewMapFile = client
							.getFile(viewMapKey);

					map.put("viewMapUrl", viewMapFile
							.get(FileAttributeEnum.VISIT_ADDR.name()));
					map.put("viewMapWidth",
							viewMapFile.get(FileAttributeEnum.WIDTH.name()));
					map.put("viewMapHeight",
							viewMapFile.get(FileAttributeEnum.HEIGHT.name()));
					map.put("viewMapId", EncryptUtil.encode(viewMapKey));
				}

				if (map.get("elementMap") != null) {
					Long elementMapKey = Long.parseLong(map.get("elementMap")
							.toString());

					Map<String, String> elementMapFile = client
							.getFile(elementMapKey);

					map.put("elementMapUrl", elementMapFile
							.get(FileAttributeEnum.VISIT_ADDR.name()));
					map.put("elementMapWidth",
							elementMapFile.get(FileAttributeEnum.WIDTH.name()));
					map.put("elementMapHeight",
							elementMapFile.get(FileAttributeEnum.HEIGHT.name()));
					map.put("elementMapId", EncryptUtil.encode(elementMapKey));
				}
			}
			//List<Map<String, Object>> list = QueryPerspectiveByProductSn(houseStyleSn,packageTypeSn,productSn);
			ajaxOutput(response, JsonUtils.arrayToJson(list.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "perspective/pro";
	}
	
	
	@RequestMapping("QueryProduct")
	public void QueryProduct(HttpServletResponse response, ModelMap model_map,String productSn){
		try{
			Product product = product_service.getProductBySn(productSn);
			JSONObject json = new JSONObject();
			json.put("product", product);
			ajaxOutput(response, json.toJSONString());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("UpdateShopCart")
	public void UpdateShopCart(HttpServletResponse response, String userSn,String data){
		try{
			JSONObject map = JSONObject.parseObject(data);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
	            HashMap<String,Object> hashMap = new HashMap<String,Object>();
	            hashMap.put("element_sn", entry.getKey());
	            String value = entry.getValue().toString();
	            if(value.equals("")){
	            	continue;
	            }
	            hashMap.put("product_sn", entry.getValue());
	            hashMap.put("user_sn",userSn);
	            _service.UpdateShopCart(hashMap);
	        }
			JSONObject json = new JSONObject();
			json.put("info", "succeed");
			ajaxOutput(response, json.toJSONString());
		}catch(SQLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
