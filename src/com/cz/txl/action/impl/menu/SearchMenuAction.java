package com.cz.txl.action.impl.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.model.Menu;
import com.cz.txl.model.QueryCondtion;
import com.cz.txl.util.RequestToQueryCondtionUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchMenuAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<QueryCondtion> qcList = RequestToQueryCondtionUtil.parseRequestToQueryCondtion(request, Menu.class);
			List<Menu> menuList = DaoFactory.getMenuDao().queryPage(qcList,0, 10);
			JSONArray jsonArray = JSONArray.fromObject(menuList);
			JSONObject result=new JSONObject();
			result.put("success", true);
			
			result.put("rows", jsonArray);
			ResponseUtil.write(response, result);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
