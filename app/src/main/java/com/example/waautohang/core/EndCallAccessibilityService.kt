package com.example.waautohang.core

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class EndCallAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        HangupCommander.attach(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not used for now
    }

    override fun onInterrupt() {}

    fun hangupNow() {
        val root = rootInActiveWindow ?: return
        val targets = mutableListOf<AccessibilityNodeInfo>()

        // Try by text
        targets += root.findAccessibilityNodeInfosByText("End call")
        targets += root.findAccessibilityNodeInfosByText("Hang up")

        // Try by content description (varies by locale/app version)
        // We’ll just traverse and look for clickable nodes with a likely description
        if (targets.isEmpty()) {
            traverse(root) { node ->
                val cd = node.contentDescription?.toString()?.lowercase() ?: ""
                if (cd.contains("end") || cd.contains("hang") || cd.contains("decline")) {
                    targets += node
                }
            }
        }

        if (targets.isNotEmpty()) {
            targets[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
            Toast.makeText(this, "Hangup clicked", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Couldn't find hangup button", Toast.LENGTH_SHORT).show()
        }
    }

    private fun traverse(node: AccessibilityNodeInfo?, visit: (AccessibilityNodeInfo) -> Unit) {
        if (node == null) return
        visit(node)
        for (i in 0 until node.childCount) {
            traverse(node.getChild(i), visit)
        }
    }
}
