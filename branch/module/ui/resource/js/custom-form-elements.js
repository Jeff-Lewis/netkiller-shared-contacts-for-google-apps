/*

CUSTOM FORM ELEMENTS

Created by Ryan Fait
www.ryanfait.com

The only things you may need to change in this file are the following
variables: checkboxHeight, radioHeight and selectWidth (lines 24, 25, 26)

The numbers you set for checkboxHeight and radioHeight should be one quarter
of the total height of the image want to use for checkboxes and radio
buttons. Both images should contain the four stages of both inputs stacked
on top of each other in this order: unchecked, unchecked-clicked, checked,
checked-clicked.

You may need to adjust your images a bit if there is a slight vertical
movement during the different stages of the button activation.

The value of selectWidth should be the width of your select list image.

Visit http://ryanfait.com/ for more information.
 */

var checkboxHeight = "25";
var radioHeight = "25";
var selectWidth = "190";

/* No need to change anything after this */

document
		.write('<style type="text/css">input.styled { display: none; } select.styled { position: relative; width: 35px; opacity: 0; filter: alpha(opacity=0); z-index: 5; } .disabled { opacity: 0.5; filter: alpha(opacity=50); }</style>');

document
		.write('<style type="text/css">input.styled2 { display: none; } select.styled2 { position: relative; width:90px; opacity: 0; filter: alpha(opacity=0); z-index: 5; float:left; margin-top:-23px; } .disabled { opacity: 0.5; filter: alpha(opacity=50); }</style>');

document
		.write('<style type="text/css">input.styled3 { display: none; } select.styled3 { position: relative; width:100px; opacity: 0; filter: alpha(opacity=0); z-index: 5; float:left;margin-top:5px;} .disabled { opacity: 0.5; filter: alpha(opacity=50); }</style>');

document
		.write('<style type="text/css">input.styled4 { display: none; } select.styled4 { position: relative; width: '
				+ selectWidth
				+ 'px; opacity: 0; filter: alpha(opacity=0); z-index: 5; float:left; margin-top:-23px; } .disabled { opacity: 0.5; filter: alpha(opacity=50); }</style>');

document
		.write('<style type="text/css">input.styled5 { display: none; } select.styled5 { position: relative; width:166px; opacity: 0; filter: alpha(opacity=0); z-index: 5; float:left;margin-top:8px;} .disabled { opacity: 0.5; filter: alpha(opacity=50); }</style>');

var Custom = {
	init : function() {
		var inputs = document.getElementsByTagName("input"), span = Array(), textnode, option, active;

		inputs = document.getElementsByTagName("select");
		for (a = 0; a < inputs.length; a++) {
			if (inputs[a].className == "styled") {
				option = inputs[a].getElementsByTagName("option");
				if (option.length != 0) {
					active = option[0].childNodes[0].nodeValue;
					textnode = document.createTextNode(active);
					for (b = 0; b < option.length; b++) {
						if (option[b].selected == true) {
							textnode = document
									.createTextNode(option[b].childNodes[0].nodeValue);
						}
					}
					span[a] = document.createElement("span");
					span[a].className = "select";
					span[a].id = "select" + inputs[a].name;
					span[a].appendChild(textnode);
					inputs[a].parentNode.insertBefore(span[a], inputs[a]);
					if (!inputs[a].getAttribute("disabled")) {
						inputs[a].onchange = Custom.choose;
					} else {
						inputs[a].previousSibling.className = inputs[a].previousSibling.className += " disabled";
					}
				}

			} else if (inputs[a].className == "styled2") {
				option = inputs[a].getElementsByTagName("option");
				active = option[0].childNodes[0].nodeValue;
				textnode = document.createTextNode(active);
				for (b = 0; b < option.length; b++) {
					if (option[b].selected == true) {
						textnode = document
								.createTextNode(option[b].childNodes[0].nodeValue);
					}
				}
				span[a] = document.createElement("span");
				span[a].className = "select2";
				span[a].id = "select" + inputs[a].name;
				span[a].appendChild(textnode);
				inputs[a].parentNode.insertBefore(span[a], inputs[a]);
				if (!inputs[a].getAttribute("disabled")) {
					inputs[a].onchange = Custom.choose;
				} else {
					inputs[a].previousSibling.className = inputs[a].previousSibling.className += " disabled";
				}

			} else if (inputs[a].className == "styled3") {
				option = inputs[a].getElementsByTagName("option");
				active = option[0].childNodes[0].nodeValue;
				textnode = document.createTextNode(active);
				for (b = 0; b < option.length; b++) {
					if (option[b].selected == true) {
						textnode = document
								.createTextNode(option[b].childNodes[0].nodeValue);
					}
				}
				span[a] = document.createElement("span");
				span[a].className = "select3";
				span[a].id = "select" + inputs[a].name;
				span[a].appendChild(textnode);
				inputs[a].parentNode.insertBefore(span[a], inputs[a]);
				if (!inputs[a].getAttribute("disabled")) {
					inputs[a].onchange = Custom.choose;
				} else {
					inputs[a].previousSibling.className = inputs[a].previousSibling.className += " disabled";
				}
			} else if (inputs[a].className == "styled4") {
				option = inputs[a].getElementsByTagName("option");
				active = option[0].childNodes[0].nodeValue;
				textnode = document.createTextNode(active);
				for (b = 0; b < option.length; b++) {
					if (option[b].selected == true) {
						textnode = document
								.createTextNode(option[b].childNodes[0].nodeValue);
					}
				}
				span[a] = document.createElement("span");
				span[a].className = "select4";
				span[a].id = "select" + inputs[a].name;
				span[a].appendChild(textnode);
				inputs[a].parentNode.insertBefore(span[a], inputs[a]);
				if (!inputs[a].getAttribute("disabled")) {
					inputs[a].onchange = Custom.choose;
				} else {
					inputs[a].previousSibling.className = inputs[a].previousSibling.className += " disabled";
				}
			} else if (inputs[a].className == "styled5") {
				option = inputs[a].getElementsByTagName("option");
				active = option[0].childNodes[0].nodeValue;
				textnode = document.createTextNode(active);
				for (b = 0; b < option.length; b++) {
					if (option[b].selected == true) {
						textnode = document
								.createTextNode(option[b].childNodes[0].nodeValue);
					}
				}
				span[a] = document.createElement("span");
				span[a].className = "select5";
				span[a].id = "select" + inputs[a].name;
				span[a].appendChild(textnode);
				inputs[a].parentNode.insertBefore(span[a], inputs[a]);
				if (!inputs[a].getAttribute("disabled")) {
					inputs[a].onchange = Custom.choose;
				} else {
					inputs[a].previousSibling.className = inputs[a].previousSibling.className += " disabled";
				}
			}

		}
		document.onmouseup = Custom.clear;
	},

	clear : function() {
		inputs = document.getElementsByTagName("input");
		for ( var b = 0; b < inputs.length; b++) {
			if (inputs[b].type == "checkbox" && inputs[b].checked == true
					&& inputs[b].className == "styled") {
				inputs[b].previousSibling.style.backgroundPosition = "0 -"
						+ checkboxHeight * 2 + "px";
			} else if (inputs[b].type == "checkbox"
					&& inputs[b].className == "styled") {
				inputs[b].previousSibling.style.backgroundPosition = "0 0";
			} else if (inputs[b].type == "radio" && inputs[b].checked == true
					&& inputs[b].className == "styled") {
				inputs[b].previousSibling.style.backgroundPosition = "0 -"
						+ radioHeight * 2 + "px";
			} else if (inputs[b].type == "radio"
					&& inputs[b].className == "styled") {
				inputs[b].previousSibling.style.backgroundPosition = "0 0";
			}
		}
	},

	choose : function() {
		option = this.getElementsByTagName("option");
		for (d = 0; d < option.length; d++) {
			if (option[d].selected == true) {
				document.getElementById("select" + this.name).childNodes[0].nodeValue = option[d].childNodes[0].nodeValue;
			}
		}

	}
}
window.onload = Custom.init;