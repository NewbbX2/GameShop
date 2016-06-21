package com.gameshop.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gameshop.controller.action.Action;
import com.gameshop.dao.ProductDAO;
import com.gameshop.dto.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class AdminProductUpdateAction implements Action {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String url = "gameshopServlet?command=admin_product_list";
    
    HttpSession session = request.getSession();
    
    int sizeLimit = 5 * 1024 * 1024;
    String savePath = "product_images";
    ServletContext context = session.getServletContext();
    String uploadFilePath = context.getRealPath(savePath);
    
    MultipartRequest multi = new MultipartRequest(request, // ��û ��ü
        uploadFilePath, // ���ε�� ������ ����� ���� ��θ�
        sizeLimit, // ���ε�� ������ �ִ� ũ��(5Mb)
        "UTF-8", // ���ڵ� Ÿ�� ����
        new DefaultFileRenamePolicy() // ����⸦ ���� ���� �κ�
    ); // �� ������ ���� ������ �̹� ������ �Ǿ���
    
    ProductVO productVO = new ProductVO();
    productVO.setPseq(Integer.parseInt(multi.getParameter("pseq")));
    productVO.setKind(multi.getParameter("kind"));
    productVO.setName(multi.getParameter("name"));
    productVO.setPrice1(Integer.parseInt(multi.getParameter("price1")));
    productVO.setPrice2(Integer.parseInt(multi.getParameter("price2")));
    productVO.setPrice3(Integer.parseInt(multi.getParameter("price2"))
        - Integer.parseInt(multi.getParameter("price1")));
    productVO.setContent(multi.getParameter("content"));
    productVO.setBestyn(multi.getParameter("bestyn"));
    productVO.setUseyn(multi.getParameter("useyn"));
    if(multi.getFilesystemName("image")==null){
      productVO.setImage(multi.getParameter("nonmakeImg"));
    }else{
      productVO.setImage(multi.getFilesystemName("image"));
    }
    
    ProductDAO productDAO = ProductDAO.getInstance();
    productDAO.updateProduct(productVO);
    
    response.sendRedirect(url);
  }
}