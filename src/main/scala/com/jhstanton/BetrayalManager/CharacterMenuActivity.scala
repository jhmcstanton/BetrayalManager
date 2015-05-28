package com.jhstanton.BetrayalManager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.{ScrollView, LinearLayout, Button}



// import com.jhstanton.BetrayalManager.Implicits._

/**
 * Created by jim on 5/25/15.
 */
// public
object CharacterMenuActivity extends Activity {
  def apply() = new CharacterMenuActivity()
  final val EXTRA_MESSAGE : String = "com.jhstanton.BetrayalManager.MESSAGE"
}
class CharacterMenuActivity extends Activity {
    //final val EXTRA_MESSAGE : String = "com.jhstanton.BetrayalManager.MESSAGE"
    override def onCreate(savedInstanceState : Bundle) {
      super.onCreate(savedInstanceState)
      val characterView : ScrollView = new ScrollView(this)
      val characterList : LinearLayout = new LinearLayout(this)
      characterView.addView(characterList)
      characterList.setOrientation(LinearLayout.VERTICAL)
      val names = getResources.getStringArray(R.array.character_names)
      val intent = new Intent(this, classOf[CharacterSheet])
      names.zip(0 to names.length).foreach{case (name, id) => {
        val btn: Button = new Button(this)
        btn.setText(name)
        btn.setId(id)
        btn.setOnClickListener(new OnClickListener(){
          def onClick(v: View) {
            // This is cast to to a context (which is the type of @this) b/c scalac couldn't determine the correct constructor
//            val intent : Intent = new Intent(this.asInstanceOf[android.content.Context], classOf[CharacterSheet])
            intent.putExtra(CharacterMenuActivity.EXTRA_MESSAGE, name)
            startActivity(intent)
          }
        })
        /*btn.setOnClickListener(view => {
          val intent = new Intent(this, CharacterSheet.getClass())
          intent.putExtra(EXTRA_MESSAGE, name)
          startActivity(intent)
        })*/
        characterList.addView(btn)
        }
      }

      setContentView(characterView)
    }

}
