package com.focus3d.pano.project.component;

import net.sf.json.JSONObject;


/**
 * 
 * *
 * @author lihaijun
 *
 */
public class ProjectSelectLeaf extends ProjectSelectNode {

	public ProjectSelectLeaf(String id, String name) {
		super(id, name);
	}

	@Override
	public void add(ProjectSelectNode node) {
		throw new RuntimeException("不可以添加到叶子节点");
	}

	@Override
	public void remove(ProjectSelectNode node) {
		throw new RuntimeException("不可以从叶子节点删除");
	}

	@Override
	public void display(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("-");
		}
		System.out.println(getName());
	}

	@Override
	public ProjectSelectNode find(String name) {
		if(this.getName().equals(name)){
			return this;
		}
		return null;
	}

	@Override
	public Object toJson() {
		JSONObject jo = new JSONObject();
		jo.put("value", getId());
		jo.put("text", getName());
		return jo;
	}
}
