package com.xuln.stock;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class StockApplet extends Applet implements Runnable {
	int Move_Length = 0, Move_Sum = 0;
	String FileName, Name_Str, Content_Date;
	int SP[] = new int[2000];
	int KP[] = new int[2000];
	int JD[] = new int[2000];
	int JG[] = new int[2000];
	int Mid_Worth[] = new int[2000];
	String myDate[] = new String[2000];
	double CJL[] = new double[2000];
	double MaxCJL, MidCJL;
	Label label[] = new Label[10];
	int MaxWorth, MinWorth;
	int x_move0, x_move1, MaxLength = 0;
	int x0, y0, X, Y, Record_Num;
	boolean Mouse_Move, Name_Change = true;
	int JX_Five1, JX_Five2, JX_Ten1, JX_Ten2;
	Thread M_pointThread ;
	TextField text1;
	public void init() {
		TextField text1 = new TextField();
		
		setLayout(null); // 声明布局管理器
		this.setBackground(Color.white); // 设置背景色
		this.setForeground(Color.black); // 设定文字颜色
		for (int i = 1; i < 10; i++) // 以下循环用于向布局中添加标签
		{
			label[i] = new Label();
			this.add(label[i]);
			label[i].reshape(i * 80 - 65, 10, 50, 15);
			if (i == 2) {
				label[i].reshape(80, 10, 70, 15);
			}
			if (i == 7) {
				label[i].reshape(510, 10, 80, 15);
			}
			if (i > 7) {
				label[i].reshape((i - 8) * 490 + 45, 380, 70, 15);
			}
		}
		FileName = "000001 "; // 程序启动时的默认股票代码
		Name_Str = "上证指数 ";
		this.add(text1); // 向布局中添加文本框
		text1.reshape(150, 385, 70, 20);
		text1.getText(); // 取得文本框中输入的内容
	}

	public void start() { // 创建并启动多线程
		if (M_pointThread == null) {
			M_pointThread = new Thread(this);
			M_pointThread.start();
		}
	}

	public void run() { // 运行多线程
		Graphics M_graphics;
		M_graphics = getGraphics();
		M_graphics.setXORMode(Color.white);
		while (true) // 绘制“十字”游标
		{
			try {
				if (Mouse_Move == true) {
					if (x0 > 50 & x0 < 600) {
						M_graphics.drawLine(x0, 30, x0, 380);
					}
					if (y0 > 30 & y0 < 380) {
						M_graphics.drawLine(50, y0, 600, y0);
					}
					if (X > 50 & X < 600) {
						M_graphics.drawLine(X, 30, X, 380);
					}
					if (Y > 30 & Y < 380) {
						M_graphics.drawLine(50, Y, 600, Y);
					}
					Mouse_Move = false;
					x0 = X;
					y0 = Y;
				}
			} catch (NullPointerException npe) {
			}
		}
	}

	public boolean action(Event evt, Object arg) // 监测事件
	{
		FileName = text1.getText(); // 取得新文件名
		repaint();
		// 将各变量恢复初始状态
		Name_Change = true;
		Move_Length = 0;
		Move_Sum = 0;
		MaxLength = 0;
		MaxCJL = 0;
		return true;
	}

	public boolean keyDown(Event evt, int key) // 检测键盘事件
	{
		if (key == 10) // 检测是否是回车键
		{
			Event event = new Event(text1, Event.ACTION_EVENT, "text1 ");
			deliverEvent(event);
			return true;
		}
		return false;
	}

	public boolean mouseDown(Event evt, int x_d, int y_d) // 检测按下鼠标按键事件
	{
		x_move0 = x_d; // 记录鼠标当前的横座标
		x0 = x_d; // 把当前座标传递给另一线程
		y0 = y_d;
		return true;
	}

	public boolean mouseUp(Event evt, int x_up, int y_up) // 检测释放鼠标按键事件
	{
		int Screen = 520; // 定义在页面上的显示区域
		x_move1 = x_up; // 记录鼠标当前的横座标
		Move_Length = ((int) (x_move1 - x_move0) / 10) * 10; // 计算鼠标移动距离
		Move_Sum = Move_Sum + Move_Length;
		if (Move_Sum < 0) {
			Move_Sum = 0;
		} // 控制K线的拖动范围
		if (Move_Sum > MaxLength - Screen) {
			Move_Sum = MaxLength - Screen - 10;
		}
		x0 = x_up; // 把当前座标传递给另一线程
		y0 = y_up;
		MaxWorth = JG[Move_Sum];
		MinWorth = JD[Move_Sum];
		MaxCJL = CJL[Move_Sum];
		// 以下循环用于查找页面显示区域内的最高、最低值及成交量
		for (int i = Screen + Move_Sum; i > Move_Sum; i -= 10) {
			if (MaxWorth < JG[i]) {
				MaxWorth = JG[i];
			}
			if (MinWorth > JD[i]) {
				MinWorth = JD[i];
			}
			if (MaxCJL < CJL[i]) {
				MaxCJL = CJL[i];
			}
		}
		MidCJL = MaxCJL / 2;
		for (int i = 1; i < 7; i++) // 计算出在纵座标上显示的中间值
		{
			Mid_Worth[i] = MinWorth + (MaxWorth - MinWorth) / 7 * (7 - i);
		}
		repaint();
		return true;
	}

	public boolean mouseMove(Event evt, int x_m, int y_m) // 检测鼠标移动事件
	{
		int Axsis, MNumberR;
		Mouse_Move = true;
		Axsis = 560 + Move_Sum - ((int) (x_m / 10)) * 10; // 设定横座标范围
		if (Axsis > MaxLength) {
			Axsis = MaxLength;
		}
		// 在以下区域内，把鼠标当前横座标处的各种股票参数显示在相应的标签上
		if (x_m > 50 & x_m < 600) {
			label[2].setText(myDate[Axsis]);
			label[3].setText(String.valueOf((float) KP[Axsis] / 100));
			label[4].setText(String.valueOf((float) JG[Axsis] / 100));
			label[5].setText(String.valueOf((float) JD[Axsis] / 100));
			label[6].setText(String.valueOf((float) SP[Axsis] / 100));
			label[7].setText(String.valueOf((int) CJL[Axsis]));
			label[8].setText(myDate[510 + Move_Sum]);
			MNumberR = Move_Sum;
			if (Move_Sum > 30) {
				MNumberR = Move_Sum - 30;
			}
			label[9].setText(myDate[MNumberR]);
			X = x_m; // 把当前座标传递给另一线程
			Y = y_m;
		}
		return true;
	}

	public void stop() // 当页面关闭或转向其他页面时，释放系统资源
	{
		if (M_pointThread != null) {
			M_pointThread.stop();
			M_pointThread = null;
		}
	}

	public void paint(Graphics g) // 绘图方法
	{
		if (Name_Change == true) // 若改变了查询股票的代码
		{
			ReadData(); // 则重新读取数据
			Name_Change = false;
		}
		PaintFrame(g); // 调用绘制页面区域方法
		PaintData(g); // 调用绘制K线数据方法
		x0 = -1;
		y0 = -1;
	}

	private void PaintFrame(Graphics g) // 绘制页面区域方法
	{
		int nWidth = 600;
		int nHeight = 260;
		g.setColor(Color.white);
		g.fillRect(0, 0, 600, 420);
		g.setColor(Color.black);
		g.fillRect(50, 30, 550, 350);
		g.setColor(Color.white);
		g.drawLine(50, nHeight, nWidth, nHeight); // 画出上下两区域间的分隔线
		g.setColor(Color.black); // 设置字体颜色
		for (int i = 1; i < 7; i++) // 绘出纵座标中间数值
		{
			g.drawString(String.valueOf((float) Mid_Worth[i] / 100), 10,
					55 + i * 30);
		}
		g.drawString(String.valueOf((int) MaxCJL), 2, 270);
		g.drawString(String.valueOf((int) MidCJL), 8, 325);
		g.drawString("0 ", 40, 380);
	}

	public void PaintData(Graphics g) // 绘制K线数据方法
	{
		int SPPoint[] = new int[2000];
		int KPPoint[] = new int[2000];
		int JGPoint[] = new int[2000];
		int JDPoint[] = new int[2000];
		double CJLPoint[] = new double[2000];
		int Left_Start = 40, reDraw = 250; // 设置绘图左边界及参数
		int i = 0;
		int JxTemp1 = 0, JxTemp2 = 0;
		// 以下语句在布局区域内绘出固定信息
		g.setColor(Color.red);
		g.drawString("牡丹江 ", 5, 45);
		g.drawString("信息港 ", 5, 60);
		g.setColor(Color.black);
		g.drawString("开盘 ", 150, 20);
		g.drawString("最高 ", 230, 20);
		g.drawString("最低 ", 310, 20);
		g.drawString("收盘 ", 390, 20);
		g.drawString("成交量 ", 470, 20);
		g.drawString("要查询其他股票,请输入股票代码,然后回车。 ", 230, 400);
		for (int N = MaxLength; N >= 0; N -= 10) // 该循环用于在绘图区域内作图
		{ // 计算股票各参数在绘图区域内要显示的相对值
			KPPoint[N] = ((KP[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			SPPoint[N] = ((SP[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			JGPoint[N] = ((JG[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			JDPoint[N] = ((JD[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			CJLPoint[N] = (CJL[N] * 120 / MaxCJL);
			for (int Num = 0; Num < 5; Num++) // 计算五日均线
			{
				JxTemp1 = JxTemp1 + (SPPoint[N + (Num + 1) * 10]);
				JxTemp2 = JxTemp2 + (SPPoint[N + Num * 10]);
			}
			JX_Five1 = JxTemp1 / 5;
			JX_Five2 = JxTemp2 / 5;
			JxTemp1 = 0;
			JxTemp2 = 0;
			for (int Num = 0; Num < 10; Num++) // 计算十日均线
			{
				JxTemp1 = JxTemp1 + (SPPoint[N + (Num + 1) * 10]);
				JxTemp2 = JxTemp2 + (SPPoint[N + Num * 10]);
			}
			JX_Ten1 = JxTemp1 / 10;
			JX_Ten2 = JxTemp2 / 10;
			JxTemp1 = 0;
			JxTemp2 = 0;
			if (N < 520 + Move_Sum) // 若位于显示区域内，则绘制K线
			{
				i = i + 10; // 横座标按10象素递增
				g.setColor(Color.white);
				g.drawLine(i + Left_Start, 220 - JX_Five1, i + Left_Start + 10,
						220 - JX_Five2);
				g.setColor(Color.yellow);
				g.drawLine(i + Left_Start, 220 - JX_Ten1, i + Left_Start + 10,
						220 - JX_Ten2);
				if (KP[N] <= SP[N]) // 如果开盘价小于收盘价，则绘制阳线
				{ // 绘制空心矩形
					g.setColor(Color.red);
					g.drawRect(i + Left_Start, reDraw - SPPoint[N], 8,
							(SPPoint[N] - KPPoint[N]));
					// 绘制最高、最低价
					if (SPPoint[N] == KPPoint[N]) {
						g.drawRect(i + Left_Start, reDraw - SPPoint[N], 8, 1);
					}
					if (JGPoint[N] > SPPoint[N]) {
						g.drawLine(i + Left_Start + 4, reDraw - SPPoint[N], i
								+ Left_Start + 4, reDraw - JGPoint[N]);
					}
					if (JDPoint[N] < KPPoint[N]) {
						g.drawLine(i + Left_Start + 4, reDraw - JDPoint[N], i
								+ Left_Start + 4, reDraw - KPPoint[N]);
					}
					// 在下方对应位置绘制成交量实心矩形
					g.fillRect(i + Left_Start, 380 - (int) CJLPoint[N], 8,
							(int) CJLPoint[N]);
				} else // 如果开盘价大于收盘价，则绘制阴线
				{ // 绘制实心矩形
					g.setColor(Color.green);
					g.fillRect(i + Left_Start, reDraw - KPPoint[N], 8,
							(KPPoint[N]) - SPPoint[N]);
					// 绘制最高、最低价
					g.drawLine(i + Left_Start + 4, reDraw - KPPoint[N], i
							+ Left_Start + 4, reDraw - JGPoint[N]);
					g.drawLine(i + Left_Start + 4, reDraw - SPPoint[N], i
							+ Left_Start + 4, reDraw - JDPoint[N]);
					// 在下方对应位置绘制成交量实心矩形
					g.fillRect(i + Left_Start, 380 - (int) CJLPoint[N], 8,
							(int) CJLPoint[N]);
				}
				if (i > 540) {
					N = 0;
				} // 如果显示区域已全部绘制完毕，则退出循环
			}
		}
	}

	public void ReadData() // 读取股票数据的方法
	{
		int piont = 1, Piont_Num;
		Integer tempInteger;
		Float tempFloat, Content_CJL;
		Float content_Num[] = new Float[5];
		int Count_Number[] = new int[10];
		String content;
		try { // 定位文件的URL地址
			URL urlc = new URL("http://127.0.0.1/temp/ " + FileName + ".txt ");
			// 打开数据流
			BufferedReader bis = new BufferedReader(new InputStreamReader(urlc
					.openStream()));
			Name_Str = bis.readLine(); // 读取股票的汉字名称
			label[1].setText(Name_Str); // 在相应标签内显示
			while ((content = bis.readLine()) != null) // 按行读取整个文本文件内容
			{
				Piont_Num = content.indexOf("   ", piont); // 查找空格位置
				Content_Date = content.substring(1, Piont_Num); // 取出日期字符串
				myDate[MaxLength] = Content_Date;
				piont = Piont_Num + 1;
				for (int i = 1; i < 5; i++) // 从读入的一行中分离出对应数值
				{
					Piont_Num = content.indexOf("   ", piont);
					content_Num[i] = new Float(content.substring(piont,
							Piont_Num));
					piont = Piont_Num + 1;
				}
				KP[MaxLength] = (int) (content_Num[1].floatValue() * 100);
				JG[MaxLength] = (int) (content_Num[2].floatValue() * 100);
				JD[MaxLength] = (int) (content_Num[3].floatValue() * 100);
				SP[MaxLength] = (int) (content_Num[4].floatValue() * 100);
				// 最后取出成交量的数值
				Content_CJL = new Float(content.substring(piont, content
						.length() - 1));
				CJL[MaxLength] = (int) Content_CJL.floatValue();
				piont = 1;
				Piont_Num = 0;
				MaxLength = MaxLength + 10; // 读取下一行（记录数以10为单位递增）
			}
			MaxWorth = JG[Move_Sum];
			MinWorth = JD[Move_Sum];
			MaxCJL = CJL[Move_Sum];
			// 找出在页面布局内的最高、最低值及最大成交量
			for (Record_Num = 540 + Move_Sum; Record_Num > Move_Sum; Record_Num -= 10) {
				if (MaxWorth < JG[Record_Num]) {
					MaxWorth = JG[Record_Num];
				}
				if (MinWorth > JD[Record_Num]) {
					MinWorth = JD[Record_Num];
				}
				if (MaxCJL < CJL[Record_Num]) {
					MaxCJL = CJL[Record_Num];
				}
			}
			MidCJL = MaxCJL / 2;
			for (int i = 1; i < 7; i++) // 计算中间值
			{
				Mid_Worth[i] = MinWorth + (MaxWorth - MinWorth) / 7 * (7 - i);
			}
			bis.close(); // 关闭数据流
			repaint();
		} catch (NullPointerException npe) { // 捕捉可能出现的异常
		} catch (IOException e) {
		}
	}
}
