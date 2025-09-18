package vn.edu.hcmute.fit.sql;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String url;
        String message = "";

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/index.jsp";
        } else if (action.equals("add")) {
            // Lấy và chuẩn hóa dữ liệu đầu vào
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            if (firstName != null) firstName = firstName.trim();
            if (lastName != null) lastName = lastName.trim();
            if (email != null) email = email.trim();

            // Kiểm tra dữ liệu rỗng
            assert firstName != null;
            if (firstName.isEmpty() || Objects.requireNonNull(lastName).isEmpty() || Objects.requireNonNull(email).isEmpty()) {
                message = "Vui lòng nhập đầy đủ thông tin.";
                url = "/index.jsp";
            } else {
                User user = new User(firstName, lastName, email);

                System.out.println("Email kiểm tra: " + email);
                boolean exists = UserDB.emailExists(email);
                System.out.println("Tồn tại trong DB: " + exists);

                if (exists) {
                    message = "Email đã tồn tại.<br>Vui lòng nhập email khác.";
                    url = "/index.jsp";
                } else {
                    int result = UserDB.insert(user);
                    System.out.println("Kết quả insert: " + result);

                    if (result > 0) {
                        url = "/thanks.jsp";
                    } else {
                        message = "Lỗi: Không thể thêm người dùng vào cơ sở dữ liệu. Vui lòng thử lại.";
                        url = "/index.jsp";
                    }
                }

                request.setAttribute("user", user);
            }

            request.setAttribute("message", message);
        } else {
            url = "/index.jsp";
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}