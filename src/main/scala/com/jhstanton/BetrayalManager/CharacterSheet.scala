package com.jhstanton.BetrayalManager

import android.app.Activity
import android.content.{Context, Intent}
import android.os.Bundle
//import android.util.AttributeSet
import android.view.{View, LayoutInflater}// ViewGroup, View}
import android.view.ViewGroup.{LayoutParams}
import android.graphics.Color
import android.widget.LinearLayout//{Button, TextView, LinearLayout}
import android.content.SharedPreferences

import org.scaloid.common._

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterSheet extends SActivity {
  def apply()     = new CharacterSheet()
}

class CharacterSheet extends SActivity {
  val params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT
					      , LayoutParams.MATCH_PARENT
					      , 1.0f)
  val packageInfo = "com.jhstanton.BetrayalManager.CharacterSheet"
  val stats = scala.collection.mutable.Map[String, Int]()
  var name  = ""
  
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val center = 0x11
    val intent : Intent = getIntent
    val name : String = intent.getStringExtra(CharacterMenuActivity.EXTRA_MESSAGE)

    this.name = name // needed to save state :/, see onBackPressed

    val scrollEnvironment = new SScrollView()
    val topView = new SVerticalLayout()
    scrollEnvironment += topView

    characters.find(_.name == name) match {
      case None => alert("Key Error", "Unable to find character: " + name)
      case Some(character) => {
	topView += new STextView(character.name).gravity(center)
        topView += new STextView("Birthday: " + character.birthday + " Age: " + character.age).gravity(center)
	topView += new STextView(character.hobbies).gravity(center)
	
	// create new stats from defaults, or take a saved instance (lost focus), or take from preferences (app destroyed)
	val prefs = getSharedPreferences(packageInfo + name, 0)
	character.statMap.foreach { case (stat, value) => 
	   if (savedInstanceState == null && prefs == null) 
	    this.stats +=((stat, value)) // pull from default
	  else if(prefs == null) 
	    this.stats +=((stat, savedInstanceState.getInt(stat))) // pull from last saved instance
	  else
	    this.stats += ( (stat, prefs.getInt(stat, value) )) //pull from last created instance
	  /*if (savedInstanceState == null) 
	    this.stats +=((stat, value)) // pull from default
	  else if(prefs == null) 
	    this.stats +=((stat, savedInstanceState.getInt(stat))) // pull from last saved instance
	  else
	    this.stats += ( (stat, prefs.getInt(stat, value) )) //pull from last created instance*/
	}	
	  
	val skillView = new SLinearLayout()
	skillView.setLayoutParams(params)
	val resetFunctions = List(character.speedStats, character.mightStats, character.knowledgeStats, character.sanityStats)
	  .zip(character.statMap.toList)              //List("Speed", "Might", "Knowledge", "Sanity"))
	  .map{ case(stats, (statName, statIndex)) =>
		    val statView = new SVerticalLayout() 
		    statView.setLayoutParams(params)
		    statView += new STextView(statName).gravity(center)
		    val enumeratedStats = stats.zip(0 to 8)
		    val statValViews = enumeratedStats map { case (statValue, index) => 
		      val valView = new STextView(statValue.toString).gravity(center)
		      if (index == this.stats(statName)){
			valView.setTextColor(Color.RED)
		      }
		      statView += valView
		      valView
		    }
		   
		  
		    def updater(modifier: Int)(v: View) {
		      val originalIndex = this.stats(statName)
		      val newIndex      = originalIndex + modifier
		      if (newIndex >= 0 && newIndex <= 8) {
			statValViews(this.stats(statName)).setTextColor(Color.WHITE)
			this.stats.+=((statName, this.stats(statName) + modifier))
			statValViews(this.stats(statName)).setTextColor(Color.RED)
		      }
		    }

		    val plusButton  = new SButton("+", updater(-1) _).gravity(center)
		    val minusButton = new SButton("-", updater( 1) _).gravity(center)

		    statView += plusButton
		    statView += minusButton
		    skillView += statView
		    // returns a reset function
		    (statName, (originalIndex: Int) => {
		      statValViews(this.stats(statName)).setTextColor(Color.WHITE)
		      this.stats += ( (statName, originalIndex) )
		      statValViews(this.stats(statName)).setTextColor(Color.RED)
		    })
		  }
	topView += skillView
	val resetBtn = new SButton("Reset", (view: View) => resetFunctions.foreach{ 
	  case (statName, resetter) => resetter(character.statMap(statName))
	})
	topView += resetBtn
     }	
    }
    setContentView(scrollEnvironment) 
  }

  override def onSaveInstanceState(savedInstanceState: Bundle) {
    this.stats.foreach{ case (statName, statVal) => savedInstanceState.putInt(statName, statVal) }
    super.onSaveInstanceState(savedInstanceState)
  }

  override def onBackPressed() {
    val editor : SharedPreferences.Editor = getSharedPreferences(packageInfo + name, 0).edit()  // 0 specifies private mode
    stats foreach { case (statName, value) => editor.putInt(statName, value) }
    editor.commit
    super.onBackPressed()
  }
  // these should probably be moved elsewhere..
  val characters : Array[Character] = Array(
    new Character("Heather Granville", 18, "August 2nd", "Television, Shopping", 5, 5, 5, 3, Array(8, 7, 6, 6, 5, 4, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 3, 0), Array(6, 6, 6, 5, 4, 3, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 2, 0)),
    new Character("Jenny LeClerc", 21, "March 4th", "Reading, Soccer", 4, 5, 3, 5, Array(8, 6, 5, 4, 4, 4, 3, 2, 0), Array(8, 6, 5, 4, 4, 4, 4, 3, 0), Array(6, 5, 4, 4, 4, 2, 1, 1, 0), Array(8, 6, 5, 4, 4, 3, 3, 2, 0)),
    new Character("Ox Bellows", 23, "October 18th", "Football, Shiny Objects", 3, 5, 5, 5, Array(6, 5, 5, 4, 3, 2, 2, 2, 0), Array(8, 8, 7, 6, 6, 5, 5, 4, 0), Array(7, 6, 5, 5, 4, 3, 2, 2, 0), Array(6, 6, 5, 5, 4, 4, 2, 2, 0)),
    new Character("Darrin 'Flash' Williams", 20, "June 6th", "Track, Music, Shakespearean Literature", 3, 5, 5, 5, Array(8, 7, 7, 6, 5, 4, 4, 4, 0), Array(7, 6, 6, 5, 4, 3, 3, 2, 0), Array(7, 5, 5, 5, 4, 3, 2, 1, 0), Array(7, 5, 5, 5, 4, 3, 3, 2, 0)),
    new Character("Vivian Lopez", 42, "January 11th", "Old Movies, Horses", 4, 5, 5, 4, Array(8, 7, 6, 4, 4, 4, 4, 3, 0), Array(6, 6, 5, 4, 4, 2, 2, 2, 0), Array(8, 8, 7, 6, 5, 4, 4, 4, 0), Array(7, 6, 6, 5, 5, 5, 5, 4, 0)),
    new Character("Madame Zostra", 37, "December 10th", "Astrology, Cooking, Baseball", 5, 4, 5, 4, Array(7, 6, 6, 5, 5, 3, 3, 2, 0), Array(6, 5, 5, 5, 4, 3, 3, 2, 0), Array(8, 8, 7, 6, 5, 4, 4, 4, 0), Array(6, 6, 5, 4, 4, 4, 3, 1, 0)),
    new Character("Missy Dubourde", 9, "February 14th", "Swimming, Medicine", 5, 4, 5, 4, Array(7, 7, 6, 6, 6, 5, 4, 3, 0), Array(7, 6, 5, 4, 3, 3, 3, 2, 0), Array(7, 6, 5, 5, 4, 3, 2, 1, 0), Array(6, 6, 6, 5, 4, 4, 3, 2, 0)),
    new Character("Zoe Ingstrom", 8, "November 5th", "Dolls, Music", 4, 4, 5, 5, Array(8, 8, 6, 5, 4, 4, 4, 4, 0), Array(7, 6, 4, 4, 3, 3, 2, 2, 0), Array(8, 7, 6, 6, 5, 5, 4, 3, 0), Array(5, 5, 5, 4, 4, 3, 2, 1, 0)),
    new Character("Peter Akimoto", 13, "September 3rd", "Bugs, Basketball", 4, 5, 4, 5, Array(7, 7, 6, 6, 4, 3, 3, 3, 0), Array(8, 6, 5, 5, 4, 3, 3, 2, 0), Array(7, 6, 6, 5, 4, 4, 4, 3, 0), Array(8, 7, 7, 6, 5, 4, 4, 3, 0)),
    new Character("Brandon Jaspers", 12, "May 21st", "Computers, Camping, Hockey", 5, 4, 4, 5, Array(8, 7, 6, 5, 4, 4, 4, 3, 0), Array(7, 6, 6, 5, 4, 3, 3, 2, 0), Array(8, 7, 6, 5, 4, 3, 3, 3, 0), Array(7, 6, 6, 5, 5, 4, 4, 1, 0)),
    new Character("Professor Longfellow", 57, "July 27th", "Gaelic Music, Drama, Fine Wines", 4, 5, 5, 7, Array(6, 6, 5, 5, 4, 4, 2, 2, 0), Array(6, 6, 5, 5, 4, 3, 2, 1, 0), Array(7, 6, 5, 5, 4, 3, 3, 1, 0), Array(8, 7, 6, 5, 5, 5, 5, 4, 0)),
    new Character("Father Rhinehardt", 62, "April 29th", "Fencing, Gardening", 5, 5, 3, 4, Array(7, 7, 6, 5, 4, 3, 3, 2, 0), Array(7, 5, 5, 4, 4, 2, 2, 1, 0), Array(8, 7, 7, 6, 5, 5, 4, 3, 0), Array(8, 6, 6, 5, 4, 3, 3, 1, 0))
  )
}

class Character(val name: String,
                val age: Int,
                val birthday: String,
                val hobbies: String,
                    speed: Int, // these four stats are indexes into the stat lists, and now are stored in a map :/
                    might: Int,
                    knowledge: Int,
                    sanity: Int,
                val speedStats: Array[Int],
                val mightStats: Array[Int],
                val knowledgeStats: Array[Int],
                val sanityStats: Array[Int]){
  val statMap = // useful for checking the character stats using keys from the intent
    Map("Speed" -> speed, "Might" -> might, "Knowledge" -> knowledge, "Sanity" -> sanity)
}
