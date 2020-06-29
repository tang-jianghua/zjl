package com.mfangsoft.zhuangjialong.common.model;

import java.util.ArrayList;
import java.util.List;

public class Tree<Tree> {
	
	private String id;
	
	private String text;
	

	private  boolean checked;

	private String node_id;

	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	
	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}


	private boolean expanded;
	
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

	private String sprite;

	private List<Tree> items = new ArrayList<Tree>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tree> getItems() {
		return items;
	}

	public void setItems(List<Tree> items) {
		this.items = items;
	}

	
	
	
	
	
}
