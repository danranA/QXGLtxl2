package com.cz.txl.dao.menu;

import java.util.List;

import com.cz.txl.model.Menu;
import com.cz.txl.model.QueryCondtion;

public interface MenuDAO {
	public void insert(Menu menu) throws Exception;
    public void update(Menu menu) throws Exception;
    public void delete(int menuid) throws Exception;
    public Menu queryById(int menuid) throws Exception;
    public List<Menu> queryAll() throws Exception;
    public Menu queryByName(String menuname) throws Exception;
    public List<Menu> queryPage(int start, int pageSize) throws Exception;
    public int queryTotal() throws Exception;
    public void deleteBatch(String[] menuids) throws Exception;
    public List<Menu> queryPage(List<QueryCondtion> qcList, int start, int pageSize) throws Exception;
   


}
