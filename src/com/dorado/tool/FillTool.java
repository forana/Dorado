package com.dorado.tool;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FillTool extends Tool {

	@Override
	protected String getCursorImagePath() {
		return "resources/ui/tools/fill-cursor.png";
	}

	@Override
	protected String getIconPath() {
		return "resources/ui/tools/fill-button.png";
	}

	@Override
	public String getName() {
		return "Fill";
	}

	@Override
	protected void onMouseDown() {
		int targetIndex = model.getColorIndexAt(currentLocation.x, currentLocation.y);
		if (colorIndex != targetIndex) {
			Set<Point> chosenPoints = new HashSet<Point>();
			Set<Point> toInvestigate = new HashSet<Point>();
			Set<Point> investigated = new HashSet<Point>();
			toInvestigate.add(new Point(currentLocation.x, currentLocation.y));
			
			Point[] directions = new Point[4];
			
			while (toInvestigate.size() > 0) {
				Iterator<Point> iter = toInvestigate.iterator();
				Point point = iter.next();
				iter.remove();
				investigated.add(point);
				
				if (model.getColorIndexAt(point.x, point.y) == targetIndex) {
					chosenPoints.add(point);
					
					directions[0] = new Point(point.x - 1, point.y);
					directions[1] = new Point(point.x + 1, point.y);
					directions[2] = new Point(point.x, point.y - 1);
					directions[3] = new Point(point.x, point.y + 1);
					
					for (Point d : directions) {
						if (!investigated.contains(d) &&
							d.x >= 0 && d.x < model.getWidth() &&
							d.y >= 0 && d.y < model.getHeight()) {
							toInvestigate.add(d);
						}
					}
				}
			}
			
			List<ColoredPoint> originalPoints = new LinkedList<ColoredPoint>();
			List<ColoredPoint> affectedPoints = new LinkedList<ColoredPoint>();
			for (Point p : chosenPoints) {
				originalPoints.add(new ColoredPoint(p.x, p.y, targetIndex));
				affectedPoints.add(new ColoredPoint(p.x, p.y, colorIndex));
			}
			
			actionList.addAndApply(new RasterToolAction(getName(), originalPoints, affectedPoints));
		}
	}

	@Override
	protected void onMouseUp() {
	}

	@Override
	protected void onMouseMoved() {
	}

	@Override
	public void cleanUp() {
	}

}
