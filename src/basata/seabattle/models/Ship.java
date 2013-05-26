package basata.seabattle.models;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author ARTIST
 */
public class Ship {
    private Field field;
    private int x;
    private int y;
    private int size;
    private int health;
    private int position;
    private Point[] points;
    
    /**
     * 
     * @param x   - start coordinate
     * @param y   - start coordinate
     * @param size - ship size
     * @param position - 0 if horizontal
     *                 - 1 if vertical
     */
    
    public Ship(Field field, int size) {
        this.field = field;
        this.size = size;
        this.health = size;
        points = new Point[size];
    }
    
    
    public Field getField() {
		return field;
	}
    /**
     * Check if (i,j) are in field
     * @param i
     * @param j
     * @return 
     */
    private boolean inField(int i, int j){
        boolean f;
        if((i >= 0) && (i <= 9) && (j >= 0) && (j <= 9)){
            f =  true;
        } else{
            f = false;
        }
        return f;
    }
    
    /**
     * Check, can we put ship with position @param p to
     * cell @param x, @param y; 
     * 
     */
    public boolean CheckPlace(int x, int y, int p){
        if(p == 0){
            if (y + this.size >= 9){
                return false;
            }
            for(int i = x - 1; i < x + 2; i++){
                for (int j = y - 1; j < y + this.size + 1; j++) {
                    if ( (inField(i, j))  &&
                            (this.field.getElem(i,j) != Field.FREE_CELL)) {
                        return false;
                    }    
                }
            }
        } else{
            if (x + this.size >= 9){
                return false;
            }
            for(int i = x - 1; i < x + this.size + 1; i++){
                for (int j = y - 1; j < y + 2; j++) {
                    if ((inField(i, j) ) &&
                            (this.field.getElem(i,j) != Field.FREE_CELL)){
                        return false;
                    }    
                }
            }
        }
        return true;    
    }
    
    
    
    public boolean CheckPlace(int x, int y, int size, int p){
        if(p == 0){
            if (y + size > 10){
                return false;
            }
            for(int i = x - 1; i < x + 2; i++){
                for (int j = y - 1; j < y + size + 1; j++) {
                    if ( (inField(i, j))  &&
                            (this.field.getElem(i,j) != Field.FREE_CELL)) {
                        return false;
                    }    
                }
            }
        } else{
            if (x + size > 10){
            	return false;
            }
            for(int i = x - 1; i < x + size + 1; i++){
                for (int j = y - 1; j < y + 2; j++) {
                    if ((inField(i, j) ) &&
                            (this.field.getElem(i,j) != Field.FREE_CELL)){
                        return false;
                    }    
                }
            }
        }
        return true;    
    }
    /**
     * chose plase and position for ship
     */
    public void SetPlace(){
        Random rand = new Random();
        int xpos = 0;
	int ypos = 0;
        int pos = 0;
        boolean f = false;
        while (f != true){
            xpos = Math.abs(rand.nextInt()) % 10;
            ypos = Math.abs(rand.nextInt()) % 10;
            pos = Math.abs(rand.nextInt()) % 2;
          
        
            f = CheckPlace(xpos, ypos, pos);
        }
	
        this.x = xpos;
        this.y = ypos;
        this.position = pos;
        
        this.SetShip();
        
    }
    
    /**
     * Set current ship on game board
     */
    private void SetShip(){
        int k = 0;
        if (this.position == 0){
            for(int j = this.y; j < this.y + this.size; j++){
                points[k] = new Point(this.x, j);
                this.field.setElem(this.x, j, Field.CELL_WITH_SHEEP);
                k++;
            }
        }else{
            for(int j = this.x; j < this.x + this.size; j++){
                points[k] = new Point(j, this.y);
                this.field.setElem(j, this.y, Field.CELL_WITH_SHEEP);
                k++;
            }
        }
    }
    
    
    public void SetShip(int x, int y, int size, int position){
        int k = 0;
        if (position == 0){
            for(int j = y; j < y + size; j++){
                points[k] = new Point(x, j);
                this.field.setElem(x, j, Field.CELL_WITH_SHEEP);
                k++;
            }
        }else{
            for(int j = x; j < x + size; j++){
                points[k] = new Point(j, y);
                this.field.setElem(j, y, Field.CELL_WITH_SHEEP);
                k++;
            }
        }
    }
    
    

    
    /**
     * finding ship with part on @param x, @param y
     * and decriment health of this ship
     */
    public boolean  find(int x, int y){
        boolean rez = false;
        for (int i = 0; i < points.length; i++) {
            if ((points[i].getX() == x)&&(points[i].getY() == y)){
                rez = true;
            }
        }
        if(rez){
            this.health --;
        }
        return rez;
    }
    
    /**
     * Fill area aroun ship, when ship is destroyed
     */
    public void fillAround(){
        if(this.position == 0){
            
            for(int i = x - 1; i < x + 2; i++){
                for (int j = y - 1; j < y + this.size + 1; j++) {
                    if ((inField(i, j) ) && 
                            (this.field.getElem(i, j) != Field.STRUCKED_CELL_WITH_SHEEP)){
                        this.field.setElem(i, j, Field.STRUCKED_FREE_CELL);
                    }    
                }
            }
        } else{
            for(int i = x - 1; i < x + this.size + 1; i++){
                for (int j = y - 1; j < y + 2; j++) {
                    if ((inField(i, j) ) && 
                            (this.field.getElem(i, j) != Field.STRUCKED_CELL_WITH_SHEEP)){
                        this.field.setElem(i, j, Field.STRUCKED_FREE_CELL);
                    }    
                }
            }
        }
    }
    
    
    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * Show ship points to console
     */
    public void showPoints(){
        System.out.println(size+"  ");
        for (int i = 0; i < this.points.length; i++) {
            System.out.println(points[i].getX()+"  "+points[i].getY());
        }
    }
    
}