package com.example.worldofhunting.other

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

fun Date.format(
    pattern: String = "dd.MM.yyyy HH:mm:ss",
    locale: Locale = Locale.getDefault()
): String {
    return SimpleDateFormat(pattern, locale).format(this)
}

fun View.addSystemWindowInsetToPadding(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updatePadding(
            left = initialLeft + (if (left) insets.systemWindowInsetLeft else 0),
            top = initialTop + (if (top) insets.systemWindowInsetTop else 0),
            right = initialRight + (if (right) insets.systemWindowInsetRight else 0),
            bottom = initialBottom + (if (bottom) insets.systemWindowInsetBottom else 0)
        )

        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.addSystemWindowInsetToMargin(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMargins(
                    left = initialLeft + (if (left) insets.systemWindowInsetLeft else 0),
                    top = initialTop + (if (top) insets.systemWindowInsetTop else 0),
                    right = initialRight + (if (right) insets.systemWindowInsetRight else 0),
                    bottom = initialBottom + (if (bottom) insets.systemWindowInsetBottom else 0)
                )
            }
        }

        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.setHeightOrWidthAsSystemWindowInset(
    side: InsetSide,
    onApplyInsets: ((inset: Int) -> Unit)? = null
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updateLayoutParams {
            when (side) {
                InsetSide.LEFT -> {
                    width =insets.systemWindowInsetLeft
                    onApplyInsets?.invoke(insets.systemWindowInsetLeft)
                }
                InsetSide.RIGHT -> {
                    width =insets.systemWindowInsetRight
                    onApplyInsets?.invoke(insets.systemWindowInsetRight)
                }
                InsetSide.BOTTOM -> {
                    height =insets.systemWindowInsetBottom
                    onApplyInsets?.invoke(insets.systemWindowInsetBottom)
                }
                InsetSide.TOP -> {
                    height =insets.systemWindowInsetTop
                    onApplyInsets?.invoke(insets.systemWindowInsetTop)
                }
            }
        }
        insets
    }
    requestApplyInsetsWhenAttached()
}
enum class InsetSide {
    LEFT, RIGHT, BOTTOM, TOP
}
fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

val Context.isInternetConnected: Boolean
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        val networks = connectivityManager.allNetworks
        var hasInternet = false
        if (networks.isNotEmpty()) {
            for (network in networks) {
                val nc = connectivityManager.getNetworkCapabilities(network)
                if (nc?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: return false)
                    hasInternet = true
            }
        }
        return hasInternet
    }

fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.getColorCompat(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

inline fun View.animate(animation: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator =
    animate().apply(animation).apply { start() }

inline fun <reified T> ValueAnimator.animatedValue() = animatedValue as T