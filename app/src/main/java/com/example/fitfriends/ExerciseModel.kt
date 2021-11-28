package com.example.fitfriends

class ExerciseModel (
    private var id: Int,
    private var name:String,
    private var image: Int,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false
    ){
    fun getId(): Int {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }
    fun getIsCompleted():Boolean{
        return isCompleted
    }
    fun setIsCompleted(isComplete: Boolean){
        this.isCompleted = isCompleted
    }
    fun getIsSelected():Boolean{
        return isSelected
    }
    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
    fun setName(name: String){
        this.name = name
    }
    fun getName(): String{
        return name
    }
    fun getImage():Int{
        return image
    }
    fun setImage(image: Int){
        this.image = image
    }
}