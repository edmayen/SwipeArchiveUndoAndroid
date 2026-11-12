package com.example.reversiblelist.utils

import com.example.reversiblelist.domain.PaymentNotification

object Utils {

    val notificationsSample = listOf(
        PaymentNotification(
            id = "N001",
            title = "Payment received",
            message = "₹2,500 received"
        ),
        PaymentNotification(
            id = "N002",
            title = "Transfer completed",
            message = "₹1,200 transferred"
        ),
        PaymentNotification(
            id = "N003",
            title = "Bill payment",
            message = "₹500 paid"
        ),
        PaymentNotification(
            id = "N004",
            title = "Reward credited",
            message = "₹100 received"
        )
    )
}