package com.focus3d.pano.project.component;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class ProjectSelect extends ProjectSelectNode {

	private List<ProjectSelectNode> children = new ArrayList<ProjectSelectNode>();
	
	public ProjectSelect(String id, String name) {
		super(id, name);
	}
	public ProjectSelect(String name) {
		super("0", name);
	}

	@Override
	public void add(ProjectSelectNode node) {
		children.add(node);
	}
	
	@Override
	public void remove(ProjectSelectNode node) {
		children.remove(node);
	}

	@Override
	public void display(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("-");
		}
		System.out.println(getName());
		for (ProjectSelectNode cd : children) {
			cd.display(depth + 2);
		}
	}

	public List<ProjectSelectNode> getChildren() {
		return children;
	}

	public void setChildren(List<ProjectSelectNode> children) {
		this.children = children;
	}

	@Override
	public ProjectSelectNode find(String name) {
		if(this.getName().equals(name)){
			return this;
		}
		ProjectSelectNode find = null;
		for(ProjectSelectNode cd : children){
			String cdName = cd.getName();
			if(name.equals(cdName)){
				find = cd;
				break;
			}
			find = cd.find(name);
			if(find != null){
				break;
			}
		}
		return find;
	}
	@Override
	public Object toJson() {
		JSONObject jo = new JSONObject();
		jo.put("value", getId());
		jo.put("text", getName());
		JSONArray jary = new JSONArray();
		for(ProjectSelectNode cd : children){
			jary.add(cd.toJson());
		}
		jo.put("children", jary);
		return jo;
	}

}
