/**
 * Copyright 2011 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 */
package jogamp.newt.driver.android;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Display;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import jogamp.newt.driver.android.test.GearsGL2ES1;
import com.jogamp.opengl.util.Animator;

import jogamp.newt.driver.android.AndroidWindow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NewtVersionActivity extends Activity {
   AndroidWindow window = null;
   GLWindow glWindow = null;
   Animator animator = null;
   @Override
   public void onCreate(Bundle savedInstanceState) {
       Log.d(MD.TAG, "onCreate - S");
       super.onCreate(savedInstanceState);
       
       System.setProperty("nativewindow.debug", "all");
       System.setProperty("jogl.debug", "all");
       System.setProperty("newt.debug", "all");
       System.setProperty("jogamp.debug.JNILibLoader", "true");
       System.setProperty("jogamp.debug.NativeLibrary", "true");
       // System.setProperty("jogamp.debug.NativeLibrary.Lookup", "true");
       
       GLProfile.initSingleton(true);
       
       GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GLES1));
       // caps.setRedBits(5); caps.setGreenBits(6); caps.setBlueBits(5);
       AndroidDisplay display = (AndroidDisplay) NewtFactory.createDisplay(null);
       AndroidScreen screen = (AndroidScreen) NewtFactory.createScreen(display, 0);
       screen.setAppContext(this.getApplicationContext());

       window = (AndroidWindow) NewtFactory.createWindow(new Object[] { this }, screen, caps);
       setContentView(window.getView());
       
       glWindow = GLWindow.create(window);
       glWindow.addGLEventListener(new GearsGL2ES1(1));
       glWindow.setVisible(true);
       animator = new Animator(glWindow);
       animator.setUpdateFPSFrames(1, null);
       
       Log.d(MD.TAG, "onCreate - X");
   }
   
   @Override
   public void onStart() {
     Log.d(MD.TAG, "onStart - S");
     super.onStart();
     Log.d(MD.TAG, "onStart - X");
   }
     
   @Override
   public void onRestart() {
     Log.d(MD.TAG, "onRestart - S");
     super.onRestart();
     Log.d(MD.TAG, "onRestart - X");
   }

   @Override
   public void onResume() {
     Log.d(MD.TAG, "onResume - S");
     super.onResume();
     if(null != animator) {
         animator.start();
     }
     Log.d(MD.TAG, "onResume - X");
   }

   @Override
   public void onPause() {
     Log.d(MD.TAG, "onPause - S");
     super.onPause();
     if(null != animator) {
         animator.pause();
     }
     Log.d(MD.TAG, "onPause - X");
   }

   @Override
   public void onStop() {
     Log.d(MD.TAG, "onStop - S");
     super.onStop();  
     Log.d(MD.TAG, "onStop - X");
   }

   @Override
   public void onDestroy() {
     Log.d(MD.TAG, "onDestroy - S");
     super.onDestroy(); 
     glWindow.destroy();
     window.destroy();
     Log.d(MD.TAG, "onDestroy - X");
   }   
}
