/* INTERFACCE UTENTE - MATERIALE FORNITO PER PROGETTO DI LABORATORIO */

/**
 * Instances of this class are pair of double values.
 * They are used to represent a point in the plane (position of a particle)
 * or a two-dimensional vector (speed of a particle).
 */

public class PairOfDouble
{

  /* CLASS */

  /*
  The following constants describe the orientation of three 
  points: left i.e., counterclockwise, aligned, or
  right i.e., clockwise.
  */

  /** symbolic name for three points defining a left
      (i.e., counterclockwise) turn */
  public static final int LEFT = -1;
  /** symbolic name for three points being aligned */
  public static final int ALIGN = 0;
  /** symbolic name for three points defining a right
     (i.e., clockwise) turn */
  public static final int RIGHT = 1;

  /** Return the orientation of (type of turn defined by)
   *  the three points p1-0-p2.
   *  @param p1   first point, second point is (0,0)
   *  @param p2   third point
   *  @return     one of LEFT, ALIGN, RIGHT
   */
  public static int orientation(PairOfDouble p1, PairOfDouble p2)
  {
    double d = (p2.x*p1.y - p2.y*p1.x);
    if (d<0) return RIGHT;
    if (d>0) return LEFT;
    return ALIGN;
  }

  /** Return the orientation of (type of turn defined by) three points.
   *  @param p1   first point
   *  @param p2   second point
   *  @param p3   third point
   *  @return     one of LEFT, ALIGN, RIGHT
   */
  public static int orientation(PairOfDouble p1, PairOfDouble p2,
                                PairOfDouble p3)
  {
    double d = ( (p3.x-p2.x)*(p1.y-p2.y) - (p3.y-p2.y)*(p1.x-p2.x) );
    if (d<0) return RIGHT;
    if (d>0) return LEFT;
    return ALIGN;
  }

  /** Return the middle point of segment joining two points.
   *  @param p1   first point
   *  @param p2   second point
   *  @return     middle point of segment p1-p2
   */
  public static PairOfDouble midPoint(PairOfDouble p1, PairOfDouble p2)
  {  return new PairOfDouble(0.5*(p1.x+p2.x), 0.5*(p1.y+p2.y));  }

  /* INSTANCE */

  /*
  Instances of this class are pairs of coordinates, and represent
  either points in the plane, or two-dimensional vectors.
  */

  /* Point position or vector components */
  /** x point coordinate or x vector component */
  public double x;
  /** y point coordinate or y vector component */
  public double y;

  /** Create a point or vector with given coordinates.
   * @param     x0   x coordinate of new point
   * @param     y0   y coordinate of new point
   */
  public PairOfDouble(double x0, double y0) { x = x0; y = y0;  }

  /** Create a point or vector by copying it from a given one.
   * @param     p   point to be copied
   */
  public PairOfDouble(PairOfDouble p) { x = p.x; y = p.y;  }

  /** Return length of this vector or distance of this point from O */
  public double length() {  return Math.sqrt(x*x + y*y);  }

  /** Normalize this vector, in order to make it a versor. */
  public void normalize()
  {  double l = length();  if (l>0) {  x /= l; y /= l;  }   }

  /** Scale this point or vector.
   * @param  f    scale factor
   */
  public void scale(double f)  {  x *= f; y *= f;  }

  /** Translate this point or vector.
   * @param  p  translation vector
   */
  public void translate(PairOfDouble p) {  x += p.x; y += p.y;  }

  /** Rotate this point or vector counterclockwise.
   * @param cosA   cosinus of rotation angle
   * @param sinA   sinus of rotation angle
   */
  public void counterclockRotate(double cosA, double sinA)
  {
    double x1 = cosA*x - sinA*y;
    double y1 = sinA*x + cosA*y;
    x = x1; y = y1;
  }

};
