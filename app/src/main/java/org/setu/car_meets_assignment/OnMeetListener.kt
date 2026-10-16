package org.setu.car_meets_assignment

import org.setu.car_meets_assignment.models.CarmeetModel

interface OnMeetListener {
    fun onMeetClick(meet: CarmeetModel)
    fun onMeetLongClick(meet: CarmeetModel): Boolean
}