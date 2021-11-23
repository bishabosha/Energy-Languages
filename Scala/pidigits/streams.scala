def stream[A, B, C](
    next: B => C,
    safe: B => C => Boolean,
    prod: B => C => B,
    cons: B => A => B,
    z: B,
    as: LazyList[A]
): LazyList[C] =
  def y = next(z)
  def x = as.head
  def xs = as.tail
  if safe(z)(y) then y #:: stream(next, safe, prod, cons, prod(z)(y), as)
  else stream(next, safe, prod, cons, cons(z)(x), xs)

case class Rational(x: Long, y: Long) {

  private def gcd(a: Long, b: Long): Long =
    if (b == 0) a else gcd(b, a % b)

  private val g = gcd(x, y)

  def numer = x / g
  def denom = y / g

  def +(that: Rational): Rational =
    Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  def -(that: Rational): Rational =
    Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )
  def *(that: Rational): Rational =
    Rational(numer * that.numer, denom * that.denom)
  def /(that: Rational): Rational =
    Rational(numer * that.denom, denom * that.numer)
  override def toString = s"$numer/$denom"
  def floor =
    if (denom == 1L) numer
    else if (numer >= 0) numer / denom
    else numer / denom - 1L
}

object Rational:
  def fromLong(n: Long): Rational =
    Rational(n, 1)

case class LFT(
  q: Long, r: Long,
  s: Long, t: Long
)

def extr(qrst: LFT, x: Long): Rational = {
  val LFT(q, r, s, t) = qrst
  ((Rational.fromLong(q) * Rational.fromLong(x)) + Rational.fromLong(r))
    / ((Rational.fromLong(s) * Rational.fromLong(x)) + Rational.fromLong(t))
}

def unit: LFT =
  LFT(1, 0, 0, 1)

def comp(lft1: LFT, lft2: LFT): LFT = {
  val LFT(q, r, s, t) = lft1
  val LFT(u, v, w, x) = lft2
  LFT(q * u + r * w, q * v + r * x, s * u + t * w, s * v + t * x)
}

def pi = {
  def init = unit
  def lfts =
    for k <- LazyList.from(1)
    yield LFT(k, (4 * k) + 2, 0, (2 * k) + 1)
  def next(z: LFT) = extr(z, 3).floor
  def safe(z: LFT)(n: Long) = n == extr(z, 4).floor
  def prod(z: LFT)(n: Long) = comp(LFT(10, -10 * n, 0, 1), z)
  def cons(z: LFT)(z1: LFT) = comp(z, z1)
  stream(next, safe, prod, cons, init, lfts)
}

def piL = {
  def init = (LFT(0,4,1,0), 1l)
  def lfts =
    for i <- LazyList.iterate(1l)(_ + 1)
    yield LFT(2*i -1, i * i, 1, 0)
  def next(z: (LFT, Long)) = {
    val (LFT(q, r, s, t), i) = z
    val x = 2*i -1
    Rational(q*x+r, s*x+t).floor
  }
  def safe(z: (LFT, Long))(n: Long) = {
    val (LFT(q, r, s, t), i) = z
    val x = 5*i -2
    n == Rational(q*x+2*r, s*x+2*t).floor
  }
  def prod(p: (LFT, Long))(n: Long) = {
    val (z, i) = p
    (comp(LFT(10, -10*n, 0, 1), z), i)
  }
  def cons(p: (LFT, Long))(z1: LFT) = {
    val (z, i) = p
    (comp(z, z1), i + 1)
  }
  stream(next, safe, prod, cons, init, lfts)
}

def format(ll: LazyList[Long], digits: Int) =
  val is = ll.take(digits).takeWhile(_ >= 0).toList
  val base = is match
    case Nil => "?"
    case i :: Nil => s"$i"
    case i :: is => s"$i.${is.mkString("")}"
  val append = if is.lengthCompare(digits) == 0 then "" else "...(precision lost)"
  s"$base$append"

@main def printPi(digits: Int) =
  println(format(pi, digits))

@main def printPiL(digits: Int) =
  println(format(piL, digits))

@main def conv =
  val res = convert((3, 7), LazyList(1,0,0,2,2,1,0,1,1,2))
  println(res.take(5).toList)

def convert(p: (Long,Long), ps: LazyList[Long]): LazyList[Long] = {
  val (m, n) = p
  def init = (Rational(0, 1), Rational(1, 1))
  def next(p: (Rational, Rational)) =
    val (u, v) = p
    (u*v*n1).floor
  def safe(p: (Rational, Rational))(y: Long) =
    val (u, v) = p
    y == ((u+Rational.fromLong(1))*v*n1).floor
  def prod(p: (Rational, Rational))(y: Long) =
    val (u, v) = p
    (u - Rational.fromLong(y) / (v * n1), v*n1)
  def cons(p: (Rational, Rational))(x: Long) =
    val (u, v) = p
    (Rational.fromLong(x) + u * m1, v/m1)
  def m1 = Rational.fromLong(m)
  def n1 = Rational.fromLong(n)
  stream(next, safe, prod, cons, init, ps)
}
