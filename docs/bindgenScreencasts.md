---
layout: default
title: Bindgen Screencasts
---

<script type="text/javascript" src="swfobject.js"></script>
<script type="text/javascript">
    swfobject.registerObject("csSWF", "9.0.115", "screencasts/expressInstall.swf");
</script>

Bindgen Screencasts
===================

This screencast shows the details of how bindgen basically duplicates the effect type-safe anonymous inner classes. 

<div id="media">
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="640" height="498" id="csSWF">
        <param name="movie" value="screencasts/bindgen_controller.swf" />
        <param name="quality" value="best" />
        <param name="bgcolor" value="#1a1a1a" />
        <param name="allowfullscreen" value="true" />
        <param name="scale" value="showall" />
        <param name="allowscriptaccess" value="always" />
        <param name="flashvars" value="autostart=false&thumb=screencasts/bindgen_firstFrame.png&thumbscale=45&color=0x000000,0x000000" />
        <!--[if !IE]>-->
        <object type="application/x-shockwave-flash" data="screencasts/bindgen_controller.swf" width="640" height="498">
            <param name="quality" value="best" />
            <param name="bgcolor" value="#1a1a1a" />
            <param name="allowfullscreen" value="true" />
            <param name="scale" value="showall" />
            <param name="allowscriptaccess" value="always" />
            <param name="flashvars" value="autostart=false&thumb=screencasts/bindgen_firstFrame.png&thumbscale=45&color=0x000000,0x000000" />
        <!--<![endif]-->
            <div id="noUpdate">
                <p>The Camtasia Studio video content presented here requires a more recent version of the Adobe Flash Player. If you are you using a browser with JavaScript disabled please enable it now. Otherwise, please update your version of the free Flash Player by <a href="http://www.adobe.com/go/getflashplayer">downloading here</a>.</p>
            </div>
        <!--[if !IE]>-->
        </object>
        <!--<![endif]-->
    </object>
</div>


Note
----

Per the [caveat](index.html), Eclipse must be running *on* a JDK6 JVM for the annotation processing to work--it cannot be running on a JDK5 JVM and using the "JDK6" compiler option.

