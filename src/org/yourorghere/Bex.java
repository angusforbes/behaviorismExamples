package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;


/**
 * @author Brian Paul
 * Converted to Java by Ron Cemer and Sven Goethel,
 * ported to JOGL2 by Michael Bien.
 *
 * This code is based on Brian Paul's version 1.2 1999/10/21
 */
public class Bex implements GLEventListener {

    public static void main(String[] args) {

        Frame frame = new Frame("Simple JOGL Application");

        // use GL2 profile since we only use the old OpenGL 2.x fixed function pipeline
        GLCapabilities capabilities = new GLCapabilities(GLProfile.get(GLProfile.GL2));

        // try to enable 2x anti aliasing - should be supported on most hardware
//        capabilities.setNumSamples(2);
//        capabilities.setSampleBuffers(true);

        GLCanvas canvas = new GLCanvas(capabilities);

        canvas.addGLEventListener(new Bex());
        frame.add(canvas);

        // use JOGL's Animator utility for rendering
        final Animator animator = new Animator(canvas);

        // stop the Animator when we receive a window closing event
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        // Center frame, set its size and start rendering
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    // GLEventListener methods

    public void init(GLAutoDrawable drawable) {

        // Use debug pipeline, all OpenGL error codes will be automatically
        // converted to GLExceptions as soon as they appear
        drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));

        GL2 gl = drawable.getGL().getGL2();
        System.out.println("INIT GL2 IS: " + gl.getClass().getName());

        // Enable VSync - this limits the rendering FPS to screen refresh rate
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        // avoid a divide by zero error!
        if (height <= 0)
            height = 1;

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        // Clear the drawing area
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);

        // Drawing Using Triangles
        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glColor3f(1.0f, 0.0f, 0.0f);    // Set the current drawing color to red
            gl.glVertex3f(0.0f, 1.0f, 0.0f);   // Top
            gl.glColor3f(0.0f, 1.0f, 0.0f);    // Set the current drawing color to green
            gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
            gl.glColor3f(0.0f, 0.0f, 1.0f);    // Set the current drawing color to blue
            gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
        // Finished Drawing The Triangle
        gl.glEnd();

        // Move the "drawing cursor" to another position
        gl.glTranslatef(3.0f, 0.0f, 0.0f);
        // Draw A Quad
        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.5f, 0.5f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, 1.0f, 0.0f);  // Top Left
            gl.glVertex3f(1.0f, 1.0f, 0.0f);   // Top Right
            gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();

    }

    public void dispose(GLAutoDrawable arg0) {
    }

}
