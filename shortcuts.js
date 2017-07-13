//shorcuts

// new room
var theGate={
	searchables: [],
	takeables: [],
	talkables: [],
	attackables: [],
	roomHeader: "",
	roomLine1: "",
	roomLine2: "",
	roomLine3: "",
	roomLine4: "",
	roomLine5: "",
	roomImage: "url('images/gate.png')",
	onEnter: function(){console.log("enter"});
	openStatus: false,
	openCheck: function(){if(theGate.openStatus !== true && inventory.indexOf("key") == -1){
							alert("The Gate before you is locked.");
							return false;
							}else{
								room.openStatus=true;
								return true;}
						}
};