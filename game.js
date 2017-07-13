/*
To do.
NEW FEATURES

-add combat buttons
- add unequip function and button

BUGS

-fix none for you to take bug
-figure out loop breaking for killing characters

*/
var room;
var roomId = 0;
var roomList = ["theGate", "entryWay" ]


//var taken=false;
var takeFocus;
var searchFocus;
var talkFocus;
var attackFocus;
//character stats
//var attackPower = 1;
var healthPoints = 10;
var pierceAtk = 0;
var slashAtk = 0;
var bluntAtk = 1;
var fireAtk = 0;
var iceAtk = 0;
var radAtk = 0;
var magicAtk = 0;
var pierceRes = 1;
var slashRes = 1;
var bluntRes = 1;
var fireRes = 1;
var iceRes = 1;
var radRes = 1;
var magicRes = 1 ;
var equiper = "";
var unequiper="";
var curArmor = "clothes";
var weaponHand = "fist";
var shieldHand = "aura";
var curRing = "fingernail";
var specialFunc = ["Pull", "Push", "Eat", "Drink", "Open", "Close", ]
var inventory = ["Strange_Coin", "dagger", "helm", "buckler", "small_ring"];
var inventoryAsString = inventory.join(", ");


// These objects are living organisms in the game. you can talk and attack them and search them if they are dead
var goblin={
	health: 10,
	pierceAtk: 0,
	slashAtk: 2,
	bluntAtk: 0,
	fireAtk: 0,
	iceAtk: 0,
	radAtk: 0,
	magicAtk: 0,
	pierceRes: 1,
	slashRes: 1,
	bluntRes: 1,
	fireRes: 1,
	iceRes: 1,
	radRes: 1,
	magicRes: 1,
	alive: true,
	inventory: ["key"],
	special: [""],
	Search: function(){
		if(goblin.alive==false){
			alert("You search the freshly slain goblin \nItems: " + goblin.inventory);
			room.takeables.push("key");
			console.log(room.takeables);
		}else{alert("The goblin would prefer it if you kept your distance");}
	},
	dialogue: function(){
						var answer = prompt('"You look like you want to get into the dungeon" The goblin says to you. "I can let you in for a coin." Respond with yes or no');
						if(answer == "yes"){
							for(i=0; i < inventory.length; i++){
								if(inventory[i] == "Strange_Coin"){
									inventory.splice(i,1);
									inventory.push("key");
									inventoryAsString = inventory.join(", ");
									document.getElementById("inventoryText").innerText=inventoryAsString;
									alert('"A pleasure to do business with you"');
									break;
								}
							}
							

						}else{alert('"Suit Yourself"');}
					},

	charDeath: function(){
		alert("The small goblin falls to the ground dead");
		room.roomLine4 = "To the right of the gate lies a small dead goblin";
	}
};



//These Objects are inanimate objects in the game. You can search and take them
var log ={
	Search: function(){alert("It is a log from a tree up above. It is large but a good push may move it. It looks like something is stuck under it.")},
	special: ["Push"],
	Push: function(){alert("You manage to move the log a few feet. Underneath the log was a dagger");
	}
	
};

var dagger ={
	name: "dagger",
	pierceAtk: 5,
	slashAtk: 0,
	bluntAtk: 0,
	fireAtk: 0,
	iceAtk: 0,
	radAtk: 0,
	magicAtk: 0,
	equippable: true,
	type: "weapon",
	hand: 1,
	special: [""],
	Search: function(){alert("Its a small dagger");},
	Take: function(){alert("you take the dagger");},
	Equip: function(){alert("You equip the dagger");}
};

var key={
	special: [""],
	equippable: false,
	Take: function(){
		alert("you take the key");
		if(roomId==0){
			for(i=0; i < goblin.inventory.length; i++){
			if(goblin.inventory[i] == "key"){
				goblin.inventory.splice(i,1);
				break;}	
			}
		}
	}

};

var Strange_Coin={
	special: [""],
	equippable: false,
	Search: function(){
		alert("A strange coin that bears a face that you do not recognize.");
	}
};

var torch = {
	name: "torch",
	pierceAtk: 0,
	slashAtk: 0,
	bluntAtk: 2,
	fireAtk: 1,
	iceAtk: 0,
	radAtk: 0,
	magicAtk: 0,
	equippable: true,
	type: "weapon",
	hand: 1,
	special: [""],
	Search: function(){alert("Its a torch");},
	Take: function(){alert("you take the torch");},
	Equip: function(){alert("You equip the torch.");}
};


var fist = {
	name: "fist",
	pierceAtk: 0,
	slashAtk: 0,
	bluntAtk: 1,
	fireAtk: 0,
	iceAtk: 0,
	radAtk: 0,
	magicAtk: 0,
	equippable: true,
	type: "weapon",
	hand: 1,
};

var fingernail = {
	name: "fingernail",
	pierceAtk: 0,
	slashAtk: 0,
	bluntAtk: 0,
	fireAtk: 0,
	iceAtk: 0,
	radAtk: 0,
	magicAtk: 0,
	equippable: true,
	type: "ring",
	hand: 1,
};

var small_ring = {
	name: "small_ring",
	pierceAtk: 0,
	slashAtk: 0,
	bluntAtk: 0,
	fireAtk: 0,
	iceAtk: 0,
	radAtk: 1,
	magicAtk: 0,
	equippable: true,
	type: "ring",
	hand: 1,
	Search: function(){alert("Its a small glowing ring");},
	Take: function(){alert("you take the small ring");},
	Equip: function(){alert("You equip the small ring");}
};


var clothes = {
	name: "clothes",
	pierceRes: 0,
	slashRes: 0,
	bluntRes: 0,
	fireRes: 0,
	iceRes: 0,
	radRes: 0,
	magicRes: 0,
	equippable: true,
	type: "armor",
	hand: 1,
};

var helm = {
	name: "helm",
	pierceRes: 0.4,
	slashRes: 0.4,
	bluntRes: 0.4,
	fireRes: 0,
	iceRes: 0,
	radRes: 0,
	magicRes: 0,
	equippable: true,
	type: "armor",
	hand: 1,
	Search: function(){alert("It is an iron helmet");},
	Take: function(){alert("You pick up the helmet");},
	Equip: function(){alert("You equip the helmet");}
};

var buckler = {
	name: "buckler",
	pierceRes: 0.4,
	slashRes: 0.4,
	bluntRes: 0.4,
	fireRes: 0,
	iceRes: 0,
	radRes: 0,
	magicRes: 0,
	equippable: true,
	type: "shield",
	hand: 1,
	Search: function(){alert("It is a small round iron shiled");},
	Take: function(){alert("You pick up the buckler");},
	Equip: function(){alert("You equip the buckler");}
};

var aura = {
	name: "aura",
	pierceRes: 0,
	slashRes: 0,
	bluntRes: 0,
	fireRes: 0,
	iceRes: 0,
	radRes: 0,
	magicRes: 0,
	equippable: true,
	type: "shield",
	hand: 1,
	Search: function(){alert("It is a small round iron shiled");},
	Take: function(){alert("You pick up the buckler");},
	Equip: function(){alert("You equip the buckler");}
};

var torches = {
	special: [""],
	Search: function(){	alert("Its a torch");
}

}

var gate = {
	special: [""],
	health: 60,
	Search: function(){	alert("Its a gate");}
}


// These Objects are rooms in the dungeon
var theGate={
	searchables: ["torch", "torches", "gate", "goblin", "log"],
	takeables: ["torch", "torch", "dagger"],
	talkables: ["goblin"],
	attackables: ["goblin"],
	roomHeader: "At the locked Gate",
	roomLine1: "You stand in front of a large locked gate bolted into the rocky wall at the foot of a mountain. ",
	roomLine2: "Two torches sit in sconces on either side. ",
	roomLine3: "between you and the gate a rather large log has fallen onto the path, although you see no reason you could not easily pass over it. ",
	roomLine4: "To the right of the gate sits a weary looking goblin. ",
	roomLine5: "",
	roomImage: "url('images/gate.png')",
	onEnter: function(){console.log("enter")},
	openStatus: false,
	openCheck: function(){if(theGate.openStatus !== true && inventory.indexOf("key") == -1){
							alert("The Gate before you is locked.");
							return false;
							}else{
								for(i=0; i < inventory.length; i++){
								if(inventory[i] == "key"){
									inventory.splice(i,1);
									inventoryAsString = inventory.join(", ");
									document.getElementById("inventoryText").innerText=inventoryAsString;
									break;}	
								}
						room.openStatus=true;
						room.roomLine1 = "You stand in front of a large unlocked gate bolted into the rocky wall at the foot of a mountain. ";
						return true;}
					}
};

var entryWay={
	searchables: ["door", "globe", "bookshelf",],
	takeables: [],
	talkables: [],
	attackables: [],
	roomHeader: " A small Entry Way",
	roomLine1: "You stand in a small antechamber. The light from the outside barely iluminates this dank room. ",
	roomLine2: "In front of you is an old wooden door. ",
	roomLine3: "To your left is a globe propped up in its stand. ",
	roomLine4: "To your right is a large moldy bookshelf. ",
	roomLine5: "",
	roomImage: "url('images/entryway.jpg')",
	onEnter: function(){console.log("enter")},
	openStatus: false,
	openCheck: function(){if(theGate.openStatus !== true && inventory.indexOf("key") == -1){
							console.log(window[theGate.openStatus]);
							alert("The Gate before you is locked.");
							return false;
							}else{
								room.openStatus=true;
								return true;}
						}
};



function runGame(){
	room=window[roomList[0]];
	roomChange();
	document.getElementById("inventoryText").innerText=inventoryAsString;
	document.getElementById("hpText").innerText=healthPoints;
}

function goEquip(){
	if(inventory.length < 1){
		alert("You don't have anything to equip");
	}else{
		var equipFocus= prompt("what do you wish to equip?");
		if(inventory.indexOf(equipFocus) !== -1){
				if(window[equipFocus].equippable == true){
				equiper = window[equipFocus];
				var equipFunction = equiper.type;
				console.log(equipFunction);
				equips[equipFunction]();
	
				}else{alert("This can not be equipped");}
		}else{alert("You dont have that.");}
	}
}



function goSearch(){
	if(room.searchables.length < 1 && inventory.length < 1){
		alert("There is nothing to search.")
	}
	else{
		searchFocus = prompt("what would you like to search?");
	}
	if(room.searchables.indexOf(searchFocus) !== -1){
		//alert("This exists in the room");
		window[searchFocus].Search();
	}
	else if(inventory.indexOf(searchFocus) !== -1){
		window[searchFocus].Search();
		
	}else{alert("This is not something that you can search")};
	}

function goTake(){
	if(room.takeables.length < 1){
		alert("There is nothing to take.");
	}else{var takeFocus= prompt("What would you like to take?")};		
	 		if(room.takeables.indexOf(takeFocus) !== -1){
				window[takeFocus].Take();
				inventory.push(takeFocus);
				inventoryAsString = inventory.join(", ");
				document.getElementById("inventoryText").innerText=inventoryAsString;
				}else{alert("There are none for you to take.");}
	for(i=0; i < room.takeables.length; i++){
		if(room.takeables[i] == takeFocus){
			room.takeables.splice(i,1);
			break;}
				
		
	}
	}

function goTalk(){
	if(room.talkables.length < 1){
		alert("There is no one to speak to.")
	}
		else{
			var talkFocus= prompt("Whom do you wish to speak to?");
		
	if(room.talkables.indexOf(talkFocus) !== -1){
		window[talkFocus].dialogue();
		
	}
	else{alert("That is not something you can speak to here")}
	}
}


















function goAttack(){
	if(room.attackables.length < 1){
		alert("There is nothing to attack.")
	}
		else{
			var attackFocus= prompt("What do you wish to attack?");
		
	if(room.attackables.indexOf(attackFocus) !== -1){
		//alert("you can attack that");
		var targetHP = window[attackFocus].health;
		var enPierce = window[attackFocus].pierceAtk;
		var enSlash = window[attackFocus].slashAtk;
		var enBlunt = window[attackFocus].bluntAtk;
		var enFire = window[attackFocus].fireAtk;
		var enIce = window[attackFocus].iceAtk;
		var enRad = window[attackFocus].radAtk;
		var enMag = window[attackFocus].magicAtk;
		var enPierceRes = window[attackFocus].pierceRes;
		var enSlashRes = window[attackFocus].slashRes;
		var enBluntRes = window[attackFocus].bluntRes;
		var enFireRes = window[attackFocus].fireRes;
		var enIceRes = window[attackFocus].iceRes;
		var enRadRes = window[attackFocus].radRes;
		var enMagRes = window[attackFocus].magicRes;
		var yourAttack = (Math.floor(pierceAtk*enPierceRes)+Math.floor(slashAtk*enSlashRes)+Math.floor(bluntAtk*enBluntRes)+Math.floor(fireAtk*enFireRes)+Math.floor(iceAtk*enIceRes)+Math.floor(radAtk*enRadRes)+Math.floor(magicAtk*enMagRes));
		var enemyAttack = (Math.floor(enPierce*pierceRes)+Math.floor(enBlunt*bluntRes)+Math.floor(enSlash*slashRes)+Math.floor(enFire*fireRes)+Math.floor(enIce*iceRes)+Math.floor(enRad*radRes)+Math.floor(enMag*magicRes));
		while(targetHP > 0 && healthPoints > 0){
			targetHP -= yourAttack ;
			alert("You do " + yourAttack + " damage to the " + attackFocus + "! It now has " + targetHP + " HP");
			if(targetHP <= 0){break;}
			healthPoints -= enemyAttack;
			alert("Your Opponent does " + enemyAttack + " damage to you! You now have " + healthPoints + " HP");

		}
		if(healthPoints <= 0){
			alert("You died.");
			location.reload(true);
		}else{
		window[attackFocus].charDeath();
		window[attackFocus].alive = false;
		document.getElementById("hpText").innerText=healthPoints;
		for(i=0; i < room.attackables.length; i++){
		if(room.attackables[i] == attackFocus){
			room.attackables.splice(i,1);
						}
		for(i=0; i < room.talkables.length; i++){
		if(room.talkables[i] == attackFocus){
			room.talkables.splice(i,1);
			
			}
		}
	}
	}

	}
	else{alert("Attacking that would not do much");}
	}
}

function goForward(){
	var checker = room.openCheck();
	if(checker == true){roomId+=1;
						roomChange();}
}

function goBackward(){
	roomId-=1;
	roomChange();}


function goSpecial(){
	var specAction = prompt("Enter one of the following actions (case sensitive): Pull, Push, Eat, Drink, Open, Close");
	if(specialFunc.indexOf(specAction) !== -1){
		var specFocus = prompt("What would you like to " + specAction + " (case sensitive)");
		if(room.searchables.indexOf(specFocus) !== -1 || inventory.indexOf(specFocus) !== -1){
			if(window[specFocus].special.indexOf(specAction) !== -1){
				alert("thats a thing you can do");
				window[specFocus][specAction]();
			}else{alert("That wouldn't do much of anything");}
		}else{ alert("There isn't one in this room");}		
	}else{alert("Please use one of the listed actions");}
}


function roomChange(){
		room=window[roomList[roomId]];
		document.getElementById("header").innerText=room.roomHeader;
		document.getElementById("image").style.backgroundImage=room.roomImage;
		document.getElementById("textbox").innerText=room.roomLine1+room.roomLine2+room.roomLine3+room.roomLine4+room.roomLine5;
}





var equips = {

	weapon: function(){
						if(equiper.name == weaponHand){alert("You already Have this equipped");}
						else{ if(shieldHand != "" && equiper.hand == 2){alert("You need to unequip your shield to equip a two handed weapon.");}
								else{
								var eqPierce = equiper.pierceAtk;
								var eqSlash = equiper.slashAtk;
								var eqBlunt = equiper.bluntAtk;
								var eqFire = equiper.fireAtk;
								var eqIce = equiper.iceAtk;
								var eqRad = equiper.radAtk;
								var eqMagic = equiper.magicAtk;
								var unequiper = window[weaponHand];
								var uneqPierce = unequiper.pierceAtk;
								var uneqSlash = unequiper.slashAtk;
								var uneqBlunt = unequiper.bluntAtk;
								var uneqFire = unequiper.fireAtk;
								var uneqIce = unequiper.iceAtk;
								var uneqRad = unequiper.radAtk;
								var uneqMagic = unequiper.magicAtk;
								pierceAtk += (eqPierce - uneqPierce);
								slashAtk += (eqSlash - uneqSlash);
								bluntAtk += (eqBlunt - uneqBlunt);
								fireAtk += (eqFire - uneqFire);
								iceAtk += (eqIce - uneqIce);
								radAtk += (eqRad - uneqRad);
								magicAtk += (eqMagic - uneqMagic);
								weaponHand = equiper.name;
								equiper.Equip();
								document.getElementById("weaponText").innerText=equiper.name;

						}	

				}
			
	},// ends weapon function


	ring: function(){
						if(equiper.name == curRing){alert("You already Have this equipped");}
						else{ if(shieldHand != "" && equiper.hand == 2){alert("You need to unequip your shield to equip a two handed weapon.");}
								else{
								var eqPierce = equiper.pierceAtk;
								var eqSlash = equiper.slashAtk;
								var eqBlunt = equiper.bluntAtk;
								var eqFire = equiper.fireAtk;
								var eqIce = equiper.iceAtk;
								var eqRad = equiper.radAtk;
								var eqMagic = equiper.magicAtk;
								var unequiper = window[curRing];
								var uneqPierce = unequiper.pierceAtk;
								var uneqSlash = unequiper.slashAtk;
								var uneqBlunt = unequiper.bluntAtk;
								var uneqFire = unequiper.fireAtk;
								var uneqIce = unequiper.iceAtk;
								var uneqRad = unequiper.radAtk;
								var uneqMagic = unequiper.magicAtk;
								pierceAtk += (eqPierce - uneqPierce);
								slashAtk += (eqSlash - uneqSlash);
								bluntAtk += (eqBlunt - uneqBlunt);
								fireAtk += (eqFire - uneqFire);
								iceAtk += (eqIce - uneqIce);
								radAtk += (eqRad - uneqRad);
								magicAtk += (eqMagic - uneqMagic);
								curRing = equiper.name;
								equiper.Equip();
								document.getElementById("ringText").innerText=equiper.name;

						}	

				}
			
	},// ends weapon function

	armor: function(){
						if(equiper.name == curArmor){alert("You already Have this equipped");}
						else{ if(1 == 2){alert("you shouldnt see this line 334");}
						else{	
								console.log("armor");
								console.log("current slash" + slashRes);
								var eqPierceRes = equiper.pierceRes;
								var eqSlashRes = equiper.slashRes;
								var eqBluntRes = equiper.bluntRes;
								var eqFireRes = equiper.fireRes;
								var eqIceRes = equiper.iceRes;
								var eqRadRes = equiper.radRes;
								var eqMagicRes = equiper.magicRes;
								console.log("current armor is " +curArmor);
								var armUnequiper = window[curArmor];
								console.log("unequipping" + armUnequiper.name);
								var uneqPierceRes = armUnequiper.pierceRes;
								var uneqSlashRes = armUnequiper.slashRes;
								var uneqBluntRes = armUnequiper.bluntRes;
								var uneqFireRes = armUnequiper.fireRes;
								var uneqIceRes = armUnequiper.iceRes;
								var uneqRadRes = armUnequiper.radRes;
								var uneqMagicRes = armUnequiper.magicRes;
								console.log("unequiper" + uneqSlashRes);
								console.log("subtracting" + (eqSlashRes - uneqSlashRes));
								pierceRes -= (eqPierceRes - uneqPierceRes);
								slashRes -= (eqSlashRes - uneqSlashRes);
								bluntRes -= (eqBluntRes - uneqBluntRes);
								fireRes -= (eqFireRes - uneqFireRes);
								iceRes -= (eqIceRes - uneqIceRes);
								radRes -= (eqRadRes - uneqRadRes);
								magicRes -= (eqMagicRes - uneqMagicRes);
								curArmor = equiper.name;
								equiper.Equip();
								document.getElementById("armorText").innerText=equiper.name;
								console.log(slashRes);

						}
					

				
			} //ends armor check
	},//ends armor function

	shield: function(){
						if(equiper.name == shieldHand){alert("You already Have this equipped");}
						else{ if(window[weaponHand].hand==2){alert("You can not equip a shield with a two handed weapon already equipped.");}
						else{
								console.log("shield");
								console.log("current slash" + slashRes);
								var eqPierceRes = equiper.pierceRes;
								var eqSlashRes = equiper.slashRes;
								var eqBluntRes = equiper.bluntRes;
								var eqFireRes = equiper.fireRes;
								var eqIceRes = equiper.iceRes;
								var eqRadRes = equiper.radRes;
								var eqMagicRes = equiper.magicRes;
								var sheUnequiper = window[shieldHand];
								console.log("unequipping" + sheUnequiper.name);
								var uneqPierceRes = sheUnequiper.pierceRes;
								var uneqSlashRes = sheUnequiper.slashRes;
								var uneqBluntRes = sheUnequiper.bluntRes;
								var uneqFireRes = sheUnequiper.fireRes;
								var uneqIceRes = sheUnequiper.iceRes;
								var uneqRadRes = sheUnequiper.radRes;
								var uneqMagicRes = sheUnequiper.magicRes;
								console.log("unequiper" + uneqSlashRes);
								console.log("subtracting" + (eqSlashRes - uneqSlashRes));
								pierceRes -= (eqPierceRes - uneqPierceRes);
								slashRes -= (eqSlashRes - uneqSlashRes);
								bluntRes -= (eqBluntRes - uneqBluntRes);
								fireRes -= (eqFireRes - uneqFireRes);
								iceRes -= (eqIceRes - uneqIceRes);
								radRes -= (eqRadRes - uneqRadRes);
								magicRes -= (eqMagicRes - uneqMagicRes);
								shieldHand = equiper.name;
								equiper.Equip();
								document.getElementById("shieldText").innerText=equiper.name;
								console.log(slashRes);
								

						}


				
			} //ends armor check

	},//ends shield function


};//ends var equips





