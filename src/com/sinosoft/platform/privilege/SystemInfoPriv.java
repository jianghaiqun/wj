package com.sinosoft.platform.privilege;

public class SystemInfoPriv extends AbstractMenuPriv {
	public static final String MenuID = "Platform.SystemInfo";
	public static final String ChangeLoginStatus = "Platform.SystemInfo.ChangeLoginStatus";
	public static final String ForceExit = "Platform.SystemInfo.ForceExit";
	public static final String Export = "Platform.SystemInfo.Export";
	public static final String Import = "Platform.SystemInfo.Import";

	public SystemInfoPriv() {
		super("Platform.SystemInfo", null);
		addItem("Platform.SystemInfo.ChangeLoginStatus", "@{SysInfo.AllowLogin}");
		addItem("Platform.SystemInfo.ForceExit", "@{SysInfo.LogoutAll}");
		addItem("Platform.SystemInfo.Export", "@{SysInfo.ExportDB}");
		addItem("Platform.SystemInfo.Import", "@{SysInfo.ImportDB}");
	}
}