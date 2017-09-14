package com.focus3d.pano.project.component;

import net.sf.json.JSONObject;


/**
 * 
 * *
 * @author lihaijun
 *
 */
public abstract class ProjectSelectNode {
	private String id;
	private String name;
	
	public ProjectSelectNode(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public abstract void add(ProjectSelectNode node);
	
	public abstract void remove(ProjectSelectNode node);
	public abstract void display(int depth);
	public abstract Object toJson();
	
	public abstract ProjectSelectNode find(String name);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
