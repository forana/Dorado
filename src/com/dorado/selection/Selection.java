package com.dorado.selection;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Selection {
	private Set<Point> points;
	
	public Selection() {
		points = new HashSet<Point>();
	}
	
	public Selection(int x, int y, int width, int height) {
		this();
		
		for (int i=0; i < width; i++) {
			for (int j=0; j < height; j++) {
				points.add(new Point(x + i, y + j));
			}
		}
	}
	
	public static Selection union(Selection a, Selection b) {
		Selection r = new Selection();
		
		r.points.addAll(a.points);
		r.points.addAll(b.points);
		
		return r;
	}
	
	public static Selection intersection(Selection a, Selection b) {
		Selection r = new Selection();
		
		for (Point p : a.points) {
			if (b.points.contains(p)) {
				r.points.add(p);
			}
		}
		
		return r;
	}
	
	public static Selection minus(Selection a, Selection b) {
		Selection r = new Selection();
		
		r.points.addAll(a.points);
		
		for (Point p : b.points) {
			r.points.remove(p);
		}
		
		return r;
	}
	
	public Iterable<Point> getIncludedPoints() {
		return points;
	}
}
