package Engine;

import javax.swing.*;

public class Window extends JFrame{
    private final int w, h;
    private static Window instance;

    public Window(int w, int h){
        this.w = w;
        this.h = h;
        this.setSize(w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static Window get(int w, int h){
        if(instance==null){
            instance = new Window(w, h);
        }
        return instance;
    }

    public int getW(){
        return w;
    }

    public int getH() {
        return h;
    }
}
