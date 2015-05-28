package com.jhstanton.BetrayalManager

//import android.app.Activity
import android.content.{Context, Intent}
//import android.os.Bundle
//import android.util.AttributeSet
import android.view.View//{LayoutInflater, ViewGroup, View}
import android.view.ViewGroup.{LayoutParams}
import android.widget.LinearLayout//{Button, TextView, LinearLayout}

import org.scaloid.common._

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterSheet extends SActivity {
  def apply() = new CharacterSheet()
}
class CharacterSheet extends SActivity {
  val params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT
					      , LayoutParams.MATCH_PARENT
					      , 1.0f)
  onCreate {
    val center = 0x11
    val intent : Intent = getIntent
    val name : String = intent.getStringExtra(CharacterMenuActivity.EXTRA_MESSAGE)
    val topView = new SVerticalLayout()
    characters.find(_.name ==name) match {
      case None => alert("Key Error", "Unable to find character: " + name)
      case Some(character) => {
	topView += new STextView(character.name).gravity(center)
        topView += new STextView("Birthday: " + character.birthday + " Age: " + character.age).gravity(center)
	topView += new STextView(character.hobbies).gravity(center)
	val skillView = new SLinearLayout()
	skillView.setLayoutParams(params)
	List(character.speedStats, character.mightStats, character.knowledgeStats, character.sanityStats)
	  .zip(List("Speed", "Might", "Knowledge", "Sanity"))
	  .foreach{ case(stats, statName) =>
		    val statView = new SVerticalLayout() //.Weight(1.0f)
		    statView.setLayoutParams(params)
		    statView += new STextView(statName).gravity(center)
		    stats.foreach((x: Int) => statView += new STextView(x.toString).gravity(center))
		    val plusButton = new SButton("+", (_: View) => ()).gravity(center)
		    val minusButton = new SButton("-", (_: View) => ()).gravity(center)
		    statView += plusButton
		    statView += minusButton
		    skillView += statView
		  }
	topView += skillView
      }	
    }
    contentView = topView
  }
  val characters : Array[Character] = Array(
    Character("Heather Granville", 18, "August 2nd", "Television, Shopping", 5, 5, 5, 3, Array(8, 7, 6, 6, 5, 4, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 3, 0), Array(6, 6, 6, 5, 4, 3, 3, 3, 0), Array(8, 7, 6, 5, 4, 3, 3, 2, 0)),
    Character("Jenny LeClerc", 21, "March 4th", "Reading, Soccer", 4, 5, 3, 5, Array(8, 6, 5, 4, 4, 4, 3, 2, 0), Array(8, 6, 5, 4, 4, 4, 4, 3, 0), Array(6, 5, 4, 4, 4, 2, 1, 1, 0), Array(8, 6, 5, 4, 4, 3, 3, 2, 0)),
    Character("Ox Bellows", 23, "October 18th", "Football, Shiny Objects", 3, 5, 5, 5, Array(6, 5, 5, 4, 3, 2, 2, 2, 0), Array(8, 8, 7, 6, 6, 5, 5, 4, 0), Array(7, 6, 5, 5, 4, 3, 2, 2, 0), Array(6, 6, 5, 5, 4, 4, 2, 2, 0)),
    Character("Darrin 'Flash' Williams", 20, "June 6th", "Track, Music, Shakespearean Literature", 3, 5, 5, 5, Array(8, 7, 7, 6, 5, 4, 4, 4, 0), Array(7, 6, 6, 5, 4, 3, 3, 2, 0), Array(7, 5, 5, 5, 4, 3, 2, 1, 0), Array(7, 5, 5, 5, 4, 3, 3, 2, 0)),
    Character("Vivian Lopez", 42, "January 11th", "Old Movies, Horses", 4, 5, 5, 4, Array(8, 7, 6, 4, 4, 4, 4, 3, 0), Array(6, 6, 5, 4, 4, 2, 2, 2, 0), Array(8, 8, 7, 6, 5, 4, 4, 4, 0), Array(7, 6, 6, 5, 5, 5, 5, 4, 0)),
    Character("Madame Zostra", 37, "December 10th", "Astrology, Cooking, Baseball", 5, 4, 5, 4, Array(7, 6, 6, 5, 5, 3, 3, 2, 0), Array(6, 5, 5, 5, 4, 3, 3, 2, 0), Array(8, 8, 7, 6, 5, 4, 4, 4, 0), Array(6, 6, 5, 4, 4, 4, 3, 1, 0)),
    Character("Missy Dubourde", 9, "February 14th", "Swimming, Medicine", 5, 4, 5, 4, Array(7, 7, 6, 6, 6, 5, 4, 3, 0), Array(7, 6, 5, 4, 3, 3, 3, 2, 0), Array(7, 6, 5, 5, 4, 3, 2, 1, 0), Array(6, 6, 6, 5, 4, 4, 3, 2, 0)),
    Character("Zoe Ingstrom", 8, "November 5th", "Dolls, Music", 4, 4, 5, 5, Array(8, 8, 6, 5, 4, 4, 4, 4, 0), Array(7, 6, 4, 4, 3, 3, 2, 2, 0), Array(8, 7, 6, 6, 5, 5, 4, 3, 0), Array(5, 5, 5, 4, 4, 3, 2, 1, 0)),
    Character("Peter Akimoto", 13, "September 3rd", "Bugs, Basketball", 4, 5, 4, 5, Array(7, 7, 6, 6, 4, 3, 3, 3, 0), Array(8, 6, 5, 5, 4, 3, 3, 2, 0), Array(7, 6, 6, 5, 4, 4, 4, 3, 0), Array(8, 7, 7, 6, 5, 4, 4, 3, 0)),
    Character("Brandon Jaspers", 12, "May 21st", "Computers, Camping, Hockey", 5, 4, 4, 5, Array(8, 7, 6, 5, 4, 4, 4, 3, 0), Array(7, 6, 6, 5, 4, 3, 3, 2, 0), Array(8, 7, 6, 5, 4, 3, 3, 3, 0), Array(7, 6, 6, 5, 5, 4, 4, 1, 0)),
    Character("Professor Longfellow", 57, "July 27th", "Gaelic Music, Drama, Fine Wines", 4, 5, 5, 7, Array(6, 6, 5, 5, 4, 4, 2, 2, 0), Array(6, 6, 5, 5, 4, 3, 2, 1, 0), Array(7, 6, 5, 5, 4, 3, 3, 1, 0), Array(8, 7, 6, 5, 5, 5, 5, 4, 0)),
    Character("Father Rhinehardt", 62, "April 29th", "Fencing, Gardening", 5, 5, 3, 4, Array(7, 7, 6, 5, 4, 3, 3, 2, 0), Array(7, 5, 5, 4, 4, 2, 2, 1, 0), Array(8, 7, 7, 6, 5, 5, 4, 3, 0), Array(8, 6, 6, 5, 4, 3, 3, 1, 0))
  )
}

case class Character(name: String,
                      age: Int,
                      birthday: String,
                      hobbies: String,
                      speed: Int, // these four stats are indexes into the stat lists
                      might: Int,
                      knowledge: Int,
                      sanity: Int,
                      speedStats: Array[Int],
                      mightStats: Array[Int],
                      knowledgeStats: Array[Int],
                      sanityStats: Array[Int])
