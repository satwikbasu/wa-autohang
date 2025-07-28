package com.example.waautohang.core

import java.lang.ref.WeakReference

object HangupCommander {
    private var serviceRef: WeakReference<EndCallAccessibilityService>? = null

    fun attach(service: EndCallAccessibilityService) {
        serviceRef = WeakReference(service)
    }

    fun hangup() {
        serviceRef?.get()?.hangupNow()
    }
}
