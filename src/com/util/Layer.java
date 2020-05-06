package com.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.util.Util;
import com.view.monitor.Monitor;

/***
 * 定义的Dialog对话框
 * 
 * @author xiebing
 *
 */
public class Layer extends JDialog {

	private static final long serialVersionUID = 1L;

	private static int width = 520;
	private static int height = 460;

	private static JButton okBtn = new JButton("确定");
	private static JButton cancelBtn = new JButton("取消");
	@Override
	public int getWidth() {
		return Layer.width;
	}

	public static void setWidth(int width) {
		Layer.width = width;
	}
	@Override
	public int getHeight() {
		return Layer.height;
	}

	public static void setHeight(int height) {
		Layer.height = height;
	}

	public Layer() {
	}

	public Layer(String title, JPanel content, String type, LayerCallback callback) {

		JPanel rootPane = new JPanel();
		rootPane.setLayout(null);
		rootPane.setPreferredSize(new Dimension(width, height));
		rootPane.setBounds(0, 0, width, height);
		rootPane.add(content);
		rootPane.add(bottom(type, callback));

		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;
		pack();
		setModal(true);
		setTitle(title);
		setSize(width, height);
		setResizable(false);
		setContentPane(rootPane);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Util.getLogoIcon("logo_16_16.png"));
		setLocation(x, y);
		setVisible(true);
	}

	/*************************** 确认框 **************************/
	public static Layer confirm(String content, LayerCallback callback) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height - 78));
		panel.setBounds(0, 0, width, height - 78);

		JLabel label = new JLabel(content);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setFont(CyFont.puHuiTi(CyFont.Medium, 14));
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
		panel.add(label);
		return new Layer("提示", panel, "confirm", callback);
	}

	public static Layer confirm(String content, int width, int height, LayerCallback callback) {
		Layer.setWidth(width);
		Layer.setHeight(height);
		return Layer.confirm(content, callback);
	}

	/*************************** 提示框 **************************/

	public static void alert(String content) {
		JPanel panel = new JPanel();
		panel.setSize(width - 16, height - 78);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel();
		label.setSize(panel.getWidth() - 10, panel.getHeight() - 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		labelTextLine(label, content);

		panel.add(label, BorderLayout.CENTER);
		new Layer("提示", panel, "alert", null);
	}

	public static void alert(String content, int width, int height) {
		Layer.setWidth(width);
		Layer.setHeight(height);
		alert(content);
	}

	/**************************************************************************/

	public JPanel bottom(String type, LayerCallback callback) {

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottom.setPreferredSize(new Dimension(width, 38));
		bottom.setBounds(0, height - 78, width, 38);
		switch (type) {
		case "confirm":
			cancelBtn.setFocusPainted(false);
			cancelBtn.setBorderPainted(false);
			cancelBtn.setBackground(Color.white);
			cancelBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					callback.clickCancelBtn();
				}
			});
			bottom.add(cancelBtn);
			okBtn.setFocusPainted(false);
			okBtn.setBorderPainted(false);
			okBtn.setBackground(new Color(0, 150, 36));
			okBtn.setForeground(Color.white);
			bottom.add(okBtn);
			okBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					callback.clickBtn("ok");
					callback.clickOkBtn();
					callback.clickOkBtn(new LayerConfirmCallback() {
						@Override
						public void destroy() {
							dispose();
						}

						@Override
						public void destroy(JDialog dialog) {
							dialog.dispose();
						}
					});
				}
			});
			break;
		case "alert":
			cancelBtn.setFocusPainted(false);
			cancelBtn.setBorderPainted(false);
			cancelBtn.setForeground(Color.white);
			cancelBtn.setBackground(new Color(0, 150, 36));
			cancelBtn.setText("确定");
			bottom.add(cancelBtn);
			break;
		default:
			break;
		}
		okBtn.setCursor(new Cursor(12));
		cancelBtn.setCursor(new Cursor(12));

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (callback != null) {
					callback.clickBtn("cancel");
				}
				dispose();
			}
		});
		return bottom;
	}

	/**
	 * 弹出自定义层
	 * 
	 * @param title
	 * @param comp
	 * @param width
	 * @param height
	 * @return
	 */
	public static JDialog window(String title, Container comp, int width, int height) {
		Layer.setWidth(width);
		Layer.setHeight(height);
		return Layer.window(title, comp);
	}

	/**
	 * 弹出自定义层
	 * @param title
	 * @param contentPane
	 * @return
	 */
	public static JDialog window(String title, Container contentPane) {
		JDialog dialog = new JDialog(Monitor.getFrames()[0], true);
		dialog.setTitle(title);
		dialog.setResizable(false);
		dialog.setSize(width, height);
		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;
		dialog.setLocation(x, y);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setIconImage(Util.getLogoIcon("logo_16_16.png"));
		dialog.setContentPane(contentPane);
		return dialog;
	}

	/**
	 * 弹出加载层
	 * 
	 * @param text
	 * @param callback
	 */
	public static void loading(String text, LayerLoadingCallback callback) {
		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - 100;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - 18;
		callback.start();
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);

		JDialog dialog = new JDialog();
		dialog.setTitle(text);
		dialog.setLocation(x, y);
		dialog.setContentPane(label);
		dialog.setResizable(false);
		dialog.setSize(200, 36);
		dialog.setUndecorated(true);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setIconImage(Util.getLogoIcon("logo_16_16.png"));
		callback.handle(dialog);
		callback.end(dialog);
	}

	/**
	 * 弹出加载层
	 * 
	 * @param callback
	 */
	public static void loading(LayerLoadingCallback callback) {
		loading("正在加载中...", callback);
	}

	/************************ 公共方法 ***************************/

	static void labelTextLine(JLabel jLabel, String longString) {
		StringBuilder builder = new StringBuilder("<html>");
		char[] chars = longString.toCharArray();
		FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
		int start = 0;
		int len = 0;
		while (start + len < longString.length()) {
			while (true) {
				len++;
				if (start + len > longString.length()){
					break;
				}
				if (fontMetrics.charsWidth(chars, start, len) > jLabel.getWidth()) {
					break;
				}
			}
			builder.append(chars, start, len - 1).append("<br/>");
			start = start + len - 1;
			len = 0;
		}
		builder.append(chars, start, longString.length() - start);
		builder.append("</html>");
		jLabel.setText(builder.toString());
	}

	public interface LayerCallback {
		/**
		 * 点击按钮回调
		 * @param btn
		 */
		void clickBtn(String btn);

		/**
		 * 点击确定按钮
		 */
		void clickOkBtn();

		/**
		 * 点击确定按钮
		 * @param dispose
		 */
		void clickOkBtn(LayerConfirmCallback dispose);

		/**
		 * 点击取消回调
		 */
		void clickCancelBtn();

	}

	public interface LayerConfirmCallback {
		/**
		 * 销毁
		 */
		void destroy();

		/**
		 * 销毁
		 * @param dialog
		 */
		void destroy(JDialog dialog);
	}

	public interface LayerLoadingCallback {
		/**
		 * 开始
		 */
		void start();

		/**
		 * 结束
		 * @param dialog
		 */
		void end(JDialog dialog);

		/**
		 * 执行
		 * @param dialog
		 */
		void handle(JDialog dialog);

	}

}
