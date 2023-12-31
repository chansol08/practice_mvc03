package controller;

import model.MemberDAO;
import model.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class MemberDeleteController implements Controller {

    @Override
    public String requestHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int number = Integer.parseInt(request.getParameter("number"));

        MemberDAO dao = new MemberDAO();
        MemberVO member = dao.memberContent(number);
        String filename = member.getFilename();

        String UPLOAD_DIR = "file_repository";
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File file = new File(uploadPath + "\\" + filename);

        if (file.exists()) {
            file.delete();
        }

        int count = dao.memberDelete(number);

        String nextPage = null;

        if (count > 0) {
            request.getSession().invalidate();
            nextPage = "redirect:/memberList.do";
        } else {
            throw new ServletException("delete fail");
        }

        return nextPage;
    }
}
