public class DeadLock {

  public static void main(String[] args) {
    //多个资源
    A a = new A();
    B b = new B();
    //多线程循环使用资源，非抢占式
    new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized(a) {
          a.f();
          try {
            Thread.sleep(500);
            synchronized(b) {
              b.f();
            }
          } catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
    //多线程循环使用资源
    new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized(b) {
          b.f();
          try {
            Thread.sleep(500);
            synchronized(a) {
              a.f();
            }
          } catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

}

class A {
  public void f() {
    System.out.println(Thread.currentThread().getName() + " has A");
  }
}

class B {
  public void f() {
    System.out.println(Thread.currentThread().getName() + " has B");
  }
}
