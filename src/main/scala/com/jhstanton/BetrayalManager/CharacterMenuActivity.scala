package com.jhstanton.BetrayalManager

import android.content.Intent
import android.view.View
import org.scaloid.common._

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterMenuActivity extends SActivity {
  def apply() = new CharacterMenuActivity()
  final val EXTRA_MESSAGE : String = "com.jhstanton.BetrayalManager.MESSAGE"
}
class CharacterMenuActivity extends SActivity with SContext {
  
  onCreate {
    val topView = new SScrollView()
    val charList = new SVerticalLayout()
    getResources.getStringArray(R.array.character_names).foreach{ name => 
      val intent = new Intent(this, classOf[CharacterSheet])
      intent.putExtra(CharacterMenuActivity.EXTRA_MESSAGE, name)
      charList += new SButton(name, (v: View) => startActivity(intent))
    }
    topView += charList
    contentView = topView
  }
}
