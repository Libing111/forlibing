/* <p>文件名称: ImageCaptchaServlet.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-13</p>
 * <p>完成日期：2014-1-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-13下午1:33:10
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.cas.captcha.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;
import com.proper.uip.cas.captcha.CaptchaServiceSingleton;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] captchaChallengeAsJpeg = (byte[]) null;

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();

			BufferedImage challenge = CaptchaServiceSingleton.getInstance()
					.getImageChallengeForID(captchaId, request.getLocale());

			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);
		} catch (IllegalArgumentException e) {
			response.sendError(404);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(500);
			return;
		}
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);

		responseOutputStream.flush();
		responseOutputStream.close();
	}

	public void init() throws ServletException {
	}
}
