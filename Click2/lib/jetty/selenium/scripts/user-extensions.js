
function getCookie(name) {
   var dc = document.cookie;
   
   // find beginning of cookie value in document.cookie
   var prefix = name + "=";
   var begin = dc.indexOf("; " + prefix);
   if (begin == -1) {
      begin = dc.indexOf(prefix);
      if (begin != 0) return null;
   }
   else begin += 2;
   
   // find end of cookie value
   var end = document.cookie.indexOf(";", begin);
   if (end == -1) end = dc.length;
   
   // return cookie value
   return unescape(dc.substring(begin + prefix.length, end));
}

function callSetupServlet(parameters, host) {
	if (!host) { host = getCookie("basePath"); }
	var ajax = new Ajax.Request(
					host + "/setup",
	{ method: 'get', parameters: parameters, asynchronous: false });
	Assert.equals("okay", ajax.transport.responseText);
}

function pageX(element) {
	return element.offsetParent ? element.offsetLeft + pageX(element.offsetParent) : element.offsetLeft;
}

function pageY(element) {
	return element.offsetParent ? element.offsetTop + pageY(element.offsetParent) : element.offsetTop;
}

Selenium.prototype.doSetup = function(testdata) {
	callSetupServlet("testdata=" + testdata, false);
	this.doKillAllWindows();
};

Selenium.prototype.doFdrSend = function(testdata, host) {
	callSetupServlet("fdrSend=1", host);
};

Selenium.prototype.doFdrReceive = function(testdata, host) {
	callSetupServlet("fdrReceive=1", host);
};

Selenium.prototype.doRunProcess = function(process, host) {
	callSetupServlet("runProcess=" + process, host);
};

Selenium.prototype.doProduction = function(testdata, host) {
	callSetupServlet("production=1", host);
};

Selenium.prototype.doFaxLoader = function(testdata, host) {
	callSetupServlet("faxloader=1", host);
};

Selenium.prototype.doReceiptRequest = function(testdata, host) {
	callSetupServlet("receiptRequest=1", host);
};

Selenium.prototype.doIsoDcpAccountStatusUpdate = function(testdata, host) {
	callSetupServlet("isoDcpAccountStatusUpdate=1", host);
};

Selenium.prototype.doTestFiles = function(processAndTest, host) {
	callSetupServlet("testFiles=" + processAndTest, host);
};

Selenium.prototype.doTestFiles2 = function(host) {
	callSetupServlet("testFiles2=true", host);
};

Selenium.prototype.doFreeze = function(datetime, host) {
	callSetupServlet(("timefreeze=" + datetime), host);
};

Selenium.prototype.doDebug = function(host) {
 	callSetupServlet(("debug=true"), host);
};

Selenium.prototype.doSwitchUser = function(username, host) {
	callSetupServlet("username=" + username, host);
};

Selenium.prototype.doClearUser = function(host) {
	callSetupServlet("clearUser=clearUser", host);
};

Selenium.prototype.doReplace = function(locator, text) {
	var element = this.page().findElement(locator);
	var existing = getInputValue(element);
	var parts = text.split(',');
	this.page().replaceText(element, existing.replace(new RegExp(parts[0].trim(), "g"), parts[1].trim()));
};

Selenium.prototype.doSit = function(locator, text) {
	this.browserbot.newPageLoaded = false;
};

Selenium.prototype.assertOccurs = function(expectedNumber, text) {
	var allText = this.page().bodyText();
	var actualNumber = 0;
	while (allText.indexOf(text) != -1) {
		allText = allText.substring(allText.indexOf(text) + 1);
		actualNumber++;
	}
	Assert.equals(expectedNumber, actualNumber.toString());
};

Selenium.prototype.assertOrder = function(text1, text2) {
	var allText = this.page().bodyText();
	Assert.equals(true, allText.indexOf(text1) < allText.indexOf(text2));
};

Selenium.prototype.doWaitForIndicator = function() {
	return this.doWaitForCondition("selenium.page().findElement('statusIndicator').style.display == 'none'", 30000);
};

Selenium.prototype.doWaitForCallTimer = function() {
	var value = "selenium.page().findElement('callTimer').childNodes[0].nodeValue";
	return this.doWaitForCondition(value + " != '00:00'", 30000);
};

Selenium.prototype.assertElementBackgroundColor = function(locator, text) {
	var element = this.page().findElement(locator);
	Assert.matches(text, element.style.backgroundColor);
};

Selenium.prototype.assertValid = function(locator) {
	var element = this.page().findElement(locator);
	if ("white" != element.style.backgroundColor) {
		Assert.matches("White", element.style.backgroundColor);
	}
};

Selenium.prototype.assertInvalid = function(locator) {
	var element = this.page().findElement(locator);
	if ("lightcoral" != element.style.backgroundColor) {
		Assert.matches("LightCoral", element.style.backgroundColor);
	}
};

Selenium.prototype.assertEnabled = function(locator) {
	return !this.assertDisabled(locator);
};

Selenium.prototype.assertDisabled = function(locator) {
	var element = this.page().findElement(locator);
	return element.disabled; 
};


Selenium.prototype.assertTextLenghtGreaterThanZero = function(locator) {
	var value = this.getText(locator).length;
	Assert.equals(true, 0 < value);
};

Selenium.prototype.assertTextLenghtEqualZero = function(locator) {
	var value = this.getText(locator).length;
	Assert.equals(true, value == 0);
};

Selenium.prototype.doClickAndWaitForMain = function(locator) {
	var element = this.page().findElement(locator);
	this.page().clickElement(element);
	return this.doPause(10000);
};

Selenium.prototype.doClickAndWaitJSLinkInDebug = function(locator) {
	/**
	 * This method will attach an extra parameter, 'yes', to a javascript href method
	 * to allow debugging turned on
	 */
	var element = this.page().findElement(locator);
	element.href = element.href.substring(0, element.href.length - 2);
	element.href += ", 'yes');";
	this.page().clickElement(element);
	return this.doPause(5000);
};

Selenium.prototype.doKeyDownWithShiftAndAlt = function(locator, keySequence) {
	/**
	 * Simulates a user pressing a key (without releasing it yet).
	 *
	 * @param locator an <a href="#locators">element locator</a>
	 * @param keySequence Either be a string("\" followed by the numeric keycode
	 *  of the key to be pressed, normally the ASCII value of that key), or a single
	 *  character. For example: "w", "\119".
	 */
	var element = this.page().findElement(locator);
	triggerKeyEvent(element, 'keydown', keySequence, true, false, true, true);
};

Selenium.prototype.doKeyDownWithShiftAndAltAndWait = function(locator, keySequence) {
	this.doKeyDownWithShiftAndAlt(locator, keySequence);
	return this.doPause(5000);
};

Selenium.prototype.doClaimEntrySubmitAndWait = function(locator) {
	this.doKeyDownWithShiftAndAltAndWait(locator, '83');
};

Selenium.prototype.doClaimEntrySubmit = function(locator) {
	this.doKeyDownWithShiftAndAlt(locator, '83');
};

Selenium.prototype.doClaimEntryRemoveLine = function(locator) {
	this.doKeyDownWithShiftAndAlt(locator, '82');
};

Selenium.prototype.doAssertInlineClaimField = function(field, rowAndValue) {
	var row = rowAndValue.split(':')[0];
	var value = rowAndValue.split(':')[1];
	this.browserbot.getCurrentWindow().claimEntryInlineTable.editField(field + 'ValueHolder-' + row);
	this.doPause(500);
	var element = this.page().findElement('inlineEditField');
	return Assert.equals(value, element.value);
};

Selenium.prototype.doEditInlineClaimField = function(field, rowAndValue) {
	var row = rowAndValue.split(':')[0];
	var value = rowAndValue.split(':')[1];
	this.browserbot.getCurrentWindow().claimEntryInlineTable.editField(field + 'ValueHolder-' + row);
	this.doPause(500);
	this.doType('inlineEditField', value);
};

Selenium.prototype.doIsInlineEditable = function(field, row) {
	this.browserbot.getCurrentWindow().claimEntryInlineTable.editField(field + 'ValueHolder-' + row);
	try {
		this.page().findElement('inlineEditField');
	} catch(e) {
		return Assert.fail("Is not editable");
	}
};

Selenium.prototype.doIsNotInlineEditable = function(field, row) {
	this.browserbot.getCurrentWindow().claimEntryInlineTable.editField(field + 'ValueHolder-' + row);
	try {
		this.page().findElement('inlineEditField');
	} catch(e) {
		return Assert.equals(true, true);
	}
	return Assert.fail("Is editable");
};

Selenium.prototype.doBlurInlineEditField = function() {
	return this.doFireEvent('inlineEditField', 'blur');
};

Selenium.prototype.doHotKeyAndWait = function(key) {
	this.doKeyDownWithShiftAndAlt('contentcontainer', key.charCodeAt(0));
	this.doPause(5000);
};

Selenium.prototype.doKillAllWindows = function() {
	var _self = this.browserbot.getCurrentWindow();
	for (var windowName in this.browserbot.openedWindows) {
        var targetWindow = this.browserbot.openedWindows[windowName];
		if ('_self' != windowName && targetWindow && !this.browserbot._windowClosed(targetWindow)) {
			this.browserbot._selectWindowByName(windowName);
			this.doClose();
		}
	}
	this.browserbot.currentWindow = _self;
};

Selenium.prototype.doAddTinyMCEText = function(ccntent) {
	var myMCE = this.browserbot.getCurrentWindow().tinyMCE;
	myMCE.setContent.apply(this.page(), arguments);
};

Selenium.prototype.doClickAndWaitForTinyMCE = function(submitButtonId) {
	this.doClick(submitButtonId);
	return this.doPause(7000);
};

Selenium.prototype.doSelectAndWaitForTinyMCE = function(locator, value) {
	this.doSelect(locator, value);
	return this.doPause(7000);
};

Selenium.prototype.doWaitAndVerifyValue = function(locator, value) {
	this.doPause(5000);
	var element = this.page().findElement(locator);
	return Assert.equals(value, element.value);
};

Selenium.prototype.doDragdropOverElement = function(elementMove, elementDrop) {
	var from = this.page().findElement(elementMove);
	var to = this.page().findElement(elementDrop);
	
	var currentX = pageX(from);
	var currentY = pageY(from);
	
	var toX = pageX(to);
	var toY = pageY(to);
	
	var offsetX = toX - currentX;
	var offsetY = toY - currentY;
	if(offsetX > 0 ) {
		offsetX = '+' + offsetX;
	}
	
	if(offsetY > 0) {
		offsetY = '+' + (offsetY + 20);
	} else {
		offsetY = offsetY - 20;
	}
	return this.doDragdrop(elementMove, offsetX + ',' + offsetY);
};

Selenium.prototype.doTypeInUploadField = function(locator, value) {
	if (navigator.appVersion.indexOf("Windows") > -1) {
		value = value.replace(/\//g, "\\");
	}
	// get around security for file input fields.
	netscape.security.PrivilegeManager.enablePrivilege('UniversalFileRead');
	this.doType(locator, value);
};

function triggerKeyEvent(element, eventType, keySequence, canBubble, ctrl, alt, shift) {
	var keycode = getKeyCodeFromKeySequence(keySequence);
	canBubble = (typeof(canBubble) == undefined) ? true : canBubble;
	if (element.fireEvent) {
		keyEvent = element.ownerDocument.createEventObject();
		keyEvent.keyCode = keycode;
		keyEvent.shiftKey = shift;
		keyEvent.ctrlKey = ctrl;
		keyEvent.metaKey = alt;
		element.fireEvent('on' + eventType, keyEvent);
	}
	else {
		var evt;
		if (window.KeyEvent) {
			evt = document.createEvent('KeyEvents');
			evt.initKeyEvent(eventType, true, true, window, ctrl, alt, shift, alt, keycode, keycode);
			
		} else {
			evt = document.createEvent('UIEvents');
			evt.initUIEvent(eventType, true, true, window, 1);
			evt.keyCode = keycode;
		}

		element.dispatchEvent(evt);
	}
}

