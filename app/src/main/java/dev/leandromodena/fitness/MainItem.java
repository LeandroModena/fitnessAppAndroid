package dev.leandromodena.fitness;

public class MainItem {
    private int id;
    private int drawable;
    private int textStringId;
    private int color;

    public MainItem(int id, int drawable,int textStringId ,int color) {
        this.id = id;
        this.drawable = drawable;
        this.textStringId = textStringId;
        this.color = color;
    }

    public int getTextStringId() {
        return textStringId;
    }

    public void setTextStringId(int textStringId) {
        this.textStringId = textStringId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}









