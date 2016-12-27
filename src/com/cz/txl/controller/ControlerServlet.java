package com.cz.txl.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.action.ActionFactory;
/**
 * 任何请求都会到这个servlet中，这个servlet就是充当MVC模式中的C（控制层）
 */
@WebServlet("*.do")
public class ControlerServlet extends HttpServlet {
	Log log = LogFactory.getLog(ControlerServlet.class);
	private static final long serialVersionUID = 1L;

	public ControlerServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathName = request.getServletPath();
		log.info("pathName:" + pathName);

		int theSecondXie = pathName.lastIndexOf("/");
		int index = pathName.indexOf(".");
		String actionPath = pathName.substring(1,theSecondXie);
		String actionName = pathName.substring(theSecondXie  + 1,index);
		log.info("actionPath:"+actionPath+"--actionName:" + actionName);

		Action action = ActionFactory.getActionFactory().getAction(actionPath,actionName);
		String viewUrl = action.execute(request, response);
//        if (viewUrl == null) {  
//        	request.getRequestDispatcher("error.jsp").forward(request, response);  
//        } else {  
//        	request.getRequestDispatcher(viewUrl).forward(request, response);  
//        }  
		if (viewUrl != null) {
			log.info(viewUrl);
			log.info(request.getContextPath()+"/"+viewUrl);
			request.getRequestDispatcher("../"+viewUrl).forward(request, response);
//        	response.sendRedirect(viewUrl);
		}
	}

}
