package com.example.fitfriends

/**
 * This file contains an object defining the list of exercises to be performed
 **/
object Constants {
    fun ListOfExercises(): ArrayList<ExerciseModel> {
        val listofExercises = ArrayList<ExerciseModel>()
        val backTurns = ExerciseModel(
            1,
            "Back Turns",
            R.drawable.backturns,
            "add video",
            false,
            false
        )
        listofExercises.add(backTurns)
        val jumpingJacks = ExerciseModel(
            2,
            "Jumping Jacks",
            R.drawable.jumpingjacks,
            "add video",
            false,
            false
        )
        listofExercises.add(jumpingJacks)
        val sideBends = ExerciseModel(
            3,
            "Side Bends",
            R.drawable.sidebends,
            "add video",
            false,
            false
        )
        listofExercises.add(sideBends)
        val sideDeepSquats = ExerciseModel(
            4,
            "Side Deep Squats",
            R.drawable.sidedeepsquats,
            "add video",
            false,
            false
        )
        listofExercises.add(sideDeepSquats)
        val skipHops = ExerciseModel(
            5,
            "Skip Hops",
            R.drawable.skihops,
            "add video",
            false,
            false
        )
        listofExercises.add(skipHops)
        val prayerPush = ExerciseModel(
            6,
            "Prayer Pushes",
            R.drawable.prayerpushes,
            "add video",
            false,
            false
        )
        listofExercises.add(prayerPush)
        val legraises = ExerciseModel(
            7,
            "Side Leg Raises",
            R.drawable.legraises,
            "add video",
            false,
            false
        )
        listofExercises.add(legraises)
        val stepBackJacks = ExerciseModel(
            8,
            "Step Back Jacks",
            R.drawable.stepbackjacks,
            "add video",
            false,
            false
        )
        listofExercises.add(stepBackJacks)
        val kneeRaises = ExerciseModel(
            9,
            "Knee Raises",
            R.drawable.kneeraises,
            "add video",
            false,
            false
        )
        listofExercises.add(kneeRaises)
        return listofExercises
    }
}