package com.score;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 成绩查询 Servlet
 * 处理成绩列表查询，转发到 list.jsp 展示
 */
public class ScoreServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ScoreDao scoreDao = new ScoreDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 支持按姓名/课程关键词查询
        String keyword = request.getParameter("keyword");
        List<Score> list = scoreDao.findAll();
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = new java.util.ArrayList<>();
            for (Score s : scoreDao.findAll()) {
                if ((s.getStudentName() != null && s.getStudentName().contains(keyword.trim()))
                        || (s.getCourseName() != null && s.getCourseName().contains(keyword.trim()))) {
                    list.add(s);
                }
            }
        }
        request.setAttribute("scoreList", list);
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
