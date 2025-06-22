package com.example.filmmanager.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    // 验证码字符集
    private static final String CODES = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 设置响应头
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 2. 创建验证码图片
        int width = 90, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 3. 设置背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 4. 绘制干扰线
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            g.setColor(getRandomColor(200, 250));
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // 5. 生成验证码
        String verifyCode = generateVerifyCode(4);
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode", verifyCode);

        // 6. 绘制验证码
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        for (int i = 0; i < verifyCode.length(); i++) {
            g.setColor(new Color(20 + random.nextInt(110),
                    20 + random.nextInt(110),
                    20 + random.nextInt(110)));
            g.drawString(String.valueOf(verifyCode.charAt(i)), 18 * i + 5, 22);
        }

        // 7. 输出图片
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    // 生成验证码字符串
    private String generateVerifyCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CODES.charAt(random.nextInt(CODES.length())));
        }
        return sb.toString();
    }

    // 获取随机颜色
    private Color getRandomColor(int lower, int upper) {
        Random random = new Random();
        if (upper > 255) upper = 255;
        if (lower < 0) lower = 0;
        int r = lower + random.nextInt(upper - lower);
        int g = lower + random.nextInt(upper - lower);
        int b = lower + random.nextInt(upper - lower);
        return new Color(r, g, b);
    }
}