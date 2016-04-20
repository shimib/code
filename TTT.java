package ttt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ttt.Player.*;
import static ttt.Position.*;
public class TTT {
	 
	public static void main(String[] args) {
		List<List<Position>> winCOnds = Arrays.asList(Arrays.asList(N,NW,NE),
				Arrays.asList(E,C,W),
				Arrays.asList(S,SW,SE),
				Arrays.asList(N,C,S),
				Arrays.asList(C,NW,SE),
				Arrays.asList(C,SW,NW),
				Arrays.asList(SE,E,NE),
				Arrays.asList(NW,SW,W));
		
		
		Map<Position, Player> current = new HashMap<Position, Player>(){{
			put(N, X);
			put(E,O);
			put(NW,X);
			put(S, O);
			put(NE, X);
		}};
		
		
		boolean isWin = 
		
		
		
	}
}
