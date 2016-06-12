package com.xuln.task;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class TaskTest extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrayIcon trayIcon = null; // 托盘图标
	private SystemTray tray = null; // 本操作系统托盘的实例

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TaskTest().lunchFrame();
	}

	public void lunchFrame() {
		setLocation(500, 300);
		this.setSize(400, 300);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowIconified(WindowEvent e) {
				try {
					tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
					// setVisible(false); // 使窗口不可视
					dispose();
				} catch (AWTException ex) {
					ex.printStackTrace();
				}
			}
		});
		this.setResizable(false);
		this.setVisible(true);

		if (SystemTray.isSupported()) { // 如果操作系统支持托盘
			this.tray();
		}
	}

	void tray() {

		tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/")
				.getPath()
				+ "0.gif"); // 将要显示到托盘中的图标

		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
		MenuItem show = new MenuItem("打开程序");
		MenuItem exit = new MenuItem("退出程序");
		pop.add(show);
		pop.add(exit);
		trayIcon = new TrayIcon(icon.getImage(), "托盘", pop);

		/**
		 * 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
		 */
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 鼠标双击
					tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					setExtendedState(JFrame.NORMAL);
					setVisible(true); // 显示窗口
					toFront();
				}
			}
		});
		show.addActionListener(new ActionListener() { // 点击“显示窗口”菜单后将窗口显示出来
					public void actionPerformed(ActionEvent e) {
						tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
						setExtendedState(JFrame.NORMAL);
						setVisible(true); // 显示窗口
						toFront();
					}
				});
		exit.addActionListener(new ActionListener() { // 点击“退出演示”菜单后退出程序
					public void actionPerformed(ActionEvent e) {
						System.exit(0); // 退出程序
					}
				});
	}

}
