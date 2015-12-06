/*
 * Copyright (c) 2012 Quackster <alex.daniel.97@gmail>. 
 * 
 * This file is part of Sierra.
 * 
 * Sierra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Sierra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Sierra.  If not, see <http ://www.gnu.org/licenses/>.
 */

package net.quackster.icarus.game.room.models;

public class RoomModel 
{
	private String name;
	private String heightmap;
	private int doorX;
	private int doorY;
	private int doorZ;
	private int doorRot;
	private int mapSizeX;
	private int mapSizeY;
	private int mapSize;
	private int OPEN = 0;
	private int CLOSED = 1;
	private int[][] squares;
	private double[][] squareHeight;
	private RoomTileState[][] squareState;
	private String[][] squareChar;

	public RoomModel(String name, String heightmap, int doorX, int doorY, int doorZ, int doorRot) {
		
		this.name = name;
		this.heightmap = heightmap;
		this.doorX = doorX;
		this.doorY = doorY;
		this.doorZ = doorZ;
		this.doorRot = doorRot;

		String[] temporary = heightmap.split(Character.toString((char)13));

		this.mapSizeX = temporary[0].length();
		this.mapSizeY = temporary.length;
		this.squares = new int[mapSizeX][mapSizeY];
		this.squareHeight = new double[mapSizeX][mapSizeY];
		this.squareState = new RoomTileState[mapSizeX][mapSizeY];
		this.squareChar = new String[mapSizeX][mapSizeY];


		for (int y = 0; y < mapSizeY; y++) {
			
			if (y > 0) {
				temporary[y] = temporary[y].substring(1);
			}

			for (int x = 0; x < mapSizeX; x++) {
				
				String square = temporary[y].substring(x,x + 1).trim().toLowerCase();

				if (square.equals("x"))	{
					squareState[x][y] = RoomTileState.INVALID;
					squares[x][y] = CLOSED;
					
				} else if(isNumeric(square)) {
					
					squareState[x][y] = RoomTileState.VALID;
					squares[x][y] = OPEN;
					squareHeight[x][y] = Double.parseDouble(square);
					mapSize++;
				}
				
				squareChar[x][y] = square;

			}
		}
	}
	
	public String getHeightmap() {
		return heightmap;
	}
	
	private boolean isNumeric(String input) {
		
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getDoorX() {
		return doorX;
	}
	
	public int getDoorY() {
		return doorY;
	}
	
	public int getDoorZ() {
		return doorZ;
	}
	
	public int getDoorRot() {
		return doorRot;
	}
	
	public int getMapSizeX() {
		return mapSizeX;
	}
	
	public int getMapSizeY() {
		return mapSizeY;
	}
	
	public int getMapSize() {
		return mapSize;
	}

	public RoomTileState[][] getTileState() {
		return squareState;
	}
	
	public double[][] getSquareHeight() {
		return squareHeight;
	}
	
	public int[][] getSqState() {
		return squares;
	}

	public String[][] getSquareChar() {
		return squareChar;
	}
}