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
		
		setLayout(null); // �������ֹ�����
		this.setBackground(Color.white); // ���ñ���ɫ
		this.setForeground(Color.black); // �趨������ɫ
		for (int i = 1; i < 10; i++) // ����ѭ�������򲼾������ӱ�ǩ
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
		FileName = "000001 "; // ��������ʱ��Ĭ�Ϲ�Ʊ����
		Name_Str = "��ָ֤�� ";
		this.add(text1); // �򲼾��������ı���
		text1.reshape(150, 385, 70, 20);
		text1.getText(); // ȡ���ı��������������
	}

	public void start() { // �������������߳�
		if (M_pointThread == null) {
			M_pointThread = new Thread(this);
			M_pointThread.start();
		}
	}

	public void run() { // ���ж��߳�
		Graphics M_graphics;
		M_graphics = getGraphics();
		M_graphics.setXORMode(Color.white);
		while (true) // ���ơ�ʮ�֡��α�
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

	public boolean action(Event evt, Object arg) // ����¼�
	{
		FileName = text1.getText(); // ȡ�����ļ���
		repaint();
		// ���������ָ���ʼ״̬
		Name_Change = true;
		Move_Length = 0;
		Move_Sum = 0;
		MaxLength = 0;
		MaxCJL = 0;
		return true;
	}

	public boolean keyDown(Event evt, int key) // �������¼�
	{
		if (key == 10) // ����Ƿ��ǻس���
		{
			Event event = new Event(text1, Event.ACTION_EVENT, "text1 ");
			deliverEvent(event);
			return true;
		}
		return false;
	}

	public boolean mouseDown(Event evt, int x_d, int y_d) // ��ⰴ����갴���¼�
	{
		x_move0 = x_d; // ��¼��굱ǰ�ĺ�����
		x0 = x_d; // �ѵ�ǰ���괫�ݸ���һ�߳�
		y0 = y_d;
		return true;
	}

	public boolean mouseUp(Event evt, int x_up, int y_up) // ����ͷ���갴���¼�
	{
		int Screen = 520; // ������ҳ���ϵ���ʾ����
		x_move1 = x_up; // ��¼��굱ǰ�ĺ�����
		Move_Length = ((int) (x_move1 - x_move0) / 10) * 10; // ��������ƶ�����
		Move_Sum = Move_Sum + Move_Length;
		if (Move_Sum < 0) {
			Move_Sum = 0;
		} // ����K�ߵ��϶���Χ
		if (Move_Sum > MaxLength - Screen) {
			Move_Sum = MaxLength - Screen - 10;
		}
		x0 = x_up; // �ѵ�ǰ���괫�ݸ���һ�߳�
		y0 = y_up;
		MaxWorth = JG[Move_Sum];
		MinWorth = JD[Move_Sum];
		MaxCJL = CJL[Move_Sum];
		// ����ѭ�����ڲ���ҳ����ʾ�����ڵ���ߡ����ֵ���ɽ���
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
		for (int i = 1; i < 7; i++) // �����������������ʾ���м�ֵ
		{
			Mid_Worth[i] = MinWorth + (MaxWorth - MinWorth) / 7 * (7 - i);
		}
		repaint();
		return true;
	}

	public boolean mouseMove(Event evt, int x_m, int y_m) // �������ƶ��¼�
	{
		int Axsis, MNumberR;
		Mouse_Move = true;
		Axsis = 560 + Move_Sum - ((int) (x_m / 10)) * 10; // �趨�����귶Χ
		if (Axsis > MaxLength) {
			Axsis = MaxLength;
		}
		// �����������ڣ�����굱ǰ�����괦�ĸ��ֹ�Ʊ������ʾ����Ӧ�ı�ǩ��
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
			X = x_m; // �ѵ�ǰ���괫�ݸ���һ�߳�
			Y = y_m;
		}
		return true;
	}

	public void stop() // ��ҳ��رջ�ת������ҳ��ʱ���ͷ�ϵͳ��Դ
	{
		if (M_pointThread != null) {
			M_pointThread.stop();
			M_pointThread = null;
		}
	}

	public void paint(Graphics g) // ��ͼ����
	{
		if (Name_Change == true) // ���ı��˲�ѯ��Ʊ�Ĵ���
		{
			ReadData(); // �����¶�ȡ����
			Name_Change = false;
		}
		PaintFrame(g); // ���û���ҳ�����򷽷�
		PaintData(g); // ���û���K�����ݷ���
		x0 = -1;
		y0 = -1;
	}

	private void PaintFrame(Graphics g) // ����ҳ�����򷽷�
	{
		int nWidth = 600;
		int nHeight = 260;
		g.setColor(Color.white);
		g.fillRect(0, 0, 600, 420);
		g.setColor(Color.black);
		g.fillRect(50, 30, 550, 350);
		g.setColor(Color.white);
		g.drawLine(50, nHeight, nWidth, nHeight); // ���������������ķָ���
		g.setColor(Color.black); // ����������ɫ
		for (int i = 1; i < 7; i++) // ����������м���ֵ
		{
			g.drawString(String.valueOf((float) Mid_Worth[i] / 100), 10,
					55 + i * 30);
		}
		g.drawString(String.valueOf((int) MaxCJL), 2, 270);
		g.drawString(String.valueOf((int) MidCJL), 8, 325);
		g.drawString("0 ", 40, 380);
	}

	public void PaintData(Graphics g) // ����K�����ݷ���
	{
		int SPPoint[] = new int[2000];
		int KPPoint[] = new int[2000];
		int JGPoint[] = new int[2000];
		int JDPoint[] = new int[2000];
		double CJLPoint[] = new double[2000];
		int Left_Start = 40, reDraw = 250; // ���û�ͼ��߽缰����
		int i = 0;
		int JxTemp1 = 0, JxTemp2 = 0;
		// ��������ڲ��������ڻ���̶���Ϣ
		g.setColor(Color.red);
		g.drawString("ĵ���� ", 5, 45);
		g.drawString("��Ϣ�� ", 5, 60);
		g.setColor(Color.black);
		g.drawString("���� ", 150, 20);
		g.drawString("��� ", 230, 20);
		g.drawString("��� ", 310, 20);
		g.drawString("���� ", 390, 20);
		g.drawString("�ɽ��� ", 470, 20);
		g.drawString("Ҫ��ѯ������Ʊ,�������Ʊ����,Ȼ��س��� ", 230, 400);
		for (int N = MaxLength; N >= 0; N -= 10) // ��ѭ�������ڻ�ͼ��������ͼ
		{ // �����Ʊ�������ڻ�ͼ������Ҫ��ʾ�����ֵ
			KPPoint[N] = ((KP[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			SPPoint[N] = ((SP[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			JGPoint[N] = ((JG[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			JDPoint[N] = ((JD[N] - MinWorth) * 200 / (MaxWorth - MinWorth));
			CJLPoint[N] = (CJL[N] * 120 / MaxCJL);
			for (int Num = 0; Num < 5; Num++) // �������վ���
			{
				JxTemp1 = JxTemp1 + (SPPoint[N + (Num + 1) * 10]);
				JxTemp2 = JxTemp2 + (SPPoint[N + Num * 10]);
			}
			JX_Five1 = JxTemp1 / 5;
			JX_Five2 = JxTemp2 / 5;
			JxTemp1 = 0;
			JxTemp2 = 0;
			for (int Num = 0; Num < 10; Num++) // ����ʮ�վ���
			{
				JxTemp1 = JxTemp1 + (SPPoint[N + (Num + 1) * 10]);
				JxTemp2 = JxTemp2 + (SPPoint[N + Num * 10]);
			}
			JX_Ten1 = JxTemp1 / 10;
			JX_Ten2 = JxTemp2 / 10;
			JxTemp1 = 0;
			JxTemp2 = 0;
			if (N < 520 + Move_Sum) // ��λ����ʾ�����ڣ������K��
			{
				i = i + 10; // �����갴10���ص���
				g.setColor(Color.white);
				g.drawLine(i + Left_Start, 220 - JX_Five1, i + Left_Start + 10,
						220 - JX_Five2);
				g.setColor(Color.yellow);
				g.drawLine(i + Left_Start, 220 - JX_Ten1, i + Left_Start + 10,
						220 - JX_Ten2);
				if (KP[N] <= SP[N]) // ������̼�С�����̼ۣ����������
				{ // ���ƿ��ľ���
					g.setColor(Color.red);
					g.drawRect(i + Left_Start, reDraw - SPPoint[N], 8,
							(SPPoint[N] - KPPoint[N]));
					// ������ߡ���ͼ�
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
					// ���·���Ӧλ�û��Ƴɽ���ʵ�ľ���
					g.fillRect(i + Left_Start, 380 - (int) CJLPoint[N], 8,
							(int) CJLPoint[N]);
				} else // ������̼۴������̼ۣ����������
				{ // ����ʵ�ľ���
					g.setColor(Color.green);
					g.fillRect(i + Left_Start, reDraw - KPPoint[N], 8,
							(KPPoint[N]) - SPPoint[N]);
					// ������ߡ���ͼ�
					g.drawLine(i + Left_Start + 4, reDraw - KPPoint[N], i
							+ Left_Start + 4, reDraw - JGPoint[N]);
					g.drawLine(i + Left_Start + 4, reDraw - SPPoint[N], i
							+ Left_Start + 4, reDraw - JDPoint[N]);
					// ���·���Ӧλ�û��Ƴɽ���ʵ�ľ���
					g.fillRect(i + Left_Start, 380 - (int) CJLPoint[N], 8,
							(int) CJLPoint[N]);
				}
				if (i > 540) {
					N = 0;
				} // �����ʾ������ȫ��������ϣ����˳�ѭ��
			}
		}
	}

	public void ReadData() // ��ȡ��Ʊ���ݵķ���
	{
		int piont = 1, Piont_Num;
		Integer tempInteger;
		Float tempFloat, Content_CJL;
		Float content_Num[] = new Float[5];
		int Count_Number[] = new int[10];
		String content;
		try { // ��λ�ļ���URL��ַ
			URL urlc = new URL("http://127.0.0.1/temp/ " + FileName + ".txt ");
			// ��������
			BufferedReader bis = new BufferedReader(new InputStreamReader(urlc
					.openStream()));
			Name_Str = bis.readLine(); // ��ȡ��Ʊ�ĺ�������
			label[1].setText(Name_Str); // ����Ӧ��ǩ����ʾ
			while ((content = bis.readLine()) != null) // ���ж�ȡ�����ı��ļ�����
			{
				Piont_Num = content.indexOf("   ", piont); // ���ҿո�λ��
				Content_Date = content.substring(1, Piont_Num); // ȡ�������ַ���
				myDate[MaxLength] = Content_Date;
				piont = Piont_Num + 1;
				for (int i = 1; i < 5; i++) // �Ӷ����һ���з������Ӧ��ֵ
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
				// ���ȡ���ɽ�������ֵ
				Content_CJL = new Float(content.substring(piont, content
						.length() - 1));
				CJL[MaxLength] = (int) Content_CJL.floatValue();
				piont = 1;
				Piont_Num = 0;
				MaxLength = MaxLength + 10; // ��ȡ��һ�У���¼����10Ϊ��λ������
			}
			MaxWorth = JG[Move_Sum];
			MinWorth = JD[Move_Sum];
			MaxCJL = CJL[Move_Sum];
			// �ҳ���ҳ�沼���ڵ���ߡ����ֵ�����ɽ���
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
			for (int i = 1; i < 7; i++) // �����м�ֵ
			{
				Mid_Worth[i] = MinWorth + (MaxWorth - MinWorth) / 7 * (7 - i);
			}
			bis.close(); // �ر�������
			repaint();
		} catch (NullPointerException npe) { // ��׽���ܳ��ֵ��쳣
		} catch (IOException e) {
		}
	}
}