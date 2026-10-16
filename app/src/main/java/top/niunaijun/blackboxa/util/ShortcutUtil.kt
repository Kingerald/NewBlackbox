package top.niunaijun.blackboxa.util

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import top.niunaijun.blackboxa.R
import top.niunaijun.blackboxa.bean.AppInfo
import top.niunaijun.blackboxa.view.main.ShortcutActivity

object ShortcutUtil {

    fun createShortcut(context: Context, userID: Int, info: AppInfo) {
        if (!ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            toast(R.string.cannot_create_shortcut)
            return
        }

        val icon = info.icon
        if (icon == null) {
            toast(R.string.cannot_create_shortcut)
            return
        }

        val versionSuffix = ".C_v${userID + 1}"
        val labelName = if (info.name.endsWith(versionSuffix)) {
            info.name
        } else {
            "${info.name}$versionSuffix"
        }

        val intent = Intent(context, ShortcutActivity::class.java)
            .setAction(Intent.ACTION_MAIN)
            .putExtra("pkg", info.packageName)
            .putExtra("userId", userID)

        val shortcutInfo = ShortcutInfoCompat.Builder(context, "${info.packageName}-user$userID")
            .setIntent(intent)
            .setShortLabel(labelName)
            .setLongLabel(labelName)
            .setIcon(IconCompat.createWithBitmap(icon.toBitmap()))
            .build()

        ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null)
    }
}
