// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Sat Jan 17 14:49:38 PST 2009
package scalanlp.counters.ints;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Int with type Short.
 * This trait is a wrapper around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Int2ShortCounter extends ShortCounter[Int] {


  abstract override def update(k : Int, v : Short) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Int, v : Short) :Option[Short] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Int) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Int, v : Short) = {
     update(t,(this(t) + v).asInstanceOf[Short]);
   }


  override def ++=(kv: Iterable[(Int,Short)]) = kv.foreach(+=);

  /**
   * Increments the count associated with Int by Short.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Int,Short)) = incrementCount(kv._1,kv._2);

  override def default(k : Int) : Short = 0;

  override def apply(k : Int) : Short = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Int2ShortCounter  = super.clone().asInstanceOf[Int2ShortCounter]

  /**
   * Return the Int with the largest count
   */
  override  def argmax() : Int = (elements reduceLeft ((p1:(Int,Short),p2:(Int,Short)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Int with the smallest count
   */
  override  def argmin() : Int = (elements reduceLeft ((p1:(Int,Short),p2:(Int,Short)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Short = values reduceLeft ((p1:Short,p2:Short) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Short = values reduceLeft ((p1:Short,p2:Short) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Int, b :Int) = apply(a) compare apply(b);

  /**
   * Return a new Int2DoubleCounter with each Short divided by the total;
   */
  override  def normalized() : Int2DoubleCounter = {
    val normalized = Int2DoubleCounter();
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized(pair._1) = pair._2 / total;
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Int,Short)](k,(x,y) => if(x._2 < y._2) -1 else if (x._2 == y._2) 0 else 1)(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Int2ShortCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Int2ShortCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Short]);
    }
  }

  def -=(that : Int2ShortCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Short]);
    }
  }

  override  def *=(scale : Short) {
    transform { (k,v) => (v * scale).asInstanceOf[Short]}
  }

  override  def /=(scale : Short) {
    transform { (k,v) => (v / scale).asInstanceOf[Short]}
  }
}


object Int2ShortCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Int,Short] with Int2ShortCounter {
    private val under = new Int2ShortOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Int,Short]];
    override def apply(x : Int) = under.get(x);
    override def update(x : Int, v : Short) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}
