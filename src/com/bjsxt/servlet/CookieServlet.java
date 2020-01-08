package com.bjsxt.servlet;

import com.bjsxt.pojo.User;
import com.bjsxt.service.LoginService;
import com.bjsxt.service.impl.LoginServiceImpl;
import com.sun.tools.javac.Main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* Cookie信息校验
*               判断请求中是否携带正确的Cookie信息
*               如果有校验Cookie信息是否正确
*                       如果校验正确则直接响应主页面给用户
*                       如果响应不正确则直接响应登录页面给用户
*               没有则请求转发给登录页面
** Cookie学习:
 * 		作用:解决了发送的不同请求的数据共享问题
 * 		使用:
 * 			Cookie的创建和存储
 * 				//创建Cookie对象
 * 					Cookie c=new Cookie(String name, String value);
 * 				//设置cookie(可选)
 * 					//设置有效期
 * 					c.setMaxAge(int seconds);
 *					//设置有效路径
 *					c.setPath(String uri)
 *				//响应Cookie信息给客户端
 *					resp.addCookie(c);
 *			Cookie的获取
 *				//获取Cookie信息数组
 *				Cookie[] cks=req.getCookies();
 *				//遍历数组获取Cookie信息
 *					使用for循环遍历即可，示例：
	 					if(cks!=null){
							for(Cookie c:cks){
								String name=c.getName();
								String value=c.getValue();
								System.out.println(name+":"+value);
							}
						}
 *		注意:
 *			一个Cookie对象存储一条数据。多条数据，可以多创建几个Cookie对象进行存储。
 *		特点:
 *			浏览器端的数据存储技术。
 *			存储的数据声明在服务器端。
 *			临时存储:存储在浏览器的运行内存中，浏览器关闭即失效。
 *			定时存储:设置了Cookie的有效期，存储在客户端的硬盘中，在有效期内符合路径要求的请求都会附带该信息。
 *			默认cookie信息存储好之后，每次请求都会附带，除非设置有效路径
*
*
* */
@WebServlet(name = "CookieServlet",urlPatterns = "/ck")
public class CookieServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求编码格式
        req.setCharacterEncoding("utf-8");
        //设置响应编码格式
        resp.setContentType("text/html;charset=utf-8");
        //获取请求信息
            //获取Cookie信息
        Cookie[] cks = req.getCookies();
        //处理请求信息
        if (cks != null) {
                //遍历Cookie信息
                    String uid = "";
                    //iter
                    for (Cookie ck : cks) {
                        if ("uid".equals(ck.getName())){
                            uid = ck.getValue();
                        }
                    }
                //校验uid是否存在
                    if ("".equals(uid)){
                        //请求转发
                        req.getRequestDispatcher("page").forward(req,resp);
                        return;
                    }else{
                        //校验UID用户信息
                                //获取业务层信息
                        LoginService ls = new LoginServiceImpl();
                        User u = ls.checkUidService(uid);
                        if (u != null) {
                            //将用户数据存储到session对象中
                            req.getSession().setAttribute("user",u);
                            //网页计数器自增
                            int nums = (int) this.getServletContext().getAttribute("nums");
                            nums+=1;
                            this.getServletContext().setAttribute("nums",nums);
                            //重定向
                            resp.sendRedirect("main");
                            return;
                        }else {
                            //请求转发
                            req.getRequestDispatcher("page").forward(req, resp);
                            return;
                        }
                    }
        }else{
            //响应处理结果
            //请求转发
            req.getRequestDispatcher("page").forward(req, resp);
            return;
        }
    }
}
