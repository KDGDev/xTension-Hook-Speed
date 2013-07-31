package com.androtech.hooktest;

import com.kdgdev.xtension.IXposedHookLoadPackage;
import com.kdgdev.xtension.XC_MethodHook;
import com.kdgdev.xtension.XposedHelpers;
import com.kdgdev.xtension.callbacks.XC_LoadPackage;
import com.kdgdev.xtension.XposedTestHelper;

public class XposedModule implements IXposedHookLoadPackage {
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
		if (!"com.androtech.hooktest".equals(lpparam.packageName)) return;
		XposedHelpers.findAndHookMethod(
                "com.androtech.hooktest.MyActivity", lpparam.classLoader, "calcMethodHooked", int.class, int[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                });


		try {
			XposedTestHelper.getEx().findAndHookFast(
                    "com.androtech.hooktest.MyActivity", lpparam.classLoader, "calcMethodHookedFast",
                    XposedModule.class, "afterCalcHook",
                    int.class, int[].class);
		} catch (Exception ignored) { }
	}

	private static int afterCalcHook(int arg, int[] args, Object thiz, int ret) {
		return 1;
	}
}
