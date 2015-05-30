package com.jhstanton.BetrayalManager

//import android.app.Activity
import android.content.Intent
//import android.os.Bundle
import android.view.View 
import org.scaloid.common._

//public
class MainMenu extends SActivity {
    /**
     * Called when the activity is first created.
     */
    
  onCreate {

    contentView = new SVerticalLayout {
      STextView("Hello World, MainMenu")
      SButton(R.string.character_sheet, charSheets _) 
      SButton(R.string.haunt_track, hauntTrack _) 
      SButton(R.string.haunt, haunts _) 
    }  //.<<.fill.>>
  }

  def charSheets(view: View) {
    val intent : Intent = new Intent(this, classOf[CharacterMenuActivity]) //CharacterMenuActivity.getClass())
    startActivity(intent)
  }
  
  def hauntTrack(view: View) {
    val intent : Intent = new Intent(this, classOf[HauntTrack])
    startActivity(intent)
  }
  
  def haunts(view: View) {
    charSheets(view)
  }
}
