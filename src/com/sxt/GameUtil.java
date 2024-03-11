package com.sxt;

import java.awt.*;

/* 工具類別
 * 放靜態參數
 * 靜態方法
 */

public class GameUtil {
	// 定義地雷個數
	static int Bomb_Max = 100;

	static int MAP_W = 36; // 地圖寬
	static int MAP_H = 17; // 地圖高
	static int OFFSET = 45; // 雷區偏移量
	static int SQUARE_LENGTH = 50; // 格子邊長
	static int FLAG_NUM=0; // 插旗的數量

	// 滑鼠相關參數
	// 座標
	static int MOUSE_X; 
	static int MOUSE_Y;
	// 狀態
	static boolean LEFT=false;
	static boolean RIGHT=false;
	// 遊戲狀態 0:遊戲中 1:勝利 2:失敗 3:難度選擇
	static int state=3;
	// 遊戲難度選擇
	static int level;
	
	// 計時器
	static long START_TIME;
	static long END_TIME;
	
	
	// 底層的元素 -1:地雷 0:空 1-8:對應數字
	static int[][] DATA_BOTTOM = new int[MAP_W + 2][MAP_H + 2]; // 顯示區域是1-11，但實際外圍是0-12，3*3數字格才不會越界
	// 頂層的元素 -1:無覆蓋(翻開後) 0:覆蓋(初始狀態) 1:插旗子 2:插錯旗子
	static int[][] DATA_TOP = new int[MAP_W + 2][MAP_H + 2]; // 顯示區域是1-11，但實際外圍是0-12，3*3數字格才不會越界
	// 載入圖片
	// 地雷圖
	static Image bomb = Toolkit.getDefaultToolkit().getImage("imgs/bomb.png");
	// 頂層圖(覆蓋、旗子、插錯旗）
	static Image top = Toolkit.getDefaultToolkit().getImage("imgs/blank.png");
	static Image flag = Toolkit.getDefaultToolkit().getImage("imgs/flag.png");
	static Image noflag = Toolkit.getDefaultToolkit().getImage("imgs/wrongflag.png");
	// 遊戲狀態圖
	static Image face = Toolkit.getDefaultToolkit().getImage("imgs/face6.png");
	static Image over = Toolkit.getDefaultToolkit().getImage("imgs/face8.png");
	static Image win = Toolkit.getDefaultToolkit().getImage("imgs/face9.png");
    // 數字圖
	static Image[] images=new Image[9];
	static {
		for (int i=1;i<=8;i++) {
			images[i]=Toolkit.getDefaultToolkit().getImage("imgs/"+i+".png");
	}
	}
	static void drawWord(Graphics g,String str,int x,int y,int size,Color color) {
	g.setColor(color);
	g.setFont(new Font("Arial",Font.BOLD,size));
	g.drawString(str,x,y);
	}
}
