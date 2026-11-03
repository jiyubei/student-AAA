package com.score;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 添加成绩 Servlet
 * 接收 addScore.jsp 表单数据，写入数据库后跳转回列表
 */
public class AddScoreServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ScoreDao scoreDao = new ScoreDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET 请求直接跳转到添加页面
        response.sendRedirect(request.getContextPath() + "/addScore.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String sno = request.getParameter("sno");
        String studentName = request.getParameter("studentName");
        String courseName = request.getParameter("courseName");
        String scoreStr = request.getParameter("score");

        // 参数校验
        if (sno == null || sno.trim().isEmpty()
                || studentName == null || studentName.trim().isEmpty()
                || courseName == null || courseName.trim().isEmpty()) {
            request.setAttribute("error", "学号、姓名、课程名称不能为空！");
            request.getRequestDispatcher("/addScore.jsp").forward(request, response);
            return;
        }

        double scoreVal;
        try {
            scoreVal = Double.parseDouble(scoreStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "成绩格式不正确，请输入数字！");
            request.getRequestDispatcher("/addScore.jsp").forward(request, response);
            return;
        }
        if (scoreVal < 0 || scoreVal > 100) {
            request.setAttribute("error", "成绩必须在 0~100 之间！");
            request.getRequestDispatcher("/addScore.jsp").forward(request, response);
            return;
        }

        Score score = new Score();
        score.setSno(sno.trim());
        score.setStudentName(studentName.trim());
        score.setCourseName(courseName.trim());
        score.setScore(scoreVal);

        boolean success = scoreDao.add(score);
        if (success) {
            // 添加成功跳转到列表
            response.sendRedirect(request.getContextPath() + "/listScore");
        } else {
            request.setAttribute("error", "添加失败，请检查数据库连接！");
            request.getRequestDispatcher("/addScore.jsp").forward(request, response);
        }
    }
}
