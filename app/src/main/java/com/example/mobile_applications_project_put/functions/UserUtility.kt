package com.example.mobile_applications_project_put.functions

object UserUtility {
    fun calculateBMI(weight: Double, height: Int?): Double {
        val heightInMeters = height?.div(100.0)

        if (heightInMeters != null) {
            return weight / (heightInMeters * heightInMeters)
        }
        return 0.0
    }
}
