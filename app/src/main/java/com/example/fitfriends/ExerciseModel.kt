package com.example.fitfriends

/**
 * This class is a model class and acts as a template for the list of exercises to be displayed
 **/
class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var video: String,
    private var startTime: Int,
    private var endTime: Int,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false
) {
    fun getId(): Int {
        return id
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isComplete: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }

    fun getImage(): Int {
        return image
    }

    fun getVideoId(): String{
        return video
    }

    fun getVideoStartTime(): Int {
        return startTime
    }

    fun getVideoEndTime(): Int {
        return endTime
    }
}