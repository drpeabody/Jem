package util;

class Animator {
    private float startTime;
    private Animatable a;
    private float duration;


    Animator(Animatable a, float startTime, float duration){
        this.startTime = startTime;
        this.a = a;
        this.duration = duration;
    }

    // Returns whether the animation is done or not
    boolean update(float currentTime){
        if(currentTime < startTime) return false;
        float x = (currentTime - startTime) / duration;
        a.animate(x);
        return x > 1f;
    }
}


