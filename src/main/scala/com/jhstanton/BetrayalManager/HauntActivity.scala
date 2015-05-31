package com.jhstanton.BetrayalManager

import org.scaloid.common._
import android.os.Bundle
import android.graphics.Color
import android.widget.{Spinner, AdapterView, Adapter, TextView}
import android.view.View
import android.content.SharedPreferences

object HauntActivity extends SActivity { //with AdapterView.OnItemSelectedListener {
  def apply() = new HauntActivity()
  val STORE   = "com.jhstanton.BetrayalManager.HauntActivity.STORE"
  val LENGTH  = "com.jhstanton.BetrayalManager.HauntActivity.LENGTH"
}

class HauntActivity extends SActivity with AdapterView.OnItemSelectedListener {
  var roomSelection  = ""
  var omenSelection  = ""
  var hauntReady     = false
  val previousHaunts = scala.collection.mutable.MutableList[Int]()
  val hauntBaseStr   = "Your Haunt: "
  val traitorBaseStr = "The Traitor: "
  val infoSize       = 30
  val listSize       = 22
  val center         = 0x11
  
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    
    val scrollEnvironment = new SScrollView()
    val prefs = getPreferences(0)
    if (prefs != null) {
      getHaunts(prefs)
    }
    val topLayout = new SVerticalLayout()
    
    val roomDropdown = new Spinner(this)
    val roomAdapter  = new SArrayAdapter(rooms toArray).dropDownStyle((x : TextView) => x.textSize(listSize))
    roomDropdown setAdapter roomAdapter
    roomDropdown setOnItemSelectedListener this

    val omenDropdown = new Spinner(this)
    val omenAdapter  = new SArrayAdapter(omens toArray).dropDownStyle((x: TextView) => x.textSize(listSize))
    omenDropdown setAdapter omenAdapter
    omenDropdown setOnItemSelectedListener this

    topLayout += roomDropdown
    topLayout += omenDropdown
    
    val hauntView = new STextView(hauntBaseStr).textSize(infoSize).gravity(center)
    hauntView.setTextColor(Color.RED)
    val traitorView = new STextView(traitorBaseStr).textSize(infoSize).gravity(center)
    traitorView.setTextColor(Color.RED)
    val getHaunt  = new SButton("Get Your Haunt", retrieveHaunt(hauntView, traitorView) _) 
    val skipHaunt = new SButton("Skip", (view: View) => alert("Indicate Omen Room", "Find the next closest omen room and indicate it above"))
    topLayout += hauntView
    topLayout += traitorView
    topLayout += getHaunt
    topLayout += skipHaunt

    val resetBtn = new SButton("Reset", (view: View) => previousHaunts.clear)
    topLayout += resetBtn

    scrollEnvironment += topLayout
    setContentView(scrollEnvironment)
  }
  
  def retrieveHaunt(hauntView: STextView, traitorView: STextView)(view: View) {
    if (hauntReady){
      val hauntNumber = hauntMap(roomSelection)(omenSelection)      
      if (previousHaunts.exists(_ == hauntNumber)){
	alert("Previous Haunt Found" , "You have already dealt with this haunt, hit skip to find a new one")
      }
      previousHaunts += hauntNumber
      hauntView.setText(hauntBaseStr + hauntNumber.toString)
      traitorView.setText(traitorBaseStr + traitors(hauntNumber - 1))
    }
    else {
      alert("Haunt Not Ready", "Indicate the room and omen that triggered the haunt.")
    } 
  }
  
  override def onBackPressed() {
    storeHaunts()
    super.onBackPressed
  }

  def storeHaunts() {
    val editor : SharedPreferences.Editor = getPreferences(0).edit()
    editor.putInt(HauntActivity.LENGTH, previousHaunts.length)
    (0 to (previousHaunts.length - 1)).foreach{ x => 
      editor.putInt(HauntActivity.STORE + x.toString, previousHaunts(x))
    }
    editor.commit()
  }

  def getHaunts(prefs: SharedPreferences)  {
    val count = prefs.getInt(HauntActivity.LENGTH, 0)
    (0 to count).foreach{ x => previousHaunts += prefs.getInt(HauntActivity.STORE + x.toString, 0) }
  }

  def onItemSelected(parent: AdapterView[_], view: View, pos: Int, id: Long) {
    val selection = parent.getItemAtPosition(pos)
    if (rooms.exists(_ == selection)) {
      roomSelection = selection.toString
    }
    else {
      omenSelection = selection.toString
    }
    hauntReady = (roomSelection != "") && (omenSelection != "")
  }

  def onNothingSelected(parent: AdapterView[_]) {
    
  }
  
  val traitors = Array(
    "Haunt Revealer",
    "Haunt Revealer",
    "Lowest Knowledge (except for the haunt revealer)",
    "Highest Might (except for the haunt revealer)",
    "Haunt Revealer",
    "Lowest Sanity",
    "Father Rhinehardt (Gardening) or Highest Sanity",
    "Haunt Revealer",
    "None (at first)",
    "Haunt Revealer",
    "Haunt Revealer",
    "None",
    "Lowest Sanity (except for Haunt Revealer)",
    "Haunt Revealer",
    "Lowest Speed (except for the Haunt Revealer)",
    "Left of the Haunt Revealer",
    "Left of the Haunt Revealer",
    "Haunt Revealer",
    "Left of the Haunt Revealer",
    "Vivan Lopez (Old Movies) or left of the Haunt Revealer",
    "Oldest Explorer (except for the Haunt Revealer)",
    "Left of the Haunt Revealer",
    "Left of the Haunt Revealer",
    "Branden Jaspers (Camping) or Lowest Speed",
    "Zoe Ingstrom (Dolls) or Highest Knowledge",
    "Left of the Haunt Revealer",
    "Highest Knowledge (except for the Haunt Revealer)",
    "Highest Knowledge (except for the Haunt Revealer)",
    "Haunt Revealer",
    "Haunt Revealer",
    "None (see Secrets of Survival)",
    "Highest Sanity",
    "Haunt Revealer",
    "Hidden Traitor (see Secrets of Survival)",
    "Highest Knowledge",
    "Missy Dubourde (Swimming) or Highest Speed",
    "Lowest Might",
    "Lowest Knowledge (except for the Haunt Revealer)",
    "Highest Speed (except for the Haunt Revealer)",
    "Left of the Haunt Revealer",
    "Haunt Revealer",
    "Highest Might",
    "Hidden Traitor (see Secrets of Survival)",
    "Youngest Explorer (except for the Haunt Revealer)",
    "Highest Knowledge (except for the Haunt Revealer)",
    "Madame Zostra (Cooking) or Lowest Speed",
    "Haunt Revealer",
    "Left of the Haunt Revealer",
    "Heather Granville or Highest Knowledge",
    "None (see Secrets of Survival)"
  )
  
  // get a proofreading..
  val roomOmenMap = List(
    ("Abandoned Room", Vector(18, 7, 12, 38, 1, 9, 45, 42, 49, 28, 34, 43, 48)),
    ("Balcony", Vector(24, 7, 32, 5, 16, 6, 11, 25, 49, 20, 47, 39, 2)),
    ("Catacombs", Vector(4, 7, 23, 46, 1, 13, 10, 25, 49, 41, 37, 43, 48)),
    ("Charred Room", Vector(24, 33, 23, 38, 30, 13, 31, 48, 44, 20, 47, 15, 8)),
    ("Dining Room", Vector(24, 3, 27, 5, 16, 6, 45, 42, 21, 20, 37, 39, 40)),
    ("Furnace Room", Vector(4, 33, 32, 38, 30, 13, 10, 42, 36, 28, 34, 15, 2)),
    ("Gallery", Vector(18, 3, 19, 19, 19, 22, 10, 25, 36, 41, 37, 15, 8)),
    ("Gymnasium", Vector(35, 29, 12, 46, 1, 22, 11, 22, 21, 41, 47, 43, 48)),
    ("Junk Room", Vector(4, 33, 27, 46, 1, 9, 11, 25, 44, 17, 17, 17, 40)),
    ("Kitchen", Vector(18, 3, 23, 46, 16, 22, 31, 32, 36, 41, 37, 39, 2)),
    ("Master Bedroom", Vector(35, 29, 27, 5, 16, 6, 10, 35, 44, 20, 47, 43, 2)), 
    ("Pentagram Chamber", Vector(26, 50, 32, 50, 26, 26, 45, 14, 14, 26, 14, 50, 40)),
    ("Servants' Quarters", Vector(35, 29, 12, 5, 30, 9, 31, 42, 21, 28, 34, 15, 8))
  ) toMap
  
  val omens = List( 
    "Bite",
    "Book",
    "Crystal Ball",
    "Dog",
    "Girl",
    "Holy Symbol",
    "Madman",
    "Mask",
    "Medallion",
    "Ring",
    "Skull",
    "Spear",
    "Spirit Board"
  )

  val hauntMap : Map[String, Map[String, Int]] = roomOmenMap.map{ case(room, haunts) =>
      (room, omens.zip(haunts).toMap)
  }.toMap

  val rooms = roomOmenMap map { case (room, _) => room } toArray
}
