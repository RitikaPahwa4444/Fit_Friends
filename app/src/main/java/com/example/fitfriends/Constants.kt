package com.example.fitfriends

object Constants {
    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val backTurns = ExerciseModel(
            1,
            "Back Turns",
            R.drawable.backturns,
            false,
            false
        )
        exerciseList.add(backTurns)
        val jumpingJacks = ExerciseModel(
            2,
            "Jumping Jacks",
            R.drawable.jumpingjacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)
        val sideBends = ExerciseModel(
            3,
            "Side Bends",
            R.drawable.sidebends,
            false,
            false
        )
        exerciseList.add(sideBends)
        val sideDeepSquats = ExerciseModel(
            4,
            "Side Deep Squats",
            R.drawable.sidedeepsquats,
            false,
            false
        )
        exerciseList.add(sideDeepSquats)
        val skipHops = ExerciseModel(
            5,
            "Skip Hops",
            R.drawable.skihops,
            false,
            false
        )
        exerciseList.add(skipHops)
        val prayerPush = ExerciseModel(
            6,
            "Prayer Pushes",
            R.drawable.prayerpushes,
            false,
            false
        )
        exerciseList.add(prayerPush)
        val legraises = ExerciseModel(
            7,
            "Side Leg Raises",
            R.drawable.legraises,
            false,
            false
        )
        exerciseList.add(legraises)
        val stepBackJacks = ExerciseModel(
            8,
            "Step Back Jacks",
            R.drawable.stepbackjacks,
            false,
            false
        )
        exerciseList.add(stepBackJacks)
        val kneeRaises = ExerciseModel(
            9,
            "Knee Raises",
            R.drawable.kneeraises,
            false,
            false
        )
        exerciseList.add(kneeRaises)

        return exerciseList
    }
}