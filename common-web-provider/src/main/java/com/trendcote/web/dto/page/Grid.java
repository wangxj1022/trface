package com.trendcote.web.dto.page;

import java.util.ArrayList;
import java.util.List;

public class Grid implements java.io.Serializable {

	private Long total = 0L;
	private List rows = new ArrayList();
	private List footer = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
