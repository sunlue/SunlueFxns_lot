package com.server.monitor.hikvision;

import com.sun.jna.Native;
import com.sun.jna.examples.win32.W32API;

/**
 * windows gdi接口,gdi32.dll in system32 folder, 在设置遮挡区域,移动侦测区域等情况下使用
 * @author xiebing
 */
public interface GDI32 extends W32API {
	GDI32 INSTANCE = (GDI32) Native.loadLibrary("gdi32", GDI32.class, DEFAULT_OPTIONS);

	public static final int TRANSPARENT = 1;

	/**
	 *
	 * @param hdc
	 * @param i
	 * @return
	 */
	int setBkMode(HDC hdc, int i);

	/**
	 *
	 * @param icolor
	 * @return
	 */
	HANDLE createSolidBrush(int icolor);
}
