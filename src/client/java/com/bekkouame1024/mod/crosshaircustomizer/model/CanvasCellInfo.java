package com.bekkouame1024.mod.crosshaircustomizer.model;

public class CanvasCellInfo {
    private final int column;
    private final int row;
    private boolean isPainted;
    
    public CanvasCellInfo(int column, int row, boolean isPainted) {
        this.column = column;
        this.row = row;
        this.isPainted = isPainted;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getRow() {
        return row;
    }
    
    public boolean isPainted() {
        return isPainted;
    }
    
    public void setPainted(boolean painted) {
        isPainted = painted;
    }
}
