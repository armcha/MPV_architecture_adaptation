package io.github.armcha.architecturesampleproject.ui.base

import android.support.v7.app.AppCompatActivity


/**
 * NeverForget
 *
 * Created by Arman Chatikyan on 26 Mar 2018
 * Company - Volo LLC
 */

interface Injector<A:AppCompatActivity> {

    val activity:A
}