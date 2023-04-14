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
            "bIthHcAxehg",
            45,
            84,
            false,
            false
        )
        listofExercises.add(backTurns)
        val jumpingJacks = ExerciseModel(
            2,
            "Jumping Jacks",
            R.drawable.jumpingjacks,
            "bIthHcAxehg",
            233,
            267,
            false,
            false
        )
        listofExercises.add(jumpingJacks)
        val sideBends = ExerciseModel(
            3,
            "Side Bends",
            R.drawable.sidebends,
            "V61R5rGhiKk",
            369,
            379,
            false,
            false
        )
        listofExercises.add(sideBends)
        val sideDeepSquats = ExerciseModel(
            4,
            "Side Deep Squats",
            R.drawable.sidedeepsquats,
            "bIthHcAxehg",
            163,
            203,
            false,
            false
        )
        listofExercises.add(sideDeepSquats)
        val skipHops = ExerciseModel(
            5,
            "Ski Hops",
            R.drawable.skihops,
            "bIthHcAxehg",
            115,
            136,
            false,
            false
        )
        listofExercises.add(skipHops)
        val prayerPush = ExerciseModel(
            6,
            "Prayer Pushes",
            R.drawable.prayerpushes,
            "V61R5rGhiKk",
            216,
            239,
            false,
            false
        )
        listofExercises.add(prayerPush)
        val legraises = ExerciseModel(
            7,
            "Side Leg Raises",
            R.drawable.legraises,
            "V61R5rGhiKk",
            318,
            328,
            false,
            false
        )
        listofExercises.add(legraises)
        val stepBackJacks = ExerciseModel(
            8,
            "Step Back Jacks",
            R.drawable.stepbackjacks,
            "B9a3GzZvlcI",
            597,
            627,
            false,
            false
        )
        listofExercises.add(stepBackJacks)
        val kneeRaises = ExerciseModel(
            9,
            "Knee Raises",
            R.drawable.kneeraises,
            "V61R5rGhiKk",
            137,
            147,
            false,
            false
        )
        listofExercises.add(kneeRaises)
        return listofExercises
    }
}