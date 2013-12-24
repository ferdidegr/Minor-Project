package Frustum;

import java.util.ArrayList;
import Utils.Vector;


/**
 * 
 * I neglect the near plane
 * @author ZL
 *
 */

public class ftum {

	private Plane pnear, pfar, pbottom, ptop, pleft, pright;					// Planes
	private Vector vup, vright;													// Vectors
	private Vector farcenter, farcleft, farcright, farcup, farcbot, nearcenter;	// Points
	private double fov, ratio, nearDist, farDist;								// Perspective parameters
	private double Wfar, Hfar;													// Far plane dimensions
	
	public ftum(double fov, double ratio, double nearDist, double farDist){
		this.fov = fov;
		this.ratio = ratio;
		this.nearDist = nearDist;
		this.farDist = farDist;
		
	}
	
	public ftum update(Vector pLocation, Vector pLookat){
		// Calculate the viewing coordinate
		vright = pLookat.crossprod(new Vector(0,1,0));
		vup = vright.crossprod(pLookat);
		
		// Calculate far plane properties
		Hfar = 2*Math.tan(Math.toRadians(fov/2))*farDist;
		Wfar = Hfar / ratio;
		
		// Calculate the necessary vectors to the points in the far plane from the player location
		// nc = plocation + plookat * nearDist
		nearcenter = pLocation.add(pLookat.scale(nearDist));
		// fc = plocation + plookat * farDist
		farcenter = pLocation.add(pLookat.scale(farDist));
		// right = (fc + right * Wfar / 2) -p
		farcright = farcenter.add(vright.scale(Wfar/2)).add(pLocation.scale(-1));
		// left
		farcleft = farcenter.add(vright.scale(-Wfar/2)).add(pLocation.scale(-1));
		// up
		farcup = farcenter.add(vup.scale(Hfar/2)).add(pLocation.scale(-1));
		// down
		farcbot = farcenter.add(vup.scale(-Hfar/2)).add(pLocation.scale(-1));
		
		// create planes
		pright 	= Plane.derivPlane(farcright, vup, pLocation);
		pleft	= Plane.derivPlane(vup, farcleft, pLocation).normalize();
		ptop	= Plane.derivPlane(vup, farcup, pLocation);
		pbottom	= Plane.derivPlane(vup.scale(-1), farcbot, pLocation);
		pfar	= Plane.derivPlane(pLookat, farcenter);
		pnear	= Plane.derivPlane(pLookat.scale(-1), nearcenter);
		
		
		return this;
	}
	
	public boolean inFrustum(Vector point){
				
		ArrayList<Plane> frustum = new ArrayList<Plane>();
		frustum.add(pright);
		frustum.add(pleft);
		frustum.add(ptop);
		frustum.add(pbottom);
		frustum.add(pnear);
		frustum.add(pfar);
		System.out.println(pleft.toString());
		if (pleft.signDist(point)>1) return false;
		
//		for(int i = 0; i < 6; i++){
//			if(frustum.get(i).signDist(point)>100){return false;}
//		}		
		return true;
	}
	

	
}
