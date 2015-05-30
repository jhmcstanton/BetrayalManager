package com.jhstanton.BetrayalManager

//import android.app.Activity
import android.content.{Context, Intent}
import android.os.Bundle
//import android.util.AttributeSet
import android.view.{View, LayoutInflater}// ViewGroup, View}
import android.view.ViewGroup.{LayoutParams}
import android.graphics.Color
import android.widget.LinearLayout//{Button, TextView, LinearLayout}
//import android.support.v4.app.Fragment;

import org.scaloid.common._

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterSheet extends SActivity {
  def apply()     = new CharacterSheet()
  val speed_key     = "Speed"
  val might_key     = "Might"
  val knowledge_key = "Knowledge"
  val sanity_key    = "Sanity"
}
class CharacterSheet extends SActivity {
  val params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT
					      , LayoutParams.MATCH_PARENT
					      , 1.0f)
  val stats = scala.collection.mutable.Map[String, Int]()
  onCreate {
    val center = 0x11
    val intent : Intent = getIntent
    val name : String = intent.getStringExtra(CharacterMenuActivity.EXTRA_MESSAGE)
    // these two values are provided by CharacterSheet when it refreshes itself for + or - btn presses
    val scrollEnvironment = new SScrollView()
    val topView = new SVerticalLayout()
    scrollEnvironment += topView
    characters.find(_.name == name) match {
      case None => alert("Key Error", "Unable to find character: " + name)
      case Some(character) => {
	topView += new STextView(character.name).gravity(center)
        topView += new STextView("Birthday: " + character.birthday + " Age: " + character.age).gravity(center)
	topView += new STextView(character.hobbies).gravity(center)
	
	character.statMap.foreach { pair => this.stats += pair }
	val skillView = new SLinearLayout()
	skillView.setLayoutParams(params)
	List(character.speedStats, character.mightStats, character.knowledgeStats, character.sanityStats)
	  .zip(character.statMap.toList)              //List("Speed", "Might", "Knowledge", "Sanity"))
	  .foreach{ case(stats, (statName, statIndex)) =>
		    val statView = new SVerticalLayout() //.Weight(1.0f)
		    statView.setLayoutParams(params)
		    statView += new STextView(statName).gravity(center)
//		    val indexToHighlight = intent.getIntExtra(statName, character.statMap(statName))
		    val enumeratedStats = stats.zip(0 to 8)
		    val statValViews = enumeratedStats map { case (statValue, index) => 
		      val valView = new STextView(statValue.toString).gravity(center)
		      if (index == this.stats(statName)){
//			valView.setBackgroundColor(Color.DKGRAY)
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
		  }
	topView += skillView
      }	
    }
    contentView = scrollEnvironment
  }
  val characters : Array[Character] = Array(
    new Character("Heather Granville", 18, "August 2nd", "Television, Shopping", 5, 5, 5 , 3, Array(8, 7, 6, 6, 5, 4, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 3, 0), Array(6, 6, 6, 5, 4, 3, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 2, 0)),
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
