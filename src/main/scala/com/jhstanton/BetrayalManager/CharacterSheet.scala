package com.jhstanton.BetrayalManager

import android.app.Activity
import android.content.{Context, Intent}
import android.os.Bundle
import android.util.AttributeSet
import android.view.{LayoutInflater, ViewGroup, View}
import android.view.ViewGroup.{LayoutParams}
import android.widget.{Button, TextView, LinearLayout}

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterSheet extends Activity {
  def apply() = new CharacterSheet()
}
class CharacterSheet extends Activity {
  val params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT
					      , LayoutParams.MATCH_PARENT
					      , 1.0f)
  
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val intent : Intent = getIntent
    val name   : String = intent.getStringExtra(CharacterMenuActivity.EXTRA_MESSAGE)
    setContentView(R.layout.charactersheet)

    characters.find(_.name == name) match {
      //case None =>  // shouldn't happen, if resource names match character names below
      case Some(character) => {
	//val topLevelLayout : LinearLayout = findViewById(R.id.character_sheet_layout).asInstanceOf[LinearLayout]
        val nameView : TextView = findViewById(R.id.char_name).asInstanceOf[TextView]
        nameView.setText(character.name)
        val birthdayView: TextView = findViewById(R.id.birthday).asInstanceOf[TextView]
        birthdayView.setText(character.birthday)
        val hobbyView : TextView = findViewById(R.id.hobbies).asInstanceOf[TextView]
        hobbyView.setText(character.hobbies)

        val listsView : LinearLayout = findViewById(R.id.stats).asInstanceOf[LinearLayout]
        List(character.speedStats, character.mightStats, character.knowledgeStats, character.sanityStats)
		.zip(List("Speed", "Might", "Knowledge", "Sanity")).foreach{ case(stats, statName) =>
          val statView: LinearLayout = new LinearLayout(this)
	  
          statView.setOrientation(LinearLayout.VERTICAL) 
	  val statNameView: TextView = new TextView(this)
	  statNameView.setText(statName)
	  statNameView.setGravity(0x11)
	  statView.addView(statNameView, params)
          stats.foreach{pt =>
            val ptView : TextView = new TextView(this)
            ptView.setText(pt.toString)
	    ptView.setGravity(0x11)
            statView.addView(ptView, params)
          }
          val (plusBtn, minusBtn) = (new Button(this), new Button(this))
          plusBtn.setText("+")
          minusBtn.setText("-")									     
          statView.addView(plusBtn)
          statView.addView(minusBtn)
	  listsView.addView(statView, params)
        }
      }
  }

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
