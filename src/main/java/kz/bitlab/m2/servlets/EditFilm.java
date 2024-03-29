package kz.bitlab.m2.servlets;

import kz.bitlab.m2.db.DBManager;
import kz.bitlab.m2.model.Film;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditFilm", value = "/edit-film")
public class EditFilm extends HttpServlet {
    Long id;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("CURRENT_USER") != null) {
            id = Long.parseLong(request.getParameter("filmId"));
            request.setAttribute("film", DBManager.getFilmById(id));
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else {
            response.sendRedirect("/auth");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        id = Long.parseLong(request.getParameter("filmId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Long studioId = Long.parseLong(request.getParameter("studioId"));
        double rating = Double.parseDouble(request.getParameter("rating"));
        Film film = new Film(id, title, description, studioId, rating);
        DBManager.updateFilm(film);
        response.sendRedirect("/details-film?filmId=" + id);
    }
}
