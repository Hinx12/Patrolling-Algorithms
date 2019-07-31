// Nathan Lewis
// MSc Project

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/*
 * Class represents the group colours of points
 * From G1 to G12
 */
public class Colors {
	
	private List<Color> Colors = new ArrayList<>();

	public Colors() {
		
		Colors.add(new Color(255,51,51));
		Colors.add(new Color(96,96, 96));
		Colors.add(new Color(102,102,255));
		Colors.add(new Color(255, 153, 51));
		Colors.add(new Color(255,153,153));
		Colors.add(new Color(204,204,255));
		Colors.add(new Color(153,255,51));
		Colors.add(new Color(255,204,229));
		Colors.add(new Color(0, 204, 102));
		Colors.add(new Color(255, 204, 204));
		Colors.add(new Color(229,255,204));
		Colors.add(new Color(224, 244,244));
		
	}
	
	public List<Color> getColors() {
		return Colors;
	}

}
