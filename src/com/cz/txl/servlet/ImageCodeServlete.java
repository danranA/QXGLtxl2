package com.cz.txl.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ImageCodeServlete
 */
public class ImageCodeServlete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageCodeServlete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");//设值输出内容以图片格式进行解析
		response.setHeader("Prama", "No-cache");//设值图片在页面上不缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);//设置失效时间

		//获得该次会话的session
		HttpSession session = request.getSession();

		//在内存中创建一个图片
		int width = 100,height=40;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//获取图形上下文
		Graphics g = image.getGraphics();//获得画笔
		//设置图片的背景色
		g.setColor(getRandColor(200, 250));//改变画笔颜色
		g.fillRect(0, 0, width, height);

		//设定验证码字体
		g.setFont(new Font("Times New Roman",Font.PLAIN,24));

		//画边框
		g.setColor(getRandColor(160, 200));//改变画笔的颜色
		g.drawRect(0, 0, width-1, height-1);

		//生成随机类
		Random random = new Random();

		//随机画15条干扰的线条,避免被机器人识别 OCR
		g.setColor(getRandColor(160, 200));//改变画笔的颜色
		for (int i = 0;i<15;i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);

			g.drawLine(x, y, x+xl, y+yl);

		}

		//生成验证码上的随机数
		String sRand = "";
		for (int i=0;i<4;i++) {
			String rand = String.valueOf(random.nextInt(10));

			sRand += rand;

			//将验证码显示到页面中
			g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));//改变画笔颜色
			g.drawString(rand, 13*i + 14, 20);//用画笔画文字
		}

		//将验证码文字存入到session中
		session.setAttribute("imageCode", sRand);

		//设置图像开始生效
		g.dispose();


		//最后把图片输入到流里返回到浏览器中
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	//给定范围内获得随机颜色
	private Color getRandColor(int fc,int bc){
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}

		if (bc > 255) {
			bc = 255;
		}

		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r,g,b);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
