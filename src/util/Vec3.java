package util;

public class Vec3 {
    float x, y, z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vec3(){
        this.x = this.y = this.z = 0.0f;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

    public float dot(Vec3 v){
        return v.x*x + v.y*y + v.z*z;
    }

    public Vec3 sum(Vec3 v){
        return new Vec3(x + v.x,y + v.y,z + v.z);
    }
    public void add(Vec3 v){
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public Vec3 diff(Vec3 v){
        return new Vec3(x - v.x,y - v.y,z - v.z);
    }
    public void subtract(Vec3 v){
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public Vec3 prod(Vec3 v){
        return new Vec3(x * v.x,y * v.y,z * v.z);
    }
    public void multiply(Vec3 v){
        x *= v.x;
        y *= v.y;
        z *= v.z;
    }

    public Vec3 quotient(Vec3 v){
        return new Vec3(x / v.x,y / v.y,z / v.z);
    }
    public void divide(Vec3 v){
        x /= v.x;
        y /= v.y;
        z /= v.z;
    }

    public float squaredMag(){
        return x*x + y*y + z*z;
    }

}
