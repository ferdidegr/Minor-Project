package ModelLoader;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	public Vector3f vertex = new Vector3f(); // three indices, not vertices or normals
	public Vector3f normal = new Vector3f();

	public Face(Vector3f vertex, Vector3f normal) {
		this.vertex = vertex;
		this.normal = normal;
	}

	public Vector3f getVertex() {return vertex;}
	public void setVertex(Vector3f vertex) {this.vertex = vertex;}

	public Vector3f getNormal() {return normal;}
	public void setNormal(Vector3f normal) {this.normal = normal;}
}

