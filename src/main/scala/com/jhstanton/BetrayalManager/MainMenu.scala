package com.jhstanton.BetrayalManager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

//public
class MainMenu extends Activity {
    /**
     * Called when the activity is first created.
     */

    override def onCreate(savedInstanceState : Bundle) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.main)
      
    }

    def charSheets(view: View) {
      val intent : Intent = new Intent(this, classOf[CharacterMenuActivity]) //CharacterMenuActivity.getClass())
      startActivity(intent)
    }

    def doomTrack(view: View) {
      charSheets(view)
    }

    def haunts(view: View) {
    	charSheets(view)
   }
}
