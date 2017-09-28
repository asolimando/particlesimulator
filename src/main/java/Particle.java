/* INTERFACCE UTENTE - MATERIALE FORNITO PER PROGETTO DI LABORATORIO */

import java.util.*;

/**
 * The instances of this class are individual particles. Each particle has:
 * type (one of three possible types: SINGLE, DOUBLE, BREAKER),
 * radius (depending on type),
 * speed intensity (depending on type),
 * position of particle center,
 * speed versor,
 * history (what happened to this particle in last animation step,
 * one of five possible values: REFLECT, SPLIT, MERGE, BORDER, PLAIN).
 */

public class Particle
{
  /* CLASS ATTRIBUTES */

  /* Symbolic names for the three particle types. */
  /** symbolic name of breaker particle (when it hits a double particle,
      breaks it into two single particles). */
  public static final int BREAKER = 0;
  /** symbolic name of single particle (when it hits another single
      particle, merges with it into a double particle). */
  public static final int SINGLE = 1;
  /** symbolic name of double particle (when it hits a breaker particle,
      gets split into two single particles). */
  public static final int DOUBLE = 2;

  /* particle types are used as indexes in the following arrays */
 
  /* array of particle radius for each type */
  private static final double[] radiusFromType = { 4.0, 8.0, 12.0 };

  /* array of speed intensity for each type */
  private static final double[] speedFromType = { 4.0, 2.0, 1.0 };

  /* Symbolic names for history (to tell what happened to a given
     particle in the last step of the simulation). */
  /** this particle has hit another particle and has been reflected. */
  public static final int REFLECT = 1;
  /** this particle results from splitting of another one. */
  public static final int SPLIT = 2;
  /** this particle results from merging with another one. */
  public static final int MERGE = 3;
  /** this particle has hit one of the four walls bounding the
      environment, and has been reflected. */
  public static final int BORDER = 4;
  /** this particle has had no hit. */
  public static final int PLAIN = 5;

  /* CLASS METHODS */

  /** Get particle radius (the value of radius depends on particle type).
   * @param     t  particle type, one of SINGLE, DOUBLE, BREAKER
   */
  static public double getRadius(int t)
  {  return radiusFromType[t];  }

  /** Get intensity of particle speed (the value of speed depends on
   *  particle type).
   * @param     t  particle type, one of SINGLE, DOUBLE, BREAKER
   */
  static public double getSpeedValue(int t) 
  {  return speedFromType[t];  }

  /** Return squared distance between the centers of two particles.
   * @param     b1  one particle
   * @param     b2  the other particle
   * @return    squared distance between the centers of b1 and b2
   */
  public static double squaredDistance(Particle b1, Particle b2)
  {
    double dx = (b1.center.x - b2.center.x);
    double dy = (b1.center.y - b2.center.y);
    return ( dx*dx + dy*dy );
  }

  /** Return the distance between the centers of two particles.
   * @param     b1  one particle   
   * @param     b2  the other particle   
   * @return    distance between the centers of b1 and b2
   */
  public static double distance(Particle b1, Particle b2)
  {  return Math.sqrt( squaredDistance(b1,b2) );  }

  /** Test if two particles hit each other.
   * @param     b1  one particle   
   * @param     b2  the other particle
   * @return    true if b1 and b2 hit, false otherwise
   */
  public static boolean isHit(Particle b1, Particle b2)
  {
    double r = b1.getRadius()+b2.getRadius();
    if ( squaredDistance(b1,b2) > (r*r) ) return false;
    return true;
//    /* if directions of the two speeds diverge, then it is not a hit */
//    PairOfDouble tangent = new PairOfDouble(normal);
//    tangent.counterclockRotate(0.0,1.0);
//    if (PairOfDouble.orientation(direction,tangent) 
//        != PairOfDouble.LEFT)
//    {  return new PairOfDouble(direction);  }
  }
 
  /* INSTANCE ATTRIBUTES */

  private int type;               /* particle type */
  private PairOfDouble center;    /* position of center */
  private PairOfDouble direction; /* speed versor */
  private int history;            /* what happened last step */

  /** Create a particle of given type, located at given position
   *  and with given speed direction (speed direction is normalized
   *  inside constructor).
   * @param     t  type of particle to be created, 
                one of SINGLE, DOUBLE, BREAKER
   * @param     x0  x coordinate of particle center
   * @param     y0  y coordinate of particle center
   * @param     svx0  x component of speed direction
   * @param     svy0  y component of speed direction
   */
  public Particle(int t, double x0, double y0,
                  double svx0, double svy0)
  {
    type = t;
    center = new PairOfDouble(x0,y0);
    direction = new PairOfDouble(svx0,svy0);
    direction.normalize();
    history = PLAIN;
  }

  /** Create a particle of given type, located at given position
   *  and with given speed direction (speed direction is normalized
   *  inside constructor).
   * @param     t  type of particle to be created, 
                one of SINGLE, DOUBLE, BREAKER
   * @param     xy0  position of particle center
   * @param     sv0  vector parallel to speed direction
   */
  public Particle(int t, PairOfDouble xy0, PairOfDouble sv0)
  {
    type = t;
    center =  xy0;
    direction = sv0;
    direction.normalize();
    history = PLAIN;
  }

  /** Create a particle by copying it from a given particle.
   * @param     b  particle to be copied
   */
  public Particle(Particle b)
  {
    type = b.getType();
    center = new PairOfDouble(b.center);
    direction = new PairOfDouble(b.direction);
    history = PLAIN;
  }

  /** Set the type of this particle.
   * @param     t  the new type, one of SIGNLE, DOUBLE, BREAKER 
   */
  public void setType(int t) {  type = t;  }

  /** Return the type of this particle.
   * @return  one of SIGNLE, DOUBLE, BREAKER
   */
  public int getType() {  return type;  }

  /** Set the location of the particle center.
   * @param     x1  new x coordinate of particle center
   * @param     y1  new y coordinate of particle center
   */
  public void setCenter(double x1, double y1)
  {  center = new PairOfDouble(x1,y1);  }

  /** Set the location of the particle center.
   * @param     c1  position of new center
   */
  public void setCenter(PairOfDouble c1) {  center = c1;  }

  /** Return the position of the center of the particle.
   * @return   center point of this circle
   */
  public PairOfDouble getCenter() {  return center;  }

  /** Set the direction of speed for this particle (perform normalization).
   * @param     vx1  new x component of speed 
   * @param     vy1  new y component of speed
   */
  public void setDirection(double vx1, double vy1)
  {
    direction = new PairOfDouble(vx1,vy1);
    direction.normalize();
  }

  /** Set the direction of speed for this particle (perform normalization).
   * @param     sv1  new components of speed 
   */
  public void setDirection(PairOfDouble sv1)
  {
    direction = sv1;
    direction.normalize();
  }

  /** Get the direction of speed for this particle.
   * @return     components of speed vector
   */
  public PairOfDouble getDirection() {  return direction;  }

  /** Set the history of this particle (what happened to this particle
   * in the last step of the simulation).
   * @param   h   new history, one of REFLECT, SPLIT, MERGE, BORDER, PLAIN
   */
  public void setHistory(int h) {  history = h;  }

  /** Get the history of this particle (what happened to this particle
   * in the last step of the simulation).
   * @return  history, one of REFLECT, SPLIT, MERGE, BORDER, PLAIN   
   */
  public int getHistory() {  return history;  }

  /** Return the direction of the line directed from particle b1
      to this particle.
   * @param   b1   particle
   */
  public PairOfDouble axis(Particle b1)
  {
    return new PairOfDouble(center.x-b1.center.x, center.y-b1.center.y);
  }

  /** Return the direction of reflected speed when this particle hits
      a wall of given normal.
   * @param   nx   x component of wall normal
   * @param   ny   y component of wall normal
   */
  public PairOfDouble reflectDirection(double nx, double ny)
  {  return reflectDirection( new PairOfDouble(nx,ny) );  }

  /** Return the direction of reflected speed when this particle hits
      a wall of given normal.
   * @param   normal   wall normal
   */
  public PairOfDouble reflectDirection(PairOfDouble normal)
  {
    normal.normalize();

   /* if direction of incident speed is not against the normal, then
      we don't need to reflect it */

    PairOfDouble tangent = new PairOfDouble(normal);
    tangent.counterclockRotate(0.0,1.0);
    if (PairOfDouble.orientation(direction,tangent) 
        != PairOfDouble.LEFT)
    {  return new PairOfDouble(direction);  }

    /* otherwise, we compute direction of reflected speed */

    /* A = angle formed by reverse particle direction with wall normal */
    double cosA = (-direction.x*normal.x -direction.y*normal.y);
    double sinA = Math.sqrt(1-cosA*cosA);
    if (PairOfDouble.orientation(direction,normal)==PairOfDouble.RIGHT)
    {  sinA = -sinA;  }

    /* reflected direction = 
       reverse incident direction rotated 2A counterclockwise */

    PairOfDouble ref = new PairOfDouble(-direction.x,-direction.y);
    ref.counterclockRotate(cosA,sinA);
    ref.counterclockRotate(cosA,sinA);
    return ref;
  }

  /** Get particle radius (the value depends on particle type).
   * @return  the radius of this particle
   */
  public double getRadius()
  {  return radiusFromType[type];  }

  /** Get intensity of particle speed (the value depends on particle type).
   * @return  the speed value of this particle
   */
  public double getSpeedValue() 
  {  return speedFromType[type];  }

  /** Get particle speed as a vector.
   * @return  vector expressing speed of this particle
   */
  public PairOfDouble getSpeed()
  {
    PairOfDouble r = new PairOfDouble(getDirection());
    r.scale( getSpeedValue() );
    return r;
  }

  /* valutano la posizione della particella rispetto a rettangolo
     di diagonale (x0,y0)-(x1,y1) */

  /** Test if this particle if totally inside an area.
   * @param     x0  minimum x of area 
   * @param     y0  mimimum y of area 
   * @param     x1  maximum x of area 
   * @param     y1  maximum y of area
   * @return  true if this particle is strictly inside area
   *          of diagonal (x0,y0)-(x1,y1)
   */
  public boolean isTotalInside(double x0, double y0, double x1, double y1)
  {
    return ( (center.x-getRadius() >= x0)&&(center.x+getRadius() <= x1)
             &&
             (center.y-getRadius() >= y0)&&(center.y+getRadius() <= y1) );
  }

  /** Test if this particle if totally outside an area.
   * @param     x0  minimum x of area                      
   * @param     y0  mimimum y of area
   * @param     x1  maximum x of area                      
   * @param     y1  maximum y of area                      
   * @return  true if this particle is strictly outside area
   *          of diagonal (x0,y0)-(x1,y1)
   */
  public boolean isTotalOutside(double x0, double y0, double x1, double y1)
  {
    return ( (center.x+getRadius() <= x0)||(center.x-getRadius() >= x1)
             ||
             (center.y+getRadius() <= y0)||(center.y-getRadius() >= y1) );
  }

  /** Test if this particle is at least partially inside an area.
   * @param     x0  minimum x of area                      
   * @param     y0  mimimum y of area
   * @param     x1  maximum x of area                      
   * @param     y1  maximum y of area                      
   * @return  true if this particle is not strictly outside area
   *          of diagonal (x0,y0)-(x1,y1)
   */
  public boolean isPartInside(double x0, double y0, double x1, double y1)
  {
    return ( ( (center.x+getRadius() > x0)&&(center.x-getRadius() < x1) ) 
             &&
             ( (center.y+getRadius() > y0)&&(center.y-getRadius() < y1) ) );
  }

  /** Test if this particle is at least partially outside an area.
   * @param     x0  minimum x of area                      
   * @param     y0  mimimum y of area
   * @param     x1  maximum x of area                      
   * @param     y1  maximum y of area                      
   * @return  true if this particle is not strictly inside area
   *          of diagonal (x0,y0)-(x1,y1)
   */
  public boolean isPartOutside(double x0, double y0, double x1, double y1)
  {
    return ( (center.x-getRadius() < x0)||(center.x+getRadius() > x1)
             ||
             (center.y-getRadius() < y0)||(center.y+getRadius() > y1) );
  }
};
