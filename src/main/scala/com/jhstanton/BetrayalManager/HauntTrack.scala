package com.jhstanton.BetrayalManager

import org.scaloid.common._
import android.os.Bundle
import android.graphics.Color
import android.view.View
import android.view.ViewGroup.{LayoutParams}
import android.widget.LinearLayout
import android.content.SharedPreferences
import scala.util.Random

object HauntTrack extends SActivity {
  def apply()       = new HauntTrack()
  val HAUNT_PACKAGE = "com.jhstanton.BetrayalManager.HauntTrack"
  val HAUNT_INDEX   = HAUNT_PACKAGE + "haunt_index"
}

class HauntTrack extends SActivity {
  val numDice    : Int = 8 // 8 six sided , but numbers are 0, 1, and 2. Probability of each needs to be accounted for
  var hauntLvl   : Int = 1
  val center           =  0x11
  val hauntLvlBase     = "Haunt Level: "
  val hauntLvlSize     = 140

  val params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT
					      , LayoutParams.MATCH_PARENT
					      , 1.0f)

  override def onCreate(savedInstanceState : Bundle) {
    super.onCreate(savedInstanceState)

    val prefs = getPreferences(0)
    if (savedInstanceState == null && prefs == null)
      hauntLvl = 0
    else if(prefs == null)
      hauntLvl = savedInstanceState.getInt(HauntTrack.HAUNT_INDEX)
    else
      hauntLvl = prefs.getInt(HauntTrack.HAUNT_INDEX, 0)
    val scrollEnvironment = new SScrollView()
    val mainLayout = new SVerticalLayout()
    
    val hauntLvlView = new STextView(hauntLvl.toString).textSize(hauntLvlSize sp).gravity(center)
    hauntLvlView.setTextColor(Color.RED)
    mainLayout += hauntLvlView
    val modButtons = new SLinearLayout{
      SButton("+", update( 1, hauntLvlView) _).<<.Weight(1.0f).>>
      SButton("-", update(-1, hauntLvlView) _).<<.Weight(1.0f).>> // need to set weight
    }
    mainLayout += modButtons
    val reset = new SButton("Reset", update(1 - hauntLvl, hauntLvlView) _)
    mainLayout += reset
    scrollEnvironment += mainLayout
    setContentView(scrollEnvironment)
  }

  def update(modifier : => Int, textView: STextView)(view: View) {
    val newIndex = this.hauntLvl + modifier
    if (newIndex >= 1 && newIndex <= 10) {
      this.hauntLvl = newIndex
      textView.setText(hauntLvl.toString)
    }    
  }

  override def onSaveInstanceState(savedInstanceState: Bundle) {
    savedInstanceState.putInt(HauntTrack.HAUNT_INDEX, hauntLvl)
    super.onSaveInstanceState(savedInstanceState)
  }

  override def onBackPressed() {
    val editor : SharedPreferences.Editor = getPreferences(0).edit()
    editor.putInt(HauntTrack.HAUNT_INDEX, hauntLvl)
    editor.commit
    super.onBackPressed
  }
  /*def rollDice(resultView: View) {
    val result = (1 to 8).map(
  }*/

}

