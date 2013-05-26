package basata.seabattle.models;

/**
 * 
 * @author ARTIST
 */
public class Field {
    
	public final static byte N = 10;
	public final static byte FREE_CELL = 0;
	public final static byte CELL_WITH_SHEEP = 1;
	public final static byte STRUCKED_FREE_CELL = 3;
	public final static byte STRUCKED_CELL_WITH_SHEEP = 2;

	private byte[][] pole;
	private Ship[] ships;
    private int shipsCount;

	public Field() {
		this.pole = new byte[N][N];
		this.ships = new Ship[10];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				pole[i][j] = FREE_CELL;
			}
		}
		
		ships[0] = new Ship(this, 4);
        ships[1] = new Ship(this, 3);
        ships[2] = new Ship(this, 3);
        ships[3] = new Ship(this, 2);
        ships[4] = new Ship(this, 2);
        ships[5] = new Ship(this, 2);
        ships[6] = new Ship(this, 1);
        ships[7] = new Ship(this, 1);
        ships[8] = new Ship(this, 1);
        ships[9] = new Ship(this, 1);
        
        shipsCount = 10;
	}
	
	
	public void setField(byte[][] pole){
		this.pole = pole;
	}
	
	public byte[][] getField(){
		return pole;
	}

	public byte getElem(int i, int j) {
		return this.pole[i][j];
	}

	public void setElem(int i, int j, byte val) {
		this.pole[i][j] = val;
	}
	
	
	public void setShipsArray(Ship[] ships) {
		this.ships = ships;
	}
	
	public void setShipsCount(int shipsCount) {
        this.shipsCount = shipsCount;
    }
	
	public int getShipsCount() {
		return shipsCount;
	}
	
	public void SetShips(){
        for (int i = 0; i < ships.length; i++) {
            ships[i].SetPlace();
        }
    }
	
	/**
     * find ship that have part with coordinates @param x @param y 
     * and check ship count
     */
    public void findShip(int x, int y){
        boolean rez = false;
        for (int i = 0; i < ships.length; i++) {
          
            rez = ships[i].find(x, y);
            if(rez){
                if (ships[i].getHealth() == 0){
                    this.shipsCount --;
                    ships[i].fillAround();
                }
                break;
            }
        }
     }
	
	public static byte[][] FromStringToOBJ(String stringField) {
		System.out.println(stringField);
		System.out.println(stringField.length());
		byte[][] pole = new byte[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 10; j++) {
				pole[i][j] = Byte.valueOf(stringField.substring(i * 10 + j, i
						* 10 + j + 1));
			}
		}
		return pole;
	}

	public String FromOBJToString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 10; j++) {
				s.append(String.valueOf(pole[i][j]));
			}
		}
		return s.toString();
	}

	public void show() {
		System.out.println("===================");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(pole[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("===================");
	}
	
	public boolean doShot(int x, int y){
        byte elem = this.pole[x][y];
        byte value = STRUCKED_FREE_CELL;
        boolean rez = false;
       
        if (elem == CELL_WITH_SHEEP){
            
            rez = true;
            value = STRUCKED_CELL_WITH_SHEEP;
        }
        
        if (elem == STRUCKED_CELL_WITH_SHEEP){
            value = STRUCKED_CELL_WITH_SHEEP;
        }
        
        this.pole[x][y] = value;
        
        return rez;
    
    }
	
	
	public static void main(String[] args) {
		String s = "01234567899876543210741025896398745632107946130258"
				 + "01234567899876543210741025896398745632100123456789";
		Field f = new Field();
		
		f.setField(Field.FromStringToOBJ(s));
		f.show();
		String ns = f.FromOBJToString();
		System.out.println(ns.equals(s));
		

	}
}